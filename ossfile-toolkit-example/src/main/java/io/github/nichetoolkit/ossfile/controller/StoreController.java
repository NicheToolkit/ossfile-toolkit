package io.github.nichetoolkit.ossfile.controller;


import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.ossfile.service.OssfileService;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestResult;
import io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog;
import io.github.nichetoolkit.rice.stereotype.RestSkip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestSkip
@RestNotelog
@RestController
@RequestMapping("/ossfile/store")
public class StoreController {

    private final OssfileService ossfileService;

    public StoreController(OssfileService ossfileService) {
        this.ossfileService = ossfileService;
    }

    @PostMapping("/upload/file")
    public RestResult<OssfileBulkModel> uploadOfFile(@RequestPart("file") MultipartFile file,
                                                       OssfileRequest fileRequest) throws RestException {
        OssfileBulkModel bulkModel = ossfileService.uploadOfFile(file, fileRequest);
        return RestResult.success(bulkModel);
    }

    @PostMapping("/part/start")
    public RestResult<OssfileBulkModel> startPart(@RequestBody OssfileRequest request) throws RestException {
        OssfileBulkModel bulkModel = ossfileService.startOfPart(request);
        return RestResult.success(bulkModel);
    }

    @PostMapping("/part/upload/{bulkId}")
    public RestResult<OssfilePartModel> uploadPart(@PathVariable(value = "bulkId") String bulkId,
                                                   @RequestParam(value = "index") int index,
                                                   @RequestParam(value = "size", required = false, defaultValue = "0") long size,
                                                   @RequestParam(value = "last", required = false, defaultValue = "false") boolean last,
                                                   @RequestPart("file") MultipartFile file) throws RestException {
        OssfilePartModel partModel = ossfileService.uploadOfPart(file, bulkId, index, size, last);
        return RestResult.success(partModel);
    }

    @PostMapping("/part/finish/{bulkId}")
    public RestResult<?> finishPart(@PathVariable(value = "bulkId") String bulkId) throws RestException {
        ossfileService.finishOfPart(bulkId);
        return RestResult.success();
    }

    @GetMapping("/download/{id}")
    public void downloadById(@PathVariable(value = "id") String id,
                             @RequestParam(value = "preview", required = false, defaultValue = "true") boolean preview,
                             HttpServletRequest request, HttpServletResponse response) throws RestException {
        ossfileService.downloadOfId(id, preview, request, response);
    }

    @PostMapping("/download/filter")
    public void downloadOfFilter(@RequestBody OssfileFilter ossfileFilter, HttpServletResponse response) throws RestException {
        ossfileService.downloadOfFilter(ossfileFilter, response);
    }

    @DeleteMapping("/delete/{id}")
    public RestResult<?> deleteById(@PathVariable(value = "id") String id) throws RestException {
        ossfileService.deleteOfId(id);
        return RestResult.success();
    }

    @DeleteMapping("/delete/filter")
    public RestResult<?> deleteOfFilter(@RequestBody OssfileFilter fileFilter) throws RestException {
        ossfileService.deleteOfFilter(fileFilter);
        return RestResult.success();
    }

}

