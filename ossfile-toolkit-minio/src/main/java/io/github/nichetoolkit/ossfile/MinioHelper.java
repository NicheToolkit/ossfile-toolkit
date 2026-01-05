package io.github.nichetoolkit.ossfile;

import com.google.common.collect.Multimap;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.natives.UnsupportedErrorException;
import io.github.nichetoolkit.rest.future.RestCompletableFuture;
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

@Slf4j
public class MinioHelper {

    public static OssfileMinioClient createOssfileClient(OssfileProperties ossfileProperties) {
        OssfileMinioClient.Builder builder = OssfileMinioClient.ofBuilder();
        if (ossfileProperties.getIntranetPriority() && GeneralUtils.isNotEmpty(ossfileProperties.getIntranet())) {
            builder.endpoint(ossfileProperties.getIntranet());
        } else {
            builder.endpoint(ossfileProperties.getEndpoint());
        }
        return builder.credentials(ossfileProperties.getAccessKey(), ossfileProperties.getSecretKey()).build();
    }

    public static MinioAsyncClient createAsyncClient(OssfileProperties ossfileProperties) {
        MinioAsyncClient.Builder builder = MinioAsyncClient.builder();
        if (ossfileProperties.getIntranetPriority() && GeneralUtils.isNotEmpty(ossfileProperties.getIntranet())) {
            builder.endpoint(ossfileProperties.getIntranet());
        } else {
            builder.endpoint(ossfileProperties.getEndpoint());
        }
        return builder.credentials(ossfileProperties.getAccessKey(), ossfileProperties.getSecretKey()).build();
    }

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


    public static void initDefaultBucket(OssfileMinioClient minioClient, String bucketName) throws RestException {
        RestCompletableFuture<Boolean> isBucketExistsFuture = minioClient.ofBucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        isBucketExistsFuture.ofThenAcceptAsync(isBucketExists -> {
            if (!isBucketExists) {
                minioClient.ofMakeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        });
    }

    public static String bucketPolicy() throws RestException {
        return bucketPolicy(OssfileStoreHolder.defaultBucket());
    }

    public static String bucketPolicy(String bucketName) throws RestException {
        return MinioContextHolder.defaultClient().getBucketOfPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());

    }

    public static List<Bucket> listBuckets() throws RestException {
        return MinioContextHolder.defaultClient().listOfBuckets();
    }

    public static Optional<Bucket> getBucket() throws RestException {
        return getBucket(OssfileStoreHolder.defaultBucket());
    }

    public static Optional<Bucket> getBucket(String bucketName) throws RestException {
        return listBuckets().stream().filter(bucket -> bucket.name().equals(bucketName)).findFirst();
    }

    public static void switchBucket(String bucketName) throws RestException {
        OssfileStoreHolder.switchBucket(bucketName);
        initDefaultBucket(MinioContextHolder.defaultClient(), bucketName);
    }

    public static void makeBucket(String bucketName) throws RestException {
        MinioContextHolder.defaultClient().makeOfBucket(MakeBucketArgs.builder().bucket(bucketName).build());

    }

    public static void removeBucket(String bucketName) throws RestException {
        try {
            MinioContextHolder.defaultClient().removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.serviceErrorOfCause(OssfileErrorStatus.OSSFILE_REMOVE_BUCKET_ERROR, exception);
        }
    }

