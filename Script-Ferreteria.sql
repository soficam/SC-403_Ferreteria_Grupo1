CREATE DATABASE ferreteria;
USE ferreteria;

CREATE TABLE categoria(
	id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100)
);

CREATE TABLE producto (
  id_producto INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100),
  precio DECIMAL(5,2),
  id_categoria INT,
  FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria),
  imagen_ruta TEXT
);

CREATE USER 'usuario_prueba'@'localhost' IDENTIFIED BY 'Usuar1o_Clave.';
GRANT ALL PRIVILEGES ON ferreteria.* TO 'usuario_prueba'@'localhost';
FLUSH PRIVILEGES;

COMMIT;

