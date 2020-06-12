SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dg_role
-- ----------------------------
DROP TABLE IF EXISTS `dg_role`;
CREATE TABLE `dg_role` (
  `id` int(11) NOT NULL COMMENT '角色id',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `valid` tinyint(1) DEFAULT NULL COMMENT '是否有效 1是 0否',
  `createTime` date DEFAULT NULL COMMENT '创建日期',
  `updateTime` date DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dg_role
-- ----------------------------
BEGIN;
INSERT INTO `dg_role` VALUES (1, 'ROLE_ADMIN', 1, '2019-12-14', '2019-12-14');
INSERT INTO `dg_role` VALUES (2, 'ROLE_USER', 1, '2019-12-14', '2019-12-14');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
