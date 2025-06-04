package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rice.DefaultIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <code>OssfilePartEntity</code>
 * <p>The ossfile part entity class.</p>
 * @param <E> {@link io.github.nichetoolkit.ossfile.OssfilePartEntity} <p>The generic parameter is <code>OssfilePartEntity</code> type.</p>
 * @param <M> {@link io.github.nichetoolkit.ossfile.OssfilePartModel} <p>The generic parameter is <code>OssfilePartModel</code> type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfilePartModel
 * @see io.github.nichetoolkit.rice.DefaultIdEntity
 * @see lombok.Data
 * @see lombok.EqualsAndHashCode
 * @since Jdk1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OssfilePartEntity<E extends OssfilePartEntity<E,M>,M extends OssfilePartModel<M,E>> extends DefaultIdEntity<E,M,String> {
    /**
     * <code>bulkId</code>
     * {@link java.lang.String} <p>The <code>bulkId</code> field.</p>
     * @see java.lang.String
     */
    protected String bulkId;
    /**
     * <code>projectId</code>
     * {@link java.lang.String} <p>The <code>projectId</code> field.</p>
     * @see java.lang.String
     */
    protected String projectId;

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
     * <code>partSize</code>
     * {@link java.lang.Long} <p>The <code>partSize</code> field.</p>
     * @see java.lang.Long
     */
    protected Long partSize;
    /**
     * <code>partStart</code>
     * {@link java.lang.Long} <p>The <code>partStart</code> field.</p>
     * @see java.lang.Long
     */
    protected Long partStart;
    /**
     * <code>partEnd</code>
     * {@link java.lang.Long} <p>The <code>partEnd</code> field.</p>
     * @see java.lang.Long
     */
    protected Long partEnd;
    /**
     * <code>partMd5</code>
     * {@link java.lang.String} <p>The <code>partMd5</code> field.</p>
     * @see java.lang.String
     */
    protected String partMd5;
    /**
     * <code>lastPart</code>
     * {@link java.lang.Boolean} <p>The <code>lastPart</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean lastPart;

    /**
     * <code>partTime</code>
     * {@link java.util.Date} <p>The <code>partTime</code> field.</p>
     * @see java.util.Date
     */
    protected Date partTime;
    /**
     * <code>startTime</code>
     * {@link java.util.Date} <p>The <code>startTime</code> field.</p>
     * @see java.util.Date
     */
    protected Date startTime;
    /**
     * <code>endTime</code>
     * {@link java.util.Date} <p>The <code>endTime</code> field.</p>
     * @see java.util.Date
     */
    protected Date endTime;

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

}
