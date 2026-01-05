package io.github.nichetoolkit.ossfile;

import com.google.common.collect.Multimap;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.future.RestCompletableFuture;
import io.minio.*;
import io.minio.credentials.Credentials;
import io.minio.credentials.Provider;
import io.minio.errors.*;
import io.minio.http.HttpUtils;
import io.minio.http.Method;
import io.minio.messages.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <code>OssfileMinioClient</code>
 * <p>The ossfile minio client class.</p>
 * @see  io.minio.MinioAsyncClient
 * @see  lombok.extern.slf4j.Slf4j
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk17
 */
@Slf4j
public class OssfileMinioClient extends MinioAsyncClient {

    /**
     * <code>OssfileMinioClient</code>
     * <p>Instantiates a new ossfile minio client.</p>
     * @param client {@link io.minio.MinioAsyncClient} <p>The client parameter is <code>MinioAsyncClient</code> type.</p>
     * @see  io.minio.MinioAsyncClient
     */
    protected OssfileMinioClient(MinioAsyncClient client) {
        super(client);
    }

    // MinioClient

    /**
     * <code>statOfObject</code>
     * <p>The stat of object method.</p>
     * @param args {@link io.minio.StatObjectArgs} <p>The args parameter is <code>StatObjectArgs</code> type.</p>
     * @see  io.minio.StatObjectArgs
     * @see  io.minio.StatObjectResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.StatObjectResponse} <p>The stat of object return object is <code>StatObjectResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public StatObjectResponse statOfObject(StatObjectArgs args) throws FileErrorException {
        try {
            return this.statObject(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_STAT_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>getOfObject</code>
     * <p>The get of object getter method.</p>
     * @param args {@link io.minio.GetObjectArgs} <p>The args parameter is <code>GetObjectArgs</code> type.</p>
     * @see  io.minio.GetObjectArgs
     * @see  io.minio.GetObjectResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.GetObjectResponse} <p>The get of object return object is <code>GetObjectResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public GetObjectResponse getOfObject(GetObjectArgs args) throws FileErrorException {
        try {
            return this.getObject(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>downloadOfObject</code>
     * <p>The download of object method.</p>
     * @param args {@link io.minio.DownloadObjectArgs} <p>The args parameter is <code>DownloadObjectArgs</code> type.</p>
     * @see  io.minio.DownloadObjectArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void downloadOfObject(DownloadObjectArgs args) throws FileErrorException {
        try {
            this.downloadObject(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_DOWNLOAD_OBJECT_ERROR, exception);
        }

    }

    /**
     * <code>copyOfObject</code>
     * <p>The copy of object method.</p>
     * @param args {@link io.minio.CopyObjectArgs} <p>The args parameter is <code>CopyObjectArgs</code> type.</p>
     * @see  io.minio.CopyObjectArgs
     * @see  io.minio.ObjectWriteResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.ObjectWriteResponse} <p>The copy of object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public ObjectWriteResponse copyOfObject(CopyObjectArgs args) throws FileErrorException {
        try {
            return this.copyObject(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_COPY_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>composeOfObject</code>
     * <p>The compose of object method.</p>
     * @param args {@link io.minio.ComposeObjectArgs} <p>The args parameter is <code>ComposeObjectArgs</code> type.</p>
     * @see  io.minio.ComposeObjectArgs
     * @see  io.minio.ObjectWriteResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.ObjectWriteResponse} <p>The compose of object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public ObjectWriteResponse composeOfObject(ComposeObjectArgs args) throws FileErrorException {
        try {
            return this.composeObject(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_COMPOSE_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>getOfPresignedPostFormData</code>
     * <p>The get of presigned post form data getter method.</p>
     * @param policy {@link io.minio.PostPolicy} <p>The policy parameter is <code>PostPolicy</code> type.</p>
     * @see  io.minio.PostPolicy
     * @see  java.util.Map
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link java.util.Map} <p>The get of presigned post form data return object is <code>Map</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public Map<String, String> getOfPresignedPostFormData(PostPolicy policy) throws FileErrorException {
        try {
            return this.getPresignedPostFormData(policy);
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | ServerException | InvalidResponseException |
                 ErrorResponseException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PRESIGNED_POLICY_ERROR, exception);
        }
    }

    /**
     * <code>removeOfObject</code>
     * <p>The remove of object method.</p>
     * @param args {@link io.minio.RemoveObjectArgs} <p>The args parameter is <code>RemoveObjectArgs</code> type.</p>
     * @see  io.minio.RemoveObjectArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void removeOfObject(RemoveObjectArgs args) throws FileErrorException {
        try {
            this.removeObject(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | ExecutionException | InterruptedException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_REMOVE_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>restoreOfObject</code>
     * <p>The restore of object method.</p>
     * @param args {@link io.minio.RestoreObjectArgs} <p>The args parameter is <code>RestoreObjectArgs</code> type.</p>
     * @see  io.minio.RestoreObjectArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void restoreOfObject(RestoreObjectArgs args) throws FileErrorException {
        try {
            this.restoreObject(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | ExecutionException | InterruptedException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_RESTORE_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>listOfBuckets</code>
     * <p>The list of buckets method.</p>
     * @return  {@link java.util.List} <p>The list of buckets return object is <code>List</code> type.</p>
     * @see  java.util.List
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public List<Bucket> listOfBuckets() throws FileErrorException {
        try {
            return this.listBuckets().get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception);
        }
    }

    /**
     * <code>listOfBuckets</code>
     * <p>The list of buckets method.</p>
     * @param args {@link io.minio.ListBucketsArgs} <p>The args parameter is <code>ListBucketsArgs</code> type.</p>
     * @see  io.minio.ListBucketsArgs
     * @see  java.util.List
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link java.util.List} <p>The list of buckets return object is <code>List</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public List<Bucket> listOfBuckets(ListBucketsArgs args) throws FileErrorException {
        try {
            return this.listBuckets(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception);
        }
    }

    /**
     * <code>bucketOfExists</code>
     * <p>The bucket of exists method.</p>
     * @param args {@link io.minio.BucketExistsArgs} <p>The args parameter is <code>BucketExistsArgs</code> type.</p>
     * @see  io.minio.BucketExistsArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return boolean <p>The bucket of exists return object is <code>boolean</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public boolean bucketOfExists(BucketExistsArgs args) throws FileErrorException {
        try {
            return this.bucketExists(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_EXIST_BUCKET_ERROR, exception);
        }
    }

    /**
     * <code>makeOfBucket</code>
     * <p>The make of bucket method.</p>
     * @param args {@link io.minio.MakeBucketArgs} <p>The args parameter is <code>MakeBucketArgs</code> type.</p>
     * @see  io.minio.MakeBucketArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void makeOfBucket(MakeBucketArgs args) throws FileErrorException {
        try {
            this.makeBucket(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_EXIST_BUCKET_ERROR, exception);
        }

    }

    /**
     * <code>setBucketOfVersioning</code>
     * <p>The set bucket of versioning setter method.</p>
     * @param args {@link io.minio.SetBucketVersioningArgs} <p>The args parameter is <code>SetBucketVersioningArgs</code> type.</p>
     * @see  io.minio.SetBucketVersioningArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void setBucketOfVersioning(SetBucketVersioningArgs args) throws FileErrorException {
        try {
            this.setBucketVersioning(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>getBucketOfVersioning</code>
     * <p>The get bucket of versioning getter method.</p>
     * @param args {@link io.minio.GetBucketVersioningArgs} <p>The args parameter is <code>GetBucketVersioningArgs</code> type.</p>
     * @see  io.minio.GetBucketVersioningArgs
     * @see  io.minio.messages.VersioningConfiguration
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.VersioningConfiguration} <p>The get bucket of versioning return object is <code>VersioningConfiguration</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public VersioningConfiguration getBucketOfVersioning(GetBucketVersioningArgs args) throws FileErrorException {
        try {
            return this.getBucketVersioning(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>setObjectLockOfConfiguration</code>
     * <p>The set object lock of configuration setter method.</p>
     * @param args {@link io.minio.SetObjectLockConfigurationArgs} <p>The args parameter is <code>SetObjectLockConfigurationArgs</code> type.</p>
     * @see  io.minio.SetObjectLockConfigurationArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void setObjectLockOfConfiguration(SetObjectLockConfigurationArgs args) throws FileErrorException {
        try {
            this.setObjectLockConfiguration(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>deleteObjectLockOfConfiguration</code>
     * <p>The delete object lock of configuration method.</p>
     * @param args {@link io.minio.DeleteObjectLockConfigurationArgs} <p>The args parameter is <code>DeleteObjectLockConfigurationArgs</code> type.</p>
     * @see  io.minio.DeleteObjectLockConfigurationArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void deleteObjectLockOfConfiguration(DeleteObjectLockConfigurationArgs args) throws FileErrorException {
        try {
            this.deleteObjectLockConfiguration(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }


    }

    /**
     * <code>getObjectLockOfConfiguration</code>
     * <p>The get object lock of configuration getter method.</p>
     * @param args {@link io.minio.GetObjectLockConfigurationArgs} <p>The args parameter is <code>GetObjectLockConfigurationArgs</code> type.</p>
     * @see  io.minio.GetObjectLockConfigurationArgs
     * @see  io.minio.messages.ObjectLockConfiguration
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.ObjectLockConfiguration} <p>The get object lock of configuration return object is <code>ObjectLockConfiguration</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public ObjectLockConfiguration getObjectLockOfConfiguration(GetObjectLockConfigurationArgs args) throws FileErrorException {
        try {
            return this.getObjectLockConfiguration(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>setObjectOfRetention</code>
     * <p>The set object of retention setter method.</p>
     * @param args {@link io.minio.SetObjectRetentionArgs} <p>The args parameter is <code>SetObjectRetentionArgs</code> type.</p>
     * @see  io.minio.SetObjectRetentionArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void setObjectOfRetention(SetObjectRetentionArgs args) throws FileErrorException {
        try {
            this.setObjectRetention(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>getObjectOfRetention</code>
     * <p>The get object of retention getter method.</p>
     * @param args {@link io.minio.GetObjectRetentionArgs} <p>The args parameter is <code>GetObjectRetentionArgs</code> type.</p>
     * @see  io.minio.GetObjectRetentionArgs
     * @see  io.minio.messages.Retention
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.Retention} <p>The get object of retention return object is <code>Retention</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public Retention getObjectOfRetention(GetObjectRetentionArgs args) throws FileErrorException {
        try {
            return this.getObjectRetention(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>enableObjectOfLegalHold</code>
     * <p>The enable object of legal hold method.</p>
     * @param args {@link io.minio.EnableObjectLegalHoldArgs} <p>The args parameter is <code>EnableObjectLegalHoldArgs</code> type.</p>
     * @see  io.minio.EnableObjectLegalHoldArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void enableObjectOfLegalHold(EnableObjectLegalHoldArgs args) throws FileErrorException {
        try {
            this.enableObjectLegalHold(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>disableObjectOfLegalHold</code>
     * <p>The disable object of legal hold method.</p>
     * @param args {@link io.minio.DisableObjectLegalHoldArgs} <p>The args parameter is <code>DisableObjectLegalHoldArgs</code> type.</p>
     * @see  io.minio.DisableObjectLegalHoldArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void disableObjectOfLegalHold(DisableObjectLegalHoldArgs args) throws FileErrorException {
        try {
            this.disableObjectLegalHold(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>isObjectOfLegalHoldEnabled</code>
     * <p>The is object of legal hold enabled method.</p>
     * @param args {@link io.minio.IsObjectLegalHoldEnabledArgs} <p>The args parameter is <code>IsObjectLegalHoldEnabledArgs</code> type.</p>
     * @see  io.minio.IsObjectLegalHoldEnabledArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return boolean <p>The is object of legal hold enabled return object is <code>boolean</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public boolean isObjectOfLegalHoldEnabled(IsObjectLegalHoldEnabledArgs args) throws FileErrorException {
        try {
            return this.isObjectLegalHoldEnabled(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>removeOfBucket</code>
     * <p>The remove of bucket method.</p>
     * @param args {@link io.minio.RemoveBucketArgs} <p>The args parameter is <code>RemoveBucketArgs</code> type.</p>
     * @see  io.minio.RemoveBucketArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void removeOfBucket(RemoveBucketArgs args) throws FileErrorException {
        try {
            this.removeBucket(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_REMOVE_BUCKET_ERROR, exception);
        }

    }

    /**
     * <code>putOfObject</code>
     * <p>The put of object method.</p>
     * @param args {@link io.minio.PutObjectArgs} <p>The args parameter is <code>PutObjectArgs</code> type.</p>
     * @see  io.minio.PutObjectArgs
     * @see  io.minio.ObjectWriteResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.ObjectWriteResponse} <p>The put of object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public ObjectWriteResponse putOfObject(PutObjectArgs args) throws FileErrorException {
        try {
            return this.putObject(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>uploadOfObject</code>
     * <p>The upload of object method.</p>
     * @param args {@link io.minio.UploadObjectArgs} <p>The args parameter is <code>UploadObjectArgs</code> type.</p>
     * @see  io.minio.UploadObjectArgs
     * @see  io.minio.ObjectWriteResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.ObjectWriteResponse} <p>The upload of object return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public ObjectWriteResponse uploadOfObject(UploadObjectArgs args) throws FileErrorException {
        try {
            return this.uploadObject(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_UPLOAD_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>getBucketOfPolicy</code>
     * <p>The get bucket of policy getter method.</p>
     * @param args {@link io.minio.GetBucketPolicyArgs} <p>The args parameter is <code>GetBucketPolicyArgs</code> type.</p>
     * @see  io.minio.GetBucketPolicyArgs
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link java.lang.String} <p>The get bucket of policy return object is <code>String</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public String getBucketOfPolicy(GetBucketPolicyArgs args) throws FileErrorException {
        try {
            return this.getBucketPolicy(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>setBucketOfPolicy</code>
     * <p>The set bucket of policy setter method.</p>
     * @param args {@link io.minio.SetBucketPolicyArgs} <p>The args parameter is <code>SetBucketPolicyArgs</code> type.</p>
     * @see  io.minio.SetBucketPolicyArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void setBucketOfPolicy(SetBucketPolicyArgs args) throws FileErrorException {
        try {
            this.setBucketPolicy(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>deleteBucketOfPolicy</code>
     * <p>The delete bucket of policy method.</p>
     * @param args {@link io.minio.DeleteBucketPolicyArgs} <p>The args parameter is <code>DeleteBucketPolicyArgs</code> type.</p>
     * @see  io.minio.DeleteBucketPolicyArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void deleteBucketOfPolicy(DeleteBucketPolicyArgs args) throws FileErrorException {
        try {
            this.deleteBucketPolicy(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>setBucketOfLifecycle</code>
     * <p>The set bucket of lifecycle setter method.</p>
     * @param args {@link io.minio.SetBucketLifecycleArgs} <p>The args parameter is <code>SetBucketLifecycleArgs</code> type.</p>
     * @see  io.minio.SetBucketLifecycleArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void setBucketOfLifecycle(SetBucketLifecycleArgs args) throws FileErrorException {
        try {
            this.setBucketLifecycle(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>deleteBucketOfLifecycle</code>
     * <p>The delete bucket of lifecycle method.</p>
     * @param args {@link io.minio.DeleteBucketLifecycleArgs} <p>The args parameter is <code>DeleteBucketLifecycleArgs</code> type.</p>
     * @see  io.minio.DeleteBucketLifecycleArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void deleteBucketOfLifecycle(DeleteBucketLifecycleArgs args) throws FileErrorException {
        try {
            this.deleteBucketLifecycle(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>getBucketOfLifecycle</code>
     * <p>The get bucket of lifecycle getter method.</p>
     * @param args {@link io.minio.GetBucketLifecycleArgs} <p>The args parameter is <code>GetBucketLifecycleArgs</code> type.</p>
     * @see  io.minio.GetBucketLifecycleArgs
     * @see  io.minio.messages.LifecycleConfiguration
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.LifecycleConfiguration} <p>The get bucket of lifecycle return object is <code>LifecycleConfiguration</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public LifecycleConfiguration getBucketOfLifecycle(GetBucketLifecycleArgs args) throws FileErrorException {
        try {
            return this.getBucketLifecycle(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>getBucketOfNotification</code>
     * <p>The get bucket of notification getter method.</p>
     * @param args {@link io.minio.GetBucketNotificationArgs} <p>The args parameter is <code>GetBucketNotificationArgs</code> type.</p>
     * @see  io.minio.GetBucketNotificationArgs
     * @see  io.minio.messages.NotificationConfiguration
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.NotificationConfiguration} <p>The get bucket of notification return object is <code>NotificationConfiguration</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public NotificationConfiguration getBucketOfNotification(GetBucketNotificationArgs args) throws FileErrorException {
        try {
            return this.getBucketNotification(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>setBucketOfNotification</code>
     * <p>The set bucket of notification setter method.</p>
     * @param args {@link io.minio.SetBucketNotificationArgs} <p>The args parameter is <code>SetBucketNotificationArgs</code> type.</p>
     * @see  io.minio.SetBucketNotificationArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void setBucketOfNotification(SetBucketNotificationArgs args) throws FileErrorException {
        try {
            this.setBucketNotification(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>deleteBucketOfNotification</code>
     * <p>The delete bucket of notification method.</p>
     * @param args {@link io.minio.DeleteBucketNotificationArgs} <p>The args parameter is <code>DeleteBucketNotificationArgs</code> type.</p>
     * @see  io.minio.DeleteBucketNotificationArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void deleteBucketOfNotification(DeleteBucketNotificationArgs args) throws FileErrorException {
        try {
            this.deleteBucketNotification(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>getBucketOfReplication</code>
     * <p>The get bucket of replication getter method.</p>
     * @param args {@link io.minio.GetBucketReplicationArgs} <p>The args parameter is <code>GetBucketReplicationArgs</code> type.</p>
     * @see  io.minio.GetBucketReplicationArgs
     * @see  io.minio.messages.ReplicationConfiguration
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.ReplicationConfiguration} <p>The get bucket of replication return object is <code>ReplicationConfiguration</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public ReplicationConfiguration getBucketOfReplication(GetBucketReplicationArgs args) throws FileErrorException {
        try {
            return this.getBucketReplication(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>setBucketOfReplication</code>
     * <p>The set bucket of replication setter method.</p>
     * @param args {@link io.minio.SetBucketReplicationArgs} <p>The args parameter is <code>SetBucketReplicationArgs</code> type.</p>
     * @see  io.minio.SetBucketReplicationArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void setBucketOfReplication(SetBucketReplicationArgs args) throws FileErrorException {
        try {
            this.setBucketReplication(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>deleteBucketOfReplication</code>
     * <p>The delete bucket of replication method.</p>
     * @param args {@link io.minio.DeleteBucketReplicationArgs} <p>The args parameter is <code>DeleteBucketReplicationArgs</code> type.</p>
     * @see  io.minio.DeleteBucketReplicationArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void deleteBucketOfReplication(DeleteBucketReplicationArgs args) throws FileErrorException {
        try {
            this.deleteBucketReplication(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }


    /**
     * <code>selectObjectOfContent</code>
     * <p>The select object of content method.</p>
     * @param args {@link io.minio.SelectObjectContentArgs} <p>The args parameter is <code>SelectObjectContentArgs</code> type.</p>
     * @see  io.minio.SelectObjectContentArgs
     * @see  io.minio.SelectResponseStream
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.SelectResponseStream} <p>The select object of content return object is <code>SelectResponseStream</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public SelectResponseStream selectObjectOfContent(SelectObjectContentArgs args) throws FileErrorException {
        try {
            return this.selectObjectContent(args);
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | ServerException | InvalidResponseException |
                 ErrorResponseException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_SELECT_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>setBucketOfEncryption</code>
     * <p>The set bucket of encryption setter method.</p>
     * @param args {@link io.minio.SetBucketEncryptionArgs} <p>The args parameter is <code>SetBucketEncryptionArgs</code> type.</p>
     * @see  io.minio.SetBucketEncryptionArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void setBucketOfEncryption(SetBucketEncryptionArgs args) throws FileErrorException {
        try {
            this.setBucketEncryption(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>getBucketOfEncryption</code>
     * <p>The get bucket of encryption getter method.</p>
     * @param args {@link io.minio.GetBucketEncryptionArgs} <p>The args parameter is <code>GetBucketEncryptionArgs</code> type.</p>
     * @see  io.minio.GetBucketEncryptionArgs
     * @see  io.minio.messages.SseConfiguration
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.SseConfiguration} <p>The get bucket of encryption return object is <code>SseConfiguration</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public SseConfiguration getBucketOfEncryption(GetBucketEncryptionArgs args) throws FileErrorException {
        try {
            return this.getBucketEncryption(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>deleteBucketOfEncryption</code>
     * <p>The delete bucket of encryption method.</p>
     * @param args {@link io.minio.DeleteBucketEncryptionArgs} <p>The args parameter is <code>DeleteBucketEncryptionArgs</code> type.</p>
     * @see  io.minio.DeleteBucketEncryptionArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void deleteBucketOfEncryption(DeleteBucketEncryptionArgs args) throws FileErrorException {
        try {
            this.deleteBucketEncryption(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>getBucketOfTags</code>
     * <p>The get bucket of tags getter method.</p>
     * @param args {@link io.minio.GetBucketTagsArgs} <p>The args parameter is <code>GetBucketTagsArgs</code> type.</p>
     * @see  io.minio.GetBucketTagsArgs
     * @see  io.minio.messages.Tags
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.Tags} <p>The get bucket of tags return object is <code>Tags</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public Tags getBucketOfTags(GetBucketTagsArgs args) throws FileErrorException {
        try {
            return this.getBucketTags(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>setBucketOfTags</code>
     * <p>The set bucket of tags setter method.</p>
     * @param args {@link io.minio.SetBucketTagsArgs} <p>The args parameter is <code>SetBucketTagsArgs</code> type.</p>
     * @see  io.minio.SetBucketTagsArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void setBucketOfTags(SetBucketTagsArgs args) throws FileErrorException {
        try {
            this.setBucketTags(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>deleteBucketOfTags</code>
     * <p>The delete bucket of tags method.</p>
     * @param args {@link io.minio.DeleteBucketTagsArgs} <p>The args parameter is <code>DeleteBucketTagsArgs</code> type.</p>
     * @see  io.minio.DeleteBucketTagsArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void deleteBucketOfTags(DeleteBucketTagsArgs args) throws FileErrorException {
        try {
            this.deleteBucketTags(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>getObjectOfTags</code>
     * <p>The get object of tags getter method.</p>
     * @param args {@link io.minio.GetObjectTagsArgs} <p>The args parameter is <code>GetObjectTagsArgs</code> type.</p>
     * @see  io.minio.GetObjectTagsArgs
     * @see  io.minio.messages.Tags
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.Tags} <p>The get object of tags return object is <code>Tags</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public Tags getObjectOfTags(GetObjectTagsArgs args) throws FileErrorException {
        try {
            return this.getObjectTags(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }

    /**
     * <code>setObjectOfTags</code>
     * <p>The set object of tags setter method.</p>
     * @param args {@link io.minio.SetObjectTagsArgs} <p>The args parameter is <code>SetObjectTagsArgs</code> type.</p>
     * @see  io.minio.SetObjectTagsArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void setObjectOfTags(SetObjectTagsArgs args) throws FileErrorException {
        try {
            this.setObjectTags(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>deleteObjectOfTags</code>
     * <p>The delete object of tags method.</p>
     * @param args {@link io.minio.DeleteObjectTagsArgs} <p>The args parameter is <code>DeleteObjectTagsArgs</code> type.</p>
     * @see  io.minio.DeleteObjectTagsArgs
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public void deleteObjectOfTags(DeleteObjectTagsArgs args) throws FileErrorException {
        try {
            this.deleteObjectTags(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }

    }

    /**
     * <code>uploadSnowballOfObjects</code>
     * <p>The upload snowball of objects method.</p>
     * @param args {@link io.minio.UploadSnowballObjectsArgs} <p>The args parameter is <code>UploadSnowballObjectsArgs</code> type.</p>
     * @see  io.minio.UploadSnowballObjectsArgs
     * @see  io.minio.ObjectWriteResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.ObjectWriteResponse} <p>The upload snowball of objects return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public ObjectWriteResponse uploadSnowballOfObjects(UploadSnowballObjectsArgs args) throws FileErrorException {
        try {
            return this.uploadSnowballObjects(args).get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_UPLOAD_OBJECT_ERROR, exception);
        }
    }

    @Override
    public String getPresignedObjectUrl(GetPresignedObjectUrlArgs args) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, XmlParserException, ServerException {
        this.checkArgs(args);
        byte[] body = args.method() != Method.PUT && args.method() != Method.POST ? null : HttpUtils.EMPTY_BODY;
        Multimap<String, String> queryParams = this.newMultimap(args.extraQueryParams());
        if (args.versionId() != null) {
            queryParams.put("versionId", args.versionId());
        }
        String region = null;
        try {
            region = this.getRegionAsync(args.bucket(), args.region()).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
        }

        if (this.provider == null) {
            HttpUrl url = this.buildUrl(args.method(), args.bucket(), args.object(), region, queryParams);
            return url.toString();
        } else {
            Credentials credentials = this.provider.fetch();
            if (credentials.sessionToken() != null) {
                queryParams.put("X-Amz-Security-Token", credentials.sessionToken());
            }
            HttpUrl httpUrl = this.buildUrl(args.method(), args.bucket(), args.object(), region, queryParams);
            String url = httpUrl.toString();
            OssfileProperties ossfileProperties = MinioContextHolder.ossfileProperties();
            String intranet = ossfileProperties.getIntranet();
            String endpoint = ossfileProperties.getEndpoint();
            if (url.contains(intranet)) {
                url = url.replace(intranet, endpoint);
                httpUrl = HttpUrl.get(url);
            }
            Request request = this.createRequest(httpUrl, args.method(), args.extraHeaders() == null ? null : this.httpHeaders(args.extraHeaders()), body, 0, credentials);
            httpUrl = Signer.presignV4(request, region, credentials.accessKey(), credentials.secretKey(), args.expiry());
            return httpUrl.toString();
        }
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @see  io.minio.messages.InitiateMultipartUploadResult
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName) throws FileErrorException {
        return initiateMultipart(bucketName, objectName, null, null);
    }

    /**
     * <code>initiateMultipartAsync</code>
     * <p>The initiate multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @see  java.util.concurrent.CompletableFuture
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.io.IOException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @return  {@link java.util.concurrent.CompletableFuture} <p>The initiate multipart async return object is <code>CompletableFuture</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     */
    public CompletableFuture<InitiateMultipartUploadResult> initiateMultipartAsync(String bucketName, String objectName) throws FileErrorException, InsufficientDataException, IOException, NoSuchAlgorithmException, InvalidKeyException, XmlParserException, InternalException {
        return initiateMultipartAsync(bucketName, objectName, null, null);
    }

