package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultFilter;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.builder.SqlBuilders;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileFilter extends DefaultFilter<String,String> {
    protected String userId;
    protected String projectId;
    protected String bulkId;
    protected String uploadId;
    protected String bucket;
    protected OssfileFileType fileType;

    protected boolean isPart;
    protected boolean isFinish;
    protected boolean isCompress;
    protected boolean isPreview;

    public OssfileFilter() {
    }

    public OssfileFilter(String id) {
        super(id);
    }

    public OssfileFilter(String... ids) {
        super(ids);
    }

    public OssfileFilter(OssfileFilter.Builder builder) {
        super(builder);
        this.userId = builder.userId;
        this.projectId = builder.projectId;
        this.bulkId = builder.bulkId;
        this.uploadId = builder.uploadId;
        this.bucket = builder.bucket;
        this.fileType = builder.fileType;
        this.isPart = builder.isPart;
        this.isFinish = builder.isFinish;
        this.isCompress = builder.isCompress;
        this.isPreview = builder.isPreview;
    }

    public OssfileFilter toBucketSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.bucket)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.bucket);
        }
        return this;
    }

    public OssfileFilter toFileTypeSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.fileType)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.fileType);
        }
        return this;
    }

    public OssfileFilter toUserIdSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.userId)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.userId);
        }
        return this;
    }

    public OssfileFilter toProjectIdSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.projectId)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.projectId);
        }
        return this;
    }

    public OssfileFilter toBulkIdSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.bulkId)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.bulkId);
        }
        return this;
    }

    public OssfileFilter toUploadIdSql(@NonNull String alias) {
        if (GeneralUtils.isNotEmpty(this.uploadId)) {
            SqlBuilders.equal(SQL_BUILDER, alias, this.uploadId);
        }
        return this;
    }

    public OssfileFilter toPartStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.isPart);
        return this;
    }

    public OssfileFilter toFinishStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.isFinish);
        return this;
    }
    public OssfileFilter toCompressStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.isCompress);
        return this;
    }

    public OssfileFilter toPreviewStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.isPreview);
        return this;
    }

    public static class Builder extends DefaultFilter.Builder<String,String> {
        protected String userId;
        protected String projectId;
        protected String bulkId;
        protected String uploadId;
        protected String bucket;
        protected OssfileFileType fileType;

        protected boolean isPart;
        protected boolean isFinish;
        protected boolean isCompress;
        protected boolean isPreview;

        public Builder() {
        }

        public OssfileFilter.Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public OssfileFilter.Builder userId(@NonNull RestId<String> user) {
            this.userId = user.getId();
            return this;
        }

        public OssfileFilter.Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public OssfileFilter.Builder projectId(@NonNull RestId<String> project) {
            this.projectId = project.getId();
            return this;
        }

        public OssfileFilter.Builder bulkId(String bulkId) {
            this.bulkId = bulkId;
            return this;
        }

        public OssfileFilter.Builder bulkId(@NonNull RestId<String> bulk) {
            this.bulkId = bulk.getId();
            return this;
        }

        public OssfileFilter.Builder uploadId(String uploadId) {
            this.uploadId = uploadId;
            return this;
        }

        public OssfileFilter.Builder bucket(String bucket) {
            this.bucket = bucket;
            return this;
        }

        public OssfileFilter.Builder fileType(@NonNull String fileType) {
            this.fileType = OssfileFileType.parseKey(fileType);
            return this;
        }

        public OssfileFilter.Builder fileType(OssfileFileType fileType) {
            this.fileType = fileType;
            return this;
        }

        public OssfileFilter.Builder ofPart(boolean isPart) {
            this.isPart = isPart;
            return this;
        }

        public OssfileFilter.Builder ofFinish(boolean isFinish) {
            this.isFinish = isFinish;
            return this;
        }

        public OssfileFilter.Builder ofCompress(boolean isCompress) {
            this.isCompress = isCompress;
            return this;
        }

        public OssfileFilter.Builder ofPreview(boolean isPreview) {
            this.isPreview = isPreview;
            return this;
        }

        public OssfileFilter build() {
            return new OssfileFilter(this);
        }
    }


}
