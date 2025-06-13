package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rest.RestException;
import io.github.nichetoolkit.rest.RestOptional;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.RestId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileRequest implements Serializable {
    protected String userId;
    protected String projectId;
    protected String bulkId;
    protected String uploadId;

    protected String filename;
    protected long fileSize;

    protected String fileType;

    protected int width;
    protected int height;

    protected Boolean part;
    protected long partSize;

    protected Boolean signature;
    protected Boolean finish;
    protected Boolean compress;
    protected Boolean preview;

    protected Map<String,Object> properties;

    public OssfileRequest() {}

    public void setUser(RestId<String> user) {
        this.userId = user.getId();
    }

    public void setProject(RestId<String> project) {
        this.projectId = project.getId();
    }

    public void setBulk(RestId<String> bulk) {
        this.bulkId = bulk.getId();
    }

    public OssfileBulkModel toBulkModel() throws RestException {
        OssfileBulkModel bulkModel = new OssfileBulkModel();
        bulkModel.setUserId(this.userId);
        bulkModel.setProjectId(this.projectId);
        bulkModel.setUploadId(this.uploadId);
        bulkModel.setFilename(this.filename);
        bulkModel.setFileSize(this.fileSize);
        OssfileFileType fileType = OssfileFileType.parseKey(this.fileType);
        bulkModel.setFileType(fileType != OssfileFileType.UNKNOWN ? fileType : null);
        RestOptional.ofEmptyable(this.finish).isNotEmpty(bulkModel::setFinishState).ofEmpty(() -> {
            bulkModel.setCompleteTime(new Date());
            bulkModel.setFinishState(true);
        });
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
