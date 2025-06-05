package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import io.minio.credentials.AssumeRoleProvider;
import io.minio.credentials.Credentials;
import io.minio.messages.InitiateMultipartUploadResult;
import io.minio.messages.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MinioStoreService extends OssfileStoreService {

    public MinioStoreService(OssfileProperties properties) {
        super(properties);
    }

    @Override
    public OssfileCredentials credentials() throws RestException {
        try {
            AssumeRoleProvider provider = new AssumeRoleProvider(properties.getEndpoint(), properties.getAccessKey(),
                    properties.getSecretKey(), Math.toIntExact(properties.getExpire()),
                    null, properties.getRegion(), null, null, null, null);
            Credentials credential = provider.fetch();
            return new OssfileCredentials(credential.accessKey(), credential.secretKey(), credential.sessionToken(), properties.getExpire());
        } catch (NoSuchAlgorithmException exception) {
            log.error("the minio server provided credentials url has error, error: {}", exception.getMessage());
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_CREDENTIALS_ERROR, exception.getMessage());
        }
    }

    @Override
    public void createClient() throws RestException {
        MinioContextHolder.refreshClient();
    }

    @Override
    public OssfileProviderType providerType() throws RestException {
        return OssfileProviderType.MINIO;
    }

    @Override
    public URL getOssfileUrl(String objectKey) throws RestException {
        return MinioHelper.objectUrl(objectKey, Math.toIntExact(properties.getExpire()));
    }

    @Override
    public URL getOssfileUrl(String bucket, String objectKey) throws RestException {
        return MinioHelper.objectUrl(bucket, objectKey, Math.toIntExact(properties.getExpire()));
    }

    @Override
    public InputStream getOssfile(String objectKey) throws RestException {
        return MinioHelper.getObject(objectKey);
    }

    @Override
    public InputStream getOssfile(String bucket, String objectKey) throws RestException {
        return MinioHelper.getObject(bucket, objectKey);
    }

    @Override
    public void putOssfile(String objectKey, InputStream inputStream) throws RestException {
        MinioHelper.putObject(objectKey, inputStream);
    }

    @Override
    public void putOssfile(String bucket, String objectKey, InputStream inputStream) throws RestException {
        MinioHelper.putObject(bucket, objectKey, inputStream);
    }

    @Override
    public String startMultipart(String objectKey) throws RestException {
        InitiateMultipartUploadResult result = MinioHelper.initiateMultipart(objectKey);
        return Optional.ofNullable(result).map(InitiateMultipartUploadResult::uploadId).orElse(null);
    }

    @Override
    public String startMultipart(String bucket, String objectKey) throws RestException {
        InitiateMultipartUploadResult result = MinioHelper.initiateMultipart(bucket, objectKey, null, null);
        return Optional.ofNullable(result).map(InitiateMultipartUploadResult::uploadId).orElse(null);
    }

    @Override
    public void uploadMultipart(String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException {
        MinioHelper.uploadMultipart(objectKey, uploadId, partIndex, inputStream, partSize);
    }

    @Override
    public void uploadMultipart(String bucket, String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException {
        MinioHelper.uploadMultipart(bucket, objectKey, uploadId, partIndex, inputStream, partSize, null, null);
    }

    @Override
    public void finishMultipart(String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException {
        List<Part> partETagList = partETags.stream().map(partETag -> new Part(partETag.getPartIndex(), partETag.getPartEtag())).collect(Collectors.toList());
        MinioHelper.completeMultipart(objectKey, uploadId, partETagList);
    }

    @Override
    public void finishMultipart(String bucket, String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException {
        List<Part> partETagList = partETags.stream().map(partETag -> new Part(partETag.getPartIndex(), partETag.getPartEtag())).collect(Collectors.toList());
        MinioHelper.completeMultipart(bucket, objectKey, uploadId, partETagList, null, null);
    }

    @Override
    public void deleteOssfile(String objectKey) throws RestException {
        MinioHelper.removeObject(objectKey);
    }

    @Override
    public void deleteOssfile(String bucket, String objectKey) throws RestException {
        MinioHelper.removeObject(bucket, objectKey);
    }

    @Override
    public void deleteOssfile(Collection<String> objectKeyList) throws RestException {
        MinioHelper.removeObjects(objectKeyList);
    }

    @Override
    public void deleteOssfile(String bucket, Collection<String> objectKeyList) throws RestException {
        MinioHelper.removeObjects(bucket, objectKeyList);
    }
}
