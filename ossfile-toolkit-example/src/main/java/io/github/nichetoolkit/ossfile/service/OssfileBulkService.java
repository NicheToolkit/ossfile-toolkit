package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel;
import io.github.nichetoolkit.ossfile.domain.model.OssfileFilter;
import io.github.nichetoolkit.rice.service.*;

/**
 * <code>OssfileBulkService</code>
 * <p>The ossfile bulk service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.service.FilterService
 * @see io.github.nichetoolkit.rice.service.SingleService
 * @see io.github.nichetoolkit.rice.service.QueryLinkService
 * @see io.github.nichetoolkit.rice.service.AlertLinkService
 * @see io.github.nichetoolkit.rice.service.DeleteLinkService
 * @since Jdk1.8
 */
public interface OssfileBulkService extends FilterService<OssfileBulkModel, OssfileFilter, String,String>, SingleService<OssfileBulkModel, String, String>, QueryLinkService<OssfileBulkModel,String,String>,
        AlertLinkService<String, String>, DeleteLinkService<String, String> {

}
