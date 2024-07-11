-- --------------------------------------------------------
-- 호스트:                          140.245.65.2
-- 서버 버전:                        10.3.39-MariaDB-0ubuntu0.20.04.2 - Ubuntu 20.04
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 테이블 cpt2024pgu.coin_info_list 구조 내보내기
CREATE TABLE IF NOT EXISTS `coin_info_list` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `ticker` varchar(50) DEFAULT NULL COMMENT '코인코드',
  `nm_kr` varchar(50) DEFAULT NULL COMMENT '한글명',
  `nm_en` varchar(50) DEFAULT NULL COMMENT '영문명',
  `keyword_arr` tinytext DEFAULT NULL COMMENT '필터링검색어',
  `except_arr` tinytext DEFAULT NULL COMMENT '제외할검색어',
  `is_collection` char(1) NOT NULL DEFAULT 'N' COMMENT '가격정보수집대상',
  `is_alarm` char(1) DEFAULT 'N' COMMENT '텔레그램알림대상',
  `exchange_nm` varchar(50) DEFAULT NULL COMMENT '거래소',
  `telegram_bot_token` varchar(50) DEFAULT NULL COMMENT '텔레그램 봇 api key',
  `telegram_ch_chat_id` varchar(50) DEFAULT NULL COMMENT '텔레그램 채널아이디',
  `my_chat_id` varchar(50) DEFAULT NULL COMMENT '나와봇의대화채널아이디',
  `reg_dt` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일시',
  PRIMARY KEY (`idx`),
  KEY `coin_cd` (`ticker`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='업비트에서 거래되는 코인 목록';

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 cpt2024pgu.coin_price_hist 구조 내보내기
CREATE TABLE IF NOT EXISTS `coin_price_hist` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `ticker` varchar(50) DEFAULT NULL COMMENT '코인 티커',
  `trade_price` varchar(50) DEFAULT NULL COMMENT '현재거래가',
  `prev_closing_price` varchar(50) DEFAULT NULL COMMENT '전일종가',
  `change` varchar(50) DEFAULT NULL COMMENT '시세변화',
  `reg_dt` datetime NOT NULL DEFAULT current_timestamp() COMMENT '등록일시',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='코인별 가격 이력';

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 cpt2024pgu.cpt_cont_hist 구조 내보내기
CREATE TABLE IF NOT EXISTS `cpt_cont_hist` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `ticker` varchar(50) DEFAULT NULL,
  `cont_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '게시글번호',
  `cont_title` text CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '게시글 제목',
  `cont_reg_dt` datetime NOT NULL DEFAULT current_timestamp() COMMENT '게시글 등록일',
  PRIMARY KEY (`cont_no`) USING BTREE,
  KEY `cont_reg_dt` (`cont_reg_dt`) USING BTREE,
  KEY `idx` (`idx`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='코인판 자유게시판 게시글 이력';

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 cpt2024pgu.cpt_test_tb 구조 내보내기
CREATE TABLE IF NOT EXISTS `cpt_test_tb` (
  `t_idx` int(11) NOT NULL AUTO_INCREMENT,
  `tr_nm` varchar(50) NOT NULL DEFAULT '0',
  `tr_desc` varchar(50) NOT NULL DEFAULT '0',
  `reg_dt` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`t_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='테스트 테이블';

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
