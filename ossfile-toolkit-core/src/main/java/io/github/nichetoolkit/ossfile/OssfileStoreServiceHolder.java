package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.fitter.RestFulfilledFitter;
import io.github.nichetoolkit.rest.holder.ApplicationContextHolder;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.SpringFactoriesLoader;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>OssfileStoreServiceHolder</code>
 * <p>The ossfile store service holder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.fitter.RestFulfilledFitter
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public class OssfileStoreServiceHolder implements RestFulfilledFitter<OssfileStoreServiceHolder> {

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileStoreServiceHolder} <p>The constant <code>INSTANCE</code> field.</p>
     */
    private static OssfileStoreServiceHolder INSTANCE = null;

    /**
     * <code>OSSFILE_STORE_SERVICE_CACHE</code>
     * {@link java.util.Map} <p>The constant <code>OSSFILE_STORE_SERVICE_CACHE</code> field.</p>
     * @see java.util.Map
     */
    private static final Map<OssfileProviderType, OssfileStoreService> OSSFILE_STORE_SERVICE_CACHE = new ConcurrentHashMap<>();

    /**
     * <code>properties</code>
     * {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The <code>properties</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     * @see javax.annotation.Resource
     */
    @Resource
    private OssfileProperties properties;

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    @Override
    public void afterAutowirePropertiesSet() {
        cacheStoreServices();
    }

    /**
     * <code>cacheStoreServices</code>
     * <p>The cache store services method.</p>
     */
    static void cacheStoreServices() {
        List<OssfileStoreService> storeServices = ApplicationContextHolder.beansOfType(OssfileStoreService.class);
        if (GeneralUtils.isNotEmpty(storeServices)) {
            return;
        }
        storeServices = SpringFactoriesLoader.loadFactories(OssfileStoreService.class, null);
        if (GeneralUtils.isEmpty(storeServices)) {
            return;
        }
        storeServices.forEach(storeService -> {
            OSSFILE_STORE_SERVICE_CACHE.putIfAbsent(storeService.providerType(),storeService);
        });
        log.debug("There are {} store service beans has be initiated.", storeServices.size());
    }

    /**
     * <code>storeService</code>
     * <p>The store service method.</p>
     * @param providerType {@link io.github.nichetoolkit.ossfile.OssfileProviderType} <p>The provider type parameter is <code>OssfileProviderType</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileStoreService} <p>The store service return object is <code>OssfileStoreService</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileProviderType
     */
    public static OssfileStoreService storeService(OssfileProviderType providerType) {
        return OSSFILE_STORE_SERVICE_CACHE.get(providerType);
    }

    /**
     * <code>properties</code>
     * <p>The properties method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The properties return object is <code>OssfileProperties</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     */
    public static OssfileProperties properties() {
        return INSTANCE.properties;
    }
}
