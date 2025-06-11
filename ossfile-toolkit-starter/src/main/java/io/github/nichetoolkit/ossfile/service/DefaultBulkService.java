package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.*;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.DefaultIdService;

public abstract class DefaultBulkService extends DefaultIdService<OssfileBulkModel, OssfileBulkEntity, OssfileFilter, String, String> implements OssfileBulkService {

    @Override
    public String queryWhereSql(OssfileFilter filter) throws RestException {
        return filter.toBucketSql("bucket")
                .toFileTypeSql("file_type")
                .toUserIdSql("user_id")
                .toProjectIdSql("project_id")
                .toUploadIdSql("upload_id")
                .toPartStateSql("part_state")
                .toFinishStateSql("finish_state")
                .toCompressStateSql("compress_state")
                .toPreviewStateSql("preview_state")
                .toNameSql("filename")
                .toTimeSql("create_time")
                .toIdSql().addSorts("update_time").toSql();
    }

    @Override
    protected boolean isIdentityOfInvade() {
        return true;
    }
}
