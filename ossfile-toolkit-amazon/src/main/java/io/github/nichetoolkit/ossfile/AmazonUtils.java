package io.github.nichetoolkit.ossfile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * <code>AmazonUtils</code>
 * <p>The amazon utils class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public class AmazonUtils {

    /**
     * <code>initDefaultBucket</code>
     * <p>The init default bucket method.</p>
     * @param amazonClient {@link com.amazonaws.services.s3.AmazonS3} <p>The amazon client parameter is <code>AmazonS3</code> type.</p>
     * @param bucketName   {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @see com.amazonaws.services.s3.AmazonS3
     * @see java.lang.String
     */
    public static void initDefaultBucket(AmazonS3 amazonClient, String bucketName) {
        try {
            AmazonHelper.initDefaultBucket(amazonClient, bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server connect has error: {}, bucket: {}", exception.getMessage(), bucketName);
            GeneralUtils.printStackTrace(exception);
        }
    }

    /**
     * <code>bucketPolicy</code>
     * <p>The bucket policy method.</p>
     * @return {@link com.amazonaws.services.s3.model.BucketPolicy} <p>The bucket policy return object is <code>BucketPolicy</code> type.</p>
     * @see com.amazonaws.services.s3.model.BucketPolicy
     */
    public static BucketPolicy bucketPolicy() {
        return bucketPolicy(OssfileStoreHolder.defaultBucket());
    }

    /**
     * <code>bucketPolicy</code>
     * <p>The bucket policy method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.BucketPolicy} <p>The bucket policy return object is <code>BucketPolicy</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.BucketPolicy
     */
    public static BucketPolicy bucketPolicy(String bucketName) {
        try {
            return AmazonHelper.bucketPolicy(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server get bucket policy has error, bucket: {}, error: {}", bucketName, exception.getMessage());
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
            return AmazonHelper.listBuckets();
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server get all buckets has error: {}", exception.getMessage());
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
            return AmazonHelper.getBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server get bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
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
            AmazonHelper.switchBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server switch bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
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
            AmazonHelper.makeBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server make bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
        }
    }

    /**
     * <code>deleteBucket</code>
     * <p>The delete bucket method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static void deleteBucket(String bucketName) {
        try {
            AmazonHelper.deleteBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server delete bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
        }
    }

    /**
     * <code>statObject</code>
     * <p>The stat object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The stat object return object is <code>ObjectMetadata</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     */
    public static ObjectMetadata statObject(String objectName) {
        return statObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>statObject</code>
     * <p>The stat object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The stat object return object is <code>ObjectMetadata</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     */
    public static ObjectMetadata statObject(String bucketName, String objectName) {
        try {
            return AmazonHelper.statObject(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server stat object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
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
        try {
            return AmazonHelper.isObjectExist(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server exist object has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return false;
        }
    }

    /**
     * <code>listObjects</code>
     * <p>The list objects method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.ObjectListing} <p>The list objects return object is <code>ObjectListing</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectListing
     */
    public static ObjectListing listObjects(String prefix) {
        return listObjects(OssfileStoreHolder.defaultBucket(), prefix);
    }

    /**
     * <code>listObjects</code>
     * <p>The list objects method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param prefix     {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.ObjectListing} <p>The list objects return object is <code>ObjectListing</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectListing
     */
    public static ObjectListing listObjects(String bucketName, String prefix) {
        try {
            return AmazonHelper.listObjects(bucketName, prefix);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server list object has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>allObjects</code>
     * <p>The all objects method.</p>
     * @param prefix {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.util.List} <p>The all objects return object is <code>List</code> type.</p>
     * @see java.lang.String
     * @see java.util.List
     */
    public static List<S3ObjectSummary> allObjects(String prefix) {
        return allObjects(OssfileStoreHolder.defaultBucket(), prefix);
    }

    /**
     * <code>allObjects</code>
     * <p>The all objects method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param prefix     {@link java.lang.String} <p>The prefix parameter is <code>String</code> type.</p>
     * @return {@link java.util.List} <p>The all objects return object is <code>List</code> type.</p>
     * @see java.lang.String
     * @see java.util.List
     */
    public static List<S3ObjectSummary> allObjects(String bucketName, String prefix) {
        try {
            return AmazonHelper.allObjects(bucketName, prefix);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server all objects has error, prefix: {}, error: {}", prefix, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return Collections.emptyList();
        }
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.S3Object} <p>The get object return object is <code>S3Object</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.S3Object
     */
    public static S3Object getObject(String objectName) {
        return getObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.S3Object} <p>The get object return object is <code>S3Object</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.S3Object
     */
    public static S3Object getObject(String bucketName, String objectName) {
        try {
            return AmazonHelper.getObject(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server get object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>getObject</code>
     * <p>The get object getter method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param start      long <p>The start parameter is <code>long</code> type.</p>
     * @param end        long <p>The end parameter is <code>long</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.S3Object} <p>The get object return object is <code>S3Object</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.S3Object
     */
    public static S3Object getObject(String objectName, long start, long end) {
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
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.S3Object
     */
    public static S3Object getObject(String bucketName, String objectName, long start, long end) {
        try {
            return AmazonHelper.getObject(bucketName, objectName, start, end);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server get object with start and end has error, range: [ {} , {} ], object: {}, bucket: {} error: {}", start, end, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param file           {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @param objectName     {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param objectMetadata {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The object metadata parameter is <code>ObjectMetadata</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The put object return object is <code>PutObjectResult</code> type.</p>
     * @see org.springframework.web.multipart.MultipartFile
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see com.amazonaws.services.s3.model.PutObjectResult
     */
    public static PutObjectResult putObject(MultipartFile file, String objectName, ObjectMetadata objectMetadata) {
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
     * @see java.lang.String
     * @see org.springframework.web.multipart.MultipartFile
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see com.amazonaws.services.s3.model.PutObjectResult
     */
    public static PutObjectResult putObject(String bucketName, MultipartFile file, String objectName, ObjectMetadata objectMetadata) {
        try {
            return AmazonHelper.putObject(bucketName, file, objectName, objectMetadata);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server put object has error with object metadata, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The put object return object is <code>PutObjectResult</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.PutObjectResult
     */
    public static PutObjectResult putObject(String objectName, InputStream inputStream) {
        return putObject(OssfileStoreHolder.defaultBucket(), objectName, inputStream);
    }

    /**
     * <code>putObject</code>
     * <p>The put object method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The put object return object is <code>PutObjectResult</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.PutObjectResult
     */
    public static PutObjectResult putObject(String bucketName, String objectName, InputStream inputStream) {
        try {
            return AmazonHelper.putObject(bucketName, objectName, inputStream);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server put object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param metadata   {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The metadata parameter is <code>ObjectMetadata</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see org.springframework.lang.Nullable
     * @see com.amazonaws.services.s3.model.InitiateMultipartUploadResult
     */
    public static InitiateMultipartUploadResult initiateMultipart(String objectName, @Nullable ObjectMetadata metadata) {
        return initiateMultipart(OssfileStoreHolder.defaultBucket(), objectName, metadata);
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param metadata   {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The metadata parameter is <code>ObjectMetadata</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see org.springframework.lang.Nullable
     * @see com.amazonaws.services.s3.model.InitiateMultipartUploadResult
     */
    public static InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable ObjectMetadata metadata) {
        try {
            return AmazonHelper.initiateMultipart(bucketName, objectName, metadata);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server initiate multipart has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
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
     * @return {@link com.amazonaws.services.s3.model.UploadPartResult} <p>The upload multipart return object is <code>UploadPartResult</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.UploadPartResult
     */
    public static UploadPartResult uploadMultipart(String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) {
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
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.UploadPartResult
     */
    public static UploadPartResult uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) {
        try {
            return AmazonHelper.uploadMultipart(bucketName, objectName, uploadId, partIndex, inputStream, partSize);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server upload multipart has error, uploadId: {}, object: {}, bucket: {}, error: {}", uploadId, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>completeMultipart</code>
     * <p>The complete multipart method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId   {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partETags  {@link java.util.Collection} <p>The part e tags parameter is <code>Collection</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.CompleteMultipartUploadResult} <p>The complete multipart return object is <code>CompleteMultipartUploadResult</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     * @see com.amazonaws.services.s3.model.CompleteMultipartUploadResult
     */
    public static CompleteMultipartUploadResult completeMultipart(String objectName, String uploadId, Collection<PartETag> partETags) {
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
     * @see java.lang.String
     * @see java.util.Collection
     * @see com.amazonaws.services.s3.model.CompleteMultipartUploadResult
     */
    public static CompleteMultipartUploadResult completeMultipart(String bucketName, String objectName, String uploadId, Collection<PartETag> partETags) {
        try {
            return AmazonHelper.completeMultipart(bucketName, objectName, uploadId, partETags);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server complete multipart has error, uploadId：{}, object: {}, bucket: {}, error: {}", uploadId, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>uploadObject</code>
     * <p>The upload object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The upload object return object is <code>PutObjectResult</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.PutObjectResult
     */
    public static PutObjectResult uploadObject(String objectName, String filename) {
        return uploadObject(OssfileStoreHolder.defaultBucket(),objectName, filename);
    }

    /**
     * <code>uploadObject</code>
     * <p>The upload object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param filename   {@link java.lang.String} <p>The filename parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The upload object return object is <code>PutObjectResult</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.PutObjectResult
     */
    public static PutObjectResult uploadObject(String bucketName, String objectName, String filename) {
        try {
            return AmazonHelper.putObject(bucketName, objectName, filename);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server upload object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>appendObject</code>
     * <p>The append object method.</p>
     * @param objectName  {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param metadata    {@link com.amazonaws.services.s3.model.ObjectMetadata} <p>The metadata parameter is <code>ObjectMetadata</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.PutObjectResult} <p>The append object return object is <code>PutObjectResult</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see org.springframework.lang.Nullable
     * @see com.amazonaws.services.s3.model.PutObjectResult
     */
    public static PutObjectResult appendObject(String objectName, InputStream inputStream, @Nullable ObjectMetadata metadata) {
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
     * @see java.lang.String
     * @see java.io.InputStream
     * @see com.amazonaws.services.s3.model.ObjectMetadata
     * @see org.springframework.lang.Nullable
     * @see com.amazonaws.services.s3.model.PutObjectResult
     */
    public static PutObjectResult appendObject(String bucketName, String objectName, InputStream inputStream, @Nullable ObjectMetadata metadata) {
        try {
            return AmazonHelper.appendObject(bucketName, objectName, inputStream, metadata);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server append object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>copyObject</code>
     * <p>The copy object method.</p>
     * @param sourceObjectName {@link java.lang.String} <p>The source object name parameter is <code>String</code> type.</p>
     * @param targetObjectName {@link java.lang.String} <p>The target object name parameter is <code>String</code> type.</p>
     * @return {@link com.amazonaws.services.s3.model.CopyObjectResult} <p>The copy object return object is <code>CopyObjectResult</code> type.</p>
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.CopyObjectResult
     */
    public static CopyObjectResult copyObject(String sourceObjectName, String targetObjectName) {
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
     * @see java.lang.String
     * @see com.amazonaws.services.s3.model.CopyObjectResult
     */
    public static CopyObjectResult copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) {
        try {
            return AmazonHelper.copyObject(sourceBucketName, sourceObjectName, targetBucketName, targetObjectName);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server copy object has error, target object: {}, target bucket: {}, source object: {}, source bucket: {}, error: {}", targetObjectName, targetBucketName, sourceObjectName, sourceBucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>deleteObject</code>
     * <p>The delete object method.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static void deleteObject(String objectName) {
        deleteObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    /**
     * <code>deleteObject</code>
     * <p>The delete object method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static void deleteObject(String bucketName, String objectName) {
        try {
            AmazonHelper.deleteObject(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server delete object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
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
        deleteObjects(OssfileStoreHolder.defaultBucket(), objectNames);
    }

    /**
     * <code>deleteObjects</code>
     * <p>The delete objects method.</p>
     * @param bucketName  {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectNames {@link java.util.Collection} <p>The object names parameter is <code>Collection</code> type.</p>
     * @see java.lang.String
     * @see java.util.Collection
     */
    public static void deleteObjects(String bucketName, Collection<String> objectNames) {
        objectNames.forEach(objectName -> deleteObject(bucketName, objectName));
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
            return AmazonHelper.objectUrl(bucketName, objectName, expire);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server presigned object url has error, expire: {}, object: {}, bucket: {}, error: {}", expire, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    /**
     * <code>objectUrl</code>
     * <p>The object url method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param expire     {@link java.util.Date} <p>The expire parameter is <code>Date</code> type.</p>
     * @return {@link java.net.URL} <p>The object url return object is <code>URL</code> type.</p>
     * @see java.lang.String
     * @see java.util.Date
     * @see java.net.URL
     */
    public static URL objectUrl(String bucketName, String objectName, Date expire) {
        try {
            return AmazonHelper.objectUrl(bucketName, objectName, expire);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server presigned object url has error with expire date, expire: {}, object: {}, bucket: {}, error: {}", expire, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }
}
