package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.net.httpserver.Headers;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultInfoModel;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.jsonb.Property;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class OssfileBulkModel<M extends OssfileBulkModel<M, E>, E extends OssfileBulkEntity<E,M>> extends DefaultInfoModel<M, E, String> implements OssfileResource {
    protected RestId<String> user;
    protected RestId<String> project;
    protected String original;
    protected String filename;
    protected String alias;
    protected String suffix;

    protected String fileMd5;
    protected Long fileSize;
    protected OssfileFileType fileType;
    protected List<Property> properties;

    protected Boolean finish;
    protected Boolean compress;
    protected Boolean part;
    protected Integer parteSize;
    protected Boolean merge;

    protected String etag;
    protected String version;
    protected Map<String, List<String>> headers;

    protected List<OssfilePartModel<?,?>> fileParts;

    protected OssfilePartModel<?,?> filePart;

    @JsonIgnore
    protected byte[] bytes;

    @JsonIgnore
    protected File file;

    @Override
    public String getObjectKey() {
        return this.id;
    }

    public OssfileBulkModel() {
    }

    public OssfileBulkModel(String id) {
        super(id);
    }

    @Override
    public boolean isReadable() {
        return this.finish;
    }

    abstract public OssfileBulkModel<?,?> ofRequest(OssfileRequest request);

    @JsonIgnore
    public InputStream inputStream() {
        if (GeneralUtils.isNotEmpty(this.bytes)) {
            return new ByteArrayInputStream(this.bytes);
        } else if (GeneralUtils.isNotEmpty(this.file)) {
            try {
                return Files.newInputStream(this.file.toPath());
            } catch (IOException ignored) {
            }
        }
        return null;
    }

    public void setAlias(String alias) {
        this.alias = alias;
        addProperty("alias", alias);
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
        addProperty("suffix", suffix);
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        addProperty("md5", fileMd5);
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
        addProperty("size", fileSize);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        addProperty("name", name);
    }

    @JsonIgnore
    public void headers(Headers headers) {
        this.headers = headers;
    }

    public void addProperty(@NonNull String name, Object value) {
        if (GeneralUtils.isNotEmpty(value)) {
            if (GeneralUtils.isEmpty(this.properties)) {
                this.properties = new ArrayList<>();
            } else {
                this.properties.remove(new Property(name));
            }
            this.properties.add(new Property(name, value));
        }
    }

}
