package io.github.nichetoolkit.ossfile.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <code>OssfileCoreAutoConfigure</code>
 * <p>The ossfile core auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.autoconfigure.AutoConfiguration
 * @see org.springframework.context.annotation.ComponentScan
 * @since Jdk1.8
 */
@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
public class OssfileCoreAutoConfigure {

    /**
     * <code>OssfileCoreAutoConfigure</code>
     * <p>Instantiates a new ossfile core auto configure.</p>
     */
    public OssfileCoreAutoConfigure() {
        log.debug("The auto configuration for [ossfile-core] initiated");
    }
}
