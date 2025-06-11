package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultIdModel;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.jsonb.Property;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileBulkModel extends DefaultIdModel<OssfileBulkModel, OssfileBulkEntity, String> implements OssfileResource {
    protected RestId<String> user;
    protected RestId<String> project;
    protected String userId;
    protected String projectId;
    protected String uploadId;

    protected String original;
    protected String filename;

    protected String bucket;

    protected String objectKey;
    protected String objectPath;

    protected String previewKey;
    protected String previewPath;

    protected String fileMd5;
    protected Long fileSize;
    protected OssfileFileType fileType;
    protected Integer partSize;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date completeTime;

    protected List<Property> properties;

    protected Boolean finishState;
    protected Boolean compressState;
    protected Boolean partState;
    protected Boolean previewState;

    protected String etag;
    protected String version;

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
        return this.finishState;
    }

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
