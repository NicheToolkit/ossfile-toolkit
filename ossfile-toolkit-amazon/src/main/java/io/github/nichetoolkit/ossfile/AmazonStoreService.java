package io.github.nichetoolkit.ossfile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AmazonStoreService extends OssfileStoreService {

    public AmazonStoreService(OssfileProperties properties) {
        super(properties);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        List<CORSRule.AllowedMethods> allowedMethods = new ArrayList<>();
        allowedMethods.add(CORSRule.AllowedMethods.GET);
        allowedMethods.add(CORSRule.AllowedMethods.PUT);
        allowedMethods.add(CORSRule.AllowedMethods.POST);
        allowedMethods.add(CORSRule.AllowedMethods.DELETE);
        allowedMethods.add(CORSRule.AllowedMethods.HEAD);
        OssfileProperties.OssAllowed ossAllowed = properties.getAllowed();
        Set<String> origins = ossAllowed.getOrigins();
        origins.add("*");
        Set<String> headers = ossAllowed.getHeaders();
        CORSRule rule = new CORSRule()
                .withId("CORSAccessRule")
                .withAllowedOrigins(new ArrayList<>(origins))
                .withAllowedHeaders(new ArrayList<>(headers))
                .withAllowedMethods(allowedMethods);
        AmazonContextHolder.defaultClient().setBucketCrossOriginConfiguration(properties.getBucket(),
                new BucketCrossOriginConfiguration().withRules(rule));
    }

    @Override
    public OssfileCredentials credentials() throws RestException {
        AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey())))
                .withRegion(properties.getRegion()).build();

        AssumeRoleRequest request = new AssumeRoleRequest()
                .withRoleArn(properties.getRoleArn())
                .withRoleSessionName(properties.getRoleName())
                .withDurationSeconds(Math.toIntExact(properties.getExpire()));
        AssumeRoleResult result = stsClient.assumeRole(request);
        Credentials credentials = result.getCredentials();
        return new OssfileCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey(),
                credentials.getSessionToken(), (credentials.getExpiration().getTime() - System.currentTimeMillis()) / 1000);
    }

    @Override
    public void createClient() throws RestException {
        AmazonContextHolder.refreshClient();
    }

    @Override
    public OssfileProviderType providerType() {
        return OssfileProviderType.AMAZON;
    }

    @Override
    public Future<URL> getOssfileUrl(String objectKey) throws RestException {
        return AsyncResult.forValue(AmazonHelper.objectUrl(objectKey, Math.toIntExact(properties.getExpire())));
    }

    @Override
    public Future<URL> getOssfileUrl(String bucket, String objectKey) throws RestException {
        return AsyncResult.forValue(AmazonHelper.objectUrl(bucket, objectKey, Math.toIntExact(properties.getExpire())));
    }

    @Override
    public Future<InputStream> getOssfile(String objectKey) throws RestException {
        S3Object ossObject = AmazonHelper.getObject(objectKey);
        return AsyncResult.forValue(ossObject.getObjectContent());
    }

    @Override
    public Future<InputStream> getOssfile(String bucket, String objectKey) throws RestException {
        S3Object ossObject = AmazonHelper.getObject(bucket, objectKey);
        return AsyncResult.forValue(ossObject.getObjectContent());
    }

    @Async
    @Override
    public Future<OssfileETagVersion> putOssfile(String objectKey, InputStream inputStream) throws RestException {
        PutObjectResult putObjectResult = AmazonHelper.putObject(objectKey, inputStream);
        return AsyncResult.forValue(new OssfileETagVersion(putObjectResult.getETag(),putObjectResult.getVersionId()));
    }

    @Async
    @Override
    public Future<OssfileETagVersion> putOssfile(String bucket, String objectKey, InputStream inputStream) throws RestException {
        PutObjectResult putObjectResult = AmazonHelper.putObject(bucket, objectKey, inputStream);
        return AsyncResult.forValue(new OssfileETagVersion(putObjectResult.getETag(),putObjectResult.getVersionId()));
    }

    @Override
    public Future<String> startMultipart(String objectKey) throws RestException {
        InitiateMultipartUploadResult result = AmazonHelper.initiateMultipart(objectKey, null);
        return AsyncResult.forValue(result.getUploadId());
    }

    @Override
    public Future<String> startMultipart(String bucket, String objectKey) throws RestException {
        InitiateMultipartUploadResult result = AmazonHelper.initiateMultipart(bucket, objectKey, null);
        return AsyncResult.forValue(result.getUploadId());
    }

    @Async
    @Override
    public Future<OssfilePartETag> uploadMultipart(String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException {
        UploadPartResult uploadPartResult = AmazonHelper.uploadMultipart(objectKey, uploadId, partIndex, inputStream, partSize);
        return AsyncResult.forValue(new OssfilePartETag(uploadPartResult.getPartNumber(),uploadPartResult.getETag()));
    }

    @Async
    @Override
    public Future<OssfilePartETag> uploadMultipart(String bucket, String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException {
        UploadPartResult uploadPartResult = AmazonHelper.uploadMultipart(bucket, objectKey, uploadId, partIndex, inputStream, partSize);
        return AsyncResult.forValue(new OssfilePartETag(uploadPartResult.getPartNumber(),uploadPartResult.getETag()));
    }

    @Async
    @Override
    public Future<OssfileETagVersion> finishMultipart(String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException {
        List<PartETag> partETagList = partETags.stream().map(partETag -> new PartETag(partETag.getPartIndex(), partETag.getPartEtag())).collect(Collectors.toList());
        CompleteMultipartUploadResult uploadResult = AmazonHelper.completeMultipart(objectKey, uploadId, partETagList);
        return AsyncResult.forValue(new OssfileETagVersion(uploadResult.getETag(),uploadResult.getVersionId()));
    }

    @Async
    @Override
    public Future<OssfileETagVersion> finishMultipart(String bucket, String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException {
        List<PartETag> partETagList = partETags.stream().map(partETag -> new PartETag(partETag.getPartIndex(), partETag.getPartEtag())).collect(Collectors.toList());
        CompleteMultipartUploadResult uploadResult = AmazonHelper.completeMultipart(bucket, objectKey, uploadId, partETagList);
        return AsyncResult.forValue(new OssfileETagVersion(uploadResult.getETag(),uploadResult.getVersionId()));
    }

    @Async
    @Override
    public void deleteOssfile(String objectKey) throws RestException {
        AmazonHelper.deleteObject(objectKey);
    }

    @Async
    @Override
    public void deleteOssfile(String bucket, String objectKey) throws RestException {
        AmazonHelper.deleteObject(bucket, objectKey);
    }

    @Async
    @Override
    public void deleteOssfile(Collection<String> objectKeyList) throws RestException {
        AmazonHelper.deleteObjects(objectKeyList);
    }

    @Async
    @Override
    public void deleteOssfile(String bucket, Collection<String> objectKeyList) throws RestException {
        AmazonHelper.deleteObjects(bucket, objectKeyList);
    }

}
