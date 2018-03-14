<%-- 
    Document   : UploadImages
    Created on : 8/03/2018, 10:36:37 AM
    Author     : julian.rojas
--%>
<%@page import="com.statics.vo.PuntoMuestral"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.statics.vo.Campanas"%>
<%@page import="com.statics.dao.CampanasJpaController"%>
<%@page import="io.minio.MinioClient"%>
<%@page import="com.statics.dao.RutaJpaController"%>
<%@page import="com.statics.vo.Ruta"%>
<%@page import="com.statics.util.Fechas"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="java.io.File"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="com.statics.util.Cadenas"%>
<%@page import="com.statics.vo.Usuarios"%>
<%
    Usuarios user = (Usuarios) session.getAttribute("usuarioVO");
    Campanas campana = (Campanas) session.getAttribute("campana");
    PuntoMuestral punto = (PuntoMuestral) session.getAttribute("punto");
    if (user != null && user.getUsuaId() != null) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RPU");
        File file = new File("");
        String separador = System.getProperty("file.separator");
        String rutaTempFolder = application.getRealPath("") + separador + "Temp" + separador;
        RutaJpaController rutaDao = new RutaJpaController(emf);

        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletContext servletContext = this.getServletConfig().getServletContext();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Ruta ruta = rutaDao.findRuta(1);
            MinioClient minioClient = new MinioClient(ruta.getUrl(), ruta.getAccessKey(), ruta.getSecretKey());
            String bucketName = campana.getCampId() + "-" + campana.getCampNombre().trim().toLowerCase().replaceAll(" ", "").replaceAll("�", "n");
            boolean bucketExist = minioClient.bucketExists(bucketName);
            if (!bucketExist) {
                minioClient.makeBucket(bucketName);
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String[] imageNameSplit = item.getName().split("\\.");
                    String extencionFile = imageNameSplit[imageNameSplit.length - 1];
                    String fileName = punto.getPumuId() + item.getFieldName() + "." + extencionFile;
                    String rutaFileTemporal = rutaTempFolder + fileName;
                    file = new File(rutaFileTemporal);
                    item.write(file);
                    //Imagenes
                    minioClient.putObject(bucketName, fileName, rutaFileTemporal);
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.removeAttribute("campana");
            session.removeAttribute("punto");
        }
    }
%>