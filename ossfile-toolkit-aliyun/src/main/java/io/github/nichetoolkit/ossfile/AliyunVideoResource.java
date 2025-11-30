package io.github.nichetoolkit.ossfile;

import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.SimplifiedObjectMeta;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;

import java.io.IOException;

/**
 * <code>AliyunVideoResource</code>
 * <p>The aliyun video resource class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfileVideoResource
 * @since Jdk17
 */
public class AliyunVideoResource extends OssfileVideoResource {
    /**
     * <code>statObject</code>
     * {@link com.aliyun.oss.model.SimplifiedObjectMeta} <p>The <code>statObject</code> field.</p>
     * @see com.aliyun.oss.model.SimplifiedObjectMeta
     */
    private final SimplifiedObjectMeta statObject;

    /**
     * <code>AliyunVideoResource</code>
     * <p>Instantiates a new aliyun video resource.</p>
     * @param ossObject {@link com.aliyun.oss.model.OSSObject} <p>The oss object parameter is <code>OSSObject</code> type.</p>
     * @param resource  {@link io.github.nichetoolkit.ossfile.OssfileResource} <p>The resource parameter is <code>OssfileResource</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @see com.aliyun.oss.model.OSSObject
     * @see io.github.nichetoolkit.ossfile.OssfileResource
     * @see java.io.IOException
     */
    public AliyunVideoResource(OSSObject ossObject, OssfileResource resource) throws IOException {
        super(ossObject.getObjectContent(), resource);
        try {
            this.statObject = AliyunHelper.statObject(resource.getObjectKey());
        } catch (FileErrorException exception) {
            throw new IOException(exception.getMessage(), exception);
        }
    }

    @Override
    public long contentLength() throws IOException {
        return statObject.getSize();
    }

    @Override
    public long lastModified() throws IOException {
        return statObject.getLastModified().getTime();
    }

}
