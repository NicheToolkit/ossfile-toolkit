package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.ossfile.helper.FileServiceHelper;
import io.github.nichetoolkit.rest.RestEntry;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.error.lack.InstanceLackError;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.holder.ApplicationContextHolder;
import io.github.nichetoolkit.rest.stream.RestStream;
import io.github.nichetoolkit.rest.util.*;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.RestPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * <p>FileServiceImpl</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Service
public class OssfileService {

    protected final OssfileHandleService handleService;

    public OssfileService(OssfileHandleService handleService) {
        this.handleService = handleService;
    }

    @Async
    public void deleteOfId(String id) throws RestException {
        OssfileBulkModel ossfileBulkModel = OssfileServiceHolder.bulkService().queryById(id);
        if (GeneralUtils.isNotEmpty(ossfileBulkModel)) {
            String bucket = ossfileBulkModel.getBucket();
            String objectKey = ossfileBulkModel.getObjectKey();
            OssfileServiceHolder.storeService().deleteOssfile(bucket, objectKey);
            OssfileServiceHolder.bulkService().deleteById(id);
            if (GeneralUtils.isNotEmpty(ossfileBulkModel.getPartState())) {
                OssfileServiceHolder.partService().queryByLinkId(id, RestKey.of("bulkId"));
            }
        }
    }

    @Async
    public void deleteOfFilter(OssfileFilter fileFilter) throws RestException {
        fileFilter.setPageSize(0);
        RestPage<OssfileBulkModel> restPage = OssfileServiceHolder.bulkService().queryAllWithFilter(fileFilter);
        if (GeneralUtils.isNotEmpty(restPage) && GeneralUtils.isNotEmpty(restPage.getItems())) {
            deleteOfBulks(restPage.getItems());
        }
    }

    @Async
    public void deleteOfAll(Collection<String> idList) throws RestException {
        List<OssfileBulkModel> bulkModels = OssfileServiceHolder.bulkService().queryAll(idList);
        RestOptional.ofEmptyable(bulkModels).ifEmptyPresent(this::deleteOfBulks);
    }

    private void deleteOfBulks(Collection<OssfileBulkModel> bulkModels) throws RestException {
        Map<String, List<OssfileResource>> resourceListMap = bulkModels.stream().collect(Collectors.groupingBy(OssfileResource::getBucket));
        RestOptional.ofEmptyable(resourceListMap).ifEmptyPresent(resourceMap -> RestStream.stream(resourceMap.entrySet()).forEach(resourceEntry -> {
            String bucket = resourceEntry.getKey();
            List<String> objectKeys = resourceEntry.getValue().stream().map(OssfileResource::getObjectKey).distinct().collect(Collectors.toList());
            OssfileServiceHolder.storeService().deleteOssfile(bucket, objectKeys);
        }));
        List<String> idList = bulkModels.stream().map(RestId::getId).distinct().collect(Collectors.toList());
        RestOptional.ofEmptyable(idList).ifEmptyPresent(ids -> OssfileServiceHolder.bulkService().deleteAll(ids));
        List<String> bulkIdList = bulkModels.stream().filter(OssfileBulkModel::getPartState).map(RestId::getId).distinct().collect(Collectors.toList());
        RestOptional.ofEmptyable(bulkIdList).ifEmptyPresent(bulkIds -> OssfileServiceHolder.partService().deleteAllByLinkIds(bulkIds, RestKey.of("bulkId")));
    }

