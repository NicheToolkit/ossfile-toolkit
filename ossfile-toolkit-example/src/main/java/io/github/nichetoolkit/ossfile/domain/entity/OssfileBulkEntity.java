package io.github.nichetoolkit.ossfile.domain.entity;

import io.github.nichetoolkit.mybatis.column.RestAlertKey;
import io.github.nichetoolkit.mybatis.column.RestFickleEntry;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.ossfile.domain.OssfileFileType;
import io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel;
import io.github.nichetoolkit.rest.util.BeanUtils;
import io.github.nichetoolkit.rice.DefaultIdEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <code>OssfileBulkEntity</code>
 * <p>The ossfile bulk entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultIdEntity
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.experimental.SuperBuilder
 * @see io.github.nichetoolkit.mybatis.table.RestEntity
 * @see lombok.EqualsAndHashCode
 * @since Jdk17
 */
@Getter
@Setter
@SuperBuilder
@RestEntity(name = "ossfile_bulk")
@EqualsAndHashCode(callSuper = true)
public class OssfileBulkEntity extends DefaultIdEntity<OssfileBulkEntity, OssfileBulkModel, String> {
    /**
     * <code>links</code>
     * {@link io.github.nichetoolkit.ossfile.domain.entity.OssfileBulkLinks} <p>The <code>links</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.domain.entity.OssfileBulkLinks
     * @see io.github.nichetoolkit.mybatis.column.RestLinkKey
     */
    @RestLinkKey
    protected OssfileBulkLinks links;
    /**
     * <code>states</code>
     * {@link io.github.nichetoolkit.ossfile.domain.entity.OssfileStates} <p>The <code>states</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.domain.entity.OssfileStates
     * @see io.github.nichetoolkit.mybatis.column.RestAlertKey
     */
    @RestAlertKey
    protected OssfileStates states;

    /**
     * <code>original</code>
     * {@link java.lang.String} <p>The <code>original</code> field.</p>
     * @see java.lang.String
     */
    protected String original;
    /**
     * <code>filename</code>
     * {@link java.lang.String} <p>The <code>filename</code> field.</p>
     * @see java.lang.String
     */
    protected String filename;

    /**
     * <code>bucket</code>
     * {@link java.lang.String} <p>The <code>bucket</code> field.</p>
     * @see java.lang.String
     */
    protected String bucket;

    /**
     * <code>objectKey</code>
     * {@link java.lang.String} <p>The <code>objectKey</code> field.</p>
     * @see java.lang.String
     */
    protected String objectKey;
    /**
     * <code>objectPath</code>
     * {@link java.lang.String} <p>The <code>objectPath</code> field.</p>
     * @see java.lang.String
     */
    protected String objectPath;

    /**
     * <code>previewKey</code>
     * {@link java.lang.String} <p>The <code>previewKey</code> field.</p>
     * @see java.lang.String
     */
    protected String previewKey;
    /**
     * <code>previewPath</code>
     * {@link java.lang.String} <p>The <code>previewPath</code> field.</p>
     * @see java.lang.String
     */
    protected String previewPath;

    /**
     * <code>beginTime</code>
     * {@link java.util.Date} <p>The <code>beginTime</code> field.</p>
     * @see java.util.Date
     */
    protected Date beginTime;
    /**
     * <code>completeTime</code>
     * {@link java.util.Date} <p>The <code>completeTime</code> field.</p>
     * @see java.util.Date
     */
    protected Date completeTime;

    /**
     * <code>fileMd5</code>
     * {@link java.lang.String} <p>The <code>fileMd5</code> field.</p>
     * @see java.lang.String
     */
    protected String fileMd5;
    /**
     * <code>fileSize</code>
     * {@link java.lang.Long} <p>The <code>fileSize</code> field.</p>
     * @see java.lang.Long
     */
    protected Long fileSize;
    /**
     * <code>fileType</code>
     * {@link java.lang.String} <p>The <code>fileType</code> field.</p>
     * @see java.lang.String
     */
    protected String fileType;
    /**
     * <code>partSize</code>
     * {@link java.lang.Long} <p>The <code>partSize</code> field.</p>
     * @see java.lang.Long
     */
    protected Long partSize;

    /**
     * <code>properties</code>
     * {@link java.lang.String} <p>The <code>properties</code> field.</p>
     * @see java.lang.String
     */
    protected String properties;

    /**
     * <code>etag</code>
     * {@link java.lang.String} <p>The <code>etag</code> field.</p>
     * @see java.lang.String
     */
    protected String etag;
    /**
     * <code>version</code>
     * {@link java.lang.String} <p>The <code>version</code> field.</p>
     * @see java.lang.String
     */
    protected String version;

    /**
     * <code>metas</code>
     * {@link java.util.List} <p>The <code>metas</code> field.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.column.RestFickleEntry
     */
    @RestFickleEntry
    private List<RestFickle<?>> metas;


    /**
     * <code>OssfileBulkEntity</code>
     * <p>Instantiates a new ossfile bulk entity.</p>
     */
    public OssfileBulkEntity() {
    }

    /**
     * <code>OssfileBulkEntity</code>
     * <p>Instantiates a new ossfile bulk entity.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssfileBulkEntity(String id) {
        super(id);
    }

    @Override
    public OssfileBulkModel toModel() {
        OssfileBulkModel bulkModel = new OssfileBulkModel();
        BeanUtils.copyNonnullProperties(this, bulkModel);
        bulkModel.setFileType(OssfileFileType.parseKey(this.fileType));
        Optional.ofNullable(this.links).ifPresent((link) -> {
            bulkModel.setUserId(link.getUserId());
            bulkModel.setProjectId(link.getProjectId());
            bulkModel.setUploadId(link.getUploadId());
        });
        Optional.ofNullable(this.states).ifPresent((state) -> {
            bulkModel.setFinishState(state.getFinishState());
            bulkModel.setSignatureState(state.getSignatureState());
            bulkModel.setCompressState(state.getCompressState());
            bulkModel.setPartState(state.getPartState());
            bulkModel.setPreviewState(state.getPreviewState());
        });
        return bulkModel;
    }

}
