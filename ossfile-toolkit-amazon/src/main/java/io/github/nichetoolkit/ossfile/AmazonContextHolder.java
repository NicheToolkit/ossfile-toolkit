package io.github.nichetoolkit.ossfile;

import com.amazonaws.services.s3.AmazonS3;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.fitter.RestFulfilledFitter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * <code>AmazonContextHolder</code>
 * <p>The amazon context holder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.fitter.RestFulfilledFitter
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public class AmazonContextHolder implements RestFulfilledFitter<AmazonContextHolder> {

    /**
     * <code>amazonClient</code>
     * {@link com.amazonaws.services.s3.AmazonS3} <p>The <code>amazonClient</code> field.</p>
     * @see com.amazonaws.services.s3.AmazonS3
     */
    private AmazonS3 amazonClient;

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
    }

    @Override
    public int getOrder() {
        return 10;
    }

    /**
     * <code>refreshClient</code>
     * <p>The refresh client method.</p>
     */
    static void refreshClient() {
        INSTANCE.amazonClient = AmazonHelper.createAmazonClient(INSTANCE.ossfileProperties);
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
