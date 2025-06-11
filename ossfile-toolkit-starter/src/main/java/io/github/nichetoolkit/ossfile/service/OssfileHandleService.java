package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Path;

@Slf4j
@Service
public class OssfileHandleService  {

    protected final OssfileProperties properties;

    @Autowired
    public OssfileHandleService(OssfileProperties properties) {
        this.properties = properties;
    }

    @Async
    public void handleOfSignature(OssfileBulkModel bulkModel) {
        InputStream inputStream = bulkModel.inputStream();
        BufferedImage bufferedImage = ImageUtils.read(inputStream);
        BufferedImage binaryImage = ImageUtils.binaryImage(bufferedImage);
        BufferedImage signatureImage = ImageUtils.signature(binaryImage);
        byte[] bytes = ImageUtils.bytesJpeg(signatureImage);
        bulkModel.setBytes(bytes);
        String md5 = DigestUtils.md2Hex(bytes);
        bulkModel.setFileMd5(md5);
    }

    @Async
    public void handleOfImage(OssfileBulkModel bulkModel, Integer width, Integer height) {
        byte[] bytes = bytesOfImage(bulkModel.inputStream(), width, height);
        bulkModel.setBytes(bytes);
        String md5 = DigestUtils.md2Hex(bytes);
        bulkModel.setFileMd5(md5);
        bulkModel.setFileSize((long) bytes.length);
    }

    @Async
    public void handleOfPreview(OssfileBulkModel bulkModel, Integer width, Integer height) throws RestException {
        byte[] bytes = bytesOfImage(bulkModel.inputStream(), width, height);
        bulkModel.setBytes(bytes);
        String md5 = DigestUtils.md2Hex(bytes);
        bulkModel.setFileMd5(md5);
        bulkModel.setFileSize((long) bytes.length);

        String bucket = RestOptional.ofEmptyable(bulkModel.getBucket()).orElse(OssfileStoreHolder.defaultBucket());

        String objectKey = bulkModel.getObjectKey();
        OssfileServiceHolder.storeService().putOssfile(bucket, objectKey, bulkModel.inputStream());
        OssfileServiceHolder.bulkService().save(bulkModel);
    }

    @Async
    public void handleOfFile(OssfileBulkModel bulkModel) {
        String filename = bulkModel.getFilename();
        Path tempFile = FileUtils.createTempFile(filename, OssfileConstants.FILE_ZIP_SUFFIX);
        ZipUtils.zip(tempFile, filename, bulkModel.inputStream());
        byte[] bytes = IoStreamUtils.bytes(tempFile);
        bulkModel.setBytes(bytes);
        String md5 = DigestUtils.md2Hex(bytes);
        bulkModel.setFileMd5(md5);
        bulkModel.setFileSize((long) bytes.length);
        FileUtils.clear(tempFile);
    }

    private byte[] bytesOfImage(InputStream inputStream, Integer width, Integer height) {
        double quality = 0.9d;
        double scale = 1.0d;
        boolean isScale = false;
        long size;
        byte[] bytes;
        OssfileProperties.OssImage image = properties.getImage();
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

}
