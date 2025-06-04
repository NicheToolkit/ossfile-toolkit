package io.github.nichetoolkit.ossfile.image;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;

/**
 * <code>OssImageReadException</code>
 * <p>The oss image read exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
 * @since Jdk1.8
 */
public class OssImageReadException extends FileErrorException {
    /**
     * <code>OssImageReadException</code>
     * <p>Instantiates a new oss image read exception.</p>
     */
    public OssImageReadException() {
        super(OssImageErrorStatus.IMAGE_READ_ERROR);
    }

    /**
     * <code>OssImageReadException</code>
     * <p>Instantiates a new oss image read exception.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public OssImageReadException(RestStatus status) {
        super(status, RestError.error(status));
    }

    /**
     * <code>OssImageReadException</code>
     * <p>Instantiates a new oss image read exception.</p>
     * @param message {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssImageReadException(String message) {
        super(OssImageErrorStatus.IMAGE_READ_ERROR, RestError.error(OssImageErrorStatus.IMAGE_READ_ERROR, message));
    }

    /**
     * <code>OssImageReadException</code>
     * <p>Instantiates a new oss image read exception.</p>
     * @param resource {@link java.lang.String} <p>The resource parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssImageReadException(String resource, String message) {
        super(OssImageErrorStatus.IMAGE_READ_ERROR, RestError.error(resource, OssImageErrorStatus.IMAGE_READ_ERROR, message));
    }

    @Override
    public OssImageReadException get() {
        return new OssImageReadException();
    }
}
