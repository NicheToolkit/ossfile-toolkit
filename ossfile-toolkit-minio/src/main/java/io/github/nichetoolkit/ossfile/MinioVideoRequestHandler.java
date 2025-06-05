package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.minio.GetObjectResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MinioVideoRequestHandler extends OssfileVideoRequestHandler {

    @Override
    public OssfileVideoResource ossfileResource(OssfileBulkModel<?, ?> ossfileBulkModel) throws IOException {
        try {
            GetObjectResponse getObjectResponse = MinioHelper.getObject(ossfileBulkModel.getObjectKey());
            return new MinioVideoResource(getObjectResponse, ossfileBulkModel);
        } catch (FileErrorException exception) {
            throw new IOException(exception.getMessage(), exception);
        }
    }
}
