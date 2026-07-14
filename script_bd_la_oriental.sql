-- =============================================================
-- Script SQL - Sistema POS Taqueria La Oriental
-- Empresa: Fortis IT Solutions
-- Base de datos: fortis_la_oriental
-- Compatible con el proyecto NetBeans LaOriental
-- =============================================================

DROP DATABASE IF EXISTS fortis_la_oriental;
CREATE DATABASE fortis_la_oriental
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_spanish_ci;

USE fortis_la_oriental;

-- =============================================================
-- TABLA: usuario
-- =============================================================
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_usuario PRIMARY KEY (id_usuario),
    CONSTRAINT uq_usuario_username UNIQUE (username)
) ENGINE=InnoDB;

-- =============================================================
-- TABLA: producto
-- =============================================================
CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_producto PRIMARY KEY (id_producto)
) ENGINE=InnoDB;

-- =============================================================
-- TABLA: mesa
-- Nota: columnas 'numero', 'capacidad', 'ubicacion', 'activo'
-- y estado en MAYUSCULAS (DISPONIBLE/OCUPADA)
-- =============================================================
CREATE TABLE mesa (
    id_mesa INT AUTO_INCREMENT,
    numero INT NOT NULL,
    capacidad INT NOT NULL,
    estado VARCHAR(20) DEFAULT 'DISPONIBLE',
    ubicacion VARCHAR(100) DEFAULT '',
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_mesa PRIMARY KEY (id_mesa),
    CONSTRAINT uq_mesa_numero UNIQUE (numero)
) ENGINE=InnoDB;

-- =============================================================
-- TABLA: pedido
-- Nota: estado de cuenta en MAYUSCULAS (ABIERTO/CERRADO)
-- =============================================================
CREATE TABLE pedido (
    id_pedido INT AUTO_INCREMENT,
    id_mesa INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'ABIERTO',
    total DECIMAL(10,2) DEFAULT 0.00,
    CONSTRAINT pk_pedido PRIMARY KEY (id_pedido),
    CONSTRAINT fk_pedido_mesa
        FOREIGN KEY (id_mesa) REFERENCES mesa(id_mesa),
    CONSTRAINT fk_pedido_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- =============================================================
-- TABLA: comanda
-- Nota: cada comanda representa un envio a cocina dentro de una cuenta.
-- Estados: PENDIENTE/EN_COCINA/LISTA
-- =============================================================
CREATE TABLE comanda (
    id_comanda INT AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_envio DATETIME NULL,
    fecha_lista DATETIME NULL,
    CONSTRAINT pk_comanda PRIMARY KEY (id_comanda),
    CONSTRAINT fk_comanda_pedido
        FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
) ENGINE=InnoDB;

-- =============================================================
-- TABLA: detalle_pedido
-- =============================================================
CREATE TABLE detalle_pedido (
    id_detalle INT AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    id_comanda INT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_detalle_pedido PRIMARY KEY (id_detalle),
    CONSTRAINT fk_detalle_pedido_pedido
        FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    CONSTRAINT fk_detalle_pedido_comanda
        FOREIGN KEY (id_comanda) REFERENCES comanda(id_comanda),
    CONSTRAINT fk_detalle_pedido_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
) ENGINE=InnoDB;

-- =============================================================
-- TABLA: venta
-- =============================================================
CREATE TABLE venta (
    id_venta INT AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    id_usuario_cajero INT NOT NULL,
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    observaciones VARCHAR(255),
    CONSTRAINT pk_venta PRIMARY KEY (id_venta),
    CONSTRAINT uq_venta_pedido UNIQUE (id_pedido),
    CONSTRAINT fk_venta_pedido
        FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    CONSTRAINT fk_venta_usuario_cajero
        FOREIGN KEY (id_usuario_cajero) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- =============================================================
-- TABLA: detalle_venta
-- =============================================================
CREATE TABLE detalle_venta (
    id_detalle_venta INT AUTO_INCREMENT,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_detalle_venta PRIMARY KEY (id_detalle_venta),
    CONSTRAINT fk_detalle_venta_venta
        FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
    CONSTRAINT fk_detalle_venta_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
) ENGINE=InnoDB;

-- =============================================================
-- DATOS DE PRUEBA
-- =============================================================

INSERT INTO usuario (nombre, username, contrasena, rol) VALUES
('Administrador', 'admin', 'admin123', 'administrador'),
('Mesero Principal', 'mesero1', 'mesero123', 'mesero'),
('Cocinero', 'cocina1', 'cocina123', 'cocina'),
('Cajero', 'cajero1', 'cajero123', 'cajero');

INSERT INTO producto (nombre, categoria, precio, stock) VALUES
('Taco al pastor', 'Tacos', 22.00, 100),
('Taco arabe', 'Tacos', 25.00, 100),
('Taco de bistec', 'Tacos', 24.00, 100),
('Gringa', 'Especialidades', 35.00, 60),
('Quesadilla', 'Especialidades', 28.00, 60),
('Horchata', 'Bebidas', 18.00, 80),
('Refresco', 'Bebidas', 20.00, 80),
('Cebollitas', 'Complementos', 15.00, 50);

INSERT INTO mesa (numero, capacidad, estado, ubicacion) VALUES
(1, 4, 'DISPONIBLE', 'Interior - ventana'),
(2, 4, 'DISPONIBLE', 'Interior - centro'),
(3, 6, 'DISPONIBLE', 'Interior - esquina'),
(4, 2, 'DISPONIBLE', 'Interior - barra'),
(5, 4, 'DISPONIBLE', 'Terraza - sombra'),
(6, 6, 'DISPONIBLE', 'Terraza - sol'),
(7, 8, 'DISPONIBLE', 'Terraza - grande'),
(8, 2, 'DISPONIBLE', 'Exterior - banqueta');

INSERT INTO pedido (id_mesa, id_usuario, estado, total) VALUES
(1, 2, 'ABIERTO', 84.00),
(2, 2, 'CERRADO', 125.00);

INSERT INTO comanda (id_pedido, estado, fecha_envio, fecha_lista) VALUES
(1, 'PENDIENTE', NULL, NULL),
(2, 'LISTA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO detalle_pedido (id_pedido, id_comanda, id_producto, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 1, 3, 22.00, 66.00),
(1, 1, 6, 1, 18.00, 18.00),
(2, 2, 2, 2, 25.00, 50.00),
(2, 2, 4, 1, 35.00, 35.00),
(2, 2, 7, 2, 20.00, 40.00);

INSERT INTO venta (id_pedido, id_usuario_cajero, total, observaciones) VALUES
(2, 4, 125.00, 'Pago en efectivo');
