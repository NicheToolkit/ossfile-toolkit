package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.rest.RestEntry;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.stream.RestStream;
import io.github.nichetoolkit.rest.util.*;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.RestPage;
import lombok.extern.slf4j.Slf4j;
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
 * <code>OssfileService</code>
 * <p>The ossfile service class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Service
 * @since Jdk1.8
 */
@Slf4j
@Service
public class OssfileService {

    /**
     * <code>handleService</code>
     * {@link io.github.nichetoolkit.ossfile.service.OssfileHandleService} <p>The <code>handleService</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.service.OssfileHandleService
     */
    protected final OssfileHandleService handleService;

    /**
     * <code>OssfileService</code>
     * <p>Instantiates a new ossfile service.</p>
     * @param handleService {@link io.github.nichetoolkit.ossfile.service.OssfileHandleService} <p>The handle service parameter is <code>OssfileHandleService</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.service.OssfileHandleService
     */
    public OssfileService(OssfileHandleService handleService) {
        this.handleService = handleService;
    }

    /**
     * <code>deleteOfId</code>
     * <p>The delete of id method.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
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

    /**
     * <code>deleteOfFilter</code>
     * <p>The delete of filter method.</p>
     * @param fileFilter {@link io.github.nichetoolkit.ossfile.OssfileFilter} <p>The file filter parameter is <code>OssfileFilter</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileFilter
     * @see io.github.nichetoolkit.rest.RestException
     */
    public void deleteOfFilter(OssfileFilter fileFilter) throws RestException {
        fileFilter.setPageSize(0);
        RestPage<OssfileBulkModel> restPage = OssfileServiceHolder.bulkService().queryAllWithFilter(fileFilter);
        if (GeneralUtils.isNotEmpty(restPage) && GeneralUtils.isNotEmpty(restPage.getItems())) {
            deleteOfBulks(restPage.getItems());
        }
    }

    /**
     * <code>deleteOfAll</code>
     * <p>The delete of all method.</p>
     * @param idList {@link java.util.Collection} <p>The id list parameter is <code>Collection</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    public void deleteOfAll(Collection<String> idList) throws RestException {
        List<OssfileBulkModel> bulkModels = OssfileServiceHolder.bulkService().queryAll(idList);
        RestOptional.ofEmptyable(bulkModels).isNotEmpty(this::deleteOfBulks);
    }

    /**
     * <code>deleteOfBulks</code>
     * <p>The delete of bulks method.</p>
     * @param bulkModels {@link java.util.Collection} <p>The bulk models parameter is <code>Collection</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    private void deleteOfBulks(Collection<OssfileBulkModel> bulkModels) throws RestException {
        Map<String, List<OssfileResource>> resourceListMap = bulkModels.stream().collect(Collectors.groupingBy(OssfileResource::getBucket));
        RestOptional.ofEmptyable(resourceListMap).isNotEmpty(resourceMap -> RestStream.stream(resourceMap.entrySet()).forEach(resourceEntry -> {
            String bucket = resourceEntry.getKey();
            List<String> objectKeys = resourceEntry.getValue().stream().map(OssfileResource::getObjectKey).distinct().collect(Collectors.toList());
            OssfileServiceHolder.storeService().deleteOssfile(bucket, objectKeys);
        }));
        List<String> idList = bulkModels.stream().map(RestId::getId).distinct().collect(Collectors.toList());
        RestOptional.ofEmptyable(idList).isNotEmpty(ids -> OssfileServiceHolder.bulkService().deleteAll(ids));
        List<String> bulkIdList = bulkModels.stream().filter(OssfileBulkModel::getPartState).map(RestId::getId).distinct().collect(Collectors.toList());
        RestOptional.ofEmptyable(bulkIdList).isNotEmpty(bulkIds -> OssfileServiceHolder.partService().deleteAllByLinkIds(bulkIds, RestKey.of("bulkId")));
    }

    /**
     * <code>downloadOfBulk</code>
     * <p>The download of bulk method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @param preview   boolean <p>The preview parameter is <code>boolean</code> type.</p>
     * @param request   {@link javax.servlet.http.HttpServletRequest} <p>The request parameter is <code>HttpServletRequest</code> type.</p>
     * @param response  {@link javax.servlet.http.HttpServletResponse} <p>The response parameter is <code>HttpServletResponse</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileBulkModel
     * @see javax.servlet.http.HttpServletRequest
     * @see javax.servlet.http.HttpServletResponse
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
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
            OssfileStoreService storeService = OssfileServiceHolder.storeService();
            Future<InputStream> inputStreamFuture = storeService.getOssfile(bucket, ossfileKey);
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                InputStream inputStream = inputStreamFuture.get();
                response.addHeader(OssfileConstants.CONTENT_DISPOSITION_HEADER, OssfileConstants.ATTACHMENT_FILENAME_VALUE + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
                IoStreamUtils.write(outputStream, inputStream);
            } catch (InterruptedException | ExecutionException | IOException exception) {
                log.error("the file download with bulk has error, filename: {}, error: {}", filename, exception.getMessage());
                throw new FileErrorException(OssfileErrorStatus.OSSFILE_DOWNLOAD_ERROR, exception.getMessage());
            }
        }
    }

    /**
     * <code>downloadOfFilter</code>
     * <p>The download of filter method.</p>
     * @param fileFilter {@link io.github.nichetoolkit.ossfile.OssfileFilter} <p>The file filter parameter is <code>OssfileFilter</code> type.</p>
     * @param response   {@link javax.servlet.http.HttpServletResponse} <p>The response parameter is <code>HttpServletResponse</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileFilter
     * @see javax.servlet.http.HttpServletResponse
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
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
            OssfileStoreService storeService = OssfileServiceHolder.storeService();
            Future<InputStream> inputStreamFuture = storeService.getOssfile(bucket, objectKey);
            try {
                InputStream inputStream = inputStreamFuture.get();
                ossfileEntryList.add(RestEntry.of(resource.getFilename(), inputStream));
            } catch (InterruptedException | ExecutionException exception) {
                log.error("the file download with bulk has error, error: {}", exception.getMessage());
                throw new FileErrorException(OssfileErrorStatus.OSSFILE_DOWNLOAD_ERROR, exception.getMessage());
            }
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

    /**
     * <code>downloadOfFile</code>
     * <p>The download of file method.</p>
     * @param filePath {@link java.nio.file.Path} <p>The file path parameter is <code>Path</code> type.</p>
     * @param filename {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @param response {@link javax.servlet.http.HttpServletResponse} <p>The response parameter is <code>HttpServletResponse</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.nio.file.Path
     * @see java.lang.String
     * @see javax.servlet.http.HttpServletResponse
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
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

    /**
     * <code>downloadOfId</code>
     * <p>The download of id method.</p>
     * @param fileId   {@link java.lang.String} <p>The file id parameter is <code>String</code> type.</p>
     * @param request  {@link javax.servlet.http.HttpServletRequest} <p>The request parameter is <code>HttpServletRequest</code> type.</p>
     * @param response {@link javax.servlet.http.HttpServletResponse} <p>The response parameter is <code>HttpServletResponse</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see javax.servlet.http.HttpServletRequest
     * @see javax.servlet.http.HttpServletResponse
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    public void downloadOfId(String fileId, HttpServletRequest request, HttpServletResponse response) throws RestException {
        OssfileBulkModel bulkModel = OssfileServiceHolder.bulkService().queryById(fileId);
        OptionalUtils.ofEmpty(bulkModel, () -> new FileErrorException(OssfileErrorStatus.OSSFILE_NO_FOUND_ERROR));
        OptionalUtils.ofFalse(bulkModel.getFinishState(), () -> new FileErrorException(OssfileErrorStatus.OSSFILE_FINISHED_ERROR));
        downloadOfBulk(bulkModel, true, request, response);
    }

    /**
     * <code>uploadOfFile</code>
     * <p>The upload of file method.</p>
     * @param file    {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @param request {@link io.github.nichetoolkit.ossfile.OssfileRequest} <p>The request parameter is <code>OssfileRequest</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileBulkModel} <p>The upload of file return object is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.springframework.web.multipart.MultipartFile
     * @see io.github.nichetoolkit.ossfile.OssfileRequest
     * @see io.github.nichetoolkit.ossfile.OssfileBulkModel
     * @see io.github.nichetoolkit.rest.RestException
     */
    public OssfileBulkModel uploadOfFile(MultipartFile file, OssfileRequest request) throws RestException {
        OssfileBulkModel bulkModel = request.toBulkModel().ofFile(file);
        return uploadOfBulk(bulkModel, request.getWidth(), request.getHeight());
    }

