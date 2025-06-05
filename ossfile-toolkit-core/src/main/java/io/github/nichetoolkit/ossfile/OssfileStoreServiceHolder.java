package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.error.lack.InstanceLackError;
import io.github.nichetoolkit.rest.fitter.RestFulfilledFitter;
import io.github.nichetoolkit.rest.holder.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        RestOptional.ofEmptyable(storeServices).orElseThrow(() -> new InstanceLackError("There are no any store service has been initiated."));
        storeServices.forEach(storeService -> OSSFILE_STORE_SERVICE_CACHE.putIfAbsent(storeService.providerType(),storeService));
        log.debug("There are {} store service beans has been initiated.", storeServices.size());
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
     * <code>anyoneStoreService</code>
     * <p>The anyone store service method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileStoreService} <p>The anyone store service return object is <code>OssfileStoreService</code> type.</p>
     */
    public static OssfileStoreService anyoneStoreService() {
        Optional<OssfileStoreService>  anyoneStoreServiceOptional = OSSFILE_STORE_SERVICE_CACHE.values().stream().findAny();
        assert anyoneStoreServiceOptional.isPresent();
        return anyoneStoreServiceOptional.get();
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
