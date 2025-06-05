package io.github.nichetoolkit.ossfile;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AliyunHelper {

    public static OSS createAliyunClient(OssfileProperties ossfileProperties) {
        return new OSSClientBuilder()
                .build(ossfileProperties.getEndpoint(), ossfileProperties.getAccessKey(), ossfileProperties.getSecretKey());
    }

    public static void initDefaultBucket(OSS aliyunClient, String bucketName) throws ServiceErrorException {
        try {
            boolean isBucketExists = aliyunClient.doesBucketExist(bucketName);
            if (!isBucketExists) {
                aliyunClient.createBucket(bucketName);
            }
        } catch (OSSException | ClientException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_CONFIG_ERROR, exception.getMessage());
        }
    }

    public static GetBucketPolicyResult bucketPolicy() throws ServiceErrorException {
        return bucketPolicy(AliyunContextHolder.defaultBucket());
    }

    public static GetBucketPolicyResult bucketPolicy(String bucketName) throws ServiceErrorException {
        try {
            return AliyunContextHolder.defaultClient().getBucketPolicy(bucketName);
        } catch (OSSException | ClientException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_BUCKET_POLICY_ERROR, exception.getMessage());
        }
    }

    public static List<Bucket> listBuckets() throws ServiceErrorException {
        try {
            return AliyunContextHolder.defaultClient().listBuckets();
        } catch (OSSException | ClientException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }

    public static Optional<Bucket> getBucket() throws ServiceErrorException {
        return getBucket(AliyunContextHolder.defaultBucket());
    }

    public static Optional<Bucket> getBucket(String bucketName) throws ServiceErrorException {
        return listBuckets().stream().filter(bucket -> bucket.getName().equals(bucketName)).findFirst();
    }

    public static void switchBucket(String bucketName) throws ServiceErrorException {
        AliyunContextHolder.switchBucket(bucketName);
        initDefaultBucket(AliyunContextHolder.defaultClient(), bucketName);
    }

    public static void makeBucket(String bucketName) throws ServiceErrorException {
        try {
            AliyunContextHolder.defaultClient().createBucket(bucketName);
        } catch (OSSException | ClientException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_MAKE_BUCKET_ERROR, exception.getMessage());
        }
    }

    public static void deleteBucket(String bucketName) throws ServiceErrorException {
        try {
            AliyunContextHolder.defaultClient().deleteBucket(bucketName);
        } catch (OSSException | ClientException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_REMOVE_BUCKET_ERROR, exception.getMessage());
        }
    }

    public static SimplifiedObjectMeta statObject(String objectName) throws FileErrorException {
        return statObject(AliyunContextHolder.defaultBucket(), objectName);
    }

    public static SimplifiedObjectMeta statObject(String bucketName, String objectName) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().getSimplifiedObjectMeta(bucketName,objectName);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_STAT_OBJECT_ERROR, exception.getMessage());
        }
    }


    public static boolean isObjectExist(String objectName) throws FileErrorException {
        return isObjectExist(AliyunContextHolder.defaultBucket(), objectName);
    }

    public static boolean isObjectExist(String bucketName, String objectName) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().doesObjectExist(bucketName, objectName);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_STAT_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static ObjectListing listObjects(String prefix) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().listObjects(AliyunContextHolder.defaultBucket(), prefix);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }

    public static ObjectListing listObjects(String bucketName, String prefix) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().listObjects(bucketName, prefix);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }

    public static ObjectListing listObjects(String prefix, String marker, String delimiter, Integer maxKeys) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().listObjects(new ListObjectsRequest(AliyunContextHolder.defaultBucket(), prefix, marker, delimiter, maxKeys));
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }

    public static ObjectListing listObjects(String bucketName, String prefix, String marker, String delimiter, Integer maxKeys) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().listObjects(new ListObjectsRequest(bucketName, prefix, marker, delimiter, maxKeys));
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }


    public static List<OSSObjectSummary> allObjects(String prefix) throws FileErrorException {
        return allObjects(AliyunContextHolder.defaultBucket(), prefix);
    }

    public static List<OSSObjectSummary> allObjects(String bucketName, String prefix) throws FileErrorException {
        ObjectListing objectListing = listObjects(bucketName, prefix);
        if (GeneralUtils.isNotEmpty(objectListing.getObjectSummaries())) {
            return objectListing.getObjectSummaries();
        }
        return Collections.emptyList();
    }

    public static OSSObject getObject(String objectName) throws FileErrorException {
        return getObject(AliyunContextHolder.defaultBucket(), objectName);
    }

    public static OSSObject getObject(String bucketName, String objectName) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().getObject(bucketName, objectName);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static OSSObject getObject(String objectName, long start, long end) throws FileErrorException {
        return getObject(AliyunContextHolder.defaultBucket(), objectName, start, end);
    }

    public static OSSObject getObject(String bucketName, String objectName, long start, long end) throws FileErrorException {
        try {
            GetObjectRequest objectRequest = new GetObjectRequest(bucketName, objectName);
            objectRequest.setRange(start, end);
            return AliyunContextHolder.defaultClient().getObject(objectRequest);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static PutObjectResult putObject(MultipartFile file, String objectName, ObjectMetadata objectMetadata) throws FileErrorException {
        return putObject(AliyunContextHolder.defaultBucket(), file, objectName, objectMetadata);
    }

    public static PutObjectResult putObject(String bucketName, MultipartFile file, String objectName, ObjectMetadata objectMetadata) throws FileErrorException {
        try {
            InputStream inputStream = file.getInputStream();
            return AliyunContextHolder.defaultClient().putObject(bucketName, objectName, inputStream, objectMetadata);
        } catch (IOException | OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception.getMessage());
        }
    }


    public static PutObjectResult putObject(String objectName, InputStream inputStream) throws FileErrorException {
        return putObject(AliyunContextHolder.defaultBucket(), objectName, inputStream);
    }

    public static PutObjectResult putObject(String bucketName, String objectName, InputStream inputStream) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().putObject(bucketName, objectName, inputStream);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static InitiateMultipartUploadResult initiateMultipart(String objectName, @Nullable ObjectMetadata metadata) throws FileErrorException {
        return initiateMultipart(AliyunContextHolder.defaultBucket(), objectName, metadata);
    }

    public static InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable ObjectMetadata metadata) throws FileErrorException {
        try {
            InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, objectName);
            Optional.ofNullable(metadata).ifPresent(initiateMultipartUploadRequest::setObjectMetadata);
            return AliyunContextHolder.defaultClient().initiateMultipartUpload(initiateMultipartUploadRequest);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_INITIATE_MULTIPART_ERROR, exception.getMessage());
        }
    }

    public static CompleteMultipartUploadResult completeMultipart(String objectName, String uploadId, Collection<PartETag> partETags) throws FileErrorException {
        return completeMultipart(AliyunContextHolder.defaultBucket(), objectName, uploadId, partETags);
    }

    public static CompleteMultipartUploadResult completeMultipart(String bucketName, String objectName, String uploadId, Collection<PartETag> partETags) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().completeMultipartUpload(new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, new ArrayList<>(partETags)));
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_COMPLETE_MULTIPART_ERROR, exception.getMessage());
        }
    }

    public static UploadPartResult uploadMultipart(String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws FileErrorException {
        return uploadMultipart(AliyunContextHolder.defaultBucket(), objectName, uploadId, partIndex, inputStream, partSize);
    }


    public static UploadPartResult uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().uploadPart(new UploadPartRequest(bucketName, objectName, uploadId, partIndex, inputStream, partSize));
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_UPLOAD_MULTIPART_ERROR, exception.getMessage());
        }
    }

    public static UploadFileResult uploadObject(String objectName, String filename) throws FileErrorException {
        return uploadObject(AliyunContextHolder.defaultBucket(), objectName, filename, 0L, 0);
    }

    public static UploadFileResult uploadObject(String bucketName, String objectName, String filename) throws FileErrorException {
        return uploadObject(bucketName, objectName, filename, 0L, 0);
    }

    public static UploadFileResult uploadObject(String bucketName, String objectName, String filename, long partSize, int taskNum) throws FileErrorException {
        try {
            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, objectName);
            uploadFileRequest.setUploadFile(filename);
            RestOptional.ofEmptyable(partSize).ifEmptyPresent(uploadFileRequest::setPartSize);
            RestOptional.ofEmptyable(taskNum).ifEmptyPresent(uploadFileRequest::setTaskNum);
            return AliyunContextHolder.defaultClient().uploadFile(uploadFileRequest);
        } catch (Throwable exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_UPLOAD_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static AppendObjectResult appendObject(String objectName, InputStream inputStream, @Nullable ObjectMetadata metadata) throws FileErrorException {
        return appendObject(AliyunContextHolder.defaultBucket(), objectName, inputStream, metadata);
    }

    public static AppendObjectResult appendObject(String bucketName, String objectName, InputStream inputStream, @Nullable ObjectMetadata metadata) throws FileErrorException {
        try {
            AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, objectName, inputStream);
            Optional.ofNullable(metadata).ifPresent(appendObjectRequest::setMetadata);
            return AliyunContextHolder.defaultClient().appendObject(appendObjectRequest);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_APPEND_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static CopyObjectResult copyObject(String sourceObjectName, String targetObjectName) throws FileErrorException {
        return copyObject(AliyunContextHolder.defaultBucket(), sourceObjectName, AliyunContextHolder.defaultBucket(), targetObjectName);
    }

    public static CopyObjectResult copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().copyObject(sourceBucketName, sourceObjectName, targetBucketName, targetObjectName);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_COPY_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static void deleteObject(String objectName) throws FileErrorException {
        deleteObject(AliyunContextHolder.defaultBucket(), objectName);
    }

    public static void deleteObject(String bucketName, String objectName) throws FileErrorException {
        try {
            AliyunContextHolder.defaultClient().deleteObject(bucketName, objectName);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_REMOVE_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static void deleteObjects(Collection<String> objectNames) throws FileErrorException {
        deleteObjects(AliyunContextHolder.defaultBucket(), objectNames);
    }

    public static void deleteObjects(String bucketName, Collection<String> objectNames) throws FileErrorException {
        for (String objectName : objectNames) {
            deleteObject(bucketName, objectName);
        }
    }

    public static URL objectUrl(String objectName, @Nullable Integer expire) throws FileErrorException {
        return objectUrl(AliyunContextHolder.defaultBucket(), objectName, expire);
    }

    public static URL objectUrl(String bucketName, String objectName, @Nullable Integer expire) throws FileErrorException {
        try {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectName);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            int offsetExpire = RestOptional.ofEmptyable(expire).orElse(Math.toIntExact(TimeUnit.DAYS.toSeconds(7L)));
            Date offsetDate = new Date(new Date().getTime() + offsetExpire);
            generatePresignedUrlRequest.setExpiration(offsetDate);
            return AliyunContextHolder.defaultClient().generatePresignedUrl(generatePresignedUrlRequest);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PRESIGNED_OBJECT_URL_ERROR, exception.getMessage());
        }
    }

    public static URL objectUrl(String bucketName, String objectName, Date expire) throws FileErrorException {
        try {
            return AliyunContextHolder.defaultClient().generatePresignedUrl(bucketName, objectName, expire);
        } catch (OSSException | ClientException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PRESIGNED_OBJECT_URL_ERROR, exception.getMessage());
        }
    }

}
