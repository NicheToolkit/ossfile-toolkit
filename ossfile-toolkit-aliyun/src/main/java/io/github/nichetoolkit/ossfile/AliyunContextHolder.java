package io.github.nichetoolkit.ossfile;

import com.aliyun.oss.OSS;
import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.error.lack.ConfigureLackError;
import io.github.nichetoolkit.rest.fitter.RestFulfilledFitter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.Resource;

/**
 * <code>AliyunContextHolder</code>
 * <p>The aliyun context holder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.fitter.RestFulfilledFitter
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk17
 */
@Slf4j
public class AliyunContextHolder implements RestFulfilledFitter<AliyunContextHolder> {

    /**
     * <code>aliyunClient</code>
     * {@link com.aliyun.oss.OSS} <p>The <code>aliyunClient</code> field.</p>
     * @see com.aliyun.oss.OSS
     */
    private OSS aliyunClient;

    /**
     * <code>ossfileProperties</code>
     * {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The <code>ossfileProperties</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     * @see lombok.Setter
     * @see jakarta.annotation.Resource
     */
    @Setter
    @Resource
    private OssfileProperties ossfileProperties;

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.ossfile.AliyunContextHolder} <p>The constant <code>INSTANCE</code> field.</p>
     */
    private static AliyunContextHolder INSTANCE = null;

    /**
     * <code>instance</code>
     * <p>The instance method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.AliyunContextHolder} <p>The instance return object is <code>AliyunContextHolder</code> type.</p>
     */
    public static AliyunContextHolder instance() {
        return RestOptional.ofNullable(INSTANCE).orNullThrow(ConfigureLackError::new);
    }

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    @Override
    public void afterAutowirePropertiesSet() {
        this.aliyunClient = AliyunHelper.createAliyunClient(this.ossfileProperties);
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
        instance().aliyunClient = AliyunHelper.createAliyunClient(instance().ossfileProperties);
    }

    /**
     * <code>defaultClient</code>
     * <p>The default client method.</p>
     * @return {@link com.aliyun.oss.OSS} <p>The default client return object is <code>OSS</code> type.</p>
     * @see com.aliyun.oss.OSS
     */
    public static OSS defaultClient() {
        return instance().aliyunClient;
    }



}
