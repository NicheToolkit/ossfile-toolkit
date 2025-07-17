package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.RestStatus;
import io.github.nichetoolkit.rest.util.I18nUtils;
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
    OSSFILE_DOWNLOAD_ERROR(11000, "It has encountered an error with file download"),
    /**
     * <code>OSSFILE_FINISHED_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_FINISHED_ERROR</code> field.</p>
     */
    OSSFILE_FINISHED_ERROR(11002, "The file cannot be downloaded as it has not been uploaded or finished"),
    /**
     * <code>OSSFILE_CONTENT_RANGE_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_CONTENT_RANGE_ERROR</code> field.</p>
     */
    OSSFILE_CONTENT_RANGE_ERROR(11003, "The fragment upload request header Content-Range parsing error"),

    /**
     * <code>OSSFILE_NO_FOUND_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_NO_FOUND_ERROR</code> field.</p>
     */
    OSSFILE_NO_FOUND_ERROR(11004, "The file cannot be downloaded because of no found"),
    /**
     * <code>OSSFILE_UPLOAD_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_UPLOAD_ERROR</code> field.</p>
     */
    OSSFILE_UPLOAD_ERROR(11005, "It has encountered a file upload error"),

    /**
     * <code>OSSFILE_READ_STREAM_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_READ_STREAM_ERROR</code> field.</p>
     */
    OSSFILE_READ_STREAM_ERROR(11006, "It has encountered a stream read error"),
    /**
     * <code>OSSFILE_READ_BYTE_NULL</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_READ_BYTE_NULL</code> field.</p>
     */
    OSSFILE_READ_BYTE_NULL(11007, "The byte data of read is empty"),
    /**
     * <code>OSSFILE_READ_BYTE_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_READ_BYTE_ERROR</code> field.</p>
     */
    OSSFILE_READ_BYTE_ERROR(11008, "It has encountered a file data read error"),
    /**
     * <code>OSSFILE_CONDENSE_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_CONDENSE_ERROR</code> field.</p>
     */
    OSSFILE_CONDENSE_ERROR(11009, "It has encountered a file compression error"),
    /**
     * <code>OSSFILE_HANDLE_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_HANDLE_ERROR</code> field.</p>
     */
    OSSFILE_HANDLE_ERROR(11010, "It has encountered a file handle error"),

    /**
     * <code>OSSFILE_UNSUPPORTED_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_UNSUPPORTED_ERROR</code> field.</p>
     */
    OSSFILE_UNSUPPORTED_ERROR(11011, "The file is unsupported"),

    /**
     * <code>OSSFILE_CREDENTIALS_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_CREDENTIALS_ERROR</code> field.</p>
     */
    OSSFILE_CREDENTIALS_ERROR(11012, "It has encountered a serve credentials error"),
    /**
     * <code>OSSFILE_BUCKET_CORS_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_BUCKET_CORS_ERROR</code> field.</p>
     */
    OSSFILE_BUCKET_CORS_ERROR(11013, "It has encountered a setting bucket CORS error"),

    /**
     * <code>OSSFILE_INITIATE_MULTIPART_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_INITIATE_MULTIPART_ERROR</code> field.</p>
     */
    OSSFILE_INITIATE_MULTIPART_ERROR(11014, "It has encountered a initiate multipart error"),
    /**
     * <code>OSSFILE_UPLOAD_MULTIPART_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_UPLOAD_MULTIPART_ERROR</code> field.</p>
     */
    OSSFILE_UPLOAD_MULTIPART_ERROR(11015, "It has encountered a upload multipart error"),
    /**
     * <code>OSSFILE_COMPLETE_MULTIPART_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_COMPLETE_MULTIPART_ERROR</code> field.</p>
     */
    OSSFILE_COMPLETE_MULTIPART_ERROR(11016, "It has encountered a complete multipart error"),

    /**
     * <code>OSSFILE_SERVER_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_SERVER_ERROR</code> field.</p>
     */
    OSSFILE_SERVER_ERROR(11100, "It has encountered a server related error"),
    /**
     * <code>OSSFILE_CONFIG_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_CONFIG_ERROR</code> field.</p>
     */
    OSSFILE_CONFIG_ERROR(11101, "It has encountered a server configuration error"),
    /**
     * <code>OSSFILE_BUCKET_POLICY_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_BUCKET_POLICY_ERROR</code> field.</p>
     */
    OSSFILE_BUCKET_POLICY_ERROR(11102, "It has encountered a bucket policy error"),
    /**
     * <code>OSSFILE_MAKE_BUCKET_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_MAKE_BUCKET_ERROR</code> field.</p>
     */
    OSSFILE_MAKE_BUCKET_ERROR(11103, "It has encountered a make bucket error"),
    /**
     * <code>OSSFILE_REMOVE_BUCKET_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_REMOVE_BUCKET_ERROR</code> field.</p>
     */
    OSSFILE_REMOVE_BUCKET_ERROR(11104, "It has encountered a remove bucket error"),
    /**
     * <code>OSSFILE_LIST_ALL_BUCKETS_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_LIST_ALL_BUCKETS_ERROR</code> field.</p>
     */
    OSSFILE_LIST_ALL_BUCKETS_ERROR(11105, "It has encountered a list all buckets error"),
    /**
     * <code>OSSFILE_STAT_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_STAT_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_STAT_OBJECT_ERROR(11106, "It has encountered a stat object error"),
    /**
     * <code>OSSFILE_GET_ALL_OBJECTS_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_GET_ALL_OBJECTS_ERROR</code> field.</p>
     */
    OSSFILE_GET_ALL_OBJECTS_ERROR(11107, "It has encountered a get all objects error"),
    /**
     * <code>OSSFILE_GET_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_GET_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_GET_OBJECT_ERROR(11108, "It has encountered a get object error"),
    /**
     * <code>OSSFILE_PUT_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_PUT_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_PUT_OBJECT_ERROR(11109, "It has encountered a put object error"),
    /**
     * <code>OSSFILE_PUT_FOLDER_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_PUT_FOLDER_ERROR</code> field.</p>
     */
    OSSFILE_PUT_FOLDER_ERROR(11110, "It has encountered a put folder error"),
    /**
     * <code>OSSFILE_COMPOSE_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_COMPOSE_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_COMPOSE_OBJECT_ERROR(11111, "It has encountered a compose object error"),
    /**
     * <code>OSSFILE_UPLOAD_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_UPLOAD_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_UPLOAD_OBJECT_ERROR(11112, "It has encountered a upload object error"),
    /**
     * <code>OSSFILE_APPEND_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_APPEND_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_APPEND_OBJECT_ERROR(11113, "It has encountered a append object error"),
    /**
     * <code>OSSFILE_COPY_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_COPY_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_COPY_OBJECT_ERROR(11114, "It has encountered a copy object error"),
    /**
     * <code>OSSFILE_REMOVE_OBJECT_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_REMOVE_OBJECT_ERROR</code> field.</p>
     */
    OSSFILE_REMOVE_OBJECT_ERROR(11115, "It has encountered a remove object error"),
    /**
     * <code>OSSFILE_PRESIGNED_OBJECT_URL_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_PRESIGNED_OBJECT_URL_ERROR</code> field.</p>
     */
    OSSFILE_PRESIGNED_OBJECT_URL_ERROR(11116, "It has encountered a presigned object url error"),
    /**
     * <code>OSSFILE_PRESIGNED_ALL_OBJECT_URL_ERROR</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileErrorStatus} <p>The <code>OSSFILE_PRESIGNED_ALL_OBJECT_URL_ERROR</code> field.</p>
     */
    OSSFILE_PRESIGNED_ALL_OBJECT_URL_ERROR(11117, "It has encountered a presigned all object urls error"),
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

    @Override
    public String getMessage() {
        return I18nUtils.message(name(), this.message);
    }
}

