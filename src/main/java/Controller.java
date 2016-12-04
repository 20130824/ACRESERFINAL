//package Controlador;
/**
 * Created by Dany on 04/09/2016.
 *
 */

import spark.ModelAndView;
import spark.Request;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;
import spark.utils.IOUtils;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import static spark.Spark.*;

public class Controller {

    private  static  ICRUD <Participante>    participanteModel;
    private  static  ICRUD <Entrenador>      entrenadorModel;
    private  static  ICRUD <Grupo>           grupoModel;
    private  static  ICRUD <Taller>          tallerModel;
    private  static  ICRUD <Ciclo>           cicloModel;
    private  static  ICRUD <Voluntario>      voluntarioModel;


    public  static String layout;
    public static void main(String[] args) {

        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist

        staticFiles.externalLocation("upload");
        Spark.staticFileLocation("/templates");
        layout = "templates/layout.vtl";
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

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

            tallerModel = new TallerModel<>(5432, "1234", "Acreser", "postgres");
            System.out.println(tallerModel.getElements().get(0).getNombre());

            // Participante par = new Participante("Pierre Dany", "Ridore Lamothe", formatter.parse("1992-05-14"), 'M',"uPm39t", "JNNJN-MMHGMB", "ridoreda1992@gmail.com", "8294480042", "8099714287", 9000);
            participanteModel = new ParticipanteModel<>(5432, "1234", "Acreser", "postgres");
            System.out.println(participanteModel.readOne("uPm39t").getBalance());
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
            String telRes = request.queryParams("res1") + request.queryParams("res2") + request.queryParams("res3");
            String telcel = request.queryParams("cel1") + request.queryParams("cel2") + request.queryParams("cel3");
            String sexo = request.queryParams("Sexo");
            String fecha = request.queryParams("birthday");
            String email = request.queryParams("email");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(fecha);

            System.out.println(nombres + "  " + apellidos + " " + fecha + "  " + cedula + "  " + sexo + "  " + telcel + "  " + telRes);
            if (participanteModel.insert(new Participante(nombres, apellidos, date, sexo.charAt(0), null, cedula, email, telcel, telRes, 0))) {
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
         get("/registrarGrupo", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("template", "templates/registrarGrupo.html");
            return new ModelAndView(model, "templates/registrarGrupo.html");
        }, new VelocityTemplateEngine());


        post("/registrarGrupo", (request, response) -> {
            response.type("text/html");
            grupoModel   = new GrupoModel<>(5432, "1234", "Acreser", "postgres");
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
            String fechaPago3= request.queryParams("fecha3");

            // Grupo(String codigo, String nombre, Date fechaInicio, Date fechaFin, Taller tipo,  String codigoEntrenador, Integer cupo, Float precio, Date fechaDePago1, Date fechaDePago2, Date fechaDePago3) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = formatter.parse(fechaPago1);
            Date date2 = formatter.parse(fechaPago2);
            Date date3 = formatter.parse(fechaPago3);
            Date fechaIni = formatter.parse(fechaInicio);
            Date fechafin = formatter.parse(fechaFin);

            if (grupoModel.insert(new Grupo(null, nombre, fechaIni, fechafin, new Taller(nombre, null, null, null), entrenador, Integer.parseInt(cupo), Float.parseFloat(costo), date1, date2, date3 ))){
                model.put("name", nombre);
                return new ModelAndView(model, "templates/registrarGrupo.html");
            } else{
                response.status(404);
                model.put("error", "hubo un error al registrar el Grupo!!!");
                return new ModelAndView(model, "templates/registrarGrupo.html" );
            }

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
            String nombre = request.queryParams("nombre");
            String fechaInicio = request.queryParams("fechaInicio");
            String fechaFin = request.queryParams("fechaFin");
            String tipo = request.queryParams("tipo");
            String talleres = request.queryParams("talleres");


            TipoCiclo tipoCiclo;
            if (tipo == "Adultos") {
                tipoCiclo = TipoCiclo.ADULTOS;
            } else {
                tipoCiclo = TipoCiclo.JOVENES;
            }
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaini = formatter.parse(fechaInicio);
                Date fechfin = formatter.parse(fechaFin);
                cicloModel = new CicloModel<>(5432, "1234", "Acreser", "postgres");

                System.out.println(cicloModel.insert(new Ciclo(nombre, null, fechaini, fechfin, tipoCiclo, new Taller(talleres, null, null, null))));

            } catch (Exception ex) {
                System.out.println("Failed!");
                System.out.println(ex.getMessage());
            }

            System.out.println("Programa " + nombre + "  " + fechaInicio + " " + fechaFin + " " + tipo + " " + talleres);
            return new ModelAndView(model, "templates/registrarPrograma.html");
        }, new VelocityTemplateEngine());
//*****************************************************************************************************************************************
        get("/realizarPago", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();

            model.put("template", "templates/realizarPago.html");
            return new ModelAndView(model, "templates/realizarPago.html");
        }, new VelocityTemplateEngine());


        post("/realizarPago", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();
            String participante = request.queryParams("participante");
            String monto = request.queryParams("monto");
            String fechaPago = request.queryParams("fechaPago");
            String fechaProxPago = request.queryParams("fechaProxPago");
            String balance = request.queryParams("balance");


            System.out.println(participante + "  " + monto + " " + fechaPago + " " + fechaProxPago + " " + balance);
            return new ModelAndView(model, "templates/realizarPago.html");

        }, new VelocityTemplateEngine());
//****************************************************************************************************************************************8

        get("/registrarVoluntario", (request, response) -> {
            response.type("text/html");
            //voluntarioModel = new VoluntarioModel<>(5432, "1234", "Acreser", "postgres");
            participanteModel = new ParticipanteModel<>(5432, "1234", "Acreser", "postgres");
            HashMap model = new HashMap();

            model.put("participantes", participanteModel.getElements());
            //model.put("tipoVoluntario",  )
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
                //String[] st1 = new String[2];
                // st1 = Funcion.split(";");
                //
                st = Participante.split(";");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = formatter.parse(FechaRealizacion);


             if (voluntarioModel.insert(new Voluntario(st[1], Funcion, date1, null))){
                 model.put("succes", "Error al registrar el voluntario!!!");
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


        post("/enviarMaterial", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();
            String grupo = request.queryParams("grupo");
            String archivo = request.queryParams("archivo");


            System.out.println(grupo + "  " + archivo);
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

            Path tempFile = Files.createTempFile(uploadDir.toPath(), "file ", ".png");

            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            try (InputStream input = request.raw().getPart("file").getInputStream()) { // getPart needs to use same "name" as input field in form

                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            System.out.println(tempFile.getFileName());
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

}

