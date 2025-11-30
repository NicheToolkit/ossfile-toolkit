package io.github.nichetoolkit.ossfile.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.ossfile.OssfilePartETag;
import io.github.nichetoolkit.ossfile.OssfileResource;
import io.github.nichetoolkit.ossfile.domain.entity.OssfilePartEntity;
import io.github.nichetoolkit.ossfile.domain.entity.OssfilePartLinks;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rest.util.IoStreamUtils;
import io.github.nichetoolkit.rice.DefaultIdModel;
import io.github.nichetoolkit.rice.RestId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.jspecify.annotations.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * <code>OssfilePartModel</code>
 * <p>The ossfile part model class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultIdModel
 * @see java.util.Comparator
 * @see java.lang.Comparable
 * @see io.github.nichetoolkit.ossfile.OssfileResource
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.experimental.SuperBuilder
 * @see lombok.EqualsAndHashCode
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk17
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfilePartModel extends DefaultIdModel<OssfilePartModel, OssfilePartEntity,String> implements Comparator<OssfilePartModel>, Comparable<OssfilePartModel>, OssfileResource {
    /**
     * <code>bulkId</code>
     * {@link java.lang.String} <p>The <code>bulkId</code> field.</p>
     * @see java.lang.String
     */
    protected String bulkId;
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
     * <code>filename</code>
     * {@link java.lang.String} <p>The <code>filename</code> field.</p>
     * @see java.lang.String
     */
    protected String filename;
    /**
     * <code>partIndex</code>
     * {@link java.lang.Integer} <p>The <code>partIndex</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer partIndex;
    /**
     * <code>partEtag</code>
     * {@link java.lang.String} <p>The <code>partEtag</code> field.</p>
     * @see java.lang.String
     */
    protected String partEtag;

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
     * <code>partSize</code>
     * {@link java.lang.Long} <p>The <code>partSize</code> field.</p>
     * @see java.lang.Long
     */
    protected Long partSize;
    /**
     * <code>partMd5</code>
     * {@link java.lang.String} <p>The <code>partMd5</code> field.</p>
     * @see java.lang.String
     */
    protected String partMd5;

    /**
     * <code>partTime</code>
     * {@link java.util.Date} <p>The <code>partTime</code> field.</p>
     * @see java.util.Date
     * @see org.springframework.format.annotation.DateTimeFormat
     * @see com.fasterxml.jackson.annotation.JsonFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date partTime;

    /**
     * <code>metas</code>
     * {@link java.util.List} <p>The <code>metas</code> field.</p>
     * @see java.util.List
     */
    protected List<RestFickle<?>> metas;

    /**
     * <code>bytes</code>
     * <p>The <code>bytes</code> field.</p>
     * @see com.fasterxml.jackson.annotation.JsonIgnore
     */
    @JsonIgnore
    protected byte[] bytes;

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
     * <code>OssfilePartModel</code>
     * <p>Instantiates a new ossfile part model.</p>
     */
    public OssfilePartModel() {
    }

    /**
     * <code>OssfilePartModel</code>
     * <p>Instantiates a new ossfile part model.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssfilePartModel(String id) {
        super(id);
    }

    /**
     * <code>toPartETag</code>
     * <p>The to part e tag method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfilePartETag} <p>The to part e tag return object is <code>OssfilePartETag</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.OssfilePartETag
     */
    public OssfilePartETag toPartETag() {
        return new OssfilePartETag(this.partIndex, this.partEtag);
    }

    /**
     * <code>setBulk</code>
     * <p>The set bulk setter method.</p>
     * @param bulk {@link io.github.nichetoolkit.rice.RestId} <p>The bulk parameter is <code>RestId</code> type.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    public void setBulk(RestId<String> bulk) {
        this.bulkId = bulk.getId();
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
        return OssfilePartLinks.builder().bulkId(this.bulkId).uploadId(this.uploadId).projectId(this.projectId).build();
    }

    /**
     * <code>ofFile</code>
     * <p>The of file method.</p>
     * @param file {@link org.springframework.web.multipart.MultipartFile} <p>The file parameter is <code>MultipartFile</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel} <p>The of file return object is <code>OssfilePartModel</code> type.</p>
     * @see org.springframework.web.multipart.MultipartFile
     */
    public OssfilePartModel ofFile(MultipartFile file) {
        this.partSize = file.getSize();
        byte[] bytes = IoStreamUtils.bytes(file);
        return ofBytes(bytes);
    }

    /**
     * <code>ofBytes</code>
     * <p>The of bytes method.</p>
     * @param bytes byte <p>The bytes parameter is <code>byte</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel} <p>The of bytes return object is <code>OssfilePartModel</code> type.</p>
     */
    public OssfilePartModel ofBytes(byte[] bytes) {
        this.bytes = bytes;
        this.partMd5 = DigestUtils.md2Hex(bytes);
        return this;
    }

    @Override
    public OssfilePartEntity toEntity() {
        OssfilePartEntity partEntity = new OssfilePartEntity();
        BeanUtils.copyNonnullProperties(this, partEntity);
        partEntity.setLinks(OssfilePartLinks.builder()
                .bulkId(this.bulkId)
                .projectId(this.projectId)
                .uploadId(this.uploadId).build());
        return partEntity;
    }

    @Override
    public int compare(OssfilePartModel source, OssfilePartModel target) {
        return Integer.compare(source.getPartIndex(), target.getPartIndex());
    }

    @Override
    public int compareTo(@NonNull OssfilePartModel target) {
        return Integer.compare(this.getPartIndex(), target.getPartIndex());
    }

}
