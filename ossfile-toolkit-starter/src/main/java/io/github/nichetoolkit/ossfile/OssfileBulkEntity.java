package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.mybatis.column.RestAlertKey;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.rice.DefaultIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
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
    protected Integer fileType;
    protected Integer partSize;

    protected String properties;

    protected String etag;
    protected String version;


    public OssfileBulkEntity() {
    }

    public OssfileBulkEntity(String id) {
        super(id);
    }

}
