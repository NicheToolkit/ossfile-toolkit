package io.github.nichetoolkit.ossfile.helper;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.ossfile.OssfileConstants;
import io.github.nichetoolkit.ossfile.OssfileBulkEntity;
import io.github.nichetoolkit.ossfile.OssfileFileType;
import io.github.nichetoolkit.ossfile.image.OssfileErrorStatus;
import io.github.nichetoolkit.ossfile.OssfileFilter;
import io.github.nichetoolkit.ossfile.OssfilePartModel;
import io.github.nichetoolkit.ossfile.OssfileBulkModel;
import io.github.nichetoolkit.ossfile.OssfileRequest;
import io.github.nichetoolkit.ossfile.service.FileChunkService;
import io.github.nichetoolkit.ossfile.service.FileIndexService;
import io.github.nichetoolkit.ossfile.util.Md5Utils;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.identity.IdentityUtils;
import io.github.nichetoolkit.rest.util.*;
import io.github.nichetoolkit.rice.RestPage;
import io.github.nichetoolkit.rice.helper.PropertyHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>FileServerHelper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Component
public class FileServiceHelper implements InitializingBean {

    @Autowired
    private OssfileProperties commonProperties;

    @Autowired
    private FileIndexService fileIndexService;

    @Autowired
    private FileChunkService fileChunkService;

    private static FileServiceHelper INSTANCE = null;

    public static FileServiceHelper getInstance() {
        return INSTANCE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }

    public static void checkRestPage(RestPage restPage) throws RestException {
        if (GeneralUtils.isEmpty(restPage) || GeneralUtils.isEmpty(restPage.getItems())) {
            log.warn("the file service query result is empty!");
            throw new FileErrorException(OssfileErrorStatus.FILE_NO_FOUND_ERROR);
        }
    }

    public static void buildProperties(String filename, long size, String suffix, OssfileBulkModel fileIndex) {
        fileIndex.addProperty(OssfileConstants.ORIGINAL_SUFFIX_PROPERTY, fileIndex.getSuffix());
        fileIndex.setSuffix(suffix);
        fileIndex.addProperty(OssfileConstants.ORIGINAL_NAME_PROPERTY, fileIndex.getName());
        fileIndex.setName(filename);
        fileIndex.addProperty(OssfileConstants.ORIGINAL_SIZE_PROPERTY, fileIndex.getFileSize());
        fileIndex.setFileSize(size);
        fileIndex.addProperty(OssfileConstants.ORIGINAL_MD5_PROPERTY, fileIndex.getFileMd5());
    }


    public static void buildChunkFiles(List<OssfileBulkModel> fileIndices, OssfileFilter fileFilter, String randomPath, List<File> fileList) throws RestException {
        for (OssfileBulkModel fileIndex : fileIndices) {
            List<OssfilePartModel> fileChunks = fileIndex.getFileChunks();
            for (OssfilePartModel fileChunk : fileChunks) {
                String itemFilename = fileIndex.getAlias().concat("_").concat(String.valueOf(fileChunk.getChunkIndex())).concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
                if (fileFilter.isOriginal()) {
                    itemFilename = fileIndex.getFilename().concat("_").concat(String.valueOf(fileChunk.getChunkIndex())).concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
                }
                String itemFilePath = randomPath.concat(File.separator).concat(itemFilename);
                FileHandleHelper.writeFile(fileChunk.getId(), itemFilePath);
                fileList.add(new File(itemFilePath));
            }
        }
    }


    public static void buildIndexFiles(List<OssfileBulkModel> fileIndices, OssfileFilter fileFilter, String randomPath, List<File> fileList) throws RestException {
        for (OssfileBulkModel fileIndex : fileIndices) {
            if (fileIndex.getFileSize() > INSTANCE.commonProperties.getMaxFileSize()) {
                log.warn("the file size is too large, id: {}, size: {}", fileIndex.getId(), fileIndex.getFileSize());
                throw new FileErrorException(OssfileErrorStatus.FILE_TOO_LARGE_ERROR);
            }
            String itemFilename = fileIndex.getId().concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
            if (fileFilter.isOriginal()) {
                itemFilename = fileIndex.getFilename().concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
            }
            String itemFilePath = randomPath.concat(File.separator).concat(itemFilename);
            FileHandleHelper.writeFile(fileIndex.getId(), itemFilePath);
            fileList.add(new File(itemFilePath));
        }
    }

