package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.ossfile.OssfileConstants;
import io.github.nichetoolkit.ossfile.image.OssfileErrorStatus;
import io.github.nichetoolkit.ossfile.helper.FileServiceHelper;
import io.github.nichetoolkit.ossfile.OssfileImageHelper;
import io.github.nichetoolkit.ossfile.OssfileBulkModel;
import io.github.nichetoolkit.ossfile.OssfileImageUtils;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.util.FileUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.StreamUtils;
import io.github.nichetoolkit.rest.util.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>FileHandleServiceImpl</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Service
public class FileHandleServiceImpl implements FileHandleService {

    @Autowired
    protected OssfileProperties commonProperties;
    @Autowired
    private OssfileProperties serviceProperties;

    @Async
    @Override
    public void autographImage(OssfileBulkModel fileIndex) throws RestException {
        String tempPath = FileUtils.createPath(commonProperties.getTempPath());
        String cachePath = FileUtils.createPath(tempPath, fileIndex.getId());
        String randomPath = FileUtils.createPath(cachePath, GeneralUtils.uuid());
        InputStream inputStream = fileIndex.inputStream();
        BufferedImage bufferedImage = OssfileImageUtils.read(inputStream);
        BufferedImage binaryImage = OssfileImageUtils.binaryImage(bufferedImage);
        BufferedImage autographImage = OssfileImageUtils.autograph(binaryImage);
        String filename = fileIndex.getFilename().concat(OssfileConstants.SUFFIX_REGEX).concat(OssfileConstants.IMAGE_PNG_SUFFIX);
        String filePath = randomPath.concat(File.separator).concat(filename);
        File file = new File(filePath);
        if (file.exists()) {
            FileUtils.delete(filePath);
        }
        OssfileImageUtils.write(autographImage, file);
        byte[] bytes = OssfileImageUtils.bytes(file);
        fileIndex.setBytes(bytes);
        FileUtils.delete(filePath);
        FileUtils.clear(randomPath);
        FileUtils.clear(cachePath);
    }

    @Async
    @Override
    public void condenseImage(OssfileBulkModel fileIndex) throws RestException {
        String tempPath = FileUtils.createPath(commonProperties.getTempPath());
        String cachePath = FileUtils.createPath(tempPath, fileIndex.getId());
        String randomPath = FileUtils.createPath(cachePath, GeneralUtils.uuid());
        Long imageFileSize;
        Double imageFileQuality = 1.0d;
        Double imageFileScale = 1.0d;
        String filename = fileIndex.getFilename().concat(OssfileConstants.SUFFIX_REGEX).concat(OssfileConstants.IMAGE_PNG_SUFFIX);
        String filePath;
        File file;
        Integer width = fileIndex.getWidth();
        Integer height = fileIndex.getHeight();
        filePath = randomPath.concat(File.separator).concat(filename);
        file = new File(filePath);
        do {
            if (file.exists()) {
                FileUtils.delete(filePath);
            }
            try {
                Thumbnails.of(fileIndex.inputStream()).scale(imageFileScale).outputFormat(OssfileConstants.IMAGE_PNG_SUFFIX).outputQuality(imageFileQuality).toFile(filePath);
                BufferedImage bufferedImage = OssfileImageHelper.read(file);
                int imageWidth = bufferedImage.getWidth();
                int imageHeight = bufferedImage.getHeight();
                if (GeneralUtils.isNotEmpty(width) && GeneralUtils.isNotEmpty(height)) {
                    Thumbnails.of(fileIndex.inputStream()).size(width, height).outputFormat(OssfileConstants.IMAGE_PNG_SUFFIX).outputQuality(imageFileQuality).toFile(filePath);
                } else if (GeneralUtils.isNotEmpty(width) || GeneralUtils.isNotEmpty(height)) {
                    if (GeneralUtils.isNotEmpty(width)) {
                        imageFileScale = ((double) width / (double) imageWidth >= 1.0D) ? imageFileScale : ((double) width / (double) imageWidth);
                    } else {
                        imageFileScale = ((double) height / (double) imageHeight >= 1.0D) ? imageFileScale : ((double) height / (double) imageHeight);
                    }
                    Thumbnails.of(fileIndex.inputStream()).scale(imageFileScale).outputFormat(OssfileConstants.IMAGE_PNG_SUFFIX).outputQuality(imageFileQuality).toFile(filePath);
                } else {
                    Thumbnails.of(fileIndex.inputStream()).scale(imageFileScale).outputFormat(OssfileConstants.IMAGE_PNG_SUFFIX).outputQuality(imageFileQuality).toFile(filePath);
                }
            } catch (IOException exception) {
                log.error("the image file has error during condensing: {}", exception.getMessage());
                throw new FileErrorException(OssfileErrorStatus.FILE_IMAGE_CONDENSE_ERROR);
            }
            imageFileSize = file.length();
            if (imageFileQuality.equals(serviceProperties.getMinImageQuality())) {
                imageFileScale += -0.1d;
            } else {
                imageFileQuality += -0.1d;
            }
        } while (imageFileSize > serviceProperties.getMaxImageSize()
                && imageFileQuality > serviceProperties.getMinImageQuality()
                && imageFileScale > serviceProperties.getMinImageScale());
        if (GeneralUtils.isNotEmpty(width) && GeneralUtils.isNotEmpty(height)) {
            fileIndex.addProperty(OssfileConstants.IMAGE_CONDENSE_WIDTH_PROPERTY, width);
            fileIndex.addProperty(OssfileConstants.IMAGE_CONDENSE_HEIGHT_PROPERTY, height);
        } else {
            fileIndex.addProperty(OssfileConstants.IMAGE_CONDENSE_SCALE_PROPERTY, imageFileScale);
        }
        fileIndex.addProperty(OssfileConstants.IMAGE_CONDENSE_QUALITY_PROPERTY, imageFileQuality);
        FileServiceHelper.buildProperties(filename, file.length(), OssfileConstants.IMAGE_PNG_SUFFIX, fileIndex);
        FileServiceHelper.buildMd5(file, fileIndex);
        FileUtils.delete(filePath);
        FileUtils.clear(randomPath);
        FileUtils.clear(cachePath);
    }

    @Async
    @Override
    public void condenseFile(OssfileBulkModel fileIndex) throws RestException {
        String tempPath = FileUtils.createPath(commonProperties.getTempPath());
        String cachePath = FileUtils.createPath(tempPath, fileIndex.getId());
        String randomPath = FileUtils.createPath(cachePath, GeneralUtils.uuid());
        String filename = fileIndex.getName();
        String zipFilename = fileIndex.getFilename().concat(OssfileConstants.SUFFIX_REGEX).concat(OssfileConstants.FILE_ZIP_SUFFIX);
        String filePath = randomPath.concat(File.separator).concat(filename);
        File file = FileUtils.createFile(filePath);
        StreamUtils.write(file, fileIndex.inputStream());
        File zipFile = ZipUtils.zipFile(randomPath, zipFilename, file);
        FileServiceHelper.buildProperties(zipFilename, zipFile.length(), OssfileConstants.FILE_ZIP_SUFFIX, fileIndex);
        if (fileIndex.getIsMd5()) {
            FileServiceHelper.buildMd5(zipFile, fileIndex);
        }
        FileUtils.delete(filePath);
        FileUtils.clear(randomPath);
        FileUtils.clear(cachePath);
    }
}
