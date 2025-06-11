package io.github.nichetoolkit.ossfile.service;


import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.DefaultIdService;

public abstract class DefaultPartService extends DefaultIdService<OssfilePartModel, OssfilePartEntity, OssfileFilter, String, String> implements OssfilePartService{

    @Override
    public String queryWhereSql(OssfileFilter filter) throws RestException {
        return filter.toBucketSql("bucket")
                .toFileTypeSql("file_type")
                .toUserIdSql("bulk_id")
                .toProjectIdSql("project_id")
                .toUploadIdSql("upload_id")
                .toNameSql("filename")
                .toTimeSql("part_time")
                .toIdSql().addSorts("file_index").toSql();
    }

}
