package io.github.nichetoolkit.ossfile.domain.entity;

import io.github.nichetoolkit.mybatis.column.RestAlertKey;
import io.github.nichetoolkit.mybatis.column.RestFickleEntry;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        BeanUtils.copyNonnullProperties(this, bulkModel);
        bulkModel.setFileType(RestKey.of(this.fileType));
        Optional.ofNullable(this.links).ifPresent((link) -> {
            bulkModel.setUserId(link.getUserId());
            bulkModel.setProjectId(link.getProjectId());
            bulkModel.setUploadId(link.getUploadId());
        });
        Optional.ofNullable(this.states).ifPresent((state) -> {
            bulkModel.setFinishState(state.getFinishState());
            bulkModel.setSignatureState(state.getSignatureState());
            bulkModel.setCompressState(state.getCompressState());
            bulkModel.setPartState(state.getPartState());
            bulkModel.setPreviewState(state.getPreviewState());
        });
        return bulkModel;
    }

}
