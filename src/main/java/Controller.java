//package Controlador;
/**
 * Created by Dany on 04/09/2016.
 *
 */

import spark.ModelAndView;
import spark.Request;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import static spark.Spark.*;

public class Controller {

    private  static  ICRUD <Participante>    participanteModel;
    private  static  ICRUD <Entrenador>      entrenadorModel;
    private  static  ICRUD <Grupo>           grupoModel;
    private  static  ICRUD <Taller>          tallerModel;
    private  static  ICRUD <Ciclo>           cicloModel;
    private  static  ICRUD <Voluntario>      voluntarioModel;
    private  static  ICRUD <TipoVoluntario>  tipoVoluntarioModel;
    private  static  ICRUD <Pago>            pagoModel;
    private  static  ICRUD<OtherVolunteer>   otherVolunteerModel;

    public  static String layout;
    public static void main(String[] args) {

        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist

        staticFiles.externalLocation("upload");
        Spark.staticFileLocation("/templates");
        layout = "templates/layout.vtl";
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            otherVolunteerModel = new OtherVolunteerModel<>(5432, "1234", "Acreser", "postgres");

            System.out.println(otherVolunteerModel.otherStuff().get(0).getParticipante().getNombre());
             // System.out.println( grupoModel.update(new Grupo("dpZMVz", "Intensive60",new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-30") , new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-20"), new Taller(null, null, "OOSIDU", null), "hUkY9o", Integer.parseInt("25"), Float.parseFloat("32783"), new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-06"), new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-10"),new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-12"))));

            //tipoVoluntarioModel = new TipoVoluntarioModel<>(5432, "1234", "Acreser", "postgres");
           // System.out.println(tipoVoluntarioModel.update(new TipoVoluntario("Staff", "Es un tipo de personas bien especificas que manejan los Intensive, y los discovery", "eJz772")));

            //entrenadorModel = new EntrenadorModel<>(5432, "1234", "Acreser", "postgres");
            // Iterator <Entrenador> iterator = entrenadorModel.getElements().iterator();
            // while(iterator.hasNext())
            // {
            //     System.out.println(iterator.next().getNombre());
            //  }
            //grupoModel = new GrupoModel<>(5432, "1234", "Acreser", "postgres");
            //Taller(String nombre, String descripcion, String codigo)

            // System.out.println(entrenadorModel.readOne("jkdh67").getNombre());
            //Grupo(String codigo, String nombre, Date fechaInicio, Date fechaFin, Taller tipo, String Grupo, String codigoEntrenador, Integer cupo, Float precio, Date fechaDePago1, Date fechaDePago2, Date fechaDePago3) {

            //Grupo grupo = new Grupo(null, "Antorcha" , formatter.parse("2016-11-12") , formatter.parse("2016-12-14"), new Taller(null, null, "da-d-1"), "Antorcha-60", "h3j12k", 100, Float.parseFloat("2367.6"));
            //Entrenador(String nombre, String apellidos, Date fechaNacimiento, char sexo, String matricula, String cedula, String email, String telCel, String telRes) {

            // Entrenador entrenador = new Entrenador("Isaac j.", "Perez R.", formatter.parse("1995-05-03"), 'M',"h3j12k", "JNNJN-MMHGMB", "issacperez@gmail.com", "8294480042", "8099714487");
            //System.out.println(entrenadorModel.update(entrenador));
            //Iterator<Grupo> iterator = grupoModel.getElements().iterator();
            // while (iterator.hasNext()){
            //    System.out.println(iterator.next().getNombre());
            // }
            //System.out.println( grupoModel.readOne("fsdbn").getNombre());

           // tallerModel = new TallerModel<>(5432, "1234", "Acreser", "postgres");
           // System.out.println(tallerModel.getElements().get(0).getNombre());

            // Participante par = new Participante("Pierre Dany", "Ridore Lamothe", formatter.parse("1992-05-14"), 'M',"uPm39t", "JNNJN-MMHGMB", "ridoreda1992@gmail.com", "8294480042", "8099714287", 9000);
            //participanteModel = new ParticipanteModel<>(5432, "1234", "Acreser", "postgres");
           // System.out.println(participanteModel.readOne("uPm39t").getBalance());
            //  ArrayList<Participante> participantes = participanteModel.getElements();

            // System.out.println(participanteModel.update(par));
            //  System.out.println("Participantes: " + participantes.get(0).getNombre() );
            //System.out.println();

        } catch (Exception ex) {
            ex.getMessage();
        }

