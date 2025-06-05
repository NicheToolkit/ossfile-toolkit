package io.github.nichetoolkit.ossfile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class AmazonStoreService extends OssfileStoreService {

    public AmazonStoreService(OssfileProperties properties) {
        super(properties);
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
    public OssfileProviderType providerType() throws RestException {
        return OssfileProviderType.ALIYUN;
    }

    @Override
    public URL getOssfileUrl(String objectKey) throws RestException {
        return AmazonHelper.objectUrl(objectKey, Math.toIntExact(properties.getExpire()));
    }

    @Override
    public URL getOssfileUrl(String bucket, String objectKey) throws RestException {
        return AmazonHelper.objectUrl(bucket, objectKey, Math.toIntExact(properties.getExpire()));
    }

    @Override
    public InputStream getOssfile(String objectKey) throws RestException {
        S3Object ossObject = AmazonHelper.getObject(objectKey);
        return Optional.ofNullable(ossObject).map(S3Object::getObjectContent).orElse(null);
    }

    @Override
    public InputStream getOssfile(String bucket, String objectKey) throws RestException {
        S3Object ossObject = AmazonHelper.getObject(bucket, objectKey);
        return Optional.ofNullable(ossObject).map(S3Object::getObjectContent).orElse(null);
    }

    @Override
    public void putOssfile(String objectKey, InputStream inputStream) throws RestException {
        AmazonHelper.putObject(objectKey, inputStream);
    }

    @Override
    public void putOssfile(String bucket, String objectKey, InputStream inputStream) throws RestException {
        AmazonHelper.putObject(bucket, objectKey, inputStream);
    }

    @Override
    public void deleteOssfile(String objectKey) throws RestException {
        AmazonHelper.deleteObject(objectKey);
    }

    @Override
    public void deleteOssfile(String bucket, String objectKey) throws RestException {
        AmazonHelper.deleteObject(bucket, objectKey);
    }

    @Override
    public void deleteOssfile(Collection<String> objectKeyList) throws RestException {
        AmazonHelper.deleteObjects(objectKeyList);
    }

    @Override
    public void deleteOssfile(String bucket, Collection<String> objectKeyList) throws RestException {
        AmazonHelper.deleteObjects(bucket, objectKeyList);
    }
}
