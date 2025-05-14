-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 30, 2024 at 05:18 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rental`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `bikes`
--

CREATE TABLE `bikes` (
  `id_bike` int(11) NOT NULL,
  `model` varchar(30) NOT NULL,
  `description` varchar(300) NOT NULL,
  `price` double NOT NULL,
  `picture` varchar(100) NOT NULL,
  `bike_condition` int(1) NOT NULL,
  `id_type` int(11) NOT NULL,
  `is_active` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `bikes`
--

INSERT INTO `bikes` (`id_bike`, `model`, `description`, `price`, `picture`, `bike_condition`, `id_type`, `is_active`) VALUES
(3, 'TCX-05', 'Rower TCX-05, wykonany z wysokiej jakości materiałów, idealny dla miłośników aktywnego spędzania wolnego czasu. Stalowa rama, widoczny amortyzator i duże koło tylne zapewniają komfort i stabilność.', 25.99, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 1, 1, 1),
(4, 'AVN-01', 'Ten rower jest specjalistycznym, profesjonalnym pojazdem, zaprojektowanym dla wytrzymałych i ambitnych kolarzy. Jego stalowa rama jest osadzona w tylnym osiowym ustawieniu, co umożliwia szybkie rozpędzanie i stabilne hamowanie', 15.95, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 1, 2, 1),
(5, 'AOP-59', 'Ten rower jest specjalistycznym, profesjonalnym pojazdem, zaprojektowanym dla wytrzymałych i ambitnych kolarzy. Jego stalowa rama jest osadzona w tylnym osiowym ustawieniu, co umożliwia szybkie rozpędzanie i stabilne hamowanie', 15.95, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 1, 2, 1),
(107, 'OPE-97', 'Ten rower jest specjalistycznym, profesjonalnym pojazdem, zaprojektowanym dla wytrzymałych i ambitnych kolarzy. Jego stalowa rama jest osadzona w tylnym osiowym ustawieniu, co umożliwia szybkie rozpędzanie i stabilne hamowanie', 98.55, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 1, 4, 1),
(108, 'ABC-45', 'Ten rower jest specjalistycznym, profesjonalnym pojazdem, zaprojektowanym dla wytrzymałych i ambitnych kolarzy. Jego stalowa rama jest osadzona w tylnym osiowym ustawieniu, co umożliwia szybkie rozpędzanie i stabilne hamowanie', 23.85, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 1, 3, 1),
(109, 'TYR-36', 'To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis ', 15.9, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 1, 3, 1),
(110, 'QWE-54', 'To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis ', 15.75, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 1, 3, 1),
(111, 'OWE-72', 'To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis ', 25.5, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 1, 4, 1),
(112, 'KOM-45', 'To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis ', 45.98, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 1, 1, 1),
(113, 'NAP-90', 'To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis ', 98.55, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 1, 4, 0),
(115, 'TPXYT-09', 'To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis To jest opis ', 15.9, 'https://cdn.pixabay.com/photo/2015/05/29/19/18/bicycle-789648_1280.jpg', 0, 1, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rentals`
--

CREATE TABLE `rentals` (
  `id_rental` int(11) NOT NULL,
  `id_bike` int(11) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `date_start` datetime DEFAULT NULL,
  `date_end` datetime DEFAULT NULL,
  `accepted` int(1) DEFAULT NULL,
  `returned_in_term` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Dumping data for table `rentals`
--

INSERT INTO `rentals` (`id_rental`, `id_bike`, `id_user`, `date_start`, `date_end`, `accepted`, `returned_in_term`) VALUES
(7, 115, 56, '2024-12-28 20:13:39', '2024-12-30 20:13:39', 1, 0),
(8, 111, 57, '2024-12-10 19:13:39', '2024-12-28 19:13:39', 1, 1),
(9, 112, 57, '2024-12-10 19:13:39', '2024-12-28 19:13:39', 1, NULL),
(10, 113, 57, '2024-12-10 19:13:39', '2024-12-28 19:13:39', NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `roles`
--

CREATE TABLE `roles` (
  `id_role` int(11) NOT NULL,
  `role` varchar(30) NOT NULL,
  `is_active` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id_role`, `role`, `is_active`) VALUES
(5, 'admin', 1),
(6, 'worker', 1),
(8, 'user', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `types_of_bikes`
--

CREATE TABLE `types_of_bikes` (
  `id_type` int(11) NOT NULL,
  `type` varchar(30) NOT NULL,
  `is_active` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `types_of_bikes`
--

INSERT INTO `types_of_bikes` (`id_type`, `type`, `is_active`) VALUES
(1, 'Górski', 1),
(2, 'Miejski', 1),
(3, 'Szosowy', 1),
(4, 'BMX', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `e-mail` varchar(30) NOT NULL,
  `login` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `when_modified` datetime NOT NULL,
  `who_modified` int(11) DEFAULT NULL,
  `is_active` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `first_name`, `surname`, `e-mail`, `login`, `password`, `when_modified`, `who_modified`, `is_active`) VALUES
(56, 'Imiee', 'Nazwisko', 'Imiee@gmail.com', 'szef123', 'Haslo123', '2024-06-04 01:19:26', 56, 1),
(57, 'Kacper', 'Kacprowski', 'Kacper@Kacper.com', 'login', 'haslo', '2024-12-26 23:21:21', 56, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_role`
--

CREATE TABLE `user_role` (
  `id_user_role` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_role` int(11) NOT NULL,
  `active_since` datetime DEFAULT NULL,
  `active_until` datetime DEFAULT NULL,
  `is_active` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`id_user_role`, `id_user`, `id_role`, `active_since`, `active_until`, `is_active`) VALUES
(2, 56, 5, '2024-06-04 01:20:28', NULL, 1),
(9, 57, 6, '2024-12-29 14:35:45', NULL, 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `bikes`
--
ALTER TABLE `bikes`
  ADD PRIMARY KEY (`id_bike`),
  ADD KEY `id_type` (`id_type`);

--
-- Indeksy dla tabeli `rentals`
--
ALTER TABLE `rentals`
  ADD PRIMARY KEY (`id_rental`),
  ADD KEY `id_bike` (`id_bike`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeksy dla tabeli `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id_role`);

--
-- Indeksy dla tabeli `types_of_bikes`
--
ALTER TABLE `types_of_bikes`
  ADD PRIMARY KEY (`id_type`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD KEY `who_modified` (`who_modified`);

--
-- Indeksy dla tabeli `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id_user_role`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_role` (`id_role`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bikes`
--
ALTER TABLE `bikes`
  MODIFY `id_bike` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;

--
-- AUTO_INCREMENT for table `rentals`
--
ALTER TABLE `rentals`
  MODIFY `id_rental` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id_role` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `types_of_bikes`
--
ALTER TABLE `types_of_bikes`
  MODIFY `id_type` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id_user_role` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bikes`
--
ALTER TABLE `bikes`
  ADD CONSTRAINT `bikes_ibfk_1` FOREIGN KEY (`id_type`) REFERENCES `types_of_bikes` (`id_type`);

--
-- Constraints for table `rentals`
--
ALTER TABLE `rentals`
  ADD CONSTRAINT `rentals_ibfk_1` FOREIGN KEY (`id_bike`) REFERENCES `bikes` (`id_bike`),
  ADD CONSTRAINT `rentals_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`who_modified`) REFERENCES `users` (`id_user`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`id_role`) REFERENCES `roles` (`id_role`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
