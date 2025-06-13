package io.github.nichetoolkit.ossfile.configure;

import io.github.nichetoolkit.ossfile.MinioVideoRequestHandler;
import io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>OssfileMinioAutoConfigure</code>
 * <p>The ossfile minio auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
 * @since Jdk1.8
 */
@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
public class OssfileMinioAutoConfigure {
    /**
     * <code>OssfileMinioAutoConfigure</code>
     * <p>Instantiates a new ossfile minio auto configure.</p>
     */
    public OssfileMinioAutoConfigure() {
        log.debug("The auto configuration for [ossfile-minio] initiated");
    }

    /**
     * <code>minioVideoRequestHandler</code>
     * <p>The minio video request handler method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler} <p>The minio video request handler return object is <code>OssfileVideoRequestHandler</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler
     * @see org.springframework.context.annotation.Bean
     * @see org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
     */
    @Bean
    @ConditionalOnMissingBean(value = OssfileVideoRequestHandler.class)
    public OssfileVideoRequestHandler minioVideoRequestHandler() {
        return new MinioVideoRequestHandler();
    }
}
