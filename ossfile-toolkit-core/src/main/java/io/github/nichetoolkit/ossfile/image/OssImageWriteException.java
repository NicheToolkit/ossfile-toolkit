package io.github.nichetoolkit.ossfile.image;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;

/**
 * <code>OssImageWriteException</code>
 * <p>The oss image write exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
 * @since Jdk1.8
 */
public class OssImageWriteException extends FileErrorException {
    /**
     * <code>OssImageWriteException</code>
     * <p>Instantiates a new oss image write exception.</p>
     */
    public OssImageWriteException() {
        super(OssImageErrorStatus.IMAGE_WRITE_ERROR);
    }

    /**
     * <code>OssImageWriteException</code>
     * <p>Instantiates a new oss image write exception.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public OssImageWriteException(RestStatus status) {
        super(status, RestError.error(status));
    }

    /**
     * <code>OssImageWriteException</code>
     * <p>Instantiates a new oss image write exception.</p>
     * @param message {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssImageWriteException(String message) {
        super(OssImageErrorStatus.IMAGE_WRITE_ERROR, RestError.error(OssImageErrorStatus.IMAGE_WRITE_ERROR, message));
    }

    /**
     * <code>OssImageWriteException</code>
     * <p>Instantiates a new oss image write exception.</p>
     * @param resource {@link java.lang.String} <p>The resource parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssImageWriteException(String resource, String message) {
        super(OssImageErrorStatus.IMAGE_WRITE_ERROR, RestError.error(resource, OssImageErrorStatus.IMAGE_WRITE_ERROR, message));
    }

    @Override
    public OssImageWriteException get() {
        return new OssImageWriteException();
    }
}
