package io.github.nichetoolkit.ossfile.configure;

import io.github.nichetoolkit.rest.resource.RestI18nResources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * <code>OssfileCoreAutoConfigure</code>
 * <p>The ossfile core auto configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.boot.autoconfigure.AutoConfiguration
 * @see org.springframework.context.annotation.ComponentScan
 * @since Jdk17
 */
@Slf4j
@AutoConfiguration
@ComponentScan(basePackages = {"io.github.nichetoolkit.ossfile"})
public class OssfileCoreAutoConfigure {

    /**
     * <code>OSSFILE_I18N</code>
     * {@link java.lang.String} <p>The constant <code>OSSFILE_I18N</code> field.</p>
     * @see java.lang.String
     */
    private static final String OSSFILE_I18N = "ossfile-i18n/messages";


    /**
     * <code>OssfileCoreAutoConfigure</code>
     * <p>Instantiates a new ossfile core auto configure.</p>
     */
    public OssfileCoreAutoConfigure() {
        log.debug("The auto configuration for [ossfile-core] initiated");
    }

    /**
     * <code>ossfileI18nResources</code>
     * <p>The ossfile i 18 n resources method.</p>
     * @return {@link io.github.nichetoolkit.rest.resource.RestI18nResources} <p>The ossfile i 18 n resources return object is <code>RestI18nResources</code> type.</p>
     * @see io.github.nichetoolkit.rest.resource.RestI18nResources
     * @see org.springframework.context.annotation.Bean
     */
    @Bean
    public RestI18nResources ossfileI18nResources() {
        return RestI18nResources.of(OSSFILE_I18N);
    }

}
