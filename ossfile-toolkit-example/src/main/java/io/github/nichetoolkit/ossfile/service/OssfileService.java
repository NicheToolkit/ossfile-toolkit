package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.ossfile.domain.OssfileFileType;
import io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel;
import io.github.nichetoolkit.ossfile.domain.model.OssfileFilter;
import io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel;
import io.github.nichetoolkit.ossfile.domain.model.OssfileRequest;
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

@Slf4j
@Service
public class OssfileService {

    protected final OssfileHandleService handleService;

    public OssfileService(OssfileHandleService handleService) {
        this.handleService = handleService;
    }

    public void deleteOfId(String id) throws RestException {
        OssfileBulkModel ossfileBulkModel = OssfileServiceHolder.bulkService().queryById(id);
        if (GeneralUtils.isNotEmpty(ossfileBulkModel)) {
            String bucket = ossfileBulkModel.getBucket();
            String objectKey = ossfileBulkModel.getObjectKey();
            OssfileServiceHolder.storeService().deleteOssfile(bucket, objectKey);
            OssfileServiceHolder.bulkService().deleteById(id);
            if (ossfileBulkModel.getPartState()) {
                OssfileServiceHolder.partService().deleteByLinkId(id, RestKey.of("bulkId"));
            }
        }
    }

    public void deleteOfFilter(OssfileFilter fileFilter) throws RestException {
        fileFilter.setPageSize(0);
        RestPage<OssfileBulkModel> restPage = OssfileServiceHolder.bulkService().queryAllWithFilter(fileFilter);
        if (GeneralUtils.isNotEmpty(restPage) && GeneralUtils.isNotEmpty(restPage.getItems())) {
            deleteOfBulks(restPage.getItems());
        }
    }

    public void deleteOfAll(Collection<String> idList) throws RestException {
        List<OssfileBulkModel> bulkModels = OssfileServiceHolder.bulkService().queryAll(idList);
        RestOptional.ofEmptyable(bulkModels).isNotEmpty(this::deleteOfBulks);
    }

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

    public void downloadOfBulk(OssfileBulkModel bulkModel, boolean preview, HttpServletRequest request, HttpServletResponse response) throws RestException {
        String filename = bulkModel.getFilename();
        Boolean finishState = bulkModel.getFinishState();
        OptionalUtils.ofFalseThrow(finishState, () -> new FileErrorException(OssfileErrorStatus.OSSFILE_FINISHED_ERROR));
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (preview) {
            Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(filename);
            contentType = mediaType.map(MediaType::toString).orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        }
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
        String timeDay = DateUtils.format(DateUtils.today(), OssfileConstants.FILE_DATE_PATTERN);
        String name = timeDay + timeMillis;
        Path tempZipFile = FileUtils.createTempFile(name, OssfileConstants.FILE_ZIP_SUFFIX);
        String filename = tempZipFile.getFileName().toString();
        ZipUtils.zipsOfEntry(tempZipFile, filename, ossfileEntryList);
        downloadOfFile(tempZipFile, filename, response);
        FileUtils.clear(tempZipFile);
    }

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

    public void downloadOfId(String fileId, boolean preview, HttpServletRequest request, HttpServletResponse response) throws RestException {
        OssfileBulkModel bulkModel = OssfileServiceHolder.bulkService().queryById(fileId);
        OptionalUtils.ofEmpty(bulkModel, () -> new FileErrorException(OssfileErrorStatus.OSSFILE_NO_FOUND_ERROR));
        OptionalUtils.ofFalse(bulkModel.getFinishState(), () -> new FileErrorException(OssfileErrorStatus.OSSFILE_FINISHED_ERROR));
        downloadOfBulk(bulkModel, preview, request, response);
    }

    public OssfileBulkModel startOfPart(OssfileRequest request) throws RestException {
        long partSize = request.getPartSize();
        OptionalUtils.ofFalse(partSize >= OssfileConstants.MIN_PART_SIZE && partSize <= OssfileConstants.MAX_PART_SIZE,
                () -> new FileErrorException(OssfileErrorStatus.OSSFILE_CONFIG_ERROR,"The param of part size is invalid, it is must be in 5MB-5GB range."));
        OssfileBulkModel bulkModel = request.toBulkModel();
        bulkModel.ofStartPartBuilder();
        handleService.handleOfPartStart(bulkModel);
        return bulkModel;
    }

    public OssfilePartModel uploadOfPart(MultipartFile file, String bulkId, int partIndex, long partSize, boolean last) throws RestException {
        OptionalUtils.ofEmpty(bulkId, () -> new FileErrorException(OssfileErrorStatus.OSSFILE_CONFIG_ERROR,"The param of bulk Id is empty."));
        OssfileBulkModel bulkModel = OssfileServiceHolder.bulkService().queryById(bulkId);
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(bulkModel) && GeneralUtils.isNotEmpty(bulkModel.getUploadId()), () -> new FileErrorException(OssfileErrorStatus.OSSFILE_NO_FOUND_ERROR,"The data of file part has been lost."));
        OssfilePartModel partModel = bulkModel.toPartModel(partIndex, partSize).ofFile(file);
        handleService.handleOfPartUpload(bulkModel,partModel,last);
        return partModel;
    }

    public void finishOfPart(String bulkId) throws RestException {
        OptionalUtils.ofEmpty(bulkId, () -> new FileErrorException(OssfileErrorStatus.OSSFILE_CONFIG_ERROR,"The param of bulk Id is empty."));
        OssfileBulkModel bulkModel = OssfileServiceHolder.bulkService().queryById(bulkId);
        OptionalUtils.ofFalse(GeneralUtils.isNotEmpty(bulkModel) && GeneralUtils.isNotEmpty(bulkModel.getUploadId()), () -> new FileErrorException(OssfileErrorStatus.OSSFILE_NO_FOUND_ERROR,"The data of file part has been lost."));
        List<OssfilePartModel> partModels = OssfileServiceHolder.partService().queryByLinkId(bulkModel.toPartLinks());
        OptionalUtils.ofEmpty(partModels, () -> new FileErrorException(OssfileErrorStatus.OSSFILE_NO_FOUND_ERROR,"The data of file part has been lost."));
        bulkModel.setParts(partModels);
        handleService.handleOfPartFinish(bulkModel);
    }

    public OssfileBulkModel uploadOfFile(MultipartFile file, OssfileRequest request) throws RestException {
        OssfileBulkModel bulkModel = request.toBulkModel().ofFile(file);
        return uploadOfBulk(bulkModel, request.getWidth(), request.getHeight());
    }

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
