-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 05, 2024 lúc 10:15 AM
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
-- Cơ sở dữ liệu: `login_selfcare`
--

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

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
