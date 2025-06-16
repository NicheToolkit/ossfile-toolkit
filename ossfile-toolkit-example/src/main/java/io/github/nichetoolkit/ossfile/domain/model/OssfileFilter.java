package io.github.nichetoolkit.ossfile.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.ossfile.domain.OssfileFileType;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultFilter;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.builder.SqlBuilders;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

/**
 * <code>OssfileFilter</code>
 * <p>The ossfile filter class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see io.github.nichetoolkit.rice.DefaultFilter
 * @see lombok.Data
 * @see lombok.EqualsAndHashCode
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
 * @since Jdk1.8
 */
@Data
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
     * <code>OssfileFilter</code>
     * <p>Instantiates a new ossfile filter.</p>
     * @param builder {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The builder parameter is <code>Builder</code> type.</p>
     * @see io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder
     */
    public OssfileFilter(Builder builder) {
        super(builder);
        this.userId = builder.userId;
        this.projectId = builder.projectId;
        this.bulkId = builder.bulkId;
        this.uploadId = builder.uploadId;
        this.bucket = builder.bucket;
        this.fileType = builder.fileType;
        this.part = builder.part;
        this.finish = builder.finish;
        this.compress = builder.compress;
        this.preview = builder.preview;
    }

    /**
     * <code>toBucketSql</code>
     * <p>The to bucket sql method.</p>
     * @param alias {@link java.lang.String} <p>The alias parameter is <code>String</code> type.</p>
     * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter} <p>The to bucket sql return object is <code>OssfileFilter</code> type.</p>
     * @see java.lang.String
     * @see org.springframework.lang.NonNull
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
     * @see org.springframework.lang.NonNull
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
     * @see org.springframework.lang.NonNull
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
     * @see org.springframework.lang.NonNull
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
     * @see org.springframework.lang.NonNull
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
     * @see org.springframework.lang.NonNull
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
     * @see org.springframework.lang.NonNull
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
     * @see org.springframework.lang.NonNull
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
     * @see org.springframework.lang.NonNull
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
     * @see org.springframework.lang.NonNull
     */
    public OssfileFilter toPreviewStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.preview);
        return this;
    }

    /**
     * <code>Builder</code>
     * <p>The builder class.</p>
     * @author Cyan (snow22314@outlook.com)
     * @see io.github.nichetoolkit.rice.DefaultFilter.Builder
     * @since Jdk1.8
     */
    public static class Builder extends DefaultFilter.Builder<String,String> {
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
         * {@link io.github.nichetoolkit.ossfile.domain.OssfileFileType} <p>The <code>fileType</code> field.</p>
         * @see io.github.nichetoolkit.ossfile.domain.OssfileFileType
         */
        protected OssfileFileType fileType;

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
         * <code>Builder</code>
         * <p>Instantiates a new builder.</p>
         */
        public Builder() {
        }

        /**
         * <code>userId</code>
         * <p>The user id method.</p>
         * @param userId {@link java.lang.String} <p>The user id parameter is <code>String</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The user id return object is <code>Builder</code> type.</p>
         * @see java.lang.String
         */
        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        /**
         * <code>userId</code>
         * <p>The user id method.</p>
         * @param user {@link io.github.nichetoolkit.rice.RestId} <p>The user parameter is <code>RestId</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The user id return object is <code>Builder</code> type.</p>
         * @see io.github.nichetoolkit.rice.RestId
         * @see org.springframework.lang.NonNull
         */
        public Builder userId(@NonNull RestId<String> user) {
            this.userId = user.getId();
            return this;
        }

        /**
         * <code>projectId</code>
         * <p>The project id method.</p>
         * @param projectId {@link java.lang.String} <p>The project id parameter is <code>String</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The project id return object is <code>Builder</code> type.</p>
         * @see java.lang.String
         */
        public Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        /**
         * <code>projectId</code>
         * <p>The project id method.</p>
         * @param project {@link io.github.nichetoolkit.rice.RestId} <p>The project parameter is <code>RestId</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The project id return object is <code>Builder</code> type.</p>
         * @see io.github.nichetoolkit.rice.RestId
         * @see org.springframework.lang.NonNull
         */
        public Builder projectId(@NonNull RestId<String> project) {
            this.projectId = project.getId();
            return this;
        }

        /**
         * <code>bulkId</code>
         * <p>The bulk id method.</p>
         * @param bulkId {@link java.lang.String} <p>The bulk id parameter is <code>String</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The bulk id return object is <code>Builder</code> type.</p>
         * @see java.lang.String
         */
        public Builder bulkId(String bulkId) {
            this.bulkId = bulkId;
            return this;
        }

        /**
         * <code>bulkId</code>
         * <p>The bulk id method.</p>
         * @param bulk {@link io.github.nichetoolkit.rice.RestId} <p>The bulk parameter is <code>RestId</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The bulk id return object is <code>Builder</code> type.</p>
         * @see io.github.nichetoolkit.rice.RestId
         * @see org.springframework.lang.NonNull
         */
        public Builder bulkId(@NonNull RestId<String> bulk) {
            this.bulkId = bulk.getId();
            return this;
        }

        /**
         * <code>uploadId</code>
         * <p>The upload id method.</p>
         * @param uploadId {@link java.lang.String} <p>The upload id parameter is <code>String</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The upload id return object is <code>Builder</code> type.</p>
         * @see java.lang.String
         */
        public Builder uploadId(String uploadId) {
            this.uploadId = uploadId;
            return this;
        }

        /**
         * <code>bucket</code>
         * <p>The bucket method.</p>
         * @param bucket {@link java.lang.String} <p>The bucket parameter is <code>String</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The bucket return object is <code>Builder</code> type.</p>
         * @see java.lang.String
         */
        public Builder bucket(String bucket) {
            this.bucket = bucket;
            return this;
        }

        /**
         * <code>fileType</code>
         * <p>The file type method.</p>
         * @param fileType {@link java.lang.String} <p>The file type parameter is <code>String</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The file type return object is <code>Builder</code> type.</p>
         * @see java.lang.String
         * @see org.springframework.lang.NonNull
         */
        public Builder fileType(@NonNull String fileType) {
            this.fileType = OssfileFileType.parseKey(fileType);
            return this;
        }

        /**
         * <code>fileType</code>
         * <p>The file type method.</p>
         * @param fileType {@link io.github.nichetoolkit.ossfile.domain.OssfileFileType} <p>The file type parameter is <code>OssfileFileType</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The file type return object is <code>Builder</code> type.</p>
         * @see io.github.nichetoolkit.ossfile.domain.OssfileFileType
         */
        public Builder fileType(OssfileFileType fileType) {
            this.fileType = fileType;
            return this;
        }

        /**
         * <code>ofPart</code>
         * <p>The of part method.</p>
         * @param isPart {@link java.lang.Boolean} <p>The is part parameter is <code>Boolean</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The of part return object is <code>Builder</code> type.</p>
         * @see java.lang.Boolean
         */
        public Builder ofPart(Boolean isPart) {
            this.part = isPart;
            return this;
        }

        /**
         * <code>ofFinish</code>
         * <p>The of finish method.</p>
         * @param finish {@link java.lang.Boolean} <p>The finish parameter is <code>Boolean</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The of finish return object is <code>Builder</code> type.</p>
         * @see java.lang.Boolean
         */
        public Builder ofFinish(Boolean finish) {
            this.finish = finish;
            return this;
        }

        /**
         * <code>ofCompress</code>
         * <p>The of compress method.</p>
         * @param compress {@link java.lang.Boolean} <p>The compress parameter is <code>Boolean</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The of compress return object is <code>Builder</code> type.</p>
         * @see java.lang.Boolean
         */
        public Builder ofCompress(Boolean compress) {
            this.compress = compress;
            return this;
        }

        /**
         * <code>ofPreview</code>
         * <p>The of preview method.</p>
         * @param preview {@link java.lang.Boolean} <p>The preview parameter is <code>Boolean</code> type.</p>
         * @return {@link io.github.nichetoolkit.ossfile.domain.model.OssfileFilter.Builder} <p>The of preview return object is <code>Builder</code> type.</p>
         * @see java.lang.Boolean
         */
        public Builder ofPreview(Boolean preview) {
            this.preview = preview;
            return this;
        }

        public OssfileFilter build() {
            return new OssfileFilter(this);
        }
    }


}
