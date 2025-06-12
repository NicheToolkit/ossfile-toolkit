package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.mybatis.column.RestFickleEntry;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.mybatis.table.RestExcludes;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@RestEntity(name = "ossfile_part")
@RestExcludes({"updateTime","createTime"})
@EqualsAndHashCode(callSuper = true)
public class OssfilePartEntity extends DefaultIdEntity<OssfilePartEntity,OssfilePartModel,String> {
    @RestLinkKey
    protected OssfilePartLinks links;

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
        partModel.setBulkId(this.links.getBulkId());
        partModel.setProjectId(this.links.getProjectId());
        partModel.setUploadId(this.links.getUploadId());
        return partModel;
    }
}
