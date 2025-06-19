package io.github.nichetoolkit.ossfile.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.ossfile.domain.OssfileFileType;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rice.RestId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * <code>OssfileRequest</code>
 * <p>The ossfile request class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.experimental.SuperBuilder
 * @see lombok.EqualsAndHashCode
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk1.8
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileRequest implements Serializable {
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
     * <code>bulkId</code>
     * {@link java.lang.String} <p>The <code>bulkId</code> field.</p>
     * @see java.lang.String
     */
    protected String bulkId;
    /**
     * <code>uploadId</code>
     * {@link java.lang.String} <p>The <code>uploadId</code> field.</p>
     * @see java.lang.String
     */
    protected String uploadId;

    /**
     * <code>filename</code>
     * {@link java.lang.String} <p>The <code>filename</code> field.</p>
     * @see java.lang.String
     */
    protected String filename;
    /**
     * <code>fileSize</code>
     * <p>The <code>fileSize</code> field.</p>
     */
    protected long fileSize;

    /**
     * <code>fileType</code>
     * {@link java.lang.String} <p>The <code>fileType</code> field.</p>
     * @see java.lang.String
     */
    protected String fileType;

    /**
     * <code>width</code>
     * <p>The <code>width</code> field.</p>
     */
    protected int width;
    /**
     * <code>height</code>
     * <p>The <code>height</code> field.</p>
     */
    protected int height;

    /**
     * <code>part</code>
     * {@link java.lang.Boolean} <p>The <code>part</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean part;
    /**
     * <code>partSize</code>
     * <p>The <code>partSize</code> field.</p>
     */
    protected long partSize;

    /**
     * <code>signature</code>
     * {@link java.lang.Boolean} <p>The <code>signature</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean signature;
    /**
     * <code>compress</code>
     * {@link java.lang.Boolean} <p>The <code>compress</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean compress;
    /**
     * <code>preview</code>
     * {@link java.lang.Boolean} <p>The <code>preview</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean preview;

    /**
     * <code>properties</code>
     * {@link java.util.Map} <p>The <code>properties</code> field.</p>
     * @see java.util.Map
     */
    protected Map<String,Object> properties;

    /**
     * <code>OssfileRequest</code>
     * <p>Instantiates a new ossfile request.</p>
     */
    public OssfileRequest() {}

    /**
     * <code>setUser</code>
     * <p>The set user setter method.</p>
     * @param user {@link io.github.nichetoolkit.rice.RestId} <p>The user parameter is <code>RestId</code> type.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    public void setUser(RestId<String> user) {
        this.userId = user.getId();
    }

    /**
     * <code>setProject</code>
     * <p>The set project setter method.</p>
     * @param project {@link io.github.nichetoolkit.rice.RestId} <p>The project parameter is <code>RestId</code> type.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    public void setProject(RestId<String> project) {
        this.projectId = project.getId();
    }

    /**
     * <code>setBulk</code>
     * <p>The set bulk setter method.</p>
     * @param bulk {@link io.github.nichetoolkit.rice.RestId} <p>The bulk parameter is <code>RestId</code> type.</p>
     * @see io.github.nichetoolkit.rice.RestId
     */
    public void setBulk(RestId<String> bulk) {
        this.bulkId = bulk.getId();
    }

    /**
     * <code>toBulkModel</code>
     * <p>The to bulk model method.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel} <p>The to bulk model return object is <code>OssfileBulkModel</code> type.</p>
     * @throws RestException {@link io.github.nichetoolkit.rest.RestException} <p>The rest exception is <code>RestException</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileBulkModel
     * @see io.github.nichetoolkit.rest.RestException
     */
    public OssfileBulkModel toBulkModel() throws RestException {
        OssfileBulkModel bulkModel = new OssfileBulkModel();
        bulkModel.setUserId(this.userId);
        bulkModel.setProjectId(this.projectId);
        bulkModel.setUploadId(this.uploadId);
        bulkModel.setFilename(this.filename);
        bulkModel.setFileSize(this.fileSize);
        OssfileFileType fileType = OssfileFileType.parseKey(this.fileType);
        bulkModel.setFileType(fileType != OssfileFileType.UNKNOWN ? fileType : null);
        RestOptional.ofEmptyable(this.compress).isNotEmpty(bulkModel::setCompressState).ofEmpty(() -> {
            bulkModel.setCompressState(false);
        });
        RestOptional.ofEmptyable(this.preview).isNotEmpty(bulkModel::setPreviewState).ofEmpty(() -> {
            bulkModel.setPreviewState(false);
        });
        RestOptional.ofEmptyable(this.part).isNotEmpty(bulkModel::setPartState).ofEmpty(() -> {
            bulkModel.setPartState(false);
        });
        RestOptional.ofEmptyable(this.signature).isNotEmpty(bulkModel::setSignatureState).ofEmpty(() -> {
            bulkModel.setSignatureState(false);
        });
        return bulkModel;
    }
}
