package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.ossfile.service.OssfileBulkService;
import io.github.nichetoolkit.ossfile.service.OssfilePartService;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.error.lack.InstanceLackError;
import io.github.nichetoolkit.rest.fitter.RestFulfilledFitter;
import io.github.nichetoolkit.rest.holder.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * <code>OssfileServiceHolder</code>
 * <p>The ossfile service holder class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rest.fitter.RestFulfilledFitter
 * @see lombok.extern.slf4j.Slf4j
 * @since Jdk1.8
 */
@Slf4j
public class OssfileServiceHolder implements RestFulfilledFitter<OssfileServiceHolder> {

    /**
     * <code>INSTANCE</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileServiceHolder} <p>The constant <code>INSTANCE</code> field.</p>
     */
    private static OssfileServiceHolder INSTANCE = null;

    /**
     * <code>properties</code>
     * {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The <code>properties</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     * @see javax.annotation.Resource
     */
    @Resource
    private OssfileProperties properties;

    /**
     * <code>bulkService</code>
     * {@link io.github.nichetoolkit.ossfile.service.OssfileBulkService} <p>The <code>bulkService</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.service.OssfileBulkService
     */
    protected OssfileBulkService bulkService;

    /**
     * <code>partService</code>
     * {@link io.github.nichetoolkit.ossfile.service.OssfilePartService} <p>The <code>partService</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.service.OssfilePartService
     */
    protected OssfilePartService partService;

    /**
     * <code>videoHandler</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler} <p>The <code>videoHandler</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler
     */
    protected OssfileVideoRequestHandler videoHandler;

    /**
     * <code>storeService</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileStoreService} <p>The <code>storeService</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileStoreService
     */
    protected OssfileStoreService storeService;

    @Override
    public void afterPropertiesSet() {
        INSTANCE = this;
    }

    @Override
    public void afterAutowirePropertiesSet() {
        this.bulkService = ApplicationContextHolder.beanOfType(OssfileBulkService.class);
        RestOptional.ofEmptyable(this.bulkService).orElseThrow(() -> new InstanceLackError("It is no bulk service has been initiated."));
        this.partService = ApplicationContextHolder.beanOfType(OssfilePartService.class);
        RestOptional.ofEmptyable(this.partService).orElseThrow(() -> new InstanceLackError("It is no part service has been initiated."));
        this.videoHandler = ApplicationContextHolder.beanOfType(OssfileVideoRequestHandler.class);
        RestOptional.ofEmptyable(this.videoHandler).orElseThrow(() -> new InstanceLackError("It is no video handler service has been initiated."));
        OssfileProviderType ossfileProviderType = OssfileStoreHolder.properties().getProvider();
        this.storeService = OssfileStoreHolder.storeService(ossfileProviderType);
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
     * <code>storeService</code>
     * <p>The store service method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileStoreService} <p>The store service return object is <code>OssfileStoreService</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileStoreService
     */
    public static OssfileStoreService storeService() {
        return INSTANCE.storeService;
    }

    /**
     * <code>bulkService</code>
     * <p>The bulk service method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.service.OssfileBulkService} <p>The bulk service return object is <code>OssfileBulkService</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.service.OssfileBulkService
     */
    public static OssfileBulkService bulkService() {
        return INSTANCE.bulkService;
    }

    /**
     * <code>partService</code>
     * <p>The part service method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.service.OssfilePartService} <p>The part service return object is <code>OssfilePartService</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.service.OssfilePartService
     */
    public static OssfilePartService partService() {
        return INSTANCE.partService;
    }

    /**
     * <code>videoHandler</code>
     * <p>The video handler method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler} <p>The video handler return object is <code>OssfileVideoRequestHandler</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileVideoRequestHandler
     */
    public static OssfileVideoRequestHandler videoHandler() {
        return INSTANCE.videoHandler;
    }



}
