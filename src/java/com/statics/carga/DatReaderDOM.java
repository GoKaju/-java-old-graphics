/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.carga;

import com.google.common.io.Files;
import com.statics.dao.CaerCamposJpaController;
import com.statics.dao.CargaErroresJpaController;
import com.statics.dao.CargaParametroJpaController;
import com.statics.dao.CargasJpaController;
import com.statics.dao.DatosJpaController;
import com.statics.dao.ParametrosJpaController;
import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.util.Fechas;
import com.statics.vo.CaerCampos;
import com.statics.vo.CargaErrores;
import com.statics.vo.CargaParametro;
import com.statics.vo.Cargas;
import com.statics.vo.Datos;
import com.statics.vo.Parametros;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

/**
 *
 * @author Usuario
 */
public class DatReaderDOM {

    public static void LeerDatCarga(File datFile, Cargas carga, EntityManagerFactory emf) {
        final long startInit = System.currentTimeMillis();
        //        daos
        CargasJpaController cjc = new CargasJpaController(emf);
        CargaErroresJpaController cejc = new CargaErroresJpaController(emf);
        CargaParametroJpaController cpjc = new CargaParametroJpaController(emf);
        DatosJpaController djc = new DatosJpaController(emf);
        ParametrosJpaController pjc = new ParametrosJpaController(emf);
        CaerCamposJpaController ccjc = new CaerCamposJpaController(emf);
        EntityManager em = emf.createEntityManager();

        try {
            if (carga.getUltimaFechacargada() != null) {
                System.out.println("Editando carga --> :" + carga.getPumuId().getPumuDescripcion() + " " + carga.getCargArchivo());
                carga.setCargObservaciones("Editando carga --> :" + carga.getPumuId().getPumuDescripcion() + " " + carga.getCargArchivo());

            } else {
                System.out.println("Iniciando carga --> :" + carga.getPumuId().getPumuDescripcion() + " " + carga.getCargArchivo());
                carga.setCargObservaciones("Iniciando carga --> :" + carga.getPumuId().getPumuDescripcion() + " " + carga.getCargArchivo());
            }
            cjc.edit(carga);
            int cargados = 0;
            int cargadosAhora = 0;
            int error = 0;
            int posicion = 0;
            Date UltimaFecha = null;

//            cargar datos anteriores
            if (carga.getUltimaFechacargada() != null) {
                cargados = carga.getCargExitosos();
                error = carga.getCargErrores();
                posicion = carga.getCargUltimaposicion();
                UltimaFecha = carga.getUltimaFechacargada();
            }
            carga = cjc.findCargas(carga.getCargId());

            Map<Integer, CargaParametro> headers = new HashMap();

            //         1 paso: cargar el archivo a memoria y normalizarlo(remover 1 linea y cambiar espacios duplicados por 1 solo)
            List<String> lines = Files.readLines(datFile, Charset.defaultCharset());
            lines.remove(0);
            String encabezado = lines.get(0);
            lines.remove(0);
            lines.isEmpty();
            // convertir encabezado en hashMap por el index
            String[] bruteheader = encabezado.split(";");
            int position = 0;
            for (String string : bruteheader) {

                if (!string.isEmpty() && !string.equals("#HHMM")) {
                    String[] splitheader = string.split(",");
                    String code = splitheader[0];
                    if (code != null && !code.isEmpty()) {
                        int cod = Integer.parseInt(code);
                        // cargar bien
                        CargaParametro cp = null;
                        //validar cargaParametro  
                        for (CargaParametro cp1 : carga.getCargaParametroList()) {
                            int cplid = cp1.getParaId().getParaCodigo();
                            if (cod == cplid) {
                                cp = cp1;
                                break;
                            }
                        }
                        boolean exito = true;
//                            si no esta lo crea
                        if (cp == null) {
                            cp = new CargaParametro();
                            cp.setCargId(carga);

                            TypedQuery<Parametros> query = em.createNamedQuery("Parametros.findByPareCodigo", Parametros.class);
                            query.setParameter("pareCode", cod);
                            List<Parametros> lista = query.getResultList();

                            Parametros p = null;
                            for (Parametros pa : lista) {
                                p = pa;
                            }
                            if (p != null && p.getParaId() != null) {
                                cp.setParaId(pjc.findParametros(p.getParaId()));
                                em.getTransaction().begin();
                                em.persist(cp);
                                em.getTransaction().commit();
                                //cpjc.create(cp);
                            } else {
                                //   cargar error
                                exito = false;
                                CargaErrores ce = new CargaErrores();
                                ce.setCargId(carga);

                                ce.setCaerError("--> No se encontro codigo valido;" + code);
                                ce.setCaerNumfila(1);
                                cejc.create(ce);
                                CaerCampos cc = new CaerCampos();
                                cc.setCaerId(ce);
                                //cc.setCacaValor(value);
                                cc.setCacaCampo(code);
                                ccjc.create(cc);
                                error++;

                            }
                        }
                        if (exito) {
                            headers.put(position, cp); // Añade un elemento al Map
                        }
                    }
                }
                position++;
            }

//                  recorremos las lineas las corregimos y luego recorremos el map para insertar los valores en datos}
                  int lineNum = 0;
            for (String line : lines) {
                boolean inserted = false;
                lineNum++;
                final long start = System.currentTimeMillis();
                line = line.replaceAll("  ", " ");
                String[] bruteLine = line.split(" ");
                Date fecha = Fechas.retornaDate(carga.getCargArchivo() + bruteLine[0], "yyyyMMddHHmm");
//              verificar si es nuevo o hasta que fecha se cargo   
                if (carga.getUltimaFechacargada() == null || Fechas.compare(carga.getUltimaFechacargada(), fecha) > 0) {
                    UltimaFecha = fecha;
                    for (Map.Entry<Integer, CargaParametro> entry : headers.entrySet()) {
                        // insertar datos 
                        Datos d = new Datos();
                        d.setPapuId(entry.getValue());
                        d.setDatoFecha(fecha);
                        d.setDatoData(bruteLine[entry.getKey()]);

                        // djc.create(d);
                        try {

                            em.getTransaction().begin();
                            em.persist(d);
                            em.getTransaction().commit();
                            inserted = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cargados++;
                        cargadosAhora++;
                    }
                }
                System.out.println( String.format("-># Elapsed for line#%d inserted:%b  %d ms",lineNum,inserted,(System.currentTimeMillis() - start)));
            }

            // finalizando datos de  carga 
            carga = cjc.findCargas(carga.getCargId());

            carga.setUltimaFechacargada(UltimaFecha);
            carga.setCargUltimaposicion(posicion);
            carga.setCargCantidadtotal(cargados + error);
            carga.setCargErrores(error);
            carga.setCargExitosos(cargados);
            cjc.edit(carga);

//            ejecutar procedimiento de promedios
//            ejecutar procedimiento de promedios
            if (cargadosAhora > 0) {
                final long start = System.currentTimeMillis();
                StoredProcedureQuery query = em
                        .createStoredProcedureQuery("procesaDatos")
                        .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                        .setParameter(1, carga.getCargId());

                if (!query.execute()) {
                    System.out.println("EJECUTADO PROCESADATOS");
                }
                em.flush();

                System.out.println("-->## Elapsed for exec PROCESADATOS: " + (System.currentTimeMillis() - start) + " ms");
                System.out.flush();
            }
            System.out.println("-->## Elapsed for carga  "+carga.getPumuId().getPumuDescripcion()+" "+carga.getCargArchivo()+"  " + (System.currentTimeMillis() - startInit) + " ms");
        } catch (IOException ex) {
            Logger.getLogger(DatReaderDOM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DatReaderDOM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DatReaderDOM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
