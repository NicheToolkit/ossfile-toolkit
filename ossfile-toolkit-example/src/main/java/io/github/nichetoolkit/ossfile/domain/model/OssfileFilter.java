package io.github.nichetoolkit.ossfile.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultFilter;
import io.github.nichetoolkit.rice.builder.SqlBuilders;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.NonNull;

/**
 * <code>OssfileFilter</code>
 * <p>The ossfile filter class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultFilter
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.experimental.SuperBuilder
 * @see lombok.EqualsAndHashCode
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk17
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileFilter extends DefaultFilter<String,String> {
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
     * <code>bucket</code>
     * {@link java.lang.String} <p>The <code>bucket</code> field.</p>
     * @see java.lang.String
     */
    protected String bucket;
    /**
     * <code>fileType</code>
     * {@link io.github.nichetoolkit.rest.RestKey} <p>The <code>fileType</code> field.</p>
     * @see io.github.nichetoolkit.rest.RestKey
     */
    protected RestKey<String> fileType;

    /**
     * <code>part</code>
     * {@link java.lang.Boolean} <p>The <code>part</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean part;
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
     * <code>preview</code>
     * {@link java.lang.Boolean} <p>The <code>preview</code> field.</p>
     * @see java.lang.Boolean
     */
    protected Boolean preview;

    /**
     * <code>OssfileFilter</code>
     * <p>Instantiates a new ossfile filter.</p>
     */
    public OssfileFilter() {
    }

    /**
     * <code>OssfileFilter</code>
     * <p>Instantiates a new ossfile filter.</p>
     * @param id {@link java.lang.String} <p>The id parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssfileFilter(String id) {
        super(id);
    }

    /**
     * <code>OssfileFilter</code>
     * <p>Instantiates a new ossfile filter.</p>
     * @param ids {@link java.lang.String} <p>The ids parameter is <code>String</code> type.</p>
     * @see java.lang.String
     */
    public OssfileFilter(String... ids) {
        super(ids);
    }

    /**
     * <code>toBucketSql</code>
     * <p>The to bucket sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to bucket sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public OssfileFilter toBucketSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.bucket)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.bucket);
        }
        return this;
    }

    /**
     * <code>toFileTypeSql</code>
     * <p>The to file type sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to file type sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public OssfileFilter toFileTypeSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.fileType)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.fileType);
        }
        return this;
    }

    /**
     * <code>toUserIdSql</code>
     * <p>The to user id sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to user id sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public OssfileFilter toUserIdSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.userId)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.userId);
        }
        return this;
    }

    /**
     * <code>toProjectIdSql</code>
     * <p>The to project id sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to project id sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public OssfileFilter toProjectIdSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.projectId)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.projectId);
        }
        return this;
    }

    /**
     * <code>toBulkIdSql</code>
     * <p>The to bulk id sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to bulk id sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public OssfileFilter toBulkIdSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.bulkId)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.bulkId);
        }
        return this;
    }

    /**
     * <code>toUploadIdSql</code>
     * <p>The to upload id sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to upload id sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public OssfileFilter toUploadIdSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.uploadId)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.uploadId);
        }
        return this;
    }

    /**
     * <code>toPartStateSql</code>
     * <p>The to part state sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to part state sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public OssfileFilter toPartStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.part);
        return this;
    }

    /**
     * <code>toFinishStateSql</code>
     * <p>The to finish state sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to finish state sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public OssfileFilter toFinishStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.finish);
        return this;
    }

    /**
     * <code>toCompressStateSql</code>
     * <p>The to compress state sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to compress state sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public OssfileFilter toCompressStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.compress);
        return this;
    }

    /**
     * <code>toPreviewStateSql</code>
     * <p>The to preview state sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to preview state sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.jspecify.annotations.NonNull
     */
    public OssfileFilter toPreviewStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.preview);
        return this;
    }

}
