package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.RestException;
import org.springframework.scheduling.annotation.Async;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

/**
 * <code>OssfileStore</code>
 * <p>The ossfile store interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface OssfileStore {

    /**
     * <code>getOssfileUrl</code>
     * <p>The get ossfile url getter method.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.net.URL} <p>The get ossfile url return object is <code>URL</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.net.URL
     * @see io.github.nichetoolkit.rest.RestException
     */
    URL getOssfileUrl(String objectKey) throws RestException;

    /**
     * <code>getOssfileUrl</code>
     * <p>The get ossfile url getter method.</p>
     * @param bucket    {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.net.URL} <p>The get ossfile url return object is <code>URL</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.net.URL
     * @see io.github.nichetoolkit.rest.RestException
     */
    URL getOssfileUrl(String bucket, String objectKey) throws RestException;

    /**
     * <code>getOssfile</code>
     * <p>The get ossfile getter method.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.io.InputStream} <p>The get ossfile return object is <code>InputStream</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see io.github.nichetoolkit.rest.RestException
     */
    InputStream getOssfile(String objectKey) throws RestException;

    /**
     * <code>getOssfile</code>
     * <p>The get ossfile getter method.</p>
     * @param bucket    {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.io.InputStream} <p>The get ossfile return object is <code>InputStream</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see io.github.nichetoolkit.rest.RestException
     */
    InputStream getOssfile(String bucket, String objectKey) throws RestException;

    /**
     * <code>putOssfile</code>
     * <p>The put ossfile method.</p>
     * @param objectKey   {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    void putOssfile(String objectKey, InputStream inputStream) throws RestException;

    /**
     * <code>putOssfile</code>
     * <p>The put ossfile method.</p>
     * @param bucket      {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey   {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    void putOssfile(String bucket, String objectKey, InputStream inputStream) throws RestException;

    /**
     * <code>startMultipart</code>
     * <p>The start multipart method.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The start multipart return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    String startMultipart(String objectKey) throws RestException;

    /**
     * <code>startMultipart</code>
     * <p>The start multipart method.</p>
     * @param bucket    {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The start multipart return object is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    String startMultipart(String bucket, String objectKey) throws RestException;

    /**
     * <code>uploadMultipart</code>
     * <p>The upload multipart method.</p>
     * @param objectKey   {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param uploadId    {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partIndex   int <p>The part index parameter is <code>int</code> type.</p>
     * @param partSize    long <p>The part size parameter is <code>long</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    void uploadMultipart(String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException;

    /**
     * <code>uploadMultipart</code>
     * <p>The upload multipart method.</p>
     * @param bucket      {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey   {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param uploadId    {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partIndex   int <p>The part index parameter is <code>int</code> type.</p>
     * @param partSize    long <p>The part size parameter is <code>long</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    void uploadMultipart(String bucket, String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException;

    /**
     * <code>finishMultipart</code>
     * <p>The finish multipart method.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param uploadId  {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partETags {@link java.util.Collection} <p>The part e tags parameter is <code>Collection</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    void finishMultipart(String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException;

    /**
     * <code>finishMultipart</code>
     * <p>The finish multipart method.</p>
     * @param bucket    {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param uploadId  {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partETags {@link java.util.Collection} <p>The part e tags parameter is <code>Collection</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see org.springframework.scheduling.annotation.Async
     * @see io.github.nichetoolkit.rest.RestException
     */
    @Async
    void finishMultipart(String bucket, String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException;

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
