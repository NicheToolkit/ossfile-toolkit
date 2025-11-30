package io.github.nichetoolkit.ossfile.controller;

import io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel;
import io.github.nichetoolkit.ossfile.domain.model.OssfileFilter;
import io.github.nichetoolkit.ossfile.service.OssfileBulkService;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestResult;
import io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog;
import io.github.nichetoolkit.rice.RestPage;
import io.github.nichetoolkit.rice.stereotype.RestSkip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <code>BulkController</code>
 * <p>The bulk controller class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see io.github.nichetoolkit.rice.stereotype.RestSkip
 * @see io.github.nichetoolkit.rest.userlog.stereotype.RestNotelog
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @since Jdk17
 */
@Slf4j
@RestSkip
@RestNotelog
@RestController
@RequestMapping("/ossfile/bulk")
public class BulkController {

    /**
     * <code>bulkService</code>
     * {@link io.github.nichetoolkit.ossfile.service.OssfileBulkService} <p>The <code>bulkService</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.service.OssfileBulkService
     */
    private final OssfileBulkService bulkService;

    /**
     * <code>BulkController</code>
     * <p>Instantiates a new bulk controller.</p>
     * @param bulkService {@link io.github.nichetoolkit.ossfile.service.OssfileBulkService} <p>The bulk service parameter is <code>OssfileBulkService</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.service.OssfileBulkService
     */
    public BulkController(OssfileBulkService bulkService) {
        this.bulkService = bulkService;
    }

    /**
     * <code>queryById</code>
     * <p>The query by id method.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The query by id return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.web.bind.annotation.PathVariable
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.GetMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @GetMapping("/query/{id}")
    public RestResult<OssfileBulkModel> queryById(@PathVariable String id) throws RestException {
        OssfileBulkModel bulkModel = bulkService.queryById(id);
        return RestResult.success(bulkModel);
    }

    /**
     * <code>query</code>
     * <p>The query method.</p>
     * @param filter {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The filter parameter is <code>OssfileFilter</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.RestResult} <p>The query return object is <code>RestResult</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileFilter
     * @see org.springframework.web.bind.annotation.RequestBody
     * @see io.github.nichetoolkit.rest.RestResult
     * @see org.springframework.web.bind.annotation.PostMapping
     * @see io.github.nichetoolkit.rest.RestException
     */
    @PostMapping("/query/filter")
    public RestResult<RestPage<OssfileBulkModel> > query(@RequestBody OssfileFilter filter) throws RestException {
        RestPage<OssfileBulkModel> restPage = bulkService.queryAllWithFilter(filter);
        return RestResult.success(restPage);
    }
}
