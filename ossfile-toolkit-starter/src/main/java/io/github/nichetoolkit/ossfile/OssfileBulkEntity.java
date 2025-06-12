package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.mybatis.column.RestAlertKey;
import io.github.nichetoolkit.mybatis.column.RestFickleEntry;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@RestEntity(name = "ossfile_bulk")
@EqualsAndHashCode(callSuper = true)
public class OssfileBulkEntity extends DefaultIdEntity<OssfileBulkEntity, OssfileBulkModel, String> {
    @RestLinkKey
    protected OssfileBulkLinks links;
    @RestAlertKey
    protected OssfileStates states;

    protected String original;
    protected String filename;

    protected String bucket;

    protected String objectKey;
    protected String objectPath;

    protected String previewKey;
    protected String previewPath;

    protected Date beginTime;
    protected Date completeTime;

    protected String fileMd5;
    protected Long fileSize;
    protected String fileType;
    protected Long partSize;

    protected String properties;

    protected String etag;
    protected String version;

    @RestFickleEntry
    private List<RestFickle<?>> metas;


    public OssfileBulkEntity() {
    }

    public OssfileBulkEntity(String id) {
        super(id);
    }

    @Override
    public OssfileBulkModel toModel() {
        OssfileBulkModel bulkModel = new OssfileBulkModel();
        BeanUtils.copyNonnullProperties(this,bulkModel);
        bulkModel.setFileType(RestKey.of(this.fileType));
        bulkModel.setUserId(this.links.getUserId());
        bulkModel.setProjectId(this.links.getProjectId());
        bulkModel.setUploadId(this.links.getUploadId());
        bulkModel.setFinishState(this.states.getFinishState());
        bulkModel.setCompressState(this.states.getCompressState());
        bulkModel.setPartState(this.states.getPartState());
        bulkModel.setPreviewState(this.states.getPreviewState());
        return bulkModel;
    }

}
