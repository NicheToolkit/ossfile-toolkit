package io.github.nichetoolkit.ossfile.service;


import io.github.nichetoolkit.ossfile.domain.model.OssfileFilter;
import io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel;
import io.github.nichetoolkit.rice.service.DeleteLinkService;
import io.github.nichetoolkit.rice.service.FilterService;
import io.github.nichetoolkit.rice.service.QueryLinkService;
import io.github.nichetoolkit.rice.service.SingleService;

/**
 * <code>OssfilePartService</code>
 * <p>The ossfile part service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see FilterService
 * @see SingleService
 * @see QueryLinkService
 * @see DeleteLinkService
 * @since Jdk1.8
 */
public interface OssfilePartService extends FilterService<OssfilePartModel, OssfileFilter, String, String>, SingleService<OssfilePartModel, String, String>,
        QueryLinkService<OssfilePartModel, String, String>, DeleteLinkService<String, String> {

}
