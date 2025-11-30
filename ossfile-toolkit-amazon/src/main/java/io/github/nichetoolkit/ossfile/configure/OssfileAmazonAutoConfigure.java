package io.github.nichetoolkit.ossfile.configure;

import io.github.nichetoolkit.ossfile.AmazonVideoRequestHandler;
import io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>OssfileAmazonAutoConfigure</code>
 * <p>The ossfile amazon auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.autoconfigure.AutoConfiguration
 * @see org.springframework.context.annotation.ComponentScan
 * @since Jdk17
 */
@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
public class OssfileAmazonAutoConfigure {
    /**
     * <code>OssfileAmazonAutoConfigure</code>
     * <p>Instantiates a new ossfile amazon auto configure.</p>
     */
    public OssfileAmazonAutoConfigure() {
        log.debug("The auto configuration for [ossfile-amazon] initiated");
    }

    /**
     * <code>amazonVideoRequestHandler</code>
     * <p>The amazon video request handler method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler} <p>The amazon video request handler return object is <code>OssfileVideoRequestHandler</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnMissingBean(value = OssfileVideoRequestHandler.class)
    public OssfileVideoRequestHandler amazonVideoRequestHandler() {
        return new AmazonVideoRequestHandler();
    }
}