    /**
     * <code>ofInitiateMultipartAsync</code>
     * <p>The of initiate multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @see  java.lang.String
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.io.IOException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of initiate multipart async return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     */
    public RestCompletableFuture<InitiateMultipartUploadResult> ofInitiateMultipartAsync(String bucketName, String objectName) throws RestException, InsufficientDataException, IOException, NoSuchAlgorithmException, InvalidKeyException, XmlParserException, InternalException {
        return ofInitiateMultipartAsync(bucketName, objectName, null, null);
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  io.minio.messages.InitiateMultipartUploadResult
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public InitiateMultipartUploadResult initiateMultipart(String bucketName, String objectName, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        return this.initiateMultipart(bucketName, MinioContextHolder.defaultRegion(), objectName, headers, extraQueryParams);
    }

    /**
     * <code>initiateMultipartAsync</code>
     * <p>The initiate multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  java.util.concurrent.CompletableFuture
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.io.IOException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @return  {@link java.util.concurrent.CompletableFuture} <p>The initiate multipart async return object is <code>CompletableFuture</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     */
    public CompletableFuture<InitiateMultipartUploadResult> initiateMultipartAsync(String bucketName, String objectName, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException, InsufficientDataException, IOException, NoSuchAlgorithmException, InvalidKeyException, XmlParserException, InternalException {
        return initiateMultipartAsync(bucketName, MinioContextHolder.defaultRegion(), objectName, headers, extraQueryParams);
    }

    /**
     * <code>ofInitiateMultipartAsync</code>
     * <p>The of initiate multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.io.IOException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of initiate multipart async return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     */
    public RestCompletableFuture<InitiateMultipartUploadResult> ofInitiateMultipartAsync(String bucketName, String objectName, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws RestException, InsufficientDataException, IOException, NoSuchAlgorithmException, InvalidKeyException, XmlParserException, InternalException {
        return ofInitiateMultipartAsync(bucketName, MinioContextHolder.defaultRegion(), objectName, headers, extraQueryParams);
    }

    /**
     * <code>initiateMultipart</code>
     * <p>The initiate multipart method.</p>
     * @param bucket {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param region {@link java.lang.String} <p>The region parameter is <code>String</code> type.</p>
     * @param object {@link java.lang.String} <p>The object parameter is <code>String</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  io.minio.messages.InitiateMultipartUploadResult
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.messages.InitiateMultipartUploadResult} <p>The initiate multipart return object is <code>InitiateMultipartUploadResult</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public InitiateMultipartUploadResult initiateMultipart(String bucket, String region, String object, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        try {
            CompletableFuture<InitiateMultipartUploadResult> multipartUploadAsync = this.initiateMultipartAsync(bucket, region, object, headers, extraQueryParams);
            return multipartUploadAsync.get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_INITIATE_MULTIPART_ERROR, exception);
        }
    }

    /**
     * <code>initiateMultipartAsync</code>
     * <p>The initiate multipart async method.</p>
     * @param bucket {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param region {@link java.lang.String} <p>The region parameter is <code>String</code> type.</p>
     * @param object {@link java.lang.String} <p>The object parameter is <code>String</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  java.util.concurrent.CompletableFuture
     * @see  java.io.IOException
     * @see  java.security.InvalidKeyException
     * @see  java.security.NoSuchAlgorithmException
     * @see  io.minio.errors.InsufficientDataException
     * @see  io.minio.errors.InternalException
     * @see  io.minio.errors.XmlParserException
     * @return  {@link java.util.concurrent.CompletableFuture} <p>The initiate multipart async return object is <code>CompletableFuture</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     */
    public CompletableFuture<InitiateMultipartUploadResult> initiateMultipartAsync(String bucket, String region, String object, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams)
            throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, XmlParserException {
        CompletableFuture<CreateMultipartUploadResponse> multipartUploadAsync = this.createMultipartUploadAsync(bucket, region, object, headers, extraQueryParams);
        return multipartUploadAsync.thenApply(CreateMultipartUploadResponse::result);
    }

    /**
     * <code>ofInitiateMultipartAsync</code>
     * <p>The of initiate multipart async method.</p>
     * @param bucket {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
     * @param region {@link java.lang.String} <p>The region parameter is <code>String</code> type.</p>
     * @param object {@link java.lang.String} <p>The object parameter is <code>String</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  java.io.IOException
     * @see  java.security.InvalidKeyException
     * @see  java.security.NoSuchAlgorithmException
     * @see  io.minio.errors.InsufficientDataException
     * @see  io.minio.errors.InternalException
     * @see  io.minio.errors.XmlParserException
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of initiate multipart async return object is <code>RestCompletableFuture</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<InitiateMultipartUploadResult> ofInitiateMultipartAsync(String bucket, String region, String object, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams)
            throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, XmlParserException, RestException {
        CompletableFuture<CreateMultipartUploadResponse> multipartUploadAsync = this.createMultipartUploadAsync(bucket, region, object, headers, extraQueryParams);
        return RestCompletableFuture.of(multipartUploadAsync.thenApply(CreateMultipartUploadResponse::result));
    }

    /**
     * <code>uploadMultipart</code>
     * <p>The upload multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partIndex int <p>The part index parameter is <code>int</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partSize long <p>The part size parameter is <code>long</code> type.</p>
     * @see  java.lang.String
     * @see  java.io.InputStream
     * @see  io.minio.UploadPartResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.UploadPartResponse} <p>The upload multipart return object is <code>UploadPartResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public UploadPartResponse uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws FileErrorException {
        return uploadMultipart(bucketName, objectName, uploadId, partIndex, inputStream, partSize, null, null);
    }

    /**
     * <code>uploadMultipartAsync</code>
     * <p>The upload multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partIndex int <p>The part index parameter is <code>int</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partSize long <p>The part size parameter is <code>long</code> type.</p>
     * @see  java.lang.String
     * @see  java.io.InputStream
     * @see  java.util.concurrent.CompletableFuture
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.io.IOException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @return  {@link java.util.concurrent.CompletableFuture} <p>The upload multipart async return object is <code>CompletableFuture</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     */
    public CompletableFuture<UploadPartResponse> uploadMultipartAsync(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws InsufficientDataException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InternalException {
        return uploadMultipartAsync(bucketName, objectName, uploadId, partIndex, inputStream, partSize, null, null);
    }

    /**
     * <code>ofUploadMultipartAsync</code>
     * <p>The of upload multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partIndex int <p>The part index parameter is <code>int</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partSize long <p>The part size parameter is <code>long</code> type.</p>
     * @see  java.lang.String
     * @see  java.io.InputStream
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.io.IOException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of upload multipart async return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     */
    public RestCompletableFuture<UploadPartResponse> ofUploadMultipartAsync(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize) throws RestException, InsufficientDataException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InternalException {
        return RestCompletableFuture.of(uploadMultipartAsync(bucketName, objectName, uploadId, partIndex, inputStream, partSize, null, null));
    }

    /**
     * <code>uploadMultipart</code>
     * <p>The upload multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partIndex int <p>The part index parameter is <code>int</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partSize long <p>The part size parameter is <code>long</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  java.io.InputStream
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  io.minio.UploadPartResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.UploadPartResponse} <p>The upload multipart return object is <code>UploadPartResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public UploadPartResponse uploadMultipart(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws FileErrorException {
        try {
            CompletableFuture<UploadPartResponse> uploadPartAsync = this.uploadPartAsync(bucketName, MinioContextHolder.defaultRegion(), objectName, inputStream, partSize, uploadId, partIndex, headers, extraQueryParams);
            return uploadPartAsync.get();
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | InterruptedException | ExecutionException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_UPLOAD_MULTIPART_ERROR, exception);
        }
    }

    /**
     * <code>uploadMultipartAsync</code>
     * <p>The upload multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partIndex int <p>The part index parameter is <code>int</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partSize long <p>The part size parameter is <code>long</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  java.io.InputStream
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  java.util.concurrent.CompletableFuture
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.io.IOException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @return  {@link java.util.concurrent.CompletableFuture} <p>The upload multipart async return object is <code>CompletableFuture</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     */
    public CompletableFuture<UploadPartResponse> uploadMultipartAsync(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws InsufficientDataException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InternalException {
        return this.uploadPartAsync(bucketName, MinioContextHolder.defaultRegion(), objectName, inputStream, partSize, uploadId, partIndex, headers, extraQueryParams);
    }

    /**
     * <code>ofUploadMultipartAsync</code>
     * <p>The of upload multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partIndex int <p>The part index parameter is <code>int</code> type.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param partSize long <p>The part size parameter is <code>long</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  java.io.InputStream
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.io.IOException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of upload multipart async return object is <code>RestCompletableFuture</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<UploadPartResponse> ofUploadMultipartAsync(String bucketName, String objectName, String uploadId, int partIndex, InputStream inputStream, long partSize, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws InsufficientDataException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InternalException, RestException {
        return RestCompletableFuture.of(this.uploadPartAsync(bucketName, MinioContextHolder.defaultRegion(), objectName, inputStream, partSize, uploadId, partIndex, headers, extraQueryParams));
    }

    @Override
    public CompletableFuture<UploadPartResponse> uploadPartAsync(String bucketName, String region, String objectName, Object data,
                                                                 long length, String uploadId, int partNumber, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams)
            throws InvalidKeyException, InsufficientDataException, InternalException, NoSuchAlgorithmException, XmlParserException, IOException {
        return super.uploadPartAsync(bucketName, region, objectName, data, length, uploadId, partNumber, extraHeaders, extraQueryParams);
    }

    /**
     * <code>ofUploadPartAsync</code>
     * <p>The of upload part async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param region {@link java.lang.String} <p>The region parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param data {@link java.lang.Object} <p>The data parameter is <code>Object</code> type.</p>
     * @param length long <p>The length parameter is <code>long</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param partNumber int <p>The part number parameter is <code>int</code> type.</p>
     * @param extraHeaders {@link com.google.common.collect.Multimap} <p>The extra headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  java.lang.Object
     * @see  com.google.common.collect.Multimap
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.InsufficientDataException
     * @see  io.minio.errors.InternalException
     * @see  java.security.NoSuchAlgorithmException
     * @see  io.minio.errors.XmlParserException
     * @see  java.io.IOException
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of upload part async return object is <code>RestCompletableFuture</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<UploadPartResponse> ofUploadPartAsync(String bucketName, String region, String objectName, Object data,
                                                                       long length, String uploadId, int partNumber, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams)
            throws InvalidKeyException, InsufficientDataException, InternalException, NoSuchAlgorithmException, XmlParserException, IOException, RestException {
        return RestCompletableFuture.of(uploadPartAsync(bucketName, region, objectName, data, length, uploadId, partNumber, extraHeaders, extraQueryParams));
    }

    /**
     * <code>completeMultipart</code>
     * <p>The complete multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param parts {@link java.util.Collection} <p>The parts parameter is <code>Collection</code> type.</p>
     * @see  java.lang.String
     * @see  java.util.Collection
     * @see  io.minio.ObjectWriteResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.ObjectWriteResponse} <p>The complete multipart return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
    public ObjectWriteResponse completeMultipart(String bucketName, String objectName, String uploadId, Collection<Part> parts) throws FileErrorException {
        return completeMultipart(bucketName, objectName, uploadId, parts, null, null);
    }

    /**
     * <code>completeMultipartAsync</code>
     * <p>The complete multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param parts {@link java.util.Collection} <p>The parts parameter is <code>Collection</code> type.</p>
     * @see  java.lang.String
     * @see  java.util.Collection
     * @see  java.util.concurrent.CompletableFuture
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.io.IOException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @return  {@link java.util.concurrent.CompletableFuture} <p>The complete multipart async return object is <code>CompletableFuture</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     */
    public CompletableFuture<ObjectWriteResponse> completeMultipartAsync(String bucketName, String objectName, String uploadId, Collection<Part> parts) throws InsufficientDataException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InternalException {
        return completeMultipartAsync(bucketName, objectName, uploadId, parts, null, null);

    }

    /**
     * <code>ofCompleteMultipartAsync</code>
     * <p>The of complete multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param parts {@link java.util.Collection} <p>The parts parameter is <code>Collection</code> type.</p>
     * @see  java.lang.String
     * @see  java.util.Collection
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.io.IOException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of complete multipart async return object is <code>RestCompletableFuture</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<ObjectWriteResponse> ofCompleteMultipartAsync(String bucketName, String objectName, String uploadId, Collection<Part> parts) throws InsufficientDataException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InternalException, RestException {
        return ofCompleteMultipartAsync(bucketName, objectName, uploadId, parts, null, null);
    }

    /**
     * <code>completeMultipart</code>
     * <p>The complete multipart method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param parts {@link java.util.Collection} <p>The parts parameter is <code>Collection</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  java.util.Collection
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  io.minio.ObjectWriteResponse
     * @see  io.github.nichetoolkit.rest.error.natives.FileErrorException
     * @return  {@link io.minio.ObjectWriteResponse} <p>The complete multipart return object is <code>ObjectWriteResponse</code> type.</p>
     * @throws FileErrorException {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error exception is <code>FileErrorException</code> type.</p>
     */
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

    /**
     * <code>completeMultipartAsync</code>
     * <p>The complete multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param parts {@link java.util.Collection} <p>The parts parameter is <code>Collection</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  java.util.Collection
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  java.util.concurrent.CompletableFuture
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.io.IOException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @return  {@link java.util.concurrent.CompletableFuture} <p>The complete multipart async return object is <code>CompletableFuture</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     */
    public CompletableFuture<ObjectWriteResponse> completeMultipartAsync(String bucketName, String objectName, String uploadId, Collection<Part> parts, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws InsufficientDataException, IOException, NoSuchAlgorithmException, InvalidKeyException, XmlParserException, InternalException {
        Part[] partsArray = parts.toArray(new Part[0]);
        return this.completeMultipartUploadAsync(bucketName, MinioContextHolder.defaultRegion(), objectName, uploadId, partsArray, headers, extraQueryParams);
    }

    /**
     * <code>ofCompleteMultipartAsync</code>
     * <p>The of complete multipart async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param parts {@link java.util.Collection} <p>The parts parameter is <code>Collection</code> type.</p>
     * @param headers {@link com.google.common.collect.Multimap} <p>The headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  java.util.Collection
     * @see  com.google.common.collect.Multimap
     * @see  org.springframework.lang.Nullable
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.io.IOException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of complete multipart async return object is <code>RestCompletableFuture</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<ObjectWriteResponse> ofCompleteMultipartAsync(String bucketName, String objectName, String uploadId, Collection<Part> parts, @Nullable Multimap<String, String> headers, @Nullable Multimap<String, String> extraQueryParams) throws InsufficientDataException, IOException, NoSuchAlgorithmException, InvalidKeyException, XmlParserException, InternalException, RestException {
        return RestCompletableFuture.of(this.completeMultipartAsync(bucketName, objectName, uploadId, parts, headers, extraQueryParams));
    }

    @Override
    public CompletableFuture<ObjectWriteResponse> completeMultipartUploadAsync(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams)
            throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams);
    }

    /**
     * <code>ofCompleteMultipartUploadAsync</code>
     * <p>The of complete multipart upload async method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @param region {@link java.lang.String} <p>The region parameter is <code>String</code> type.</p>
     * @param objectName {@link java.lang.String} <p>The object name parameter is <code>String</code> type.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @param parts {@link io.minio.messages.Part} <p>The parts parameter is <code>Part</code> type.</p>
     * @param extraHeaders {@link com.google.common.collect.Multimap} <p>The extra headers parameter is <code>Multimap</code> type.</p>
     * @param extraQueryParams {@link com.google.common.collect.Multimap} <p>The extra query params parameter is <code>Multimap</code> type.</p>
     * @see  java.lang.String
     * @see  io.minio.messages.Part
     * @see  com.google.common.collect.Multimap
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @see  io.minio.errors.InsufficientDataException
     * @see  java.io.IOException
     * @see  java.security.NoSuchAlgorithmException
     * @see  java.security.InvalidKeyException
     * @see  io.minio.errors.XmlParserException
     * @see  io.minio.errors.InternalException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of complete multipart upload async return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @throws InsufficientDataException {@link io.minio.errors.InsufficientDataException} <p>The insufficient data exception is <code>InsufficientDataException</code> type.</p>
     * @throws IOException {@link java.io.IOException} <p>The io exception is <code>IOException</code> type.</p>
     * @throws NoSuchAlgorithmException {@link java.security.NoSuchAlgorithmException} <p>The no such algorithm exception is <code>NoSuchAlgorithmException</code> type.</p>
     * @throws InvalidKeyException {@link java.security.InvalidKeyException} <p>The invalid key exception is <code>InvalidKeyException</code> type.</p>
     * @throws XmlParserException {@link io.minio.errors.XmlParserException} <p>The xml parser exception is <code>XmlParserException</code> type.</p>
     * @throws InternalException {@link io.minio.errors.InternalException} <p>The internal exception is <code>InternalException</code> type.</p>
     */
    public RestCompletableFuture<ObjectWriteResponse> ofCompleteMultipartUploadAsync(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws RestException, InsufficientDataException, IOException, NoSuchAlgorithmException, InvalidKeyException, XmlParserException, InternalException {
        return RestCompletableFuture.of(completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams));
    }

    /**
     * <code>ofStatObject</code>
     * <p>The of stat object method.</p>
     * @param args {@link io.minio.StatObjectArgs} <p>The args parameter is <code>StatObjectArgs</code> type.</p>
     * @see  io.minio.StatObjectArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of stat object return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<StatObjectResponse> ofStatObject(StatObjectArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.statObject(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_STAT_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>ofGetObject</code>
     * <p>The of get object method.</p>
     * @param args {@link io.minio.GetObjectArgs} <p>The args parameter is <code>GetObjectArgs</code> type.</p>
     * @see  io.minio.GetObjectArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get object return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<GetObjectResponse> ofGetObject(GetObjectArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getObject(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_GET_OBJECT_ERROR, exception);
        }
    }


    /**
     * <code>ofDownloadObject</code>
     * <p>The of download object method.</p>
     * @param args {@link io.minio.DownloadObjectArgs} <p>The args parameter is <code>DownloadObjectArgs</code> type.</p>
     * @see  io.minio.DownloadObjectArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of download object return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofDownloadObject(DownloadObjectArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.downloadObject(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_DOWNLOAD_OBJECT_ERROR, exception);
        }
    }


    /**
     * <code>ofCopyObject</code>
     * <p>The of copy object method.</p>
     * @param args {@link io.minio.CopyObjectArgs} <p>The args parameter is <code>CopyObjectArgs</code> type.</p>
     * @see  io.minio.CopyObjectArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of copy object return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<ObjectWriteResponse> ofCopyObject(CopyObjectArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.copyObject(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_COPY_OBJECT_ERROR, exception);
        }
    }


    /**
     * <code>ofComposeObject</code>
     * <p>The of compose object method.</p>
     * @param args {@link io.minio.ComposeObjectArgs} <p>The args parameter is <code>ComposeObjectArgs</code> type.</p>
     * @see  io.minio.ComposeObjectArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of compose object return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<ObjectWriteResponse> ofComposeObject(ComposeObjectArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.composeObject(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_COMPOSE_OBJECT_ERROR, exception);
        }
    }


    /**
     * <code>ofRemoveObject</code>
     * <p>The of remove object method.</p>
     * @param args {@link io.minio.RemoveObjectArgs} <p>The args parameter is <code>RemoveObjectArgs</code> type.</p>
     * @see  io.minio.RemoveObjectArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of remove object return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofRemoveObject(RemoveObjectArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.removeObject(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_REMOVE_OBJECT_ERROR, exception);
        }
    }


    /**
     * <code>ofRestoreObject</code>
     * <p>The of restore object method.</p>
     * @param args {@link io.minio.RestoreObjectArgs} <p>The args parameter is <code>RestoreObjectArgs</code> type.</p>
     * @see  io.minio.RestoreObjectArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of restore object return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofRestoreObject(RestoreObjectArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.restoreObject(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_RESTORE_OBJECT_ERROR, exception);
        }
    }


    /**
     * <code>ofListBuckets</code>
     * <p>The of list buckets method.</p>
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of list buckets return object is <code>RestCompletableFuture</code> type.</p>
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<List<Bucket>> ofListBuckets() throws RestException {
        try {
            return RestCompletableFuture.of(super.listBuckets());
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception);
        }
    }


    /**
     * <code>ofListBuckets</code>
     * <p>The of list buckets method.</p>
     * @param args {@link io.minio.ListBucketsArgs} <p>The args parameter is <code>ListBucketsArgs</code> type.</p>
     * @see  io.minio.ListBucketsArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of list buckets return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<List<Bucket>> ofListBuckets(ListBucketsArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.listBuckets(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_LIST_ALL_BUCKETS_ERROR, exception);
        }
    }


    /**
     * <code>ofBucketExists</code>
     * <p>The of bucket exists method.</p>
     * @param args {@link io.minio.BucketExistsArgs} <p>The args parameter is <code>BucketExistsArgs</code> type.</p>
     * @see  io.minio.BucketExistsArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of bucket exists return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Boolean> ofBucketExists(BucketExistsArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.bucketExists(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_EXIST_BUCKET_ERROR, exception);
        }
    }


    /**
     * <code>ofMakeBucket</code>
     * <p>The of make bucket method.</p>
     * @param args {@link io.minio.MakeBucketArgs} <p>The args parameter is <code>MakeBucketArgs</code> type.</p>
     * @see  io.minio.MakeBucketArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of make bucket return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofMakeBucket(MakeBucketArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.makeBucket(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_MAKE_BUCKET_ERROR, exception);
        }
    }


    /**
     * <code>ofSetBucketVersioning</code>
     * <p>The of set bucket versioning method.</p>
     * @param args {@link io.minio.SetBucketVersioningArgs} <p>The args parameter is <code>SetBucketVersioningArgs</code> type.</p>
     * @see  io.minio.SetBucketVersioningArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of set bucket versioning return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofSetBucketVersioning(SetBucketVersioningArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.setBucketVersioning(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofGetBucketVersioning</code>
     * <p>The of get bucket versioning method.</p>
     * @param args {@link io.minio.GetBucketVersioningArgs} <p>The args parameter is <code>GetBucketVersioningArgs</code> type.</p>
     * @see  io.minio.GetBucketVersioningArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get bucket versioning return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<VersioningConfiguration> ofGetBucketVersioning(GetBucketVersioningArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getBucketVersioning(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofSetObjectLockConfiguration</code>
     * <p>The of set object lock configuration method.</p>
     * @param args {@link io.minio.SetObjectLockConfigurationArgs} <p>The args parameter is <code>SetObjectLockConfigurationArgs</code> type.</p>
     * @see  io.minio.SetObjectLockConfigurationArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of set object lock configuration return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofSetObjectLockConfiguration(SetObjectLockConfigurationArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.setObjectLockConfiguration(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofDeleteObjectLockConfiguration</code>
     * <p>The of delete object lock configuration method.</p>
     * @param args {@link io.minio.DeleteObjectLockConfigurationArgs} <p>The args parameter is <code>DeleteObjectLockConfigurationArgs</code> type.</p>
     * @see  io.minio.DeleteObjectLockConfigurationArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of delete object lock configuration return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofDeleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.deleteObjectLockConfiguration(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofGetObjectLockConfiguration</code>
     * <p>The of get object lock configuration method.</p>
     * @param args {@link io.minio.GetObjectLockConfigurationArgs} <p>The args parameter is <code>GetObjectLockConfigurationArgs</code> type.</p>
     * @see  io.minio.GetObjectLockConfigurationArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get object lock configuration return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<ObjectLockConfiguration> ofGetObjectLockConfiguration(GetObjectLockConfigurationArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getObjectLockConfiguration(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofSetObjectRetention</code>
     * <p>The of set object retention method.</p>
     * @param args {@link io.minio.SetObjectRetentionArgs} <p>The args parameter is <code>SetObjectRetentionArgs</code> type.</p>
     * @see  io.minio.SetObjectRetentionArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of set object retention return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofSetObjectRetention(SetObjectRetentionArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.setObjectRetention(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofGetObjectRetention</code>
     * <p>The of get object retention method.</p>
     * @param args {@link io.minio.GetObjectRetentionArgs} <p>The args parameter is <code>GetObjectRetentionArgs</code> type.</p>
     * @see  io.minio.GetObjectRetentionArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get object retention return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Retention> ofGetObjectRetention(GetObjectRetentionArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getObjectRetention(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofEnableObjectLegalHold</code>
     * <p>The of enable object legal hold method.</p>
     * @param args {@link io.minio.EnableObjectLegalHoldArgs} <p>The args parameter is <code>EnableObjectLegalHoldArgs</code> type.</p>
     * @see  io.minio.EnableObjectLegalHoldArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of enable object legal hold return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofEnableObjectLegalHold(EnableObjectLegalHoldArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.enableObjectLegalHold(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofDisableObjectLegalHold</code>
     * <p>The of disable object legal hold method.</p>
     * @param args {@link io.minio.DisableObjectLegalHoldArgs} <p>The args parameter is <code>DisableObjectLegalHoldArgs</code> type.</p>
     * @see  io.minio.DisableObjectLegalHoldArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of disable object legal hold return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofDisableObjectLegalHold(DisableObjectLegalHoldArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.disableObjectLegalHold(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofIsObjectLegalHoldEnabled</code>
     * <p>The of is object legal hold enabled method.</p>
     * @param args {@link io.minio.IsObjectLegalHoldEnabledArgs} <p>The args parameter is <code>IsObjectLegalHoldEnabledArgs</code> type.</p>
     * @see  io.minio.IsObjectLegalHoldEnabledArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of is object legal hold enabled return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Boolean> ofIsObjectLegalHoldEnabled(IsObjectLegalHoldEnabledArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.isObjectLegalHoldEnabled(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofRemoveBucket</code>
     * <p>The of remove bucket method.</p>
     * @param args {@link io.minio.RemoveBucketArgs} <p>The args parameter is <code>RemoveBucketArgs</code> type.</p>
     * @see  io.minio.RemoveBucketArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of remove bucket return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofRemoveBucket(RemoveBucketArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.removeBucket(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_REMOVE_BUCKET_ERROR, exception);
        }
    }


    /**
     * <code>ofPutObject</code>
     * <p>The of put object method.</p>
     * @param args {@link io.minio.PutObjectArgs} <p>The args parameter is <code>PutObjectArgs</code> type.</p>
     * @see  io.minio.PutObjectArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of put object return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<ObjectWriteResponse> ofPutObject(PutObjectArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.putObject(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_PUT_OBJECT_ERROR, exception);
        }
    }


    /**
     * <code>ofUploadObject</code>
     * <p>The of upload object method.</p>
     * @param args {@link io.minio.UploadObjectArgs} <p>The args parameter is <code>UploadObjectArgs</code> type.</p>
     * @see  io.minio.UploadObjectArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of upload object return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<ObjectWriteResponse> ofUploadObject(UploadObjectArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.uploadObject(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_UPLOAD_OBJECT_ERROR, exception);
        }
    }


    /**
     * <code>ofGetBucketPolicy</code>
     * <p>The of get bucket policy method.</p>
     * @param args {@link io.minio.GetBucketPolicyArgs} <p>The args parameter is <code>GetBucketPolicyArgs</code> type.</p>
     * @see  io.minio.GetBucketPolicyArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get bucket policy return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<String> ofGetBucketPolicy(GetBucketPolicyArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getBucketPolicy(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofDeleteBucketPolicy</code>
     * <p>The of delete bucket policy method.</p>
     * @param args {@link io.minio.DeleteBucketPolicyArgs} <p>The args parameter is <code>DeleteBucketPolicyArgs</code> type.</p>
     * @see  io.minio.DeleteBucketPolicyArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of delete bucket policy return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofDeleteBucketPolicy(DeleteBucketPolicyArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.deleteBucketPolicy(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofSetBucketPolicy</code>
     * <p>The of set bucket policy method.</p>
     * @param args {@link io.minio.SetBucketPolicyArgs} <p>The args parameter is <code>SetBucketPolicyArgs</code> type.</p>
     * @see  io.minio.SetBucketPolicyArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of set bucket policy return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofSetBucketPolicy(SetBucketPolicyArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.setBucketPolicy(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofSetBucketLifecycle</code>
     * <p>The of set bucket lifecycle method.</p>
     * @param args {@link io.minio.SetBucketLifecycleArgs} <p>The args parameter is <code>SetBucketLifecycleArgs</code> type.</p>
     * @see  io.minio.SetBucketLifecycleArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of set bucket lifecycle return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofSetBucketLifecycle(SetBucketLifecycleArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.setBucketLifecycle(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofDeleteBucketLifecycle</code>
     * <p>The of delete bucket lifecycle method.</p>
     * @param args {@link io.minio.DeleteBucketLifecycleArgs} <p>The args parameter is <code>DeleteBucketLifecycleArgs</code> type.</p>
     * @see  io.minio.DeleteBucketLifecycleArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of delete bucket lifecycle return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofDeleteBucketLifecycle(DeleteBucketLifecycleArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.deleteBucketLifecycle(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofGetBucketLifecycle</code>
     * <p>The of get bucket lifecycle method.</p>
     * @param args {@link io.minio.GetBucketLifecycleArgs} <p>The args parameter is <code>GetBucketLifecycleArgs</code> type.</p>
     * @see  io.minio.GetBucketLifecycleArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get bucket lifecycle return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<LifecycleConfiguration> ofGetBucketLifecycle(GetBucketLifecycleArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getBucketLifecycle(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofGetBucketNotification</code>
     * <p>The of get bucket notification method.</p>
     * @param args {@link io.minio.GetBucketNotificationArgs} <p>The args parameter is <code>GetBucketNotificationArgs</code> type.</p>
     * @see  io.minio.GetBucketNotificationArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get bucket notification return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<NotificationConfiguration> ofGetBucketNotification(GetBucketNotificationArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getBucketNotification(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofSetBucketNotification</code>
     * <p>The of set bucket notification method.</p>
     * @param args {@link io.minio.SetBucketNotificationArgs} <p>The args parameter is <code>SetBucketNotificationArgs</code> type.</p>
     * @see  io.minio.SetBucketNotificationArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of set bucket notification return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofSetBucketNotification(SetBucketNotificationArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.setBucketNotification(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofDeleteBucketNotification</code>
     * <p>The of delete bucket notification method.</p>
     * @param args {@link io.minio.DeleteBucketNotificationArgs} <p>The args parameter is <code>DeleteBucketNotificationArgs</code> type.</p>
     * @see  io.minio.DeleteBucketNotificationArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of delete bucket notification return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofDeleteBucketNotification(DeleteBucketNotificationArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.deleteBucketNotification(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofGetBucketReplication</code>
     * <p>The of get bucket replication method.</p>
     * @param args {@link io.minio.GetBucketReplicationArgs} <p>The args parameter is <code>GetBucketReplicationArgs</code> type.</p>
     * @see  io.minio.GetBucketReplicationArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get bucket replication return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<ReplicationConfiguration> ofGetBucketReplication(GetBucketReplicationArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getBucketReplication(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofSetBucketReplication</code>
     * <p>The of set bucket replication method.</p>
     * @param args {@link io.minio.SetBucketReplicationArgs} <p>The args parameter is <code>SetBucketReplicationArgs</code> type.</p>
     * @see  io.minio.SetBucketReplicationArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of set bucket replication return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofSetBucketReplication(SetBucketReplicationArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.setBucketReplication(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofDeleteBucketReplication</code>
     * <p>The of delete bucket replication method.</p>
     * @param args {@link io.minio.DeleteBucketReplicationArgs} <p>The args parameter is <code>DeleteBucketReplicationArgs</code> type.</p>
     * @see  io.minio.DeleteBucketReplicationArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of delete bucket replication return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofDeleteBucketReplication(DeleteBucketReplicationArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.deleteBucketReplication(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofSetBucketEncryption</code>
     * <p>The of set bucket encryption method.</p>
     * @param args {@link io.minio.SetBucketEncryptionArgs} <p>The args parameter is <code>SetBucketEncryptionArgs</code> type.</p>
     * @see  io.minio.SetBucketEncryptionArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of set bucket encryption return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofSetBucketEncryption(SetBucketEncryptionArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.setBucketEncryption(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofGetBucketEncryption</code>
     * <p>The of get bucket encryption method.</p>
     * @param args {@link io.minio.GetBucketEncryptionArgs} <p>The args parameter is <code>GetBucketEncryptionArgs</code> type.</p>
     * @see  io.minio.GetBucketEncryptionArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get bucket encryption return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<SseConfiguration> ofGetBucketEncryption(GetBucketEncryptionArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getBucketEncryption(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofDeleteBucketEncryption</code>
     * <p>The of delete bucket encryption method.</p>
     * @param args {@link io.minio.DeleteBucketEncryptionArgs} <p>The args parameter is <code>DeleteBucketEncryptionArgs</code> type.</p>
     * @see  io.minio.DeleteBucketEncryptionArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of delete bucket encryption return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofDeleteBucketEncryption(DeleteBucketEncryptionArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.deleteBucketEncryption(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofGetBucketTags</code>
     * <p>The of get bucket tags method.</p>
     * @param args {@link io.minio.GetBucketTagsArgs} <p>The args parameter is <code>GetBucketTagsArgs</code> type.</p>
     * @see  io.minio.GetBucketTagsArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get bucket tags return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Tags> ofGetBucketTags(GetBucketTagsArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getBucketTags(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofSetBucketTags</code>
     * <p>The of set bucket tags method.</p>
     * @param args {@link io.minio.SetBucketTagsArgs} <p>The args parameter is <code>SetBucketTagsArgs</code> type.</p>
     * @see  io.minio.SetBucketTagsArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of set bucket tags return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofSetBucketTags(SetBucketTagsArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.setBucketTags(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofDeleteBucketTags</code>
     * <p>The of delete bucket tags method.</p>
     * @param args {@link io.minio.DeleteBucketTagsArgs} <p>The args parameter is <code>DeleteBucketTagsArgs</code> type.</p>
     * @see  io.minio.DeleteBucketTagsArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of delete bucket tags return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofDeleteBucketTags(DeleteBucketTagsArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.deleteBucketTags(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_BUCKET_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofGetObjectTags</code>
     * <p>The of get object tags method.</p>
     * @param args {@link io.minio.GetObjectTagsArgs} <p>The args parameter is <code>GetObjectTagsArgs</code> type.</p>
     * @see  io.minio.GetObjectTagsArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of get object tags return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Tags> ofGetObjectTags(GetObjectTagsArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.getObjectTags(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofSetObjectTags</code>
     * <p>The of set object tags method.</p>
     * @param args {@link io.minio.SetObjectTagsArgs} <p>The args parameter is <code>SetObjectTagsArgs</code> type.</p>
     * @see  io.minio.SetObjectTagsArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of set object tags return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofSetObjectTags(SetObjectTagsArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.setObjectTags(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofDeleteObjectTags</code>
     * <p>The of delete object tags method.</p>
     * @param args {@link io.minio.DeleteObjectTagsArgs} <p>The args parameter is <code>DeleteObjectTagsArgs</code> type.</p>
     * @see  io.minio.DeleteObjectTagsArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of delete object tags return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<Void> ofDeleteObjectTags(DeleteObjectTagsArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.deleteObjectTags(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_OBJECT_CONFIG_ERROR, exception);
        }
    }


    /**
     * <code>ofUploadSnowballObjects</code>
     * <p>The of upload snowball objects method.</p>
     * @param args {@link io.minio.UploadSnowballObjectsArgs} <p>The args parameter is <code>UploadSnowballObjectsArgs</code> type.</p>
     * @see  io.minio.UploadSnowballObjectsArgs
     * @see  io.github.nichetoolkit.rest.future.RestCompletableFuture
     * @see  io.github.nichetoolkit.rest.RestException
     * @return  {@link io.github.nichetoolkit.rest.future.RestCompletableFuture} <p>The of upload snowball objects return object is <code>RestCompletableFuture</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     */
    public RestCompletableFuture<ObjectWriteResponse> ofUploadSnowballObjects(UploadSnowballObjectsArgs args) throws RestException {
        try {
            return RestCompletableFuture.of(super.uploadSnowballObjects(args));
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException exception) {
            throw OssfileStoreHolder.fileErrorOfCause(OssfileErrorStatus.OSSFILE_UPLOAD_OBJECT_ERROR, exception);
        }
    }

    /**
     * <code>ofBuilder</code>
     * <p>The of builder method.</p>
     * @return  {@link io.github.nichetoolkit.ossfile.OssfileMinioClient.Builder} <p>The of builder return object is <code>Builder</code> type.</p>
     * @see  io.github.nichetoolkit.ossfile.OssfileMinioClient.Builder
     */
    public static OssfileMinioClient.Builder ofBuilder() {
        return new OssfileMinioClient.Builder();
    }

    /**
     * <code>Builder</code>
     * <p>The builder class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @since Jdk17
     */
    public static final class Builder {
        /**
         * <code>builder</code>
         * {@link io.minio.MinioAsyncClient.Builder} <p>The <code>builder</code> field.</p>
         * @see  io.minio.MinioAsyncClient.Builder
         */
        private MinioAsyncClient.Builder builder;

        /**
         * <code>Builder</code>
         * <p>Instantiates a new builder.</p>
         */
        public Builder() {
            this.builder = MinioAsyncClient.builder();
        }

        /**
         * <code>endpoint</code>
         * <p>The endpoint method.</p>
         * @param endpoint {@link java.lang.String} <p>The endpoint parameter is <code>String</code> type.</p>
         * @see  java.lang.String
         * @return  {@link io.github.nichetoolkit.ossfile.OssfileMinioClient.Builder} <p>The endpoint return object is <code>Builder</code> type.</p>
         */
        public OssfileMinioClient.Builder endpoint(String endpoint) {
            this.builder.endpoint(endpoint);
            return this;
        }

        /**
         * <code>endpoint</code>
         * <p>The endpoint method.</p>
         * @param endpoint {@link java.lang.String} <p>The endpoint parameter is <code>String</code> type.</p>
         * @param port int <p>The port parameter is <code>int</code> type.</p>
         * @param secure boolean <p>The secure parameter is <code>boolean</code> type.</p>
         * @see  java.lang.String
         * @return  {@link io.github.nichetoolkit.ossfile.OssfileMinioClient.Builder} <p>The endpoint return object is <code>Builder</code> type.</p>
         */
        public OssfileMinioClient.Builder endpoint(String endpoint, int port, boolean secure) {
            this.builder.endpoint(endpoint, port, secure);
            return this;
        }

        /**
         * <code>endpoint</code>
         * <p>The endpoint method.</p>
         * @param url {@link java.net.URL} <p>The url parameter is <code>URL</code> type.</p>
         * @see  java.net.URL
         * @return  {@link io.github.nichetoolkit.ossfile.OssfileMinioClient.Builder} <p>The endpoint return object is <code>Builder</code> type.</p>
         */
        public OssfileMinioClient.Builder endpoint(URL url) {
            this.builder.endpoint(url);
            return this;
        }

        /**
         * <code>endpoint</code>
         * <p>The endpoint method.</p>
         * @param url {@link okhttp3.HttpUrl} <p>The url parameter is <code>HttpUrl</code> type.</p>
         * @see  okhttp3.HttpUrl
         * @return  {@link io.github.nichetoolkit.ossfile.OssfileMinioClient.Builder} <p>The endpoint return object is <code>Builder</code> type.</p>
         */
        public OssfileMinioClient.Builder endpoint(HttpUrl url) {
            this.builder.endpoint(url);
            return this;
        }

        /**
         * <code>region</code>
         * <p>The region method.</p>
         * @param region {@link java.lang.String} <p>The region parameter is <code>String</code> type.</p>
         * @see  java.lang.String
         * @return  {@link io.github.nichetoolkit.ossfile.OssfileMinioClient.Builder} <p>The region return object is <code>Builder</code> type.</p>
         */
        public OssfileMinioClient.Builder region(String region) {
            this.builder.region(region);
            return this;
        }

        /**
         * <code>credentials</code>
         * <p>The credentials method.</p>
         * @param accessKey {@link java.lang.String} <p>The access key parameter is <code>String</code> type.</p>
         * @param secretKey {@link java.lang.String} <p>The secret key parameter is <code>String</code> type.</p>
         * @see  java.lang.String
         * @return  {@link io.github.nichetoolkit.ossfile.OssfileMinioClient.Builder} <p>The credentials return object is <code>Builder</code> type.</p>
         */
        public OssfileMinioClient.Builder credentials(String accessKey, String secretKey) {
            this.builder.credentials(accessKey, secretKey);
            return this;
        }

        /**
         * <code>credentialsProvider</code>
         * <p>The credentials provider method.</p>
         * @param provider {@link io.minio.credentials.Provider} <p>The provider parameter is <code>Provider</code> type.</p>
         * @see  io.minio.credentials.Provider
         * @return  {@link io.github.nichetoolkit.ossfile.OssfileMinioClient.Builder} <p>The credentials provider return object is <code>Builder</code> type.</p>
         */
        public OssfileMinioClient.Builder credentialsProvider(Provider provider) {
            this.builder.credentialsProvider(provider);
            return this;
        }

        /**
         * <code>httpClient</code>
         * <p>The http client method.</p>
         * @param httpClient {@link okhttp3.OkHttpClient} <p>The http client parameter is <code>OkHttpClient</code> type.</p>
         * @see  okhttp3.OkHttpClient
         * @return  {@link io.github.nichetoolkit.ossfile.OssfileMinioClient.Builder} <p>The http client return object is <code>Builder</code> type.</p>
         */
        public OssfileMinioClient.Builder httpClient(OkHttpClient httpClient) {
            this.builder.httpClient(httpClient);
            return this;
        }

        /**
         * <code>build</code>
         * <p>The build method.</p>
         * @return  {@link io.github.nichetoolkit.ossfile.OssfileMinioClient} <p>The build return object is <code>OssfileMinioClient</code> type.</p>
         */
        public OssfileMinioClient build() {
            return new OssfileMinioClient(this.builder.build());
        }
    }
}
