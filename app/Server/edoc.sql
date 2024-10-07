-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 07, 2024 lúc 01:26 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `edoc`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `academicrank`
--
-- Error reading structure for table edoc.academicrank: #1932 - Table 'edoc.academicrank' doesn't exist in engine
-- Error reading data for table edoc.academicrank: #1064 - You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near 'FROM `edoc`.`academicrank`' at line 1

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin`
--

CREATE TABLE `admin` (
  `aemail` varchar(255) NOT NULL,
  `apassword` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `admin`
--

INSERT INTO `admin` (`aemail`, `apassword`) VALUES
('admin@edoc.com', '123');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `appointment`
--

CREATE TABLE `appointment` (
  `appoid` int(11) NOT NULL,
  `pid` int(10) DEFAULT NULL,
  `hasDone` tinyint(1) DEFAULT NULL,
  `docid` int(11) DEFAULT NULL,
  `scheduledate` date DEFAULT NULL,
  `starttime` time DEFAULT NULL,
  `endtime` time DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `appointment`
--

INSERT INTO `appointment` (`appoid`, `pid`, `hasDone`, `docid`, `scheduledate`, `starttime`, `endtime`) VALUES
(6, 1, 0, 3, '2024-10-05', '14:00:00', '15:00:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chief`
--
-- Error reading structure for table edoc.chief: #1932 - Table 'edoc.chief' doesn't exist in engine
-- Error reading data for table edoc.chief: #1064 - You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near 'FROM `edoc`.`chief`' at line 1

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `clinic`
--
-- Error reading structure for table edoc.clinic: #1932 - Table 'edoc.clinic' doesn't exist in engine
-- Error reading data for table edoc.clinic: #1064 - You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near 'FROM `edoc`.`clinic`' at line 1

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `doctor`
--

