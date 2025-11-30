package io.github.nichetoolkit.ossfile;

import com.google.common.collect.Multimap;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import io.github.nichetoolkit.rest.error.natives.UnsupportedErrorException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.minio.*;
import io.minio.messages.*;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * <code>MinioUtils</code>
 * <p>The minio utils class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk17
 */
@Slf4j
public class MinioUtils {

    /**
     * <code>initDefaultBucket</code>
     * <p>The init default bucket method.</p>
     * @param minioClient {@link io.minio.MinioClient} <p>The minio client parameter is <code>MinioClient</code> type.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @see io.minio.MinioClient
     * @see java.lang.String
     */
    public static void initDefaultBucket(MinioClient minioClient, String bucketName) {
        try {
            MinioHelper.initDefaultBucket(minioClient, bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the minio server connect has error: {}, bucket: {}", exception.getMessage(), bucketName);
            GeneralUtils.printStackTrace(exception);
        }
    }

    /**
     * <code>bucketPolicy</code>
     * <p>The bucket policy method.</p>
     * @return {@link java.lang.String} <p>The bucket policy return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String bucketPolicy() {
        return bucketPolicy(OssfileStoreHolder.defaultBucket());
    }

    /**
     * <code>bucketPolicy</code>
     * <p>The bucket policy method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The bucket policy return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String bucketPolicy(String bucketName) {
        try {
            return MinioHelper.bucketPolicy(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the minio server get bucket policy has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>listBuckets</code>
     * <p>The list buckets method.</p>
     * @return {@link java.util.List} <p>The list buckets return object is <code>List</code> type.</p>
     * @see java.util.List
     */
    public static List<Bucket> listBuckets() {
        try {
            return MinioHelper.listBuckets();
        } catch (ServiceErrorException exception) {
            log.error("the minio server get all buckets has error: {}", exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return Collections.emptyList();
        }
    }

    /**
     * <code>getBucket</code>
     * <p>The get bucket getter method.</p>
     * @return {@link java.util.Optional} <p>The get bucket return object is <code>Optional</code> type.</p>
     * @see java.util.Optional
     */
    public static Optional<Bucket> getBucket() {
        return getBucket(OssfileStoreHolder.defaultBucket());
    }

    /**
     * <code>getBucket</code>
     * <p>The get bucket getter method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @return {@link java.util.Optional} <p>The get bucket return object is <code>Optional</code> type.</p>
     * @see java.lang.String
     * @see java.util.Optional
     */
    public static Optional<Bucket> getBucket(String bucketName) {
        try {
            return MinioHelper.getBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the minio server get bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return Optional.empty();
        }
    }

    /**
     * <code>switchBucket</code>
     * <p>The switch bucket method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static void switchBucket(String bucketName) {
        try {
            MinioHelper.switchBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the minio server switch bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
        }
    }

    /**
     * <code>makeBucket</code>
     * <p>The make bucket method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static void makeBucket(String bucketName) {
        try {
            MinioHelper.makeBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the minio server make bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
        }
    }

    /**
     * <code>removeBucket</code>
     * <p>The remove bucket method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static void removeBucket(String bucketName) {
        try {
            MinioHelper.removeBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the minio server remove bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
        }
    }


    /**
     * <code>statObject</code>
     * <p>The stat object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.StatObjectResponse} <p>The stat object return object is <code>StatObjectResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.StatObjectResponse
     */
    public static StatObjectResponse statObject(String objectName) {
        return statObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>statObject</code>
     * <p>The stat object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.StatObjectResponse} <p>The stat object return object is <code>StatObjectResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.StatObjectResponse
     */
    public static StatObjectResponse statObject(String bucketName, String objectName) {
        try {
            return MinioHelper.statObject(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the minio server stat object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
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
            MinioHelper.statObject(bucketName, objectName);
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
        return MinioHelper.listObjects(bucketName, prefix, recursive);
    }

    /**
     * <code>allObjects</code>
     * <p>The all objects method.</p>
     * @param prefix    {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param recursive boolean <p>The recursive parameter is <code>boolean</code> type.</p>
     * @return {@link java.util.List} <p>The all objects return object is <code>List</code> type.</p>
     * @see java.lang.String
     * @see java.util.List
     */
    public static List<Item> allObjects(String prefix, boolean recursive) {
        return allObjects(OssfileStoreHolder.defaultBucket(), prefix, recursive);
    }

    /**
     * <code>allObjects</code>
     * <p>The all objects method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param prefix     {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param recursive  boolean <p>The recursive parameter is <code>boolean</code> type.</p>
     * @return {@link java.util.List} <p>The all objects return object is <code>List</code> type.</p>
     * @see java.lang.String
     * @see java.util.List
     */
    public static List<Item> allObjects(String bucketName, String prefix, boolean recursive) {
        try {
            return MinioHelper.allObjects(bucketName, prefix, recursive);
        } catch (FileErrorException exception) {
            log.error("the minio server all objects has error, prefix: {}, error: {}", prefix, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.GetObjectResponse} <p>The get object return object is <code>GetObjectResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.GetObjectResponse
     */
    public static GetObjectResponse getObject(String objectName) {
        return getObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.GetObjectResponse} <p>The get object return object is <code>GetObjectResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.GetObjectResponse
     */
    public static GetObjectResponse getObject(String bucketName, String objectName) {
        try {
            return MinioHelper.getObject(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the minio server get object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param offset     long <p>The offset parameter is <code>long</code> type.</p>
     * @param length     long <p>The length parameter is <code>long</code> type.</p>
     * @return {@link io.minio.GetObjectResponse} <p>The get object return object is <code>GetObjectResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.GetObjectResponse
     */
    public static GetObjectResponse getObject(String objectName, long offset, long length) {
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
     * @see java.lang.String
     * @see io.minio.GetObjectResponse
     */
    public static GetObjectResponse getObject(String bucketName, String objectName, long offset, long length) {
        try {
            return MinioHelper.getObject(bucketName, objectName, offset, length);
        } catch (FileErrorException exception) {
            log.error("the minio server get object with offset and length has error, range: [ {} , {} ], object: {}, bucket: {} error: {}", offset, length, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param file        {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param contentType {@link java.lang.String} <p>The content type parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The put object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see org.springframework.web.multipart.MultipartFile
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse putObject(MultipartFile file, String objectName, String contentType) {
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
     * @see java.lang.String
     * @see org.springframework.web.multipart.MultipartFile
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse putObject(String bucketName, MultipartFile file, String objectName, String contentType) {
        try {
            return MinioHelper.putObject(bucketName, file, objectName, contentType);
        } catch (FileErrorException exception) {
            log.error("the minio server put object has error, contentType: {}, object: {}, bucket: {}, error: {}", contentType, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param fileName   {@link java.lang.String} <p>The file name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The put object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse putObject(String objectName, String fileName) {
        return putObject(OssfileStoreHolder.defaultBucket(), objectName, fileName);
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param fileName   {@link java.lang.String} <p>The file name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The put object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse putObject(String bucketName, String objectName, String fileName) {
        try {
            return MinioHelper.uploadObject(bucketName, objectName, fileName);
        } catch (FileErrorException exception) {
            log.error("the minio server upload object has error, fileName: {}, object: {}, bucket: {}, error: {}", fileName, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }


    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The put object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse putObject(String objectName, InputStream inputStream) {
        return putObject(OssfileStoreHolder.defaultBucket(), objectName, inputStream);
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The put object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse putObject(String bucketName, String objectName, InputStream inputStream) {
        try {
            return MinioHelper.putObject(bucketName, objectName, inputStream);
        } catch (FileErrorException exception) {
            log.error("the minio server put object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>composeSource</code>
     * <p>The compose source method.</p>
     * @param objectName     {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param composeSources {@link java.util.Collection} <p>The compose sources parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose source return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse composeSource(String objectName, Collection<ComposeSource> composeSources) {
        return composeSource(OssfileStoreHolder.defaultBucket(), objectName, composeSources);
    }


    /**
     * <code>composeSource</code>
     * <p>The compose source method.</p>
     * @param bucketName     {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName     {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param composeSources {@link java.util.Collection} <p>The compose sources parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose source return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse composeSource(String bucketName, String objectName, Collection<ComposeSource> composeSources) {
        try {
            return MinioHelper.composeSource(bucketName, objectName, composeSources);
        } catch (FileErrorException exception) {
            log.error("the minio server compose collection source object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>composeSource</code>
     * <p>The compose source method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param sourcesMap {@link java.util.Map} <p>The sources map parameter is <code>Map</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose source return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.util.Map
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse composeSource(String bucketName, String objectName, Map<String, Collection<String>> sourcesMap) {
        try {
            return MinioHelper.composeSource(bucketName, objectName, sourcesMap);
        } catch (FileErrorException exception) {
            log.error("the minio server compose map source object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>composeObject</code>
     * <p>The compose object method.</p>
     * @param bucketName     {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName     {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param composeSources {@link java.util.Collection} <p>The compose sources parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse composeObject(String bucketName, String objectName, Collection<String> composeSources) {
        try {
            return MinioHelper.composeObject(bucketName, objectName, composeSources);
        } catch (FileErrorException exception) {
            log.error("the minio server compose object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>composeObject</code>
     * <p>The compose object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param sources    {@link java.util.Collection} <p>The sources parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse composeObject(String objectName, Collection<String> sources) {
        return composeObject(OssfileStoreHolder.defaultBucket(), objectName, Collections.singletonMap(OssfileStoreHolder.defaultBucket(), sources));
    }

    /**
     * <code>composeObject</code>
     * <p>The compose object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param sourcesMap {@link java.util.Map} <p>The sources map parameter is <code>Map</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.util.Map
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse composeObject(String objectName, Map<String, Collection<String>> sourcesMap) {
        return composeSource(OssfileStoreHolder.defaultBucket(), objectName, sourcesMap);
    }

    /**
     * <code>composeObject</code>
     * <p>The compose object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param sourcesMap {@link java.util.Map} <p>The sources map parameter is <code>Map</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The compose object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.util.Map
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse composeObject(String bucketName, String objectName, Map<String, Collection<String>> sourcesMap) {
        return composeSource(bucketName, objectName, sourcesMap);
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.messages.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @see java.lang.String
     * @see io.minio.messages.InitiateMultipartUploadResult
     */
    public static InitiateMultipartUploadResult initiateMultipart(String objectName) {
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
     * @see java.lang.String
     * @see com.google.common.collect.Multimap
     * @see org.jspecify.annotations.Nullable
     * @see io.minio.messages.InitiateMultipartUploadResult
     */
    public static InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) {
        try {
            return MinioHelper.initiateMultipart(bucketName, objectName, headers, extraQueryParams);
        } catch (FileErrorException exception) {
            log.error("the minio ossfile server initiate multipart has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
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
     * @see java.lang.String
     * @see java.io.InputStream
     * @see io.minio.UploadPartResponse
     */
    public static UploadPartResponse uploadMultipart(String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) {
        return uploadMultipart(OssfileStoreHolder.defaultBucket(), objectName, uploadId, partIndex, inputStream, partSize, null, null);
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
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.google.common.collect.Multimap
     * @see org.jspecify.annotations.Nullable
     * @see io.minio.UploadPartResponse
     */
    public static UploadPartResponse uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) {
        try {
            return MinioHelper.uploadMultipart(bucketName, objectName, uploadId, partIndex, inputStream, partSize, headers, extraQueryParams);
        } catch (FileErrorException exception) {
            log.error("the minio ossfile server upload multipart has error, uploadId: {}, object: {}, bucket: {}, error: {}", uploadId, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>completeMultipart</code>
     * <p>The complete multipart method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId   {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param parts      {@link java.util.Collection} <p>The parts parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The complete multipart return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse completeMultipart(String objectName, String uploadId, Collection<Part> parts) {
        return completeMultipart(OssfileStoreHolder.defaultBucket(), objectName, uploadId, parts, null, null);
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
     * @see java.lang.String
     * @see java.util.Collection
     * @see com.google.common.collect.Multimap
     * @see org.jspecify.annotations.Nullable
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse completeMultipart(String bucketName, String objectName, String uploadId, Collection<Part> parts, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) {
        try {
            return MinioHelper.completeMultipart(bucketName, objectName, uploadId, parts, headers, extraQueryParams);
        } catch (FileErrorException exception) {
            log.error("the minio ossfile server complete multipart has error, uploadId：{}, object: {}, bucket: {}, error: {}", uploadId, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }


    /**
     * <code>uploadObject</code>
     * <p>The upload object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The upload object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse uploadObject(String objectName, String filename) {
        return uploadObject(objectName, filename, 0L);
    }

    /**
     * <code>uploadObject</code>
     * <p>The upload object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @param partSize   long <p>The part size parameter is <code>long</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The upload object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse uploadObject(String objectName, String filename, long partSize) {
        return uploadObject(OssfileStoreHolder.defaultBucket(), objectName, filename, partSize);
    }

    /**
     * <code>uploadObject</code>
     * <p>The upload object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The upload object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse uploadObject(String bucketName, String objectName, String filename) {
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
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse uploadObject(String bucketName, String objectName, String filename, long partSize) {
        try {
            return MinioHelper.uploadObject(bucketName, objectName, filename, partSize);
        } catch (FileErrorException exception) {
            log.error("the minio server upload object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>appendObject</code>
     * <p>The append object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param object     {@link io.minio.SnowballObject} <p>The object parameter is <code>SnowballObject</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The append object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.SnowballObject
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse appendObject(String objectName, SnowballObject object) {
        return appendObject(OssfileStoreHolder.defaultBucket(), objectName, object);
    }

    /**
     * <code>appendObject</code>
     * <p>The append object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param object     {@link io.minio.SnowballObject} <p>The object parameter is <code>SnowballObject</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The append object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.SnowballObject
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse appendObject(String bucketName, String objectName, SnowballObject object) {
        try {
            return MinioHelper.appendObject(bucketName, objectName, object);
        } catch (FileErrorException exception) {
            log.error("the minio server upload append object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>appendObjects</code>
     * <p>The append objects method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param objects    {@link java.util.Collection} <p>The objects parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The append objects return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse appendObjects(String objectName, Collection<SnowballObject> objects) {
        return appendObjects(OssfileStoreHolder.defaultBucket(), objectName, objects);
    }

    /**
     * <code>appendObjects</code>
     * <p>The append objects method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param objects    {@link java.util.Collection} <p>The objects parameter is <code>Collection</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The append objects return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse appendObjects(String bucketName, String objectName, Collection<SnowballObject> objects) {
        try {
            return MinioHelper.appendObjects(bucketName, objectName, objects);
        } catch (FileErrorException exception) {
            log.error("the minio server upload append objects has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>copyObject</code>
     * <p>The copy object method.</p>
     * @param sourceObjectName {@link java.lang.String} <p>The source object name parameter is <code>String</code> type.</p>
     * @param targetObjectName {@link java.lang.String} <p>The target object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.ObjectWriteResponse} <p>The copy object return object is <code>ObjectWriteResponse</code> type.</p>
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse copyObject(String sourceObjectName, String targetObjectName) {
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
     * @see java.lang.String
     * @see io.minio.ObjectWriteResponse
     */
    public static ObjectWriteResponse copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) {
        try {
            return MinioHelper.copyObject(sourceBucketName, sourceObjectName, targetBucketName, targetObjectName);
        } catch (FileErrorException exception) {
            log.error("the minio server copy object has error, target object: {}, target bucket: {}, source object: {}, source bucket: {}, error: {}", targetObjectName, targetBucketName, sourceObjectName, sourceBucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>removeObject</code>
     * <p>The remove object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static void removeObject(String objectName) {
        removeObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>removeObject</code>
     * <p>The remove object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static void removeObject(String bucketName, String objectName) {
        try {
            MinioHelper.removeObject(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the minio server remove object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
        }
    }

    /**
     * <code>removeObjects</code>
     * <p>The remove objects method.</p>
     * @param objectNames {@link java.util.Collection} <p>The object names parameter is <code>Collection</code> type.</p>
     * @see java.util.Collection
     */
    public static void removeObjects(Collection<String> objectNames) {
        removeObjects(OssfileStoreHolder.defaultBucket(), objectNames);
    }

    /**
     * <code>removeObjects</code>
     * <p>The remove objects method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectNames {@link java.util.Collection} <p>The object names parameter is <code>Collection</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     */
    public static void removeObjects(String bucketName, Collection<String> objectNames) {
        objectNames.forEach(objectName -> removeObject(bucketName, objectName));
    }

    /**
     * <code>objectUrl</code>
     * <p>The object url method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param expire     {@link java.lang.Integer} <p>The expire parameter is <code>Integer</code> type.</p>
     * @return {@link java.net.URL} <p>The object url return object is <code>URL</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     * @see java.net.URL
     */
    public static URL objectUrl(String objectName, Integer expire) {
        return objectUrl(OssfileStoreHolder.defaultBucket(), objectName, expire);
    }

    /**
     * <code>objectUrl</code>
     * <p>The object url method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param expire     {@link java.lang.Integer} <p>The expire parameter is <code>Integer</code> type.</p>
     * @return {@link java.net.URL} <p>The object url return object is <code>URL</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     * @see java.net.URL
     */
    public static URL objectUrl(String bucketName, String objectName, Integer expire) {
        try {
            return MinioHelper.objectUrl(bucketName, objectName, expire);
        } catch (FileErrorException exception) {
            log.error("the minio server presigned object url has error, expire: {}, object: {}, bucket: {}, error: {}", expire, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>uriDecode</code>
     * <p>The uri decode method.</p>
     * @param url {@link java.lang.String} <p>The url parameter is <code>String</code> type.</p>
     * @return {@link java.lang.String} <p>The uri decode return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String uriDecode(String url) {
        try {
            return MinioHelper.uriDecode(url);
        } catch (UnsupportedErrorException exception) {
            log.error("the minio server url decode has error, error: {}", exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

}
