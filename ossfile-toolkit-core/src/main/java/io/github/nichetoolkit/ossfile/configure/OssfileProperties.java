package io.github.nichetoolkit.ossfile.configure;

import io.github.nichetoolkit.ossfile.OssfileProviderType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * <code>OssfileProperties</code>
 * <p>The ossfile properties class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.Getter
 * @see lombok.Setter
 * @see org.springframework.stereotype.Component
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 * @since Jdk1.8
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "nichetoolkit.ossfile")
public class OssfileProperties {
    /**
     * <code>provider</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileProviderType} <p>The <code>provider</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileProviderType
     */
    private OssfileProviderType provider = OssfileProviderType.MINIO;
    /**
     * <code>endpoint</code>
     * {@link java.lang.String} <p>The <code>endpoint</code> field.</p>
     * @see java.lang.String
     */
    private String endpoint;
    /**
     * <code>accessKey</code>
     * {@link java.lang.String} <p>The <code>accessKey</code> field.</p>
     * @see java.lang.String
     */
    private String accessKey;
    /**
     * <code>secretKey</code>
     * {@link java.lang.String} <p>The <code>secretKey</code> field.</p>
     * @see java.lang.String
     */
    private String secretKey;
    /**
     * <code>region</code>
     * {@link java.lang.String} <p>The <code>region</code> field.</p>
     * @see java.lang.String
     */
    private String region;
    /**
     * <code>expire</code>
     * {@link java.lang.Long} <p>The <code>expire</code> field.</p>
     * @see java.lang.Long
     */
    private Long expire = 3600L;
    /**
     * <code>roleName</code>
     * {@link java.lang.String} <p>The <code>roleName</code> field.</p>
     * @see java.lang.String
     */
    private String roleName;
    /**
     * <code>roleArn</code>
     * {@link java.lang.String} <p>The <code>roleArn</code> field.</p>
     * @see java.lang.String
     */
    private String roleArn;
    /**
     * <code>bucket</code>
     * {@link java.lang.String} <p>The <code>bucket</code> field.</p>
     * @see java.lang.String
     */
    private String bucket;
    /**
     * <code>prefix</code>
     * {@link java.lang.String} <p>The <code>prefix</code> field.</p>
     * @see java.lang.String
     */
    private String prefix;
    /**
     * <code>image</code>
     * {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties.OssImage} <p>The <code>image</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties.OssImage
     * @see org.springframework.boot.context.properties.NestedConfigurationProperty
     */
    @NestedConfigurationProperty
    private OssImage image;

    /**
     * <code>OssImage</code>
     * <p>The oss image class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see lombok.Getter
     * @see lombok.Setter
     * @since Jdk1.8
     */
    @Getter
    @Setter
    public static class OssImage {
        /**
         * <code>compress</code>
         * {@link java.lang.Long} <p>The <code>compress</code> field.</p>
         * @see java.lang.Long
         */
        private Long compress = 100 * 1024L;
        /**
         * <code>quality</code>
         * {@link java.lang.Double} <p>The <code>quality</code> field.</p>
         * @see java.lang.Double
         */
        private Double quality = 0.5d;
        /**
         * <code>scale</code>
         * {@link java.lang.Double} <p>The <code>scale</code> field.</p>
         * @see java.lang.Double
         */
        private Double scale = 0.5d;
    }

}
