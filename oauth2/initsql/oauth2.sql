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

 Date: 03/08/2021 11:06:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clientdetails
-- ----------------------------
DROP TABLE IF EXISTS `clientdetails`;
CREATE TABLE `clientdetails`
(
    `appId`                  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `resourceIds`            varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `appSecret`              varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `scope`                  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `grantTypes`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `redirectUrl`            varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `authorities`            varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `access_token_validity`  int(11)                                                        NULL DEFAULT NULL,
    `refresh_token_validity` int(11)                                                        NULL DEFAULT NULL,
    `additionalInformation`  varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `autoApproveScopes`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    PRIMARY KEY (`appId`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`
(
    `userId`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `clientId`       varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `scope`          varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `status`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `expiresAt`      timestamp(0)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
    `lastModifiedAt` timestamp(0)                                                  NOT NULL
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `resource_ids`            varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `client_secret`           varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `scope`                   varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `authorized_grant_types`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `authorities`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `access_token_validity`   int(11)                                                        NULL DEFAULT NULL,
    `refresh_token_validity`  int(11)                                                        NULL DEFAULT NULL,
    `additional_information`  varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `autoapprove`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token`
(
    `token_id`          varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `token`             mediumblob                                                    NULL,
    `authentication_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `user_name`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `client_id`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`
(
    `code`           varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `authentication` mediumblob                                                    NULL
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`
(
    `token_id`       varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `token`          mediumblob                                                    NULL,
    `authentication` mediumblob                                                    NULL
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
