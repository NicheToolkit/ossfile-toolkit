package io.github.nichetoolkit.ossfile.domain.entity;

import io.github.nichetoolkit.mybatis.table.RestLinkage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <code>OssfilePartLinks</code>
 * <p>The ossfile part links class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see Serializable
 * @see Data
 * @see Builder
 * @see RestLinkage
 * @since Jdk1.8
 */
@Data
@Builder
@RestLinkage
@NoArgsConstructor
@AllArgsConstructor
public class OssfilePartLinks implements Serializable {
    /**
     * <code>bulkId</code>
     * {@link String} <p>The <code>bulkId</code> field.</p>
     * @see String
     */
    protected String bulkId;
    /**
     * <code>projectId</code>
     * {@link String} <p>The <code>projectId</code> field.</p>
     * @see String
     */
    protected String projectId;
    /**
     * <code>uploadId</code>
     * {@link String} <p>The <code>uploadId</code> field.</p>
     * @see String
     */
    protected String uploadId;

    /**
     * <code>ofBulkId</code>
     * <p>The of bulk id method.</p>
     * @param bulkId {@link String} <p>The bulk id parameter is <code>String</code> type.</p>
     * @return {@link OssfilePartLinks} <p>The of bulk id return object is <code>OssfilePartLinks</code> type.</p>
     * @see String
     */
    public static OssfilePartLinks ofBulkId(String bulkId) {
        return OssfilePartLinks.builder().bulkId(bulkId).build();
    }

    /**
     * <code>ofProjectId</code>
     * <p>The of project id method.</p>
     * @param projectId {@link String} <p>The project id parameter is <code>String</code> type.</p>
     * @return {@link OssfilePartLinks} <p>The of project id return object is <code>OssfilePartLinks</code> type.</p>
     * @see String
     */
    public static OssfilePartLinks ofProjectId(String projectId) {
        return OssfilePartLinks.builder().projectId(projectId).build();
    }

    /**
     * <code>ofUploadId</code>
     * <p>The of upload id method.</p>
     * @param uploadId {@link String} <p>The upload id parameter is <code>String</code> type.</p>
     * @return {@link OssfilePartLinks} <p>The of upload id return object is <code>OssfilePartLinks</code> type.</p>
     * @see String
     */
    public static OssfilePartLinks ofUploadId(String uploadId) {
        return OssfilePartLinks.builder().uploadId(uploadId).build();
    }
}
