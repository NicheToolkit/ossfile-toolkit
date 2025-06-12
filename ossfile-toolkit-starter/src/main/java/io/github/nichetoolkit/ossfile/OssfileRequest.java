package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rice.RestId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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

    protected boolean isPart;
    protected long partSize;

    protected boolean isSignature;
    protected boolean isFinish;
    protected boolean isCompress;
    protected boolean isPreview;

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

    public OssfileBulkModel toBulkModel() {
        OssfileBulkModel bulkModel = new OssfileBulkModel();
        bulkModel.setUserId(this.userId);
        bulkModel.setProjectId(this.projectId);
        bulkModel.setUploadId(this.uploadId);
        bulkModel.setFilename(this.filename);
        bulkModel.setFileSize(this.fileSize);
        OssfileFileType fileType = OssfileFileType.parseKey(this.fileType);
        bulkModel.setFileType(fileType != OssfileFileType.UNKNOWN ? fileType : null);
        bulkModel.setPartState(this.isPart);
        bulkModel.setPartSize(this.partSize);
        bulkModel.setFinishState(this.isFinish);
        bulkModel.setCompressState(this.isCompress);
        bulkModel.setPreviewState(this.isPreview);
        return bulkModel;
    }
}
