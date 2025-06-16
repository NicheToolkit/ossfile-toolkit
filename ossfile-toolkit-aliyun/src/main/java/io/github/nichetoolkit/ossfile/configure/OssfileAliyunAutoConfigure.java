package io.github.nichetoolkit.ossfile.configure;

import io.github.nichetoolkit.ossfile.AliyunVideoRequestHandler;
import io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>OssfileAliyunAutoConfigure</code>
 * <p>The ossfile aliyun auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.autoconfigure.AutoConfiguration
 * @see org.springframework.context.annotation.ComponentScan
 * @since Jdk1.8
 */
@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
public class OssfileAliyunAutoConfigure {
    /**
     * <code>OssfileAliyunAutoConfigure</code>
     * <p>Instantiates a new ossfile aliyun auto configure.</p>
     */
    public OssfileAliyunAutoConfigure() {
        log.debug("The auto configuration for [ossfile-aliyun] initiated");
    }

    /**
     * <code>aliyunVideoRequestHandler</code>
     * <p>The aliyun video request handler method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler} <p>The aliyun video request handler return object is <code>OssfileVideoRequestHandler</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnMissingBean(value = OssfileVideoRequestHandler.class)
    public OssfileVideoRequestHandler aliyunVideoRequestHandler() {
        return new AliyunVideoRequestHandler();
    }
}
