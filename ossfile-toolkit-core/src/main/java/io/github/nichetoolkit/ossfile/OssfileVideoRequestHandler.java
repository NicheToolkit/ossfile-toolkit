package io.github.nichetoolkit.ossfile;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <code>OssfileVideoRequestHandler</code>
 * <p>The ossfile video request handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.web.servlet.resource.ResourceHttpRequestHandler
 * @since Jdk1.8
 */
public abstract class OssfileVideoRequestHandler extends ResourceHttpRequestHandler {

    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        final OssfileBulkModel<?,?> ossfileBulkModel = (OssfileBulkModel<?,?>) request.getAttribute(OssfileConstants.OSS_VIDEO_RESOURCE);
        return ossfileResource(ossfileBulkModel);
    }

    /**
     * <code>ossfileResource</code>
     * <p>The ossfile resource method.</p>
     * @param ossfileBulkModel {@link io.github.nichetoolkit.ossfile.OssfileBulkModel} <p>The ossfile bulk model parameter is <code>OssfileBulkModel</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileVideoResource} <p>The ossfile resource return object is <code>OssfileVideoResource</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileBulkModel
     * @see io.github.nichetoolkit.ossfile.OssfileVideoResource
     * @see java.io.IOException
     */
    abstract public OssfileVideoResource ossfileResource(OssfileBulkModel<?,?> ossfileBulkModel) throws IOException;
}
