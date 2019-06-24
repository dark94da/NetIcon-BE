CREATE TABLE IF NOT EXISTS `room_info`
(
    id           bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '自增id',
    room_id      int(10)          NOT NULL COMMENT '房间id',
    member_list  varchar(100)     NOT NULL COMMENT '人员列表',
    last_talk_at int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最后聊天时间',
    created_at   int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
    updated_at   int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`room_id`),
    INDEX `idx_update_at` (`updated_at`),
    INDEX `idx_created_at` (`created_at`)
) ENGINE = innodb
  CHARSET = utf8mb4 COMMENT '房间信息表';