package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.minio.GetObjectResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <code>MinioVideoRequestHandler</code>
 * <p>The minio video request handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Component
public class MinioVideoRequestHandler extends OssfileVideoRequestHandler {

    @Override
    public OssfileVideoResource videoResource(OssfileResource resource) throws IOException {
        try {
            GetObjectResponse getObjectResponse = MinioHelper.getObject(resource.getBucket(), resource.getObjectKey());
            return new MinioVideoResource(getObjectResponse, resource);
        } catch (FileErrorException exception) {
            throw new IOException(exception.getMessage(), exception);
        }
    }
}
