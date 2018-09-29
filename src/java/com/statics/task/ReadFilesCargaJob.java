/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.task;

import com.statics.carga.ParametrizacionAppJpaController;
import com.statics.carga.XMLReaderDOM;
import com.statics.dao.CargasJpaController;
import com.statics.util.Fechas;
import com.statics.vo.Campanas;
import com.statics.vo.Cargas;
import com.statics.vo.ParametrizacionApp;
import com.statics.vo.PuntoMuestral;
import java.io.File;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Usuario
 */
public class ReadFilesCargaJob implements Runnable {

    @Override
    public void run() {
        // Do your job here.
        String nombre_job = Fechas.getCadena();
        System.out.println("### RFC_JOB INIT ::" + nombre_job + ":: -->" + Fechas.getFechaHoraTimeStamp());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");

        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Campanas> consulta = em.createNamedQuery("Campanas.activas", Campanas.class);
            List<Campanas> lista = consulta.getResultList();

            if (!lista.isEmpty()) {

                for (Campanas s : lista) {
                    for (PuntoMuestral pumu : s.getPuntoMuestralList()) {
                        String ruta = pumu.getEstaId().getEstaRutacargaservidor() + pumu.getEstaId().getEstaCarpetacarga();

//                        iterar dias segun parametrizacion
                        int diasBusqueda = 0;
                        String extencion = "xml";
                        ParametrizacionApp app = new ParametrizacionAppJpaController(emf).findParametrizacionApp(2);
                        if (app != null && app.getPaapValor() != null && !app.getPaapValor().isEmpty()) {
                            try {
                                diasBusqueda = Integer.parseInt(app.getPaapValor());
                                diasBusqueda--;
                            } catch (Exception e) {
                                System.out.println("ERROR:: --> no se pudo cargar el parametro de dias de busqueda ");
                            }

                        }
                        ParametrizacionApp app2 = new ParametrizacionAppJpaController(emf).findParametrizacionApp(3);
                        if (app2 != null && app2.getPaapValor() != null && !app2.getPaapValor().isEmpty()) {
                            try {
                            } catch (Exception e) {
                                System.out.println("ERROR:: --> no se pudo cargar el parametro de extencion de archivo carga");
                            }

                        }

                        for (int i = diasBusqueda; i >= 0; i--) {
                            String nombre_arc = Fechas.DevuelveFormato(Fechas.subtractDays(Fechas.getFechaHora(), i), "yyyyMMdd");
                            //System.out.println("####--> " + pumu.getPumuNombre() + " ::dia-->" + nombre_arc);
                            //System.out.println("### RFC_JOB INFO ::" + nombre_job + ":: --> ruta lectura: " + ruta);
                            //System.out.println("### RFC_JOB INFO ::" + nombre_job + ":: -->archivo xml: " + nombre_arc);

                            File file = new File(ruta + nombre_arc + ".xml");

                            if (file.exists()) {
                                Cargas carga = new Cargas();
                                //validar si la carga ya existe

                                TypedQuery<Cargas> query = em.createNamedQuery("Cargas.findByCargArchivoPuntoMuestral", Cargas.class);
                                query.setParameter("pumu", pumu);
                                query.setParameter("cargArchivo", nombre_arc);
                                List<Cargas> lista_cargas = query.getResultList();

                                for (Cargas pa : lista_cargas) {
                                    pa.setCargDescripcion("Carga automatica");
                                    pa.setCargObservaciones("### RFC_JOB INFO ::" + nombre_job + ":: -->carga automatica: " + Fechas.getFechaHoraTimeStamp());
                                    carga = pa;
                                }

                                // insertar carga nueva
                                if (carga.getCargId() != null) {
                                    carga.setCargRegistradopor(0);
                                    carga.setCargFechacambio(Fechas.getFechaHoraTimeStamp());
                                    new CargasJpaController(emf).edit(carga);

                                } else {
                                    carga.setPumuId(pumu);
                                    carga.setCargRegistradopor(0);
                                    carga.setCargFechacambio(Fechas.getFechaHoraTimeStamp());
                                    carga.setCargFechainicial(Fechas.getFechaHoraTimeStamp());
                                    carga.setCargArchivo(nombre_arc);
                                    carga.setCargRuta(file.getAbsolutePath());
                                    new CargasJpaController(emf).create(carga);

                                }
                                XMLReaderDOM.LeerXmlCarga(file, carga, emf);

                            } else {
                                //System.out.println("### RFC_JOB INFO ::" + nombre_job + ":: --> No se encontro el archivo :: " + ruta + nombre_arc + ".xml");

                            }
                        }
                    }
                }
            } else {
                //System.out.println("### RFC_JOB INFO ::" + nombre_job + ":: --> No hay campañas activas");

            }

        } catch (Exception ex) {
            // System.out.println("### RFC_JOB ERROR ::" + nombre_job + ":: -->" + Fechas.getFechaHoraTimeStamp());
            ex.printStackTrace();
        } finally {

            // System.out.println("### RFC_JOB END ::" + nombre_job + ":: -->" + Fechas.getFechaHoraTimeStamp());
            // System.out.flush();
            em.close();
        }
    }

}
