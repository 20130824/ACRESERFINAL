import java.util.Date;

/**
 * Created by Dany on 21/11/2016.
 */
public class Ciclo {
    private String Nombre;
    private String Codigo;
    private Date FechaInicio;
    private Date FechaFin;
    private int Tipo; // Para arreglar
    private  Taller talleres;



    public Ciclo (String nombre, String codigo, Date fechaFin, Date fechaInicio, TipoCiclo tipoCiclo, Taller talleres ){
        this.Nombre = nombre;
        this.Codigo = codigo;
        this.FechaFin = fechaFin;
        this.FechaInicio = fechaInicio;
        this.Tipo = tipoCiclo.getTipoCicloValue();

    }
    public Taller getTalleres() {
        return talleres;
    }

    public void setTalleres(Taller talleres) {
        this.talleres = talleres;
    }
    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
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
}
