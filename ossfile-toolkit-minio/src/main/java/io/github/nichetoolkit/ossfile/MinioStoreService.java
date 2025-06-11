package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import io.minio.ObjectWriteResponse;
import io.minio.UploadPartResponse;
import io.minio.credentials.AssumeRoleProvider;
import io.minio.credentials.Credentials;
import io.minio.messages.InitiateMultipartUploadResult;
import io.minio.messages.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.Future;
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
    public OssfileProviderType providerType() {
        return OssfileProviderType.MINIO;
    }

    @Override
    public Future<URL> getOssfileUrl(String objectKey) throws RestException {
        return AsyncResult.forValue(MinioHelper.objectUrl(objectKey, Math.toIntExact(properties.getExpire())));
    }

    @Override
    public Future<URL> getOssfileUrl(String bucket, String objectKey) throws RestException {
        return AsyncResult.forValue(MinioHelper.objectUrl(bucket, objectKey, Math.toIntExact(properties.getExpire())));
    }

    @Override
    public Future<InputStream> getOssfile(String objectKey) throws RestException {
        return AsyncResult.forValue(MinioHelper.getObject(objectKey));
    }

    @Override
    public Future<InputStream> getOssfile(String bucket, String objectKey) throws RestException {
        return AsyncResult.forValue(MinioHelper.getObject(bucket, objectKey));
    }

    @Override
    public Future<OssfileETagVersion> putOssfile(String objectKey, InputStream inputStream) throws RestException {
        ObjectWriteResponse writeResponse = MinioHelper.putObject(objectKey, inputStream);
        return AsyncResult.forValue(new OssfileETagVersion(writeResponse.etag(),writeResponse.versionId()));
    }

    @Override
    public Future<OssfileETagVersion> putOssfile(String bucket, String objectKey, InputStream inputStream) throws RestException {
        ObjectWriteResponse writeResponse = MinioHelper.putObject(bucket, objectKey, inputStream);
        return AsyncResult.forValue(new OssfileETagVersion(writeResponse.etag(),writeResponse.versionId()));
    }

    @Override
    public Future<String> startMultipart(String objectKey) throws RestException {
        InitiateMultipartUploadResult result = MinioHelper.initiateMultipart(objectKey);
        return AsyncResult.forValue(result.uploadId());
    }

    @Override
    public Future<String> startMultipart(String bucket, String objectKey) throws RestException {
        InitiateMultipartUploadResult result = MinioHelper.initiateMultipart(bucket, objectKey, null, null);
        return AsyncResult.forValue(result.uploadId());
    }

    @Override
    public Future<OssfilePartETag> uploadMultipart(String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException {
        UploadPartResponse uploadPartResponse = MinioHelper.uploadMultipart(objectKey, uploadId, partIndex, inputStream, partSize);
        return AsyncResult.forValue(new OssfilePartETag(uploadPartResponse.partNumber(),uploadPartResponse.etag()));
    }

    @Override
    public Future<OssfilePartETag> uploadMultipart(String bucket, String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException {
        UploadPartResponse uploadPartResponse = MinioHelper.uploadMultipart(bucket, objectKey, uploadId, partIndex, inputStream, partSize, null, null);
        return AsyncResult.forValue(new OssfilePartETag(uploadPartResponse.partNumber(),uploadPartResponse.etag()));
    }

    @Override
    public Future<OssfileETagVersion> finishMultipart(String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException {
        List<Part> partETagList = partETags.stream().map(partETag -> new Part(partETag.getPartIndex(), partETag.getPartEtag())).collect(Collectors.toList());
        ObjectWriteResponse writeResponse = MinioHelper.completeMultipart(objectKey, uploadId, partETagList);
        return AsyncResult.forValue(new OssfileETagVersion(writeResponse.etag(),writeResponse.versionId()));
    }

    @Override
    public Future<OssfileETagVersion> finishMultipart(String bucket, String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException {
        List<Part> partETagList = partETags.stream().map(partETag -> new Part(partETag.getPartIndex(), partETag.getPartEtag())).collect(Collectors.toList());
        ObjectWriteResponse writeResponse = MinioHelper.completeMultipart(bucket, objectKey, uploadId, partETagList, null, null);
        return AsyncResult.forValue(new OssfileETagVersion(writeResponse.etag(),writeResponse.versionId()));
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