        get("/Inscripciones", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("template", "templates/Inscripciones.html");
            return new ModelAndView(model, "templates/Inscripciones.html");
        }, new VelocityTemplateEngine());
//**************************************************************************************************************************************
        get("/registrarParticipantes", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("template", "templates/registrarParticipantes.html");
            return new ModelAndView(model, "templates/registrarParticipantes.html");
        }, new VelocityTemplateEngine());

        post("/gettingParticipants", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();
            String nombres = request.queryParams("nombre");
            String apellidos = request.queryParams("apellido");
            String cedula = request.queryParams("cedula");
            String telRes = request.queryParams("tel");
            String telcel = request.queryParams("cel");
            String sexo = request.queryParams("sexo");
            String fecha = request.queryParams("birthday");
            String email = request.queryParams("email");
            String nacionalidad = request.queryParams("pais");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(fecha);

            participanteModel = new ParticipanteModel<>(5432, "1234", "Acreser", "postgres");
            System.out.println(nombres + "  " + apellidos + " " + fecha + "  " + cedula + "  " + sexo + "  " + telcel + "  " + telRes+" "+ nacionalidad+""+ sexo + ""+ date +""+email);
            if (participanteModel.insert(new Participante(nombres, apellidos, date, sexo.charAt(0), null, cedula, email, telcel, telRes, 0, nacionalidad))) {
                model.put("name", " El participante:  " + nombres);
                return new ModelAndView(model, "templates/registrarParticipantes.html");
            }else {
                response.status(404);
                model.put("error", "Hubo un error al registrar el participante!!!");
                return new ModelAndView(model, "templates/registrarParticipantes.html");
            }
        }, new VelocityTemplateEngine());

//*****************************************************************************************************************************************
        get("/registrarEntrenadores", (request, response) -> {
            response.type("text/html");

            HashMap model = new HashMap();

            model.put("template", "templates/registrarEntrenadores.html");
            return new ModelAndView(model, "templates/registrarEntrenadores.html");
        }, new VelocityTemplateEngine());


        post("/registrarEntrenadores", (request, response) -> {
            response.type("text/html");

            entrenadorModel = new EntrenadorModel<>(5432, "1234", "Acreser", "postgres");
            HashMap modelEntrenadores = new HashMap();
            String nombres = request.queryParams("nombres");
            String apellidos = request.queryParams("apellidos");
            String cedula = request.queryParams("cedula");
            String telRes = request.queryParams("residencial");
            String telcel = request.queryParams("celular");
            String sexo = request.queryParams("Sexo");
            String fecha = request.queryParams("birthday");
            String email = request.queryParams("email");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(fecha);

            System.out.println(nombres + "  " + apellidos + " " + fecha + "  " + cedula + "  " + sexo + "  " + telcel + "  " + telRes);

            if (entrenadorModel.insert(new Entrenador(nombres, apellidos, date, sexo.charAt(0), null, cedula, email, telcel, telRes))) {
                modelEntrenadores.put("name", " El entrenador:  " + nombres);
                return new ModelAndView(modelEntrenadores,"templates/registrarEntrenadores.html");
            } else{
                response.status(404);
                modelEntrenadores.put("error", "Hubo un error al registrar el Entrenador!!!");
                return new ModelAndView(modelEntrenadores, "templates/registrarEntrenadores.html");
            }
        }, new VelocityTemplateEngine());

//****************************************************************************************************************************************
        get("/registrarTaller", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("template", "templates/registrarTaller.html");
            return new ModelAndView(model, "templates/registrarTaller.html");
        }, new VelocityTemplateEngine());


        post("/registrarTaller", (request, response) -> {
            response.type("text/html");
            tallerModel = new TallerModel<>(5432, "1234", "Acreser", "postgres");
            HashMap model = new HashMap();
            String nombre = request.queryParams("nombre");
            String prerequisito = request.queryParams("prerequisito");
            String descripcion = request.queryParams("descripcion");

            System.out.println(nombre + "  " + prerequisito + " " + descripcion);
            if (tallerModel.insert(new Taller(nombre, descripcion, null, prerequisito))) {
                model.put("name", " El Taller:  " + nombre);
                return new ModelAndView(model, "templates/registrarTaller.html");
            } else{
                response.status(404);
                model.put("error", "Hubo un error al registrar el taller!!!!");
                return new ModelAndView(model, "templates/registrarTaller.html");
            }
        }, new VelocityTemplateEngine());
