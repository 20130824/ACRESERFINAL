//package Controlador;
/**
 * Created by Dany on 04/09/2016.
 *
 */

import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


import static spark.Spark.*;

public class Controller {

    private  static  ICRUD <Participante>    participanteModel;
    private  static  ICRUD <Entrenador>      entrenadorModel;
    private  static  ICRUD <Grupo>           grupoModel;
    private  static  ICRUD <Taller>          tallerModel;
    private  static  ICRUD <Ciclo>           cicloModel;
    private  static  String layout;

    public static void main(String[] args) {
        Spark.staticFileLocation("/templates");
        layout = "templates/layout.vtl";
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            /*entrenadorModel = new EntrenadorModel<>(5432, "1234", "Acreser", "postgres");
            Iterator<Entrenador> iterator;
            iterator = entrenadorModel.getElements().iterator();
            while(iterator.hasNext())
            {
                 System.out.println(iterator.next().getNombre());
             }*/
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
            participanteModel = new ParticipanteModel<>(5432, "1234", "Acreser", "postgres");
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
                model.put("template", "templates/Successful.vtl");
                return new ModelAndView(model, layout);
            }else {
                response.status(404);
                model.put("error", "Hubo un error al registrar el participante!!!");
                model.put("template", "templates/Error.vtl");
                return new ModelAndView(model, layout);
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
                modelEntrenadores.put("template", "templates/Successful.vtl");
                return new ModelAndView(modelEntrenadores, layout);
            } else{
                response.status(404);
                modelEntrenadores.put("error", "Hubo un error al registrar el Entrenador!!!");
                modelEntrenadores.put("template", "templates/Error.vtl");
                return new ModelAndView(modelEntrenadores, layout);
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
                model.put("template", "templates/Successful.vtl");
                return new ModelAndView(model, layout);
            } else{
                response.status(404);
                model.put("error", "Hubo un error al registrar el taller!!!!");
                model.put("template", "templates/Error.vtl");
                return new ModelAndView(model, layout);
            }
        }, new VelocityTemplateEngine());
//****************************************************************************************************************************************8
         get("/registrarGrupo", (request, response) -> {
            response.type("text/html");
            HashMap model = new HashMap();


             entrenadorModel = new EntrenadorModel<>(5432, "1234", "Acreser", "postgres");
             ArrayList<Entrenador> entrenadores = entrenadorModel.getElements();
             model.put("entrenadores", entrenadores);
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

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = formatter.parse(fechaPago1);
            Date date2 = formatter.parse(fechaPago2);
            Date date3 = formatter.parse(fechaPago3);
            Date fechaIni = formatter.parse(fechaInicio);
            Date fechafin = formatter.parse(fechaFin);


            if (grupoModel.insert(new Grupo(null, nombre, fechaIni, fechafin, new Taller(tipo, null, null, null), entrenador, Integer.parseInt(cupo), Float.parseFloat(costo), date1, date2, date3 ))){

                model.put("name", " El grupo: " + nombre);
                model.put("template", "templates/Successful.vtl");
                return new ModelAndView(model, layout);
            } else{
                response.status(404);
                model.put("error", "hubo un error al registrar el Grupo!!!");
                model.put("template", "templates/Error.vtl");
                return new ModelAndView(model, layout);
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
            cicloModel = new CicloModel<>(5432, "1234", "Acreser", "postgres");
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

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaini = formatter.parse(fechaInicio);
            Date fechafin = formatter.parse(fechaFin);

            if (cicloModel.insert(new Ciclo(nombre, null, fechafin, fechaini, tipoCiclo, new Taller(talleres, null, null, null)))){

                model.put("name", " El Taller: " + nombre);
                model.put("template", "templates/Successful.vtl");
                return new ModelAndView(model, layout);
            } else{
                response.status(404);
                model.put("error", "hubo un error al registrar el Grupo!!!");
                model.put("template", "templates/Error.vtl");
                return new ModelAndView(model, layout);
            }
        }, new VelocityTemplateEngine());
//*****************************************************************************************************************************************
    }
}