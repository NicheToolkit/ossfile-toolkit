package io.github.nichetoolkit.ossfile.image;

import io.github.nichetoolkit.rest.RestError;
import io.github.nichetoolkit.rest.RestStatus;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;

/**
 * <code>OssImageTransferException</code>
 * <p>The oss image transfer exception class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
 * @since Jdk1.8
 */
public class OssImageTransferException extends FileErrorException {
    /**
     * <code>OssImageTransferException</code>
     * <p>Instantiates a new oss image transfer exception.</p>
     */
    public OssImageTransferException() {
        super(OssImageErrorStatus.IMAGE_TRANSFER_ERROR);
    }

    /**
     * <code>OssImageTransferException</code>
     * <p>Instantiates a new oss image transfer exception.</p>
     * @param status {@link io.github.nichetoolkit.rest.RestStatus} <p>The status parameter is <code>RestStatus</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     */
    public OssImageTransferException(RestStatus status) {
        super(status, RestError.error(status));
    }

    /**
     * <code>OssImageTransferException</code>
     * <p>Instantiates a new oss image transfer exception.</p>
     * @param message {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssImageTransferException(String message) {
        super(OssImageErrorStatus.IMAGE_TRANSFER_ERROR, RestError.error(OssImageErrorStatus.IMAGE_TRANSFER_ERROR, message));
    }

    /**
     * <code>OssImageTransferException</code>
     * <p>Instantiates a new oss image transfer exception.</p>
     * @param resource {@link java.lang.String} <p>The resource parameter is <code>String</code> type.</p>
     * @param message  {@link java.lang.String} <p>The message parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssImageTransferException(String resource, String message) {
        super(OssImageErrorStatus.IMAGE_TRANSFER_ERROR, RestError.error(resource, OssImageErrorStatus.IMAGE_TRANSFER_ERROR, message));
    }

    @Override
    public OssImageTransferException get() {
        return new OssImageTransferException();
    }
}
