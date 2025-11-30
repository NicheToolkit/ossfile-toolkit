package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel;
import io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel;
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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * <code>OssfileHandleService</code>
 * <p>The ossfile handle service class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Service
 * @since Jdk17
 */
@Slf4j
@Service
public class OssfileHandleService {

    /**
     * <code>handleOfPartFinish</code>
     * <p>The handle of part finish method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    public void handleOfPartFinish(OssfileBulkModel bulkModel) throws RestException {
        OssfileStoreService storeService = OssfileServiceHolder.storeService();
        String bucket = bulkModel.getBucket();
        String objectKey = bulkModel.getObjectKey();
        String uploadId = bulkModel.getUploadId();
        List<OssfilePartModel> parts = bulkModel.getParts();
        List<OssfilePartETag> partETags = parts.stream().map(OssfilePartModel::toPartETag).collect(Collectors.toList());
        Future<OssfileETagVersion> finishMultipart = storeService.finishMultipart(bucket, objectKey, uploadId, partETags);
        handleOfFuture(finishMultipart, eTagVersion -> {
            bulkModel.etagVersion(eTagVersion);
            bulkModel.setFinishState(true);
            bulkModel.setCompleteTime(new Date());
            OssfileServiceHolder.bulkService().save(bulkModel);
        });
    }

    /**
     * <code>handleOfPartUpload</code>
     * <p>The handle of part upload method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @param partModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel} <p>The part model parameter is <code>OssfilePartModel</code> type.</p>
     * @param lastPart  boolean <p>The last part parameter is <code>boolean</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    public void handleOfPartUpload(OssfileBulkModel bulkModel,OssfilePartModel partModel, boolean lastPart) throws RestException {
        OssfileStoreService storeService = OssfileServiceHolder.storeService();
        String bucket = partModel.getBucket();
        String objectKey = partModel.getObjectKey();
        String uploadId = partModel.getUploadId();
        InputStream inputStream = partModel.inputStream();
        Integer partIndex = partModel.getPartIndex();
        Long partSize = partModel.getPartSize();
        Future<OssfilePartETag> partETagFuture = storeService.uploadMultipart(bucket, objectKey, uploadId, inputStream, partIndex, partSize);
        handleOfFuture(partETagFuture, partETag -> {
            partModel.setPartEtag(partETag.getPartEtag());
            OssfileServiceHolder.partService().save(partModel);
            if (lastPart) {
                List<OssfilePartModel> partModels = OssfileServiceHolder.partService().queryByLinkId(partModel.toPartLinks());
                bulkModel.setParts(partModels);
                handleOfPartFinish(bulkModel);
            }
        });
    }

    /**
     * <code>handleOfPartStart</code>
     * <p>The handle of part start method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    public void handleOfPartStart(OssfileBulkModel bulkModel) throws RestException {
        OssfileStoreService storeService = OssfileServiceHolder.storeService();
        Future<String> uploadIdFuture = storeService.startMultipart(bulkModel.getBucket(), bulkModel.getObjectKey());
        handleOfFuture(uploadIdFuture, uploadId -> {
            bulkModel.setUploadId(uploadId);
            OssfileServiceHolder.bulkService().save(bulkModel);
        });
    }


    /**
     * <code>handleOfFile</code>
     * <p>The handle of file method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    public void handleOfFile(OssfileBulkModel bulkModel) throws RestException {
        byte[] bytes = IoStreamUtils.bytes(bulkModel.inputStream());
        bulkModel.ofBytes(bytes);
        handleOfOssfileStore(bulkModel, bulkModel.getObjectKey());
    }

    /**
     * <code>handleOfSignature</code>
     * <p>The handle of signature method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    public void handleOfSignature(OssfileBulkModel bulkModel) throws RestException {
        InputStream inputStream = bulkModel.inputStream();
        BufferedImage bufferedImage = ImageUtils.read(inputStream);
        BufferedImage binaryImage = ImageUtils.binaryImage(bufferedImage);
        BufferedImage signatureImage = ImageUtils.signature(binaryImage);
        byte[] bytes = ImageUtils.bytesPng(signatureImage);
        bulkModel.ofBytes(bytes);
        handleOfOssfileStore(bulkModel, bulkModel.getObjectKey());
    }

    /**
     * <code>handleOfImageCompress</code>
     * <p>The handle of image compress method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @param width     {@link java.lang.Integer} <p>The width parameter is <code>Integer</code> type.</p>
     * @param height    {@link java.lang.Integer} <p>The height parameter is <code>Integer</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see java.lang.Integer
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    public void handleOfImageCompress(OssfileBulkModel bulkModel, Integer width, Integer height) throws RestException {
        byte[] bytes = bytesOfImage(bulkModel.inputStream(), width, height);
        bulkModel.ofBytes(bytes);
        handleOfOssfileStore(bulkModel, bulkModel.getObjectKey());
    }

    /**
     * <code>handleOfImagePreviewAndCompress</code>
     * <p>The handle of image preview and compress method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @param width     {@link java.lang.Integer} <p>The width parameter is <code>Integer</code> type.</p>
     * @param height    {@link java.lang.Integer} <p>The height parameter is <code>Integer</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see java.lang.Integer
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    public void handleOfImagePreviewAndCompress(OssfileBulkModel bulkModel, Integer width, Integer height) throws RestException {
        byte[] bytes = bytesOfImage(bulkModel.inputStream(), width, height);
        bulkModel.ofBytes(bytes);
        handleOfOssfileStore(bulkModel, bulkModel.getObjectKey());
    }

    /**
     * <code>handleOfImagePreview</code>
     * <p>The handle of image preview method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @param width     {@link java.lang.Integer} <p>The width parameter is <code>Integer</code> type.</p>
     * @param height    {@link java.lang.Integer} <p>The height parameter is <code>Integer</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see java.lang.Integer
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    public void handleOfImagePreview(OssfileBulkModel bulkModel, Integer width, Integer height) throws RestException {
        byte[] bytes = bytesOfImage(bulkModel.inputStream(), width, height);
        bulkModel.ofBytes(bytes);
        handleOfOssfileStore(bulkModel, bulkModel.getPreviewKey());
    }

    /**
     * <code>handleOfFileCompress</code>
     * <p>The handle of file compress method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    public void handleOfFileCompress(OssfileBulkModel bulkModel) throws RestException {
        String filename = bulkModel.getFilename();
        Path tempFile = FileUtils.createTempFile(filename, OssfileConstants.FILE_ZIP_SUFFIX);
        ZipUtils.zip(tempFile, filename, bulkModel.inputStream());
        byte[] bytes = IoStreamUtils.bytes(tempFile);
        bulkModel.ofBytes(bytes);
        handleOfOssfileStore(bulkModel, bulkModel.getObjectKey());
        FileUtils.clear(tempFile);
    }

    /**
     * <code>handleOfOssfileStore</code>
     * <p>The handle of ossfile store method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    public void handleOfOssfileStore(OssfileBulkModel bulkModel, String objectKey) throws RestException {
        OssfileStoreService storeService = OssfileServiceHolder.storeService();
        Future<OssfileETagVersion> ossfileFuture = storeService.putOssfile(bulkModel.getBucket(), objectKey, bulkModel.inputStream());
        handleOfFuture(ossfileFuture, eTagVersion -> {
            bulkModel.etagVersion(eTagVersion);
            bulkModel.setCompleteTime(new Date());
            OssfileServiceHolder.bulkService().save(bulkModel);
        });
    }

    /**
     * <code>bytesOfImage</code>
     * <p>The bytes of image method.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param width       {@link java.lang.Integer} <p>The width parameter is <code>Integer</code> type.</p>
     * @param height      {@link java.lang.Integer} <p>The height parameter is <code>Integer</code> type.</p>
     * @return byte <p>The bytes of image return object is <code>byte</code> type.</p>
     * @see java.io.InputStream
     * @see java.lang.Integer
     */
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


    /**
     * <code>handleOfFuture</code>
     * <p>The handle of future method.</p>
     * @param <F>              {@link java.lang.Object} <p>The parameter can be of any type.</p>
     * @param ossfileFuture    {@link java.util.concurrent.Future} <p>The ossfile future parameter is <code>Future</code> type.</p>
     * @param consumerActuator {@link io.github.nichetoolkit.rest.actuator.ConsumerActuator} <p>The consumer actuator parameter is <code>ConsumerActuator</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.concurrent.Future
     * @see io.github.nichetoolkit.rest.actuator.ConsumerActuator
     * @see io.github.nichetoolkit.rest.RestException
     */
    private <F> void handleOfFuture(Future<F> ossfileFuture, ConsumerActuator<F> consumerActuator) throws RestException {
        try {
            F ossfileValue = ossfileFuture.get();
            consumerActuator.actuate(ossfileValue);
        } catch (InterruptedException | ExecutionException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_HANDLE_ERROR, exception.getMessage());
        }
    }

}
