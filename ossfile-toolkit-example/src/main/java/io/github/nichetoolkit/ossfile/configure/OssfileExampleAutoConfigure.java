package io.github.nichetoolkit.ossfile.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * <code>OssfileExampleAutoConfigure</code>
 * <p>The ossfile example auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.autoconfigure.AutoConfiguration
 * @see org.springframework.context.annotation.ComponentScan
 * @see org.springframework.boot.autoconfigure.ImportAutoConfiguration
 * @since Jdk1.8
 */
@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
@ImportAutoConfiguration(value = {DatasourceAutoConfigure.class})
public class OssfileExampleAutoConfigure {
    /**
     * <code>OssfileExampleAutoConfigure</code>
     * <p>Instantiates a new ossfile example auto configure.</p>
     */
    public OssfileExampleAutoConfigure() {
        log.debug("The auto configuration for [ossfile-example] initiated");
    }
}
