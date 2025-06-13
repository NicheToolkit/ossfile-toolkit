package io.github.nichetoolkit.ossfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nichetoolkit.rest.RestKey;
import io.github.nichetoolkit.rest.util.GeneralUtils;
import io.github.nichetoolkit.rice.DefaultFilter;
import io.github.nichetoolkit.rice.RestId;
import io.github.nichetoolkit.rice.builder.SqlBuilders;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OssfileFilter extends DefaultFilter<String,String> {
    protected String userId;
    protected String projectId;
    protected String bulkId;
    protected String uploadId;
    protected String bucket;
    protected RestKey<String> fileType;

    protected Boolean part;
    protected Boolean finish;
    protected Boolean compress;
    protected Boolean preview;

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
        this.part = builder.part;
        this.finish = builder.finish;
        this.compress = builder.compress;
        this.preview = builder.preview;
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
        SqlBuilders.equal(SQL_BUILDER, alias, this.part);
        return this;
    }

    public OssfileFilter toFinishStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.finish);
        return this;
    }
    public OssfileFilter toCompressStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.compress);
        return this;
    }

    public OssfileFilter toPreviewStateSql(@NonNull String alias) {
        SqlBuilders.equal(SQL_BUILDER, alias, this.preview);
        return this;
    }

    public static class Builder extends DefaultFilter.Builder<String,String> {
        protected String userId;
        protected String projectId;
        protected String bulkId;
        protected String uploadId;
        protected String bucket;
        protected OssfileFileType fileType;

        protected Boolean part;
        protected Boolean finish;
        protected Boolean compress;
        protected Boolean preview;

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

        public OssfileFilter.Builder ofPart(Boolean isPart) {
            this.part = isPart;
            return this;
        }

        public OssfileFilter.Builder ofFinish(Boolean finish) {
            this.finish = finish;
            return this;
        }

        public OssfileFilter.Builder ofCompress(Boolean compress) {
            this.compress = compress;
            return this;
        }

        public OssfileFilter.Builder ofPreview(Boolean preview) {
            this.preview = preview;
            return this;
        }

        public OssfileFilter build() {
            return new OssfileFilter(this);
        }
    }


}
