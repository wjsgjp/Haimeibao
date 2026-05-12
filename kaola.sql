/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : kaola

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 31/12/2024 13:20:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货地址',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '收货地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (8, '123213131', '123', '123', 1, 'USER');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin', '管理员', 'http://localhost:9090/files/1734936074843-u=777275381,595148566&fm=253&fmt=auto&app=138&f=JPEG.webp', 'ADMIN', '13677889922', 'admin@xm.com');

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `business_id` int NULL DEFAULT NULL COMMENT '商家ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '广告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of banner
-- ----------------------------
INSERT INTO `banner` VALUES (3, 'http://localhost:9090/files/1701851471766-QQ截图20231022182940.png', 11);
INSERT INTO `banner` VALUES (4, 'http://localhost:9090/files/1701076373864-QQ截图20231022180706.png', 10);

-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'logo',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '简介',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '营业执照',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '待审核' COMMENT '审核状态',
  `time_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '营业时间',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商家表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of business
-- ----------------------------
INSERT INTO `business` VALUES (10, 'hbw', '123', '汉堡王', 'http://localhost:9090/files/1734934249059-QQ截图20231022180706.png', 'BUSINESS', '13233222233', '好吃的汉堡', 'sdasa', 'http://localhost:9090/files/1734936599596-微信截图_20231018161554.png', '通过', '9:00-21:00', '炸鸡汉堡');
INSERT INTO `business` VALUES (11, 'kwk', '123', '卡旺卡', 'http://localhost:9090/files/1734936632586-微信截图_20231019102827.png', 'BUSINESS', '0551-11659987', '好喝不贵', '123123', 'http://localhost:9090/files/1734936644479-微信截图_20231018161611.png', '通过', '9:00-19:00', '奶茶饮品');
INSERT INTO `business` VALUES (12, 'test', 'test', 'test', 'http://localhost:9090/files/1734936677545-QQ截图20231022183935.png', 'BUSINESS', '13275110727', '123123123', 'sdasa', 'http://localhost:9090/files/1734936683606-微信截图_20231018161554.png', '通过', '7:00-21:00', '轻食简餐');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `goods_id` int NULL DEFAULT NULL COMMENT '商品ID',
  `num` int NULL DEFAULT NULL COMMENT '数量',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `business_id` int NULL DEFAULT NULL COMMENT '商家ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '购物车' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (10, 10, 1, 1, 11);
INSERT INTO `cart` VALUES (11, 5, 1, 1, 11);
INSERT INTO `cart` VALUES (13, 1, 3, 1, 10);
INSERT INTO `cart` VALUES (14, 12, 1, 1, NULL);
INSERT INTO `cart` VALUES (15, 12, 1, 1, NULL);
INSERT INTO `cart` VALUES (16, 10, 1, 1, NULL);
INSERT INTO `cart` VALUES (17, 10, 1, 1, NULL);
INSERT INTO `cart` VALUES (18, 5, 1, 1, NULL);
INSERT INTO `cart` VALUES (19, 10, 1, 1, NULL);
INSERT INTO `cart` VALUES (20, 12, 1, 1, NULL);
INSERT INTO `cart` VALUES (21, 12, 1, 1, NULL);
INSERT INTO `cart` VALUES (22, 12, 1, 1, NULL);
INSERT INTO `cart` VALUES (23, 2, 1, 1, NULL);
INSERT INTO `cart` VALUES (24, 2, 1, 1, NULL);
INSERT INTO `cart` VALUES (25, 2, 1, 1, NULL);
INSERT INTO `cart` VALUES (26, 5, 1, 1, NULL);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `business_id` int NULL DEFAULT NULL COMMENT '商家ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '伯牙绝选', 11);
INSERT INTO `category` VALUES (2, '竹林幽静', 11);
INSERT INTO `category` VALUES (3, '高山流水', 11);
INSERT INTO `category` VALUES (10, '巨无霸汉堡', 10);
INSERT INTO `category` VALUES (11, '111', 11);
INSERT INTO `category` VALUES (12, '饭菜', 12);

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `business_id` int NULL DEFAULT NULL COMMENT '商家ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户收藏' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (7, 11, 1, '2023-12-06 15:30:26');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论',
  `star` double(10, 1) NULL DEFAULT 0.0 COMMENT '评分',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `business_id` int NULL DEFAULT NULL COMMENT '商家iD',
  `order_id` int NULL DEFAULT NULL COMMENT '订单ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `只能评论一次`(`user_id` ASC, `order_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评价表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, '1111', 0.0, 1, 10, 1, 'xxx');
