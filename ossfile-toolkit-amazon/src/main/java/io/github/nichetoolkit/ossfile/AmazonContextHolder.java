package io.github.nichetoolkit.ossfile;

import com.amazonaws.services.s3.AmazonS3;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.fitter.RestFulfilledFitter;

import javax.annotation.Resource;

/**
 * <code>AmazonContextHolder</code>
 * <p>The amazon context holder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.fitter.RestFulfilledFitter
 * @since Jdk1.8
 */
public class AmazonContextHolder implements RestFulfilledFitter<AmazonContextHolder> {

    /**
     * <code>amazonClient</code>
     * {@link com.amazonaws.services.s3.AmazonS3} <p>The <code>amazonClient</code> field.</p>
     * @see com.amazonaws.services.s3.AmazonS3
     */
    private AmazonS3 amazonClient;

    /**
     * <code>defaultBucket</code>
     * {@link java.lang.String} <p>The <code>defaultBucket</code> field.</p>
     * @see java.lang.String
     */
    private String defaultBucket;

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
     * {@link io.github.nichetoolkit.ossfile.AmazonContextHolder} <p>The constant <code>INSTANCE</code> field.</p>
     */
    private static AmazonContextHolder INSTANCE = null;

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    @Override
    public void afterAutowirePropertiesSet() {
        this.amazonClient = AmazonHelper.createAmazonClient(this.ossfileProperties);
        this.defaultBucket = ossfileProperties.getBucket();
    }

    /**
     * <code>refreshClient</code>
     * <p>The refresh client method.</p>
     */
    static void refreshClient() {
        INSTANCE.amazonClient = AmazonHelper.createAmazonClient(INSTANCE.ossfileProperties);
    }

    /**
     * <code>switchBucket</code>
     * <p>The switch bucket method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    static void switchBucket(String bucketName) {
        INSTANCE.defaultBucket = bucketName;
    }

    /**
     * <code>defaultBucket</code>
     * <p>The default bucket method.</p>
     * @return {@link java.lang.String} <p>The default bucket return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String defaultBucket() {
        return INSTANCE.defaultBucket;
    }

    /**
     * <code>defaultClient</code>
     * <p>The default client method.</p>
     * @return {@link com.amazonaws.services.s3.AmazonS3} <p>The default client return object is <code>AmazonS3</code> type.</p>
     * @see com.amazonaws.services.s3.AmazonS3
     */
    public static AmazonS3 defaultClient() {
        return INSTANCE.amazonClient;
    }



}
