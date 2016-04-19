create table if not exists `system` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`display_nm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`host`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`port`  int(11) NOT NULL ,
`authorized_keys`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`status_cd`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'INITIAL' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;

create table if not exists `profiles` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`nm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`desc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

create table if not exists `system_map` (
`profile_id`  int(11) NOT NULL ,
`system_id`  int(11) NOT NULL ,
PRIMARY KEY (`profile_id`, `system_id`),
FOREIGN KEY (`profile_id`) REFERENCES `profiles` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
FOREIGN KEY (`system_id`) REFERENCES `system` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
INDEX `system_id` (`system_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

create table if not exists `application_key` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`public_key`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`private_key`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`passphrase`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;

create table if not exists `status` (
`id`  int(11) NOT NULL AUTO_INCREMENT,
`status_cd`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'INITIAL' ,
`system_id`  int(11) NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`system_id`) REFERENCES `system` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
INDEX `system_id` (`system_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

create table if not exists `public_keys` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`key_nm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`type`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`fingerprint`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`public_key`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`enabled`  tinyint(1) NOT NULL DEFAULT 1 ,
`create_dt`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`profile_id`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`profile_id`) REFERENCES `profiles` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
INDEX `profile_id` (`profile_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;



create table if not exists `session_log` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`session_tm`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


create table if not exists `terminal_log` (
`session_id`  bigint(20) NULL DEFAULT NULL ,
`instance_id`  int(11) NULL DEFAULT NULL ,
`system_id`  int(11) NULL DEFAULT NULL ,
`output`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`log_tm`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
FOREIGN KEY (`session_id`) REFERENCES `session_log` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
FOREIGN KEY (`system_id`) REFERENCES `system` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
INDEX `session_id` (`session_id`) USING BTREE ,
INDEX `system_id` (`system_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

create table if not exists `auth`` (
`id`  int(11) NOT NULL ,
`userId`  int(11) NULL DEFAULT NULL ,
`sessionId`  int(11) NULL DEFAULT NULL ,
`authToken`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`timeout`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;