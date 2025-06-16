package io.github.nichetoolkit.ossfile.mapper;

import io.github.nichetoolkit.mybatis.MybatisAlertLinkMapper;
import io.github.nichetoolkit.mybatis.MybatisDeleteLinkMapper;
import io.github.nichetoolkit.mybatis.MybatisFindLinkMapper;
import io.github.nichetoolkit.mybatis.MybatisSuperMapper;
import io.github.nichetoolkit.mybatis.natives.MybatisFilterFickleMapper;
import io.github.nichetoolkit.mybatis.natives.MybatisFindFickleMapper;
import io.github.nichetoolkit.mybatis.natives.MybatisLinkFickleMapper;
import io.github.nichetoolkit.ossfile.domain.entity.OssfileBulkEntity;
import io.github.nichetoolkit.ossfile.domain.entity.OssfileBulkLinks;
import io.github.nichetoolkit.ossfile.domain.entity.OssfileStates;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OssfileBulkMapper extends MybatisSuperMapper<OssfileBulkEntity,String>,
        MybatisFindLinkMapper<OssfileBulkEntity, OssfileBulkLinks, String>,
        MybatisAlertLinkMapper<OssfileBulkEntity, OssfileBulkLinks, OssfileStates, String>,
        MybatisDeleteLinkMapper<OssfileBulkEntity, OssfileBulkLinks, String>,
        MybatisFindFickleMapper<OssfileBulkEntity, String>,
        MybatisFilterFickleMapper<OssfileBulkEntity, String>,
        MybatisLinkFickleMapper<OssfileBulkEntity, OssfileBulkLinks, String>{
}
