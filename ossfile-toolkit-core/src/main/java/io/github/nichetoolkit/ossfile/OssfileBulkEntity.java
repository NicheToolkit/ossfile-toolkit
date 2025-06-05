package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.rice.DefaultInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <code>OssfileBulkEntity</code>
 * <p>The ossfile bulk entity class.</p>
 * @param <E> {@link io.github.nichetoolkit.ossfile.OssfileBulkEntity} <p>The generic parameter is <code>OssfileBulkEntity</code> type.</p>
 * @param <M> {@link io.github.nichetoolkit.ossfile.OssfileBulkModel} <p>The generic parameter is <code>OssfileBulkModel</code> type.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.ossfile.OssfileBulkModel
 * @see io.github.nichetoolkit.rice.DefaultInfoEntity
 * @see lombok.Data
 * @see lombok.EqualsAndHashCode
 * @since Jdk1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OssfileBulkEntity<E extends OssfileBulkEntity<E, M>, M extends OssfileBulkModel<M, E>> extends DefaultInfoEntity<E, M, String> {
    /**
     * <code>userId</code>
     * {@link java.lang.String} <p>The <code>userId</code> field.</p>
     * @see java.lang.String
     */
    protected String userId;
    /**
     * <code>projectId</code>
     * {@link java.lang.String} <p>The <code>projectId</code> field.</p>
     * @see java.lang.String
     */
    protected String projectId;
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
     * <code>alias</code>
     * {@link java.lang.String} <p>The <code>alias</code> field.</p>
     * @see java.lang.String
     */
    protected String alias;
    /**
     * <code>suffix</code>
     * {@link java.lang.String} <p>The <code>suffix</code> field.</p>
     * @see java.lang.String
     */
    protected String suffix;

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
     * {@link java.lang.Integer} <p>The <code>fileType</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer fileType;
    /**
     * <code>properties</code>
     * {@link java.lang.String} <p>The <code>properties</code> field.</p>
     * @see java.lang.String
     */
    protected String properties;

    /**
     * <code>finish</code>
     * {@link java.lang.Boolean} <p>The <code>finish</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean finish;
    /**
     * <code>compress</code>
     * {@link java.lang.Boolean} <p>The <code>compress</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean compress;
    /**
     * <code>part</code>
     * {@link java.lang.Boolean} <p>The <code>part</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean part;
    /**
     * <code>parteSize</code>
     * {@link java.lang.Integer} <p>The <code>parteSize</code> field.</p>
     * @see java.lang.Integer
     */
    protected Integer parteSize;
    /**
     * <code>merge</code>
     * {@link java.lang.Boolean} <p>The <code>merge</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean merge;

    /**
     * <code>uploadId</code>
     * {@link java.lang.String} <p>The <code>uploadId</code> field.</p>
     * @see java.lang.String
     */
    protected String uploadId;

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
     * <code>headers</code>
     * {@link java.lang.String} <p>The <code>headers</code> field.</p>
     * @see java.lang.String
     */
    protected String headers;


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

}
