package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.mybatis.column.RestFickleEntry;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.rice.DefaultIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
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

}
