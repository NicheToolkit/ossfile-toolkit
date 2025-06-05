package io.github.nichetoolkit.ossfile;

import com.google.common.collect.Multimap;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
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

/**
 * <code>MinioMultipartClient</code>
 * <p>The minio multipart client class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.minio.MinioAsyncClient
 * @since Jdk1.8
 */
public class MinioMultipartClient extends MinioAsyncClient {

    /**
     * <code>MinioMultipartClient</code>
     * <p>Instantiates a new minio multipart client.</p>
     * @param client {@link io.minio.MinioAsyncClient} <p>The client parameter is <code>MinioAsyncClient</code> type.</p>
     * @see io.minio.MinioAsyncClient
     */
    public MinioMultipartClient(MinioAsyncClient client) {
        super(client);
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @return {@link io.minio.messages.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see io.minio.messages.InitiateMultipartUploadResult
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName) throws FileErrorException {
        return initiateMultipart(bucketName, objectName, null, null);
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
    public InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        try {
            return this.initiateMultipart(bucketName, MinioContextHolder.defaultRegion(), objectName, headers, extraQueryParams);
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_INITIATE_MULTIPART_ERROR, exception.getMessage());
        }
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param bucket           {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param region           {@link java.lang.String} <p>The region parameter is <code>String</code> type.</p>
     * @param object           {@link java.lang.String} <p>The object parameter is <code>String</code> type.</p>
     * @param headers          {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @return {@link io.minio.messages.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @throws IOException               {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws InvalidKeyException       {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws NoSuchAlgorithmException  {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws InternalException         {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     * @throws XmlParserException        {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InterruptedException      {@link java.lang.InterruptedException} <p>The interrupted exception is <code>InterruptedException</code> type.</p>
     * @throws ExecutionException        {@link java.util.concurrent.ExecutionException} <p>The execution exception is <code>ExecutionException</code> type.</p>
     * @see java.lang.String
     * @see com.google.common.collect.Multimap
     * @see io.minio.messages.InitiateMultipartUploadResult
     * @see java.io.IOException
     * @see java.security.InvalidKeyException
     * @see java.security.NoSuchAlgorithmException
     * @see io.minio.errors.InsufficientDataException
     * @see io.minio.errors.InternalException
     * @see io.minio.errors.XmlParserException
     * @see java.lang.InterruptedException
     * @see java.util.concurrent.ExecutionException
     */
    public InitiateMultipartUploadResult initiateMultipart(String bucket, String region, String object, Multimap<String, String> headers, Multimap<String, String> extraQueryParams)
            throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, XmlParserException, InterruptedException, ExecutionException {
        CompletableFuture<CreateMultipartUploadResponse> response = this.createMultipartUploadAsync(bucket, region, object, headers, extraQueryParams);
        return response.get().result();
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
     * @return {@link io.minio.UploadPartResponse} <p>The upload multipart return object is <code>UploadPartResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @see java.lang.String
     * @see java.io.InputStream
     * @see io.minio.UploadPartResponse
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public UploadPartResponse uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws FileErrorException {
        return uploadMultipart(bucketName, objectName, uploadId, partIndex, inputStream, partSize, null, null);
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
    public UploadPartResponse uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        try {
            CompletableFuture<UploadPartResponse> uploadPartAsync = this.uploadPartAsync(bucketName, MinioContextHolder.defaultRegion(), objectName, inputStream, partSize, uploadId, partIndex, headers, extraQueryParams);
            return uploadPartAsync.get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_UPLOAD_MULTIPART_ERROR, exception.getMessage());
        }
    }

    @Override
    public CompletableFuture<UploadPartResponse> uploadPartAsync(String bucketName, String region, String objectName, Object data,
                                                                 long length, String uploadId, int partNumber, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams)
            throws InvalidKeyException, InsufficientDataException, InternalException, NoSuchAlgorithmException, XmlParserException, IOException {
        return super.uploadPartAsync(bucketName, region, objectName, data, length, uploadId, partNumber, extraHeaders, extraQueryParams);
    }

    /**
     * <code>completeMultipart</code>
     * <p>The complete multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
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
    public ObjectWriteResponse completeMultipart(String bucketName, String objectName, String uploadId, Collection<Part> parts) throws FileErrorException {
        return completeMultipart(bucketName, objectName, uploadId, parts, null, null);
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
    public ObjectWriteResponse completeMultipart(String bucketName, String objectName, String uploadId, Collection<Part> parts, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        try {
            Part[] partsArray = parts.toArray(new Part[0]);
            CompletableFuture<ObjectWriteResponse> completeUploadAsync = this.completeMultipartUploadAsync(bucketName, MinioContextHolder.defaultRegion(), objectName, uploadId, partsArray, headers, extraQueryParams);
            return completeUploadAsync.get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw new FileErrorException(OssfileErrorStatus.OSSFILE_COMPLETE_MULTIPART_ERROR, exception.getMessage());
        }
    }

    @Override
    public CompletableFuture<ObjectWriteResponse> completeMultipartUploadAsync(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams)
            throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams);
    }

}
