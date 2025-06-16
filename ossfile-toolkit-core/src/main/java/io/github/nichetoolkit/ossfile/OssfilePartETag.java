package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * <code>OssfilePartETag</code>
 * <p>The ossfile part e tag class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Data
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk1.8
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfilePartETag implements Serializable {
    /**
     * <code>partIndex</code>
     * {@link java.lang.Integer} <p>The <code>partIndex</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer partIndex;
    /**
     * <code>partEtag</code>
     * {@link java.lang.String} <p>The <code>partEtag</code> field.</p>
     * @see java.lang.String
     */
    protected String partEtag;

    /**
     * <code>OssfilePartETag</code>
     * <p>Instantiates a new ossfile part e tag.</p>
     */
    public OssfilePartETag() {
    }

    /**
     * <code>OssfilePartETag</code>
     * <p>Instantiates a new ossfile part e tag.</p>
     * @param partIndex {@link java.lang.Integer} <p>The part index parameter is <code>Integer</code> type.</p>
     * @param partEtag  {@link java.lang.String} <p>The part etag parameter is <code>String</code> type.</p>
     * @see java.lang.Integer
     * @see java.lang.String
     */
    public OssfilePartETag(Integer partIndex, String partEtag) {
        this.partIndex = partIndex;
        this.partEtag = partEtag;
    }
}
