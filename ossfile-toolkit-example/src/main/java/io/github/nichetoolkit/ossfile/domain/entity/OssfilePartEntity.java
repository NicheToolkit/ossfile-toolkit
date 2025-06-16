package io.github.nichetoolkit.ossfile.domain.entity;

import io.github.nichetoolkit.mybatis.column.RestFickleEntry;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.mybatis.table.RestExcludes;
import io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@RestEntity(name = "ossfile_part")
@RestExcludes({"updateTime","createTime"})
@EqualsAndHashCode(callSuper = true)
public class OssfilePartEntity extends DefaultIdEntity<OssfilePartEntity, OssfilePartModel,String> {
    @RestLinkKey
    protected OssfilePartLinks links;

    protected String filename;
    protected Integer partIndex;
    protected String partEtag;

    protected String bucket;
    protected String objectKey;
    protected String objectPath;

    protected Long partSize;
    protected String partMd5;

    protected Date partTime;

    @RestFickleEntry
    private List<RestFickle<?>> metas;

    public OssfilePartEntity() {
    }

    public OssfilePartEntity(String id) {
        super(id);
    }


    @Override
    public OssfilePartModel toModel() {
        OssfilePartModel partModel = new OssfilePartModel();
        BeanUtils.copyNonnullProperties(this,partModel);
        Optional.ofNullable(this.links).ifPresent((link) -> {
            partModel.setBulkId(link.getBulkId());
            partModel.setProjectId(link.getProjectId());
            partModel.setUploadId(link.getUploadId());
        });
        return partModel;
    }
}
