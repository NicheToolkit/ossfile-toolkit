package io.github.nichetoolkit.ossfile;

import org.springframework.core.io.InputStreamResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;

public abstract class OssfileVideoResource extends InputStreamResource {
    private final OssfileResource resource;

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
