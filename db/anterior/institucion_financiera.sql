-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-01-2021 a las 17:16:27
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `institucion_financiera`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activobaja`
--

CREATE TABLE `activobaja` (
  `id` int(11) NOT NULL,
  `idactivo` int(11) NOT NULL,
  `motivo` varchar(300) COLLATE utf8_spanish_ci NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activofijo`
--

CREATE TABLE `activofijo` (
  `id` int(11) NOT NULL,
  `idsucursal` int(11) NOT NULL,
  `iddepartamento` int(11) NOT NULL,
  `idtipo` int(11) NOT NULL,
  `nombre` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `descripcion` varchar(300) COLLATE utf8_spanish_ci NOT NULL,
  `procedencia` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `fechaadquisicion` date NOT NULL,
  `vidautil` int(11) DEFAULT NULL,
  `uso` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `activofijo`
--

INSERT INTO `activofijo` (`id`, `idsucursal`, `iddepartamento`, `idtipo`, `nombre`, `descripcion`, `procedencia`, `precio`, `fechaadquisicion`, `vidautil`, `uso`) VALUES
(9, 8, 8, 7, 'Vehiculo 2020', 'Mazda ', 'Nuevo', '25000.00', '2021-01-17', 5, 1),
(10, 6, 8, 10, 'Computadora portatil', '- 1GB de ram', 'Usado', '1550.00', '2017-01-14', 4, 0),
(12, 6, 11, 11, 'Software', 'Para llevar el control de ventas', 'Nuevo', '19803.30', '2010-01-16', 10, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bgeneral`
--

CREATE TABLE `bgeneral` (
  `id` int(11) NOT NULL,
  `idcjuridico` int(11) NOT NULL,
  `cuenta` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `monto` double(10,2) NOT NULL,
  `fecha` int(11) NOT NULL,
  `clasificacion` varchar(100) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `bgeneral`
--

INSERT INTO `bgeneral` (`id`, `idcjuridico`, `cuenta`, `monto`, `fecha`, `clasificacion`) VALUES
(22, 11, '100', 10.00, 2021, 'Activo corriente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carterageneral`
--

CREATE TABLE `carterageneral` (
  `id` int(11) NOT NULL,
  `idasesor` int(11) NOT NULL,
  `idcartera` int(11) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `carterageneral`
--

INSERT INTO `carterageneral` (`id`, `idasesor`, `idcartera`, `estado`) VALUES
(45, 54, 8, 0),
(46, 49, 6, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cjuridico`
--

CREATE TABLE `cjuridico` (
  `id` int(11) NOT NULL,
  `codigo` varchar(8) COLLATE utf8_spanish_ci NOT NULL,
  `idrepresentate` int(11) NOT NULL,
  `empresa` varchar(300) COLLATE utf8_spanish_ci NOT NULL,
  `direccion` varchar(300) COLLATE utf8_spanish_ci NOT NULL,
  `telefono` varchar(9) COLLATE utf8_spanish_ci NOT NULL,
  `clasificacion` varchar(5) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `cjuridico`
--

INSERT INTO `cjuridico` (`id`, `codigo`, `idrepresentate`, `empresa`, `direccion`, `telefono`, `clasificacion`) VALUES
(9, 'JUR00001', 47, 'BIC S.A DE C.V', 'No se', '7458-8956', NULL),
(10, 'JUR00010', 50, 'EL SOL S.A DE C.V', 'av. # 14, san martin, san salvador', '2225-7845', NULL),
(11, 'JUR00010', 53, 'PIZZA HUT S.A DE C.V', 'cojutepeque, cuscatlan', '2222-2255', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnatural`
--

CREATE TABLE `cnatural` (
  `id` int(11) NOT NULL,
  `codigo` varchar(8) COLLATE utf8_spanish_ci NOT NULL,
  `idpersona` int(11) NOT NULL,
  `lugartrabajo` varchar(300) COLLATE utf8_spanish_ci NOT NULL,
  `clasificacion` varchar(5) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tipo` varchar(25) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `cnatural`
--

INSERT INTO `cnatural` (`id`, `codigo`, `idpersona`, `lugartrabajo`, `clasificacion`, `tipo`) VALUES
(19, 'CNA00001', 46, 'No se', NULL, 'Natural'),
(20, 'FIA00001', 48, 'No se', NULL, 'Fiador'),
(21, 'CNA00020', 51, 'Farmacias economicas', NULL, 'Natural'),
(22, 'CNA00020', 52, 'DIANA S.A DE C.V', NULL, 'Natural');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contrato`
--

CREATE TABLE `contrato` (
  `id` int(11) NOT NULL,
  `idprestamo` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `imagen` varchar(300) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE `departamento` (
  `id` int(11) NOT NULL,
  `nombre` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `codigo` varchar(5) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `departamento`
--

INSERT INTO `departamento` (`id`, `nombre`, `codigo`) VALUES
(8, 'Mercadeo', 'DP001'),
(9, 'Administracion', 'DP009'),
(10, 'Compras', 'DP009'),
(11, 'Ventas', 'DP011'),
(12, 'Publicidad', 'DP012'),
(13, 'Recursos Humanos', 'DP013');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallecartera`
--

CREATE TABLE `detallecartera` (
  `id` int(11) NOT NULL,
  `idcarterag` int(11) NOT NULL,
  `idcliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `detallecartera`
--

INSERT INTO `detallecartera` (`id`, `idcarterag`, `idcliente`) VALUES
(56, 45, 52),
(57, 45, 53),
(58, 45, 50),
(59, 46, 47);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallefiador`
--

CREATE TABLE `detallefiador` (
  `id` int(11) NOT NULL,
  `idprestamo` int(11) NOT NULL,
  `idfiador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallehipoteca`
--

CREATE TABLE `detallehipoteca` (
  `id` int(11) NOT NULL,
  `idprestamo` int(11) NOT NULL,
  `idhipoteca` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `disponibilidad`
--

CREATE TABLE `disponibilidad` (
  `id` int(11) NOT NULL,
  `idnatural` int(11) DEFAULT NULL,
  `idfiador` int(11) DEFAULT NULL,
  `operacion` varchar(300) COLLATE utf8_spanish_ci NOT NULL,
  `monto` double(10,2) NOT NULL,
  `tipo` varchar(25) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `disponibilidad`
--

INSERT INTO `disponibilidad` (`id`, `idnatural`, `idfiador`, `operacion`, `monto`, `tipo`) VALUES
(155, 22, NULL, 'Remesas', 1500.00, 'Ingreso');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eresultado`
--

CREATE TABLE `eresultado` (
  `id` int(11) NOT NULL,
  `idcjuridico` int(11) NOT NULL,
  `cuenta` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `monto` double(10,2) NOT NULL,
  `fecha` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `eresultado`
--

INSERT INTO `eresultado` (`id`, `idcjuridico`, `cuenta`, `monto`, `fecha`) VALUES
(20, 11, '10', 10.00, 2021);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hipoteca`
--

CREATE TABLE `hipoteca` (
  `id` int(11) NOT NULL,
  `nombre` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `descripcion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `monto` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `id` int(11) NOT NULL,
  `dui` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `apellido` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `sexo` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `telefono` varchar(9) COLLATE utf8_spanish_ci NOT NULL,
  `direccion` varchar(300) COLLATE utf8_spanish_ci NOT NULL,
  `estadocivil` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `fechanacimiento` date NOT NULL,
  `tipo` varchar(20) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id`, `dui`, `nombre`, `apellido`, `sexo`, `telefono`, `direccion`, `estadocivil`, `fechanacimiento`, `tipo`) VALUES
(46, '12345678-9', 'Kevin Alberto', 'Sanchez Ventura', 'Masculino', '7485-8956', 'No se', 'Soltero/a', '2000-01-14', 'Natural'),
(47, '12345678-9', 'Rosa Maria', 'Garcia Beltran', 'Femenino', '7485-8956', 'No se', 'Casado/a', '1975-01-20', 'Juridico'),
(48, '74589548-8', 'Ashly Tatiana', 'Sanchez Diaz', 'Femenino', '7485-8965', 'No se', 'Soltero/a', '1998-01-14', 'Fiador'),
(49, '74859658-7', 'Jose Saul', 'Hernandez Vasquez', 'Femenino', '7458-7845', 'No se', 'Soltero/a', '1998-01-14', 'Empleado'),
(50, '74589658-1', 'Lucia', 'Sanchez Ventura', 'Femenino', '7485-8956', 'San pedro perulapan', 'Casado/a', '2021-01-18', 'Juridico'),
(51, '74857412-5', 'Ana Carolina', 'Lopez Beltran', 'Femenino', '7485-8956', 'san vicente, san vicente', 'Soltero/a', '1998-01-18', 'Natural'),
(52, 'Ana Marina', 'Ana Marina', 'Caseres Beltran', 'Femenino', '7458-8596', 'San pedro perulapan', 'Casado/a', '1975-01-18', 'Natural'),
(53, '12458546-7', 'Luis Eduardo', 'Lopez Ventura', 'Masculino', '7458-8956', 'cojutepeque, cuscatlan', 'Divorciado/a', '1998-01-18', 'Juridico'),
(54, '74859658-0', 'Andrea Tatiana', 'Beltran', 'Femenino', '7485-8956', 'No se', 'Casado/a', '2000-01-19', 'Empleado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamo`
--

CREATE TABLE `prestamo` (
  `id` int(11) NOT NULL,
  `idcliente` int(11) NOT NULL,
  `idtipoprestamo` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `monto` double(10,2) NOT NULL,
  `numcuotas` int(11) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursal`
--

CREATE TABLE `sucursal` (
  `id` int(11) NOT NULL,
  `nombre` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `codigo` varchar(5) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `sucursal`
--

INSERT INTO `sucursal` (`id`, `nombre`, `codigo`) VALUES
(6, 'Tepetitan', 'SC001'),
(7, 'San vicente', 'SC007'),
(8, 'Tecoluca', 'SC008');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoactivo`
--

CREATE TABLE `tipoactivo` (
  `id` int(11) NOT NULL,
  `nombre` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `codigo` varchar(5) COLLATE utf8_spanish_ci NOT NULL,
  `activo` varchar(20) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tipoactivo`
--

INSERT INTO `tipoactivo` (`id`, `nombre`, `codigo`, `activo`) VALUES
(7, 'Vehiculos', 'TP001', 'Tangible'),
(8, 'Edificaciones', 'TP008', 'Tangible'),
(9, 'Maquinaria', 'TP008', 'Tangible'),
(10, 'Otros Bienes Muebles', 'TP010', 'Tangible'),
(11, 'Programa informatico', 'TP011', 'Intangible');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoprestamo`
--

CREATE TABLE `tipoprestamo` (
  `id` int(11) NOT NULL,
  `tipo` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `porcentaje` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tipoprestamo`
--

INSERT INTO `tipoprestamo` (`id`, `tipo`, `porcentaje`) VALUES
(14, 'Agricola', 10.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `idempleado` int(11) NOT NULL,
  `usuario` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `clave` varchar(300) COLLATE utf8_spanish_ci NOT NULL,
  `correo` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `nivel` int(11) NOT NULL,
  `activo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `idempleado`, `usuario`, `clave`, `correo`, `nivel`, `activo`) VALUES
(3, 49, 'jvasquez', '25d55ad283aa400af464c76d713c07ad', 'jvasquez@gmail.com', 1, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `activobaja`
--
ALTER TABLE `activobaja`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idactivo_baja` (`idactivo`);

--
-- Indices de la tabla `activofijo`
--
ALTER TABLE `activofijo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_iddepartamento_activo` (`iddepartamento`),
  ADD KEY `fk_idtipo_activo` (`idtipo`),
  ADD KEY `fk_idsucursal_activo` (`idsucursal`);

--
-- Indices de la tabla `bgeneral`
--
ALTER TABLE `bgeneral`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idcj_bg` (`idcjuridico`);

--
-- Indices de la tabla `carterageneral`
--
ALTER TABLE `carterageneral`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idcartera_sucursal` (`idcartera`),
  ADD KEY `fk_idasesor_cartera` (`idasesor`);

--
-- Indices de la tabla `cjuridico`
--
ALTER TABLE `cjuridico`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idpersona_juridico` (`idrepresentate`);

--
-- Indices de la tabla `cnatural`
--
ALTER TABLE `cnatural`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idpersona_cliente` (`idpersona`);

--
-- Indices de la tabla `contrato`
--
ALTER TABLE `contrato`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idprestamo_contrato` (`idprestamo`);

--
-- Indices de la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detallecartera`
--
ALTER TABLE `detallecartera`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idcartera_detalleg` (`idcarterag`),
  ADD KEY `fk_idcliente_detalleg` (`idcliente`);

--
-- Indices de la tabla `detallefiador`
--
ALTER TABLE `detallefiador`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idprestamo_detalle` (`idprestamo`),
  ADD KEY `fk_idnatural_detalle` (`idfiador`);

--
-- Indices de la tabla `detallehipoteca`
--
ALTER TABLE `detallehipoteca`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idpres_hipo` (`idprestamo`),
  ADD KEY `fk_idhipoteca_detalle` (`idhipoteca`);

--
-- Indices de la tabla `disponibilidad`
--
ALTER TABLE `disponibilidad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idper_natural` (`idnatural`),
  ADD KEY `fk_idper_fiador` (`idfiador`);

--
-- Indices de la tabla `eresultado`
--
ALTER TABLE `eresultado`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idc_ee` (`idcjuridico`);

--
-- Indices de la tabla `hipoteca`
--
ALTER TABLE `hipoteca`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idcliente_prestamo` (`idcliente`),
  ADD KEY `fk_idtipopr_prestamo` (`idtipoprestamo`);

--
-- Indices de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipoactivo`
--
ALTER TABLE `tipoactivo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipoprestamo`
--
ALTER TABLE `tipoprestamo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idempleado` (`idempleado`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `activobaja`
--
ALTER TABLE `activobaja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `activofijo`
--
ALTER TABLE `activofijo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `bgeneral`
--
ALTER TABLE `bgeneral`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `carterageneral`
--
ALTER TABLE `carterageneral`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT de la tabla `cjuridico`
--
ALTER TABLE `cjuridico`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `cnatural`
--
ALTER TABLE `cnatural`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `contrato`
--
ALTER TABLE `contrato`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `departamento`
--
ALTER TABLE `departamento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `detallecartera`
--
ALTER TABLE `detallecartera`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT de la tabla `detallefiador`
--
ALTER TABLE `detallefiador`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detallehipoteca`
--
ALTER TABLE `detallehipoteca`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `disponibilidad`
--
ALTER TABLE `disponibilidad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=156;

--
-- AUTO_INCREMENT de la tabla `eresultado`
--
ALTER TABLE `eresultado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `hipoteca`
--
ALTER TABLE `hipoteca`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `tipoactivo`
--
ALTER TABLE `tipoactivo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `tipoprestamo`
--
ALTER TABLE `tipoprestamo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `activobaja`
--
ALTER TABLE `activobaja`
  ADD CONSTRAINT `fk_idactivo_baja` FOREIGN KEY (`idactivo`) REFERENCES `activofijo` (`id`);

--
-- Filtros para la tabla `activofijo`
--
ALTER TABLE `activofijo`
  ADD CONSTRAINT `fk_iddepartamento_activo` FOREIGN KEY (`iddepartamento`) REFERENCES `departamento` (`id`),
  ADD CONSTRAINT `fk_idsucursal_activo` FOREIGN KEY (`idsucursal`) REFERENCES `sucursal` (`id`),
  ADD CONSTRAINT `fk_idtipo_activo` FOREIGN KEY (`idtipo`) REFERENCES `tipoactivo` (`id`);

--
-- Filtros para la tabla `bgeneral`
--
ALTER TABLE `bgeneral`
  ADD CONSTRAINT `fk_idcj_bg` FOREIGN KEY (`idcjuridico`) REFERENCES `cjuridico` (`id`);

--
-- Filtros para la tabla `carterageneral`
--
ALTER TABLE `carterageneral`
  ADD CONSTRAINT `fk_idasesor_cartera` FOREIGN KEY (`idasesor`) REFERENCES `persona` (`id`),
  ADD CONSTRAINT `fk_idcartera_sucursal` FOREIGN KEY (`idcartera`) REFERENCES `sucursal` (`id`);

--
-- Filtros para la tabla `cjuridico`
--
ALTER TABLE `cjuridico`
  ADD CONSTRAINT `fk_idpersona_juridico` FOREIGN KEY (`idrepresentate`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `cnatural`
--
ALTER TABLE `cnatural`
  ADD CONSTRAINT `fk_idpersona_cliente` FOREIGN KEY (`idpersona`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `contrato`
--
ALTER TABLE `contrato`
  ADD CONSTRAINT `fk_idprestamo_contrato` FOREIGN KEY (`idprestamo`) REFERENCES `prestamo` (`id`);

--
-- Filtros para la tabla `detallecartera`
--
ALTER TABLE `detallecartera`
  ADD CONSTRAINT `fk_idcartera_detalleg` FOREIGN KEY (`idcarterag`) REFERENCES `carterageneral` (`id`),
  ADD CONSTRAINT `fk_idcliente_detalleg` FOREIGN KEY (`idcliente`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `detallefiador`
--
ALTER TABLE `detallefiador`
  ADD CONSTRAINT `fk_idnatural_detalle` FOREIGN KEY (`idfiador`) REFERENCES `cnatural` (`id`),
  ADD CONSTRAINT `fk_idprestamo_detalle` FOREIGN KEY (`idprestamo`) REFERENCES `prestamo` (`id`);

--
-- Filtros para la tabla `detallehipoteca`
--
ALTER TABLE `detallehipoteca`
  ADD CONSTRAINT `fk_idhipoteca_detalle` FOREIGN KEY (`idhipoteca`) REFERENCES `hipoteca` (`id`),
  ADD CONSTRAINT `fk_idpres_hipo` FOREIGN KEY (`idprestamo`) REFERENCES `prestamo` (`id`);

--
-- Filtros para la tabla `disponibilidad`
--
ALTER TABLE `disponibilidad`
  ADD CONSTRAINT `fk_idper_fiador` FOREIGN KEY (`idfiador`) REFERENCES `cnatural` (`id`),
  ADD CONSTRAINT `fk_idper_natural` FOREIGN KEY (`idnatural`) REFERENCES `cnatural` (`id`);

--
-- Filtros para la tabla `eresultado`
--
ALTER TABLE `eresultado`
  ADD CONSTRAINT `fk_idc_ee` FOREIGN KEY (`idcjuridico`) REFERENCES `cjuridico` (`id`);

--
-- Filtros para la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD CONSTRAINT `fk_idcliente_prestamo` FOREIGN KEY (`idcliente`) REFERENCES `persona` (`id`),
  ADD CONSTRAINT `fk_idtipopr_prestamo` FOREIGN KEY (`idtipoprestamo`) REFERENCES `tipoprestamo` (`id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_idempleado` FOREIGN KEY (`idempleado`) REFERENCES `persona` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