    @Async
    public void downloadOfBulk(OssfileBulkModel bulkModel, boolean preview, HttpServletRequest request, HttpServletResponse response) throws RestException {
        String filename = bulkModel.getFilename();
        Boolean finishState = bulkModel.getFinishState();
        OptionalUtils.ofFalseThrow(finishState, () -> new FileErrorException(OssfileErrorStatus.OSSFILE_FINISHED_ERROR));
        Optional<MediaType> mediaTypeOptional = MediaTypeFactory.getMediaType(filename);
        String contentType = mediaTypeOptional.orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        response.setContentType(contentType);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (bulkModel.getFileType() == OssfileFileType.VIDEO && preview) {
            request.setAttribute(OssfileConstants.OSS_VIDEO_RESOURCE, bulkModel);
            try {
                OssfileServiceHolder.videoHandler().handleRequest(request, response);
            } catch (ServletException | IOException exception) {
                log.error("the file download for video handle with bulk has error, filename: {}, error: {}", filename, exception.getMessage());
                throw new FileErrorException(OssfileErrorStatus.OSSFILE_DOWNLOAD_ERROR, exception.getMessage());
            }
        } else {
            String bucket = bulkModel.getBucket();
            String ossfileKey = preview && bulkModel.getPreviewState() ? bulkModel.getPreviewKey() : bulkModel.getObjectKey();
            try (InputStream inputStream = OssfileServiceHolder.storeService().getOssfile(bucket, ossfileKey);
                 ServletOutputStream outputStream = response.getOutputStream()) {
                response.addHeader(OssfileConstants.CONTENT_DISPOSITION_HEADER, OssfileConstants.ATTACHMENT_FILENAME_VALUE + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
                IoStreamUtils.write(outputStream, inputStream);
            } catch (IOException exception) {
                log.error("the file download with bulk has error, filename: {}, error: {}", filename, exception.getMessage());
                throw new FileErrorException(OssfileErrorStatus.OSSFILE_DOWNLOAD_ERROR, exception.getMessage());
            }
        }
    }
    @Async
    public void downloadOfFilter(OssfileFilter fileFilter, HttpServletResponse response) throws RestException {
        RestPage<? extends OssfileBulkModel> restPage = OssfileServiceHolder.bulkService().queryAllWithFilter(fileFilter);
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(restPage) && GeneralUtils.isNotEmpty(restPage.getItems()),
                () -> new FileErrorException(OssfileErrorStatus.OSSFILE_NO_FOUND_ERROR));
        Collection<? extends OssfileBulkModel> pageItems = restPage.getItems();
        List<? extends OssfileBulkModel> bulkModels = pageItems.stream().filter(OssfileBulkModel::getFinishState).collect(Collectors.toList());
        OptionalUtils.ofEmpty(bulkModels, () -> new FileErrorException(OssfileErrorStatus.OSSFILE_FINISHED_ERROR));
        List<Map.Entry<String, InputStream>> ossfileEntryList = new ArrayList<>();
        for (OssfileBulkModel resource : bulkModels) {
            String bucket = resource.getBucket();
            String objectKey = resource.getObjectKey();
            InputStream ossfile = OssfileServiceHolder.storeService().getOssfile(bucket, objectKey);
            ossfileEntryList.add(RestEntry.of(resource.getFilename(), ossfile));
        }
        long timeMillis = System.currentTimeMillis();
        String timeDay = DateUtils.format(DateUtils.today(), "yyyyMMdd-HHmmss");
        String name = timeDay + timeMillis;
        Path tempZipFile = FileUtils.createTempFile(name, ".zip");
        String filename = tempZipFile.getFileName().toString();
        ZipUtils.zipsOfEntry(tempZipFile, filename, ossfileEntryList);
        downloadOfFile(tempZipFile, filename, response);
        FileUtils.clear(tempZipFile);
    }

    @Async
    public void downloadOfFile(Path filePath, String filename, HttpServletResponse response) throws RestException {
        try (FileInputStream inputStream = new FileInputStream(filePath.toFile());
             ServletOutputStream outputStream = response.getOutputStream()) {
            response.addHeader(OssfileConstants.CONTENT_DISPOSITION_HEADER, OssfileConstants.ATTACHMENT_FILENAME_VALUE + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            Optional<MediaType> mediaTypeOptional = MediaTypeFactory.getMediaType(filename);
            String contentType = mediaTypeOptional.orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
            response.setContentType(contentType);
            IoStreamUtils.write(outputStream, inputStream);
        } catch (IOException exception) {
            log.error("the file download with file path has error, filename: {}, error: {}", filename, exception.getMessage());
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_DOWNLOAD_ERROR, exception.getMessage());
        }
    }

