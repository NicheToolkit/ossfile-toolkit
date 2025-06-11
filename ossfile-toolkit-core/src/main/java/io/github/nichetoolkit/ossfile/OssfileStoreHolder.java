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

@Slf4j
public class OssfileStoreHolder implements RestFulfilledFitter<OssfileStoreHolder> {

    private static OssfileStoreHolder INSTANCE = null;

    private static final Map<OssfileProviderType, OssfileStoreService> OSSFILE_STORE_SERVICE_CACHE = new ConcurrentHashMap<>();

    @Resource
    private OssfileProperties properties;

    private String defaultBucket;

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    @Override
    public void afterAutowirePropertiesSet() {
        this.defaultBucket = properties.getBucket();
        cacheStoreServices();
    }

    static void cacheStoreServices() {
        List<OssfileStoreService> storeServices = ApplicationContextHolder.beansOfType(OssfileStoreService.class);
        RestOptional.ofEmptyable(storeServices).orElseThrow(() -> new InstanceLackError("There are no any store service has been initiated."));
        storeServices.forEach(storeService -> OSSFILE_STORE_SERVICE_CACHE.putIfAbsent(storeService.providerType(),storeService));
        log.debug("There are {} store service beans has been initiated.", storeServices.size());
    }

    public static OssfileStoreService storeServiceOfType(OssfileProviderType providerType) {
        return OSSFILE_STORE_SERVICE_CACHE.get(providerType);
    }

    public static OssfileStoreService storeServiceOfAnyone() {
        Optional<OssfileStoreService>  anyoneStoreServiceOptional = OSSFILE_STORE_SERVICE_CACHE.values().stream().findAny();
        assert anyoneStoreServiceOptional.isPresent();
        return anyoneStoreServiceOptional.get();
    }

    public static OssfileProperties properties() {
        return INSTANCE.properties;
    }


    static void switchBucket(String bucketName) {
        INSTANCE.defaultBucket = bucketName;
    }

    public static String defaultBucket() {
        return INSTANCE.defaultBucket;
    }

    public static OssfileStoreService storeService(OssfileProviderType providerType) {
        OssfileStoreService storeService = OSSFILE_STORE_SERVICE_CACHE.get(providerType);
        return Optional.ofNullable(storeService).orElse(storeServiceOfAnyone());
    }


}