    public static OssfileBulkModel createFileIndex(OssfileBulkModel fileIndex) throws RestException {
        if (GeneralUtils.isEmpty(fileIndex)) {
            log.warn("the file index is null!");
            throw new FileErrorException(OssfileErrorStatus.FILE_INDEX_IS_NULL);
        }
        String originalFilename = fileIndex.getName();
        if (GeneralUtils.isEmpty(originalFilename)) {
            log.warn("the name of file index is null!");
            throw new FileErrorException(OssfileErrorStatus.FILE_INDEX_NAME_IS_NULL);
        }
        if (GeneralUtils.isEmpty(fileIndex.getFileSize())) {
            log.warn("the size of file index is null!");
            throw new FileErrorException(OssfileErrorStatus.FILE_INDEX_SIZE_IS_NULL);
        }
        String filename = FileUtils.filename(originalFilename);
        if (GeneralUtils.isEmpty(fileIndex.getFilename())) {
            fileIndex.setFilename(filename);
        }
        if (GeneralUtils.isEmpty(fileIndex.getAlias())) {
            fileIndex.setAlias(String.valueOf(System.currentTimeMillis()));
        }
        String suffix = FileUtils.suffix(originalFilename);
        if (GeneralUtils.isEmpty(fileIndex.getSuffix())) {
            fileIndex.setSuffix(suffix);
        }
        OssfileFileType fileType = parseType(suffix);
        fileIndex.setFileType(fileType);
        if (GeneralUtils.isEmpty(fileIndex.getIsCondense())) {
            if (fileType == OssfileFileType.IMAGE) {
                fileIndex.setIsCondense(true);
            } else {
                fileIndex.setIsCondense(false);
            }
        }
        fileIndex.setIsSlice(true);
        fileIndex.setCurrentIndex(0);
        fileIndex.setIsFinish(false);
        fileIndex.setCreateTime(new Date());
        return fileIndex;
    }

    public static void buildMd5(MultipartFile file, OssfileBulkModel fileIndex) throws RestException {
        try {
            buildMd5(file.getInputStream(), fileIndex);
        } catch (IOException exception) {
            log.error("the file read with bytes has error, filename: {}, error: {}", fileIndex.getName(), exception.getMessage());
            throw new FileErrorException(OssfileErrorStatus.FILE_READ_BYTE_ERROR);
        }
    }

