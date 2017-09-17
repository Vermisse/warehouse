-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.2.7-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 warehouse 的数据库结构
DROP DATABASE IF EXISTS `warehouse`;
CREATE DATABASE IF NOT EXISTS `warehouse` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `warehouse`;

-- 导出  表 warehouse.order 结构
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `id` varchar(50) NOT NULL COMMENT '单号',
  `delivery` varchar(50) NOT NULL COMMENT '快递',
  `status` int(50) NOT NULL COMMENT '订单状态：1-未处理、2-正在处理、3-已完成',
  `create_date` date NOT NULL COMMENT '下单日期',
  `update_date` datetime DEFAULT NULL COMMENT '最后处理时间',
  `accept` int(11) DEFAULT NULL COMMENT '处理人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 warehouse.order_product 结构
DROP TABLE IF EXISTS `order_product`;
CREATE TABLE IF NOT EXISTS `order_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) NOT NULL,
  `product_id` varchar(50) NOT NULL,
  `product_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 warehouse.product 结构
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '产品名，如果用不到就删除',
  `count` int(11) NOT NULL DEFAULT 0 COMMENT '库存总数量',
  `current_count` int(11) NOT NULL DEFAULT 0 COMMENT '当前库存数量',
  `new_inventory` int(11) NOT NULL DEFAULT 0,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `accept` int(11) DEFAULT NULL COMMENT '处理人',
  `del_state` int(11) DEFAULT 0 COMMENT '0：正常；1：删除；',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 warehouse.sys_user 结构
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL COMMENT '1:管理员/0:普通用户',
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
