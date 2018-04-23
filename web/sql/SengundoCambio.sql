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

ALTER TABLE campanas DROP COLUMN cliente;
ALTER TABLE campanas ADD COLUMN id_cliente INT;
ALTER TABLE campanas ADD FOREIGN KEY (id_cliente) REFERENCES cliente (id);

ALTER TABLE parametros ADD masa_molar DOUBLE;
UPDATE parametros SET masa_molar=30.01 WHERE para_id=1;
UPDATE parametros SET masa_molar=46.0055 WHERE para_id=2;
UPDATE parametros SET masa_molar=46.01 WHERE para_id=4;
UPDATE parametros SET masa_molar=1450 WHERE para_id=5;
UPDATE parametros SET masa_molar=144.912749 WHERE para_id=5;

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

CREATE TABLE factor_conversion(
	id INT AUTO_INCREMENT,
	id_unidad_destino INT,
	id_pais INT,
	PRIMARY KEY (id),
	FOREIGN KEY (id_unidad_destino) REFERENCES unidad_medida(id),
	FOREIGN KEY (id_pais) REFERENCES pais(id)
);

INSERT INTO factor_conversion (id_unidad_destino, id_pais) VALUES (3,1);

CREATE TABLE parametro_factorconversion(
	id INT AUTO_INCREMENT,
	id_parametro INT,
	id_unidad_medida INT,
	id_factor_conversion INT,
	PRIMARY KEY (id),
	FOREIGN KEY (id_unidad_medida) REFERENCES unidad_medida(id),
	FOREIGN KEY (id_parametro) REFERENCES parametros(para_id),
	FOREIGN KEY (id_factor_conversion) REFERENCES factor_conversion (id)
);

INSERT INTO (id_parametro, id_unidad_medida, id_factor_conversion) VALUES (5, 1, 1);

CREATE TABLE unidad_tiempo (
	id INT AUTO_INCREMENT,
	descripcion VARCHAR(20),
	unidad VARCHAR(20),
	numero_horas INT,
	PRIMARY KEY (id)
);

INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("Año", "Hora", 8760);
INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("Mes", "Hora", 730);
INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("Semana", "Hora", 168);
INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("Dia", "Hora", 24);
INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("16", "Hora", 16);
INSERT INTO unidad_tiempo (descripcion, unidad, numero_horas) VALUES ("8", "Hora", 8);

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



CREATE TABLE dato_procesado(
	id INT AUTO_INCREMENT,
	id_punto_muestral INT,
	id_unidad_tiempo INT,
	id_parametro_factorconversion INT,
	facha DATE,
	valor DOUBLE,
	PRIMARY KEY(id),
	FOREIGN KEY (id_punto_muestral) REFERENCES punto_muestral(pumu_id),
	FOREIGN KEY (id_unidad_tiempo) REFERENCES unidad_tiempo(id),
	FOREIGN KEY (id_parametro_factorconversion) REFERENCES parametro_factorconversion(id)
);

COMMIT;