package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.constant.UtilConstants;
import io.github.nichetoolkit.rest.identity.IdentityUtils;
import io.github.nichetoolkit.rest.serialize.RestKeySerializer;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rest.util.FileUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.IoStreamUtils;
import io.github.nichetoolkit.rice.DefaultIdModel;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.jsonb.Property;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
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
    @JsonSerialize(using = RestKeySerializer.class)
    protected RestKey<String> fileType;
    protected Long partSize;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date completeTime;

    protected List<Property> properties;

    protected Boolean finishState;
    protected Boolean signatureState;
    protected Boolean compressState;
    protected Boolean partState;
    protected Boolean previewState;

    protected String etag;
    protected String version;

    protected List<RestFickle<?>> metas;

    protected List<OssfilePartModel> parts;

    @JsonIgnore
    protected byte[] bytes;

    public OssfileBulkModel() {
    }

    public OssfileBulkModel(String id) {
        super(id);
    }

    @Override
    public OssfileBulkEntity toEntity() {
        OssfileBulkEntity bulkEntity = new OssfileBulkEntity();
        BeanUtils.copyNonnullProperties(this, bulkEntity);
        bulkEntity.setFileType(this.fileType.getKey());
        bulkEntity.setLinks(OssfileBulkLinks.builder()
                .userId(this.userId)
                .projectId(this.projectId)
                .uploadId(this.uploadId).build());
        bulkEntity.setStates(OssfileStates.builder()
                .partState(this.partState)
                .finishState(this.finishState)
                .signatureState(this.signatureState)
                .previewState(this.previewState)
                .compressState(this.compressState)
                .build());
        return bulkEntity;
    }

    public void etagVersion(OssfileETagVersion eTagVersion) {
        this.etag = eTagVersion.getEtag();
        this.version = eTagVersion.getVersion();
    }

    public void setUser(RestId<String> user) {
        this.userId = user.getId();
    }

    public void setProject(RestId<String> project) {
        this.projectId = project.getId();
    }

    public OssfilePartLinks toPartLinks() {
        return OssfilePartLinks.builder().bulkId(this.id).uploadId(this.uploadId).projectId(this.projectId).build();
    }

    public OssfilePartModel toPartModel(int partIndex, long partSize) throws RestException {
        OssfilePartModel partModel = new OssfilePartModel();
        partModel.setBulkId(this.id);
        partModel.setProjectId(this.projectId);
        partModel.setUploadId(this.uploadId);
        partModel.setPartIndex(partIndex);
        RestOptional.ofValidable(partSize).isValid(partModel::setPartSize).ofEmpty(() -> {
            partModel.setPartSize(this.partSize);
        });
        partModel.setFilename(this.filename + "_" + partIndex);
        partModel.setPartTime(new Date());
        return partModel;
    }

    public OssfileBulkModel ofFile(MultipartFile file) throws RestException {
        RestOptional.ofEmptyable(this.original).ofEmpty(() -> this.original = file.getOriginalFilename());
        RestOptional.ofEmptyable(this.filename).ofEmpty(() -> this.filename = file.getResource().getFilename());
        RestOptional.ofEmptyable(this.fileSize).ofEmpty(() -> this.fileSize = file.getSize());
        RestOptional.ofEmptyable(this.fileType).ofEmpty(() -> {
            this.fileType = this.parseFileType();
        });
        byte[] bytes = IoStreamUtils.bytes(file);
        return ofBytes(bytes);
    }

    public RestKey<String> parseFileType() {
        String suffix = FileUtils.suffix(this.original);
        return OssfileFileType.parseSuffix(suffix);
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

    public void ofStartPartBuilder() throws RestException {
        this.partState = true;
        this.original = this.filename;
        RestOptional.ofEmptyable(this.fileType).ofEmpty(() -> {
            this.fileType = this.parseFileType();
        });
        ofDefaultBuilder();
    }

    public OssfileBulkModel ofDefaultBuilder() throws RestException {
        RestOptional.ofEmptyable(this.id).ofEmpty(() -> this.id = IdentityUtils.valueOfString());
        assert GeneralUtils.isNotEmpty(this.original);
        assert GeneralUtils.isNotEmpty(this.filename);
        RestOptional.ofEmptyable(this.beginTime).ofEmpty(() -> this.beginTime = new Date());
        RestOptional.ofEmptyable(this.signatureState).isNotEmpty(state -> {
            if (state) {
                String suffix = FileUtils.suffix(this.original);
                this.filename = this.original.replaceAll(suffix, UtilConstants.PNG_IMAGE_SUFFIX);
            }
        }).ofEmpty(() -> this.signatureState = false);
        RestOptional.ofEmptyable(this.compressState).ofEmpty(() -> this.compressState = false);
        RestOptional.ofEmptyable(this.previewState).ofEmpty(() -> this.previewState = false);
        assert GeneralUtils.isNotEmpty(this.fileSize);
        RestOptional.ofEmptyable(this.partState).isNotEmpty(part -> {
            if (part) {
                this.finishState = false;
            } else {
                this.completeTime = new Date();
                this.finishState = true;
            }
        }).ofEmpty(() -> {
            this.partSize = this.fileSize;
            this.partState = false;
            this.completeTime = new Date();
            this.finishState = true;
        });
        ofBucket().ofObjectPath().ofObjectKey();
        return this;
    }

    public void ofPreviewBuilder() throws RestException {
        ofDefaultBuilder().ofPreviewPath().ofPreviewKey();
        this.previewState = true;
    }

    public void ofObjectCopyBuilder() throws RestException {
        ofDefaultBuilder().ofObjectCopy();
        this.previewState = true;
    }

    public OssfileBulkModel ofBucket() throws RestException {
        RestOptional.ofEmptyable(this.bucket).ofEmpty(() -> this.bucket = OssfileStoreHolder.defaultBucket());
        return this;
    }

    public OssfileBulkModel ofObjectPath() throws RestException {
        assert GeneralUtils.isNotEmpty(this.filename);
        RestOptional.ofEmptyable(this.objectPath).ofEmpty(() -> {
            String bulkPrefix = OssfileStoreHolder.bulkPrefix();
            if (GeneralUtils.isNotEmpty(this.projectId)) {
                this.objectPath = bulkPrefix + File.separator + this.projectId + File.separator + GeneralUtils.uuid();
            } else {
                this.objectPath = bulkPrefix + File.separator + GeneralUtils.uuid();
            }
        });
        return this;
    }

    public void ofObjectKey() throws RestException {
        assert GeneralUtils.isNotEmpty(this.objectPath);
        RestOptional.ofEmptyable(this.objectKey).ofEmpty(() -> {
            this.objectKey = this.objectPath + File.separator + this.filename;
        });
    }

    public OssfileBulkModel ofPreviewPath() throws RestException {
        assert GeneralUtils.isNotEmpty(this.filename);
        RestOptional.ofEmptyable(this.previewPath).ofEmpty(() -> {
            String previewPrefix = OssfileStoreHolder.previewPrefix();
            if (GeneralUtils.isNotEmpty(this.projectId)) {
                this.previewPath = previewPrefix + File.separator + this.projectId + File.separator + GeneralUtils.uuid();
            } else {
                this.previewPath = previewPrefix + File.separator + GeneralUtils.uuid();
            }
        });
        return this;
    }

    public void ofPreviewKey() throws RestException {
        assert GeneralUtils.isNotEmpty(this.previewPath);
        RestOptional.ofEmptyable(this.previewKey).ofEmpty(() -> {
            this.previewKey =  this.previewPath + File.separator + this.filename;
        });
    }

    public void ofObjectCopy() {
        assert GeneralUtils.isNotEmpty(this.objectPath);
        assert GeneralUtils.isNotEmpty(this.objectKey);
        this.previewPath = objectPath;
        this.previewKey = objectKey;
    }

    @Override
    public boolean isReadable() {
        return this.finishState;
    }

    @JsonIgnore
    public InputStream inputStream() {
        if (GeneralUtils.isNotEmpty(this.bytes)) {
            return new ByteArrayInputStream(this.bytes);
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
