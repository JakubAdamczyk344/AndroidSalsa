-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 09 Gru 2016, 20:03
-- Wersja serwera: 10.1.16-MariaDB
-- Wersja PHP: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `bazadanychlistview`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `events`
--

CREATE TABLE `events` (
  `id` int(11) NOT NULL,
  `day` date NOT NULL,
  `hour` time NOT NULL,
  `city` text NOT NULL,
  `cityBlock` text NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Zrzut danych tabeli `events`
--

INSERT INTO `events` (`id`, `day`, `hour`, `city`, `cityBlock`, `description`) VALUES
(1, '2016-12-14', '05:32:24', 'Gdańsk', 'Wrzeszcz', 'Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawa Najlepsza zabawaNajlepsza zabawaNajlepsza zabawaNajlepsza zabawaNajlepsza zabawaNajlepsza zabawaNajlepsza zabawaNajlepsza zabawaNajlepsza zabawa'),
(2, '2016-12-12', '09:35:00', 'Gdańsk', 'Osowa', 'NanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNanaNana');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ludzie`
--

CREATE TABLE `ludzie` (
  `ID` int(11) NOT NULL,
  `Imię` text NOT NULL,
  `Nazwisko` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Zrzut danych tabeli `ludzie`
--

INSERT INTO `ludzie` (`ID`, `Imię`, `Nazwisko`) VALUES
(1, 'Jakub', 'Adamczyk'),
(2, 'Piotr', 'Bzowski'),
(3, 'Szymon', 'Hełmecki');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `events`
--
ALTER TABLE `events`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
