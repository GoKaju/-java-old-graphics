<%-- 
    Document   : inv_pro_car_arc
    Created on : 24/05/2016, 10:23:17 AM
    Author     : DJGOMEZ
--%>




<%@page import="com.statics.carga.XMLReaderDOM"%>
<%@page import="com.statics.dao.DatosJpaController"%>
<%@page import="com.statics.dao.CargaParametroJpaController"%>
<%@page import="com.statics.vo.CargaParametro"%>
<%@page import="com.statics.vo.Datos"%>
<%@page import="com.statics.dao.CargasJpaController"%>
<%@page import="com.statics.vo.ParametroLabels"%>
<%@page import="javax.persistence.TypedQuery"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="com.statics.carga.ProcesaCarga"%>
<%@page import="java.util.Date"%>
<%@page import="com.statics.vo.Cargas"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="com.csvreader.CsvReader"%>
<%@page import="com.statics.util.Fechas"%>
<%@page import="com.statics.util.Cadenas"%>
<%@page import="com.statics.vo.Usuarios"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.io.*"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileUploadException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

    Usuarios user = (Usuarios) session.getAttribute("usuarioVO");
    if (user != null && user.getUsuaId() != null) {
        Cadenas o = new Cadenas(request);
        String rfid = o.getvariable("rfid");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");
        Cargas carga = (Cargas) session.getAttribute("carga_manual");

        CargaParametroJpaController cpjc = new CargaParametroJpaController(emf);
        DatosJpaController djc = new DatosJpaController(emf);

        String hash = null;
        File file = new File("");
        String nombre = "";
        /* hala el separador del sistema operativo*/
        String separador = System.getProperty("file.separator");
        /* se obtiene la ruta de la aplicacion mas el separador mas la carpeta dentro de la aplicacion ***tiene que estar creada*** mas el separador nuevamente  */
        String ruta = application.getRealPath("") + separador + "Temp" + separador;
        System.out.println("ruta-->" + ruta);
        int i = 1;

        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletContext servletContext = this.getServletConfig().getServletContext();
            /*se obtiene la ruta de los temporales del servlet o el el servidor*/
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            ServletFileUpload upload = new ServletFileUpload(factory);
            /*se obtiene la lista de archivos que hay en el request recuerde que se debe encriptar como multipart/form-data */
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                /*aqui evaluamos si no es un campo de un formulario no es necesario*/
                if (!item.isFormField()) {
                    // Get the uploaded file parameters
                    nombre = item.getName();
//              d.setDocuPeso(String.valueOf(item.getSize()/1024)+" KB");
                    /*aqui se crea un archivo vacio con la ruta y el nombre q queramos y luego sobrescribimos ese archivo con el contenido de el enviado */
                    file = new File(ruta + Fechas.getCadena() + "_" + nombre);
                    item.write(file);

                } else {
                    String fieldName = item.getFieldName();

                }
            }

            try {
                nombre = nombre.substring(0, nombre.indexOf('.'));
//validar si la carga ya existe

                EntityManager em = emf.createEntityManager();
                TypedQuery<Cargas> query = em.createNamedQuery("Cargas.findByCargArchivoPuntoMuestral", Cargas.class);
                query.setParameter("pumu", carga.getPumuId());
                query.setParameter("cargArchivo", nombre);
                List<Cargas> lista = query.getResultList();
                em.close();
                for (Cargas pa : lista) {
                    pa.setCargDescripcion(carga.getCargDescripcion());
                    pa.setCargObservaciones(carga.getCargObservaciones());
                    carga = pa;
                }

//                insertar carga nueva
                if (carga.getCargId() != null) {
                    carga.setCargRegistradopor(user.getUsuaId());
                    carga.setCargFechacambio(Fechas.getFechaHoraTimeStamp());
                    new CargasJpaController(emf).edit(carga);

                } else {
                    carga.setCargRegistradopor(user.getUsuaId());
                    carga.setCargFechacambio(Fechas.getFechaHoraTimeStamp());
                    carga.setCargFechainicial(Fechas.getFechaHoraTimeStamp());
                    carga.setCargArchivo(nombre);
                    carga.setCargRuta(file.getAbsolutePath());
                    new CargasJpaController(emf).create(carga);

                }
                XMLReaderDOM.LeerXmlCarga(file, carga, emf);
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.getWriter().write("cargaOK()");

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    } else {%>
<script type="text/javascript">
    location.href = '../logout.jsp';
</script>
<%}%>