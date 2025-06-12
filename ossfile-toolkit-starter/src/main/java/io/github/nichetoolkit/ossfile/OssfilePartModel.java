package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultIdModel;
import io.github.nichetoolkit.rice.RestId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.io.ByteArrayInputStream;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfilePartModel extends DefaultIdModel<OssfilePartModel,OssfilePartEntity,String> implements Comparator<OssfilePartModel>, Comparable<OssfilePartModel>, OssfileResource {
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
    protected Long partStart;
    protected Long partEnd;
    protected String partMd5;
    protected Boolean lastPart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date partTime;

    protected List<RestFickle<?>> metas;

    @JsonIgnore
    protected byte[] bytes;

    @JsonIgnore
    public ByteArrayInputStream inputStream() {
        return new ByteArrayInputStream(this.bytes);
    }

    public OssfilePartModel() {
    }

    public OssfilePartModel(String id) {
        super(id);
    }
    public void setBulk(RestId<String> bulk) {
        this.bulkId = bulk.getId();
    }

    public void setProject(RestId<String> project) {
        this.projectId = project.getId();
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
