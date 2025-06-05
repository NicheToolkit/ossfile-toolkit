package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.RestException;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

public interface OssfileService {

    URL getOssfileUrl(String objectKey) throws RestException;

    URL getOssfileUrl(String bucket, String objectKey) throws RestException;

    InputStream getOssfile(String objectKey) throws RestException;

    InputStream getOssfile(String bucket, String objectKey) throws RestException;

    void putOssfile(String objectKey, InputStream inputStream) throws RestException;

    void putOssfile(String bucket, String objectKey, InputStream inputStream) throws RestException;

    String startMultipart(String objectKey) throws RestException;

    String startMultipart(String bucket, String objectKey) throws RestException;

    void uploadMultipart(String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException;

    void uploadMultipart(String bucket, String objectKey, String uploadId, InputStream inputStream, int partIndex, long partSize) throws RestException;

    void finishMultipart(String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException;

    void finishMultipart(String bucket, String objectKey, String uploadId, Collection<OssfilePartETag> partETags) throws RestException;

    void deleteOssfile(String objectKey) throws RestException;

    void deleteOssfile(String bucket, String objectKey) throws RestException;

    void deleteOssfile(Collection<String> objectKeyList) throws RestException;

    void deleteOssfile(String bucket, Collection<String> objectKeyList) throws RestException;
}
