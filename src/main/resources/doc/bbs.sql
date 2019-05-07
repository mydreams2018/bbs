/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : bbs

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-07 20:12:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for java_details
-- ----------------------------
DROP TABLE IF EXISTS `java_details`;
CREATE TABLE `java_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(66) COLLATE utf8_bin DEFAULT NULL,
  `publish_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `detail_data` mediumtext CHARACTER SET utf8 COLLATE utf8_bin,
  `posts_id` bigint(20) DEFAULT NULL,
  `is_posts` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of java_details
-- ----------------------------

-- ----------------------------
-- Table structure for java_posts
-- ----------------------------
DROP TABLE IF EXISTS `java_posts`;
CREATE TABLE `java_posts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(66) COLLATE utf8_bin DEFAULT NULL,
  `posts_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `category` tinyint(4) DEFAULT NULL,
  `publish_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `reply_time_end` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `reply_total` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of java_posts
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_methods` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `descript` varchar(126) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('cn.kungreat.bbs.controller.LoginController-test', 'login-test');

-- ----------------------------
-- Table structure for permission_mapping
-- ----------------------------
DROP TABLE IF EXISTS `permission_mapping`;
CREATE TABLE `permission_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(126) COLLATE utf8_bin DEFAULT NULL,
  `permission_methods` varchar(126) COLLATE utf8_bin DEFAULT NULL,
  `descript` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of permission_mapping
-- ----------------------------
INSERT INTO `permission_mapping` VALUES ('1', 'qepau886', 'cn.kungreat.bbs.controller.LoginController-test', '测试');

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `series` varchar(64) COLLATE utf8_bin NOT NULL,
  `token` varchar(64) COLLATE utf8_bin NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for posts_category
-- ----------------------------
DROP TABLE IF EXISTS `posts_category`;
CREATE TABLE `posts_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(126) COLLATE utf8_bin DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of posts_category
-- ----------------------------
INSERT INTO `posts_category` VALUES ('1', '分享信息', '1');
INSERT INTO `posts_category` VALUES ('2', '求助信息', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(66) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `alias` varchar(66) COLLATE utf8_bin DEFAULT NULL,
  `phone` bigint(11) DEFAULT NULL,
  `img` varchar(188) COLLATE utf8_bin DEFAULT NULL,
  `is_vip` bit(1) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `email` varchar(136) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `register_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'qepau886', '$2a$10$XLXhmfYIvR3bQTJukptX1.Ut/xYH.wbuIb6P.kdnkQwy9O6kDw4oG', 'Death Water', '186565645', '/userImg/qepau886.gif', '\0', null, '462044357@qq.com', '死水论坛', '2019-05-04 18:39:10');
SET FOREIGN_KEY_CHECKS=1;
