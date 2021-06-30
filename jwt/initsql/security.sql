/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : security

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 28/06/2021 10:50:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '功能说明',
  `pid` int(10) NULL DEFAULT NULL COMMENT '父id',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url路径',
  `method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `state` int(2) NULL DEFAULT 0 COMMENT '是否可用，0可用，1禁用',
  `key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code_index`(`code`) USING BTREE COMMENT '简单索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 'd19dfbd4-401c-43fb-841c-5a20c1fffff2', '用户管理', '用户列表', 0, '/user/getUsers', NULL, 0, 'query_user');
INSERT INTO `sys_permission` VALUES (2, '63bdf4c5-548f-46d0-8cf8-a5c37f178854', '角色管理', '角色列表', 0, '/role/getRole', NULL, 0, 'query_role');
INSERT INTO `sys_permission` VALUES (3, '99093fff-d061-4d31-9a91-8f387a8b26d7', '用户管理', '删除用户', 0, '/user/deleteUserById', NULL, 0, 'delete_user');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识主键',
  `rolename` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code_index`(`code`) USING BTREE COMMENT '简单索引'
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '83ddb71c-5b26-4cf2-aaa2-472b246cfcce', '超级管理员');
INSERT INTO `sys_role` VALUES (2, 'be1bb164-4b1f-4758-9264-da61065965d4', '普通用户表');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识主键',
  `permission_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code_index`(`code`) USING BTREE COMMENT '简单索引'
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, '97137c38-2961-4086-a1fd-9468561d0d7a', 1, 1);
INSERT INTO `sys_role_permission` VALUES (2, '8e9f2ab4-5e93-4e81-8789-14eeed4cef0d', 2, 1);
INSERT INTO `sys_role_permission` VALUES (3, '0c9caff1-deb0-48bb-a71d-73a76ce56077', 3, 1);
INSERT INTO `sys_role_permission` VALUES (4, '78ee11ed-9cbc-48d0-b3e0-67af90be93b3', 1, 2);
INSERT INTO `sys_role_permission` VALUES (5, 'a635c3d1-5b3e-42bc-a79b-eb9121b169ce', 2, 2);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code_index`(`code`) USING BTREE COMMENT '简单索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, '5edaa6e9-cd8e-4d74-a87a-28872269cb76', 2, 1);
INSERT INTO `sys_role_user` VALUES (2, 'cf76ad78-1af7-42ff-b77b-93bea1093db4', 1, 2);
INSERT INTO `sys_role_user` VALUES (3, '03d5a939-6d16-4522-9e8e-8cd0a90373bb', 3, 2);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识主键',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code_index`(`code`) USING BTREE COMMENT '简单索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'c2dc219e-b51b-405a-93bc-7301921415ef', 'joker', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `sys_user` VALUES (2, 'ce6e523c-f181-49df-ada2-e15601b00c4b', 'admin', '202cb962ac59075b964b07152d234b70');
INSERT INTO `sys_user` VALUES (3, 'eaa661f3-7838-491e-aaa5-c1accb291a81', 'neva', '63a9f0ea7bb98050796b649e85481845');

SET FOREIGN_KEY_CHECKS = 1;
