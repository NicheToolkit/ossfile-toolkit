package io.github.nichetoolkit.ossfile.controller;


import io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel;
import io.github.nichetoolkit.ossfile.domain.model.OssfileFilter;
import io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel;
import io.github.nichetoolkit.ossfile.domain.model.OssfileRequest;
import io.github.nichetoolkit.ossfile.service.OssfileService;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestResult;
import io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog;
import io.github.nichetoolkit.rice.stereotype.RestSkip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <code>StoreController</code>
 * <p>The store controller class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see io.github.nichetoolkit.rice.stereotype.RestSkip
 * @see io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @since Jdk1.8
 */
@Slf4j
@RestSkip
@RestNotelog
@RestController
@RequestMapping("/ossfile/store")
public class StoreController {

    /**
     * <code>ossfileService</code>
     * {@link io.github.nichetoolkit.ossfile.service.OssfileService} <p>The <code>ossfileService</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.service.OssfileService
     */
    private final OssfileService ossfileService;

    /**
     * <code>StoreController</code>
     * <p>Instantiates a new store controller.</p>
     * @param ossfileService {@link io.github.nichetoolkit.ossfile.service.OssfileService} <p>The ossfile service parameter is <code>OssfileService</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.service.OssfileService
     */
    public StoreController(OssfileService ossfileService) {
        this.ossfileService = ossfileService;
    }

    /**
     * <code>uploadOfFile</code>
     * <p>The upload of file method.</p>
     * @param file        {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @param fileRequest {@link io.github.nichetoolkit.ossfile.domain.model.OssfileRequest} <p>The file request parameter is <code>OssfileRequest</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The upload of file return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.springframework.web.multipart.MultipartFile
     * @see org.springframework.web.bind.annotation.RequestPart
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileRequest
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/upload/file")
    public RestResult<OssfileBulkModel> uploadOfFile(@RequestPart("file") MultipartFile file,
                                                     OssfileRequest fileRequest) throws RestException {
        OssfileBulkModel bulkModel = ossfileService.uploadOfFile(file, fileRequest);
        return RestResult.success(bulkModel);
    }

    /**
     * <code>startPart</code>
     * <p>The start part method.</p>
     * @param request {@link io.github.nichetoolkit.ossfile.domain.model.OssfileRequest} <p>The request parameter is <code>OssfileRequest</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The start part return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileRequest
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/part/start")
    public RestResult<OssfileBulkModel> startPart(@RequestBody OssfileRequest request) throws RestException {
        OssfileBulkModel bulkModel = ossfileService.startOfPart(request);
        return RestResult.success(bulkModel);
    }

    /**
     * <code>uploadPart</code>
     * <p>The upload part method.</p>
     * @param bulkId {@link java.lang.String} <p>The bulk id parameter is <code>String</code> type.</p>
     * @param index  int <p>The index parameter is <code>int</code> type.</p>
     * @param size   long <p>The size parameter is <code>long</code> type.</p>
     * @param last   boolean <p>The last parameter is <code>boolean</code> type.</p>
     * @param file   {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The upload part return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.web.bind.annotation.PathVariable
     * @see org.springframework.web.bind.annotation.RequestParam
     * @see org.springframework.web.multipart.MultipartFile
     * @see org.springframework.web.bind.annotation.RequestPart
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/part/upload/{bulkId}")
    public RestResult<OssfilePartModel> uploadPart(@PathVariable(value = "bulkId") String bulkId,
                                                   @RequestParam(value = "index") int index,
                                                   @RequestParam(value = "size", required = false, defaultValue = "0") long size,
                                                   @RequestParam(value = "last", required = false, defaultValue = "false") boolean last,
                                                   @RequestPart("file") MultipartFile file) throws RestException {
        OssfilePartModel partModel = ossfileService.uploadOfPart(file, bulkId, index, size, last);
        return RestResult.success(partModel);
    }

    /**
     * <code>finishPart</code>
     * <p>The finish part method.</p>
     * @param bulkId {@link java.lang.String} <p>The bulk id parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The finish part return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.web.bind.annotation.PathVariable
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.GetMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @GetMapping("/part/finish/{bulkId}")
    public RestResult<?> finishPart(@PathVariable(value = "bulkId") String bulkId) throws RestException {
        ossfileService.finishOfPart(bulkId);
        return RestResult.success();
    }

    /**
     * <code>downloadById</code>
     * <p>The download by id method.</p>
     * @param id       {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @param preview  boolean <p>The preview parameter is <code>boolean</code> type.</p>
     * @param request  {@link javax.servlet.http.HttpServletRequest} <p>The request parameter is <code>HttpServletRequest</code> type.</p>
     * @param response {@link javax.servlet.http.HttpServletResponse} <p>The response parameter is <code>HttpServletResponse</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.web.bind.annotation.PathVariable
     * @see org.springframework.web.bind.annotation.RequestParam
     * @see javax.servlet.http.HttpServletRequest
     * @see javax.servlet.http.HttpServletResponse
     * @see org.springframework.web.bind.annotation.GetMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @GetMapping("/download/{id}")
    public void downloadById(@PathVariable(value = "id") String id,
                             @RequestParam(value = "preview", required = false, defaultValue = "true") boolean preview,
                             HttpServletRequest request, HttpServletResponse response) throws RestException {
        ossfileService.downloadOfId(id, preview, request, response);
    }

    /**
     * <code>downloadOfFilter</code>
     * <p>The download of filter method.</p>
     * @param ossfileFilter {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The ossfile filter parameter is <code>OssfileFilter</code> type.</p>
     * @param response      {@link javax.servlet.http.HttpServletResponse} <p>The response parameter is <code>HttpServletResponse</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileFilter
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see javax.servlet.http.HttpServletResponse
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/download/filter")
    public void downloadOfFilter(@RequestBody OssfileFilter ossfileFilter, HttpServletResponse response) throws RestException {
        ossfileService.downloadOfFilter(ossfileFilter, response);
    }

    /**
     * <code>deleteById</code>
     * <p>The delete by id method.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The delete by id return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.web.bind.annotation.PathVariable
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.DeleteMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @DeleteMapping("/delete/{id}")
    public RestResult<?> deleteById(@PathVariable(value = "id") String id) throws RestException {
        ossfileService.deleteOfId(id);
        return RestResult.success();
    }

    /**
     * <code>deleteOfFilter</code>
     * <p>The delete of filter method.</p>
     * @param fileFilter {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The file filter parameter is <code>OssfileFilter</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The delete of filter return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileFilter
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.DeleteMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @DeleteMapping("/delete/filter")
    public RestResult<?> deleteOfFilter(@RequestBody OssfileFilter fileFilter) throws RestException {
        ossfileService.deleteOfFilter(fileFilter);
        return RestResult.success();
    }

}

