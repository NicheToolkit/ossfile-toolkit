package io.github.nichetoolkit.ossfile.mapper;

import io.github.nichetoolkit.mybatis.MybatisDeleteLinkMapper;
import io.github.nichetoolkit.mybatis.MybatisFindLinkMapper;
import io.github.nichetoolkit.mybatis.MybatisSuperMapper;
import io.github.nichetoolkit.ossfile.OssfilePartEntity;
import io.github.nichetoolkit.ossfile.OssfilePartLinks;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OssfilePartMapper extends MybatisSuperMapper<OssfilePartEntity, String>,
        MybatisFindLinkMapper<OssfilePartEntity, OssfilePartLinks, String>,
        MybatisDeleteLinkMapper<OssfilePartEntity, OssfilePartLinks, String> {

}
