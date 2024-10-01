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
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL UNIQUE,
  `user_id` bigint NOT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bird_ibfk_1` (`user_id`),
  CONSTRAINT `bird_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

DROP TABLE IF EXISTS `tournament`;
CREATE TABLE `tournament` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `birds_num` int NOT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `start_date` timestamp NOT NULL,
  `end_date` timestamp DEFAULT NULL,
  `rest_time_per_day` float NOT NULL,
  `is_actived` tinyint(1) NOT NULL,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
);

DROP TABLE IF EXISTS `tournament_detail`;
CREATE TABLE `tournament_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bird_code` varchar(255) NOT NULL,
  `tour_id` bigint NOT NULL,
  `start_point_time` timestamp DEFAULT NULL,
  `point1_time` timestamp DEFAULT NULL,
  `point1_speed` float DEFAULT NULL,
  `point2_time` timestamp DEFAULT NULL,
  `point2_speed` float DEFAULT NULL,
  `point3_time` timestamp DEFAULT NULL,
  `point3_speed` float DEFAULT NULL,
  `point4_time` timestamp DEFAULT NULL,
  `point4_speed` float DEFAULT NULL,
  `point5_time` timestamp DEFAULT NULL,
  `point5_speed` float DEFAULT NULL,
  `end_point_time` timestamp DEFAULT NULL,
  `end_point_speed` float DEFAULT NULL,
  `avg_speed` float DEFAULT NULL,
  `rank_of_bird` bigint DEFAULT NULL,
  `memo` text,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tour_id` (`tour_id`),
  KEY `bird_code` (`bird_code`),
  CONSTRAINT `tournament_detail_ibfk_1` FOREIGN KEY (`tour_id`) REFERENCES `tournament` (`id`),
  CONSTRAINT `tournament_detail_ibfk_2` FOREIGN KEY (`bird_code`) REFERENCES `bird` (`code`)
);

DROP TABLE IF EXISTS `tournament_location`;
CREATE TABLE `tournament_location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tour_id` bigint NOT NULL,
  `start_point_name` varchar(255) NOT NULL,
  `start_point_coor` varchar(255) DEFAULT NULL,
  `point1_name` varchar(255) DEFAULT NULL,
  `point1_coor` varchar(255) DEFAULT NULL,
  `point1_dist` float DEFAULT NULL,
  `point2_name` varchar(255) DEFAULT NULL,
  `point2_coor` varchar(255) DEFAULT NULL,
  `point2_dist` float DEFAULT NULL,
  `point3_name` varchar(255) DEFAULT NULL,
  `point3_coor` varchar(255) DEFAULT NULL,
  `point3_dist` float DEFAULT NULL,
  `point4_name` varchar(255) DEFAULT NULL,
  `point4_coor` varchar(255) DEFAULT NULL,
  `point4_dist` float DEFAULT NULL,
  `point5_name` varchar(255) DEFAULT NULL,
  `point5_coor` varchar(255) DEFAULT NULL,
  `point5_dist` float DEFAULT NULL,
  `end_point_name` varchar(255) NOT NULL,
  `end_point_coor` varchar(255) DEFAULT NULL,
  `end_point_dist` float DEFAULT NULL,
  `created_at` timestamp DEFAULT NOW(),
  `created_by` bigint NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_tour_id` (`tour_id`),
  KEY `tour_id` (`tour_id`),
  CONSTRAINT `tournament_location_ibfk_1` FOREIGN KEY (`tour_id`) REFERENCES `tournament` (`id`)
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
