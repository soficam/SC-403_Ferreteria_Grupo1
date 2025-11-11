# --- CREAR BASE DE DATOS
CREATE DATABASE ferreteria;
USE ferreteria;

# --- CREAR USUARIO PARA CONEXIÓN CON LA BASE DE DATOS
CREATE USER 'usuario_prueba'@'localhost' IDENTIFIED BY 'Usuar1o_Clave.';
GRANT ALL PRIVILEGES ON ferreteria.* TO 'usuario_prueba'@'localhost';

# --- CREAR TABLAS
CREATE TABLE categoria(
	id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    estado boolean NOT NULL
);

CREATE TABLE producto (
  id_producto INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion VARCHAR(300),
  precio DECIMAL(10,2) NOT NULL,
  existencias INT NOT NULL,
  id_categoria INT NOT NULL,
  FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria),
  imagen_ruta TEXT,
  estado boolean NOT NULL
);

CREATE TABLE pedido (
  id_pedido INT AUTO_INCREMENT PRIMARY KEY,
  nombre_cliente VARCHAR(100) NOT NULL,
  fecha DATETIME NOT NULL,
  estado VARCHAR(50) NOT NULL -- Ej: 'Pendiente', 'En proceso', 'Entregado'
);

CREATE TABLE usuarios (
id_usuario INT AUTO_INCREMENT PRIMARY KEY,
nombre_usuario VARCHAR(30) NOT NULL,
correo VARCHAR(50) NOT NULL,
contrasena VARCHAR(30) NOT NULL,
rol VARCHAR(10) NOT NULL
);

COMMIT;

# --- INSERCIONES A LAS TABLAS
INSERT INTO usuarios (nombre_usuario, correo, contrasena, rol) VALUES
('admin', 'admin@ferreteria.com', 'admin', 'admin'),
('user','user@mail.com', 'user', 'user')
;

INSERT INTO pedido (nombre_cliente, fecha, estado) VALUES
('Carlos Méndez', NOW(), 'Pendiente'),
('Ana López', NOW(), 'En proceso');

INSERT INTO categoria (nombre, estado) VALUES
('Herramientas', 1),
('Electricidad', 1),
('Construcción', 1),
('Pintura', 1),
('Jardinería', 1);

INSERT INTO producto (nombre, descripcion, precio, existencias, id_categoria, imagen_ruta, estado)
VALUES 
('Martillo', 'Martillo de acero con mango de goma antideslizante', 4500.00, 25, 1, 'https://walmartcr.vtexassets.com/arquivos/ids/334478/Martillo-Hyper-Tough-16-oz-1-76131.jpg?v=637968227967270000', 1),
('Destornillador Phillips', 'Destornillador punta cruz de acero inoxidable', 1800.00, 40, 1, 'https://ferreteriavidri.com/images/items/large/148525.jpg', 1),
('Llave inglesa', 'Llave ajustable de 10 pulgadas', 5200.00, 30, 1, 'https://cdn.palbincdn.com/users/29310/images/51798.2-1668448046.jpg', 1),
('Taladro', 'Taladro eléctrico 750W con velocidad variable', 29500.00, 15, 1, 'https://ferreteriavidri.com/images/items/large/436316.jpg', 1),
('Sierra manual', 'Sierra de mano para madera con hoja de 45 cm', 3800.00, 20, 1, 'https://m.media-amazon.com/images/I/51Xrb+0JxCL._AC_UF894,1000_QL80_.jpg', 1),
('Pintura blanca', 'Pintura látex blanca 1 galón', 12500.00, 18, 1, 'https://walmartcr.vtexassets.com/arquivos/ids/408363/Pintura-L-tex-Marca-Corona-Color-Blanco-Cl-sica-946ml-1-70337.jpg?v=638207986124970000', 1),
('Brocha', 'Brocha de 2 pulgadas para pintura', 1500.00, 50, 1, 'https://www.pintulac.com.ec/media/catalog/product/cache/01d5a80ef248257bf9991bbfc9cf4de4/b/e/bep2.jpg', 1),
('Clavos', 'Caja de clavos de 2 pulgadas (100 unidades)', 900.00, 60, 1, 'https://media.falabella.com/sodimacCO/90440/public', 1),
('Lija', 'Lija para madera grano 120', 300.00, 100, 1, 'https://media.nidux.net/pull/599/800/10565/5753-product-5f982ee58cab0-0200296-1.jpg', 1),
('Flexómetro', 'Cinta métrica retráctil de 5 metros', 2700.00, 25, 1, 'https://seir.com.gt/wp-content/uploads/2022/03/FH-5M.jpg', 1);

