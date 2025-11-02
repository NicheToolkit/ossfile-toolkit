package io.github.nichetoolkit.ossfile.configure;

import io.github.nichetoolkit.ossfile.OssfileConstants;
import io.github.nichetoolkit.ossfile.OssfileProviderType;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
     * <code>intranet</code>
     * {@link java.lang.String} <p>The <code>intranet</code> field.</p>
     * @see java.lang.String
     */
    private String intranet;

    /**
     * <code>intranetPriority</code>
     * {@link java.lang.Boolean} <p>The <code>intranetPriority</code> field.</p>
     * @see java.lang.Boolean
     */
    private Boolean intranetPriority= false;

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
     * <code>bulkPrefix</code>
     * {@link java.lang.String} <p>The <code>bulkPrefix</code> field.</p>
     * @see java.lang.String
     */
    private String bulkPrefix = OssfileConstants.BULK_PREFIX;
    /**
     * <code>previewPrefix</code>
     * {@link java.lang.String} <p>The <code>previewPrefix</code> field.</p>
     * @see java.lang.String
     */
    private String previewPrefix = OssfileConstants.PREVIEW_PREFIX;

    /**
     * <code>image</code>
     * {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties.OssImage} <p>The <code>image</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties.OssImage
     * @see org.springframework.boot.context.properties.NestedConfigurationProperty
     */
    @NestedConfigurationProperty
    private OssImage image = new OssImage();

    /**
     * <code>allowed</code>
     * {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties.OssAllowed} <p>The <code>allowed</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties.OssAllowed
     * @see org.springframework.boot.context.properties.NestedConfigurationProperty
     */
    @NestedConfigurationProperty
    private OssAllowed allowed = new OssAllowed();

    /**
     * <code>OssAllowed</code>
     * <p>The oss allowed class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see lombok.Setter
     * @since Jdk1.8
     */
    @Setter
    public static class OssAllowed {
        /**
         * <code>origins</code>
         * {@link java.lang.String} <p>The <code>origins</code> field.</p>
         * @see java.lang.String
         */
        private String[] origins;
        /**
         * <code>headers</code>
         * {@link java.lang.String} <p>The <code>headers</code> field.</p>
         * @see java.lang.String
         */
        private String[] headers;

        /**
         * <code>getOrigins</code>
         * <p>The get origins getter method.</p>
         * @return {@link java.util.Set} <p>The get origins return object is <code>Set</code> type.</p>
         * @see java.util.Set
         */
        public Set<String> getOrigins() {
            if (GeneralUtils.isEmpty(this.origins)) {
                return Collections.emptySet();
            }
            return Arrays.stream(this.origins).collect(Collectors.toSet());
        }

        /**
         * <code>getHeaders</code>
         * <p>The get headers getter method.</p>
         * @return {@link java.util.Set} <p>The get headers return object is <code>Set</code> type.</p>
         * @see java.util.Set
         */
        public Set<String> getHeaders() {
            if (GeneralUtils.isEmpty(this.headers)) {
                return Collections.emptySet();
            }
            return Arrays.stream(this.headers).collect(Collectors.toSet());
        }
    }

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

    /**
     * <code>bulkPrefix</code>
     * <p>The bulk prefix method.</p>
     * @return {@link java.lang.String} <p>The bulk prefix return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String bulkPrefix() {
       if (GeneralUtils.isEmpty(this.prefix)) {
           return this.prefix + OssfileConstants.FILE_SEPARATOR + this.bulkPrefix;
       }
        return this.bulkPrefix;
    }

    /**
     * <code>previewPrefix</code>
     * <p>The preview prefix method.</p>
     * @return {@link java.lang.String} <p>The preview prefix return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public String previewPrefix() {
        if (GeneralUtils.isEmpty(this.prefix)) {
            return this.prefix + OssfileConstants.FILE_SEPARATOR + this.previewPrefix;
        }
        return this.previewPrefix;
    }

}
