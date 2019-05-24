SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dg_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `dg_role_permission`;
CREATE TABLE `dg_role_permission` (
  `id` int(11) NOT NULL COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dg_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `dg_role_permission` VALUES (1, 1, 1);
INSERT INTO `dg_role_permission` VALUES (2, 1, 2);
INSERT INTO `dg_role_permission` VALUES (4, 1, 4);
INSERT INTO `dg_role_permission` VALUES (5, 1, 5);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
