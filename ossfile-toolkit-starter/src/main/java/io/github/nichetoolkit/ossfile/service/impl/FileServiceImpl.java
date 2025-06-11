package io.github.nichetoolkit.ossfile.service.impl;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.ossfile.OssfileConstants;
import io.github.nichetoolkit.ossfile.OssfileFileType;
import io.github.nichetoolkit.ossfile.image.OssfileErrorStatus;
import io.github.nichetoolkit.ossfile.OssfileFilter;
import io.github.nichetoolkit.ossfile.OssfileStoreService;
import io.github.nichetoolkit.ossfile.helper.FileServiceHelper;
import io.github.nichetoolkit.ossfile.OssfileRequest;
import io.github.nichetoolkit.ossfile.service.OssfilePartService;
import io.github.nichetoolkit.ossfile.service.OssfileBulkService;
import io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.util.*;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.RestPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>FileServiceImpl</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    protected OssfileProperties commonProperties;

    @Autowired
    protected OssfileBulkService fileIndexService;

    @Autowired
    protected OssfilePartService fileChunkService;

    @Autowired
    protected FileHandleService fileHandleService;

    @Resource
    protected OssfileStoreService fileStoreService;

    @Resource
    protected OssfileVideoRequestHandler videoHttpRequestHandler;

    @Override
    public void remove(OssfileFilter fileFilter) throws RestException {
        if (GeneralUtils.isEmpty(fileFilter)) {
            return;
        }
        RestPage<? extends RestId<String>> restPage;
        if (fileFilter.isChunk()) {
            restPage = fileChunkService.queryAllWithFilter(fileFilter);
        } else {
            restPage = fileIndexService.queryAllWithFilter(fileFilter);
        }
        List<String> fileIdList = restPage.getItems().stream().map(RestId::getId).distinct().collect(Collectors.toList());
        if (GeneralUtils.isNotEmpty(fileIdList)) {
            fileStoreService.removeAll(fileIdList);
            if (fileFilter.isChunk()) {
                if (fileFilter.isDelete()) {
                    fileChunkService.deleteAll(fileIdList);
                } else {
                    fileChunkService.removeAll(fileIdList);
                }
            } else {
                if (fileFilter.isDelete()) {
                    fileIndexService.deleteAll(fileIdList);
                } else {
                    fileIndexService.removeAll(fileIdList);
                }
            }
        }

    }


    @Override
    public void remove(String fileId, Boolean chunk, Boolean delete, Boolean rename) throws RestException {
        if (GeneralUtils.isEmpty(fileId)) {
            return;
        }
        boolean isExist = false;
        if (chunk) {
            OssfilePartModel fileChunk = fileChunkService.queryById(fileId);
            isExist = GeneralUtils.isNotEmpty(fileChunk);
            if (delete) {
                fileChunkService.deleteById(fileId);
            } else {
                fileChunkService.removeById(fileId);
            }
        } else {
            OssfileBulkModel fileIndex = fileIndexService.queryById(fileId);
            isExist = GeneralUtils.isNotEmpty(fileIndex);
            if (delete) {
                fileIndexService.deleteById(fileId);
            } else {
                fileIndexService.removeById(fileId);
            }
        }
        if (isExist) {
            if (rename) {
                fileStoreService.renameById(fileId, fileId.concat("_del"));
            }
            fileStoreService.removeById(fileId);
        }
    }


    @Override
    public void download(File file, String filename, String contentType, HttpServletRequest request, HttpServletResponse response) throws RestException {
        try (FileInputStream inputStream = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream()) {
            log.info("file size: {}", file.length());
            response.addHeader(OssfileConstants.CONTENT_DISPOSITION_HEADER, OssfileConstants.ATTACHMENT_FILENAME_VALUE + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(contentType);
            StreamUtils.write(outputStream, inputStream);
        } catch (IOException exception) {
            log.error("the file service download has error: {}", exception.getMessage());
            throw new FileErrorException(OssfileErrorStatus.SERVICE_DOWNLOAD_ERROR);
        }
    }

    @Override
    public void download(OssfileBulkModel fileIndex, String filename, String contentType, Boolean preview, OssfileFileType fileType, HttpServletRequest request, HttpServletResponse response) throws RestException {
        response.setContentType(contentType);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (fileType == OssfileFileType.VIDEO && preview) {
            request.setAttribute(OssfileVideoRequestHandler.VIDEO_FILE, fileIndex);
            try {
                videoHttpRequestHandler.handleRequest(request, response);
            } catch (ServletException | IOException exception) {
                log.error("the file service download has error: {}", exception.getMessage());
                throw new FileErrorException(OssfileErrorStatus.SERVICE_DOWNLOAD_ERROR);
            }
        } else {
            try (InputStream inputStream = fileStoreService.getById(fileIndex.getId());
                 ServletOutputStream outputStream = response.getOutputStream()) {
                response.addHeader(OssfileConstants.CONTENT_DISPOSITION_HEADER, OssfileConstants.ATTACHMENT_FILENAME_VALUE + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
                StreamUtils.write(outputStream, inputStream);
            } catch (IOException exception) {
                log.error("the file service download has error: {}", exception.getMessage());
                throw new FileErrorException(OssfileErrorStatus.SERVICE_DOWNLOAD_ERROR);
            }
        }

    }

    @Override
    public void download(OssfileBulkModel fileIndex, String filename, Boolean preview, OssfileFileType fileType, HttpServletRequest request, HttpServletResponse response) throws RestException {
        MediaType mediaType = FileServiceHelper.parseContentType(filename);
        if (!preview) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        download(fileIndex, filename, mediaType.toString(), preview, fileType, request, response);
    }

    @Override
    public void download(OssfileFilter fileFilter, HttpServletRequest request, HttpServletResponse response) throws RestException {
        List<OssfileBulkModel> fileIndices;
        if (fileFilter.isChunk()) {
            RestPage<OssfilePartModel> fileChunkRestPage = fileChunkService.queryAllWithFilter(fileFilter);
            FileServiceHelper.checkRestPage(fileChunkRestPage);
            List<OssfilePartModel> fileChunks = fileChunkRestPage.getItems();
            List<String> fileIndexIds = fileChunks.stream().map(OssfilePartModel::getFileId).distinct().collect(Collectors.toList());
            List<OssfileBulkModel> fileIndexList = fileIndexService.queryAll(fileIndexIds);
            if (GeneralUtils.isNotEmpty(fileIndexList)) {
                Map<String, List<OssfilePartModel>> fileChunkMap = fileChunks.stream().collect(Collectors.groupingBy(OssfilePartModel::getFileId));
                for (OssfileBulkModel fileIndex : fileIndexList) {
                    List<OssfilePartModel> chunkList = fileChunkMap.get(fileIndex.getId());
                    if (GeneralUtils.isNotEmpty(chunkList)) {
                        fileIndex.setFileChunks(chunkList);
                    }
                }
            }
            fileIndices = fileIndexList;
        } else {
            RestPage<OssfileBulkModel> fileIndexRestPage = fileIndexService.queryAllWithFilter(fileFilter);
            FileServiceHelper.checkRestPage(fileIndexRestPage);
            fileIndices = fileIndexRestPage.getItems()
                    .stream().filter(OssfileBulkModel::getIsFinish).collect(Collectors.toList());
        }

        if (GeneralUtils.isEmpty(fileIndices)) {
            log.warn("the file slice upload has not finish !");
            throw new FileErrorException(OssfileErrorStatus.FILE_NO_FINISH_ERROR);
        }
        String tempPath = FileUtils.createPath(commonProperties.getTempPath());
        String randomPath = FileUtils.createPath(tempPath, GeneralUtils.uuid());

        if (fileIndices.size() == 1) {
            OssfileBulkModel fileIndex = fileIndices.get(0);
            if (!fileFilter.isChunk()) {
                String filename = fileIndex.getAlias().concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
                if (fileFilter.isOriginal()) {
                    filename = fileIndex.getFilename().concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
                }
                download(fileIndex, filename, true, fileIndex.getFileType(), request, response);
                return;
            }
        }
        List<File> fileList = new ArrayList<>();
        if (fileFilter.isChunk()) {
            FileServiceHelper.buildChunkFiles(fileIndices, fileFilter, randomPath, fileList);
        } else {
            FileServiceHelper.buildIndexFiles(fileIndices, fileFilter, randomPath, fileList);
        }
        String filename = DateUtils.format(DateUtils.today(), "yyyyMMdd_HHmmss");
        File zipFile = ZipUtils.zipFiles(randomPath, filename, fileList);
        MediaType mediaType = FileServiceHelper.parseContentType(filename);
        download(zipFile, zipFile.getName(), mediaType.toString(), request, response);
        FileUtils.clear(randomPath);
    }

    @Override
    public void fileDownload(String fileId, Boolean chunk, Boolean preview, Boolean original, HttpServletRequest request, HttpServletResponse response) throws RestException {
        OssfileBulkModel fileIndex;
        if (chunk) {
            OssfilePartModel fileChunk = fileChunkService.queryById(fileId);
            if (GeneralUtils.isEmpty(fileChunk)) {
                log.warn("the file service query result is empty!");
                throw new FileErrorException(OssfileErrorStatus.FILE_NO_FOUND_ERROR);
            }
            String fileIndexId = fileChunk.getFileId();
            fileIndex = fileIndexService.queryById(fileIndexId);
            fileIndex.setFileChunk(fileChunk);
        } else {
            fileIndex = fileIndexService.queryById(fileId);
        }
        if (GeneralUtils.isEmpty(fileIndex)) {
            log.warn("the file service query result is empty!");
            throw new FileErrorException(OssfileErrorStatus.FILE_NO_FOUND_ERROR);
        }
        if (!fileIndex.getIsFinish()) {
            log.warn("the file slice upload has not finish !");
            throw new FileErrorException(OssfileErrorStatus.FILE_NO_FINISH_ERROR);
        }
        String filename = fileIndex.getAlias().concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
        if (chunk) {
            OssfilePartModel fileChunk = fileIndex.getFileChunk();
            if (original) {
                filename = fileIndex.getFilename().concat("_").concat(String.valueOf(fileChunk.getChunkIndex())).concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
            } else {
                filename = fileIndex.getAlias().concat("_").concat(String.valueOf(fileChunk.getChunkIndex())).concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
            }
        } else if (original) {
            filename = fileIndex.getFilename().concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
        }
        download(fileIndex, filename, preview, fileIndex.getFileType(), request, response);
    }

    @Override
    public void imageDownload(String fileId, Boolean preview, Boolean original, HttpServletRequest request, HttpServletResponse response) throws RestException {
        String filename = fileId.concat(OssfileConstants.SUFFIX_REGEX).concat(OssfileConstants.IMAGE_JPG_SUFFIX);
        OssfileBulkModel fileIndex = null;
        if (original) {
            fileIndex = fileIndexService.queryById(fileId);
            if (GeneralUtils.isEmpty(fileIndex)) {
                log.warn("the image service query result is empty!");
                throw new RestException(OssfileErrorStatus.FILE_NO_FOUND_ERROR);
            }
            filename = fileIndex.getFilename().concat(OssfileConstants.SUFFIX_REGEX).concat(fileIndex.getSuffix());
        }
        if (GeneralUtils.isEmpty(fileIndex)) {
            fileIndex = new OssfileBulkModel(fileId);
        }
        download(fileIndex, filename, preview, OssfileFileType.IMAGE, request, response);
    }

    @Override
    public OssfileBulkModel upload(MultipartFile file, OssfileRequest fileRequest) throws RestException {
        OssfileBulkModel createIndex = FileServiceHelper.createFileIndex(file, fileRequest.toIndex());
        return upload(createIndex);
    }

    @Override
    public OssfileBulkModel upload(OssfileBulkModel fileIndex) throws RestException {
        if (GeneralUtils.isEmpty(fileIndex)) {
            throw new RestException(OssfileErrorStatus.FILE_INDEX_IS_NULL);
        }
        if (fileIndex.getIsAutograph() != null && fileIndex.getIsAutograph() && fileIndex.getFileType() == OssfileFileType.IMAGE) {
            fileHandleService.autographImage(fileIndex);
        }
        if (fileIndex.getIsCondense()) {
            if (fileIndex.getFileType() == OssfileFileType.IMAGE) {
                fileHandleService.condenseImage(fileIndex);
            } else {
                fileHandleService.condenseFile(fileIndex);
            }
        }
        String fileId = fileIndex.getId();
        fileStoreService.putById(fileId, fileIndex.inputStream());
        checkFileIndex(fileIndex);
        fileIndexService.save(fileIndex);
        return fileIndex;
    }

    @Override
    public OssfileBulkModel indexUpload(OssfileBulkModel fileIndex) throws RestException {
        String name = fileIndex.getName();
        OssfileBulkModel uploadInterrupt = fileIndexService.queryByNameWithUploadInterrupt(name);
        if (GeneralUtils.isNotEmpty(uploadInterrupt)) {
            return uploadInterrupt;
        } else {
            checkFileIndex(fileIndex);
            return fileIndexService.save(fileIndex);
        }
    }

    @Override
    public OssfileBulkModel chunkUpload(MultipartFile file, String contentRange, OssfileRequest fileRequest) throws RestException {
        OssfileBulkModel fileChunkIndex = FileServiceHelper.createFileChunk(fileRequest, contentRange);
        OssfileBulkModel fileIndex = FileServiceHelper.createFileChunk(file, fileChunkIndex);
        checkFileIndex(fileIndex);
        OssfilePartModel uploadChunk = fileChunkService.save(fileIndex.getFileChunk());
        fileStoreService.putById(uploadChunk.getId(), uploadChunk.inputStream());
        fileIndex.setFileChunk(uploadChunk);
        if (GeneralUtils.isEmpty(fileIndex.getFileChunks())) {
            fileIndex.setFileChunks(new ArrayList<>());
        }
        List<OssfilePartModel> fileChunks = fileIndex.getFileChunks();
        fileChunks.add(uploadChunk);
        fileIndex.setCurrentIndex(uploadChunk.getChunkIndex());
        if ((uploadChunk.getIsLastChunk() || uploadChunk.getChunkIndex().equals(fileIndex.getSliceSize())) && fileIndex.getIsMerge()) {
            fileIndex.setIsFinish(true);
            List<String> sources = fileIndex.getFileChunks().stream().map(RestId::getId).collect(Collectors.toList());
            fileStoreService.margeById(fileIndex.getId(), sources);
            checkFileIndex(fileIndex);
            fileIndexService.save(fileIndex);
        }
        return fileIndex;
    }

    private void checkFileIndex(OssfileBulkModel fileIndex) {
        if (GeneralUtils.isEmpty(fileIndex.getIsCondense())) {
            fileIndex.setIsCondense(false);
        }
        if (GeneralUtils.isEmpty(fileIndex.getIsFinish())) {
            fileIndex.setIsFinish(true);
        }
        if (GeneralUtils.isEmpty(fileIndex.getIsSlice())) {
            fileIndex.setIsSlice(false);
        }
        if (GeneralUtils.isEmpty(fileIndex.getSliceSize())) {
            fileIndex.setSliceSize(0);
        }
        if (GeneralUtils.isEmpty(fileIndex.getIsMerge())) {
            fileIndex.setIsMerge(true);
        }
    }


}
