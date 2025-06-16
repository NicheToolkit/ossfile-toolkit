package io.github.nichetoolkit.ossfile;

import com.amazonaws.services.s3.model.S3Object;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <code>AmazonVideoRequestHandler</code>
 * <p>The amazon video request handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Component
public class AmazonVideoRequestHandler extends OssfileVideoRequestHandler {

    @Override
    public OssfileVideoResource videoResource(OssfileResource resource) throws IOException {
        try {
            S3Object ossObject = AmazonHelper.getObject(resource.getBucket(),resource.getObjectKey());
            return new AmazonVideoResource(ossObject, resource);
        } catch (FileErrorException exception) {
            throw new IOException(exception.getMessage(), exception);
        }
    }
}
