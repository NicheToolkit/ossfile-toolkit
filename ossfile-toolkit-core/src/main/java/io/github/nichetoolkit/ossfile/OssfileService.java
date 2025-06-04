package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.RestException;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

/**
 * <code>OssfileService</code>
 * <p>The ossfile service interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface OssfileService {

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
     * @see io.github.nichetoolkit.rest.RestException
     */
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
     * @see io.github.nichetoolkit.rest.RestException
     */
    void putOssfile(String bucket, String objectKey, InputStream inputStream) throws RestException;

    /**
     * <code>margeOssfile</code>
     * <p>The marge ossfile method.</p>
     * @param objectKey     {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param objectKeyList {@link java.util.Collection} <p>The object key list parameter is <code>Collection</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    void margeOssfile(String objectKey, Collection<String> objectKeyList) throws RestException;

    /**
     * <code>margeOssfile</code>
     * <p>The marge ossfile method.</p>
     * @param bucket        {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey     {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @param objectKeyList {@link java.util.Collection} <p>The object key list parameter is <code>Collection</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    void margeOssfile(String bucket, String objectKey, Collection<String> objectKeyList) throws RestException;

    /**
     * <code>deleteOssfile</code>
     * <p>The delete ossfile method.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    void deleteOssfile(String objectKey) throws RestException;

    /**
     * <code>deleteOssfile</code>
     * <p>The delete ossfile method.</p>
     * @param bucket    {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKey {@link java.lang.String} <p>The object key parameter is <code>String</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.RestException
     */
    void deleteOssfile(String bucket, String objectKey) throws RestException;

    /**
     * <code>deleteOssfile</code>
     * <p>The delete ossfile method.</p>
     * @param objectKeyList {@link java.util.Collection} <p>The object key list parameter is <code>Collection</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    void deleteOssfile(Collection<String> objectKeyList) throws RestException;

    /**
     * <code>deleteOssfile</code>
     * <p>The delete ossfile method.</p>
     * @param bucket        {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param objectKeyList {@link java.util.Collection} <p>The object key list parameter is <code>Collection</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.RestException
     */
    void deleteOssfile(String bucket, Collection<String> objectKeyList) throws RestException;
}
