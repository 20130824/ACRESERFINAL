/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author isaac
 */
public class Taller {
    private String Nombre;
    private String Descripcion;
    private String Codigo;
    private String Prerequisito;

    public Taller(String nombre, String descripcion, String codigo, String prerequisito) {
        this.Nombre = nombre;
        this.Descripcion = descripcion;
        this.Codigo = codigo;
        this.Prerequisito = prerequisito;

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getPrerequisito() {
        return Prerequisito;
    }

    public void setPrerequisito(String prerequisito) {
        Prerequisito = prerequisito;
    }

}