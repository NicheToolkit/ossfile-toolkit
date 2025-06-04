package io.github.nichetoolkit.ossfile.service;

import io.github.nichetoolkit.ossfile.OssfilePartEntity;
import io.github.nichetoolkit.ossfile.OssfileFilter;
import io.github.nichetoolkit.ossfile.mapper.FileChunkMapper;
import io.github.nichetoolkit.ossfile.OssfilePartModel;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.RiceIdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>FileChunkServiceImpl</p>
 * @author Cyan (snow22314@outlook.com)
 * @version v1.0.0
 */
@Slf4j
@Service
public class FileChunkServiceImpl extends RiceIdService<OssfilePartModel, OssfilePartEntity, OssfileFilter> implements FileChunkService {

    @Override
    public OssfilePartModel queryByFileIdAndChunkIndex(String fileId, Integer chunkIndex) throws RestException {
        if (GeneralUtils.isEmpty(fileId) || GeneralUtils.isEmpty(chunkIndex)) {
            return null;
        }
        OssfilePartEntity entity = ((FileChunkMapper) superMapper).findByFileIdAndChunkIndex(fileId, chunkIndex);
        if (GeneralUtils.isNotEmpty(entity)) {
            return modelActuator(entity);
        }
        return null;
    }

    @Override
    public OssfilePartModel queryByFileIdFirstChunk(String fileId) throws RestException {
        if (GeneralUtils.isEmpty(fileId)) {
            return null;
        }
        OssfilePartEntity entity = ((FileChunkMapper) superMapper).findByFileIdFirstChunk(fileId);
        if (GeneralUtils.isNotEmpty(entity)) {
            return modelActuator(entity);
        }
        return null;
    }

    @Override
    public OssfilePartModel queryByFileIdLastChunk(String fileId) throws RestException {
        if (GeneralUtils.isEmpty(fileId)) {
            return null;
        }
        OssfilePartEntity entity = ((FileChunkMapper) superMapper).findByFileIdLastChunk(fileId);
        if (GeneralUtils.isNotEmpty(entity)) {
            return modelActuator(entity);
        }
        return null;
    }

    @Override
    public List<OssfilePartModel> queryAllByFileId(String fileId) throws RestException {
        if (GeneralUtils.isEmpty(fileId)) {
            return Collections.emptyList();
        }
        List<OssfilePartEntity> entityList = ((FileChunkMapper) superMapper).findAllByFileId(fileId);
        log.debug("file chunk list has querying successful! size: {}", entityList.size());
        return modelActuator(entityList);
    }

    @Override
    public List<OssfilePartModel> queryAllByFileIds(Collection<String> fileIds) throws RestException {
        if (GeneralUtils.isEmpty(fileIds)) {
            return Collections.emptyList();
        }
        List<OssfilePartEntity> entityList = ((FileChunkMapper) superMapper).findAllByFileIds(fileIds);
        log.debug("file chunk list has querying successful! size: {}", entityList.size());
        return modelActuator(entityList);
    }

    @Override
    public String queryWhereSql(OssfileFilter filter) throws RestException {
        return filter.toFileChunkSql().toTimeSql("chunk_time").toOperateSql().toIdSql().addSorts("chunk_time").toSql();
    }

    @Override
    public String deleteWhereSql(OssfileFilter filter) throws RestException {
        return filter.toIdSql().toSql();
    }
}