//****************************************************************************************************************************************8


        get("/consultarParticipantes", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("matricula", "Matricula");
            model.put("nombres", "Nombre(s)");
            model.put("apellidos","Apellido(s)");
            model.put("email", "Email");
            model.put("nacionalidad", "Nacionalidad");
            model.put("tel", "Celular");
            model.put("balance", "Balance");
            participanteModel = new ParticipanteModel<>(5432, "1234", "Acreser", "postgres");

            model.put("participantes", participanteModel.getElements());
            model.put("tableRow", "templates/tableRow.html");
            model.put("template", "templates/consultarParticipantes.html");
            return new ModelAndView(model, "templates/consultarParticipantes.html");
        }, new VelocityTemplateEngine());


        post("/consultarParticipantes", (request, response) -> {
            response.type("text/html");
            tallerModel = new TallerModel<>(5432, "1234", "Acreser", "postgres");
            HashMap model = new HashMap();

                return new ModelAndView(model, "templates/registrarTaller.html");

        }, new VelocityTemplateEngine());

//*****************************************************************************************************************************
        get("/consultarEntrenadores", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("matricula", "Matricula");
            model.put("nombres", "Nombre(s)");
            model.put("apellidos","Apellido(s)");
            model.put("email", "Email");
            model.put("tel", "Celular");
            entrenadorModel = new EntrenadorModel<>(5432, "1234", "Acreser", "postgres");

            model.put("entrenadores", entrenadorModel.getElements());
            model.put("tableRow", "templates/trEntrenadores.html");
            model.put("template", "templates/consultarEntrenadores.html");
            return new ModelAndView(model, "templates/consultarEntrenadores.html");
        }, new VelocityTemplateEngine());

//****************************************************************************************************************************************
        get("/consultarVoluntarios", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("funcion", "Funcion");
            model.put("nombre",  "Nombre(s)");
            model.put("apellido", "Apellido(s)");
            model.put("matricula","Matricula");
            model.put("fecha", "Fecha Inicio");

            otherVolunteerModel = new OtherVolunteerModel<>(5432, "1234", "Acreser", "postgres");

            model.put("voluntarios", otherVolunteerModel.otherStuff());
            model.put("tableRow", "templates/trVoluntarios.html");

            model.put("template", "templates/consultarVoluntarios.html");
            return new ModelAndView(model, "templates/consultarVoluntarios.html");
        }, new VelocityTemplateEngine());

//****************************************************************************************************************************************

        get("/registrarGrupo", (request, response) -> {
            response.type("text/html");

            HashMap model = new HashMap();

             tallerModel = new TallerModel<>(5432, "1234", "Acreser", "postgres");
            model.put("talleres", tallerModel.getElements());
             entrenadorModel = new EntrenadorModel<>(5432, "1234", "Acreser", "postgres");
             model.put("entrenadores", entrenadorModel.getElements());
            model.put("template", "templates/registrarGrupo.html");
            return new ModelAndView(model, "templates/registrarGrupo.html");
        }, new VelocityTemplateEngine());


        post("/registrarGrupo", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();
            String nombre = request.queryParams("nombre");
            String fechaInicio = request.queryParams("fechaInicio");
            String fechaFin = request.queryParams("fechaFin");
            String tipo = request.queryParams("tipo");
            String entrenador = request.queryParams("entrenador");
            String cupo = request.queryParams("cupo");
            String costo = request.queryParams("costo");
            String fechaPago1 = request.queryParams("fecha1");
            String fechaPago2 = request.queryParams("fecha2");
            String fechaPago3 = request.queryParams("fecha3");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date1  = formatter.parse(fechaPago1);
            Date date2 = formatter.parse(fechaPago2);
            Date date3 = formatter.parse(fechaPago3);
            Date fechaIni = formatter.parse(fechaInicio);
            Date fechafin = formatter.parse(fechaFin);
            String[] st = new String[2];
            st = entrenador.split(":");

            grupoModel   = new GrupoModel<>(5432, "1234", "Acreser", "postgres");
          System.out.println(grupoModel.insert(new Grupo(null, nombre, fechaIni, fechafin, new Taller(null, null, tipo, null), st[0], Integer.parseInt(cupo), Float.parseFloat(costo), date1, date2, date3)));

            response.status(400);
            return new ModelAndView(model, "templates/registrarGrupo.html");


        }, new VelocityTemplateEngine());