CREATE TABLE `doctor` (
  `docid` int(11) NOT NULL,
  `docemail` varchar(255) DEFAULT NULL,
  `docname` varchar(255) DEFAULT NULL,
  `docpassword` varchar(255) DEFAULT NULL,
  `doctel` varchar(15) DEFAULT NULL,
  `specialties` int(2) DEFAULT NULL,
  `chief_id` int(11) DEFAULT NULL,
  `academic_rank` int(11) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `price` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `doctor`
--

INSERT INTO `doctor` (`docid`, `docemail`, `docname`, `docpassword`, `doctel`, `specialties`, `chief_id`, `academic_rank`, `sex`, `price`) VALUES
(3, 'doc1@gmail.com', 'test doctor 1', '123', '123', 1, 1, 2, 'M', 150000),
(4, 'doc2@edoc.com', 'test doctor 2', '123', '123', 2, 1, 4, 'F', 150000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `sender_username` varchar(50) NOT NULL,
  `receiver_username` varchar(50) NOT NULL,
  `message` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `patient`
--

CREATE TABLE `patient` (
  `pid` int(11) NOT NULL,
  `pemail` varchar(255) DEFAULT NULL,
  `pname` varchar(255) DEFAULT NULL,
  `ppassword` varchar(255) DEFAULT NULL,
  `paddress` varchar(255) DEFAULT NULL,
  `pnic` varchar(15) DEFAULT NULL,
  `pdob` date DEFAULT NULL,
  `ptel` varchar(15) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `patient`
--

INSERT INTO `patient` (`pid`, `pemail`, `pname`, `ppassword`, `paddress`, `pnic`, `pdob`, `ptel`) VALUES
(1, 'patient@edoc.com', 'Test Patient', '123', 'Sri Lanka', '0000000000', '2000-01-01', '0120000000'),
(2, 'emhashenudara@gmail.com', 'Hashen Udara', '123', 'Sri Lanka', '0110000000', '2022-06-03', '0700000000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `schedule`
--

CREATE TABLE `schedule` (
  `scheduleid` int(11) NOT NULL,
  `docid` int(11) DEFAULT NULL,
  `scheduledate` date DEFAULT NULL,
  `starttime` time DEFAULT NULL,
  `endtime` time DEFAULT NULL,
  `booked` tinyint(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `schedule`
--

INSERT INTO `schedule` (`scheduleid`, `docid`, `scheduledate`, `starttime`, `endtime`, `booked`) VALUES
(549, NULL, '2024-10-05', '07:00:00', '08:00:00', 0),
(550, NULL, '2024-10-05', '08:00:00', '09:00:00', 0),
(551, NULL, '2024-10-05', '09:00:00', '10:00:00', 0),
(552, NULL, '2024-10-05', '10:00:00', '11:00:00', 0),
(553, NULL, '2024-10-05', '11:00:00', '12:00:00', 0),
(554, NULL, '2024-10-05', '13:00:00', '14:00:00', 0),
(555, 3, '2024-10-05', '14:00:00', '15:00:00', 1),
(556, 3, '2024-10-05', '15:00:00', '16:00:00', 0),
(557, NULL, '2024-10-06', '07:00:00', '08:00:00', 0),
(558, NULL, '2024-10-06', '08:00:00', '09:00:00', 0),
(559, NULL, '2024-10-06', '09:00:00', '10:00:00', 0),
(560, NULL, '2024-10-06', '10:00:00', '11:00:00', 0),
(561, NULL, '2024-10-06', '11:00:00', '12:00:00', 0),
(562, NULL, '2024-10-06', '13:00:00', '14:00:00', 0),
(563, NULL, '2024-10-06', '14:00:00', '15:00:00', 0),
(564, NULL, '2024-10-06', '15:00:00', '16:00:00', 0),
(565, NULL, '2024-10-07', '07:00:00', '08:00:00', 0),
(566, NULL, '2024-10-07', '08:00:00', '09:00:00', 0),
(567, NULL, '2024-10-07', '09:00:00', '10:00:00', 0),
(568, NULL, '2024-10-07', '10:00:00', '11:00:00', 0),
(569, NULL, '2024-10-07', '11:00:00', '12:00:00', 0),
(570, NULL, '2024-10-07', '13:00:00', '14:00:00', 0),
(571, NULL, '2024-10-07', '14:00:00', '15:00:00', 0),
(572, NULL, '2024-10-07', '15:00:00', '16:00:00', 0),
(573, NULL, '2024-10-08', '07:00:00', '08:00:00', 0),
(574, NULL, '2024-10-08', '08:00:00', '09:00:00', 0),
(575, NULL, '2024-10-08', '09:00:00', '10:00:00', 0),
(576, NULL, '2024-10-08', '10:00:00', '11:00:00', 0),
(577, NULL, '2024-10-08', '11:00:00', '12:00:00', 0),
(578, NULL, '2024-10-08', '13:00:00', '14:00:00', 0),
(579, NULL, '2024-10-08', '14:00:00', '15:00:00', 0),
(580, NULL, '2024-10-08', '15:00:00', '16:00:00', 0),
(581, NULL, '2024-10-09', '07:00:00', '08:00:00', 0),
(582, NULL, '2024-10-09', '08:00:00', '09:00:00', 0),
(583, NULL, '2024-10-09', '09:00:00', '10:00:00', 0),
(584, NULL, '2024-10-09', '10:00:00', '11:00:00', 0),
(585, NULL, '2024-10-09', '11:00:00', '12:00:00', 0),
(586, NULL, '2024-10-09', '13:00:00', '14:00:00', 0),
(587, NULL, '2024-10-09', '14:00:00', '15:00:00', 0),
(588, NULL, '2024-10-09', '15:00:00', '16:00:00', 0),
(589, NULL, '2024-10-10', '07:00:00', '08:00:00', 0),
(590, NULL, '2024-10-10', '08:00:00', '09:00:00', 0),
(591, NULL, '2024-10-10', '09:00:00', '10:00:00', 0),
(592, NULL, '2024-10-10', '10:00:00', '11:00:00', 0),
(593, NULL, '2024-10-10', '11:00:00', '12:00:00', 0),
(594, NULL, '2024-10-10', '13:00:00', '14:00:00', 0),
(595, NULL, '2024-10-10', '14:00:00', '15:00:00', 0),
(596, NULL, '2024-10-10', '15:00:00', '16:00:00', 0),
(605, NULL, '2024-10-05', '07:00:00', '08:00:00', 0),
(606, NULL, '2024-10-05', '08:00:00', '09:00:00', 0),
(607, NULL, '2024-10-05', '09:00:00', '10:00:00', 0),
(608, NULL, '2024-10-05', '10:00:00', '11:00:00', 0),
(609, NULL, '2024-10-05', '11:00:00', '12:00:00', 0),
(610, NULL, '2024-10-05', '13:00:00', '14:00:00', 0),
(611, NULL, '2024-10-05', '14:00:00', '15:00:00', 0),
(612, NULL, '2024-10-05', '15:00:00', '16:00:00', 0),
(613, NULL, '2024-10-06', '07:00:00', '08:00:00', 0),
(614, NULL, '2024-10-06', '08:00:00', '09:00:00', 0),
(615, NULL, '2024-10-06', '09:00:00', '10:00:00', 0),
(616, NULL, '2024-10-06', '10:00:00', '11:00:00', 0),
(617, NULL, '2024-10-06', '11:00:00', '12:00:00', 0),
(618, NULL, '2024-10-06', '13:00:00', '14:00:00', 0),
(619, NULL, '2024-10-06', '14:00:00', '15:00:00', 0),
(620, NULL, '2024-10-06', '15:00:00', '16:00:00', 0),
(621, NULL, '2024-10-07', '07:00:00', '08:00:00', 0),
(622, NULL, '2024-10-07', '08:00:00', '09:00:00', 0),
(623, NULL, '2024-10-07', '09:00:00', '10:00:00', 0),
(624, NULL, '2024-10-07', '10:00:00', '11:00:00', 0),
(625, NULL, '2024-10-07', '11:00:00', '12:00:00', 0),
(626, NULL, '2024-10-07', '13:00:00', '14:00:00', 0),
(627, NULL, '2024-10-07', '14:00:00', '15:00:00', 0),
(628, NULL, '2024-10-07', '15:00:00', '16:00:00', 0),
(629, NULL, '2024-10-08', '07:00:00', '08:00:00', 0),
(630, NULL, '2024-10-08', '08:00:00', '09:00:00', 0),
(631, NULL, '2024-10-08', '09:00:00', '10:00:00', 0),
(632, NULL, '2024-10-08', '10:00:00', '11:00:00', 0),
(633, NULL, '2024-10-08', '11:00:00', '12:00:00', 0),
(634, NULL, '2024-10-08', '13:00:00', '14:00:00', 0),
(635, NULL, '2024-10-08', '14:00:00', '15:00:00', 0),
(636, NULL, '2024-10-08', '15:00:00', '16:00:00', 0),
(637, NULL, '2024-10-09', '07:00:00', '08:00:00', 0),
(638, NULL, '2024-10-09', '08:00:00', '09:00:00', 0),
(639, NULL, '2024-10-09', '09:00:00', '10:00:00', 0),
(640, NULL, '2024-10-09', '10:00:00', '11:00:00', 0),
(641, NULL, '2024-10-09', '11:00:00', '12:00:00', 0),
(642, NULL, '2024-10-09', '13:00:00', '14:00:00', 0),
(643, NULL, '2024-10-09', '14:00:00', '15:00:00', 0),
(644, NULL, '2024-10-09', '15:00:00', '16:00:00', 0),
(645, NULL, '2024-10-10', '07:00:00', '08:00:00', 0),
(646, NULL, '2024-10-10', '08:00:00', '09:00:00', 0),
(647, NULL, '2024-10-10', '09:00:00', '10:00:00', 0),
(648, NULL, '2024-10-10', '10:00:00', '11:00:00', 0),
(649, NULL, '2024-10-10', '11:00:00', '12:00:00', 0),
(650, NULL, '2024-10-10', '13:00:00', '14:00:00', 0),
(651, NULL, '2024-10-10', '14:00:00', '15:00:00', 0),
(652, NULL, '2024-10-10', '15:00:00', '16:00:00', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `specialties`
--

CREATE TABLE `specialties` (
  `id` int(2) NOT NULL,
  `sname` varchar(50) CHARACTER SET utf16 COLLATE utf16_vietnamese_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `specialties`
--

INSERT INTO `specialties` (`id`, `sname`) VALUES
(1, 'Khám chức năng hô hấp'),
(2, 'Khám da liễu'),
(3, 'Khám điều trị vết thương'),
(4, 'Khám hậu môn trực tràng'),
(5, 'Khám mắt'),
(6, 'Khám nhi'),
(7, 'Khám nội soi tai mũi họng'),
(8, 'Khám nội tiết'),
(9, 'Khám  phụ khoa'),
(10, 'Khám thai'),
(11, 'Khám thần kinh'),
(12, 'Khám tiết niệu'),
(13, 'Khám tiêu hoa - gan mật'),
(14, 'Khám tim mạch'),
(15, 'Khám tổng quát'),
(16, 'Khám viêm gan'),
(17, 'Khám xương khớp'),
(18, 'Lồng ngực - Mạch máu - Bướu cổ'),
(19, 'Tạo hình thẩm mỹ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `utype` enum('doctor','patient','admin') NOT NULL,
  `fullname` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`email`, `password`, `utype`, `fullname`, `username`, `phone`) VALUES
('admin@edoc.com', '123', 'admin', '', '', ''),
('doctor@edoc.com', '123', 'doctor', '', '', ''),
('patient@edoc.com', '123', 'patient', '', '', ''),
('emhashenudara@gmail.com', '123', 'patient', '', '', ''),
('bnh@gmail;.com', '$2y$10$i9Q0L096kA5AUjjnf8ZY/u5GNigq2n4uWGBXEJzyVhw6hRvLmgb8G', 'patient', 'Benh Nhan Ngheo', 'bnn', '012345565'),
('leha@edoc.com', '$2y$10$Xv5rJ.QqvWQ7YL1oJMJXs.HYMBcZNlrjFDuY7nGSEwiZqsxiDitJS', 'patient', 'Le Phi Ha', 'lephiha', '0392405600'),
('admin@gmail.com', '$2y$10$h1STM14PRUAZe577cFWihOMD3X74.vPdz9YUt4crOhfya.jf.M06m', 'admin', 'ADMIN', 'admin', '012345678'),
('drxuan@edoc.com', '$2y$10$wYoDOc5P.3Fk6pec.psG9esolQwG3cTqzonKG6UBHrvJdw8l80MYe', 'doctor', 'Doctor Xuan', 'xuantocdo', '0584649100');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `webuser`
--

CREATE TABLE `webuser` (
  `email` varchar(255) NOT NULL,
  `usertype` char(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `webuser`
--

INSERT INTO `webuser` (`email`, `usertype`) VALUES
('admin@edoc.com', 'a'),
('doctor@edoc.com', 'd'),
('patient@edoc.com', 'p'),
('emhashenudara@gmail.com', 'p');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`aemail`);

--
-- Chỉ mục cho bảng `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`appoid`),
  ADD KEY `pid` (`pid`);

--
-- Chỉ mục cho bảng `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`docid`),
  ADD KEY `specialties` (`specialties`);

--
-- Chỉ mục cho bảng `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`pid`);

--
-- Chỉ mục cho bảng `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`scheduleid`);

--
-- Chỉ mục cho bảng `specialties`
--
ALTER TABLE `specialties`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`email`);

--
-- Chỉ mục cho bảng `webuser`
--
ALTER TABLE `webuser`
  ADD PRIMARY KEY (`email`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `appointment`
--
ALTER TABLE `appointment`
  MODIFY `appoid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `doctor`
--
ALTER TABLE `doctor`
  MODIFY `docid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `patient`
--
ALTER TABLE `patient`
  MODIFY `pid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `schedule`
--
ALTER TABLE `schedule`
  MODIFY `scheduleid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=765;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
