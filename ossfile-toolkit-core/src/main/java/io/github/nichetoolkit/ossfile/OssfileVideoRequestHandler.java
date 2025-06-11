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
        final OssfileResource resource = (OssfileResource) request.getAttribute(OssfileConstants.OSS_VIDEO_RESOURCE);
        return videoResource(resource);
    }

    /**
     * <code>videoResource</code>
     * <p>The video resource method.</p>
     * @param resource {@link io.github.nichetoolkit.ossfile.OssfileResource} <p>The resource parameter is <code>OssfileResource</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileVideoResource} <p>The video resource return object is <code>OssfileVideoResource</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileResource
     * @see io.github.nichetoolkit.ossfile.OssfileVideoResource
     * @see java.io.IOException
     */
    abstract public OssfileVideoResource videoResource(OssfileResource resource) throws IOException;
}
