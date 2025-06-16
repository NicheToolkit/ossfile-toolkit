package io.github.nichetoolkit.ossfile.mapper;

import io.github.nichetoolkit.mybatis.MybatisDeleteLinkMapper;
import io.github.nichetoolkit.mybatis.MybatisFindLinkMapper;
import io.github.nichetoolkit.mybatis.MybatisSuperMapper;
import io.github.nichetoolkit.mybatis.natives.MybatisFilterFickleMapper;
import io.github.nichetoolkit.mybatis.natives.MybatisFindFickleMapper;
import io.github.nichetoolkit.mybatis.natives.MybatisLinkFickleMapper;
import io.github.nichetoolkit.ossfile.domain.entity.OssfilePartEntity;
import io.github.nichetoolkit.ossfile.domain.entity.OssfilePartLinks;
import org.apache.ibatis.annotations.Mapper;

/**
 * <code>OssfilePartMapper</code>
 * <p>The ossfile part mapper interface.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.mybatis.MybatisSuperMapper
 * @see io.github.nichetoolkit.mybatis.MybatisFindLinkMapper
 * @see io.github.nichetoolkit.mybatis.MybatisDeleteLinkMapper
 * @see io.github.nichetoolkit.mybatis.natives.MybatisFindFickleMapper
 * @see io.github.nichetoolkit.mybatis.natives.MybatisFilterFickleMapper
 * @see io.github.nichetoolkit.mybatis.natives.MybatisLinkFickleMapper
 * @see org.apache.ibatis.annotations.Mapper
 * @since Jdk1.8
 */
@Mapper
public interface OssfilePartMapper extends MybatisSuperMapper<OssfilePartEntity, String>,
        MybatisFindLinkMapper<OssfilePartEntity, OssfilePartLinks, String>,
        MybatisDeleteLinkMapper<OssfilePartEntity, OssfilePartLinks, String>,
        MybatisFindFickleMapper<OssfilePartEntity, String>,
        MybatisFilterFickleMapper<OssfilePartEntity, String>,
        MybatisLinkFickleMapper<OssfilePartEntity, OssfilePartLinks, String> {

}
