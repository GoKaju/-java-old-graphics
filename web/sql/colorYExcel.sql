ALTER TABLE `rstatics`.`parametros` DROP COLUMN `masa_molar`; 
ALTER TABLE `rstatics`.`parametros` ADD COLUMN `pare_color_borde` VARCHAR(20) NULL 
AFTER `para_tipografica`, ADD COLUMN `pare_color_background` VARCHAR(20) NULL AFTER `pare_color_borde`; 

DELIMITER //
DROP PROCEDURE IF EXISTS calculaPromediosPorHorarioExcel; //
CREATE PROCEDURE calculaPromediosPorHorarioExcel
(IN horas INT,
IN fechaInicio DATETIME,
IN fechaFinal DATETIME,
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
WHERE dp.id_punto_muestral=puntoMuestral AND dp.fecha BETWEEN fechaInicio AND fechaFinal );
IF horas <> 8 THEN 
SELECT DATE_FORMAT(tmp.fecha, '%Y-%m-%d %H:%i') AS fecha, 
GROUP_CONCAT(CONCAT(pfc.id_parametro,'#'), ROUND(tmp.valor,3) SEPARATOR ';') 
FROM tmp_datos tmp INNER JOIN parametro_factorconversion pfc ON tmp.id_parametro_factorconversion=pfc.id 
	GROUP BY (
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
SELECT IFNULL(ROUND(AVG(tmp.valor),3),0) AS valor,fecha AS fecha, 
IFNULL(tmp.id_parametro_factorconversion,0), 4, IFNULL(tmp.id_punto_muestral,puntoMuestral) 
FROM tmp_datos tmp WHERE tmp.fecha BETWEEN DATE_SUB(fecha, INTERVAL 8 HOUR) AND fecha 
GROUP BY tmp.id_parametro_factorconversion;
SET fecha = DATE_ADD(fecha, INTERVAL 1 HOUR);
END WHILE;
SELECT DATE_FORMAT(tmp.fecha, '%Y-%m-%d %H:%i') AS fecha, 
GROUP_CONCAT(CONCAT(pfc.id_parametro,'#'), ROUND(tmp.valor,3) SEPARATOR ';') 
FROM tmp_datos tmp INNER JOIN parametro_factorconversion pfc ON tmp.id_parametro_factorconversion=pfc.id 
GROUP BY tmp.fecha;
END IF;   

END; //
DELIMITER ;