INSERT INTO `comment` VALUES (8, 'qe', 3.0, 1, 11, 2, '2023-12-04 10:12:23');
INSERT INTO `comment` VALUES (9, '2121', 5.0, 1, 11, 7, '2023-12-06 15:31:40');
INSERT INTO `comment` VALUES (10, '111', 2.0, 1, 11, 5, '2023-12-06 16:43:36');
INSERT INTO `comment` VALUES (11, '3232', 3.0, 1, 11, 3, '2024-12-22 15:02:34');
INSERT INTO `comment` VALUES (12, '这家店很好啊', 4.0, 1, 10, 9, '2024-12-22 15:08:14');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `descr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `origin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '原材料',
  `taste` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '口味',
  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '规格',
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上架日期',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '上架' COMMENT '上架状态',
  `discount` double(11, 1) NULL DEFAULT 1.0 COMMENT '折扣',
  `business_id` int NULL DEFAULT NULL COMMENT '商家ID',
  `category_id` int NULL DEFAULT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '超级汉堡', 14.60, 'http://localhost:9090/files/1734934951481-微信截图_20231020115918.png', '好吃！！想吃，好馋！！', '肉', '香辣', '超大', '2023-11-30', '上架', 1.0, 10, 10);
INSERT INTO `goods` VALUES (2, '炸鸡', 28.60, 'http://localhost:9090/files/1734934925794-QQ截图20231022181557.png', '香香脆脆', '鸡肉', '香辣', '一般', '2023-11-28', '上架', 1.0, 10, 10);
INSERT INTO `goods` VALUES (5, '芋泥啵啵', 16.00, 'http://localhost:9090/files/1734934168149-QQ截图20231022181936.png', '啵啵啵', '芋泥和啵啵', '甜甜的', '中杯', '2023-11-28', '上架', 0.8, 11, 3);
INSERT INTO `goods` VALUES (10, '雪白奶盖', 14.00, 'http://localhost:9090/files/1734851073995-QQ截图20231022181626.png', '柔软香甜', '牛奶', '柔软香甜', '中杯', '2023-12-06', '上架', 1.0, 11, 2);
INSERT INTO `goods` VALUES (12, '1', 1.00, 'http://localhost:9090/files/1734850890768-QQ截图20231022180706.png', '1', '1', '辣', '1', '2024-12-22', '上架', 1.0, 12, 12);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int NOT NULL COMMENT '发送者ID',
  `receiver_id` int NOT NULL COMMENT '接收者ID',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `create_time` datetime NOT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sender`(`sender_id` ASC) USING BTREE,
  INDEX `idx_receiver`(`receiver_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 1, 3, '你好', '2024-12-31 13:17:30');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建时间',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (5, '欢迎', '欢迎各位商家入驻', '2024-12-23', 'admin');
INSERT INTO `notice` VALUES (6, 'qs', '欢迎来到泉师外卖小程序~', '2024-12-23', 'admin');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '下单时间',
  `pay_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付方式',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单状态',
  `business_id` int NULL DEFAULT NULL COMMENT '接单商家ID',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '送货地址',
  `user_id` int NULL DEFAULT NULL COMMENT '下单人ID',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单总价',
  `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠金额',
  `actual` decimal(10, 2) NULL DEFAULT NULL COMMENT '实付款',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '缩略图',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (2, '1731211321401794560', '2023-12-03 15:18:35', '2023-12-04 09:43:32', '支付宝', '已完成', 11, '123', '123', '123213131', 1, 16.00, 3.20, 12.80, NULL, 'http://localhost:9090/files/1701132561341-QQ截图20231022182940.png', '芋泥啵啵等1件商品');
