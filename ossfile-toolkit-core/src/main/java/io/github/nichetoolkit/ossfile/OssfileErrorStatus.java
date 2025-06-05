package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rest.RestStatus;
import lombok.Getter;

@Getter
public enum OssfileErrorStatus implements RestStatus {

    OSSFILE_DOWNLOAD_ERROR(11000, "The server has encountered an error with file download."),
    OSSFILE_FINISHED_ERROR(11002, "The file cannot be downloaded as it has not been uploaded or merged."),
    OSSFILE_CONTENT_RANGE_ERROR(11003, "The fragment upload request header Content-Range parsing error."),

    OSSFILE_READ_STREAM_ERROR(11006, "The ossfile server has encountered an error with stream read."),
    OSSFILE_READ_BYTE_NULL(11007, "The ossfile server has encountered an error with data read."),
    OSSFILE_READ_BYTE_ERROR(11008, "The ossfile server has encountered an error with file data read."),
    OSSFILE_CONDENSE_ERROR(11009, "The ossfile server has encountered an error with file compression."),
    OSSFILE_UNSUPPORTED_ERROR(11011, "The ossfile server has encountered an error with file unsupported."),

    OSSFILE_CREDENTIALS_ERROR(11011, "The ossfile server has encountered an error with serve credentials."),

    OSSFILE_INITIATE_MULTIPART_ERROR(11013, "The ossfile server has encountered an error with initiate multipart."),
    OSSFILE_UPLOAD_MULTIPART_ERROR(11014, "The ossfile server has encountered an error with upload multipart."),
    OSSFILE_COMPLETE_MULTIPART_ERROR(11015, "The ossfile server has encountered an error with complete multipart."),

    OSSFILE_SERVER_ERROR(11100, "The ossfile server has encountered an error."),
    OSSFILE_CONFIG_ERROR(110101, "The ossfile server has encountered an error with ossfile configuration."),
    OSSFILE_BUCKET_POLICY_ERROR(11102, "The ossfile server has encountered an error with ossfile bucket policy."),
    OSSFILE_MAKE_BUCKET_ERROR(11103, "The ossfile server has encountered an error with ossfile make bucket."),
    OSSFILE_REMOVE_BUCKET_ERROR(11104, "The ossfile server has encountered an error with ossfile remove bucket."),
    OSSFILE_LIST_ALL_BUCKETS_ERROR(11105, "The ossfile server has encountered an error with ossfile list all buckets."),
    OSSFILE_STAT_OBJECT_ERROR(11106, "The ossfile server has encountered an error with ossfile stat object."),
    OSSFILE_GET_ALL_OBJECTS_ERROR(11107, "The ossfile server has encountered an error with ossfile get all objects."),
    OSSFILE_GET_OBJECT_ERROR(11108, "The ossfile server has encountered an error with ossfile get object."),
    OSSFILE_PUT_OBJECT_ERROR(11109, "The ossfile server has encountered an error with ossfile put object."),
    OSSFILE_PUT_FOLDER_ERROR(11110, "The ossfile server has encountered an error with ossfile put folder."),
    OSSFILE_COMPOSE_OBJECT_ERROR(11111, "The ossfile server has encountered an error with ossfile compose object."),
    OSSFILE_UPLOAD_OBJECT_ERROR(11112, "The ossfile server has encountered an error with ossfile upload object."),
    OSSFILE_APPEND_OBJECT_ERROR(11113, "The ossfile server has encountered an error with ossfile append object."),
    OSSFILE_COPY_OBJECT_ERROR(11114, "The ossfile server has encountered an error with ossfile copy object."),
    OSSFILE_REMOVE_OBJECT_ERROR(11115, "The ossfile server has encountered an error with ossfile remove object."),
    OSSFILE_PRESIGNED_OBJECT_URL_ERROR(11116, "The ossfile server has encountered an error with ossfile presigned object url."),
    OSSFILE_PRESIGNED_ALL_OBJECT_URL_ERROR(11117, "The ossfile server has encountered an error with ossfile presigned all object urls."),
    ;

    private final Integer status;
    private final String message;

    OssfileErrorStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
