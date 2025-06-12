package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.mybatis.table.RestLinkage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Builder
@RestLinkage
public class OssfileBulkLinks implements Serializable {
    protected String userId;
    protected String projectId;
    protected String uploadId;

    public static OssfileBulkLinks ofUserId(String userId) {
        return OssfileBulkLinks.builder().userId(userId).build();
    }

    public static OssfileBulkLinks ofProjectId(String projectId) {
        return OssfileBulkLinks.builder().projectId(projectId).build();
    }

    public static OssfileBulkLinks ofUploadId(String uploadId) {
        return OssfileBulkLinks.builder().uploadId(uploadId).build();
    }
}
