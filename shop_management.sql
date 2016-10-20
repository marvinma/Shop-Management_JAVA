-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 22, 2016 at 07:51 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `shop_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE IF NOT EXISTS `customers` (
  `cust_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'customer identification number.',
  `cust_name` varchar(50) NOT NULL COMMENT 'customer name of upto 50 chars.',
  `cust_mobile` decimal(10,0) NOT NULL COMMENT 'customer contact number of 10 digits.',
  `cust_points` int(11) NOT NULL DEFAULT '500' COMMENT 'customer loyalty points',
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1011360 ;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`cust_id`, `cust_name`, `cust_mobile`, `cust_points`) VALUES
(0, 'GUEST', '0', 0),
(1011342, 'vikalp sajwan', '7895012015', 3000),
(1011343, 'dhruv gothwal', '8956451254', 2000),
(1011344, 'shaniya nisha', '9565454545', 3800),
(1011345, 'karan bhutyani', '7856565656', 6500),
(1011346, 'prakash mehra', '9564646464', 1500),
(1011347, 'harry singh', '9855566622', 500),
(1011351, 'suraj sharma', '7895656545', 500),
(1011353, 'nikhil negi', '8945454521', 403),
(1011355, 'pinky semwal', '7845454565', 500),
(1011356, 'shivam sharma', '7856232142', 500),
(1011357, 'raj avasthi', '8565654545', 500),
(1011358, 'priya rai', '7852525232', 500),
(1011359, 'rupali sharma', '8754212121', 500);

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE IF NOT EXISTS `items` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'item identification number starting from 1001.',
  `item_name` varchar(50) NOT NULL COMMENT 'item name.',
  `item_details` varchar(50) NOT NULL COMMENT 'item detail in short such as size, material.',
  `item_price` decimal(10,2) NOT NULL COMMENT 'price for item with 10 digits in which 2 are after decimal.',
  `item_category` tinyint(4) NOT NULL,
  PRIMARY KEY (`item_id`),
  KEY `fk_item_cat` (`item_category`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1021 ;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`item_id`, `item_name`, `item_details`, `item_price`, `item_category`) VALUES
(1001, 'UCB Shirt', 'large(42 inches)', '850.00', 1),
(1002, 'Moto G smartphone', '16GB', '24000.00', 2),
(1004, 'Nike basketball  ', 'size 5', '1200.00', 3),
(1005, 'D''decor cousions set', 'set of 5', '2500.00', 4),
(1006, 'adidas T-Shirt', 'large(42 inches)', '900.00', 1),
(1007, 'reebok tracksuit', 'size - medium, waterproof', '2000.00', 1),
(1008, 'Peter England formal Shirt', 'slimfit', '1300.00', 1),
(1009, 'Levis jeans', '34 inches', '2000.00', 1),
(1010, 'Apple iphone6', '64GB', '68000.00', 2),
(1011, 'Philips Trimmer qt100', 'cordless', '1500.00', 2),
(1012, 'iball wireless keyboard', 'ergonomic design', '800.00', 2),
(1013, 'intex 5.1 channel speakers', '3500 watt output', '1800.00', 2),
(1014, 'Puma Men''s cotton Tshirt', 'medium', '799.00', 1),
(1015, 'UCB Men''s casual trousers', '30 inches', '1399.00', 1),
(1016, 'French Connection casual shirt', 'large(42 inches)', '1399.00', 1),
(1017, 'wrangler men''s skinny fit jeans', '34 inches', '1899.00', 1),
(1018, 'skullcandy headphones', 'noise reduction', '1399.00', 2),
(1019, 'seagate external Harddisk', '1 TB', '4599.00', 2),
(1020, 'cannon laser printer', '20 pages per minute', '6999.00', 2);

-- --------------------------------------------------------

--
-- Table structure for table `item_categories`
--

