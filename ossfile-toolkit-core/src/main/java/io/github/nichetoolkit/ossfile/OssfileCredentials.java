package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <code>OssfileCredentials</code>
 * <p>The ossfile credentials class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Data
 * @see lombok.Builder
 * @see lombok.EqualsAndHashCode
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk1.8
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileCredentials implements Serializable {

    /**
     * <code>DELAY</code>
     * <p>The constant <code>DELAY</code> field.</p>
     */
    private static final int DELAY = 300;

    /**
     * <code>accessKeyId</code>
     * {@link java.lang.String} <p>The <code>accessKeyId</code> field.</p>
     * @see java.lang.String
     */
    private String accessKeyId;

    /**
     * <code>accessKeySecret</code>
     * {@link java.lang.String} <p>The <code>accessKeySecret</code> field.</p>
     * @see java.lang.String
     */
    private String accessKeySecret;

    /**
     * <code>securityToken</code>
     * {@link java.lang.String} <p>The <code>securityToken</code> field.</p>
     * @see java.lang.String
     */
    private String securityToken;

    /**
     * <code>expire</code>
     * {@link java.lang.Long} <p>The <code>expire</code> field.</p>
     * @see java.lang.Long
     */
    private Long expire;

    /**
     * <code>OssfileCredentials</code>
     * <p>Instantiates a new ossfile credentials.</p>
     */
    public OssfileCredentials() {
    }

    /**
     * <code>OssfileCredentials</code>
     * <p>Instantiates a new ossfile credentials.</p>
     * @param accessKeyId     {@link java.lang.String} <p>The access key id parameter is <code>String</code> type.</p>
     * @param accessKeySecret {@link java.lang.String} <p>The access key secret parameter is <code>String</code> type.</p>
     * @param securityToken   {@link java.lang.String} <p>The security token parameter is <code>String</code> type.</p>
     * @param expire          {@link java.lang.Long} <p>The expire parameter is <code>Long</code> type.</p>
     * @see java.lang.String
     * @see java.lang.Long
     */
    public OssfileCredentials(String accessKeyId, String accessKeySecret, String securityToken, Long expire) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = securityToken;
        this.expire = expire - DELAY;
    }
    
}
