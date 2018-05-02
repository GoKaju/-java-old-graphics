/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.carga;

/**
 *
 * @author Usuario
 */
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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReaderDOM {

    public static void LeerXmlCarga(File xmlFile, Cargas carga, EntityManagerFactory emf) {

//        daos
        CargasJpaController cjc = new CargasJpaController(emf);
        CargaErroresJpaController cejc = new CargaErroresJpaController(emf);
        CargaParametroJpaController cpjc = new CargaParametroJpaController(emf);
        DatosJpaController djc = new DatosJpaController(emf);
        ParametrosJpaController pjc = new ParametrosJpaController(emf);
        CaerCamposJpaController ccjc = new CaerCamposJpaController(emf);
         EntityManager em = emf.createEntityManager();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            

            NodeList nodeList = doc.getElementsByTagName("Lectura");
            //now XML is loaded as Document in memory, lets convert it to Object List
          if(carga.getUltimaFechacargada()!=null){
            System.out.println("Editando carga --> :" + doc.getDocumentElement().getNodeName() + " ::nombre= " + doc.getDocumentElement().getAttribute("nombre") + " :: code= " + doc.getDocumentElement().getAttribute("codigo"));
            carga.setCargObservaciones(carga.getCargObservaciones() +";; Editando carga --> :" + doc.getDocumentElement().getNodeName() + " ::nombre= " + doc.getDocumentElement().getAttribute("nombre") + " :: code= " + doc.getDocumentElement().getAttribute("codigo"));
          
          }else{
            System.out.println("Iniciando carga --> :" + doc.getDocumentElement().getNodeName() + " ::nombre= " + doc.getDocumentElement().getAttribute("nombre") + " :: code= " + doc.getDocumentElement().getAttribute("codigo"));
            carga.setCargObservaciones("Iniciando carga --> :" + doc.getDocumentElement().getNodeName() + " ::nombre= " + doc.getDocumentElement().getAttribute("nombre") + " :: code= " + doc.getDocumentElement().getAttribute("codigo"));
          }
 cjc.edit(carga);
            int cargados = 0;
            int error = 0;
            int posicion = 0;
            Date UltimaFecha = null;
            
//            cargar datos anteriores
              if(carga.getUltimaFechacargada()!=null){
            cargados = carga.getCargExitosos();
             error = carga.getCargErrores();
             posicion = carga.getCargUltimaposicion();
             UltimaFecha = carga.getUltimaFechacargada();
          }
            for (int i = 0; i < nodeList.getLength(); i++) {
                final long start = System.currentTimeMillis();
                Date fecha = Fechas.retornaDate(nodeList.item(i).getAttributes().getNamedItem("fecha").getNodeValue(), "dd/MM/yyyy HH:mm:ss");
                 System.out.println("-->## Elapsed retornaDate " + (System.currentTimeMillis() - start) + " ms");
//              verificar si es nuevo o hasta que fecha se cargo   
                if(carga.getUltimaFechacargada()== null || Fechas.compare(carga.getUltimaFechacargada(),fecha)>0){
                UltimaFecha = fecha;
                
                System.out.println("-->" + nodeList.item(i).getNodeName());
                System.out.println("-->" + nodeList.item(i).getAttributes().getNamedItem("fecha").toString() + " @ " + fecha);
                System.out.println("-->{");
                NodeList ChildNodeList = nodeList.item(i).getChildNodes();

                for (int j = 0; j < ChildNodeList.getLength(); j++) {
                    if (ChildNodeList.item(j).getNodeName().equals("Canal")) {
                        posicion++;
                        String code = ChildNodeList.item(j).getAttributes().getNamedItem("codigo").getNodeValue();
                        String value = ChildNodeList.item(j).getAttributes().getNamedItem("valor").getNodeValue();
                            carga = cjc.findCargas(carga.getCargId());
                        if (code != null && !code.isEmpty()) {
                            int cod = Integer.parseInt(code);
                            // cargar bien
                            CargaParametro cp = null;
                            //validar cargaParametro  
                            for (CargaParametro cp1 : carga.getCargaParametroList()) {
                                int cplid =  cp1.getParaId().getParaCodigo();
                                if ( cod== cplid) {
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
                                    cpjc.create(cp);
                                } else {
                                    //   cargar error
                                    exito = false;
                                    CargaErrores ce = new CargaErrores();
                                    ce.setCargId(carga);
                                    
                                    ce.setCaerError("--> No se encontro codigo valido;");
                                    ce.setCaerNumfila(posicion);
                                    cejc.create(ce);
                                    CaerCampos cc = new CaerCampos();
                                    cc.setCaerId(ce);
                                    cc.setCacaValor(value);
                                    cc.setCacaCampo(code);
                                    ccjc.create(cc);
                                    error++;

                                }
                            }
                            if (exito) {
                                Datos d = new Datos();
                                d.setPapuId(cp);
                                d.setDatoFecha(fecha);
                                d.setDatoData(value);
                                djc.create(d);
                                cargados++;
                            }
                        } else {
//                        cargar error
                            CargaErrores ce = new CargaErrores();
                            ce.setCargId(carga);
                            ce.setCaerError("--> No se encontro codigo;");
                            ce.setCaerNumfila(posicion);
                            cejc.create(ce);
                            CaerCampos cc = new CaerCampos();
                            cc.setCaerId(ce);
                            cc.setCacaValor(value);
                            ccjc.create(cc);
                            error++;
                        }

                    }
                }
            
                System.out.println("}<--");
                }
            }
            
            carga = cjc.findCargas(carga.getCargId());
            
                System.out.println(":::1--> "+UltimaFecha);
                System.out.println(":::2--> "+carga.getUltimaFechacargada());
            carga.setUltimaFechacargada(UltimaFecha);
                System.out.println(":::3--> "+carga.getUltimaFechacargada());
            
            carga.setCargUltimaposicion(posicion);
            carga.setCargCantidadtotal(cargados + error);
            carga.setCargErrores(error);
            carga.setCargExitosos(cargados);

            cjc.edit(carga);
            
//            ejecutar procedimiento de promedios
            StoredProcedureQuery query = em
                    .createStoredProcedureQuery("procesaDatos")
                    .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                    .setParameter(1, carga.getCargId());

            if(!query.execute()){
            System.out.println("EJECUTADO PROCESADATOS");
            }
            
            
                System.out.println(":::4--> "+carga.getUltimaFechacargada());
em.flush();
            System.out.flush();
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(XMLReaderDOM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(XMLReaderDOM.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        
         em.close();
        }

    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

}
