package io.github.nichetoolkit.ossfile;

/**
 * <code>OssfileResource</code>
 * <p>The ossfile resource interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @since Jdk1.8
 */
public interface OssfileResource {
    /**
     * <code>getObjectKey</code>
     * <p>The get object key getter method.</p>
     * @return {@link java.lang.String} <p>The get object key return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    String getObjectKey();

    /**
     * <code>getFilename</code>
     * <p>The get filename getter method.</p>
     * @return {@link java.lang.String} <p>The get filename return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    String getFilename();

    /**
     * <code>isReadable</code>
     * <p>The is readable method.</p>
     * @return boolean <p>The is readable return object is <code>boolean</code> type.</p>
     */
    default boolean isReadable() {
        return true;
    }

    /**
     * <code>exists</code>
     * <p>The exists method.</p>
     * @return boolean <p>The exists return object is <code>boolean</code> type.</p>
     */
    default boolean exists() {
        return true;
    }

    /**
     * <code>isOpen</code>
     * <p>The is open method.</p>
     * @return boolean <p>The is open return object is <code>boolean</code> type.</p>
     */
    default boolean isOpen() {
        return true;
    }
}
