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



DELIMITER //
DROP PROCEDURE IF EXISTS calculaPromediosPorHorario; //
CREATE PROCEDURE calculaPromediosPorHorario
(IN horas INT,
IN fechaInicio DATETIME,
IN fechaFinal DATETIME,
IN parametro INT,
IN puntoMuestral INT)
BEGIN
DECLARE fecha DATETIME DEFAULT fechaInicio;
DECLARE fechaF DATETIME DEFAULT fechaFinal;
DROP TABLE IF EXISTS  tmp_datos2;
CREATE TEMPORARY TABLE tmp_datos2 LIKE dato_procesado;

DROP TABLE IF EXISTS  tmp_datos;
CREATE TEMPORARY TABLE tmp_datos AS (
SELECT dp.* FROM dato_procesado dp 
INNER JOIN parametro_factorconversion pfc ON dp.id_parametro_factorconversion=pfc.id 
WHERE dp.id_punto_muestral=puntoMuestral AND pfc.id_parametro=parametro AND fecha >= fechaInicio  AND fecha < fechaFinal );

IF horas <> 8 THEN 
SELECT tmp.id, tmp.id_punto_muestral, tmp.id_unidad_tiempo, tmp.id_parametro_factorconversion,
 tmp.fecha, AVG(tmp.valor) AS valor, tmp.fecha_conversion 
FROM tmp_datos tmp GROUP BY (
	CASE WHEN horas = 1 THEN DATE_FORMAT(tmp.fecha, "%Y-%m-%d %H")
	     WHEN horas = 24 THEN DATE_FORMAT(tmp.fecha,"%Y-%m-%d")
	     WHEN horas = 730 THEN DATE_FORMAT(tmp.fecha,"%Y-%m")
	     WHEN horas = 8760 THEN DATE_FORMAT(tmp.fecha,"%Y")
	END);
	
ELSE

SET fecha = (SELECT MIN(tmp.fecha) FROM tmp_datos tmp);
SET fechaF = (SELECT MAX(tmp.fecha) FROM tmp_datos tmp);
WHILE fecha <> fechaF DO
INSERT INTO tmp_datos2 (valor,fecha, id_parametro_factorconversion, id_unidad_tiempo, id_punto_muestral) 
SELECT AVG(tmp.valor) AS valor, tmp.fecha, tmp.id_parametro_factorconversion, tmp.id_unidad_tiempo,tmp.id_punto_muestral FROM tmp_datos tmp WHERE tmp.fecha BETWEEN fecha AND DATE_ADD(fecha, INTERVAL 8 HOUR);
SET fecha = DATE_ADD(fecha, INTERVAL 1 HOUR);
END WHILE;
SELECT * FROM tmp_datos2;
END IF;    
END; //

DELIMITER ;
