/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Date;

/**
 *
 * @author isaac
 */
public class Grupo {
    private  String Codigo;
    private  String Nombre;
    private  Date FechaInicio;
    private  Date FechaFin;
    private  Taller Tipo;
    private  String CodigoEntrenador;
    private  Integer Cupo;
    private  Float Precio;
    private  String grupo;
    private  Date FechaDePago1;
    private  Date FechaDePago2;
    private  Date FechaDePago3;

    public Grupo(String codigo, String nombre, Date fechaInicio, Date fechaFin, Taller tipo, String Grupo, String codigoEntrenador, Integer cupo, Float precio, Date fechaDePago1, Date fechaDePago2, Date fechaDePago3) {
        Codigo = codigo;
        Nombre = nombre;
        FechaInicio = fechaInicio;
        FechaFin = fechaFin;
        Tipo = tipo;
        CodigoEntrenador = codigoEntrenador;
        Cupo = cupo;
        Precio = precio;
        grupo = Grupo;
        FechaDePago1= fechaDePago1;
        FechaDePago2= fechaDePago2;
        FechaDePago3= fechaDePago3;

    }



    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        FechaFin = fechaFin;
    }

    public Taller getTipo() {
        return Tipo;
    }

    public void setTipo(Taller tipo) {
        Tipo = tipo;
    }

    public String getCodigoEntrenador() {
        return CodigoEntrenador;
    }

    public void setCodigoEntrenador(String codigoEntrenador) {
        CodigoEntrenador = codigoEntrenador;
    }

    public Integer getCupo() {
        return Cupo;
    }

    public void setCupo(Integer cupo) {
        Cupo = cupo;
    }

    public Float getPrecio() {
        return Precio;
    }

    public void setPrecio(Float precio) {
        Precio = precio;
    }
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }


    public Date getFechaDePago1() {
        return FechaDePago1;
    }

    public void setFechaDePago1(Date fechaDePago1) {
        FechaDePago1 = fechaDePago1;
    }

    public Date getFechaDePago2() {
        return FechaDePago2;
    }

    public void setFechaDePago2(Date fechaDePago2) {
        FechaDePago2 = fechaDePago2;
    }

    public Date getFechaDePago3() {
        return FechaDePago3;
    }

    public void setFechaDePago3(Date fechaDePago3) {
        FechaDePago3= fechaDePago3;
    }


}
