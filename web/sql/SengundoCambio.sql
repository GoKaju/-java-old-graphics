START TRANSACTION;

DROP TABLE cliente;

CREATE TABLE cliente(
	id INT AUTO_INCREMENT, 
	nombre VARCHAR(40),
	direccion VARCHAR(40),
	email VARCHAR(40),
	nit VARCHAR(15),
	telefono VARCHAR(20),
	creado_por INT, 
	creado_fecha DATETIME,
	PRIMARY KEY (id)
);

INSERT INTO cliente (nombre, direccion) VALUES ('Jimmi','qdasdasd');

ALTER TABLE campanas DROP COLUMN cliente;
ALTER TABLE campanas ADD COLUMN id_cliente INT;
ALTER TABLE campanas ADD FOREIGN KEY (id_cliente) REFERENCES cliente (id);

ALTER TABLE parametros ADD masa_molar DOUBLE;
UPDATE parametros SET masa_molar=30.0061 WHERE para_id=1;
UPDATE parametros SET masa_molar=46.0055 WHERE para_id=2;
UPDATE parametros SET masa_molar=46.01 WHERE para_id=4;
UPDATE parametros SET masa_molar=64.638 WHERE para_id=4;

CREATE TABLE pais (
	id INT AUTO_INCREMENT,
	nombre VARCHAR(20),
	k_conversion DOUBLE,
	PRIMARY KEY (id)
);

INSERT INTO pais (nombre, k_conversion) VALUES ("Colombia",2);

CREATE TABLE unidad_medida(
	id INT AUTO_INCREMENT,
	descripcion VARCHAR(30),
	factor DOUBLE,
	PRIMARY KEY (id)
);

INSERT INTO unidad_medida (descripcion, factor) VALUES ("ppb", 0);
INSERT INTO unidad_medida (descripcion, factor) VALUES ("ppm", 0);
INSERT INTO unidad_medida (descripcion, factor) VALUES ("ug/m3", 0);
INSERT INTO unidad_medida (descripcion, factor) VALUES ("Int", 0);
INSERT INTO unidad_medida (descripcion, factor) VALUES ("C°", 0);
INSERT INTO unidad_medida (descripcion, factor) VALUES ("Grados", 0);
INSERT INTO unidad_medida (descripcion, factor) VALUES ("mg/m3", 0);

CREATE TABLE unidadmedida_parametro(
	id INT AUTO_INCREMENT,
	id_parametro INT,
	id_unidad_medida INT,
	PRIMARY KEY (id),
	FOREIGN KEY (id_unidad_medida) REFERENCES unidad_medida (id),
	FOREIGN KEY (id_parametro) REFERENCES parametros (para_id)
);

INSERT INTO unidadmedida_parametro (id_parametro,id_unidad_medida) VALUES (1,1);
INSERT INTO unidadmedida_parametro (id_parametro,id_unidad_medida) VALUES (2,1);
INSERT INTO unidadmedida_parametro (id_parametro,id_unidad_medida) VALUES (4,1);
INSERT INTO unidadmedida_parametro (id_parametro,id_unidad_medida) VALUES (5,3);
INSERT INTO unidadmedida_parametro (id_parametro,id_unidad_medida) VALUES (6,3);
INSERT INTO unidadmedida_parametro (id_parametro,id_unidad_medida) VALUES (7,4);
INSERT INTO unidadmedida_parametro (id_parametro,id_unidad_medida) VALUES (8,4);
INSERT INTO unidadmedida_parametro (id_parametro,id_unidad_medida) VALUES (9,1);


CREATE TABLE factor_conversion(
	id INT AUTO_INCREMENT,
	id_unidad_destino INT,
	id_pais INT,
	PRIMARY KEY (id),
	FOREIGN KEY (id_unidad_destino) REFERENCES unidad_medida(id),
	FOREIGN KEY (id_pais) REFERENCES pais(id)
);

INSERT INTO factor_conversion (id_unidad_destino, id_pais) VALUES (1,1);
INSERT INTO factor_conversion (id_unidad_destino, id_pais) VALUES (2,1);
INSERT INTO factor_conversion (id_unidad_destino, id_pais) VALUES (3,1);
INSERT INTO factor_conversion (id_unidad_destino, id_pais) VALUES (4,1);
INSERT INTO factor_conversion (id_unidad_destino, id_pais) VALUES (5,1);
INSERT INTO factor_conversion (id_unidad_destino, id_pais) VALUES (6,1);