//****************************************************************************************************************************************
        get("/registrarPrograma", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("template", "templates/registrarPrograma.html");
            return new ModelAndView(model, "templates/registrarPrograma.html");
        }, new VelocityTemplateEngine());


        post("/registrarPrograma", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();
            String nombre = request.queryParams("nombre" );
            String fechaInicio = request.queryParams("fechaInicio");
            String fechaFin = request.queryParams("fechaFin");
            String tipo = request.queryParams("tipo");
            String talleres = request.queryParams("talleres");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaini = formatter.parse(fechaInicio);
            Date fechafin = formatter.parse(fechaFin);
            cicloModel = new CicloModel<>(5432, "1234", "Acreser", "postgres");


            System.out.println(tipo);

            //Ciclo (String nombre, String codigo, Date fechaFin, Date fechaInicio, TipoCiclo tipoCiclo, Taller talleres )
            System.out.println("Programa " + nombre + "  " + fechaInicio + " " + fechaFin + " " + tipo + " " + talleres);
                if (cicloModel.insert(new Ciclo(nombre,null, fechafin, fechaini ,1,  new Taller(talleres, null, null,null)))){
                model.put("error", "Hubo un error registrando el ");
                return new ModelAndView(model, "templates/registrarPrograma.html");
            } else{
                response.status(404);
                model.put("error", "hubo un error al registrar el Grupo!!!");

                return new ModelAndView(model, "templates/registrarPrograma.html" );
            }
        }, new VelocityTemplateEngine());
//*****************************************************************************************************************************************
        get("/realizarPago", (request, response) -> {

            response.type("text/html");
            HashMap model = new HashMap();
            participanteModel = new ParticipanteModel<>(5432, "1234", "Acreser", "postgres");
            model.put("participantes", participanteModel.getElements());

            grupoModel = new GrupoModel<>(5432, "1234", "Acreser", "postgres");
            model.put("grupos", grupoModel.getElements());
            model.put("template", "templates/realizarPago.html");
            return new ModelAndView(model, "templates/realizarPago.html");
        }, new VelocityTemplateEngine());


        post("/realizarPago", (request, response) -> {
            response.type("text/html");
            pagoModel = new PagoModel<>(5432, "1234", "Acreser", "postgres");
            HashMap model = new HashMap();
            String participante = request.queryParams("participante");
            String monto = request.queryParams("monto");
            String fechaPago = request.queryParams("fechaPago");
            String grupo = request.queryParams("grupo");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = formatter.parse(fechaPago);


            System.out.println( monto + " " + date1 + " " + grupo );
            if (pagoModel.insert(new Pago(date1, Float.parseFloat(monto), participante, null, grupo))){
                model.put("name", "Pago");
                return new ModelAndView(model, "templates/realizarPago.html");
            } else{
                response.status(404);
                model.put("error", "hubo un error al registrar el tipo de voluntario!!!");
                return new ModelAndView(model, "templates/realizarPago.html");
            }

        }, new VelocityTemplateEngine());
//****************************************************************************************************************************************8

        get("/tipoVoluntario", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

                                                                  model.put("template", "templates/tipoVoluntario.html");
            return new ModelAndView(model, "templates/tipoVoluntario.html");
        }, new VelocityTemplateEngine());


        post("/tipoVoluntario", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();
            tipoVoluntarioModel= new TipoVoluntarioModel<>(5432, "1234", "Acreser", "postgres");
            String nombre = request.queryParams("nombre");
            String descripcion = request.queryParams("descripcion");


            System.out.println(nombre + "  " + descripcion );
            if (tipoVoluntarioModel.insert(new TipoVoluntario(nombre, descripcion, null))){
                model.put("name", "Tipo de voluntario: "+nombre);
                return new ModelAndView(model, "templates/tipoVoluntario.html");
            } else{
                response.status(404);
                model.put("error", "hubo un error al registrar el tipo de voluntario!!!");
                return new ModelAndView(model, "templates/tipoVoluntario.html" );
            }

        }, new VelocityTemplateEngine());
