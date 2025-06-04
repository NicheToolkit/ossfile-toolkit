package io.github.nichetoolkit.ossfile.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>FileServiceAutoConfigure</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
public class FileServiceAutoConfigure {

    public FileServiceAutoConfigure() {
        log.debug("================= file-service-auto-config initiated ！ ===================");
    }
}
