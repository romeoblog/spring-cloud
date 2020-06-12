SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dg_account
-- ----------------------------
DROP TABLE IF EXISTS `dg_account`;
CREATE TABLE `dg_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `account_name` varchar(30) DEFAULT NULL COMMENT '会员名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别 1男0女',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `birthday` date DEFAULT NULL COMMENT '出生日',
  `createTime` date DEFAULT NULL COMMENT '注册日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dg_account
-- ----------------------------
BEGIN;
INSERT INTO `dg_account` VALUES (1, 'admin', '123456', '111', 1, '1111', '2018-12-13', '2018-12-13');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
