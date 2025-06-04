package io.github.nichetoolkit.ossfile.configure;

import io.github.nichetoolkit.ossfile.MinioVideoRequestHandler;
import io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>FileMinioStarterAutoConfigure</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
public class OssfileMinioAutoConfigure {
    public OssfileMinioAutoConfigure() {
        log.debug("The auto configuration for [ossfile-minio] initiated");
    }

    @Bean
    @ConditionalOnMissingBean(value = OssfileVideoRequestHandler.class)
    public OssfileVideoRequestHandler minioVideoRequestHandler() {
        return new MinioVideoRequestHandler();
    }
}
