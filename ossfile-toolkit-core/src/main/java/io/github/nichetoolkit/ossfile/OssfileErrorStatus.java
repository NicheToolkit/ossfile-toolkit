package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.RestStatus;
import lombok.Getter;

/**
 * <code>OssfileErrorStatus</code>
 * <p>The ossfile error status enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestStatus
 * @see lombok.Getter
 * @since Jdk1.8
 */
@Getter
public enum OssfileErrorStatus implements RestStatus {

    /**
     * <code>OSSFILE_DOWNLOAD_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_DOWNLOAD_ERROR</code> field.</p>
     */
    OSSFILE_DOWNLOAD_ERROR(11000, "The server has encountered an error with file download."),
    /**
     * <code>OSSFILE_FINISHED_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_FINISHED_ERROR</code> field.</p>
     */
    OSSFILE_FINISHED_ERROR(11002, "The file cannot be downloaded as it has not been uploaded or merged."),
    /**
     * <code>OSSFILE_CONTENT_RANGE_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_CONTENT_RANGE_ERROR</code> field.</p>
     */
    OSSFILE_CONTENT_RANGE_ERROR(11003, "The fragment upload request header Content-Range parsing error."),

    /**
     * <code>OSSFILE_READ_STREAM_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_READ_STREAM_ERROR</code> field.</p>
     */
    OSSFILE_READ_STREAM_ERROR(11006, "The ossfile server has encountered an error with stream read."),
    /**
     * <code>OSSFILE_READ_BYTE_NULL</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_READ_BYTE_NULL</code> field.</p>
     */
    OSSFILE_READ_BYTE_NULL(11007, "The ossfile server has encountered an error with data read."),
    /**
     * <code>OSSFILE_READ_BYTE_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_READ_BYTE_ERROR</code> field.</p>
     */
    OSSFILE_READ_BYTE_ERROR(11008, "The ossfile server has encountered an error with file data read."),
    /**
     * <code>OSSFILE_CONDENSE_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_CONDENSE_ERROR</code> field.</p>
     */
    OSSFILE_CONDENSE_ERROR(11009, "The ossfile server has encountered an error with file compression."),
    /**
     * <code>OSSFILE_UNSUPPORTED_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_UNSUPPORTED_ERROR</code> field.</p>
     */
    OSSFILE_UNSUPPORTED_ERROR(11011, "The ossfile server has encountered an error with file unsupported."),

    /**
     * <code>OSSFILE_CREDENTIALS_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_CREDENTIALS_ERROR</code> field.</p>
     */
    OSSFILE_CREDENTIALS_ERROR(11012, "The ossfile server has encountered an error with serve credentials."),
    /**
     * <code>OSSFILE_BUCKET_CORS_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_BUCKET_CORS_ERROR</code> field.</p>
     */
    OSSFILE_BUCKET_CORS_ERROR(11013, "The ossfile server has encountered an error with setting bucket CORS."),

    /**
     * <code>OSSFILE_INITIATE_MULTIPART_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_INITIATE_MULTIPART_ERROR</code> field.</p>
     */
    OSSFILE_INITIATE_MULTIPART_ERROR(11014, "The ossfile server has encountered an error with initiate multipart."),
    /**
     * <code>OSSFILE_UPLOAD_MULTIPART_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_UPLOAD_MULTIPART_ERROR</code> field.</p>
     */
    OSSFILE_UPLOAD_MULTIPART_ERROR(11015, "The ossfile server has encountered an error with upload multipart."),
    /**
     * <code>OSSFILE_COMPLETE_MULTIPART_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_COMPLETE_MULTIPART_ERROR</code> field.</p>
     */
    OSSFILE_COMPLETE_MULTIPART_ERROR(11016, "The ossfile server has encountered an error with complete multipart."),

