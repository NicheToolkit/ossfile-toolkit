package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.RestException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.Future;

/**
 * <code>OssfileStoreService</code>
 * <p>The ossfile store service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.beans.factory.InitializingBean
 * @since Jdk1.8
 */
public interface OssfileStoreService extends InitializingBean {

    @Override
    default void afterPropertiesSet() throws Exception {
    }

    /**
     * <code>credentials</code>
     * <p>The credentials method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileCredentials} <p>The credentials return object is <code>OssfileCredentials</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileCredentials
     * @see io.github.nichetoolkit.rest.RestException
     */
    OssfileCredentials credentials() throws RestException;

    /**
     * <code>createClient</code>
     * <p>The create client method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
    void createClient() throws RestException;

    /**
     * <code>providerType</code>
     * <p>The provider type method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileProviderType} <p>The provider type return object is <code>OssfileProviderType</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileProviderType
     */
    OssfileProviderType providerType();

    /**
     * <code>getOssfileUrl</code>
     * <p>The get ossfile url getter method.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The get ossfile url return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<URL> getOssfileUrl(String objectKey) throws RestException;

    /**
     * <code>getOssfileUrl</code>
     * <p>The get ossfile url getter method.</p>
     * @param bucket    {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The get ossfile url return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<URL> getOssfileUrl(String bucket, String objectKey) throws RestException;

    /**
     * <code>getOssfile</code>
     * <p>The get ossfile getter method.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The get ossfile return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<InputStream> getOssfile(String objectKey) throws RestException;

    /**
     * <code>getOssfile</code>
     * <p>The get ossfile getter method.</p>
     * @param bucket    {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The get ossfile return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<InputStream> getOssfile(String bucket, String objectKey) throws RestException;

    /**
     * <code>putOssfile</code>
     * <p>The put ossfile method.</p>
     * @param objectKey   {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The put ossfile return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<OssfileETagVersion> putOssfile(String objectKey, InputStream inputStream) throws RestException;

    /**
     * <code>putOssfile</code>
     * <p>The put ossfile method.</p>
     * @param bucket      {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey   {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The put ossfile return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<OssfileETagVersion> putOssfile(String bucket, String objectKey, InputStream inputStream) throws RestException;

    /**
     * <code>startMultipart</code>
     * <p>The start multipart method.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The start multipart return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<String> startMultipart(String objectKey) throws RestException;

    /**
     * <code>startMultipart</code>
     * <p>The start multipart method.</p>
     * @param bucket    {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The start multipart return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<String> startMultipart(String bucket, String objectKey) throws RestException;

    /**
     * <code>uploadMultipart</code>
     * <p>The upload multipart method.</p>
     * @param objectKey   {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param uploadId    {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partIndex   int <p>The part index parameter is <code>int</code> type.</p>
     * @param partSize    long <p>The part size parameter is <code>long</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The upload multipart return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<OssfilePartETag> uploadMultipart(String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException;

    /**
     * <code>uploadMultipart</code>
     * <p>The upload multipart method.</p>
     * @param bucket      {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey   {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param uploadId    {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partIndex   int <p>The part index parameter is <code>int</code> type.</p>
     * @param partSize    long <p>The part size parameter is <code>long</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The upload multipart return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<OssfilePartETag> uploadMultipart(String bucket, String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException;

    /**
     * <code>finishMultipart</code>
     * <p>The finish multipart method.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param uploadId  {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partETags {@link java.util.Collection} <p>The part e tags parameter is <code>Collection</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The finish multipart return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<OssfileETagVersion> finishMultipart(String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException;

    /**
     * <code>finishMultipart</code>
     * <p>The finish multipart method.</p>
     * @param bucket    {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param uploadId  {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partETags {@link java.util.Collection} <p>The part e tags parameter is <code>Collection</code> type.</p>
     * @return {@link java.util.concurrent.Future} <p>The finish multipart return object is <code>Future</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see java.util.concurrent.Future
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    Future<OssfileETagVersion> finishMultipart(String bucket, String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException;

    /**
     * <code>deleteOssfile</code>
     * <p>The delete ossfile method.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    void deleteOssfile(String objectKey) throws RestException;

    /**
     * <code>deleteOssfile</code>
     * <p>The delete ossfile method.</p>
     * @param bucket    {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    void deleteOssfile(String bucket, String objectKey) throws RestException;

    /**
     * <code>deleteOssfile</code>
     * <p>The delete ossfile method.</p>
     * @param objectKeyList {@link java.util.Collection} <p>The object key list parameter is <code>Collection</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.Collection
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    void deleteOssfile(Collection<String> objectKeyList) throws RestException;

    /**
     * <code>deleteOssfile</code>
     * <p>The delete ossfile method.</p>
     * @param bucket        {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKeyList {@link java.util.Collection} <p>The object key list parameter is <code>Collection</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    void deleteOssfile(String bucket, Collection<String> objectKeyList) throws RestException;
}
