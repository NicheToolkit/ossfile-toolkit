package io.github.nichetoolkit.ossfile;

public interface OssfileConstants {

    String OSS_VIDEO_RESOURCE = "Oss-Video-Resource";

    String[] IMAGE_SUFFIX = new String[]
            {"jpg", "jpeg", "png", "bpm", "gif", "svg", "icon", "tfw", "psd", "tif", "tiff", "raw", "tag"};
    String[] DOCUMENT_SUFFIX = new String[]
            {"txt", "doc", "docx", "ppt", "pptx", "xls", "xlsx", "pdf", "vsdx", "eapx"};
    String[] VIDEO_SUFFIX = new String[]
            {"mp3", "mp4", "avi", "mkv", "rmvb", "rm", "asf", "wmv", "mov", "raw", "tag"};
    String[] EXECUTABLE_SUFFIX = new String[]{"sh", "bat", "exe", "py"};

    String[] COMPRESSED_SUFFIX = new String[]{"rar", "tar", "zip", "jar", "war"};


    String CONTENT_RANGE_HEADER = "Content-Range";
    String CONTENT_RANGE_BYTES_HEADER = "bytes ";
    String CONTENT_RANGE_RANGE_REGEX = "-";
    String CONTENT_RANGE_SIZE_REGEX = "/";

    String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    String ATTACHMENT_FILENAME_VALUE = "attachment; filename=";

    String SUFFIX_REGEX = ".";
    String IMAGE_JPEG_SUFFIX = "jpeg";
    String IMAGE_PNG_SUFFIX = "png";

    String FILE_ZIP_SUFFIX = ".zip";

    String FILE_DATE_PATTERN = "yyyyMMdd-HHmmss";

    String BULK_PREFIX = "bulk";

    String PREVIEW_PREFIX = "preview";
    String PART_PREFIX = "part";

}
