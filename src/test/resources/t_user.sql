/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : localhost:3306
 Source Schema         : demox

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 06/07/2020 22:06:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(10) NOT NULL COMMENT '用户id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `age` int(10) NULL DEFAULT NULL COMMENT '年龄',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1001, '张三', 12, '18356985692');
INSERT INTO `t_user` VALUES (1002, '里斯', 39, '15956235412');
INSERT INTO `t_user` VALUES (1003, '王武', 25, '13074502653');

SET FOREIGN_KEY_CHECKS = 1;
