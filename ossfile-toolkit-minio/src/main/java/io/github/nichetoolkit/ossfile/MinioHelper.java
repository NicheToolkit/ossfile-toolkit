package io.github.nichetoolkit.ossfile;

import com.google.common.collect.Multimap;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import io.github.nichetoolkit.rest.error.natives.UnsupportedErrorException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.minio.messages.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * <code>MinioHelper</code>
 * <p>The minio helper class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public class MinioHelper {

    /**
     * <code>createAsyncClient</code>
     * <p>The create async client method.</p>
     * @param ossfileProperties {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The ossfile properties parameter is <code>OssfileProperties</code> type.</p>
     * @return {@link io.minio.MinioAsyncClient} <p>The create async client return object is <code>MinioAsyncClient</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     * @see io.minio.MinioAsyncClient
     */
    public static MinioAsyncClient createAsyncClient(OssfileProperties ossfileProperties) {
        MinioAsyncClient.Builder builder = MinioAsyncClient.builder();
        if (ossfileProperties.getIntranetPriority() && GeneralUtils.isNotEmpty(ossfileProperties.getIntranet())) {
            builder.endpoint(ossfileProperties.getIntranet());
        } else {
            builder.endpoint(ossfileProperties.getEndpoint());
        }
        return builder.credentials(ossfileProperties.getAccessKey(), ossfileProperties.getSecretKey()).build();
    }

    /**
     * <code>createMinioClient</code>
     * <p>The create minio client method.</p>
     * @param ossfileProperties {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The ossfile properties parameter is <code>OssfileProperties</code> type.</p>
     * @return {@link io.minio.MinioClient} <p>The create minio client return object is <code>MinioClient</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     * @see io.minio.MinioClient
     */
    public static MinioClient createMinioClient(OssfileProperties ossfileProperties) {
        MinioClient.Builder builder = MinioClient.builder();
        Optional.ofNullable(ossfileProperties.getRegion()).ifPresent(builder::region);
        if (ossfileProperties.getIntranetPriority() && GeneralUtils.isNotEmpty(ossfileProperties.getIntranet())) {
            builder.endpoint(ossfileProperties.getIntranet());
        } else {
            builder.endpoint(ossfileProperties.getEndpoint());
        }
        return builder
                .credentials(ossfileProperties.getAccessKey(), ossfileProperties.getSecretKey())
                .build();
    }

    /**
     * <code>initDefaultBucket</code>
     * <p>The init default bucket method.</p>
     * @param minioClient {@link io.minio.MinioClient} <p>The minio client parameter is <code>MinioClient</code> type.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see io.minio.MinioClient
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static void initDefaultBucket(MinioClient minioClient, String bucketName) throws ServiceErrorException {
        try {
            boolean isBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isBucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException exception) {
            throw OssfileStoreHolder.serviceErrorOfCause(OssfileErrorStatus.OSSFILE_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>bucketPolicy</code>
     * <p>The bucket policy method.</p>
     * @return {@link java.lang.String} <p>The bucket policy return object is <code>String</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static String bucketPolicy() throws ServiceErrorException {
        return bucketPolicy(OssfileStoreHolder.defaultBucket());
    }

    /**
     * <code>bucketPolicy</code>
     * <p>The bucket policy method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The bucket policy return object is <code>String</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static String bucketPolicy(String bucketName) throws ServiceErrorException {
        try {
            return MinioContextHolder.defaultClient().getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.serviceErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_POLICY_ERROR, exception);
        }
    }

    /**
     * <code>listBuckets</code>
     * <p>The list buckets method.</p>
     * @return {@link java.util.List} <p>The list buckets return object is <code>List</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static List<Bucket> listBuckets() throws ServiceErrorException {
        try {
            return MinioContextHolder.defaultClient().listBuckets();
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.serviceErrorOfCause(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception);
        }
    }

    /**
     * <code>getBucket</code>
     * <p>The get bucket getter method.</p>
     * @return {@link java.util.Optional} <p>The get bucket return object is <code>Optional</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see java.util.Optional
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static Optional<Bucket> getBucket() throws ServiceErrorException {
        return getBucket(OssfileStoreHolder.defaultBucket());
    }

    /**
     * <code>getBucket</code>
     * <p>The get bucket getter method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @return {@link java.util.Optional} <p>The get bucket return object is <code>Optional</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Optional
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static Optional<Bucket> getBucket(String bucketName) throws ServiceErrorException {
        return listBuckets().stream().filter(bucket -> bucket.name().equals(bucketName)).findFirst();
    }

    /**
     * <code>switchBucket</code>
     * <p>The switch bucket method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static void switchBucket(String bucketName) throws ServiceErrorException {
        OssfileStoreHolder.switchBucket(bucketName);
        initDefaultBucket(MinioContextHolder.defaultClient(), bucketName);
    }

    /**
     * <code>makeBucket</code>
     * <p>The make bucket method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static void makeBucket(String bucketName) throws ServiceErrorException {
        try {
            MinioContextHolder.defaultClient().makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.serviceErrorOfCause(OssfileErrorStatus.OSSFILE_MAKE_BUCKET_ERROR, exception);
        }
    }

    /**
     * <code>removeBucket</code>
     * <p>The remove bucket method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static void removeBucket(String bucketName) throws ServiceErrorException {
        try {
            MinioContextHolder.defaultClient().removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.serviceErrorOfCause(OssfileErrorStatus.OSSFILE_REMOVE_BUCKET_ERROR, exception);
        }
    }

    /**
     * <code>statObject</code>
     * <p>The stat object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.StatObjectResponse} <p>The stat object return object is <code>StatObjectResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.StatObjectResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static StatObjectResponse statObject(String objectName) throws FileErrorException {
        return statObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>statObject</code>
     * <p>The stat object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.StatObjectResponse} <p>The stat object return object is <code>StatObjectResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.StatObjectResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static StatObjectResponse statObject(String bucketName, String objectName) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_STAT_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>isObjectExist</code>
     * <p>The is object exist method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return boolean <p>The is object exist return object is <code>boolean</code> type.</p>
     * @see java.lang.String
     */
    public static boolean isObjectExist(String objectName) {
        return isObjectExist(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>isObjectExist</code>
     * <p>The is object exist method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return boolean <p>The is object exist return object is <code>boolean</code> type.</p>
     * @see java.lang.String
     */
    public static boolean isObjectExist(String bucketName, String objectName) {
        boolean exist = true;
        try {
            statObject(bucketName, objectName);
        } catch (FileErrorException ignored) {
            exist = false;
        }
        return exist;
    }

    /**
     * <code>listObjects</code>
     * <p>The list objects method.</p>
     * @param prefix    {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param recursive boolean <p>The recursive parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.Iterable} <p>The list objects return object is <code>Iterable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Iterable
     */
    public static Iterable<Result<Item>> listObjects(String prefix, boolean recursive) {
        return listObjects(OssfileStoreHolder.defaultBucket(), prefix, recursive);
    }

    /**
     * <code>listObjects</code>
     * <p>The list objects method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param prefix     {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param recursive  boolean <p>The recursive parameter is <code>boolean</code> type.</p>
     * @return {@link java.lang.Iterable} <p>The list objects return object is <code>Iterable</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Iterable
     */
    public static Iterable<Result<Item>> listObjects(String bucketName, String prefix, boolean recursive) {
        return MinioContextHolder.defaultClient().listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
    }


    /**
     * <code>allObjects</code>
     * <p>The all objects method.</p>
     * @param prefix    {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param recursive boolean <p>The recursive parameter is <code>boolean</code> type.</p>
     * @return {@link java.util.List} <p>The all objects return object is <code>List</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.List
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static List<Item> allObjects(String prefix, boolean recursive) throws FileErrorException {
        return allObjects(OssfileStoreHolder.defaultBucket(), prefix, recursive);
    }

    /**
     * <code>allObjects</code>
     * <p>The all objects method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param prefix     {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param recursive  boolean <p>The recursive parameter is <code>boolean</code> type.</p>
     * @return {@link java.util.List} <p>The all objects return object is <code>List</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.List
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static List<Item> allObjects(String bucketName, String prefix, boolean recursive) throws FileErrorException {
        List<Item> items = new ArrayList<>();
        Iterable<Result<Item>> resultIterable = listObjects(bucketName, prefix, recursive);
        if (GeneralUtils.isNotEmpty(resultIterable)) {
            try {
                for (Result<Item> itemResult : resultIterable) {
                    items.add(itemResult.get());
                }
            } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException exception) {
                throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_GET_ALL_OBJECTS_ERROR, exception);
            }
        }
        return items;
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.GetObjectResponse} <p>The get object return object is <code>GetObjectResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.GetObjectResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static GetObjectResponse getObject(String objectName) throws FileErrorException {
        return getObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.GetObjectResponse} <p>The get object return object is <code>GetObjectResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.GetObjectResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static GetObjectResponse getObject(String bucketName, String objectName) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param offset     long <p>The offset parameter is <code>long</code> type.</p>
     * @param length     long <p>The length parameter is <code>long</code> type.</p>
     * @return {@link io.minio.GetObjectResponse} <p>The get object return object is <code>GetObjectResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.GetObjectResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static GetObjectResponse getObject(String objectName, long offset, long length) throws FileErrorException {
        return getObject(OssfileStoreHolder.defaultBucket(), objectName, offset, length);
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param offset     long <p>The offset parameter is <code>long</code> type.</p>
     * @param length     long <p>The length parameter is <code>long</code> type.</p>
     * @return {@link io.minio.GetObjectResponse} <p>The get object return object is <code>GetObjectResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.GetObjectResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static GetObjectResponse getObject(String bucketName, String objectName, long offset, long length) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName)
                    .offset(offset).length(length).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param file        {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param contentType {@link java.lang.String} <p>The content type parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The put object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see org.springframework.web.multipart.MultipartFile
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse putObject(MultipartFile file, String objectName, String contentType) throws FileErrorException {
        return putObject(OssfileStoreHolder.defaultBucket(), file, objectName, contentType);
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param file        {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param contentType {@link java.lang.String} <p>The content type parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The put object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.web.multipart.MultipartFile
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse putObject(String bucketName, MultipartFile file, String objectName, String contentType) throws FileErrorException {
        try {
            InputStream inputStream = file.getInputStream();
            return MinioContextHolder.defaultClient().putObject(PutObjectArgs.builder().bucket(bucketName)
                    .object(objectName).contentType(contentType)
                    .stream(inputStream, inputStream.available(), -1).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The put object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse putObject(String objectName, InputStream inputStream) throws FileErrorException {
        return putObject(OssfileStoreHolder.defaultBucket(), objectName, inputStream);
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The put object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse putObject(String bucketName, String objectName, InputStream inputStream) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(inputStream, inputStream.available(), -1).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.messages.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.messages.InitiateMultipartUploadResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static InitiateMultipartUploadResult initiateMultipart(String objectName) throws FileErrorException {
        return initiateMultipart(OssfileStoreHolder.defaultBucket(), objectName, null, null);
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param bucketName       {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName       {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param headers          {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @return {@link io.minio.messages.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.google.common.collect.Multimap
     * @see org.springframework.lang.Nullable
     * @see io.minio.messages.InitiateMultipartUploadResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        return MinioContextHolder.multipartClient().initiateMultipart(bucketName,objectName,headers,extraQueryParams);
    }

    /**
     * <code>completeMultipart</code>
     * <p>The complete multipart method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId   {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param parts      {@link java.util.Collection} <p>The parts parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The complete multipart return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse completeMultipart(String objectName, String uploadId, Collection<Part> parts) throws FileErrorException {
        return completeMultipart(OssfileStoreHolder.defaultBucket(), objectName, uploadId, parts,null,null);
    }

    /**
     * <code>completeMultipart</code>
     * <p>The complete multipart method.</p>
     * @param bucketName       {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName       {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId         {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param parts            {@link java.util.Collection} <p>The parts parameter is <code>Collection</code> type.</p>
     * @param headers          {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The complete multipart return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see com.google.common.collect.Multimap
     * @see org.springframework.lang.Nullable
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse completeMultipart(String bucketName, String objectName, String uploadId, Collection<Part> parts, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        return MinioContextHolder.multipartClient().completeMultipart(bucketName,objectName,uploadId,parts,headers,extraQueryParams);
    }

    /**
     * <code>uploadMultipart</code>
     * <p>The upload multipart method.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId    {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partIndex   int <p>The part index parameter is <code>int</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partSize    long <p>The part size parameter is <code>long</code> type.</p>
     * @return {@link io.minio.UploadPartResponse} <p>The upload multipart return object is <code>UploadPartResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see io.minio.UploadPartResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static UploadPartResponse uploadMultipart(String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws FileErrorException {
        return uploadMultipart(OssfileStoreHolder.defaultBucket(), objectName, uploadId, partIndex, inputStream, partSize,null,null);
    }

    /**
     * <code>uploadMultipart</code>
     * <p>The upload multipart method.</p>
     * @param bucketName       {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName       {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId         {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partIndex        int <p>The part index parameter is <code>int</code> type.</p>
     * @param inputStream      {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partSize         long <p>The part size parameter is <code>long</code> type.</p>
     * @param headers          {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @return {@link io.minio.UploadPartResponse} <p>The upload multipart return object is <code>UploadPartResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.google.common.collect.Multimap
     * @see org.springframework.lang.Nullable
     * @see io.minio.UploadPartResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static UploadPartResponse uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        return MinioContextHolder.multipartClient().uploadMultipart(bucketName, objectName, uploadId, partIndex, inputStream, partSize,headers,extraQueryParams);
    }

    /**
     * <code>composeSource</code>
     * <p>The compose source method.</p>
     * @param objectName     {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param composeSources {@link java.util.Collection} <p>The compose sources parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose source return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse composeSource(String objectName, Collection<ComposeSource> composeSources) throws FileErrorException {
        return composeSource(OssfileStoreHolder.defaultBucket(), objectName, composeSources);
    }

    /**
     * <code>composeSource</code>
     * <p>The compose source method.</p>
     * @param bucketName     {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName     {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param composeSources {@link java.util.Collection} <p>The compose sources parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose source return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse composeSource(String bucketName, String objectName, Collection<ComposeSource> composeSources) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().composeObject(ComposeObjectArgs.builder().bucket(bucketName)
                    .object(objectName).sources(new ArrayList<>(composeSources)).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_COMPOSE_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>composeSource</code>
     * <p>The compose source method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param sourcesMap {@link java.util.Map} <p>The sources map parameter is <code>Map</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose source return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Map
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse composeSource(String bucketName, String objectName, Map<String, Collection<String>> sourcesMap) throws FileErrorException {
        List<ComposeSource> composeSources = new ArrayList<>();
        sourcesMap.forEach((sourceBucketName, sourceObjectNames) -> sourceObjectNames.forEach(sourceObjectName -> {
            composeSources.add(ComposeSource.builder().bucket(sourceBucketName).object(sourceObjectName).build());
        }));
        return composeSource(bucketName, objectName, composeSources);
    }

    /**
     * <code>composeObject</code>
     * <p>The compose object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param sources    {@link java.util.Collection} <p>The sources parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse composeObject(String bucketName, String objectName, Collection<String> sources) throws FileErrorException {
        return composeObject(bucketName, objectName, Collections.singletonMap(bucketName, sources));
    }

    /**
     * <code>composeObject</code>
     * <p>The compose object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param sources    {@link java.util.Collection} <p>The sources parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse composeObject(String objectName, Collection<String> sources) throws FileErrorException {
        return composeObject(OssfileStoreHolder.defaultBucket(), objectName, Collections.singletonMap(OssfileStoreHolder.defaultBucket(), sources));
    }

    /**
     * <code>composeObject</code>
     * <p>The compose object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param sourcesMap {@link java.util.Map} <p>The sources map parameter is <code>Map</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Map
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse composeObject(String bucketName, String objectName, Map<String, Collection<String>> sourcesMap) throws FileErrorException {
        return composeSource(bucketName, objectName, sourcesMap);
    }

    /**
     * <code>composeObject</code>
     * <p>The compose object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param sourcesMap {@link java.util.Map} <p>The sources map parameter is <code>Map</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Map
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse composeObject(String objectName, Map<String, Collection<String>> sourcesMap) throws FileErrorException {
        return composeObject(OssfileStoreHolder.defaultBucket(), objectName, sourcesMap);
    }

    /**
     * <code>uploadObject</code>
     * <p>The upload object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The upload object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse uploadObject(String objectName, String filename) throws FileErrorException {
        return uploadObject(objectName, filename, 0L);
    }

    /**
     * <code>uploadObject</code>
     * <p>The upload object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @param partSize   long <p>The part size parameter is <code>long</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The upload object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse uploadObject(String objectName, String filename, long partSize) throws FileErrorException {
        return uploadObject(OssfileStoreHolder.defaultBucket(), objectName, filename, partSize);
    }

    /**
     * <code>uploadObject</code>
     * <p>The upload object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The upload object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse uploadObject(String bucketName, String objectName, String filename) throws FileErrorException {
        return uploadObject(bucketName, objectName, filename, 0L);
    }

    /**
     * <code>uploadObject</code>
     * <p>The upload object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @param partSize   long <p>The part size parameter is <code>long</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The upload object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse uploadObject(String bucketName, String objectName, String filename, long partSize) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().uploadObject(UploadObjectArgs.builder().bucket(bucketName)
                    .object(objectName).filename(filename, partSize).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_UPLOAD_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>appendObject</code>
     * <p>The append object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param object     {@link io.minio.SnowballObject} <p>The object parameter is <code>SnowballObject</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The append object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.SnowballObject
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse appendObject(String objectName, SnowballObject object) throws FileErrorException {
        return appendObjects(OssfileStoreHolder.defaultBucket(),objectName,Collections.singletonList(object));
    }

    /**
     * <code>appendObject</code>
     * <p>The append object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param object     {@link io.minio.SnowballObject} <p>The object parameter is <code>SnowballObject</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The append object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.SnowballObject
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse appendObject(String bucketName, String objectName, SnowballObject object) throws FileErrorException {
        return appendObjects(bucketName,objectName,Collections.singletonList(object));
    }

    /**
     * <code>appendObjects</code>
     * <p>The append objects method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param objects    {@link java.util.Collection} <p>The objects parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The append objects return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse appendObjects(String objectName, Collection<SnowballObject> objects) throws FileErrorException {
        return appendObjects(OssfileStoreHolder.defaultBucket(),objectName,objects);
    }

    /**
     * <code>appendObjects</code>
     * <p>The append objects method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param objects    {@link java.util.Collection} <p>The objects parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The append objects return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse appendObjects(String bucketName, String objectName, Collection<SnowballObject> objects) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().uploadSnowballObjects(UploadSnowballObjectsArgs.builder().bucket(bucketName)
                    .object(objectName).objects(objects).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_APPEND_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>copyObject</code>
     * <p>The copy object method.</p>
     * @param sourceObjectName {@link java.lang.String} <p>The source object name parameter is <code>String</code> type.</p>
     * @param targetObjectName {@link java.lang.String} <p>The target object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The copy object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse copyObject(String sourceObjectName, String targetObjectName) throws FileErrorException {
        return copyObject(OssfileStoreHolder.defaultBucket(), sourceObjectName, OssfileStoreHolder.defaultBucket(), targetObjectName);
    }

    /**
     * <code>copyObject</code>
     * <p>The copy object method.</p>
     * @param sourceBucketName {@link java.lang.String} <p>The source bucket name parameter is <code>String</code> type.</p>
     * @param sourceObjectName {@link java.lang.String} <p>The source object name parameter is <code>String</code> type.</p>
     * @param targetBucketName {@link java.lang.String} <p>The target bucket name parameter is <code>String</code> type.</p>
     * @param targetObjectName {@link java.lang.String} <p>The target object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The copy object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectWriteResponse copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().copyObject(CopyObjectArgs.builder().source(CopySource.builder().bucket(sourceBucketName).object(sourceObjectName).build())
                    .bucket(targetBucketName).object(targetObjectName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_COPY_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>removeObject</code>
     * <p>The remove object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static void removeObject(String objectName) throws FileErrorException {
        removeObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>removeObject</code>
     * <p>The remove object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static void removeObject(String bucketName, String objectName) throws FileErrorException {
        try {
            MinioContextHolder.defaultClient().removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_REMOVE_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>removeObjects</code>
     * <p>The remove objects method.</p>
     * @param objectNames {@link java.util.Collection} <p>The object names parameter is <code>Collection</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static void removeObjects(Collection<String> objectNames) throws FileErrorException {
        removeObjects(OssfileStoreHolder.defaultBucket(), objectNames);
    }

    /**
     * <code>removeObjects</code>
     * <p>The remove objects method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectNames {@link java.util.Collection} <p>The object names parameter is <code>Collection</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static void removeObjects(String bucketName, Collection<String> objectNames) throws FileErrorException {
        for (String objectName : objectNames) {
            removeObject(bucketName, objectName);
        }
    }

    /**
     * <code>objectUrl</code>
     * <p>The object url method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param expire     {@link java.lang.Integer} <p>The expire parameter is <code>Integer</code> type.</p>
     * @return {@link java.net.URL} <p>The object url return object is <code>URL</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     * @see java.net.URL
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static URL objectUrl(String objectName, Integer expire) throws FileErrorException {
        return objectUrl(OssfileStoreHolder.defaultBucket(),objectName, expire);
    }

    /**
     * <code>objectUrl</code>
     * <p>The object url method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param expire     {@link java.lang.Integer} <p>The expire parameter is <code>Integer</code> type.</p>
     * @return {@link java.net.URL} <p>The object url return object is <code>URL</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     * @see java.net.URL
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static URL objectUrl(String bucketName, String objectName, Integer expire) throws FileErrorException {
        try {
            String presignedObjectUrl = MinioContextHolder.defaultClient().getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).expiry(expire).build());
            return new URL(presignedObjectUrl);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PRESIGNED_OBJECT_URL_ERROR, exception);
        }
    }

    /**
     * <code>uriDecode</code>
     * <p>The uri decode method.</p>
     * @param uri {@link java.lang.String} <p>The uri parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The uri decode return object is <code>String</code> type.</p>
     * @throws UnsupportedErrorException {@link io.github.nichetoolkit.rest.error.natives.UnsupportedErrorException} <p>The unsupported error exception is <code>UnsupportedErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.UnsupportedErrorException
     */
    public static String uriDecode(String uri) throws UnsupportedErrorException {
        String url = uri.trim().replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            throw new UnsupportedErrorException(OssfileErrorStatus.OSSFILE_UNSUPPORTED_ERROR, exception);
        }

    }

}
