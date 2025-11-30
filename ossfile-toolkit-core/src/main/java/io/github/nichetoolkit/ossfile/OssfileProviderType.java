package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.rest.RestKey;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * <code>OssfileProviderType</code>
 * <p>The ossfile provider type enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestKey
 * @since Jdk17
 */
public enum OssfileProviderType implements RestKey<String> {
    /**
     * <code>MINIO</code>
     * <p>The minio ossfile provider type field.</p>
     */
    MINIO("minio"),
    /**
     * <code>ALIYUN</code>
     * <p>The aliyun ossfile provider type field.</p>
     */
    ALIYUN("aliyun"),
    /**
     * <code>AMAZON</code>
     * <p>The amazon ossfile provider type field.</p>
     */
    AMAZON("amazon"),
    ;
    /**
     * <code>key</code>
     * {@link java.lang.String} <p>The <code>key</code> field.</p>
     * @see java.lang.String
     */
    private final String key;

    /**
     * <code>OssfileProviderType</code>
     * <p>Instantiates a new ossfile provider type.</p>
     * @param key {@link java.lang.String} <p>The key parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    OssfileProviderType(String key) {
        this.key = key;
    }

    @JsonValue
    @Override
    public String getKey() {
        return this.key;
    }

    /**
     * <code>parseKey</code>
     * <p>The parse key method.</p>
     * @param key {@link java.lang.String} <p>The key parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileProviderType} <p>The parse key return object is <code>OssfileProviderType</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static OssfileProviderType parseKey(@NonNull String key) {
        OssfileProviderType ossfileType = RestKey.parseKey(OssfileProviderType.class, key);
        return Optional.ofNullable(ossfileType).orElse(OssfileProviderType.MINIO);
    }
}
