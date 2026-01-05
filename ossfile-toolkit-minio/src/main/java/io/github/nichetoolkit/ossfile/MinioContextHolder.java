package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.error.lack.ConfigureLackError;
import io.github.nichetoolkit.rest.fitter.RestFulfilledFitter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * <code>MinioContextHolder</code>
 * <p>The minio context holder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.fitter.RestFulfilledFitter
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk17
 */
@Slf4j
public class MinioContextHolder implements RestFulfilledFitter<MinioContextHolder> {

    /**
     * <code>minioClient</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileMinioClient} <p>The <code>minioClient</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileMinioClient
     */
    private OssfileMinioClient minioClient;

    /**
     * <code>defaultRegion</code>
     * {@link java.lang.String} <p>The <code>defaultRegion</code> field.</p>
     * @see java.lang.String
     */
    private String defaultRegion;

    /**
     * <code>ossfileProperties</code>
     * {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The <code>ossfileProperties</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     * @see lombok.Setter
     * @see javax.annotation.Resource
     */
    @Setter
    @Resource
    private OssfileProperties ossfileProperties;

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.ossfile.MinioContextHolder} <p>The constant <code>INSTANCE</code> field.</p>
     */
    private static MinioContextHolder INSTANCE = null;

    /**
     * <code>instance</code>
     * <p>The instance method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.MinioContextHolder} <p>The instance return object is <code>MinioContextHolder</code> type.</p>
     */
    public static MinioContextHolder instance() {
        return RestOptional.ofNullable(INSTANCE).orNullThrow(ConfigureLackError::new);
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    @Override
    public void afterAutowirePropertiesSet() {
        this.minioClient = MinioHelper.createOssfileClient(this.ossfileProperties);
        this.defaultRegion = ossfileProperties.getRegion();
    }

    @Override
    public int getOrder() {
        return 10;
    }

    /**
     * <code>ossfileProperties</code>
     * <p>The ossfile properties method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The ossfile properties return object is <code>OssfileProperties</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     */
    public static OssfileProperties ossfileProperties() {
        return instance().ossfileProperties;
    }

    /**
     * <code>refreshClient</code>
     * <p>The refresh client method.</p>
     */
    static void refreshClient() {
        instance().minioClient = MinioHelper.createOssfileClient(instance().ossfileProperties);
    }

    /**
     * <code>defaultClient</code>
     * <p>The default client method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileMinioClient} <p>The default client return object is <code>OssfileMinioClient</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileMinioClient
     */
    public static OssfileMinioClient defaultClient() {
        return instance().minioClient;
    }

    /**
     * <code>defaultRegion</code>
     * <p>The default region method.</p>
     * @return {@link java.lang.String} <p>The default region return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String defaultRegion() {
        return instance().defaultRegion;
    }

}
