package io.github.nichetoolkit.ossfile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;

import java.io.IOException;

/**
 * <code>AmazonVideoResource</code>
 * <p>The amazon video resource class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfileVideoResource
 * @since Jdk1.8
 */
public class AmazonVideoResource extends OssfileVideoResource {
    /**
     * <code>statObject</code>
     * {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The <code>statObject</code> field.</p>
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     */
    private final ObjectMetadata statObject;

    /**
     * <code>AmazonVideoResource</code>
     * <p>Instantiates a new amazon video resource.</p>
     * @param ossObject {@link com.amazonaws.services.s3.model.S3Object} <p>The oss object parameter is <code>S3Object</code> type.</p>
     * @param resource  {@link io.github.nichetoolkit.ossfile.OssfileResource} <p>The resource parameter is <code>OssfileResource</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @see com.amazonaws.services.s3.model.S3Object
     * @see io.github.nichetoolkit.ossfile.OssfileResource
     * @see java.io.IOException
     */
    public AmazonVideoResource(S3Object ossObject, OssfileResource resource) throws IOException {
        super(ossObject.getObjectContent(), resource);
        try {
            this.statObject = AmazonHelper.statObject(resource.getObjectKey());
        } catch (FileErrorException exception) {
            throw new IOException(exception.getMessage(), exception);
        }
    }

    @Override
    public long contentLength() throws IOException {
        return statObject.getContentLength();
    }

    @Override
    public long lastModified() throws IOException {
        return statObject.getLastModified().getTime();
    }

}
