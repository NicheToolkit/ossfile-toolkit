package io.github.nichetoolkit.ossfile.controller;

import io.github.nichetoolkit.ossfile.OssfileFilter;
import io.github.nichetoolkit.ossfile.service.FileChunkService;
import io.github.nichetoolkit.ossfile.service.FileIndexService;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>FileIndexController</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class FileIndexController {

    @Autowired
    private FileIndexService fileIndexService;

    @Autowired
    private FileChunkService fileChunkService;

    @PostMapping("/query/{id}")
    public ResponseEntity queryById(@PathVariable String id,
                                    @RequestParam(value = "chunk", required = false, defaultValue = "false") Boolean chunk) throws RestException {
        if (chunk) {
            return RestResult.ok(fileChunkService.queryById(id));
        } else {
            return RestResult.ok(fileIndexService.queryById(id));
        }
    }

    @PostMapping("/query/filter")
    public ResponseEntity query(@RequestBody OssfileFilter filter) throws RestException {
        if (filter.isChunk()) {
            return RestResult.ok(fileChunkService.queryAllWithFilter(filter));
        } else {
            return RestResult.ok(fileIndexService.queryAllWithFilter(filter));
        }

    }
}
