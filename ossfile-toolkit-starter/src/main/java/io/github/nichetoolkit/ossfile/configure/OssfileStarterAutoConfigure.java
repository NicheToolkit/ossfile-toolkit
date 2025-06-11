package io.github.nichetoolkit.ossfile.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>OssfileStarterAutoConfigure</code>
 * <p>The ossfile starter auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ComponentScan
 * @since Jdk1.8
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
public class OssfileStarterAutoConfigure {

    /**
     * <code>OssfileStarterAutoConfigure</code>
     * <p>Instantiates a new ossfile starter auto configure.</p>
     */
    public OssfileStarterAutoConfigure() {
        log.debug("The auto configuration for [ossfile-starter] initiated");
    }
}
