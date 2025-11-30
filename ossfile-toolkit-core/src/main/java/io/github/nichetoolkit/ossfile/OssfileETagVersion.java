package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <code>OssfileETagVersion</code>
 * <p>The ossfile e tag version class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.experimental.SuperBuilder
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk17
 */
@Getter
@Setter
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileETagVersion implements Serializable {
    /**
     * <code>etag</code>
     * {@link java.lang.String} <p>The <code>etag</code> field.</p>
     * @see java.lang.String
     */
    protected String etag;
    /**
     * <code>version</code>
     * {@link java.lang.String} <p>The <code>version</code> field.</p>
     * @see java.lang.String
     */
    protected String version;

    /**
     * <code>OssfileETagVersion</code>
     * <p>Instantiates a new ossfile e tag version.</p>
     */
    public OssfileETagVersion() {
    }

    /**
     * <code>OssfileETagVersion</code>
     * <p>Instantiates a new ossfile e tag version.</p>
     * @param etag    {@link java.lang.String} <p>The etag parameter is <code>String</code> type.</p>
     * @param version {@link java.lang.String} <p>The version parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssfileETagVersion(String etag, String version) {
        this.etag = etag;
        this.version = version;
    }
}
