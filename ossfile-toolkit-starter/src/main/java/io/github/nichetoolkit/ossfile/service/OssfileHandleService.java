package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.actuator.ConsumerActuator;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Service
public class OssfileHandleService {

    @Async
    public void handleOfFile(OssfileBulkModel bulkModel) throws RestException {
        byte[] bytes = IoStreamUtils.bytes(bulkModel.inputStream());
        bulkModel.ofBytes(bytes).defaultAssert();
        handleOfOssfileStore(bulkModel,bulkModel.getObjectKey());
    }

    @Async
    public void handleOfSignature(OssfileBulkModel bulkModel) throws RestException {
        InputStream inputStream = bulkModel.inputStream();
        BufferedImage bufferedImage = ImageUtils.read(inputStream);
        BufferedImage binaryImage = ImageUtils.binaryImage(bufferedImage);
        BufferedImage signatureImage = ImageUtils.signature(binaryImage);
        byte[] bytes = ImageUtils.bytesPng(signatureImage);
        bulkModel.ofBytes(bytes).defaultAssert();
        handleOfOssfileStore(bulkModel,bulkModel.getObjectKey());
    }

    @Async
    public void handleOfImageCompress(OssfileBulkModel bulkModel, Integer width, Integer height) throws RestException {
        byte[] bytes = bytesOfImage(bulkModel.inputStream(), width, height);
        bulkModel.ofBytes(bytes).defaultAssert();
        handleOfOssfileStore(bulkModel,bulkModel.getObjectKey());
    }

    public void handleOfImagePreviewAndCompress(OssfileBulkModel bulkModel, Integer width, Integer height) throws RestException {
        byte[] bytes = bytesOfImage(bulkModel.inputStream(), width, height);
        bulkModel.ofBytes(bytes).copyAssert();
        handleOfOssfileStore(bulkModel,bulkModel.getObjectKey());
    }



    @Async
    public void handleOfImagePreview(OssfileBulkModel bulkModel, Integer width, Integer height) throws RestException {
        byte[] bytes = bytesOfImage(bulkModel.inputStream(), width, height);
        bulkModel.ofBytes(bytes).previewAssert();
        handleOfOssfileStore(bulkModel,bulkModel.getPreviewKey());
    }

    @Async
    public void handleOfFileCompress(OssfileBulkModel bulkModel) throws RestException {
        String filename = bulkModel.getFilename();
        Path tempFile = FileUtils.createTempFile(filename, OssfileConstants.FILE_ZIP_SUFFIX);
        ZipUtils.zip(tempFile, filename, bulkModel.inputStream());
        byte[] bytes = IoStreamUtils.bytes(tempFile);
        bulkModel.ofBytes(bytes).defaultAssert();
        handleOfOssfileStore(bulkModel,bulkModel.getObjectKey());
        FileUtils.clear(tempFile);
    }

    public void handleOfOssfileStore(OssfileBulkModel bulkModel, String objectKey) throws RestException {
        OssfileStoreService storeService = OssfileServiceHolder.storeService();
        Future<OssfileETagVersion> ossfileFuture = storeService.putOssfile(bulkModel.getBucket(), objectKey, bulkModel.inputStream());
        handleOfFuture(ossfileFuture, eTagVersion -> {
            bulkModel.setETagVersion(eTagVersion);
            bulkModel.setCompleteTime(new Date());
            OssfileServiceHolder.bulkService().save(bulkModel);
        });
    }

    private byte[] bytesOfImage(InputStream inputStream, Integer width, Integer height) {
        double quality = 0.9d;
        double scale = 1.0d;
        boolean isScale = false;
        long size;
        byte[] bytes;
        OssfileProperties.OssImage image = OssfileStoreHolder.properties().getImage();
        BufferedImage bufferedImage = ImageUtils.read(inputStream);
        do {
            if (GeneralUtils.isNotEmpty(width) && GeneralUtils.isNotEmpty(height)) {
                bufferedImage = ImageUtils.simplePng(bufferedImage, width, height, quality);
            } else if (GeneralUtils.isNotEmpty(width) || GeneralUtils.isNotEmpty(height)) {
                bufferedImage = ImageUtils.sizePng(bufferedImage, width, height, quality);
            } else {
                isScale = true;
                bufferedImage = ImageUtils.simplePng(inputStream, scale, quality);
            }
            bytes = ImageUtils.bytesPng(bufferedImage);
            size = bytes.length;
            if (quality == image.getQuality() && isScale) {
                scale -= 0.1d;
            } else {
                quality -= 0.1d;
            }
        } while (size > image.getCompress() && (quality > image.getQuality() || (isScale && scale > image.getScale())));
        return bytes;
    }


    private <F> void handleOfFuture(Future<F> ossfileFuture, ConsumerActuator<F> consumerActuator) throws RestException {
        try {
            F ossfileValue = ossfileFuture.get();
            consumerActuator.actuate(ossfileValue);
        } catch (InterruptedException | ExecutionException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_HANDLE_ERROR, exception.getMessage());
        }
    }

}
