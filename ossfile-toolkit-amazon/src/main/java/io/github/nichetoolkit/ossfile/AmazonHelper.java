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

@Slf4j
public class AmazonHelper {

    public static AmazonS3 createAmazonClient(OssfileProperties ossfileProperties) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ossfileProperties.getAccessKey(), ossfileProperties.getSecretKey())))
                .withRegion(ossfileProperties.getRegion())
                .build();
    }

    public static void initDefaultBucket(AmazonS3 amazonClient, String bucketName) throws ServiceErrorException {
        try {
            boolean isBucketExists = amazonClient.doesBucketExistV2(bucketName);
            if (!isBucketExists) {
                amazonClient.createBucket(bucketName);
            }
        } catch (SdkClientException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_CONFIG_ERROR, exception.getMessage());
        }
    }

    public static BucketPolicy bucketPolicy() throws ServiceErrorException {
        return bucketPolicy(AmazonContextHolder.defaultBucket());
    }

    public static BucketPolicy bucketPolicy(String bucketName) throws ServiceErrorException {
        try {
            return AmazonContextHolder.defaultClient().getBucketPolicy(bucketName);
        } catch (SdkClientException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_BUCKET_POLICY_ERROR, exception.getMessage());
        }
    }

    public static List<Bucket> listBuckets() throws ServiceErrorException {
        try {
            return AmazonContextHolder.defaultClient().listBuckets();
        } catch (SdkClientException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }

    public static Optional<Bucket> getBucket() throws ServiceErrorException {
        return getBucket(AmazonContextHolder.defaultBucket());
    }

    public static Optional<Bucket> getBucket(String bucketName) throws ServiceErrorException {
        return listBuckets().stream().filter(bucket -> bucket.getName().equals(bucketName)).findFirst();
    }

    public static void switchBucket(String bucketName) throws ServiceErrorException {
        AmazonContextHolder.switchBucket(bucketName);
        initDefaultBucket(AmazonContextHolder.defaultClient(), bucketName);
    }

    public static void makeBucket(String bucketName) throws ServiceErrorException {
        try {
            AmazonContextHolder.defaultClient().createBucket(bucketName);
        } catch (SdkClientException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_MAKE_BUCKET_ERROR, exception.getMessage());
        }
    }

    public static void deleteBucket(String bucketName) throws ServiceErrorException {
        try {
            AmazonContextHolder.defaultClient().deleteBucket(bucketName);
        } catch (SdkClientException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_REMOVE_BUCKET_ERROR, exception.getMessage());
        }
    }

    public static ObjectMetadata statObject(String objectName) throws FileErrorException {
        return statObject(AmazonContextHolder.defaultBucket(), objectName);
    }

    public static ObjectMetadata statObject(String bucketName, String objectName) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().getObjectMetadata(bucketName, objectName);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_STAT_OBJECT_ERROR, exception.getMessage());
        }
    }


    public static boolean isObjectExist(String objectName) throws FileErrorException {
        return isObjectExist(AmazonContextHolder.defaultBucket(), objectName);
    }

    public static boolean isObjectExist(String bucketName, String objectName) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().doesObjectExist(bucketName, objectName);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_STAT_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static ObjectListing listObjects(String prefix) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().listObjects(AmazonContextHolder.defaultBucket(), prefix);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }

    public static ObjectListing listObjects(String bucketName, String prefix) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().listObjects(bucketName, prefix);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }

    public static ObjectListing listObjects(String prefix, String marker, String delimiter, Integer maxKeys) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().listObjects(new ListObjectsRequest(AmazonContextHolder.defaultBucket(), prefix, marker, delimiter, maxKeys));
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }

    public static ObjectListing listObjects(String bucketName, String prefix, String marker, String delimiter, Integer maxKeys) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().listObjects(new ListObjectsRequest(bucketName, prefix, marker, delimiter, maxKeys));
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }


    public static List<S3ObjectSummary> allObjects(String prefix) throws FileErrorException {
        return allObjects(AmazonContextHolder.defaultBucket(), prefix);
    }

    public static List<S3ObjectSummary> allObjects(String bucketName, String prefix) throws FileErrorException {
        ObjectListing objectListing = listObjects(bucketName, prefix);
        if (GeneralUtils.isNotEmpty(objectListing.getObjectSummaries())) {
            return objectListing.getObjectSummaries();
        }
        return Collections.emptyList();
    }

    public static S3Object getObject(String objectName) throws FileErrorException {
        return getObject(AmazonContextHolder.defaultBucket(), objectName);
    }

    public static S3Object getObject(String bucketName, String objectName) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().getObject(bucketName, objectName);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static S3Object getObject(String objectName, long start, long end) throws FileErrorException {
        return getObject(AmazonContextHolder.defaultBucket(), objectName, start, end);
    }

    public static S3Object getObject(String bucketName, String objectName, long start, long end) throws FileErrorException {
        try {
            GetObjectRequest objectRequest = new GetObjectRequest(bucketName, objectName);
            objectRequest.setRange(start, end);
            return AmazonContextHolder.defaultClient().getObject(objectRequest);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static PutObjectResult putObject(MultipartFile file, String objectName, ObjectMetadata objectMetadata) throws FileErrorException {
        return putObject(AmazonContextHolder.defaultBucket(), file, objectName, objectMetadata);
    }

    public static PutObjectResult putObject(String bucketName, MultipartFile file, String objectName, ObjectMetadata objectMetadata) throws FileErrorException {
        try {
            InputStream inputStream = file.getInputStream();
            return AmazonContextHolder.defaultClient().putObject(bucketName, objectName, inputStream, objectMetadata);
        } catch (IOException | SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static PutObjectResult putObject(String objectName, InputStream inputStream) throws FileErrorException {
        return putObject(AmazonContextHolder.defaultBucket(), objectName, inputStream);
    }

    public static PutObjectResult putObject(String bucketName, String objectName, InputStream inputStream) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().putObject(bucketName, objectName, inputStream, null);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static InitiateMultipartUploadResult initiateMultipart(String objectName, @Nullable ObjectMetadata metadata) throws FileErrorException {
        return initiateMultipart(AmazonContextHolder.defaultBucket(), objectName, metadata);
    }

    public static InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable ObjectMetadata metadata) throws FileErrorException {
        try {
            InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, objectName);
            Optional.ofNullable(metadata).ifPresent(initiateMultipartUploadRequest::setObjectMetadata);
            return AmazonContextHolder.defaultClient().initiateMultipartUpload(initiateMultipartUploadRequest);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_INITIATE_MULTIPART_ERROR, exception.getMessage());
        }
    }

    public static CompleteMultipartUploadResult completeMultipart(String objectName, String uploadId, Collection<PartETag> partETags) throws FileErrorException {
        return completeMultipart(AmazonContextHolder.defaultBucket(), objectName, uploadId, partETags);
    }

    public static CompleteMultipartUploadResult completeMultipart(String bucketName, String objectName, String uploadId, Collection<PartETag> partETags) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().completeMultipartUpload(new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, new ArrayList<>(partETags)));
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_COMPLETE_MULTIPART_ERROR, exception.getMessage());
        }
    }

    public static UploadPartResult uploadMultipart(String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws FileErrorException {
        return uploadMultipart(AmazonContextHolder.defaultBucket(), objectName, uploadId, partIndex, inputStream, partSize);
    }

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
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_UPLOAD_MULTIPART_ERROR, exception.getMessage());
        }
    }

    public static PutObjectResult putObject(String objectName, String filename) throws FileErrorException {
        return putObject(AmazonContextHolder.defaultBucket(), objectName, filename);
    }

    public static PutObjectResult putObject(String bucketName, String objectName, String filename) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().putObject(bucketName, objectName, filename);
        } catch (Throwable exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_UPLOAD_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static PutObjectResult appendObject(String objectName, InputStream inputStream, @Nullable ObjectMetadata metadata) throws FileErrorException {
        return appendObject(AmazonContextHolder.defaultBucket(), objectName, inputStream, metadata);
    }

    public static PutObjectResult appendObject(String bucketName, String objectName, InputStream inputStream, @Nullable ObjectMetadata metadata) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().putObject(bucketName, objectName, inputStream, metadata);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_APPEND_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static CopyObjectResult copyObject(String sourceObjectName, String targetObjectName) throws FileErrorException {
        return copyObject(AmazonContextHolder.defaultBucket(), sourceObjectName, AmazonContextHolder.defaultBucket(), targetObjectName);
    }

    public static CopyObjectResult copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().copyObject(sourceBucketName, sourceObjectName, targetBucketName, targetObjectName);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_COPY_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static void deleteObject(String objectName) throws FileErrorException {
        deleteObject(AmazonContextHolder.defaultBucket(), objectName);
    }

    public static void deleteObject(String bucketName, String objectName) throws FileErrorException {
        try {
            AmazonContextHolder.defaultClient().deleteObject(bucketName, objectName);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_REMOVE_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static void deleteObjects(Collection<String> objectNames) throws FileErrorException {
        deleteObjects(AmazonContextHolder.defaultBucket(), objectNames);
    }

    public static void deleteObjects(String bucketName, Collection<String> objectNames) throws FileErrorException {
        for (String objectName : objectNames) {
            deleteObject(bucketName, objectName);
        }
    }

    public static URL objectUrl(String objectName, @Nullable Integer expire) throws FileErrorException {
        return objectUrl(AmazonContextHolder.defaultBucket(), objectName, expire);
    }

    public static URL objectUrl(String bucketName, String objectName, @Nullable Integer expire) throws FileErrorException {
        try {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectName);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            int offsetExpire = RestOptional.ofEmptyable(expire).orElse(Math.toIntExact(TimeUnit.DAYS.toSeconds(7L)));
            Date offsetDate = new Date(new Date().getTime() + offsetExpire);
            generatePresignedUrlRequest.setExpiration(offsetDate);
            return AmazonContextHolder.defaultClient().generatePresignedUrl(generatePresignedUrlRequest);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PRESIGNED_OBJECT_URL_ERROR, exception.getMessage());
        }
    }

    public static URL objectUrl(String bucketName, String objectName, Date expire) throws FileErrorException {
        try {
            return AmazonContextHolder.defaultClient().generatePresignedUrl(bucketName, objectName, expire);
        } catch (SdkClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PRESIGNED_OBJECT_URL_ERROR, exception.getMessage());
        }
    }

}