    @Async
    public void downloadOfId(String fileId, HttpServletRequest request, HttpServletResponse response) throws RestException {
        OssfileBulkModel bulkModel = OssfileServiceHolder.bulkService().queryById(fileId);
        OptionalUtils.ofEmpty(bulkModel, () -> new FileErrorException(OssfileErrorStatus.OSSFILE_NO_FOUND_ERROR));
        OptionalUtils.ofFalse(bulkModel.getFinishState(), () -> new FileErrorException(OssfileErrorStatus.OSSFILE_FINISHED_ERROR));
        downloadOfBulk(bulkModel, true, request, response);

    }

    public OssfileBulkModel upload(MultipartFile file, OssfileRequest fileRequest) throws RestException {
        OssfileBulkModel createIndex = FileServiceHelper.createFileIndex(file, fileRequest.toIndex());
        return upload(createIndex);
    }

    public void uploadOfBulk(OssfileBulkModel bulkModel, OssfileRequest request) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(bulkModel) && GeneralUtils.isNotEmpty(bulkModel.inputStream()),
                () -> new FileErrorException(OssfileErrorStatus.OSSFILE_UPLOAD_ERROR));
        OssfileFileType fileType = bulkModel.getFileType();
        
        if (fileType == OssfileFileType.IMAGE) {
            if (request.isSignature()) {
                handleService.extractOfSignatureHandle(bulkModel);
            } else if (request.isCompress()) {
                Future<OssfileBulkModel> futureOfCompress = handleService.compressOfImageHandle(bulkModel, request.getWidth(), request.getHeight());
                if (request.isPreview()) {
                    
                }
                return storeOfFuture(futureOfCompress);
            }
        }
        
        if (request.isCompress()) {
            Future<OssfileBulkModel> futureOfCompress;
            if (fileType == OssfileFileType.IMAGE) {
                futureOfCompress = handleService.compressOfImageHandle(bulkModel, request.getWidth(), request.getHeight());
            } else {
                futureOfCompress = handleService.compressOfFileHandle(bulkModel);
            }
            return storeOfFuture(futureOfCompress);
        }

    }

    public OssfileBulkModel storeOfFuture(Future<OssfileBulkModel> bulkModelFuture) throws RestException {
        try {
            OssfileBulkModel ossfileBulkModel = bulkModelFuture.get();
            String bucket = ossfileBulkModel.getBucket();
            String objectKey = ossfileBulkModel.getObjectKey();
            OssfileServiceHolder.storeService().putOssfile(bucket, objectKey, ossfileBulkModel.inputStream());
            OssfileServiceHolder.bulkService().save(ossfileBulkModel);
            return ossfileBulkModel;
        } catch (InterruptedException | ExecutionException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_UPLOAD_ERROR, exception.getMessage());
        }
    }

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

    public OssfileBulkModel chunkUpload(MultipartFile file, String contentRange, OssfileRequest fileRequest) throws RestException {
        OssfileBulkModel fileChunkIndex = FileServiceHelper.createFileChunk(fileRequest, contentRange);
        OssfileBulkModel fileIndex = FileServiceHelper.createFileChunk(file, fileChunkIndex);
        checkFileIndex(fileIndex);
        OssfilePartModel uploadChunk = fileChunkService.save(fileIndex.getFileChunk());
        fileOssfileServiceHolder.storeService().putById(uploadChunk.getId(), uploadChunk.inputStream());
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
            fileOssfileServiceHolder.storeService().margeById(fileIndex.getId(), sources);
            checkFileIndex(fileIndex);
            fileIndexService.save(fileIndex);
        }
        return fileIndex;
    }

}
