package io.github.nichetoolkit.ossfile.service.impl;


import io.github.nichetoolkit.ossfile.domain.model.OssfileFilter;
import io.github.nichetoolkit.ossfile.domain.entity.OssfilePartEntity;
import io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel;
import io.github.nichetoolkit.ossfile.service.OssfilePartService;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rice.DefaultIdService;
import org.springframework.stereotype.Service;

@Service
public class OssfilePartServiceImpl extends DefaultIdService<OssfilePartModel, OssfilePartEntity, OssfileFilter, String, String> implements OssfilePartService {

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