CREATE TABLE IF NOT EXISTS `item_categories` (
  `item_cat_id` tinyint(4) NOT NULL COMMENT 'item category identification number.',
  `item_cat_name` varchar(50) NOT NULL COMMENT 'item category name.',
  PRIMARY KEY (`item_cat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `item_categories`
--

INSERT INTO `item_categories` (`item_cat_id`, `item_cat_name`) VALUES
(1, 'clothing'),
(2, 'electronics'),
(3, 'health and fitness'),
(4, 'home and decor');

-- --------------------------------------------------------

--
-- Table structure for table `payment_type`
--

CREATE TABLE IF NOT EXISTS `payment_type` (
  `payment_type_id` tinyint(4) NOT NULL COMMENT 'payment type identification number.',
  `payment_type_name` varchar(50) NOT NULL COMMENT 'payment method name.',
  PRIMARY KEY (`payment_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment_type`
--

INSERT INTO `payment_type` (`payment_type_id`, `payment_type_name`) VALUES
(1, 'credit card'),
(2, 'debit card'),
(3, 'cash');

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

CREATE TABLE IF NOT EXISTS `purchase` (
  `purchase_id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_id` int(11) NOT NULL,
  `items_sold` int(11) NOT NULL,
  `total_cost` decimal(10,2) NOT NULL,
  `payment_type` tinyint(4) NOT NULL,
  `time_of_purchase` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'time of purchase',
  PRIMARY KEY (`purchase_id`),
  KEY `fk_cust_id` (`cust_id`),
  KEY `fk_payment_type` (`payment_type`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=35 ;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`purchase_id`, `cust_id`, `items_sold`, `total_cost`, `payment_type`, `time_of_purchase`) VALUES
(1, 1011342, 2, '2050.00', 3, '2016-01-03 13:31:28'),
(4, 0, 2, '1700.00', 1, '2016-01-03 13:51:18'),
(5, 1011343, 2, '5000.00', 3, '2016-01-04 13:51:16'),
(6, 0, 2, '1700.00', 1, '2016-01-04 14:04:17'),
(7, 1011342, 1, '2500.00', 2, '2016-01-04 14:10:09'),
(8, 0, 1, '850.00', 2, '2016-01-04 17:21:22'),
(9, 0, 1, '850.00', 1, '2016-01-04 18:02:52'),
(10, 1011344, 2, '1700.00', 3, '2016-01-05 16:22:02'),
(11, 0, 3, '7500.00', 3, '2016-01-05 16:33:56'),
(12, 0, 2, '5000.00', 3, '2016-01-05 16:39:38'),
(13, 1011345, 2, '5000.00', 3, '2016-01-06 17:50:37'),
(14, 1011345, 3, '7500.00', 3, '2016-01-06 17:52:46'),
(15, 0, 2, '1700.00', 3, '2016-01-06 17:55:15'),
(16, 1011346, 2, '5000.00', 1, '2016-01-08 15:51:28'),
(17, 0, 1, '850.00', 3, '2016-01-08 15:57:26'),
(18, 1011346, 1, '850.00', 2, '2016-01-08 15:57:41'),
(19, 0, 2, '1700.00', 3, '2016-01-08 17:03:39'),
(20, 1011342, 1, '2500.00', 3, '2016-01-16 12:01:58'),
(21, 1011342, 2, '1700.00', 3, '2016-01-17 12:56:45'),
(22, 1011351, 1, '850.00', 1, '2016-01-17 16:06:12'),
(23, 1011351, 2, '2400.00', 2, '2016-01-18 00:11:34'),
(24, 0, 1, '850.00', 3, '2016-01-18 00:11:54'),
(25, 0, 1, '1200.00', 1, '2016-01-18 00:12:50'),
(26, 1011345, 1, '1200.00', 2, '2016-01-18 11:04:03'),
(27, 0, 1, '2500.00', 2, '2016-01-18 11:14:05'),
(28, 1011344, 1, '2500.00', 1, '2016-01-18 11:52:35'),
(29, 1011345, 2, '48000.00', 1, '2016-01-18 12:31:30'),
(30, 1011353, 4, '8797.00', 3, '2016-01-19 17:37:17'),
(31, 0, 5, '8499.00', 1, '2016-01-19 17:39:39'),
(32, 1011342, 1, '2000.00', 1, '2016-01-19 18:50:24'),
(33, 1011345, 4, '5597.00', 3, '2016-01-20 07:42:17'),
(34, 1011342, 4, '5496.00', 3, '2016-01-21 13:39:31');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `items`
--
ALTER TABLE `items`
  ADD CONSTRAINT `fk_item_cat` FOREIGN KEY (`item_category`) REFERENCES `item_categories` (`item_cat_id`);

--
-- Constraints for table `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `fk_cust_id` FOREIGN KEY (`cust_id`) REFERENCES `customers` (`cust_id`),
  ADD CONSTRAINT `fk_payment_type` FOREIGN KEY (`payment_type`) REFERENCES `payment_type` (`payment_type_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
