SELECT p.* FROM dato_procesado dp 
INNER JOIN parametro_factorconversion pfc ON dp.id_parametro_factorconversion=pfc.id 
INNER JOIN parametros p ON p.para_id=pfc.id_parametro WHERE id_punto_muestral=5 GROUP BY pare_nombre

CALL procesaDatos(20);


SELECT pfc.* FROM parametro_factorconversion pfc INNER JOIN dato_procesado dp ON dp.id_parametro_factorconversion=pfc.id WHERE id_punto_muestral=5 GROUP BY id_parametro

SELECT * FROM dato_procesado WHERE id_punto_muestral=5 AND id_parametro_factorconversion=2 AND fecha BETWEEN (SELECT MAX(fecha) - INTERVAL 24 HOUR FROM dato_procesado) AND (SELECT MAX(fecha) FROM dato_procesado)

DELETE FROM dato_procesado WHERE fecha > '2018-01-25 13:00:00'


UPDATE ruta SET url='http://127.0.0.1:9000', accessKey='XGU3NMDGSV83AMCREQIP', secretKey='z7E3e85JfmJD9roqAi3KxmTPbSlXYMekJt+vqRZv' WHERE id=1


INSERT INTO cliente (nombre, direccion, email, nit, telefono, creado_por, creado_fecha) VALUES ('Jimmy', 'DirccioFake', 'Jimmy@gmail.com', '456789', '34567890', 1, NOW());
SELECT NOW()

SELECT * FROM dato_procesado dp
INNER JOIN parametro_factorconversion pfc ON pfc.id=dp.id_parametro_factorconversion
WHERE dp.id_punto_muestral=5 AND pfc.id_parametro IN (1) GROUP BY dp.id

COMMIT


SELECT * FROM dato_procesado WHERE id_punto_muestral=10
AND id_parametro_factorconversion=5 AND fecha BETWEEN (SELECT MAX(fecha) - INTERVAL 24 HOUR FROM dato_procesado WHERE id_punto_muestral=10) 
AND (SELECT MAX(fecha) FROM dato_procesado WHERE id_punto_muestral=10)


SELECT DISTINCT(p.para_id), pare_nombre, pare_descripcion, pare_registradopor, pare_fechacambio, para_estado, para_codigo, para_tipografica, masa_molar FROM dato_procesado dp INNER JOIN parametro_factorconversion pfc ON pfc.id=dp.id_parametro_factorconversion INNER JOIN parametros p ON p.para_id=pfc.id_parametro WHERE id_punto_muestral=5