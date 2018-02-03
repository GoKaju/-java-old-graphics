/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.servlet;

//import com.statics.dao.ArchivosJpaController;
import com.statics.dao.FormatofechasJpaController;
import com.statics.dao.UsuariorolJpaController;
import com.statics.dao.UsuariosJpaController;
import com.statics.util.Cadenas;
//import com.statics.vo.Archivos;
import com.statics.vo.Formatofechas;
import com.statics.vo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author D4V3
 */
@WebServlet(name = "Servlet_archivos", urlPatterns = {"/Archivos"})
public class Servlet_archivos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession(true);
            Usuarios user = (Usuarios) session.getAttribute("usuarioVO");
            if (user != null && user.getUsuaId() != null) {
                Cadenas o = new Cadenas(request);
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");
                String modulo = o.getvariable("modulo");
                int op = Integer.parseInt(o.getvariable("op"));
//**********************************************************
//                ArchivosJpaController adao = new ArchivosJpaController(emf);
                UsuariosJpaController udao = new UsuariosJpaController(emf);
                UsuariorolJpaController urdao = new UsuariorolJpaController(emf);

                if (op > 0) {
                    switch (op) {
                        case 1:
                            out.print(ContinuarPage2(session, o));
                            break;
                        case 2:
                            out.print(agregarLineacsv(session, o));
                            break;
                        case 3:
                            out.print(eliminarLineacsv(session, o));
                            break;
                        case 4:
                            out.print(modificarCeldacsv(session, o));
                            break;
                        case 5:
                            out.print(VerificarFormatoFechas(session, o));
                            break;
                        case 6:
                            out.print(CambiarFormatoFechashhmm(session, o));
                            break;
                        case 7:
                            out.print(GuardarArchivo(session, o, emf));
                            break;
                        case 8:
                            out.print(EliminarArchivo(session, o, emf));
                            break;
                        default:
                            out.print("alerta('ERROR','Accion invalida')");

                    }
                }

            } else {
                out.println("location.href='logout.jsp'");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
//metodos case

    private String ContinuarPage2(HttpSession session, Cadenas o) {
        String mje = "";

        try {
            mje = " RecargaPanel('panels/archivos_edit.jsp?rfid=" + o.getvariable("rfid") + "','content');";

        } catch (UnsupportedEncodingException ex) {
            mje = "alerta('ERROR','Error al procesar')";

            ex.printStackTrace();
            Logger.getLogger(Servlet_archivos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mje;

    }

    private String agregarLineacsv(HttpSession session, Cadenas o) {
        String mje = "";

        try {
            int hash = Integer.parseInt(o.getvariable("hash"));
            List linescsv = (List) session.getAttribute("listCSV");
            if (linescsv != null) {
                String[] s = (String[]) linescsv.get(0);
                s = new String[s.length];

                for (int i = 0; i < s.length - 1; i++) {
                    s[i] = "";

                }

                linescsv.add(hash, s);
                session.setAttribute("listCSV", linescsv);

                mje = " recargaRowsTable();";
            } else {
                mje = "alerta('ERROR','EL erchivo parece invalido')";
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            Logger.getLogger(Servlet_archivos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mje;

    }

    private String CambiarFormatoFechashhmm(HttpSession session, Cadenas o) {
        String mje = "";

        try {
            int col = Integer.parseInt(o.getvariable("col"));
            String fecha = o.getvariable("fecha");
            fecha = fecha.replaceAll("-", "/");

            List linescsv = (List) session.getAttribute("listCSV");
            int cont = 0;
            if (linescsv != null) {
                Iterator i = linescsv.iterator();
                while (i.hasNext()) {
                    String[] next = (String[]) i.next();
                    if (cont > 0) {
                        String val = next[col];
                        if (val.length() < 4) {
                            int in = 4 - val.length();
                            String g = "";
                            for (int x = 0; x < in; x++) {
                                g += "0";
                            }
                            val = g + val;
                        }
                        System.out.println(fecha + " " + val);
                        next[col] = cambiarFormato(fecha + " " + val, "yyyy/MM/dd hhmm", "dd/MM/yyyy hh:mm");

                    } else {
                        next[col] = "date";
                    }

                    cont++;

                }

                session.removeAttribute("listCSV");
                session.setAttribute("listCSV", linescsv);

                mje = " recargaRowsTable(); alerta('OK','Fechas formateadas correctamente.!')";

            } else {
                mje = "alerta('ERROR','EL erchivo parece invalido')";
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            Logger.getLogger(Servlet_archivos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mje;

    }

    private String CambiarFormatoFechas(HttpSession session, Cadenas o, String formato) {
        String mje = "";

        try {
            int col = Integer.parseInt(o.getvariable("col"));

            List linescsv = (List) session.getAttribute("listCSV");
            int cont = 0;
            if (linescsv != null) {
                Iterator i = linescsv.iterator();
                while (i.hasNext()) {
                    String[] next = (String[]) i.next();
                    if (cont > 0) {
                        next[col] = cambiarFormato(next[col], formato, "dd/MM/yyyy hh:mm");

                    } else {
                        next[col] = "date";
                    }

                    cont++;

                }

                session.removeAttribute("listCSV");
                session.setAttribute("listCSV", linescsv);

                mje = " recargaRowsTable(); alerta('OK','Fechas formateadas correctamente.!')";

            } else {
                mje = "alerta('ERROR','EL erchivo parece invalido')";
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            Logger.getLogger(Servlet_archivos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mje;

    }

    private String eliminarLineacsv(HttpSession session, Cadenas o) {
        String mje = "";

        try {
            int hash = Integer.parseInt(o.getvariable("hash"));
            List linescsv = (List) session.getAttribute("listCSV");
            if (linescsv != null) {

                linescsv.remove(hash);
                session.setAttribute("listCSV", linescsv);

                mje = " recargaRowsTable();";
            } else {
                mje = "alerta('ERROR','EL erchivo parece invalido')";
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            Logger.getLogger(Servlet_archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mje;

    }

    private String modificarCeldacsv(HttpSession session, Cadenas o) {
        String mje = "";

        try {
            int row = Integer.parseInt(o.getvariable("row"));
            int col = Integer.parseInt(o.getvariable("col"));
            String val = o.getvariable("val");
            List linescsv = (List) session.getAttribute("listCSV");
            if (linescsv != null) {

                String[] s = (String[]) linescsv.get(row);

                s[col] = val;

                linescsv.set(row, s);
                session.removeAttribute("listCSV");
                session.setAttribute("listCSV", linescsv);

                mje = "alerta('OK','Modificado con exito!')";
            } else {
                mje = "alerta('ERROR','EL erchivo parece invalido')";
            }

        } catch (Exception ex) {

            mje = "alerta('ERROR','Error en el procesamiento')";
            ex.printStackTrace();
            Logger.getLogger(Servlet_archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mje;

    }

    private String VerificarFormatoFechas(HttpSession session, Cadenas o) {
        String mje = "";

        try {

            int col = Integer.parseInt(o.getvariable("col"));

            List linescsv = (List) session.getAttribute("listCSV");
            if (linescsv != null) {

                int row1 = 0;
                int row2 = 0;
                do {
                    row1 = ((int) Math.floor(Math.random() * linescsv.size()));
                    row2 = ((int) Math.floor(Math.random() * linescsv.size()));
                } while (row1 == 0 && row2 == 0);

                System.out.println("aleatorio: " + row1);
                System.out.println("aleatorio: " + row2);
                String s1 = this.averiguarFormato(((String[]) linescsv.get(row1))[col]);
                String s2 = this.averiguarFormato(((String[]) linescsv.get(row2))[col]);
                System.out.println("formato: " + s1);
                System.out.println("formato: " + s2);

                if (s1.equals(s2)) {
                    if (s1.equals("hhmm")) {
                        mje = "pedirfechahhmm(" + col + ")";
                    } else if (s1.equals("dd/MM/yyyy hh:mm")) {
                        mje = "alerta('OK','El formato es correcto, no es necesario cambiarlo.')";
                    } else {
                        mje = CambiarFormatoFechas(session, o, s1);

                    }
                } else {
                    mje = "alerta('Error',' Lo siento no he podido reconocer el formato')";
                }

            } else {
                mje = "alerta('ERROR','EL archivo parece invalido')";
            }

        } catch (Exception ex) {

            mje = "alerta('ERROR','No encontre fechas en esta columna.')";
            ex.printStackTrace();
            Logger.getLogger(Servlet_archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mje;

    }

    private String GuardarArchivo(HttpSession session, Cadenas o, EntityManagerFactory emf) {
        String mje = "";

        try {

//            Usuarios user = (Usuarios) session.getAttribute("usuarioVO");
//            List linescsv = (List) session.getAttribute("listCSV");
//            File file = (File) session.getAttribute("FileArchivo");
//
//            CSVWriter writer = new CSVWriter(new FileWriter(file), ',');
//            for (Object object : linescsv) {
//                String[] s = (String[]) object;
//
//                writer.writeNext(s);
//
//            }
//            writer.close();
//
//            Archivos arch = (Archivos) session.getAttribute("hashArchivo");
//            arch.setArchAlias(o.getvariable("alias"));
//            arch.setArchFechacambio(Fechas.getFechaHoraTimeStamp());
//            arch.setArchLatitud(o.getvariable("latitud"));
//            arch.setArchLongitud(o.getvariable("longitud"));
//            arch.setArchRegistradopor(user.getUsuaId());
//            arch.setArchSubidopor(user);
//            new ArchivosJpaController(emf).create(arch);
//            ParametrosJpaController paradao = new ParametrosJpaController(emf);
//
//            String[] s = (String[]) linescsv.get(0);
//            for (String string : s) {
//                Parametros p = new Parametros();
//                p.setArchId(arch);
//                p.setParaDescripcion(string);
//                paradao.create(p);
//
//            }
//
//            mje = "alerta('Ok','Guardado correctamente.'); RecargaPanel('panels/archivos.jsp?rfid=" + o.getvariable("rfid") + "', 'content')";

        } catch (Exception ex) {
            mje = "alerta('ERROR','Error en el procesamiento.')";

            ex.printStackTrace();
            Logger.getLogger(Servlet_archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mje;

    }

    private String EliminarArchivo(HttpSession session, Cadenas o, EntityManagerFactory emf) {
        String mje = "";

        try {
//
//            ArchivosJpaController adao = new ArchivosJpaController(emf);
//            ParametrosJpaController paradao = new ParametrosJpaController(emf);
//            Archivos a = adao.findArchivos(Integer.parseInt(o.getvariable("uid")));
//            
//            String separador = System.getProperty("file.separator");
//            String ruta = session.getServletContext().getRealPath("") + separador + "Datos" + separador + a.getArchRutacorta();
//            File file = new File(ruta);
//            System.out.println("-- " + file == null);
//            file.delete();
//            for (Parametros p : a.getParametrosList()) {
//                paradao.destroy(p.getParaId());
//            }
//            adao.destroy(a.getArchId());
//            mje="alerta('OK','¡Eliminado correctamente!'); RecargaPanel('panels/archivos.jsp?rfid=" + o.getvariable("rfid") + "','content');";


        } catch (Exception ex) {
            mje = "alerta('ERROR','Error en el procesamiento.')";

            ex.printStackTrace();
            Logger.getLogger(Servlet_archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mje;

    }

    private String averiguarFormato(String fecha) {

        if (fecha.length() < 4) {
            int i = 4 - fecha.length();
            String g = "";
            for (int x = 0; x < i; x++) {
                g += "0";
            }
            fecha = g + fecha;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");
        emf.getCache().evictAll();
        FormatofechasJpaController formatoDAO = new FormatofechasJpaController(emf);
//        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Formatofechas ff : formatoDAO.findFormatofechasEntities()) {
            try {

                SimpleDateFormat formatter = new SimpleDateFormat(ff.getFofeValue());
                Date date = formatter.parse(fecha);
                System.out.println(date);
                return ff.getFofeValue();
            } catch (ParseException e) {
//                                e.printStackTrace();
                continue;
            }

        }
        return null;

    }

    private String cambiarFormato(String fecha, String formatofecha, String formatoreal) {

        if (fecha.length() < 4) {
            int i = 4 - fecha.length();
            String g = "";
            for (int x = 0; x < i; x++) {
                g += "0";
            }
            fecha = g + fecha;
        }
        try {

            SimpleDateFormat formatter = new SimpleDateFormat(formatofecha);
            SimpleDateFormat formatReal = new SimpleDateFormat(formatoreal);
            Date date = formatter.parse(fecha);
            return formatReal.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

}
