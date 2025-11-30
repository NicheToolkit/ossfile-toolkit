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

/**
 * <code>OssfileBulkMapper</code>
 * <p>The ossfile bulk mapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSuperMapper
 * @see io.github.nichetoolkit.mybatis.MybatisFindLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisAlertLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisDeleteLinkMapper
 * @see io.github.nichetoolkit.mybatis.natives.MybatisFindFickleMapper
 * @see io.github.nichetoolkit.mybatis.natives.MybatisFilterFickleMapper
 * @see io.github.nichetoolkit.mybatis.natives.MybatisLinkFickleMapper
 * @see org.apache.ibatis.annotations.Mapper
 * @since Jdk17
 */
@Mapper
public interface OssfileBulkMapper extends MybatisSuperMapper<OssfileBulkEntity,String>,
        MybatisFindLinkMapper<OssfileBulkEntity, OssfileBulkLinks, String>,
        MybatisAlertLinkMapper<OssfileBulkEntity, OssfileBulkLinks, OssfileStates, String>,
        MybatisDeleteLinkMapper<OssfileBulkEntity, OssfileBulkLinks, String>,
        MybatisFindFickleMapper<OssfileBulkEntity, String>,
        MybatisFilterFickleMapper<OssfileBulkEntity, String>,
        MybatisLinkFickleMapper<OssfileBulkEntity, OssfileBulkLinks, String>{
}
