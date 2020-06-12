-- ----------------------------
-- 初始化 oAuth2 相关表
-- 使用官方提供的建表脚本初始化 oAuth2 相关表，地址如下：
-- https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql
--
-- 在数据库中配置客户端
-- 在表 oauth_client_details 中增加一条客户端配置记录，需要设置的字段如下：
-- client_id：客户端标识
-- client_secret：客户端安全码，此处不能是明文，需要加密
-- scope：客户端授权范围
-- authorized_grant_types：客户端授权类型
-- web_server_redirect_uri：服务器回调地址
-- 使用 BCryptPasswordEncoder 为客户端安全码加密，代码如下：
-- System.out.println(new BCryptPasswordEncoder().encode("secret"));
-- ----------------------------
CREATE TABLE `clientdetails`
(
  `appId`                  varchar(128) NOT NULL,
  `resourceIds`            varchar(256)  DEFAULT NULL,
  `appSecret`              varchar(256)  DEFAULT NULL,
  `scope`                  varchar(256)  DEFAULT NULL,
  `grantTypes`             varchar(256)  DEFAULT NULL,
  `redirectUrl`            varchar(256)  DEFAULT NULL,
  `authorities`            varchar(256)  DEFAULT NULL,
  `access_token_validity`  int(11)       DEFAULT NULL,
  `refresh_token_validity` int(11)       DEFAULT NULL,
  `additionalInformation`  varchar(4096) DEFAULT NULL,
  `autoApproveScopes`      varchar(256)  DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `oauth_access_token`
(
  `token_id`          varchar(256) DEFAULT NULL,
  `token`             blob,
  `authentication_id` varchar(128) NOT NULL,
  `user_name`         varchar(256) DEFAULT NULL,
  `client_id`         varchar(256) DEFAULT NULL,
  `authentication`    blob,
  `refresh_token`     varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `oauth_approvals`
(
  `userId`         varchar(256)   DEFAULT NULL,
  `clientId`       varchar(256)   DEFAULT NULL,
  `scope`          varchar(256)   DEFAULT NULL,
  `status`         varchar(10)    DEFAULT NULL,
  `expiresAt`      timestamp NULL DEFAULT NULL,
  `lastModifiedAt` timestamp NULL DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `oauth_client_details`
(
  `client_id`               varchar(128) NOT NULL,
  `resource_ids`            varchar(256)  DEFAULT NULL,
  `client_secret`           varchar(256)  DEFAULT NULL,
  `scope`                   varchar(256)  DEFAULT NULL,
  `authorized_grant_types`  varchar(256)  DEFAULT NULL,
  `web_server_redirect_uri` varchar(256)  DEFAULT NULL,
  `authorities`             varchar(256)  DEFAULT NULL,
  `access_token_validity`   int(11)       DEFAULT NULL,
  `refresh_token_validity`  int(11)       DEFAULT NULL,
  `additional_information`  varchar(4096) DEFAULT NULL,
  `autoapprove`             varchar(256)  DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `oauth_client_token`
(
  `token_id`          varchar(256) DEFAULT NULL,
  `token`             blob,
  `authentication_id` varchar(128) NOT NULL,
  `user_name`         varchar(256) DEFAULT NULL,
  `client_id`         varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `oauth_code`
(
  `code`           varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `oauth_refresh_token`
(
  `token_id`       varchar(256) DEFAULT NULL,
  `token`          blob,
  `authentication` blob
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;