//****************************************************************************************************************************************8


        get("/registrarVoluntario", (request, response) -> {
            response.type("text/html");
            tipoVoluntarioModel = new TipoVoluntarioModel<>(5432, "1234", "Acreser", "postgres");

            HashMap model = new HashMap();
            model.put("participantes", participanteModel.getElements());

            model.put("tipoVoluntario", tipoVoluntarioModel.getElements());
            model.put("template", "templates/registrarVoluntario.html");

            return new ModelAndView(model, "templates/registrarVoluntario.html");
        }, new VelocityTemplateEngine());


        post("/Voluntario", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();
                voluntarioModel = new VoluntarioModel<>(5432, "1234", "Acreser", "postgres");
                String Participante = request.queryParams("participante");
                String Funcion = request.queryParams("funcion");
                String FechaRealizacion = request.queryParams("fecharegistracion");
                String observaciones = request.queryParams("observaciones");
                String[] st = new String[2];
                String[] st1 = new String[2];
                st1 = Funcion.split(":");
                st = Participante.split(":");
                String participanteid = st[0];


                String funcion = tipoVoluntarioModel.getElements().get((Integer.parseInt(st1[0])-1)).getCodigo();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = formatter.parse(FechaRealizacion);

            if (voluntarioModel.insert(new Voluntario(participanteid, funcion, date1, null))){
                 model.put("success", "Error al registrar el voluntario!!!");
                return new ModelAndView(model, "templates/registrarVoluntario.html");
            } else{
                response.status(404);
                model.put("error", "Error al registrar el voluntario!!!");
                return new ModelAndView(model, "templates/registrarVoluntario.html" );
            }


        }, new VelocityTemplateEngine());
//****************************************************************************************************************************************8

        get("/enviarMaterial", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("template", "templates/enviarMaterial.html");
            return new ModelAndView(model, "templates/enviarMaterial.html");
        }, new VelocityTemplateEngine());


        post("/enviarMaterial", "multipart/form-data", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".jpg");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            String grupo = request.raw().getParameter("grupo");
            String asunto = request.raw().getParameter("asunto");
            try (InputStream input = request.raw().getPart("file").getInputStream()) { // getPart needs to use same "name" as input field in form

                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }


            System.out.println(tempFile.getFileName());
            enviarCorreo("isaacenmanuel28@gmail.com", "info@acreser.net", "info1234", tempFile.toAbsolutePath().toString(), asunto );

            logInfo(request, tempFile);
            return new ModelAndView(model, "templates/enviarMaterial.html");

        }, new VelocityTemplateEngine());
//****************************************************************************************************************************************8

        get("/enviarPromocion", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("template", "templates/enviarPromocion.html");
            return new ModelAndView(model, "templates/enviarPromocion.html");
        }, new VelocityTemplateEngine());


        post("/enviarPromocion", "multipart/form-data", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".jpg");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            String asunto = request.raw().getParameter("asunto");
            try (InputStream input = request.raw().getPart("file").getInputStream()) { // getPart needs to use same "name" as input field in form

                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }


            System.out.println(tempFile.getFileName());
            //enviarCorreo("isaacenmanuel28@gmail.com", "info@acreser.net", "info1234", tempFile.toAbsolutePath().toString(), asunto );
            notificarAdeudados("isaacenmanuel28@gmail.com", "info@acreser.net", "info1234", asunto, "Isaac Perez","2013" ,"1500", "28-112016" );
            logInfo(request, tempFile);
            return new ModelAndView(model, "templates/enviarPromocion.html");

        }, new VelocityTemplateEngine());
    }

    // methods used for logging
    private static void logInfo(Request req, Path tempFile) throws IOException, ServletException {
        System.out.println("Uploaded file '" + getFileName(req.raw().getPart("file")) + "' saved as '" + tempFile.toAbsolutePath() + "'");
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private static void enviarCorreo(String destino, String usuario, String clave, String attachment, String Asunto){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.acreser.net");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(usuario, clave);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("info@acreser.net"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destino));
            message.setSubject(Asunto);

            //3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("ACRESER\n" +
                    "Carretera Las Palomas\n" +
                    "#60, entrando frente a Leche Rica Santigo, Rep.Dom.\n" +
                    "Tel: (809)-489-2189\n" +
                    "Cel: (829)-649-5841\n" +
                    "Email: info@acreser.net ");



            //4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            String filename = "SendAttachment.java";//change accordingly
            DataSource source = new FileDataSource(attachment);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);


            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            //6) set the multiplart object to the message object
            message.setContent(multipart );

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void notificarAdeudados(String destino, String usuario, String clave, String Asunto, String nombre, String matricula, String monto, String fecha){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.acreser.net");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(usuario, clave);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("info@acreser.net"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destino));
            message.setSubject(Asunto);

            //3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("Usted: " + nombre +" tiene un pago pendiente por la suma de: " + monto +" pesos dominicanos, correspondientes al pago de su taller. Dicho pago debió ser realizado en la fecha: " + fecha +
                    ". Le pedimos que por favor se ponga al día con el pago." +
                    " Cordialmente se despide: El equipo administrativo de ACRESER."

            );


            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);

            //6) set the multiplart object to the message object
            message.setContent(multipart );

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }





    }





