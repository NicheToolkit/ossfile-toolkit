package io.github.nichetoolkit.ossfile.mapper;

import io.github.nichetoolkit.ossfile.OssfilePartEntity;
import io.github.nichetoolkit.rice.RiceIdMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.Collection;
import java.util.List;

@Component
public interface FileChunkMapper extends RiceIdMapper<OssfilePartEntity>, Mapper<OssfilePartEntity> {

    /**
     * 通过id查询实体
     * @param fileId     文件id
     * @param chunkIndex 分片序号
     * @return T 查询的数据
     */
    OssfilePartEntity findByFileIdAndChunkIndex(@Param("fileId") String fileId, @Param("chunkIndex") Integer chunkIndex);


    /**
     * 通过id查询实体
     * @param fileId 文件id
     * @return T 查询的数据
     */
    OssfilePartEntity findByFileIdFirstChunk(@Param("fileId") String fileId);

    /**
     * 通过id查询实体
     * @param fileId 文件id
     * @return T 查询的数据
     */
    OssfilePartEntity findByFileIdLastChunk(@Param("fileId") String fileId);

    /**
     * 通过id查询实体
     * @param fileId 文件id
     * @return T 查询的数据
     */
    List<OssfilePartEntity> findAllByFileId(@Param("fileId") String fileId);

    /**
     * 通过id查询实体
     * @param fileIds 文件id集合
     * @return T 查询的数据
     */
    List<OssfilePartEntity> findAllByFileIds(@Param("fileIds") Collection<String> fileIds);


}