CREATE TABLE parametro_factorconversion(
	id INT AUTO_INCREMENT,
	id_parametro INT,
	id_unidad_medida INT,
	id_factor_conversion INT,
	is_default BIT,
	PRIMARY KEY (id),
	FOREIGN KEY (id_unidad_medida) REFERENCES unidad_medida(id),
	FOREIGN KEY (id_parametro) REFERENCES parametros(para_id),
	FOREIGN KEY (id_factor_conversion) REFERENCES factor_conversion (id)
);

INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (1, 1, 1, 1);
INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (2, 1, 1, 1);
INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (3, 1, 1, 1);
INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (4, 1, 1, 1);
INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (5, 3, 3, 1);
INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (6, 3, 3, 1);
INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (7, 4, 4, 1);
INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (8, 4, 4, 1);
INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (9, 1, 1, 1);
INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (2, 1, 3, 0);
INSERT INTO parametro_factorconversion (id_parametro, id_unidad_medida, id_factor_conversion, is_default) VALUES (9, 1, 3, 0);

CREATE TABLE unidad_tiempo (
	id INT AUTO_INCREMENT,
	descripcion VARCHAR(20),
	unidad VARCHAR(20),
	numero_horas INT,
	PRIMARY KEY (id)
);

INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("Año", "Hora", 8760);
INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("Mes", "Hora", 730);
INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("Dia", "Hora", 24);
INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("8", "Hora", 8);
INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("1", "Hora", 1);

CREATE TABLE nivel_maximo(
	id INT AUTO_INCREMENT,
	id_parametro_factorconversion INT,
	id_unidad_tiempo INT,
	nivel_minimo DOUBLE,
	nivel_maximo DOUBLE,
	PRIMARY KEY(id),
	FOREIGN KEY (id_parametro_factorconversion) REFERENCES parametro_factorconversion(id),
	FOREIGN KEY (id_unidad_tiempo) REFERENCES unidad_tiempo(id)
);

INSERT INTO nivel_maximo (id_parametro_factorconversion, id_unidad_tiempo, nivel_minimo, nivel_maximo) 
VALUES (2, 1, 0, 29.230),(10, 1, 0, 60),(2, 7, 0, 97.435),(10, 7, 0, 200),
(9, 4, 0, 17.508),(11, 4, 0, 50),(9, 7, 0, 35.016),(11, 7, 0, 100),
(5, 1, 0, 50),(5, 4, 0, 100),
(6, 1, 0, 25),(6, 4, 0, 50);

CREATE TABLE dato_procesado(
	id INT AUTO_INCREMENT,
	id_punto_muestral INT,
	id_unidad_tiempo INT,
	id_parametro_factorconversion INT,
	fecha DATETIME,
	valor DOUBLE,
	fecha_conversion TIMESTAMP,
	PRIMARY KEY(id),
	FOREIGN KEY (id_punto_muestral) REFERENCES punto_muestral(pumu_id),
	FOREIGN KEY (id_unidad_tiempo) REFERENCES unidad_tiempo(id),
	FOREIGN KEY (id_parametro_factorconversion) REFERENCES parametro_factorconversion(id)
);

DELIMITER $$
DROP PROCEDURE IF EXISTS `procesaDatos`$$

CREATE PROCEDURE `procesaDatos`(
IN id_carga INT
)
BEGIN

INSERT INTO dato_procesado (id_punto_muestral, id_unidad_tiempo, id_parametro_factorconversion, fecha, valor, fecha_conversion) 
SELECT c.pumu_id, 7, pf.id, DATE_FORMAT(dato_fecha, '%Y-%m-%d %H') AS hora, AVG(dato_data), CURRENT_TIMESTAMP() FROM datos d 
INNER JOIN carga_parametro cp ON d.papu_id=cp.capa_id INNER JOIN cargas c ON c.carg_id=cp.carg_id
INNER JOIN parametro_factorconversion pf ON pf.id_parametro=cp.para_id AND pf.is_default=1
WHERE cp.carg_id = id_carga GROUP BY para_id, hora;

END$$

DELIMITER ;
CALL procesaDatos(23);
DELETE FROM dato_procesado WHERE fecha >'2018-01-24 21:00:00' AND id_punto_muestral=5	
COMMIT;