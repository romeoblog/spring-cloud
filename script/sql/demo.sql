SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for demo
-- ----------------------------
DROP TABLE IF EXISTS `demo`;
CREATE TABLE `demo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `address` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of demo
-- ----------------------------
BEGIN;
INSERT INTO `demo` VALUES (1, '陆哥', 11, '广东天河');
INSERT INTO `demo` VALUES (2, '老大', 22, '中国');
INSERT INTO `demo` VALUES (3, '大佬', 43, '北京');
INSERT INTO `demo` VALUES (4, '锤子', 22, '珂朵莉');
INSERT INTO `demo` VALUES (5, '无名', 22, '啊哈哈');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
