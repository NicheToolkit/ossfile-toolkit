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
    protected RestId<String> user;
    protected RestId<String> project;
    protected RestId<String> bulk;

    protected String userId;
    protected String projectId;
    protected String bulkId;
    protected String uploadId;

    protected String filename;
    protected long fileSize;

    protected OssfileFileType fileType;

    protected int width;
    protected int height;

    protected boolean isPart;
    protected int partSize;

    protected boolean isSignature;
    protected boolean isFinish;
    protected boolean isCompress;
    protected boolean isPreview;

    protected Map<String,Object> properties;


}
