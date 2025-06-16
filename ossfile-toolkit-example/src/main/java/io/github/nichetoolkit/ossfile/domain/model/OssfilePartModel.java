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
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfilePartModel extends DefaultIdModel<OssfilePartModel, OssfilePartEntity,String> implements Comparator<OssfilePartModel>, Comparable<OssfilePartModel>, OssfileResource {
    protected String bulkId;
    protected String projectId;
    protected String uploadId;

    protected String filename;
    protected Integer partIndex;
    protected String partEtag;

    protected String bucket;
    protected String objectKey;
    protected String objectPath;

    protected Long partSize;
    protected String partMd5;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date partTime;

    protected List<RestFickle<?>> metas;

    @JsonIgnore
    protected byte[] bytes;

    @JsonIgnore
    public InputStream inputStream() {
        if (GeneralUtils.isNotEmpty(this.bytes)) {
            return new ByteArrayInputStream(this.bytes);
        }
        return null;
    }

    public OssfilePartModel() {
    }

    public OssfilePartModel(String id) {
        super(id);
    }

    public OssfilePartETag toPartETag() {
        return new OssfilePartETag(this.partIndex, this.partEtag);
    }
    public void setBulk(RestId<String> bulk) {
        this.bulkId = bulk.getId();
    }

    public void setProject(RestId<String> project) {
        this.projectId = project.getId();
    }

    public OssfilePartLinks toPartLinks() {
        return OssfilePartLinks.builder().bulkId(this.bulkId).uploadId(this.uploadId).projectId(this.projectId).build();
    }

    public OssfilePartModel ofFile(MultipartFile file) {
        this.partSize = file.getSize();
        byte[] bytes = IoStreamUtils.bytes(file);
        return ofBytes(bytes);
    }

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
