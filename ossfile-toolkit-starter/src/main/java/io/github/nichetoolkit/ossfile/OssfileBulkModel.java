package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.identity.IdentityUtils;
import io.github.nichetoolkit.rest.util.FileUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.IoStreamUtils;
import io.github.nichetoolkit.rice.DefaultIdModel;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.jsonb.Property;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

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
    protected Long partSize;

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

    public void setETagVersion(OssfileETagVersion eTagVersion) {
        this.etag = eTagVersion.getEtag();
        this.version = eTagVersion.getVersion();
    }

    public void setUser(RestId<String> user) {
        this.userId = user.getId();
    }

    public void setProject(RestId<String> project) {
        this.projectId = project.getId();
    }

    public OssfileBulkModel ofFile(MultipartFile file) {
        RestOptional.ofEmptyable(this.original).orElseGet(() -> this.original = file.getOriginalFilename());
        RestOptional.ofEmptyable(this.filename).orElseGet(() -> this.filename = file.getName());
        RestOptional.ofEmptyable(this.fileSize).orElseGet(() -> this.fileSize = file.getSize());
        RestOptional.ofEmptyable(this.fileType).orElseGet(() -> {
            String suffix = FileUtils.suffix(this.original);
            return this.fileType = OssfileFileType.parseSuffix(suffix);
        });
        byte[] bytes = IoStreamUtils.bytes(file);
        return ofBytes(bytes);
    }

    public OssfileBulkModel ofComplete() {
        OssfileBulkModel complete = new OssfileBulkModel(this.id);
        complete.setCompleteTime(new Date());
        complete.setCompressState(true);
        return complete;
    }

    public OssfileBulkModel ofBytes(byte[] bytes) {
        this.bytes = bytes;
        this.fileMd5 = DigestUtils.md2Hex(bytes);
        this.fileSize = (long) bytes.length;
        return this;
    }

    public OssfileBulkModel defaultAssert() {
        RestOptional.ofEmptyable(this.id).orElseGet(() -> this.id = IdentityUtils.valueOfString());
        assert GeneralUtils.isNotEmpty(this.original);
        assert GeneralUtils.isNotEmpty(this.filename);
        RestOptional.ofEmptyable(this.beginTime).orElseGet(() -> this.beginTime = new Date());
        RestOptional.ofEmptyable(this.finishState).orElseGet(() -> {
            this.completeTime = new Date();
           return this.finishState = true;
        });
        RestOptional.ofEmptyable(this.compressState).orElseGet(() -> this.compressState = false);
        RestOptional.ofEmptyable(this.previewState).orElseGet(() -> this.previewState = false);
        assert GeneralUtils.isNotEmpty(this.fileSize);
        RestOptional.ofEmptyable(this.partState).orElseGet(() -> {
            this.partSize = this.fileSize;
            return this.partState = false;
        });
        ofBucket().ofObjectPath().ofObjectKey();
        return this;
    }

    public void previewAssert() {
        defaultAssert().ofPreviewPath().ofPreviewKey();
        this.previewState = true;
    }

    public void copyAssert() {
        defaultAssert().previewOfCopy();
        this.previewState = true;
    }

    public OssfileBulkModel ofBucket() {
        this.bucket = RestOptional.ofEmptyable(this.bucket).orElse(OssfileStoreHolder.defaultBucket());
        return this;
    }

    public OssfileBulkModel ofObjectPath() {
        assert GeneralUtils.isNotEmpty(this.filename);
        this.objectPath = RestOptional.ofEmptyable(this.objectPath).orElseGet(() -> {
            String bulkPrefix = OssfileStoreHolder.bulkPrefix();
            if (GeneralUtils.isNotEmpty(this.projectId)) {
                return bulkPrefix + File.separator + this.projectId + File.separator + this.filename;
            } else {
                return bulkPrefix + File.separator + this.filename;
            }
        });
        return this;
    }

    public OssfileBulkModel ofPreviewPath() {
        assert GeneralUtils.isNotEmpty(this.filename);
        this.previewPath = RestOptional.ofEmptyable(this.previewPath).orElseGet(() -> {
            String previewPrefix = OssfileStoreHolder.previewPrefix();
            if (GeneralUtils.isNotEmpty(this.projectId)) {
                return previewPrefix + File.separator + this.projectId + File.separator + this.filename;
            } else {
                return previewPrefix + File.separator + this.filename;
            }
        });
        return this;
    }

    public OssfileBulkModel ofObjectKey() {
        assert GeneralUtils.isNotEmpty(this.objectPath);
        this.objectKey = RestOptional.ofEmptyable(this.objectKey).orElseGet(() -> this.objectPath + File.separator + GeneralUtils.uuid());
        return this;
    }

    public OssfileBulkModel ofPreviewKey() {
        assert GeneralUtils.isNotEmpty(this.previewPath);
        this.previewKey = RestOptional.ofEmptyable(this.previewKey).orElseGet(() -> this.previewPath + File.separator + GeneralUtils.uuid());
        return this;
    }

        public OssfileBulkModel previewOfCopy() {
        assert GeneralUtils.isNotEmpty(this.objectPath);
        assert GeneralUtils.isNotEmpty(this.objectKey);
        this.previewPath = objectPath;
        this.previewKey = objectKey;
        return this;
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