    public static StatObjectResponse statObject(String objectName) throws RestException {
        return statObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    public static StatObjectResponse statObject(String bucketName, String objectName) throws RestException {
        return MinioContextHolder.defaultClient().statOfObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    public static boolean isObjectExist(String objectName) {
        return isObjectExist(OssfileStoreHolder.defaultBucket(), objectName);
    }

    public static boolean isObjectExist(String bucketName, String objectName) {
        boolean exist = true;
        try {
            statObject(bucketName, objectName);
        } catch (RestException ignored) {
            exist = false;
        }
        return exist;
    }

    public static Iterable<Result<Item>> listObjects(String prefix, boolean recursive) {
        return listObjects(OssfileStoreHolder.defaultBucket(), prefix, recursive);
    }

    public static Iterable<Result<Item>> listObjects(String bucketName, String prefix, boolean recursive) {
        return MinioContextHolder.defaultClient().listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
    }


    public static List<Item> allObjects(String prefix, boolean recursive) throws RestException {
        return allObjects(OssfileStoreHolder.defaultBucket(), prefix, recursive);
    }

    public static List<Item> allObjects(String bucketName, String prefix, boolean recursive) throws RestException {
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

    public static GetObjectResponse getObject(String objectName) throws RestException {
        return getObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    public static GetObjectResponse getObject(String bucketName, String objectName) throws RestException {
        return MinioContextHolder.defaultClient().getOfObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    public static GetObjectResponse getObject(String objectName, long offset, long length) throws RestException {
        return getObject(OssfileStoreHolder.defaultBucket(), objectName, offset, length);
    }

    public static GetObjectResponse getObject(String bucketName, String objectName, long offset, long length) throws RestException {
        return MinioContextHolder.defaultClient().getOfObject(GetObjectArgs.builder().bucket(bucketName).object(objectName)
                .offset(offset).length(length).build());
    }

    public static ObjectWriteResponse putObject(MultipartFile file, String objectName, String contentType) throws RestException {
        return putObject(OssfileStoreHolder.defaultBucket(), file, objectName, contentType);
    }

    public static ObjectWriteResponse putObject(String bucketName, MultipartFile file, String objectName, String contentType) throws RestException {
        try {
            InputStream inputStream = file.getInputStream();
            return MinioContextHolder.defaultClient().putOfObject(PutObjectArgs.builder().bucket(bucketName)
                    .object(objectName).contentType(contentType)
                    .stream(inputStream, inputStream.available(), -1).build());
        } catch (IOException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception);
        }
    }

    public static ObjectWriteResponse putObject(String objectName, InputStream inputStream) throws RestException {
        return putObject(OssfileStoreHolder.defaultBucket(), objectName, inputStream);
    }

    public static ObjectWriteResponse putObject(String bucketName, String objectName, InputStream inputStream) throws RestException {
        try {
            return MinioContextHolder.defaultClient().putOfObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(inputStream, inputStream.available(), -1).build());
        } catch (IOException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception);
        }
    }

    public static InitiateMultipartUploadResult initiateMultipart(String objectName) throws RestException {
        return initiateMultipart(OssfileStoreHolder.defaultBucket(), objectName, null, null);
    }

    public static InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws RestException {
        return MinioContextHolder.defaultClient().initiateMultipart(bucketName, objectName, headers, extraQueryParams);
    }

    public static ObjectWriteResponse completeMultipart(String objectName, String uploadId, Collection<Part> parts) throws RestException {
        return completeMultipart(OssfileStoreHolder.defaultBucket(), objectName, uploadId, parts, null, null);
    }

    public static ObjectWriteResponse completeMultipart(String bucketName, String objectName, String uploadId, Collection<Part> parts, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws RestException {
        return MinioContextHolder.defaultClient().completeMultipart(bucketName, objectName, uploadId, parts, headers, extraQueryParams);
    }

    public static UploadPartResponse uploadMultipart(String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws RestException {
        return uploadMultipart(OssfileStoreHolder.defaultBucket(), objectName, uploadId, partIndex, inputStream, partSize, null, null);
    }

    public static UploadPartResponse uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws RestException {
        return MinioContextHolder.defaultClient().uploadMultipart(bucketName, objectName, uploadId, partIndex, inputStream, partSize, headers, extraQueryParams);
    }

    public static ObjectWriteResponse composeSource(String objectName, Collection<ComposeSource> composeSources) throws RestException {
        return composeSource(OssfileStoreHolder.defaultBucket(), objectName, composeSources);
    }

    public static ObjectWriteResponse composeSource(String bucketName, String objectName, Collection<ComposeSource> composeSources) throws RestException {
        return MinioContextHolder.defaultClient().composeOfObject(ComposeObjectArgs.builder().bucket(bucketName)
                .object(objectName).sources(new ArrayList<>(composeSources)).build());
    }

    public static ObjectWriteResponse composeSource(String bucketName, String objectName, Map<String, Collection<String>> sourcesMap) throws RestException {
        List<ComposeSource> composeSources = new ArrayList<>();
        sourcesMap.forEach((sourceBucketName, sourceObjectNames) -> sourceObjectNames.forEach(sourceObjectName -> {
            composeSources.add(ComposeSource.builder().bucket(sourceBucketName).object(sourceObjectName).build());
        }));
        return composeSource(bucketName, objectName, composeSources);
    }

    public static ObjectWriteResponse composeObject(String bucketName, String objectName, Collection<String> sources) throws RestException {
        return composeObject(bucketName, objectName, Collections.singletonMap(bucketName, sources));
    }

    public static ObjectWriteResponse composeObject(String objectName, Collection<String> sources) throws RestException {
        return composeObject(OssfileStoreHolder.defaultBucket(), objectName, Collections.singletonMap(OssfileStoreHolder.defaultBucket(), sources));
    }

    public static ObjectWriteResponse composeObject(String bucketName, String objectName, Map<String, Collection<String>> sourcesMap) throws RestException {
        return composeSource(bucketName, objectName, sourcesMap);
    }

    public static ObjectWriteResponse composeObject(String objectName, Map<String, Collection<String>> sourcesMap) throws RestException {
        return composeObject(OssfileStoreHolder.defaultBucket(), objectName, sourcesMap);
    }

    public static ObjectWriteResponse uploadObject(String objectName, String filename) throws RestException {
        return uploadObject(objectName, filename, 0L);
    }

    public static ObjectWriteResponse uploadObject(String objectName, String filename, long partSize) throws RestException {
        return uploadObject(OssfileStoreHolder.defaultBucket(), objectName, filename, partSize);
    }

    public static ObjectWriteResponse uploadObject(String bucketName, String objectName, String filename) throws RestException {
        return uploadObject(bucketName, objectName, filename, 0L);
    }

    public static ObjectWriteResponse uploadObject(String bucketName, String objectName, String filename, long partSize) throws RestException {
        try {
            return MinioContextHolder.defaultClient().uploadOfObject(UploadObjectArgs.builder().bucket(bucketName)
                    .object(objectName).filename(filename, partSize).build());
        } catch (IOException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_UPLOAD_OBJECT_ERROR, exception);
        }
    }

    public static ObjectWriteResponse appendObject(String objectName, SnowballObject object) throws RestException {
        return appendObjects(OssfileStoreHolder.defaultBucket(), objectName, Collections.singletonList(object));
    }

    public static ObjectWriteResponse appendObject(String bucketName, String objectName, SnowballObject object) throws RestException {
        return appendObjects(bucketName, objectName, Collections.singletonList(object));
    }

    public static ObjectWriteResponse appendObjects(String objectName, Collection<SnowballObject> objects) throws RestException {
        return appendObjects(OssfileStoreHolder.defaultBucket(), objectName, objects);
    }

    public static ObjectWriteResponse appendObjects(String bucketName, String objectName, Collection<SnowballObject> objects) throws RestException {
        return MinioContextHolder.defaultClient().uploadSnowballOfObjects(UploadSnowballObjectsArgs.builder().bucket(bucketName)
                .object(objectName).objects(objects).build());
    }

    public static ObjectWriteResponse copyObject(String sourceObjectName, String targetObjectName) throws RestException {
        return copyObject(OssfileStoreHolder.defaultBucket(), sourceObjectName, OssfileStoreHolder.defaultBucket(), targetObjectName);
    }

    public static ObjectWriteResponse copyObject(String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) throws RestException {
        return MinioContextHolder.defaultClient().copyOfObject(CopyObjectArgs.builder().source(CopySource.builder().bucket(sourceBucketName).object(sourceObjectName).build())
                .bucket(targetBucketName).object(targetObjectName).build());
    }

    public static void removeObject(String objectName) throws RestException {
        removeObject(OssfileStoreHolder.defaultBucket(), objectName);
    }

    public static void removeObject(String bucketName, String objectName) throws RestException {
        try {
            MinioContextHolder.defaultClient().removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_REMOVE_OBJECT_ERROR, exception);
        }
    }

    public static void removeObjects(Collection<String> objectNames) throws RestException {
        removeObjects(OssfileStoreHolder.defaultBucket(), objectNames);
    }

    public static void removeObjects(String bucketName, Collection<String> objectNames) throws RestException {
        for (String objectName : objectNames) {
            removeObject(bucketName, objectName);
        }
    }

    public static URL objectUrl(String objectName, Integer expire) throws RestException {
        return objectUrl(OssfileStoreHolder.defaultBucket(), objectName, expire);
    }

    public static URL objectUrl(String bucketName, String objectName, Integer expire) throws RestException {
        try {
            String presignedObjectUrl = MinioContextHolder.defaultClient().getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).expiry(expire).build());
            return new URL(presignedObjectUrl);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PRESIGNED_OBJECT_URL_ERROR, exception);
        }
    }

    public static String uriDecode(String uri) throws UnsupportedErrorException {
        String url = uri.trim().replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            throw new UnsupportedErrorException(OssfileErrorStatus.OSSFILE_UNSUPPORTED_ERROR, exception);
        }

    }

}
