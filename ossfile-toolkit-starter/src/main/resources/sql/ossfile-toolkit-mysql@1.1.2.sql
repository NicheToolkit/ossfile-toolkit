DROP TABLE IF EXISTS `ossfile_bulk`;
CREATE TABLE `ossfile_bulk` (
    `id`             VARCHAR(64) NOT NULL,
    `user_id`        VARCHAR(64),
    `project_id`     VARCHAR(64),
    `upload_id`      VARCHAR(64),
    `original`       VARCHAR(512),
    `filename`       VARCHAR(256),
    `bucket`         VARCHAR(256),
    `object_key`     VARCHAR(256),
    `object_path`    VARCHAR(256),
    `preview_key`    VARCHAR(256),
    `preview_path`   VARCHAR(256),
    `begin_time`     TIMESTAMP,
    `complete_time`  TIMESTAMP,
    `file_md5`       VARCHAR(256),
    `file_size`      LONG,
    `file_type`      VARCHAR(256),
    `part_size`      LONG,
    `etag`           VARCHAR(128),
    `version`        VARCHAR(128),
    `properties`     TEXT,
    `part_state`     BOOLEAN,
    `finish_state`   BOOLEAN,
    `signature_state`   BOOLEAN,
    `compress_state` BOOLEAN,
    `preview_state`  BOOLEAN,
    `update_time`    TIMESTAMP,
    `create_time`    TIMESTAMP,
    CONSTRAINT `PK_OSSFILE_BULK`
        PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

ALTER TABLE `ossfile_bulk`
    ADD INDEX IDX_OSSFILE_BULK_USER_ID (`user_id`);

ALTER TABLE `ossfile_bulk`
    ADD INDEX IDX_OSSFILE_BULK_PROJECT_ID (`project_id`);

ALTER TABLE `ossfile_bulk`
    ADD INDEX IDX_OSSFILE_BULK_UPLOAD_ID (`upload_id`);

ALTER TABLE `ossfile_bulk`
    ADD INDEX IDX_OSSFILE_BULK_FILENAME (`filename`);

ALTER TABLE `ossfile_bulk`
    ADD INDEX IDX_OSSFILE_BULK_FILE_TYPE (`file_type`);


DROP TABLE IF EXISTS `ossfile_part`;
CREATE TABLE `ossfile_part` (
    `id`          VARCHAR(64) NOT NULL,
    `bulk_id`     VARCHAR(64) NOT NULL,
    `project_id`  VARCHAR(64),
    `upload_id`   VARCHAR(64),
    `filename`    VARCHAR(256),
    `part_index`  INT         NOT NULL,
    `part_etag`   VARCHAR(128),
    `bucket`      VARCHAR(256),
    `object_key`  VARCHAR(256),
    `object_path` VARCHAR(256),
    `part_size`   LONG,
    `part_start`  LONG,
    `part_end`    LONG,
    `part_md5`    VARCHAR(256),
    `last_part`   BOOLEAN,
    `part_time`   TIMESTAMP,
    CONSTRAINT `PK_OSSFILE_PART`
        PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

ALTER TABLE `ossfile_part`
    ADD INDEX IDX_OSSFILE_PART_BULK_ID (`bulk_id`);

ALTER TABLE `ossfile_part`
    ADD INDEX IDX_OSSFILE_PART_PROJECT_ID (`project_id`);

ALTER TABLE `ossfile_part`
    ADD INDEX IDX_OSSFILE_PART_UPLOAD_ID (`upload_id`);

ALTER TABLE `ossfile_part`
    ADD INDEX IDX_OSSFILE_PART_FILENAME (`filename`);

ALTER TABLE `ossfile_part`
    ADD INDEX IDX_OSSFILE_PART_PART_TIME (`part_time`);
