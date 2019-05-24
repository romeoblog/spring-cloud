SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dg_permission
-- ----------------------------
DROP TABLE IF EXISTS `dg_permission`;
CREATE TABLE `dg_permission` (
  `id` int(11) NOT NULL COMMENT '权限id',
  `method` varchar(10) DEFAULT NULL COMMENT '方法类型',
  `gateway_prefix` varchar(30) DEFAULT NULL COMMENT '网关前缀',
  `service_prefix` varchar(30) DEFAULT NULL COMMENT '服务前缀',
  `uri` varchar(100) DEFAULT NULL COMMENT '请求路径',
  `createTime` date DEFAULT NULL COMMENT '创建日期',
  `updateTime` date DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dg_permission
-- ----------------------------
BEGIN;
INSERT INTO `dg_permission` VALUES (1, 'GET', '/api', '/auth', 'exit', '2019-12-14', '2019-12-14');
INSERT INTO `dg_permission` VALUES (2, 'GET', '/api', '/auth', 'member', '2019-12-17', '2019-12-17');
INSERT INTO `dg_permission` VALUES (3, 'GET', '/api', '/account', 'hello', '2019-12-17', '2019-12-17');
INSERT INTO `dg_permission` VALUES (4, 'GET', '/api', '/account', 'current', '2019-12-17', '2019-12-17');
INSERT INTO `dg_permission` VALUES (5, 'GET', '/api', '/account', 'query', '2019-12-17', '2019-12-17');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
