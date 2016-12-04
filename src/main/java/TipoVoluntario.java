import java.util.Date;

/**
 * Created by Dany on 03/12/2016.
 */
public class TipoVoluntario  {
    private String Nombre ;
    private String Descripcion;
    private String CodigoTaller;
    private String Codigo;

    public TipoVoluntario(String nombre, String Descripcion, String codigoTaller, String Codigo){
        this.Nombre = nombre;
        this.Descripcion = Descripcion;
        this.CodigoTaller = codigoTaller;
        this.Codigo = Codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCodigoTaller() {
        return CodigoTaller;
    }

    public void setCodigoTaller(String codigoTaller) {
        CodigoTaller = codigoTaller;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }
}
