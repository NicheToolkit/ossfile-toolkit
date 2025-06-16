package io.github.nichetoolkit.ossfile.domain.entity;

import io.github.nichetoolkit.mybatis.table.RestLinkage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <code>OssfileBulkLinks</code>
 * <p>The ossfile bulk links class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see java.io.Serializable
 * @see lombok.Data
 * @see lombok.Builder
 * @see io.github.nichetoolkit.mybatis.table.RestLinkage
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 * @since Jdk1.8
 */
@Data
@Builder
@RestLinkage
@NoArgsConstructor
@AllArgsConstructor
public class OssfileBulkLinks implements Serializable {
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
     * <code>uploadId</code>
     * {@link java.lang.String} <p>The <code>uploadId</code> field.</p>
     * @see java.lang.String
     */
    protected String uploadId;

    /**
     * <code>ofUserId</code>
     * <p>The of user id method.</p>
     * @param userId {@link java.lang.String} <p>The user id parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.entity.OssfileBulkLinks} <p>The of user id return object is <code>OssfileBulkLinks</code> type.</p>
     * @see java.lang.String
     */
    public static OssfileBulkLinks ofUserId(String userId) {
        return OssfileBulkLinks.builder().userId(userId).build();
    }

    /**
     * <code>ofProjectId</code>
     * <p>The of project id method.</p>
     * @param projectId {@link java.lang.String} <p>The project id parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.entity.OssfileBulkLinks} <p>The of project id return object is <code>OssfileBulkLinks</code> type.</p>
     * @see java.lang.String
     */
    public static OssfileBulkLinks ofProjectId(String projectId) {
        return OssfileBulkLinks.builder().projectId(projectId).build();
    }

    /**
     * <code>ofUploadId</code>
     * <p>The of upload id method.</p>
     * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.entity.OssfileBulkLinks} <p>The of upload id return object is <code>OssfileBulkLinks</code> type.</p>
     * @see java.lang.String
     */
    public static OssfileBulkLinks ofUploadId(String uploadId) {
        return OssfileBulkLinks.builder().uploadId(uploadId).build();
    }
}
