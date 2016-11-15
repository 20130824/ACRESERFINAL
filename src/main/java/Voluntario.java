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
public class Voluntario {
    private String participanteID;
    private Volunteers TipoVoluntario;
    private Date FechaInicio;
    private String Codigo;

    public Voluntario(String participanteID, Volunteers tipoVoluntario, Date fechaInicio, String codigo) {
        this.participanteID = participanteID;
        TipoVoluntario = tipoVoluntario;
        FechaInicio = fechaInicio;
        Codigo = codigo;
    }
    public enum Volunteers{
        STAFF,
        SENIOR,
        DOOR_GUARD,
        AYUDANTE_EXTERNO,
        CAPITAN

    }
    public String getParticipanteID() {
        return participanteID;
    }

    public void setParticipanteID(String participanteID) {
        this.participanteID = participanteID;
    }

    public Volunteers getTipoVoluntario() {
        return TipoVoluntario;
    }

    public void setTipoVoluntario(Volunteers tipoVoluntario) {
        TipoVoluntario = tipoVoluntario;
    }

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }
}