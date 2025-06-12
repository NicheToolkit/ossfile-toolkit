package io.github.nichetoolkit.ossfile.configure;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@MapperScan("io.github.nichetoolkit.ossfile.mapper")
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
@ImportAutoConfiguration(value = {DatasourceAutoConfigure.class})
public class OssfileExampleAutoConfigure {
    public OssfileExampleAutoConfigure() {
        log.debug("The auto configuration for [ossfile-example] initiated");
    }
}
