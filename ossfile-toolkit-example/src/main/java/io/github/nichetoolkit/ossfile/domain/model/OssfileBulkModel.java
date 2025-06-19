package io.github.nichetoolkit.ossfile.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.ossfile.domain.OssfileFileType;
import io.github.nichetoolkit.ossfile.domain.entity.OssfileBulkEntity;
import io.github.nichetoolkit.ossfile.domain.entity.OssfileBulkLinks;
import io.github.nichetoolkit.ossfile.domain.entity.OssfilePartLinks;
import io.github.nichetoolkit.ossfile.domain.entity.OssfileStates;
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
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <code>OssfileBulkModel</code>
 * <p>The ossfile bulk model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultIdModel
 * @see io.github.nichetoolkit.ossfile.OssfileResource
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.experimental.SuperBuilder
 * @see lombok.EqualsAndHashCode
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk1.8
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileBulkModel extends DefaultIdModel<OssfileBulkModel, OssfileBulkEntity, String> implements OssfileResource {
    /**
     * <code>userId</code>
     * {@link java.lang.String} <p>The <code>userId</code> field.</p>
     * @see java.lang.String
     */
    protected String userId;
    /**
     * <code>projectId</code>
     * {@link java.lang.String} <p>The <code>projectId</code> field.</p>
     * @see java.lang.String
     */
    protected String projectId;
    /**
     * <code>uploadId</code>
     * {@link java.lang.String} <p>The <code>uploadId</code> field.</p>
     * @see java.lang.String
     */
    protected String uploadId;

    /**
     * <code>original</code>
     * {@link java.lang.String} <p>The <code>original</code> field.</p>
     * @see java.lang.String
     */
    protected String original;
    /**
     * <code>filename</code>
     * {@link java.lang.String} <p>The <code>filename</code> field.</p>
     * @see java.lang.String
     */
    protected String filename;

    /**
     * <code>bucket</code>
     * {@link java.lang.String} <p>The <code>bucket</code> field.</p>
     * @see java.lang.String
     */
    protected String bucket;

    /**
     * <code>objectKey</code>
     * {@link java.lang.String} <p>The <code>objectKey</code> field.</p>
     * @see java.lang.String
     */
    protected String objectKey;
    /**
     * <code>objectPath</code>
     * {@link java.lang.String} <p>The <code>objectPath</code> field.</p>
     * @see java.lang.String
     */
    protected String objectPath;

    /**
     * <code>previewKey</code>
     * {@link java.lang.String} <p>The <code>previewKey</code> field.</p>
     * @see java.lang.String
     */
    protected String previewKey;
    /**
     * <code>previewPath</code>
     * {@link java.lang.String} <p>The <code>previewPath</code> field.</p>
     * @see java.lang.String
     */
    protected String previewPath;

    /**
     * <code>fileMd5</code>
     * {@link java.lang.String} <p>The <code>fileMd5</code> field.</p>
     * @see java.lang.String
     */
    protected String fileMd5;
    /**
     * <code>fileSize</code>
     * {@link java.lang.Long} <p>The <code>fileSize</code> field.</p>
     * @see java.lang.Long
     */
    protected Long fileSize;
    /**
     * <code>fileType</code>
     * {@link io.github.nichetoolkit.rest.RestKey} <p>The <code>fileType</code> field.</p>
     * @see io.github.nichetoolkit.rest.RestKey
     * @see com.fasterxml.jackson.databind.annotation.JsonSerialize
     */
    @JsonSerialize(using = RestKeySerializer.class)
    protected RestKey<String> fileType;
    /**
     * <code>partSize</code>
     * {@link java.lang.Long} <p>The <code>partSize</code> field.</p>
     * @see java.lang.Long
     */
    protected Long partSize;

    /**
     * <code>beginTime</code>
     * {@link java.util.Date} <p>The <code>beginTime</code> field.</p>
     * @see java.util.Date
     * @see org.springframework.format.annotation.DateTimeFormat
     * @see com.fasterxml.jackson.annotation.JsonFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date beginTime;
    /**
     * <code>completeTime</code>
     * {@link java.util.Date} <p>The <code>completeTime</code> field.</p>
     * @see java.util.Date
     * @see org.springframework.format.annotation.DateTimeFormat
     * @see com.fasterxml.jackson.annotation.JsonFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date completeTime;

    /**
     * <code>properties</code>
     * {@link java.util.List} <p>The <code>properties</code> field.</p>
     * @see java.util.List
     */
    protected List<Property> properties;

    /**
     * <code>finishState</code>
     * {@link java.lang.Boolean} <p>The <code>finishState</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean finishState;
    /**
     * <code>signatureState</code>
     * {@link java.lang.Boolean} <p>The <code>signatureState</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean signatureState;
    /**
     * <code>compressState</code>
     * {@link java.lang.Boolean} <p>The <code>compressState</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean compressState;
    /**
     * <code>partState</code>
     * {@link java.lang.Boolean} <p>The <code>partState</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean partState;
    /**
     * <code>previewState</code>
     * {@link java.lang.Boolean} <p>The <code>previewState</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean previewState;

    /**
     * <code>etag</code>
     * {@link java.lang.String} <p>The <code>etag</code> field.</p>
     * @see java.lang.String
     */
    protected String etag;
    /**
     * <code>version</code>
     * {@link java.lang.String} <p>The <code>version</code> field.</p>
     * @see java.lang.String
     */
    protected String version;

    /**
     * <code>metas</code>
     * {@link java.util.List} <p>The <code>metas</code> field.</p>
     * @see java.util.List
     */
    protected List<RestFickle<?>> metas;

    /**
     * <code>parts</code>
     * {@link java.util.List} <p>The <code>parts</code> field.</p>
     * @see java.util.List
     */
    protected List<OssfilePartModel> parts;

    /**
     * <code>bytes</code>
     * <p>The <code>bytes</code> field.</p>
     * @see com.fasterxml.jackson.annotation.JsonIgnore
     */
    @JsonIgnore
    protected byte[] bytes;

    /**
     * <code>OssfileBulkModel</code>
     * <p>Instantiates a new ossfile bulk model.</p>
     */
    public OssfileBulkModel() {
    }

    /**
     * <code>OssfileBulkModel</code>
     * <p>Instantiates a new ossfile bulk model.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
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

    /**
     * <code>etagVersion</code>
     * <p>The etag version method.</p>
     * @param eTagVersion {@link io.github.nichetoolkit.ossfile.OssfileETagVersion} <p>The e tag version parameter is <code>OssfileETagVersion</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfileETagVersion
     */
    public void etagVersion(OssfileETagVersion eTagVersion) {
        this.etag = eTagVersion.getEtag();
        this.version = eTagVersion.getVersion();
    }

    /**
     * <code>setUser</code>
     * <p>The set user setter method.</p>
     * @param user {@link io.github.nichetoolkit.rice.RestId} <p>The user parameter is <code>RestId</code> type.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    public void setUser(RestId<String> user) {
        this.userId = user.getId();
    }

    /**
     * <code>setProject</code>
     * <p>The set project setter method.</p>
     * @param project {@link io.github.nichetoolkit.rice.RestId} <p>The project parameter is <code>RestId</code> type.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    public void setProject(RestId<String> project) {
        this.projectId = project.getId();
    }

    /**
     * <code>toPartLinks</code>
     * <p>The to part links method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.entity.OssfilePartLinks} <p>The to part links return object is <code>OssfilePartLinks</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.entity.OssfilePartLinks
     */
    public OssfilePartLinks toPartLinks() {
        return OssfilePartLinks.builder().bulkId(this.id).uploadId(this.uploadId).projectId(this.projectId).build();
    }

    /**
     * <code>toPartModel</code>
     * <p>The to part model method.</p>
     * @param partIndex int <p>The part index parameter is <code>int</code> type.</p>
     * @param partSize  long <p>The part size parameter is <code>long</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel} <p>The to part model return object is <code>OssfilePartModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel
     * @see io.github.nichetoolkit.rest.RestException
     */
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
        partModel.setBucket(this.bucket);
        partModel.setObjectKey(this.objectKey);
        partModel.setObjectPath(this.objectPath);
        return partModel;
    }

    /**
     * <code>ofFile</code>
     * <p>The of file method.</p>
     * @param file {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The of file return object is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see org.springframework.web.multipart.MultipartFile
     * @see io.github.nichetoolkit.rest.RestException
     */
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

    /**
     * <code>parseFileType</code>
     * <p>The parse file type method.</p>
     * @return {@link io.github.nichetoolkit.rest.RestKey} <p>The parse file type return object is <code>RestKey</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestKey
     */
    public RestKey<String> parseFileType() {
        String suffix = FileUtils.suffix(this.original);
        return OssfileFileType.parseSuffix(suffix);
    }

    /**
     * <code>ofComplete</code>
     * <p>The of complete method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The of complete return object is <code>OssfileBulkModel</code> type.</p>
     */
    public OssfileBulkModel ofComplete() {
        OssfileBulkModel complete = new OssfileBulkModel(this.id);
        complete.setCompleteTime(new Date());
        complete.setCompressState(true);
        return complete;
    }

    /**
     * <code>ofBytes</code>
     * <p>The of bytes method.</p>
     * @param bytes byte <p>The bytes parameter is <code>byte</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The of bytes return object is <code>OssfileBulkModel</code> type.</p>
     */
    public OssfileBulkModel ofBytes(byte[] bytes) {
        this.bytes = bytes;
        this.fileMd5 = DigestUtils.md2Hex(bytes);
        this.fileSize = (long) bytes.length;
        return this;
    }

    /**
     * <code>ofStartPartBuilder</code>
     * <p>The of start part builder method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
    public void ofStartPartBuilder() throws RestException {
        this.partState = true;
        this.original = this.filename;
        RestOptional.ofEmptyable(this.fileType).ofEmpty(() -> {
            this.fileType = this.parseFileType();
        });
        ofDefaultBuilder();
    }

    /**
     * <code>ofDefaultBuilder</code>
     * <p>The of default builder method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The of default builder return object is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
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

    /**
     * <code>ofPreviewBuilder</code>
     * <p>The of preview builder method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
    public void ofPreviewBuilder() throws RestException {
        ofDefaultBuilder().ofPreviewPath().ofPreviewKey();
        this.previewState = true;
    }

    /**
     * <code>ofObjectCopyBuilder</code>
     * <p>The of object copy builder method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
    public void ofObjectCopyBuilder() throws RestException {
        ofDefaultBuilder().ofObjectCopy();
        this.previewState = true;
    }

    /**
     * <code>ofBucket</code>
     * <p>The of bucket method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The of bucket return object is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
    public OssfileBulkModel ofBucket() throws RestException {
        RestOptional.ofEmptyable(this.bucket).ofEmpty(() -> this.bucket = OssfileStoreHolder.defaultBucket());
        return this;
    }

    /**
     * <code>ofObjectPath</code>
     * <p>The of object path method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The of object path return object is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
    public OssfileBulkModel ofObjectPath() throws RestException {
        assert GeneralUtils.isNotEmpty(this.filename);
        RestOptional.ofEmptyable(this.objectPath).ofEmpty(() -> {
            String bulkPrefix = OssfileStoreHolder.bulkPrefix();
            if (GeneralUtils.isNotEmpty(this.projectId)) {
                this.objectPath = bulkPrefix + OssfileConstants.FILE_SEPARATOR + this.projectId + OssfileConstants.FILE_SEPARATOR + GeneralUtils.uuid();
            } else {
                this.objectPath = bulkPrefix + OssfileConstants.FILE_SEPARATOR + GeneralUtils.uuid();
            }
        });
        return this;
    }

    /**
     * <code>ofObjectKey</code>
     * <p>The of object key method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
    public void ofObjectKey() throws RestException {
        assert GeneralUtils.isNotEmpty(this.objectPath);
        RestOptional.ofEmptyable(this.objectKey).ofEmpty(() -> {
            this.objectKey = this.objectPath + OssfileConstants.FILE_SEPARATOR + this.filename;
        });
    }

    /**
     * <code>ofPreviewPath</code>
     * <p>The of preview path method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The of preview path return object is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
    public OssfileBulkModel ofPreviewPath() throws RestException {
        assert GeneralUtils.isNotEmpty(this.filename);
        RestOptional.ofEmptyable(this.previewPath).ofEmpty(() -> {
            String previewPrefix = OssfileStoreHolder.previewPrefix();
            if (GeneralUtils.isNotEmpty(this.projectId)) {
                this.previewPath = previewPrefix + OssfileConstants.FILE_SEPARATOR + this.projectId + OssfileConstants.FILE_SEPARATOR + GeneralUtils.uuid();
            } else {
                this.previewPath = previewPrefix + OssfileConstants.FILE_SEPARATOR + GeneralUtils.uuid();
            }
        });
        return this;
    }

    /**
     * <code>ofPreviewKey</code>
     * <p>The of preview key method.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.rest.RestException
     */
    public void ofPreviewKey() throws RestException {
        assert GeneralUtils.isNotEmpty(this.previewPath);
        RestOptional.ofEmptyable(this.previewKey).ofEmpty(() -> {
            this.previewKey =  this.previewPath + OssfileConstants.FILE_SEPARATOR + this.filename;
        });
    }

    /**
     * <code>ofObjectCopy</code>
     * <p>The of object copy method.</p>
     */
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

    /**
     * <code>inputStream</code>
     * <p>The input stream method.</p>
     * @return {@link java.io.InputStream} <p>The input stream return object is <code>InputStream</code> type.</p>
     * @see java.io.InputStream
     * @see com.fasterxml.jackson.annotation.JsonIgnore
     */
    @JsonIgnore
    public InputStream inputStream() {
        if (GeneralUtils.isNotEmpty(this.bytes)) {
            return new ByteArrayInputStream(this.bytes);
        }
        return null;
    }

    /**
     * <code>addProperty</code>
     * <p>The add property method.</p>
     * @param name  {@link java.lang.String} <p>The name parameter is <code>String</code> type.</p>
     * @param value {@link java.lang.Object} <p>The value parameter is <code>Object</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.NonNull
     * @see java.lang.Object
     */
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
