package io.github.nichetoolkit.ossfile;

import io.github.nichetoolkit.mybatis.table.RestLinkage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <code>OssfilePartLinks</code>
 * <p>The ossfile part links class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Data
 * @see lombok.Builder
 * @see io.github.nichetoolkit.mybatis.table.RestLinkage
 * @since Jdk1.8
 */
@Data
@Builder
@RestLinkage
public class OssfilePartLinks implements Serializable {
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
     * <code>uploadId</code>
     * {@link java.lang.String} <p>The <code>uploadId</code> field.</p>
     * @see java.lang.String
     */
    protected String uploadId;

    /**
     * <code>ofBulkId</code>
     * <p>The of bulk id method.</p>
     * @param bulkId {@link java.lang.String} <p>The bulk id parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfilePartLinks} <p>The of bulk id return object is <code>OssfilePartLinks</code> type.</p>
     * @see java.lang.String
     */
    public static OssfilePartLinks ofBulkId(String bulkId) {
        return OssfilePartLinks.builder().bulkId(bulkId).build();
    }

    /**
     * <code>ofProjectId</code>
     * <p>The of project id method.</p>
     * @param projectId {@link java.lang.String} <p>The project id parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfilePartLinks} <p>The of project id return object is <code>OssfilePartLinks</code> type.</p>
     * @see java.lang.String
     */
    public static OssfilePartLinks ofProjectId(String projectId) {
        return OssfilePartLinks.builder().projectId(projectId).build();
    }

    /**
     * <code>ofUploadId</code>
     * <p>The of upload id method.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.OssfilePartLinks} <p>The of upload id return object is <code>OssfilePartLinks</code> type.</p>
     * @see java.lang.String
     */
    public static OssfilePartLinks ofUploadId(String uploadId) {
        return OssfilePartLinks.builder().uploadId(uploadId).build();
    }
}
