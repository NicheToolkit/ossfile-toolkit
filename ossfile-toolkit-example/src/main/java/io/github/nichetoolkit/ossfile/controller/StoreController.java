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

    @Autowired
    private OssfileService ossfileService;

    @PostMapping("/upload/file")
    public RestResult<OssfileBulkModel> uploadOfFile(@NonNull @RequestPart("file") MultipartFile file,
                                                       OssfileRequest fileRequest) throws RestException {
        OssfileBulkModel bulkModel = ossfileService.uploadOfFile(file, fileRequest);
        return RestResult.success(bulkModel);
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

