package io.github.nichetoolkit.ossfile.service.impl;

import io.github.nichetoolkit.ossfile.domain.entity.OssfileBulkEntity;
import io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel;
import io.github.nichetoolkit.ossfile.domain.model.OssfileFilter;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.DefaultIdService;
import org.springframework.stereotype.Service;

@Service
public class OssfileBulkServiceImpl extends DefaultIdService<OssfileBulkModel, OssfileBulkEntity, OssfileFilter, String, String> implements io.github.nichetoolkit.ossfile.service.OssfileBulkService {

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
