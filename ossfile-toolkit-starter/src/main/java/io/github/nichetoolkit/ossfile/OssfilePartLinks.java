package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.mybatis.table.RestLinkage;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@RestLinkage
public class OssfilePartLinks implements Serializable {
    protected String bulkId;
    protected String projectId;
    protected String uploadId;

    public OssfilePartLinks() {
    }

    public static OssfilePartLinks ofBulkId(String bulkId) {
        return OssfilePartLinks.builder().bulkId(bulkId).build();
    }

    public static OssfilePartLinks ofProjectId(String projectId) {
        return OssfilePartLinks.builder().projectId(projectId).build();
    }

    public static OssfilePartLinks ofUploadId(String uploadId) {
        return OssfilePartLinks.builder().uploadId(uploadId).build();
    }
}