    /**
     * <code>OSSFILE_SERVER_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_SERVER_ERROR</code> field.</p>
     */
    OSSFILE_SERVER_ERROR(11100, "The ossfile server has encountered an error."),
    /**
     * <code>OSSFILE_CONFIG_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_CONFIG_ERROR</code> field.</p>
     */
    OSSFILE_CONFIG_ERROR(110101, "The ossfile server has encountered an error with ossfile configuration."),
    /**
     * <code>OSSFILE_BUCKET_POLICY_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_BUCKET_POLICY_ERROR</code> field.</p>
     */
    OSSFILE_BUCKET_POLICY_ERROR(11102, "The ossfile server has encountered an error with ossfile bucket policy."),
    /**
     * <code>OSSFILE_MAKE_BUCKET_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_MAKE_BUCKET_ERROR</code> field.</p>
     */
    OSSFILE_MAKE_BUCKET_ERROR(11103, "The ossfile server has encountered an error with ossfile make bucket."),
    /**
     * <code>OSSFILE_REMOVE_BUCKET_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_REMOVE_BUCKET_ERROR</code> field.</p>
     */
    OSSFILE_REMOVE_BUCKET_ERROR(11104, "The ossfile server has encountered an error with ossfile remove bucket."),
    /**
     * <code>OSSFILE_LIST_ALL_BUCKETS_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_LIST_ALL_BUCKETS_ERROR</code> field.</p>
     */
    OSSFILE_LIST_ALL_BUCKETS_ERROR(11105, "The ossfile server has encountered an error with ossfile list all buckets."),
    /**
     * <code>OSSFILE_STAT_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_STAT_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_STAT_OBJECT_ERROR(11106, "The ossfile server has encountered an error with ossfile stat object."),
    /**
     * <code>OSSFILE_GET_ALL_OBJECTS_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_GET_ALL_OBJECTS_ERROR</code> field.</p>
     */
    OSSFILE_GET_ALL_OBJECTS_ERROR(11107, "The ossfile server has encountered an error with ossfile get all objects."),
    /**
     * <code>OSSFILE_GET_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_GET_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_GET_OBJECT_ERROR(11108, "The ossfile server has encountered an error with ossfile get object."),
    /**
     * <code>OSSFILE_PUT_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_PUT_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_PUT_OBJECT_ERROR(11109, "The ossfile server has encountered an error with ossfile put object."),
    /**
     * <code>OSSFILE_PUT_FOLDER_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_PUT_FOLDER_ERROR</code> field.</p>
     */
    OSSFILE_PUT_FOLDER_ERROR(11110, "The ossfile server has encountered an error with ossfile put folder."),
    /**
     * <code>OSSFILE_COMPOSE_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_COMPOSE_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_COMPOSE_OBJECT_ERROR(11111, "The ossfile server has encountered an error with ossfile compose object."),
    /**
     * <code>OSSFILE_UPLOAD_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_UPLOAD_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_UPLOAD_OBJECT_ERROR(11112, "The ossfile server has encountered an error with ossfile upload object."),
    /**
     * <code>OSSFILE_APPEND_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_APPEND_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_APPEND_OBJECT_ERROR(11113, "The ossfile server has encountered an error with ossfile append object."),
    /**
     * <code>OSSFILE_COPY_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_COPY_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_COPY_OBJECT_ERROR(11114, "The ossfile server has encountered an error with ossfile copy object."),
    /**
     * <code>OSSFILE_REMOVE_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_REMOVE_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_REMOVE_OBJECT_ERROR(11115, "The ossfile server has encountered an error with ossfile remove object."),
    /**
     * <code>OSSFILE_PRESIGNED_OBJECT_URL_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_PRESIGNED_OBJECT_URL_ERROR</code> field.</p>
     */
    OSSFILE_PRESIGNED_OBJECT_URL_ERROR(11116, "The ossfile server has encountered an error with ossfile presigned object url."),
    /**
     * <code>OSSFILE_PRESIGNED_ALL_OBJECT_URL_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_PRESIGNED_ALL_OBJECT_URL_ERROR</code> field.</p>
     */
    OSSFILE_PRESIGNED_ALL_OBJECT_URL_ERROR(11117, "The ossfile server has encountered an error with ossfile presigned all object urls."),
    ;

    /**
     * <code>status</code>
     * {@link java.lang.Integer} <p>The <code>status</code> field.</p>
     * @see java.lang.Integer
     */
    private final Integer status;
    /**
     * <code>message</code>
     * {@link java.lang.String} <p>The <code>message</code> field.</p>
     * @see java.lang.String
     */
    private final String message;

    /**
     * <code>OssfileErrorStatus</code>
     * <p>Instantiates a new ossfile error status.</p>
     * @param status  {@link java.lang.Integer} <p>The status parameter is <code>Integer</code> type.</p>
     * @param message {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @see java.lang.Integer
     * @see java.lang.String
     */
    OssfileErrorStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
