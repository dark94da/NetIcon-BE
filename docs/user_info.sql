CREATE TABLE IF NOT EXISTS `user_info`
(
    id           bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '自增id',
    nickname     varchar(30)      NOT NULL COMMENT '用户名',
    identifier   varchar(30)      NOT NULL COMMENT '特征码',
    room_list    varchar(255)     NOT NULL DEFAULT '[]' COMMENT '房间表',
    pending_list varchar(255)     NOT NULL DEFAULT '[]' COMMENT '待加入房间表',
    created_at   int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '创建时间',
    updated_at   int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`nickname`),
    INDEX `idx_update_at` (`updated_at`),
    INDEX `idx_created_at` (`created_at`)
) ENGINE = innodb
  CHARSET = utf8mb4 COMMENT '用户信息表';