package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.RestException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.Future;

public interface OssfileStoreService extends InitializingBean {

    @Override
    default void afterPropertiesSet() throws Exception {
    }

    OssfileCredentials credentials() throws RestException;

    void createClient() throws RestException;

    OssfileProviderType providerType();

    @Async
    Future<URL> getOssfileUrl(String objectKey) throws RestException;

    @Async
    Future<URL> getOssfileUrl(String bucket, String objectKey) throws RestException;

    @Async
    Future<InputStream> getOssfile(String objectKey) throws RestException;

    @Async
    Future<InputStream> getOssfile(String bucket, String objectKey) throws RestException;

    @Async
    Future<OssfileETagVersion> putOssfile(String objectKey, InputStream inputStream) throws RestException;

    @Async
    Future<OssfileETagVersion> putOssfile(String bucket, String objectKey, InputStream inputStream) throws RestException;

    @Async
    Future<String> startMultipart(String objectKey) throws RestException;

    @Async
    Future<String> startMultipart(String bucket, String objectKey) throws RestException;

    @Async
    Future<OssfilePartETag> uploadMultipart(String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException;

    @Async
    Future<OssfilePartETag> uploadMultipart(String bucket, String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException;

    @Async
    Future<OssfileETagVersion> finishMultipart(String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException;

    @Async
    Future<OssfileETagVersion> finishMultipart(String bucket, String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException;

    @Async
    void deleteOssfile(String objectKey) throws RestException;

    @Async
    void deleteOssfile(String bucket, String objectKey) throws RestException;

    @Async
    void deleteOssfile(Collection<String> objectKeyList) throws RestException;

    @Async
    void deleteOssfile(String bucket, Collection<String> objectKeyList) throws RestException;
}
