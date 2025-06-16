package io.github.nichetoolkit.ossfile.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.nichetoolkit.ossfile.OssfileConstants;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.RestOptional;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Optional;

/**
 * <code>OssfileFileType</code>
 * <p>The ossfile file type enumeration.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.RestKey
 * @see lombok.Getter
 * @since Jdk1.8
 */
@Getter
public enum OssfileFileType implements RestKey<String> {
    /**
     * <code>IMAGE</code>
     * <p>The image ossfile file type field.</p>
     */
    IMAGE("image", OssfileConstants.IMAGE_SUFFIX),
    /**
     * <code>DOCUMENT</code>
     * <p>The document ossfile file type field.</p>
     */
    DOCUMENT("document", OssfileConstants.DOCUMENT_SUFFIX),
    /**
     * <code>VIDEO</code>
     * <p>The video ossfile file type field.</p>
     */
    VIDEO("video", OssfileConstants.VIDEO_SUFFIX),
    /**
     * <code>EXECUTABLE</code>
     * <p>The executable ossfile file type field.</p>
     */
    EXECUTABLE("executable", OssfileConstants.EXECUTABLE_SUFFIX),
    /**
     * <code>COMPRESSED</code>
     * <p>The compressed ossfile file type field.</p>
     */
    COMPRESSED("compressed", OssfileConstants.COMPRESSED_SUFFIX),
    /**
     * <code>UNKNOWN</code>
     * {@link io.github.nichetoolkit.ossfile.domain.OssfileFileType} <p>The <code>UNKNOWN</code> field.</p>
     */
    UNKNOWN("unknown", new String[0]),
    ;

    /**
     * <code>key</code>
     * {@link java.lang.String} <p>The <code>key</code> field.</p>
     * @see java.lang.String
     */
    private String key;
    /**
     * <code>types</code>
     * {@link java.lang.String} <p>The <code>types</code> field.</p>
     * @see java.lang.String
     */
    private final String[] types;

    /**
     * <code>OssfileFileType</code>
     * <p>Instantiates a new ossfile file type.</p>
     * @param key   {@link java.lang.String} <p>The key parameter is <code>String</code> type.</p>
     * @param types {@link java.lang.String} <p>The types parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    OssfileFileType(String key, String[] types) {
        this.key = key;
        this.types = types;
    }

    @JsonValue
    @Override
    public String getKey() {
        return this.key;
    }

    /**
     * <code>setKey</code>
     * <p>The set key setter method.</p>
     * @param key {@link java.lang.String} <p>The key parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.OssfileFileType} <p>The set key return object is <code>OssfileFileType</code> type.</p>
     * @see java.lang.String
     */
    private OssfileFileType setKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * <code>parseKey</code>
     * <p>The parse key method.</p>
     * @param key {@link java.lang.String} <p>The key parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.OssfileFileType} <p>The parse key return object is <code>OssfileFileType</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.NonNull
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    @JsonCreator
    public static OssfileFileType parseKey(@NonNull String key) {
        OssfileFileType typeEnum = RestKey.parseKey(OssfileFileType.class, key);
        return Optional.ofNullable(typeEnum).orElse(OssfileFileType.UNKNOWN);
    }

    /**
     * <code>present</code>
     * <p>The present method.</p>
     * @param type {@link io.github.nichetoolkit.rest.RestKey} <p>The type parameter is <code>RestKey</code> type.</p>
     * @return boolean <p>The present return object is <code>boolean</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestKey
     */
    public boolean present(RestKey<String> type) {
        if (type instanceof OssfileFileType) {
            return type == this;
        } else {
            return Arrays.asList(this.types).contains(type.getKey());
        }
    }

    /**
     * <code>parseSuffix</code>
     * <p>The parse suffix method.</p>
     * @param suffixType {@link java.lang.String} <p>The suffix type parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.OssfileFileType} <p>The parse suffix return object is <code>OssfileFileType</code> type.</p>
     * @see java.lang.String
     */
    public static OssfileFileType parseSuffix(String suffixType) {
        return RestOptional.ofEmptyable(suffixType).map(suffix -> {
            if (Arrays.asList(OssfileConstants.IMAGE_SUFFIX).contains(suffix)) {
                return OssfileFileType.IMAGE;
            } else if (Arrays.asList(OssfileConstants.DOCUMENT_SUFFIX).contains(suffix)) {
                return OssfileFileType.DOCUMENT;
            } else if (Arrays.asList(OssfileConstants.VIDEO_SUFFIX).contains(suffix)) {
                return OssfileFileType.VIDEO;
            } else if (Arrays.asList(OssfileConstants.EXECUTABLE_SUFFIX).contains(suffix)) {
                return OssfileFileType.EXECUTABLE;
            } else if (Arrays.asList(OssfileConstants.COMPRESSED_SUFFIX).contains(suffix)) {
                return OssfileFileType.COMPRESSED;
            } else {
                return OssfileFileType.UNKNOWN.setKey(suffix);
            }
        }).orElse(OssfileFileType.UNKNOWN.setKey(suffixType));
    }
}