    /**
     * <code>uploadOfBulk</code>
     * <p>The upload of bulk method.</p>
     * @param bulkModel {@link io.github.nichetoolkit.ossfile.OssfileBulkModel} <p>The bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @param width     int <p>The width parameter is <code>int</code> type.</p>
     * @param height    int <p>The height parameter is <code>int</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileBulkModel} <p>The upload of bulk return object is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileBulkModel
     * @see io.github.nichetoolkit.rest.RestException
     */
    public OssfileBulkModel uploadOfBulk(OssfileBulkModel bulkModel, int width, int height) throws RestException {
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(bulkModel) && GeneralUtils.isNotEmpty(bulkModel.inputStream()),
                () -> new FileErrorException(OssfileErrorStatus.OSSFILE_UPLOAD_ERROR));
        RestKey<String> fileType = bulkModel.getFileType();
        if (OssfileFileType.IMAGE.present(fileType)) {
            if (bulkModel.getSignatureState()) {
                if (bulkModel.getPreviewState()) {
                    bulkModel.ofObjectCopyBuilder();
                } else {
                    bulkModel.ofDefaultBuilder();
                }
                handleService.handleOfSignature(bulkModel);
            } else if (bulkModel.getCompressState()) {
                if (bulkModel.getPreviewState()) {
                    bulkModel.ofObjectCopyBuilder();
                    handleService.handleOfImagePreviewAndCompress(bulkModel, width, height);
                } else {
                    bulkModel.ofDefaultBuilder();
                    handleService.handleOfImageCompress(bulkModel, width, height);
                }
            } else {
                bulkModel.ofDefaultBuilder();
                if (bulkModel.getPreviewState()) {
                    handleService.handleOfFile(bulkModel);
                    bulkModel.ofPreviewBuilder();
                    handleService.handleOfImagePreview(bulkModel, width, height);
                } else {
                    handleService.handleOfFile(bulkModel);
                }
            }
        } else {
            bulkModel.ofDefaultBuilder();
            if (bulkModel.getCompressState()) {
                handleService.handleOfFileCompress(bulkModel);
            } else {
                handleService.handleOfFile(bulkModel);
            }
        }
        return bulkModel;
    }
}
