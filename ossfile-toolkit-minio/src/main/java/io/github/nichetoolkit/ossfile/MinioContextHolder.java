package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.fitter.RestFulfilledFitter;
import io.minio.MinioAsyncClient;
import io.minio.MinioClient;

import javax.annotation.Resource;

/**
 * <code>MinioContextHolder</code>
 * <p>The minio context holder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.fitter.RestFulfilledFitter
 * @since Jdk1.8
 */
public class MinioContextHolder implements RestFulfilledFitter<MinioContextHolder> {

    /**
     * <code>minioClient</code>
     * {@link io.minio.MinioClient} <p>The <code>minioClient</code> field.</p>
     * @see io.minio.MinioClient
     */
    private MinioClient minioClient;

    /**
     * <code>asyncClient</code>
     * {@link io.minio.MinioAsyncClient} <p>The <code>asyncClient</code> field.</p>
     * @see io.minio.MinioAsyncClient
     */
    private MinioAsyncClient asyncClient;

    /**
     * <code>multipartClient</code>
     * {@link io.github.nichetoolkit.ossfile.MinioMultipartClient} <p>The <code>multipartClient</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.MinioMultipartClient
     */
    private MinioMultipartClient multipartClient;

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
     * @see javax.annotation.Resource
     */
    @Resource
    private OssfileProperties ossfileProperties;

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.ossfile.MinioContextHolder} <p>The constant <code>INSTANCE</code> field.</p>
     */
    private static MinioContextHolder INSTANCE = null;

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    @Override
    public void afterAutowirePropertiesSet() {
        this.asyncClient = MinioHelper.createAsyncClient(this.ossfileProperties);
        this.minioClient = MinioHelper.createMinioClient(this.ossfileProperties);
        this.multipartClient = new MinioMultipartClient(this.asyncClient);
        this.defaultRegion = ossfileProperties.getRegion();
    }

    /**
     * <code>refreshClient</code>
     * <p>The refresh client method.</p>
     */
    static void refreshClient() {
        INSTANCE.minioClient = MinioHelper.createMinioClient(INSTANCE.ossfileProperties);
    }

    /**
     * <code>defaultClient</code>
     * <p>The default client method.</p>
     * @return {@link io.minio.MinioClient} <p>The default client return object is <code>MinioClient</code> type.</p>
     * @see io.minio.MinioClient
     */
    public static MinioClient defaultClient() {
        return INSTANCE.minioClient;
    }

    /**
     * <code>multipartClient</code>
     * <p>The multipart client method.</p>
     * @return {@link io.minio.MinioClient} <p>The multipart client return object is <code>MinioClient</code> type.</p>
     * @see io.minio.MinioClient
     */
    public static MinioMultipartClient multipartClient() {
        return INSTANCE.multipartClient;
    }

    /**
     * <code>asyncClient</code>
     * <p>The async client method.</p>
     * @return {@link io.minio.MinioAsyncClient} <p>The async client return object is <code>MinioAsyncClient</code> type.</p>
     * @see io.minio.MinioAsyncClient
     */
    public static MinioAsyncClient asyncClient() {
        return INSTANCE.asyncClient;
    }

    /**
     * <code>defaultRegion</code>
     * <p>The default region method.</p>
     * @return {@link java.lang.String} <p>The default region return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String defaultRegion() {
        return INSTANCE.defaultRegion;
    }



}
