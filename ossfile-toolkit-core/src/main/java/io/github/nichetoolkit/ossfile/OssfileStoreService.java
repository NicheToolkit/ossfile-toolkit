package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.ossfile.configure.OssfileProperties;
import io.github.nichetoolkit.rest.RestException;
import org.springframework.beans.factory.InitializingBean;

/**
 * <code>OssfileStoreService</code>
 * <p>The ossfile store service class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see OssfileStore
 * @see org.springframework.beans.factory.InitializingBean
 * @since Jdk1.8
 */
public abstract class OssfileStoreService implements OssfileStore, InitializingBean {

    /**
     * <code>properties</code>
     * {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The <code>properties</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     */
    protected final OssfileProperties properties;

    /**
     * <code>OssfileStoreService</code>
     * <p>Instantiates a new ossfile store service.</p>
     * @param properties {@link io.github.nichetoolkit.ossfile.configure.OssfileProperties} <p>The properties parameter is <code>OssfileProperties</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.configure.OssfileProperties
     */
    public OssfileStoreService(OssfileProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    /**
     * <code>credentials</code>
     * <p>The credentials method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileCredentials} <p>The credentials return object is <code>OssfileCredentials</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileCredentials
     * @see io.github.nichetoolkit.rest.RestException
     */
    public abstract OssfileCredentials credentials() throws RestException;

    /**
     * <code>createClient</code>
     * <p>The create client method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
    public abstract void createClient() throws RestException;

    /**
     * <code>providerType</code>
     * <p>The provider type method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfileProviderType} <p>The provider type return object is <code>OssfileProviderType</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileProviderType
     */
    public abstract OssfileProviderType providerType();

}
