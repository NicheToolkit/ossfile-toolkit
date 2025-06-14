package io.github.nichetoolkit.ossfile;

import com.google.common.collect.Multimap;
import io.github.nichetoolkit.rest.RestStatus;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.InitiateMultipartUploadResult;
import io.minio.messages.Part;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MinioMultipartClient extends MinioAsyncClient {

    public MinioMultipartClient(MinioAsyncClient client) {
        super(client);
    }

    public InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName) throws FileErrorException {
        return initiateMultipart(bucketName, objectName, null, null);
    }

    public InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        try {
            return this.initiateMultipart(bucketName, MinioContextHolder.defaultRegion(), objectName, headers, extraQueryParams);
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
           throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_INITIATE_MULTIPART_ERROR, exception);
        }
    }

    public InitiateMultipartUploadResult initiateMultipart(String bucket, String region, String object, Multimap<String, String> headers, Multimap<String, String> extraQueryParams)
            throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, XmlParserException, InterruptedException, ExecutionException {
        CompletableFuture<CreateMultipartUploadResponse> response = this.createMultipartUploadAsync(bucket, region, object, headers, extraQueryParams);
        return response.get().result();
    }

    public UploadPartResponse uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws FileErrorException {
        return uploadMultipart(bucketName, objectName, uploadId, partIndex, inputStream, partSize, null, null);
    }

    public UploadPartResponse uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        try {
            CompletableFuture<UploadPartResponse> uploadPartAsync = this.uploadPartAsync(bucketName, MinioContextHolder.defaultRegion(), objectName, inputStream, partSize, uploadId, partIndex, headers, extraQueryParams);
            return uploadPartAsync.get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_UPLOAD_MULTIPART_ERROR, exception);
        }
    }

    @Override
    public CompletableFuture<UploadPartResponse> uploadPartAsync(String bucketName, String region, String objectName, Object data,
                                                                 long length, String uploadId, int partNumber, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams)
            throws InvalidKeyException, InsufficientDataException, InternalException, NoSuchAlgorithmException, XmlParserException, IOException {
        return super.uploadPartAsync(bucketName, region, objectName, data, length, uploadId, partNumber, extraHeaders, extraQueryParams);
    }

    public ObjectWriteResponse completeMultipart(String bucketName, String objectName, String uploadId, Collection<Part> parts) throws FileErrorException {
        return completeMultipart(bucketName, objectName, uploadId, parts, null, null);
    }

    public ObjectWriteResponse completeMultipart(String bucketName, String objectName, String uploadId, Collection<Part> parts, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        try {
            Part[] partsArray = parts.toArray(new Part[0]);
            CompletableFuture<ObjectWriteResponse> completeUploadAsync = this.completeMultipartUploadAsync(bucketName, MinioContextHolder.defaultRegion(), objectName, uploadId, partsArray, headers, extraQueryParams);
            return completeUploadAsync.get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_COMPLETE_MULTIPART_ERROR, exception);
        }
    }

    @Override
    public CompletableFuture<ObjectWriteResponse> completeMultipartUploadAsync(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams)
            throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams);
    }
}