    public static void buildMd5(File file, OssfileBulkModel fileIndex) throws RestException {
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            buildMd5(fileInputStream, fileIndex);
        } catch (IOException exception) {
            log.error("the file read with bytes has error, filename: {}, error: {}", fileIndex.getName(), exception.getMessage());
            throw new FileErrorException(OssfileErrorStatus.FILE_READ_BYTE_ERROR);
        }
    }

    public static void buildMd5(InputStream inputStream, OssfileBulkModel fileIndex) throws RestException {
        byte[] bytes = StreamUtils.bytes(inputStream);
        buildMd5(bytes, fileIndex);
    }

    public static void buildMd5(byte[] bytes, OssfileBulkModel fileIndex) throws RestException {
        if (GeneralUtils.isEmpty(bytes)) {
            log.error("the file read with bytes is null, filename: {}", fileIndex.getName());
            throw new FileErrorException(OssfileErrorStatus.FILE_READ_BYTE_NULL);
        }
        fileIndex.setBytes(bytes);
        String md5 = Md5Utils.md5(bytes);
        fileIndex.setFileMd5(md5);
    }

    public static void buildMd5(MultipartFile file, OssfilePartModel fileChunk) throws RestException {
        try {
            buildMd5(file.getInputStream(), fileChunk);
        } catch (IOException exception) {
            log.error("the chunk read with bytes has error, chunk index: {}, error: {}", fileChunk.getChunkIndex(), exception.getMessage());
            throw new FileErrorException(OssfileErrorStatus.FILE_READ_BYTE_ERROR);
        }
    }

    public static void buildMd5(File file, OssfilePartModel fileChunk) throws RestException {
        try {
            buildMd5(new FileInputStream(file), fileChunk);
        } catch (IOException exception) {
            log.error("the chunk read with bytes has error, chunk index: {}, error: {}", fileChunk.getChunkIndex(), exception.getMessage());
            throw new FileErrorException(OssfileErrorStatus.FILE_READ_BYTE_ERROR, exception.getMessage());
        }
    }

    public static void buildMd5(InputStream inputStream, OssfilePartModel fileChunk) throws RestException {
        byte[] bytes = StreamUtils.bytes(inputStream);
        buildMd5(bytes, fileChunk);
    }

    public static void buildMd5(byte[] bytes, OssfilePartModel fileChunk) throws RestException {
        if (GeneralUtils.isEmpty(bytes)) {
            log.error("the chunk read with bytes is null, chunk index: {}", fileChunk.getChunkIndex());
            throw new FileErrorException(OssfileErrorStatus.FILE_READ_BYTE_NULL);
        }
        fileChunk.setBytes(bytes);
        String md5 = Md5Utils.md5(bytes);
        fileChunk.setChunkMd5(md5);
    }

    public static OssfileBulkModel createFileChunk(MultipartFile multipartFile, OssfileBulkModel fileIndex) throws RestException {
        if (GeneralUtils.isEmpty(fileIndex)) {
            log.warn("the file index is null!");
            throw new FileErrorException(OssfileErrorStatus.FILE_INDEX_IS_NULL);
        }
        OssfilePartModel fileChunk = fileIndex.getFileChunk();
        if (GeneralUtils.isEmpty(fileIndex)) {
            log.warn("the file chunk is null!");
            throw new FileErrorException(OssfileErrorStatus.FILE_CHUNK_IS_NULL);
        }
        if (GeneralUtils.isEmpty(fileChunk.getFileId())) {
            String fileIndexId = fileIndex.getId();
            if (GeneralUtils.isEmpty(fileIndexId)) {
                log.error("the param of 'fileId' for file chunk is null! ");
                throw new FileErrorException(OssfileErrorStatus.FILE_CHUNK_PARAM_ERROR);
            }
            fileChunk.setFileId(fileIndexId);
        }
        OssfileBulkModel queryFileIndex = INSTANCE.fileIndexService.queryById(fileIndex.getId());
        if (GeneralUtils.isEmpty(queryFileIndex)) {
            log.warn("the file service query result is empty!");
            throw new FileErrorException(OssfileErrorStatus.FILE_INDEX_IS_NULL);
        }
        queryFileIndex.setFileChunk(fileIndex.getFileChunk());
        queryFileIndex.setSliceSize(fileIndex.getSliceSize());
        fileIndex = queryFileIndex;
        if (GeneralUtils.isEmpty(fileChunk.getChunkIndex())) {
            log.error("the param of 'chunkIndex' for file chunk is null! ");
            throw new FileErrorException(OssfileErrorStatus.FILE_CHUNK_PARAM_ERROR);
        }
        Long chunkSize = fileChunk.getChunkSize();
        if (GeneralUtils.isEmpty(chunkSize)) {
            chunkSize = multipartFile.getSize();
            fileChunk.setChunkSize(multipartFile.getSize());
        }
        Long chunkStart = fileChunk.getChunkStart();
        if (chunkStart == null) {
            log.error("the param of 'chunkStart' for file chunk is null! ");
            throw new FileErrorException(OssfileErrorStatus.FILE_CHUNK_PARAM_ERROR);
        }
        Long chunkEnd = fileChunk.getChunkEnd();
        if (GeneralUtils.isEmpty(chunkEnd)) {
            log.error("the param of 'chunkEnd' for file chunk is null! ");
            throw new FileErrorException(OssfileErrorStatus.FILE_CHUNK_PARAM_ERROR);
        }
        if (chunkEnd <= chunkStart || chunkSize != chunkEnd - chunkStart) {
            log.error("the param of 'chunkEnd' or 'chunkStart' or 'chunkSize' for file chunk is invalid! ");
            throw new FileErrorException(OssfileErrorStatus.FILE_CHUNK_PARAM_INVALID);
        }
        buildMd5(multipartFile, fileChunk);

        fileChunk.setChunkTime(new Date());
        if (fileChunk.getChunkIndex() == 1) {
            fileChunk.setStartTime(new Date());
        }
        if (fileChunk.getIsLastChunk() || fileChunk.getChunkIndex().equals(fileIndex.getSliceSize())) {
            fileChunk.setEndTime(new Date());
        }
        return fileIndex;

    }

    public static OssfileBulkModel createFileChunk(OssfileRequest fileRequest, String contentRange) throws RestException {
        OssfileBulkModel fileIndex = fileRequest.toIndex();
        if (GeneralUtils.isEmpty(contentRange)) {
            log.error("the header of 'Content-Range' for request is null! ");
            throw new RestException(OssfileErrorStatus.CONTENT_RANGE_IS_NULL);
        }
        String chunkString = contentRange.trim().replaceAll(OssfileConstants.CONTENT_RANGE_BYTES_HEADER, "");
        String[] splitRange = chunkString.split(OssfileConstants.CONTENT_RANGE_RANGE_REGEX);
        Long chunkStart = PropertyHelper.toLong(splitRange[0]);
        if (chunkStart == null) {
            log.error("the header of 'Content-Range' start value for request is null! ");
            throw new FileErrorException(OssfileErrorStatus.FILE_CHUNK_PARAM_ERROR);
        }
        if (GeneralUtils.isEmpty(splitRange[1])) {
            log.error("the header of 'Content-Range' for request is error! ");
            throw new FileErrorException(OssfileErrorStatus.FILE_CHUNK_PARAM_ERROR);
        }
        String[] splitSize = splitRange[1].split(OssfileConstants.CONTENT_RANGE_SIZE_REGEX);
        Long chunkEnd = PropertyHelper.toLong(splitSize[0]);
        if (chunkEnd == null) {
            log.error("the header of 'Content-Range' end value for request is null! ");
            throw new FileErrorException(OssfileErrorStatus.FILE_CHUNK_PARAM_ERROR);
        }
        Long fileSize = PropertyHelper.toLong(splitSize[1]);
        if (fileSize == null) {
            log.error("the header of 'Content-Range' size value for request is null! ");
            throw new FileErrorException(OssfileErrorStatus.FILE_CHUNK_PARAM_ERROR);
        }
        OssfilePartModel fileChunk = new OssfilePartModel();
        fileChunk.setFileId(fileIndex.getId());
        Long chunkSize = chunkEnd - chunkStart;
        Long sliceSize = fileSize / chunkSize;
        Long chunkIndex = chunkEnd / chunkSize;
        if (fileSize % chunkSize != 0) {
            sliceSize += 1;
        }
        fileIndex.setSliceSize(sliceSize.intValue());
        fileChunk.setChunkIndex(chunkIndex.intValue());
        fileChunk.setChunkSize(chunkSize);
        fileChunk.setChunkStart(chunkStart);
        fileChunk.setChunkEnd(chunkEnd);
        if (chunkEnd.equals(fileSize)) {
            fileChunk.setIsLastChunk(true);
        }
        fileIndex.setFileChunk(fileChunk);
        return fileIndex;
    }

    public static OssfileBulkModel createFileIndex(MultipartFile multipartFile, OssfileBulkModel fileIndex) throws RestException {
        if (GeneralUtils.isEmpty(fileIndex)) {
            fileIndex = new OssfileBulkModel();
        }
        if (GeneralUtils.isEmpty(fileIndex.getId())) {
            String fileId = IdentityUtils.generateString();
            fileIndex.setId(fileId);
        }
        String originalFilename = multipartFile.getOriginalFilename();
        if (GeneralUtils.isNotEmpty(originalFilename)) {
            originalFilename=originalFilename.trim().toLowerCase();
        }
        fileIndex.setName(originalFilename);
        String tempPath = FileUtils.createPath(INSTANCE.commonProperties.getTempPath());
        String cachePath = FileUtils.createPath(tempPath, fileIndex.getId());
        File file = FileUtils.cacheFile(cachePath, multipartFile);
        fileIndex.setFile(file);
        String filename = FileUtils.filename(originalFilename);
        if (GeneralUtils.isEmpty(fileIndex.getFilename())) {
            fileIndex.setFilename(filename);
        }
        if (GeneralUtils.isEmpty(fileIndex.getAlias())) {
            fileIndex.setAlias(String.valueOf(System.currentTimeMillis()));
        }
        String suffix = FileUtils.suffix(originalFilename);
        if (GeneralUtils.isEmpty(fileIndex.getSuffix())) {
            fileIndex.setSuffix(suffix);
        }
        fileIndex.setFileSize(multipartFile.getSize());
        OssfileFileType fileType = parseType(suffix);
        if (GeneralUtils.isEmpty(fileIndex.getIsSlice())) {
            fileIndex.setIsSlice(false);
        }
        fileIndex.setFileType(fileType);
        if (GeneralUtils.isEmpty(fileIndex.getIsCondense())) {
            if (fileType == OssfileFileType.IMAGE) {
                fileIndex.setIsCondense(true);
            } else {
                fileIndex.setIsCondense(false);
            }
        }
        if (GeneralUtils.isEmpty(fileIndex.getIsMd5())) {
            if (fileType == OssfileFileType.IMAGE) {
                fileIndex.setIsMd5(true);
            } else {
                fileIndex.setIsMd5(false);
            }
        }
        if (!fileIndex.getIsSlice()) {
            fileIndex.setSliceSize(0);
        }
        if (fileIndex.getIsMd5()) {
            buildMd5(file, fileIndex);
        }
        fileIndex.setCreateTime(new Date());
        return fileIndex;
    }

    public static MediaType parseContentType(String filename) throws RestException {
        Optional<MediaType> mediaTypeOptional = MediaTypeFactory.getMediaType(filename);
        return mediaTypeOptional.orElse(MediaType.APPLICATION_OCTET_STREAM);
    }


    public static OssfileFileType parseType(String suffix) throws RestException {
        if (GeneralUtils.isEmpty(suffix)) {
            return OssfileFileType.UNKNOWN;
        }
        for (String type : OssfileFileType.IMAGES_DICT) {
            if (suffix.equals(type)) {
                return OssfileFileType.IMAGE;
            }
        }
        for (String type : OssfileFileType.DOCUMENTS_DICT) {
            if (suffix.equals(type)) {
                return OssfileFileType.DOCUMENT;
            }
        }
        for (String type : OssfileFileType.RARS_DICT) {
            if (suffix.equals(type)) {
                return OssfileFileType.RAR;
            }
        }
        // 如果是可执行程序的，如脚本的话，禁传
        for (String type : OssfileFileType.EXES_DICT) {
            if (suffix.equals(type)) {
                // return FileType.EXE;
                throw new FileErrorException(OssfileErrorStatus.FILE_SUFFIX_UNSUPPORTED_ERROR, suffix);
            }
        }
        for (String type : OssfileFileType.VIDEOS_DICT) {
            if (suffix.equals(type)) {
                return OssfileFileType.VIDEO;
            }
        }
        return OssfileFileType.OTHER;
    }

    public static void buildChunks(List<OssfileBulkEntity> entityList, Collection<OssfileBulkModel> modelList) throws RestException {
        if (GeneralUtils.isEmpty(modelList)) {
            return;
        }
        List<String> fileIds = entityList.stream().filter(OssfileBulkEntity::getIsSlice).map(OssfileBulkEntity::getId).distinct().collect(Collectors.toList());
        if (GeneralUtils.isNotEmpty(fileIds)) {
            List<OssfilePartModel> fileChunks = INSTANCE.fileChunkService.queryAllByFileIds(fileIds);
            if (GeneralUtils.isNotEmpty(fileChunks)) {
                Map<String, List<OssfilePartModel>> fileChunkMap = fileChunks.stream().collect(Collectors.groupingBy(OssfilePartModel::getFileId));
                for (OssfileBulkModel fileIndex : modelList) {
                    if (fileIndex.getIsSlice()) {
                        String fileIndexId = fileIndex.getId();
                        List<OssfilePartModel> fileChunkList = fileChunkMap.get(fileIndexId);
                        if (GeneralUtils.isNotEmpty(fileChunkList)) {
                            Collections.sort(fileChunkList);
                            fileIndex.setFileChunks(fileChunkList);
                            OssfilePartModel fileChunk = fileChunkList.get(fileChunkList.size() - 1);
                            fileIndex.setFileChunk(fileChunk);
                            fileIndex.setCurrentIndex(fileChunk.getChunkIndex());
                        }

                    }
                }
            }
        }
    }

}
