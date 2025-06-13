package io.github.nichetoolkit.ossfile.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
@ImportAutoConfiguration(value = {DatasourceAutoConfigure.class})
public class OssfileExampleAutoConfigure {
    public OssfileExampleAutoConfigure() {
        log.debug("The auto configuration for [ossfile-example] initiated");
    }
}
