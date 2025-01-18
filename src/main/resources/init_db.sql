DROP DATABASE IF EXISTS clb_bc_q8_tphcm;
CREATE DATABASE clb_bc_q8_tphcm;
USE clb_bc_q8_tphcm;
SET @@global.time_zone = '+07:00';

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `role_id` int NOT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT 0,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

DROP TABLE IF EXISTS `bird`;
CREATE TABLE `bird` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL UNIQUE,
  `description` varchar(255),
  `user_id` bigint NOT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `bird_ibfk_1` (`user_id`),
  CONSTRAINT `bird_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

DROP TABLE IF EXISTS `user_location`;
CREATE TABLE `user_location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(5) NOT NULL,
  `name` varchar(255),
  `user_id` bigint NOT NULL,
  `point_coor` varchar(255) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT 0,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `user_location_ibfk_1` (`user_id`),
  CONSTRAINT `user_location_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

DROP TABLE IF EXISTS `system_location`;
CREATE TABLE `system_location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(5) NOT NULL,
  `name` varchar(255),
  `point_coor` varchar(255) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT 0,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
);

DROP TABLE IF EXISTS `tournament`;
CREATE TABLE `tournament` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `birds_num` int NOT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `start_date_info` timestamp NOT NULL,
  `end_date_info` timestamp NOT NULL,
  `start_date_receive` timestamp NOT NULL,
  `end_date_receive` timestamp NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `is_finished` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
);

DROP TABLE IF EXISTS `tournament_stage`;
CREATE TABLE `tournament_stage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tour_id` bigint NOT NULL,
  `order_no` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `rest_time_per_day` float NOT NULL,
  `start_point_code` varchar(5) NOT NULL,
  `start_point_name` varchar(255) NOT NULL,
  `start_point_coor` varchar(255) NOT NULL,
  `start_time` timestamp DEFAULT NULL,
  `is_actived` tinyint(1) DEFAULT 0,
  `is_finished` tinyint(1) DEFAULT 0,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tour_id` (`tour_id`),
  CONSTRAINT `tournament_stage_ibfk_1` FOREIGN KEY (`tour_id`) REFERENCES `tournament` (`id`)
);

DROP TABLE IF EXISTS `tournament_detail`;
CREATE TABLE `tournament_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `bird_code` varchar(255) NOT NULL,
  `tour_id` bigint NOT NULL,
  `stage_id` bigint NOT NULL,
  `end_point_code` varchar(5) NOT NULL,
  `end_point_coor` varchar(255) DEFAULT NULL,
  `end_point_dist` float DEFAULT NULL,
  `end_point_time` timestamp DEFAULT NULL,
  `end_point_speed` float DEFAULT NULL,
  `end_point_key` varchar(10) DEFAULT NULL,
  `end_point_submit_time` timestamp DEFAULT NULL,
  `memo` text,
  `status` varchar(1) DEFAULT NULL,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tour_id` (`tour_id`),
  KEY `stage_id` (`stage_id`),
  KEY `bird_code` (`bird_code`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `tournament_detail_ibfk_1` FOREIGN KEY (`tour_id`) REFERENCES `tournament` (`id`),
  CONSTRAINT `tournament_detail_ibfk_2` FOREIGN KEY (`stage_id`) REFERENCES `tournament_stage` (`id`),
  CONSTRAINT `tournament_detail_ibfk_3` FOREIGN KEY (`bird_code`) REFERENCES `bird` (`code`),
  CONSTRAINT `tournament_detail_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

DROP TABLE IF EXISTS `tournament_apply`;
CREATE TABLE tournament_apply (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`bird_code` varchar(255) NOT NULL,
	`tour_id` bigint NOT NULL,
    `status_code` varchar(1) NOT NULL,
    `requester_id` bigint NOT NULL,
    `approver_id` bigint,
    `memo` text,
	`created_at` timestamp DEFAULT NOW(),
	`created_by` bigint NOT NULL,
	`updated_at` timestamp DEFAULT NULL,
	`updated_by` bigint DEFAULT NULL,
	PRIMARY KEY (`id`),
	KEY `tour_id` (`tour_id`),
	KEY `bird_code` (`bird_code`),
    KEY `approver_id` (`approver_id`),
    KEY `requester_id` (`requester_id`),
	CONSTRAINT `tournament_apply_ibfk_1` FOREIGN KEY (`tour_id`) REFERENCES `tournament` (`id`),
	CONSTRAINT `tournament_apply_ibfk_2` FOREIGN KEY (`bird_code`) REFERENCES `bird` (`code`),
    CONSTRAINT `tournament_apply_ibfk_3` FOREIGN KEY (`approver_id`) REFERENCES `user` (`id`),
    CONSTRAINT `tournament_apply_ibfk_4` FOREIGN KEY (`requester_id`) REFERENCES `user` (`id`)
);

DROP TABLE IF EXISTS `category`;
CREATE TABLE category (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`is_deleted` bit NOT NULL DEFAULT 0,
	`created_at` timestamp DEFAULT NOW(),
	`created_by` bigint NOT NULL,
	`updated_at` timestamp DEFAULT NULL,
	`updated_by` bigint DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `name` (`name`)
);

DROP TABLE IF EXISTS `post`;
CREATE TABLE post (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`category_id` bigint NOT NULL,
	`img_url` varchar(255),
	`slug` varchar(255) NOT NULL,
	`title` varchar(255) DEFAULT NULL,
	`content` text NOT NULL,
	`is_deleted` bit NOT NULL DEFAULT 0,
	`created_at` timestamp DEFAULT NOW(),
	`created_by` bigint NOT NULL,
	`updated_at` timestamp DEFAULT NULL,
	`updated_by` bigint DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `slug` (`slug`),
	UNIQUE KEY `title` (`title`)
);

DROP TABLE IF EXISTS `contact_info`;
CREATE TABLE contact_info (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`address` varchar(255) NOT NULL,
	`phone1` varchar(255) NOT NULL,
	`name1` varchar(255) NOT NULL,
	`phone2` varchar(255),
	`name2` varchar(255),
	`email` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);