INSERT INTO `orders` VALUES (3, '1732301610493157376', '2023-12-06 15:30:55', '2023-12-06 15:31:09', '支付宝', '待发货', 11, '123', '123', '123213131', 1, 32.00, 6.40, 25.60, NULL, 'http://localhost:9090/files/1701132561341-QQ截图20231022182940.png', '芋泥啵啵等2件商品');
INSERT INTO `orders` VALUES (4, '1732301616931414016', '2023-12-06 15:30:57', '2024-12-22 15:02:27', '支付宝', '待收货', 11, '123', '123', '123213131', 1, 32.00, 6.40, 25.60, NULL, 'http://localhost:9090/files/1701132561341-QQ截图20231022182940.png', '芋泥啵啵等2件商品');
INSERT INTO `orders` VALUES (10, '1873937558839246848', '2024-12-31 11:41:42', '2024-12-31 11:41:48', '支付宝', '待发货', 12, '123', '123', '123213131', 1, 2.00, 0.00, 2.00, NULL, 'http://localhost:9090/files/1734850890768-QQ截图20231022180706.png', '1等2件商品');

-- ----------------------------
-- Table structure for orders_item
-- ----------------------------
DROP TABLE IF EXISTS `orders_item`;
CREATE TABLE `orders_item`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` int NULL DEFAULT NULL COMMENT '订单ID',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goods_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品图片',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `num` int NULL DEFAULT NULL COMMENT '购买数量',
  `goods_id` int NULL DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders_item
-- ----------------------------
INSERT INTO `orders_item` VALUES (1, 1, '111', NULL, 11.00, 1, 5);
INSERT INTO `orders_item` VALUES (2, 2, '芋泥啵啵', 'http://localhost:9090/files/1701132561341-QQ截图20231022182940.png', 12.80, 1, 5);
INSERT INTO `orders_item` VALUES (3, 3, '芋泥啵啵', 'http://localhost:9090/files/1701132561341-QQ截图20231022182940.png', 12.80, 1, 5);
INSERT INTO `orders_item` VALUES (4, 3, '芋泥啵啵', 'http://localhost:9090/files/1701132561341-QQ截图20231022182940.png', 12.80, 1, 5);
INSERT INTO `orders_item` VALUES (5, 4, '芋泥啵啵', 'http://localhost:9090/files/1701132561341-QQ截图20231022182940.png', 12.80, 1, 5);
INSERT INTO `orders_item` VALUES (6, 4, '芋泥啵啵', 'http://localhost:9090/files/1701132561341-QQ截图20231022182940.png', 12.80, 1, 5);
INSERT INTO `orders_item` VALUES (15, 10, '1', 'http://localhost:9090/files/1734850890768-QQ截图20231022180706.png', 1.00, 1, 12);
INSERT INTO `orders_item` VALUES (16, 10, '1', 'http://localhost:9090/files/1734850890768-QQ截图20231022180706.png', 1.00, 1, 12);

-- ----------------------------
-- Table structure for rider_apply
-- ----------------------------
DROP TABLE IF EXISTS `rider_apply`;
CREATE TABLE `rider_apply`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学院',
  `major_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业班级',
  `student_card` longblob NULL COMMENT '学生证图片',
  `health_card` longblob NULL COMMENT '健康证图片',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '审核中' COMMENT '申请状态',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '骑手申请表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rider_apply
-- ----------------------------
INSERT INTO `rider_apply` VALUES (1, 1, '1234', '男', '12', '12', NULL, NULL, '审核中', '2024-12-31 10:25:59');
INSERT INTO `rider_apply` VALUES (2, 2, '2121', '12', '12', '12', NULL, NULL, '已通过', '2024-12-31 10:32:35');
INSERT INTO `rider_apply` VALUES (3, 1, '123', '男', '123', '23', NULL, NULL, '审核中', '2024-12-31 12:29:04');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1234', '1234', '1234', 'http://localhost:9090/files/1701047708222-QQ截图20231022180706.png', 'USER', '女', '13275110727');
INSERT INTO `user` VALUES (2, '123', '123', '123', 'http://localhost:9090/files/1701056286648-QQ截图20231022181936.png', 'RIDER', NULL, NULL);
INSERT INTO `user` VALUES (3, 'rr', 'ee', 'e', 'http://localhost:9090/files/1701056286648-QQ截图20231022181936.png', 'BUSINESS', '男', '12312442334');

SET FOREIGN_KEY_CHECKS = 1;
