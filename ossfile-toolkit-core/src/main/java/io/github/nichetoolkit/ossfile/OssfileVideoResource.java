package io.github.nichetoolkit.ossfile;

import org.springframework.core.io.InputStreamResource;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;

/**
 * <code>OssfileVideoResource</code>
 * <p>The ossfile video resource class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.core.io.InputStreamResource
 * @since Jdk17
 */
public abstract class OssfileVideoResource extends InputStreamResource {
    /**
     * <code>resource</code>
     * {@link io.github.nichetoolkit.ossfile.OssfileResource} <p>The <code>resource</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileResource
     */
    private final OssfileResource resource;

    /**
     * <code>OssfileVideoResource</code>
     * <p>Instantiates a new ossfile video resource.</p>
     * @param inputStream {@link java.io.InputStream} <p>The input stream parameter is <code>InputStream</code> type.</p>
     * @param resource    {@link io.github.nichetoolkit.ossfile.OssfileResource} <p>The resource parameter is <code>OssfileResource</code> type.</p>
     * @see java.io.InputStream
     * @see io.github.nichetoolkit.ossfile.OssfileResource
     */
    public OssfileVideoResource(InputStream inputStream, OssfileResource resource) {
        super(inputStream);
        this.resource = resource;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public boolean isReadable() {
        return this.resource.isReadable();
    }

    @Override
    public boolean isOpen() {
        return this.resource.isOpen();
    }

    @Override
    public abstract long contentLength() throws IOException;

    @Override
    public abstract long lastModified() throws IOException;

    @Override
    public String getFilename() {
        return this.resource.getFilename();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return this == other || other instanceof OssfileResource && this.resource.equals(other);
    }

    @NonNull
    @Override
    public String getDescription() {
        return "Oss file input stream resource [" + super.getDescription() + "]";
    }

}
