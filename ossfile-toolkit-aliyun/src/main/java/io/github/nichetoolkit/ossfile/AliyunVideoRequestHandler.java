package io.github.nichetoolkit.ossfile;

import com.aliyun.oss.model.OSSObject;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.minio.GetObjectResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <code>AliyunVideoRequestHandler</code>
 * <p>The aliyun video request handler class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Component
public class AliyunVideoRequestHandler extends OssfileVideoRequestHandler {

    @Override
    public OssfileVideoResource ossfileResource(OssfileBulkModel<?, ?> ossfileBulkModel) throws IOException {
        try {
            OSSObject ossObject = AliyunHelper.getObject(ossfileBulkModel.getObjectKey());
            return new AliyunVideoResource(ossObject, ossfileBulkModel);
        } catch (FileErrorException exception) {
            throw new IOException(exception.getMessage(), exception);
        }
    }
}
