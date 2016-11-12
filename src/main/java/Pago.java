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
public class Pago {
    private Date FechaLimite;
    private Date FechaPagado;
    private Float MontoPagado;
    private Enum TipoPago;
    private Date FechaProximoPago;
    private float Balance;
    private String CodigoTaller;
    private String CodigoParticpante;
    private String Codigo;

    public Pago(Date fechaLimite, Date fechaPagado, Float montoPagado, Enum tipoPago, Date fechaProximoPago, float balance, String codigoTaller, String codigoParticpante, String codigo) {
        FechaLimite = fechaLimite;
        FechaPagado = fechaPagado;
        MontoPagado = montoPagado;
        TipoPago = tipoPago;
        FechaProximoPago = fechaProximoPago;
        Balance = balance;
        CodigoTaller = codigoTaller;
        CodigoParticpante = codigoParticpante;
        Codigo = codigo;
    }

    public Date getFechaLimite() {
        return FechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        FechaLimite = fechaLimite;
    }

    public Date getFechaPagado() {
        return FechaPagado;
    }

    public void setFechaPagado(Date fechaPagado) {
        FechaPagado = fechaPagado;
    }

    public Float getMontoPagado() {
        return MontoPagado;
    }

    public void setMontoPagado(Float montoPagado) {
        MontoPagado = montoPagado;
    }

    public Enum getTipoPago() {
        return TipoPago;
    }

    public void setTipoPago(Enum tipoPago) {
        TipoPago = tipoPago;
    }

    public Date getFechaProximoPago() {
        return FechaProximoPago;
    }

    public void setFechaProximoPago(Date fechaProximoPago) {
        FechaProximoPago = fechaProximoPago;
    }

    public float getBalance() {
        return Balance;
    }

    public void setBalance(float balance) {
        Balance = balance;
    }

    public String getCodigoTaller() {
        return CodigoTaller;
    }

    public void setCodigoTaller(String codigoTaller) {
        CodigoTaller = codigoTaller;
    }

    public String getCodigoParticpante() {
        return CodigoParticpante;
    }

    public void setCodigoParticpante(String codigoParticpante) {
        CodigoParticpante = codigoParticpante;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }
}