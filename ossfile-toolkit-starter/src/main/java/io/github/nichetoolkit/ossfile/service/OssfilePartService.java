package io.github.nichetoolkit.ossfile.service;


import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.rice.service.*;

/**
 * <code>OssfilePartService</code>
 * <p>The ossfile part service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.service.FilterService
 * @see io.github.nichetoolkit.rice.service.SingleService
 * @see io.github.nichetoolkit.rice.service.QueryLinkService
 * @see io.github.nichetoolkit.rice.service.DeleteLinkService
 * @since Jdk1.8
 */
public interface OssfilePartService extends FilterService<OssfilePartModel, OssfileFilter, String, String>, SingleService<OssfilePartModel, String, String>,
        QueryLinkService<OssfilePartModel, String, String>, DeleteLinkService<String, String> {

}
