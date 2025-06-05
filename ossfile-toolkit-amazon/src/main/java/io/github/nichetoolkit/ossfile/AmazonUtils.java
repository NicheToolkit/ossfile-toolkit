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

@Slf4j
public class AmazonUtils {

    public static void initDefaultBucket(AmazonS3 amazonClient, String bucketName) {
        try {
            AmazonHelper.initDefaultBucket(amazonClient, bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server connect has error: {}, bucket: {}", exception.getMessage(), bucketName);
            GeneralUtils.printStackTrace(exception);
        }
    }

    public static BucketPolicy bucketPolicy() {
        return bucketPolicy(AmazonContextHolder.defaultBucket());
    }

    public static BucketPolicy bucketPolicy(String bucketName) {
        try {
            return AmazonHelper.bucketPolicy(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server get bucket policy has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static List<Bucket> listBuckets() {
        try {
            return AmazonHelper.listBuckets();
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server get all buckets has error: {}", exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return Collections.emptyList();
        }
    }

    public static Optional<Bucket> getBucket() {
        return getBucket(AmazonContextHolder.defaultBucket());
    }

    public static Optional<Bucket> getBucket(String bucketName) {
        try {
            return AmazonHelper.getBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server get bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return Optional.empty();
        }
    }

    public static void switchBucket(String bucketName) {
        try {
            AmazonHelper.switchBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server switch bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
        }
    }

    public static void makeBucket(String bucketName) {
        try {
            AmazonHelper.makeBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server make bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
        }
    }

    public static void deleteBucket(String bucketName) {
        try {
            AmazonHelper.deleteBucket(bucketName);
        } catch (ServiceErrorException exception) {
            log.error("the amazon ossfile server delete bucket has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
        }
    }

    public static ObjectMetadata statObject(String objectName) {
        return statObject(AmazonContextHolder.defaultBucket(), objectName);
    }

    public static ObjectMetadata statObject(String bucketName, String objectName) {
        try {
            return AmazonHelper.statObject(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server stat object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static boolean isObjectExist(String objectName) {
        return isObjectExist(AmazonContextHolder.defaultBucket(), objectName);
    }

    public static boolean isObjectExist(String bucketName, String objectName) {
        try {
            return AmazonHelper.isObjectExist(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server exist object has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return false;
        }
    }

    public static ObjectListing listObjects(String prefix) {
        return listObjects(AmazonContextHolder.defaultBucket(), prefix);
    }

    public static ObjectListing listObjects(String bucketName, String prefix) {
        try {
            return AmazonHelper.listObjects(bucketName, prefix);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server list object has error, bucket: {}, error: {}", bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static List<S3ObjectSummary> allObjects(String prefix) {
        return allObjects(AmazonContextHolder.defaultBucket(), prefix);
    }

    public static List<S3ObjectSummary> allObjects(String bucketName, String prefix) {
        try {
            return AmazonHelper.allObjects(bucketName, prefix);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server all objects has error, prefix: {}, error: {}", prefix, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return Collections.emptyList();
        }
    }

    public static S3Object getObject(String objectName) {
        return getObject(AmazonContextHolder.defaultBucket(), objectName);
    }

    public static S3Object getObject(String bucketName, String objectName) {
        try {
            return AmazonHelper.getObject(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server get object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static S3Object getObject(String objectName, long start, long end) {
        return getObject(AmazonContextHolder.defaultBucket(), objectName, start, end);
    }

    public static S3Object getObject(String bucketName, String objectName, long start, long end) {
        try {
            return AmazonHelper.getObject(bucketName, objectName, start, end);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server get object with start and end has error, range: [ {} , {} ], object: {}, bucket: {} error: {}", start, end, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static PutObjectResult putObject(MultipartFile file, String objectName, ObjectMetadata objectMetadata) {
        return putObject(AmazonContextHolder.defaultBucket(), file, objectName, objectMetadata);
    }

    public static PutObjectResult putObject(String bucketName, MultipartFile file, String objectName, ObjectMetadata objectMetadata) {
        try {
            return AmazonHelper.putObject(bucketName, file, objectName, objectMetadata);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server put object has error with object metadata, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static PutObjectResult putObject(String objectName, InputStream inputStream) {
        return putObject(AmazonContextHolder.defaultBucket(), objectName, inputStream);
    }

    public static PutObjectResult putObject(String bucketName, String objectName, InputStream inputStream) {
        try {
            return AmazonHelper.putObject(bucketName, objectName, inputStream);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server put object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static InitiateMultipartUploadResult initiateMultipart(String objectName, @Nullable ObjectMetadata metadata) {
        return initiateMultipart(AmazonContextHolder.defaultBucket(), objectName, metadata);
    }

    public static InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable ObjectMetadata metadata) {
        try {
            return AmazonHelper.initiateMultipart(bucketName, objectName, metadata);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server initiate multipart has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static UploadPartResult uploadMultipart(String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) {
        return uploadMultipart(AmazonContextHolder.defaultBucket(), objectName, uploadId, partIndex, inputStream, partSize);
    }

    public static UploadPartResult uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) {
        try {
            return AmazonHelper.uploadMultipart(bucketName, objectName, uploadId, partIndex, inputStream, partSize);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server upload multipart has error, uploadId: {}, object: {}, bucket: {}, error: {}", uploadId, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static CompleteMultipartUploadResult completeMultipart(String objectName, String uploadId, Collection<PartETag> partETags) {
        return completeMultipart(AmazonContextHolder.defaultBucket(), objectName, uploadId, partETags);
    }

    public static CompleteMultipartUploadResult completeMultipart(String bucketName, String objectName, String uploadId, Collection<PartETag> partETags) {
        try {
            return AmazonHelper.completeMultipart(bucketName, objectName, uploadId, partETags);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server complete multipart has error, uploadId：{}, object: {}, bucket: {}, error: {}", uploadId, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static PutObjectResult uploadObject(String objectName, String filename) {
        return uploadObject(AmazonContextHolder.defaultBucket(),objectName, filename);
    }

    public static PutObjectResult uploadObject(String bucketName, String objectName, String filename) {
        try {
            return AmazonHelper.putObject(bucketName, objectName, filename);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server upload object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static PutObjectResult appendObject(String objectName, InputStream inputStream, @Nullable ObjectMetadata metadata) {
        return appendObject(AmazonContextHolder.defaultBucket(), objectName, inputStream, metadata);
    }

    public static PutObjectResult appendObject(String bucketName, String objectName, InputStream inputStream, @Nullable ObjectMetadata metadata) {
        try {
            return AmazonHelper.appendObject(bucketName, objectName, inputStream, metadata);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server append object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static CopyObjectResult copyObject(String sourceObjectName, String targetObjectName) {
        return copyObject(AmazonContextHolder.defaultBucket(), sourceObjectName, AmazonContextHolder.defaultBucket(), targetObjectName);
    }

    public static CopyObjectResult copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) {
        try {
            return AmazonHelper.copyObject(sourceBucketName, sourceObjectName, targetBucketName, targetObjectName);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server copy object has error, target object: {}, target bucket: {}, source object: {}, source bucket: {}, error: {}", targetObjectName, targetBucketName, sourceObjectName, sourceBucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

    public static void deleteObject(String objectName) {
        deleteObject(AmazonContextHolder.defaultBucket(), objectName);
    }

    public static void deleteObject(String bucketName, String objectName) {
        try {
            AmazonHelper.deleteObject(bucketName, objectName);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server delete object has error, object: {}, bucket: {}, error: {}", objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
        }
    }

    public static void removeObjects(Collection<String> objectNames) {
        deleteObjects(AmazonContextHolder.defaultBucket(), objectNames);
    }

    public static void deleteObjects(String bucketName, Collection<String> objectNames) {
        objectNames.forEach(objectName -> deleteObject(bucketName, objectName));
    }

    public static URL objectUrl(String objectName, Integer expire) {
        return objectUrl(AmazonContextHolder.defaultBucket(), objectName, expire);
    }

    public static URL objectUrl(String bucketName, String objectName, Integer expire) {
        try {
            return AmazonHelper.objectUrl(bucketName, objectName, expire);
        } catch (FileErrorException exception) {
            log.error("the amazon ossfile server presigned object url has error, expire: {}, object: {}, bucket: {}, error: {}", expire, objectName, bucketName, exception.getMessage());
            GeneralUtils.printStackTrace(exception);
            return null;
        }
    }

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
