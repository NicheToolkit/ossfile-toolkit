package io.github.nichetoolkit.ossfile.domain.entity;

import io.github.nichetoolkit.mybatis.column.RestFickleEntry;
import io.github.nichetoolkit.mybatis.column.RestLinkKey;
import io.github.nichetoolkit.mybatis.fickle.RestFickle;
import io.github.nichetoolkit.mybatis.table.RestEntity;
import io.github.nichetoolkit.mybatis.table.RestExcludes;
import io.github.nichetoolkit.ossfile.domain.model.OssfilePartModel;
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
 * <code>OssfilePartEntity</code>
 * <p>The ossfile part entity class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultIdEntity
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.experimental.SuperBuilder
 * @see io.github.nichetoolkit.mybatis.table.RestEntity
 * @see io.github.nichetoolkit.mybatis.table.RestExcludes
 * @see lombok.EqualsAndHashCode
 * @since Jdk17
 */
@Getter
@Setter
@SuperBuilder
@RestEntity(name = "ossfile_part")
@RestExcludes({"updateTime","createTime"})
@EqualsAndHashCode(callSuper = true)
public class OssfilePartEntity extends DefaultIdEntity<OssfilePartEntity, OssfilePartModel,String> {
    /**
     * <code>links</code>
     * {@link io.github.nichetoolkit.ossfile.domain.entity.OssfilePartLinks} <p>The <code>links</code> field.</p>
     * @see io.github.nichetoolkit.ossfile.domain.entity.OssfilePartLinks
     * @see io.github.nichetoolkit.mybatis.column.RestLinkKey
     */
    @RestLinkKey
    protected OssfilePartLinks links;

    /**
     * <code>filename</code>
     * {@link java.lang.String} <p>The <code>filename</code> field.</p>
     * @see java.lang.String
     */
    protected String filename;
    /**
     * <code>partIndex</code>
     * {@link java.lang.Integer} <p>The <code>partIndex</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer partIndex;
    /**
     * <code>partEtag</code>
     * {@link java.lang.String} <p>The <code>partEtag</code> field.</p>
     * @see java.lang.String
     */
    protected String partEtag;

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
     * <code>partSize</code>
     * {@link java.lang.Long} <p>The <code>partSize</code> field.</p>
     * @see java.lang.Long
     */
    protected Long partSize;
    /**
     * <code>partMd5</code>
     * {@link java.lang.String} <p>The <code>partMd5</code> field.</p>
     * @see java.lang.String
     */
    protected String partMd5;

    /**
     * <code>partTime</code>
     * {@link java.util.Date} <p>The <code>partTime</code> field.</p>
     * @see java.util.Date
     */
    protected Date partTime;

    /**
     * <code>metas</code>
     * {@link java.util.List} <p>The <code>metas</code> field.</p>
     * @see java.util.List
     * @see io.github.nichetoolkit.mybatis.column.RestFickleEntry
     */
    @RestFickleEntry
    private List<RestFickle<?>> metas;

    /**
     * <code>OssfilePartEntity</code>
     * <p>Instantiates a new ossfile part entity.</p>
     */
    public OssfilePartEntity() {
    }

    /**
     * <code>OssfilePartEntity</code>
     * <p>Instantiates a new ossfile part entity.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssfilePartEntity(String id) {
        super(id);
    }


    @Override
    public OssfilePartModel toModel() {
        OssfilePartModel partModel = new OssfilePartModel();
        BeanUtils.copyNonnullProperties(this,partModel);
        Optional.ofNullable(this.links).ifPresent((link) -> {
            partModel.setBulkId(link.getBulkId());
            partModel.setProjectId(link.getProjectId());
            partModel.setUploadId(link.getUploadId());
        });
        return partModel;
    }
}
