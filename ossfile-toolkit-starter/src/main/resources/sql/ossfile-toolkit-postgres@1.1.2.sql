DROP TABLE IF EXISTS "public"."ossfile_bulk";
CREATE TABLE "public"."ossfile_bulk" (
    "id"             VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
    "user_id"        VARCHAR(64) COLLATE "pg_catalog"."default",
    "project_id"     VARCHAR(64) COLLATE "pg_catalog"."default",
    "upload_id"      VARCHAR(64) COLLATE "pg_catalog"."default",
    "original"       VARCHAR(512) COLLATE "pg_catalog"."default",
    "filename"       VARCHAR(256) COLLATE "pg_catalog"."default",
    "bucket"         VARCHAR(256) COLLATE "pg_catalog"."default",
    "object_key"     VARCHAR(256) COLLATE "pg_catalog"."default",
    "object_path"    VARCHAR(256) COLLATE "pg_catalog"."default",
    "preview_key"    VARCHAR(256) COLLATE "pg_catalog"."default",
    "preview_path"   VARCHAR(256) COLLATE "pg_catalog"."default",
    "begin_time"     TIMESTAMPTZ,
    "complete_time"  TIMESTAMPTZ,
    "file_md5"       VARCHAR(256) COLLATE "pg_catalog"."default",
    "file_size"      INT8,
    "file_type"      VARCHAR(256) COLLATE "pg_catalog"."default",
    "part_size"      INT8,
    "etag"           VARCHAR(128) COLLATE "pg_catalog"."default",
    "version"        VARCHAR(128) COLLATE "pg_catalog"."default",
    `properties`     TEXT,
    "part_state"     BOOLEAN,
    "finish_state"   BOOLEAN,
    "signature_state"   BOOLEAN,
    "compress_state" BOOLEAN,
    "preview_state"  BOOLEAN,
    "update_time" TIMESTAMPTZ,
    "create_time" TIMESTAMPTZ
);

ALTER TABLE "public"."ossfile_bulk"
    ADD CONSTRAINT "PK_OSSFILE_BULK_ID"
        PRIMARY KEY ("id");

CREATE INDEX "IDX_OSSFILE_BULK_USER_ID" ON "public"."ossfile_bulk" USING btree ( "user_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST );

CREATE INDEX "IDX_OSSFILE_BULK_PROJECT_ID" ON "public"."ossfile_bulk" USING btree ( "project_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST );

CREATE INDEX "IDX_OSSFILE_BULK_UPLOAD_ID" ON "public"."ossfile_bulk" USING btree ( "upload_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST );

CREATE INDEX "IDX_OSSFILE_BULK_FILENAME" ON "public"."ossfile_bulk" USING btree ( "filename" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST );

CREATE INDEX "IDX_OSSFILE_BULK_FILE_TYPE" ON "public"."ossfile_bulk" USING btree ( "file_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST );

DROP TABLE IF EXISTS "public"."ossfile_part";
CREATE TABLE "public"."ossfile_part" (
    "id"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
    "bulk_id"     VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
    "project_id"  VARCHAR(64) COLLATE "pg_catalog"."default",
    "upload_id"   VARCHAR(64) COLLATE "pg_catalog"."default",
    "filename"    VARCHAR(256) COLLATE "pg_catalog"."default",
    "part_index"  INT4                                       NOT NULL,
    "part_etag"   VARCHAR(128) COLLATE "pg_catalog"."default",
    "bucket"      VARCHAR(256) COLLATE "pg_catalog"."default",
    "object_key"  VARCHAR(256) COLLATE "pg_catalog"."default",
    "object_path" VARCHAR(256) COLLATE "pg_catalog"."default",
    "part_size"   INT8,
    "part_md5"    VARCHAR(256) COLLATE "pg_catalog"."default",
    "last_part"   BOOLEAN,
    "part_time" TIMESTAMPTZ
);

ALTER TABLE "public"."ossfile_part"
    ADD CONSTRAINT "PK_OSSFILE_PART_ID"
        PRIMARY KEY ("id");

CREATE INDEX "IDX_OSSFILE_PART_BULK_ID" ON "public"."ossfile_part" USING btree ( "bulk_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST );

CREATE INDEX "IDX_OSSFILE_PART_PROJECT_ID" ON "public"."ossfile_part" USING btree ( "project_id" "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST );

CREATE INDEX "IDX_OSSFILE_PART_UPLOAD_ID" ON "public"."ossfile_part" USING btree ( "upload_id" "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST );

CREATE INDEX "IDX_OSSFILE_PART_FILENAME" ON "public"."ossfile_part" USING btree ( "filename" "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST );

CREATE INDEX "IDX_OSSFILE_PART_PART_TIME" ON "public"."ossfile_part" USING btree ( "part_time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST );