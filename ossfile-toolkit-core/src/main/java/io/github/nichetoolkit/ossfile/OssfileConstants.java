package io.github.nichetoolkit.ossfile;

/**
 * <code>OssfileConstants</code>
 * <p>The ossfile constants interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface OssfileConstants {

    /**
     * <code>OSS_VIDEO_RESOURCE</code>
     * {@link java.lang.String} <p>The constant <code>OSS_VIDEO_RESOURCE</code> field.</p>
     * @see java.lang.String
     */
    String OSS_VIDEO_RESOURCE = "Oss-Video-Resource";

    /**
     * <code>IMAGE_SUFFIX</code>
     * {@link java.lang.String} <p>The constant <code>IMAGE_SUFFIX</code> field.</p>
     * @see java.lang.String
     */
    String[] IMAGE_SUFFIX = new String[]
            {"jpg", "jpeg", "png", "bpm", "gif", "svg", "icon", "tfw", "psd", "tif", "tiff", "raw", "tag"};
    /**
     * <code>DOCUMENT_SUFFIX</code>
     * {@link java.lang.String} <p>The constant <code>DOCUMENT_SUFFIX</code> field.</p>
     * @see java.lang.String
     */
    String[] DOCUMENT_SUFFIX = new String[]
            {"txt", "doc", "docx", "ppt", "pptx", "xls", "xlsx", "pdf", "vsdx", "eapx"};
    /**
     * <code>VIDEO_SUFFIX</code>
     * {@link java.lang.String} <p>The constant <code>VIDEO_SUFFIX</code> field.</p>
     * @see java.lang.String
     */
    String[] VIDEO_SUFFIX = new String[]
            {"mp3", "mp4", "avi", "mkv", "rmvb", "rm", "asf", "wmv", "mov", "raw", "tag"};
    /**
     * <code>EXECUTABLE_SUFFIX</code>
     * {@link java.lang.String} <p>The constant <code>EXECUTABLE_SUFFIX</code> field.</p>
     * @see java.lang.String
     */
    String[] EXECUTABLE_SUFFIX = new String[]{"sh", "bat", "exe", "py"};

    /**
     * <code>COMPRESSED_SUFFIX</code>
     * {@link java.lang.String} <p>The constant <code>COMPRESSED_SUFFIX</code> field.</p>
     * @see java.lang.String
     */
    String[] COMPRESSED_SUFFIX = new String[]{"rar", "tar", "zip", "jar", "war"};

    /**
     * <code>CONTENT_DISPOSITION_HEADER</code>
     * {@link java.lang.String} <p>The constant <code>CONTENT_DISPOSITION_HEADER</code> field.</p>
     * @see java.lang.String
     */
    String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    /**
     * <code>ATTACHMENT_FILENAME_VALUE</code>
     * {@link java.lang.String} <p>The constant <code>ATTACHMENT_FILENAME_VALUE</code> field.</p>
     * @see java.lang.String
     */
    String ATTACHMENT_FILENAME_VALUE = "attachment; filename=";

    /**
     * <code>FILE_ZIP_SUFFIX</code>
     * {@link java.lang.String} <p>The constant <code>FILE_ZIP_SUFFIX</code> field.</p>
     * @see java.lang.String
     */
    String FILE_ZIP_SUFFIX = ".zip";

    /**
     * <code>FILE_DATE_PATTERN</code>
     * {@link java.lang.String} <p>The constant <code>FILE_DATE_PATTERN</code> field.</p>
     * @see java.lang.String
     */
    String FILE_DATE_PATTERN = "yyyyMMdd-HHmmss";

    /**
     * <code>BULK_PREFIX</code>
     * {@link java.lang.String} <p>The constant <code>BULK_PREFIX</code> field.</p>
     * @see java.lang.String
     */
    String BULK_PREFIX = "bulk";


    /**
     * <code>FILE_SEPARATOR</code>
     * {@link java.lang.String} <p>The constant <code>FILE_SEPARATOR</code> field.</p>
     * @see java.lang.String
     */
    String FILE_SEPARATOR = "/";

    /**
     * <code>PREVIEW_PREFIX</code>
     * {@link java.lang.String} <p>The constant <code>PREVIEW_PREFIX</code> field.</p>
     * @see java.lang.String
     */
    String PREVIEW_PREFIX = "preview";

    /**
     * <code>MIN_PART_SIZE</code>
     * {@link java.lang.Long} <p>The constant <code>MIN_PART_SIZE</code> field.</p>
     * @see java.lang.Long
     */
    Long MIN_PART_SIZE = 5 * 1024 * 1024L;

    /**
     * <code>MAX_PART_SIZE</code>
     * {@link java.lang.Long} <p>The constant <code>MAX_PART_SIZE</code> field.</p>
     * @see java.lang.Long
     */
    Long MAX_PART_SIZE = 5 * 1024 * 1024 * 1024L;
}
