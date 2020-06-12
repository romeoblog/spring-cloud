SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dg_account_role
-- ----------------------------
DROP TABLE IF EXISTS `dg_account_role`;
CREATE TABLE `dg_account_role` (
  `id` int(11) NOT NULL COMMENT '主键',
  `account_id` int(11) DEFAULT NULL COMMENT '会员id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dg_account_role
-- ----------------------------
BEGIN;
INSERT INTO `dg_account_role` VALUES (1, 1, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
