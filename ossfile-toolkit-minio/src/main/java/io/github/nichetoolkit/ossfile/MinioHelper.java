package io.github.nichetoolkit.ossfile;

import com.google.common.collect.Multimap;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import io.github.nichetoolkit.rest.error.natives.UnsupportedErrorException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.minio.*;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.MinioException;
import io.minio.errors.XmlParserException;
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

@Slf4j
public class MinioHelper {

    public static MinioAsyncClient createAsyncClient(OssfileProperties ossfileProperties) {
        MinioAsyncClient.Builder builder = MinioAsyncClient.builder();
        return builder
                .endpoint(ossfileProperties.getEndpoint())
                .credentials(ossfileProperties.getAccessKey(), ossfileProperties.getSecretKey())
                .build();
    }

    public static MinioClient createMinioClient(OssfileProperties ossfileProperties) {
        MinioClient.Builder builder = MinioClient.builder();
        Optional.ofNullable(ossfileProperties.getRegion()).ifPresent(builder::region);
        return builder
                .endpoint(ossfileProperties.getEndpoint())
                .credentials(ossfileProperties.getAccessKey(), ossfileProperties.getSecretKey())
                .build();
    }

    public static void initDefaultBucket(MinioClient minioClient, String bucketName) throws ServiceErrorException {
        try {
            boolean isBucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isBucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_CONFIG_ERROR, exception.getMessage());
        }
    }

    public static String bucketPolicy() throws ServiceErrorException {
        return bucketPolicy(MinioContextHolder.defaultBucket());
    }

    public static String bucketPolicy(String bucketName) throws ServiceErrorException {
        try {
            return MinioContextHolder.defaultClient().getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_BUCKET_POLICY_ERROR, exception.getMessage());
        }
    }

    public static List<Bucket> listBuckets() throws ServiceErrorException {
        try {
            return MinioContextHolder.defaultClient().listBuckets();
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception.getMessage());
        }
    }

    public static Optional<Bucket> getBucket() throws ServiceErrorException {
        return getBucket(MinioContextHolder.defaultBucket());
    }

    public static Optional<Bucket> getBucket(String bucketName) throws ServiceErrorException {
        return listBuckets().stream().filter(bucket -> bucket.name().equals(bucketName)).findFirst();
    }

    public static void switchBucket(String bucketName) throws ServiceErrorException {
        MinioContextHolder.switchBucket(bucketName);
        initDefaultBucket(MinioContextHolder.defaultClient(), bucketName);
    }

    public static void makeBucket(String bucketName) throws ServiceErrorException {
        try {
            MinioContextHolder.defaultClient().makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_MAKE_BUCKET_ERROR, exception.getMessage());
        }
    }

    public static void removeBucket(String bucketName) throws ServiceErrorException {
        try {
            MinioContextHolder.defaultClient().removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new ServiceErrorException(OssfileErrorStatus.OSSFILE_REMOVE_BUCKET_ERROR, exception.getMessage());
        }
    }

    public static StatObjectResponse statObject(String objectName) throws FileErrorException {
        return statObject(MinioContextHolder.defaultBucket(), objectName);
    }

    public static StatObjectResponse statObject(String bucketName, String objectName) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_STAT_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static boolean isObjectExist(String objectName) {
        return isObjectExist(MinioContextHolder.defaultBucket(), objectName);
    }

    public static boolean isObjectExist(String bucketName, String objectName) {
        boolean exist = true;
        try {
            statObject(bucketName, objectName);
        } catch (FileErrorException ignored) {
            exist = false;
        }
        return exist;
    }

    public static Iterable<Result<Item>> listObjects(String prefix, boolean recursive) {
        return listObjects(MinioContextHolder.defaultBucket(), prefix, recursive);
    }

    public static Iterable<Result<Item>> listObjects(String bucketName, String prefix, boolean recursive) {
        return MinioContextHolder.defaultClient().listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
    }


    public static List<Item> allObjects(String prefix, boolean recursive) throws FileErrorException {
        return allObjects(MinioContextHolder.defaultBucket(), prefix, recursive);
    }

    public static List<Item> allObjects(String bucketName, String prefix, boolean recursive) throws FileErrorException {
        List<Item> items = new ArrayList<>();
        Iterable<Result<Item>> resultIterable = listObjects(bucketName, prefix, recursive);
        if (GeneralUtils.isNotEmpty(resultIterable)) {
            try {
                for (Result<Item> itemResult : resultIterable) {
                    items.add(itemResult.get());
                }
            } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException exception) {
                throw new FileErrorException(OssfileErrorStatus.OSSFILE_GET_ALL_OBJECTS_ERROR, exception.getMessage());
            }
        }
        return items;
    }

    public static GetObjectResponse getObject(String objectName) throws FileErrorException {
        return getObject(MinioContextHolder.defaultBucket(), objectName);
    }

    public static GetObjectResponse getObject(String bucketName, String objectName) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static GetObjectResponse getObject(String objectName, long offset, long length) throws FileErrorException {
        return getObject(MinioContextHolder.defaultBucket(), objectName, offset, length);
    }

    public static GetObjectResponse getObject(String bucketName, String objectName, long offset, long length) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName)
                    .offset(offset).length(length).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static ObjectWriteResponse putObject(MultipartFile file, String objectName, String contentType) throws FileErrorException {
        return putObject(MinioContextHolder.defaultBucket(), file, objectName, contentType);
    }

    public static ObjectWriteResponse putObject(String bucketName, MultipartFile file, String objectName, String contentType) throws FileErrorException {
        try {
            InputStream inputStream = file.getInputStream();
            return MinioContextHolder.defaultClient().putObject(PutObjectArgs.builder().bucket(bucketName)
                    .object(objectName).contentType(contentType)
                    .stream(inputStream, inputStream.available(), -1).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static ObjectWriteResponse putObject(String objectName, InputStream inputStream) throws FileErrorException {
        return putObject(MinioContextHolder.defaultBucket(), objectName, inputStream);
    }

    public static ObjectWriteResponse putObject(String bucketName, String objectName, InputStream inputStream) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(inputStream, inputStream.available(), -1).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static InitiateMultipartUploadResult initiateMultipart(String objectName) throws FileErrorException {
        return initiateMultipart(MinioContextHolder.defaultBucket(), objectName, null, null);
    }

    public static InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        return MinioContextHolder.multipartClient().initiateMultipart(bucketName,objectName,headers,extraQueryParams);
    }

    public static ObjectWriteResponse completeMultipart(String objectName, String uploadId, Collection<Part> parts) throws FileErrorException {
        return completeMultipart(MinioContextHolder.defaultBucket(), objectName, uploadId, parts,null,null);
    }

    public static ObjectWriteResponse completeMultipart(String bucketName, String objectName, String uploadId, Collection<Part> parts, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        return MinioContextHolder.multipartClient().completeMultipart(bucketName,objectName,uploadId,parts,headers,extraQueryParams);
    }

    public static UploadPartResponse uploadMultipart(String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws FileErrorException {
        return uploadMultipart(MinioContextHolder.defaultBucket(), objectName, uploadId, partIndex, inputStream, partSize,null,null);
    }

    public static UploadPartResponse uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        return MinioContextHolder.multipartClient().uploadMultipart(bucketName, objectName, uploadId, partIndex, inputStream, partSize,headers,extraQueryParams);
    }

    public static ObjectWriteResponse composeSource(String objectName, Collection<ComposeSource> composeSources) throws FileErrorException {
        return composeSource(MinioContextHolder.defaultBucket(), objectName, composeSources);
    }

    public static ObjectWriteResponse composeSource(String bucketName, String objectName, Collection<ComposeSource> composeSources) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().composeObject(ComposeObjectArgs.builder().bucket(bucketName)
                    .object(objectName).sources(new ArrayList<>(composeSources)).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_COMPOSE_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static ObjectWriteResponse composeSource(String bucketName, String objectName, Map<String, Collection<String>> sourcesMap) throws FileErrorException {
        List<ComposeSource> composeSources = new ArrayList<>();
        sourcesMap.forEach((sourceBucketName, sourceObjectNames) -> sourceObjectNames.forEach(sourceObjectName -> {
            composeSources.add(ComposeSource.builder().bucket(sourceBucketName).object(sourceObjectName).build());
        }));
        return composeSource(bucketName, objectName, composeSources);
    }

    public static ObjectWriteResponse composeObject(String bucketName, String objectName, Collection<String> sources) throws FileErrorException {
        return composeObject(bucketName, objectName, Collections.singletonMap(bucketName, sources));
    }

    public static ObjectWriteResponse composeObject(String objectName, Collection<String> sources) throws FileErrorException {
        return composeObject(MinioContextHolder.defaultBucket(), objectName, Collections.singletonMap(MinioContextHolder.defaultBucket(), sources));
    }

    public static ObjectWriteResponse composeObject(String bucketName, String objectName, Map<String, Collection<String>> sourcesMap) throws FileErrorException {
        return composeSource(bucketName, objectName, sourcesMap);
    }

    public static ObjectWriteResponse composeObject(String objectName, Map<String, Collection<String>> sourcesMap) throws FileErrorException {
        return composeObject(MinioContextHolder.defaultBucket(), objectName, sourcesMap);
    }

    public static ObjectWriteResponse uploadObject(String objectName, String filename) throws FileErrorException {
        return uploadObject(objectName, filename, 0L);
    }

    public static ObjectWriteResponse uploadObject(String objectName, String filename, long partSize) throws FileErrorException {
        return uploadObject(MinioContextHolder.defaultBucket(), objectName, filename, partSize);
    }

    public static ObjectWriteResponse uploadObject(String bucketName, String objectName, String filename) throws FileErrorException {
        return uploadObject(bucketName, objectName, filename, 0L);
    }

    public static ObjectWriteResponse uploadObject(String bucketName, String objectName, String filename, long partSize) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().uploadObject(UploadObjectArgs.builder().bucket(bucketName)
                    .object(objectName).filename(filename, partSize).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_UPLOAD_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static ObjectWriteResponse appendObject(String objectName, SnowballObject object) throws FileErrorException {
        return appendObjects(MinioContextHolder.defaultBucket(),objectName,Collections.singletonList(object));
    }

    public static ObjectWriteResponse appendObject(String bucketName, String objectName, SnowballObject object) throws FileErrorException {
        return appendObjects(bucketName,objectName,Collections.singletonList(object));
    }

    public static ObjectWriteResponse appendObjects(String objectName, Collection<SnowballObject> objects) throws FileErrorException {
        return appendObjects(MinioContextHolder.defaultBucket(),objectName,objects);
    }

    public static ObjectWriteResponse appendObjects(String bucketName, String objectName, Collection<SnowballObject> objects) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().uploadSnowballObjects(UploadSnowballObjectsArgs.builder().bucket(bucketName)
                    .object(objectName).objects(objects).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_APPEND_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static ObjectWriteResponse copyObject(String sourceObjectName, String targetObjectName) throws FileErrorException {
        return copyObject(MinioContextHolder.defaultBucket(), sourceObjectName, MinioContextHolder.defaultBucket(), targetObjectName);
    }

    public static ObjectWriteResponse copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) throws FileErrorException {
        try {
            return MinioContextHolder.defaultClient().copyObject(CopyObjectArgs.builder().source(CopySource.builder().bucket(sourceBucketName).object(sourceObjectName).build())
                    .bucket(targetBucketName).object(targetObjectName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_COPY_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static void removeObject(String objectName) throws FileErrorException {
        removeObject(MinioContextHolder.defaultBucket(), objectName);
    }

    public static void removeObject(String bucketName, String objectName) throws FileErrorException {
        try {
            MinioContextHolder.defaultClient().removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_REMOVE_OBJECT_ERROR, exception.getMessage());
        }
    }

    public static void removeObjects(Collection<String> objectNames) throws FileErrorException {
        removeObjects(MinioContextHolder.defaultBucket(), objectNames);
    }

    public static void removeObjects(String bucketName, Collection<String> objectNames) throws FileErrorException {
        for (String objectName : objectNames) {
            removeObject(bucketName, objectName);
        }
    }

    public static URL objectUrl(String objectName, Integer expire) throws FileErrorException {
        return objectUrl(MinioContextHolder.defaultBucket(),objectName, expire);
    }

    public static URL objectUrl(String bucketName, String objectName, Integer expire) throws FileErrorException {
        try {
            String presignedObjectUrl = MinioContextHolder.defaultClient().getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).expiry(expire).build());
            return new URL(presignedObjectUrl);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_PRESIGNED_OBJECT_URL_ERROR, exception.getMessage());
        }
    }

    public static String uriDecode(String uri) throws UnsupportedErrorException {
        String url = uri.trim().replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            throw new UnsupportedErrorException(OssfileErrorStatus.OSSFILE_UNSUPPORTED_ERROR, exception.getMessage());
        }

    }

}
