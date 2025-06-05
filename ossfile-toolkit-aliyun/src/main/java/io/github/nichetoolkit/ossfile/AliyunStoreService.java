package io.github.nichetoolkit.ossfile;

import com.aliyun.oss.model.OSSObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;

/**
 * <code>AliyunStoreService</code>
 * <p>The aliyun store service class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfileStoreService
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.stereotype.Service
 * @since Jdk1.8
 */
@Slf4j
@Service
public class AliyunStoreService extends OssfileStoreService {

    /**
     * <code>AliyunStoreService</code>
     * <p>Instantiates a new aliyun store service.</p>
     * @param properties {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The properties parameter is <code>OssfileProperties</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     */
    public AliyunStoreService(OssfileProperties properties) {
        super(properties);
    }

    @Override
    public OssfileCredentials credentials() throws RestException {
        try {
            DefaultProfile profile = DefaultProfile.getProfile(
                    properties.getRegion(), properties.getAccessKey(), properties.getSecretKey());
            IAcsClient client = new DefaultAcsClient(profile);
            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setDurationSeconds(properties.getExpire());
            request.setRoleArn(properties.getRoleArn());
            request.setRoleSessionName(properties.getRoleName());
            AssumeRoleResponse.Credentials response = client.getAcsResponse(request).getCredentials();
            return new OssfileCredentials(response.getAccessKeyId(), response.getAccessKeySecret(), response.getSecurityToken(), properties.getExpire());
        } catch (ClientException exception) {
            log.error("the aliyun server provided credentials url has error, error: {}", exception.getMessage());
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_CREDENTIALS_ERROR, exception.getMessage());
        }
    }

    @Override
    public void createClient() throws RestException {
        AliyunContextHolder.refreshClient();
    }

    @Override
    public OssfileProviderType providerType() throws RestException {
        return OssfileProviderType.ALIYUN;
    }

    @Override
    public URL getOssfileUrl(String objectKey) throws RestException {
        return AliyunHelper.objectUrl(objectKey, Math.toIntExact(properties.getExpire()));
    }

    @Override
    public URL getOssfileUrl(String bucket, String objectKey) throws RestException {
        return AliyunHelper.objectUrl(bucket, objectKey, Math.toIntExact(properties.getExpire()));
    }

    @Override
    public InputStream getOssfile(String objectKey) throws RestException {
        OSSObject ossObject = AliyunHelper.getObject(objectKey);
        return Optional.ofNullable(ossObject).map(OSSObject::getObjectContent).orElse(null);
    }

    @Override
    public InputStream getOssfile(String bucket, String objectKey) throws RestException {
        OSSObject ossObject = AliyunHelper.getObject(bucket,objectKey);
        return Optional.ofNullable(ossObject).map(OSSObject::getObjectContent).orElse(null);
    }

    @Override
    public void putOssfile(String objectKey, InputStream inputStream) throws RestException {
        AliyunHelper.putObject(objectKey,inputStream);
    }

    @Override
    public void putOssfile(String bucket, String objectKey, InputStream inputStream) throws RestException {
        AliyunHelper.putObject(bucket,objectKey,inputStream);
    }

    @Override
    public void deleteOssfile(String objectKey) throws RestException {
        AliyunHelper.deleteObject(objectKey);
    }

    @Override
    public void deleteOssfile(String bucket, String objectKey) throws RestException {
        AliyunHelper.deleteObject(bucket,objectKey);
    }

    @Override
    public void deleteOssfile(Collection<String> objectKeyList) throws RestException {
        AliyunHelper.deleteObjects(objectKeyList);
    }

    @Override
    public void deleteOssfile(String bucket, Collection<String> objectKeyList) throws RestException {
        AliyunHelper.deleteObjects(bucket,objectKeyList);
    }
}
