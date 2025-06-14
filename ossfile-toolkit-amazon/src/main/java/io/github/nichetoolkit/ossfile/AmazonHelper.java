package io.github.nichetoolkit.ossfile;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <code>AmazonHelper</code>
 * <p>The amazon helper class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public class AmazonHelper {

    /**
     * <code>createAmazonClient</code>
     * <p>The create amazon client method.</p>
     * @param ossfileProperties {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The ossfile properties parameter is <code>OssfileProperties</code> type.</p>
     * @return {@link com.amazonaws.services.s3.AmazonS3} <p>The create amazon client return object is <code>AmazonS3</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     * @see com.amazonaws.services.s3.AmazonS3
     */
    public static AmazonS3 createAmazonClient(OssfileProperties ossfileProperties) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ossfileProperties.getAccessKey(), ossfileProperties.getSecretKey())))
                .withRegion(ossfileProperties.getRegion())
                .build();
    }

    /**
     * <code>initDefaultBucket</code>
     * <p>The init default bucket method.</p>
     * @param amazonClient {@link com.amazonaws.services.s3.AmazonS3} <p>The amazon client parameter is <code>AmazonS3</code> type.</p>
     * @param bucketName   {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see com.amazonaws.services.s3.AmazonS3
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static void initDefaultBucket(AmazonS3 amazonClient, String bucketName) throws ServiceErrorException {
        try {
            boolean isBucketExists = amazonClient.doesBucketExistV2(bucketName);
            if (!isBucketExists) {
                amazonClient.createBucket(bucketName);
            }
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.serviceErrorOfCause(OssfileErrorStatus.OSSFILE_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>bucketPolicy</code>
     * <p>The bucket policy method.</p>
     * @return {@link com.amazonaws.services.s3.model.BucketPolicy} <p>The bucket policy return object is <code>BucketPolicy</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see com.amazonaws.services.s3.model.BucketPolicy
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static BucketPolicy bucketPolicy() throws ServiceErrorException {
        return bucketPolicy(OssfileStoreHolder.defaultBucket());
    }

    /**
     * <code>bucketPolicy</code>
     * <p>The bucket policy method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.BucketPolicy} <p>The bucket policy return object is <code>BucketPolicy</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.BucketPolicy
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static BucketPolicy bucketPolicy(String bucketName) throws ServiceErrorException {
        try {
            return AmazonContextHolder.defaultClient().getBucketPolicy(bucketName);
        } catch (SdkClientException exception) {
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
            return AmazonContextHolder.defaultClient().listBuckets();
        } catch (SdkClientException exception) {
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
        return listBuckets().stream().filter(bucket -> bucket.getName().equals(bucketName)).findFirst();
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
        initDefaultBucket(AmazonContextHolder.defaultClient(), bucketName);
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
            AmazonContextHolder.defaultClient().createBucket(bucketName);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.serviceErrorOfCause(OssfileErrorStatus.OSSFILE_MAKE_BUCKET_ERROR, exception);
        }
    }

    /**
     * <code>deleteBucket</code>
     * <p>The delete bucket method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @throws ServiceErrorException {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error exception is <code>ServiceErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static void deleteBucket(String bucketName) throws ServiceErrorException {
        try {
            AmazonContextHolder.defaultClient().deleteBucket(bucketName);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.serviceErrorOfCause(OssfileErrorStatus.OSSFILE_REMOVE_BUCKET_ERROR, exception);
        }
    }

    /**
     * <code>statObject</code>
     * <p>The stat object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The stat object return object is <code>ObjectMetadata</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectMetadata statObject(String objectName) throws FileErrorException {
        return statObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>statObject</code>
     * <p>The stat object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The stat object return object is <code>ObjectMetadata</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectMetadata statObject(String bucketName, String objectName) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().getObjectMetadata(bucketName, objectName);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_STAT_OBJECT_ERROR, exception);
        }
    }


    /**
     * <code>isObjectExist</code>
     * <p>The is object exist method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return boolean <p>The is object exist return object is <code>boolean</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static boolean isObjectExist(String objectName) throws FileErrorException {
        return isObjectExist(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>isObjectExist</code>
     * <p>The is object exist method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return boolean <p>The is object exist return object is <code>boolean</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static boolean isObjectExist(String bucketName, String objectName) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().doesObjectExist(bucketName, objectName);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_STAT_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>listObjects</code>
     * <p>The list objects method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.ObjectListing} <p>The list objects return object is <code>ObjectListing</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectListing
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectListing listObjects(String prefix) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().listObjects(OssfileStoreHolder.defaultBucket(), prefix);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception);
        }
    }

    /**
     * <code>listObjects</code>
     * <p>The list objects method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param prefix     {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.ObjectListing} <p>The list objects return object is <code>ObjectListing</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectListing
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectListing listObjects(String bucketName, String prefix) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().listObjects(bucketName, prefix);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception);
        }
    }

    /**
     * <code>listObjects</code>
     * <p>The list objects method.</p>
     * @param prefix    {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param marker    {@link java.lang.String} <p>The marker parameter is <code>String</code> type.</p>
     * @param delimiter {@link java.lang.String} <p>The delimiter parameter is <code>String</code> type.</p>
     * @param maxKeys   {@link java.lang.Integer} <p>The max keys parameter is <code>Integer</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.ObjectListing} <p>The list objects return object is <code>ObjectListing</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     * @see com.amazonaws.services.s3.model.ObjectListing
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectListing listObjects(String prefix, String marker, String delimiter, Integer maxKeys) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().listObjects(new ListObjectsRequest(OssfileStoreHolder.defaultBucket(), prefix, marker, delimiter, maxKeys));
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception);
        }
    }

    /**
     * <code>listObjects</code>
     * <p>The list objects method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param prefix     {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @param marker     {@link java.lang.String} <p>The marker parameter is <code>String</code> type.</p>
     * @param delimiter  {@link java.lang.String} <p>The delimiter parameter is <code>String</code> type.</p>
     * @param maxKeys    {@link java.lang.Integer} <p>The max keys parameter is <code>Integer</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.ObjectListing} <p>The list objects return object is <code>ObjectListing</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Integer
     * @see com.amazonaws.services.s3.model.ObjectListing
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static ObjectListing listObjects(String bucketName, String prefix, String marker, String delimiter, Integer maxKeys) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().listObjects(new ListObjectsRequest(bucketName, prefix, marker, delimiter, maxKeys));
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception);
        }
    }


    /**
     * <code>allObjects</code>
     * <p>The all objects method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.util.List} <p>The all objects return object is <code>List</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.List
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static List<S3ObjectSummary> allObjects(String prefix) throws FileErrorException {
        return allObjects(OssfileStoreHolder.defaultBucket(), prefix);
    }

    /**
     * <code>allObjects</code>
     * <p>The all objects method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param prefix     {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.util.List} <p>The all objects return object is <code>List</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.List
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static List<S3ObjectSummary> allObjects(String bucketName, String prefix) throws FileErrorException {
        ObjectListing objectListing = listObjects(bucketName, prefix);
        if (GeneralUtils.isNotEmpty(objectListing.getObjectSummaries())) {
            return objectListing.getObjectSummaries();
        }
        return Collections.emptyList();
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.S3Object} <p>The get object return object is <code>S3Object</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.S3Object
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static S3Object getObject(String objectName) throws FileErrorException {
        return getObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.S3Object} <p>The get object return object is <code>S3Object</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.S3Object
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static S3Object getObject(String bucketName, String objectName) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().getObject(bucketName, objectName);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param start      long <p>The start parameter is <code>long</code> type.</p>
     * @param end        long <p>The end parameter is <code>long</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.S3Object} <p>The get object return object is <code>S3Object</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.S3Object
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static S3Object getObject(String objectName, long start, long end) throws FileErrorException {
        return getObject(OssfileStoreHolder.defaultBucket(), objectName, start, end);
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param start      long <p>The start parameter is <code>long</code> type.</p>
     * @param end        long <p>The end parameter is <code>long</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.S3Object} <p>The get object return object is <code>S3Object</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.S3Object
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static S3Object getObject(String bucketName, String objectName, long start, long end) throws FileErrorException {
        try {
            GetObjectRequest objectRequest = new GetObjectRequest(bucketName, objectName);
            objectRequest.setRange(start, end);
            return AmazonContextHolder.defaultClient().getObject(objectRequest);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param file           {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @param objectName     {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param objectMetadata {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The object metadata parameter is <code>ObjectMetadata</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The put object return object is <code>PutObjectResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see org.springframework.web.multipart.MultipartFile
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see com.amazonaws.services.s3.model.PutObjectResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static PutObjectResult putObject(MultipartFile file, String objectName, ObjectMetadata objectMetadata) throws FileErrorException {
        return putObject(OssfileStoreHolder.defaultBucket(), file, objectName, objectMetadata);
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param bucketName     {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param file           {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @param objectName     {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param objectMetadata {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The object metadata parameter is <code>ObjectMetadata</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The put object return object is <code>PutObjectResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.web.multipart.MultipartFile
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see com.amazonaws.services.s3.model.PutObjectResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static PutObjectResult putObject(String bucketName, MultipartFile file, String objectName, ObjectMetadata objectMetadata) throws FileErrorException {
        try {
            InputStream inputStream = file.getInputStream();
            return AmazonContextHolder.defaultClient().putObject(bucketName, objectName, inputStream, objectMetadata);
        } catch (IOException | SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The put object return object is <code>PutObjectResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.PutObjectResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static PutObjectResult putObject(String objectName, InputStream inputStream) throws FileErrorException {
        return putObject(OssfileStoreHolder.defaultBucket(), objectName, inputStream);
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The put object return object is <code>PutObjectResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.PutObjectResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static PutObjectResult putObject(String bucketName, String objectName, InputStream inputStream) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().putObject(bucketName, objectName, inputStream, null);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param metadata   {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The metadata parameter is <code>ObjectMetadata</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see org.springframework.lang.Nullable
     * @see com.amazonaws.services.s3.model.InitiateMultipartUploadResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static InitiateMultipartUploadResult initiateMultipart(String objectName, @Nullable ObjectMetadata metadata) throws FileErrorException {
        return initiateMultipart(OssfileStoreHolder.defaultBucket(), objectName, metadata);
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param metadata   {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The metadata parameter is <code>ObjectMetadata</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see org.springframework.lang.Nullable
     * @see com.amazonaws.services.s3.model.InitiateMultipartUploadResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable ObjectMetadata metadata) throws FileErrorException {
        try {
            InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, objectName);
            Optional.ofNullable(metadata).ifPresent(initiateMultipartUploadRequest::setObjectMetadata);
            return AmazonContextHolder.defaultClient().initiateMultipartUpload(initiateMultipartUploadRequest);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_INITIATE_MULTIPART_ERROR, exception);
        }
    }

    /**
     * <code>completeMultipart</code>
     * <p>The complete multipart method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId   {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partETags  {@link java.util.Collection} <p>The part e tags parameter is <code>Collection</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.CompleteMultipartUploadResult} <p>The complete multipart return object is <code>CompleteMultipartUploadResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see com.amazonaws.services.s3.model.CompleteMultipartUploadResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static CompleteMultipartUploadResult completeMultipart(String objectName, String uploadId, Collection<PartETag> partETags) throws FileErrorException {
        return completeMultipart(OssfileStoreHolder.defaultBucket(), objectName, uploadId, partETags);
    }

    /**
     * <code>completeMultipart</code>
     * <p>The complete multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId   {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partETags  {@link java.util.Collection} <p>The part e tags parameter is <code>Collection</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.CompleteMultipartUploadResult} <p>The complete multipart return object is <code>CompleteMultipartUploadResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see com.amazonaws.services.s3.model.CompleteMultipartUploadResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static CompleteMultipartUploadResult completeMultipart(String bucketName, String objectName, String uploadId, Collection<PartETag> partETags) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().completeMultipartUpload(new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, new ArrayList<>(partETags)));
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_COMPLETE_MULTIPART_ERROR, exception);
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
     * @return {@link com.amazonaws.services.s3.model.UploadPartResult} <p>The upload multipart return object is <code>UploadPartResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.UploadPartResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static UploadPartResult uploadMultipart(String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws FileErrorException {
        return uploadMultipart(OssfileStoreHolder.defaultBucket(), objectName, uploadId, partIndex, inputStream, partSize);
    }

    /**
     * <code>uploadMultipart</code>
     * <p>The upload multipart method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId    {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partIndex   int <p>The part index parameter is <code>int</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partSize    long <p>The part size parameter is <code>long</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.UploadPartResult} <p>The upload multipart return object is <code>UploadPartResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.UploadPartResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static UploadPartResult uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws FileErrorException {
        try {
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(objectName);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setPartNumber(partIndex);
            uploadPartRequest.setInputStream(inputStream);
            uploadPartRequest.setPartSize(partSize);
            return AmazonContextHolder.defaultClient().uploadPart(uploadPartRequest);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_UPLOAD_MULTIPART_ERROR, exception);
        }
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The put object return object is <code>PutObjectResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.PutObjectResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static PutObjectResult putObject(String objectName, String filename) throws FileErrorException {
        return putObject(OssfileStoreHolder.defaultBucket(), objectName, filename);
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The put object return object is <code>PutObjectResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.PutObjectResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static PutObjectResult putObject(String bucketName, String objectName, String filename) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().putObject(bucketName, objectName, filename);
        } catch (Throwable exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_UPLOAD_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>appendObject</code>
     * <p>The append object method.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param metadata    {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The metadata parameter is <code>ObjectMetadata</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The append object return object is <code>PutObjectResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see org.springframework.lang.Nullable
     * @see com.amazonaws.services.s3.model.PutObjectResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static PutObjectResult appendObject(String objectName, InputStream inputStream, @Nullable ObjectMetadata metadata) throws FileErrorException {
        return appendObject(OssfileStoreHolder.defaultBucket(), objectName, inputStream, metadata);
    }

    /**
     * <code>appendObject</code>
     * <p>The append object method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param metadata    {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The metadata parameter is <code>ObjectMetadata</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The append object return object is <code>PutObjectResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see org.springframework.lang.Nullable
     * @see com.amazonaws.services.s3.model.PutObjectResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static PutObjectResult appendObject(String bucketName, String objectName, InputStream inputStream, @Nullable ObjectMetadata metadata) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().putObject(bucketName, objectName, inputStream, metadata);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_APPEND_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>copyObject</code>
     * <p>The copy object method.</p>
     * @param sourceObjectName {@link java.lang.String} <p>The source object name parameter is <code>String</code> type.</p>
     * @param targetObjectName {@link java.lang.String} <p>The target object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.CopyObjectResult} <p>The copy object return object is <code>CopyObjectResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.CopyObjectResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static CopyObjectResult copyObject(String sourceObjectName, String targetObjectName) throws FileErrorException {
        return copyObject(OssfileStoreHolder.defaultBucket(), sourceObjectName, OssfileStoreHolder.defaultBucket(), targetObjectName);
    }

    /**
     * <code>copyObject</code>
     * <p>The copy object method.</p>
     * @param sourceBucketName {@link java.lang.String} <p>The source bucket name parameter is <code>String</code> type.</p>
     * @param sourceObjectName {@link java.lang.String} <p>The source object name parameter is <code>String</code> type.</p>
     * @param targetBucketName {@link java.lang.String} <p>The target bucket name parameter is <code>String</code> type.</p>
     * @param targetObjectName {@link java.lang.String} <p>The target object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.CopyObjectResult} <p>The copy object return object is <code>CopyObjectResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.CopyObjectResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static CopyObjectResult copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().copyObject(sourceBucketName, sourceObjectName, targetBucketName, targetObjectName);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_COPY_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>deleteObject</code>
     * <p>The delete object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static void deleteObject(String objectName) throws FileErrorException {
        deleteObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>deleteObject</code>
     * <p>The delete object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static void deleteObject(String bucketName, String objectName) throws FileErrorException {
        try {
            AmazonContextHolder.defaultClient().deleteObject(bucketName, objectName);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_REMOVE_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>deleteObjects</code>
     * <p>The delete objects method.</p>
     * @param objectNames {@link java.util.Collection} <p>The object names parameter is <code>Collection</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static void deleteObjects(Collection<String> objectNames) throws FileErrorException {
        deleteObjects(OssfileStoreHolder.defaultBucket(), objectNames);
    }

    /**
     * <code>deleteObjects</code>
     * <p>The delete objects method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectNames {@link java.util.Collection} <p>The object names parameter is <code>Collection</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static void deleteObjects(String bucketName, Collection<String> objectNames) throws FileErrorException {
        for (String objectName : objectNames) {
            deleteObject(bucketName, objectName);
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
     * @see org.springframework.lang.Nullable
     * @see java.net.URL
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static URL objectUrl(String objectName, @Nullable Integer expire) throws FileErrorException {
        return objectUrl(OssfileStoreHolder.defaultBucket(), objectName, expire);
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
     * @see org.springframework.lang.Nullable
     * @see java.net.URL
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static URL objectUrl(String bucketName, String objectName, @Nullable Integer expire) throws FileErrorException {
        try {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectName);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            int offsetExpire = RestOptional.ofEmptyable(expire).orElse(Math.toIntExact(TimeUnit.DAYS.toSeconds(7L)));
            Date offsetDate = new Date(new Date().getTime() + offsetExpire);
            generatePresignedUrlRequest.setExpiration(offsetDate);
            return AmazonContextHolder.defaultClient().generatePresignedUrl(generatePresignedUrlRequest);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PRESIGNED_OBJECT_URL_ERROR, exception);
        }
    }

    /**
     * <code>objectUrl</code>
     * <p>The object url method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param expire     {@link java.util.Date} <p>The expire parameter is <code>Date</code> type.</p>
     * @return {@link java.net.URL} <p>The object url return object is <code>URL</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.util.Date
     * @see java.net.URL
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static URL objectUrl(String bucketName, String objectName, Date expire) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().generatePresignedUrl(bucketName, objectName, expire);
        } catch (SdkClientException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PRESIGNED_OBJECT_URL_ERROR, exception);
        }
    }

}
