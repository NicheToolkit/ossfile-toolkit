package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.RestStatus;
import io.github.nichetoolkit.rest.error.lack.InstanceLackError;
import io.github.nichetoolkit.rest.error.natives.FileErrorException;
import io.github.nichetoolkit.rest.error.natives.ServiceErrorException;
import io.github.nichetoolkit.rest.fitter.RestFulfilledFitter;
import io.github.nichetoolkit.rest.holder.ApplicationContextHolder;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * <code>OssfileStoreHolder</code>
 * <p>The ossfile store holder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.fitter.RestFulfilledFitter
 * @see lombok.extern.slf4j.Slf4j
 * @see lombok.Setter
 * @since Jdk1.8
 */
@Slf4j
@Setter
public class OssfileStoreHolder implements RestFulfilledFitter<OssfileStoreHolder> {

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileStoreHolder} <p>The constant <code>INSTANCE</code> field.</p>
     */
    private static OssfileStoreHolder INSTANCE = null;

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
    public int getOrder() {
        return 20;
    }

    /**
     * <code>defaultBucket</code>
     * {@link java.lang.String} <p>The <code>defaultBucket</code> field.</p>
     * @see java.lang.String
     */
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

    /**
     * <code>cacheStoreServices</code>
     * <p>The cache store services method.</p>
     */
    static void cacheStoreServices() {
        List<OssfileStoreService> storeServices = ApplicationContextHolder.beansOfType(OssfileStoreService.class);
        RestOptional.ofEmptyable(storeServices).orElseThrow(() -> new InstanceLackError("There are no any store service has been initiated."));
        storeServices.forEach(storeService -> OSSFILE_STORE_SERVICE_CACHE.putIfAbsent(storeService.providerType(), storeService));
        log.debug("There are {} store service beans has been initiated.", storeServices.size());
    }

    /**
     * <code>storeServiceOfType</code>
     * <p>The store service of type method.</p>
     * @param providerType {@link io.github.nichetoolkit.ossfile.OssfileProviderType} <p>The provider type parameter is <code>OssfileProviderType</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileStoreService} <p>The store service of type return object is <code>OssfileStoreService</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileProviderType
     * @see io.github.nichetoolkit.ossfile.OssfileStoreService
     */
    public static OssfileStoreService storeServiceOfType(OssfileProviderType providerType) {
        return OSSFILE_STORE_SERVICE_CACHE.get(providerType);
    }

    /**
     * <code>storeServiceOfAnyone</code>
     * <p>The store service of anyone method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileStoreService} <p>The store service of anyone return object is <code>OssfileStoreService</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileStoreService
     */
    public static OssfileStoreService storeServiceOfAnyone() {
        Optional<OssfileStoreService> anyoneStoreServiceOptional = OSSFILE_STORE_SERVICE_CACHE.values().stream().findAny();
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


    /**
     * <code>switchBucket</code>
     * <p>The switch bucket method.</p>
     * @param bucketName {@link java.lang.String} <p>The bucket name parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    static void switchBucket(String bucketName) {
        INSTANCE.defaultBucket = bucketName;
    }

    /**
     * <code>defaultBucket</code>
     * <p>The default bucket method.</p>
     * @return {@link java.lang.String} <p>The default bucket return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String defaultBucket() {
        return INSTANCE.defaultBucket;
    }

    /**
     * <code>previewPrefix</code>
     * <p>The preview prefix method.</p>
     * @return {@link java.lang.String} <p>The preview prefix return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String previewPrefix() {
        return INSTANCE.properties.previewPrefix();
    }

    /**
     * <code>bulkPrefix</code>
     * <p>The bulk prefix method.</p>
     * @return {@link java.lang.String} <p>The bulk prefix return object is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public static String bulkPrefix() {
        return INSTANCE.properties.bulkPrefix();
    }

    /**
     * <code>storeService</code>
     * <p>The store service method.</p>
     * @param providerType {@link io.github.nichetoolkit.ossfile.OssfileProviderType} <p>The provider type parameter is <code>OssfileProviderType</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileStoreService} <p>The store service return object is <code>OssfileStoreService</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileProviderType
     * @see io.github.nichetoolkit.ossfile.OssfileStoreService
     */
    public static OssfileStoreService storeService(OssfileProviderType providerType) {
        OssfileStoreService storeService = OSSFILE_STORE_SERVICE_CACHE.get(providerType);
        return Optional.ofNullable(storeService).orElse(storeServiceOfAnyone());
    }

    /**
     * <code>serviceErrorOfCause</code>
     * <p>The service error of cause method.</p>
     * @param restStatus {@link io.github.nichetoolkit.rest.RestStatus} <p>The rest status parameter is <code>RestStatus</code> type.</p>
     * @param exception  {@link java.lang.Exception} <p>The exception parameter is <code>Exception</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.error.natives.ServiceErrorException} <p>The service error of cause return object is <code>ServiceErrorException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see java.lang.Exception
     * @see io.github.nichetoolkit.rest.error.natives.ServiceErrorException
     */
    public static ServiceErrorException serviceErrorOfCause(RestStatus restStatus, Exception exception) {
        return exceptionOfCause(restStatus, exception, ServiceErrorException::new);
    }

    /**
     * <code>fileErrorOfCause</code>
     * <p>The file error of cause method.</p>
     * @param restStatus {@link io.github.nichetoolkit.rest.RestStatus} <p>The rest status parameter is <code>RestStatus</code> type.</p>
     * @param exception  {@link java.lang.Throwable} <p>The exception parameter is <code>Throwable</code> type.</p>
     * @return {@link io.github.nichetoolkit.rest.error.natives.FileErrorException} <p>The file error of cause return object is <code>FileErrorException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see java.lang.Throwable
     * @see io.github.nichetoolkit.rest.error.natives.FileErrorException
     */
    public static FileErrorException fileErrorOfCause(RestStatus restStatus, Throwable exception) {
        return exceptionOfCause(restStatus, exception, FileErrorException::new);
    }

    /**
     * <code>exceptionOfCause</code>
     * <p>The exception of cause method.</p>
     * @param <T>        {@link io.github.nichetoolkit.rest.RestException} <p>The generic parameter is <code>RestException</code> type.</p>
     * @param restStatus {@link io.github.nichetoolkit.rest.RestStatus} <p>The rest status parameter is <code>RestStatus</code> type.</p>
     * @param exception  {@link java.lang.Throwable} <p>The exception parameter is <code>Throwable</code> type.</p>
     * @param function   {@link java.util.function.BiFunction} <p>The function parameter is <code>BiFunction</code> type.</p>
     * @return T <p>The exception of cause return object is <code>T</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     * @see io.github.nichetoolkit.rest.RestStatus
     * @see java.lang.Throwable
     * @see java.util.function.BiFunction
     */
    public static <T extends RestException> T exceptionOfCause(RestStatus restStatus, Throwable exception, BiFunction<RestStatus, String, T> function) {
        Throwable cause = exception.getCause();
        String message = exception.getMessage();
        if (GeneralUtils.isNotEmpty(cause)) {
            message = cause.getMessage();
        } else {
            cause = exception;
        }
        if (cause instanceof RestStatus) {
            return function.apply((RestStatus)cause, message);
        } else {
            return function.apply(restStatus, message);
        }
    }

}
