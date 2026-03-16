package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.RestException;
import io.minio.StatObjectResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * <code>MinioVideoResource</code>
 * <p>The minio video resource class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfileVideoResource
 * @since Jdk1.8
 */
public class MinioVideoResource extends OssfileVideoResource {
    /**
     * <code>statObject</code>
     * {@link io.minio.StatObjectResponse} <p>The <code>statObject</code> field.</p>
     * @see io.minio.StatObjectResponse
     */
    private final StatObjectResponse statObject;

    /**
     * <code>MinioVideoResource</code>
     * <p>Instantiates a new minio video resource.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param resource    {@link io.github.nichetoolkit.ossfile.OssfileResource} <p>The resource parameter is <code>OssfileResource</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @see java.io.InputStream
     * @see io.github.nichetoolkit.ossfile.OssfileResource
     * @see java.io.IOException
     */
    public MinioVideoResource(InputStream inputStream, OssfileResource resource) throws IOException {
        super(inputStream, resource);
        try {
            this.statObject = MinioHelper.statObject(resource.getObjectKey());
        } catch (RestException exception) {
            throw new IOException(exception.getMessage(), exception);
        }
    }

    @Override
    public long contentLength() throws IOException {
        return statObject.size();
    }

    @Override
    public long lastModified() throws IOException {
        return statObject.lastModified().toEpochSecond();
    }

}
