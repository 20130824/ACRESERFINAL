import java.util.Date;

/**
 *
 * @author isaac
 */
public class Entrenador {

    private String Nombre;
    private String Apellidos;
    private Date FechaNacimiento;
    private char Sexo;
    private String Matricula;
    private String Cedula;
    private String Email;
    private String TelCel;
    private String TelRes;

    public Entrenador(String nombre, String apellidos, Date fechaNacimiento, char sexo, String matricula, String cedula, String email, String telCel, String telRes) {
        this.Nombre = nombre;
        this.Apellidos = apellidos;
        this.FechaNacimiento = fechaNacimiento;
        this.Sexo = sexo;
        this.Matricula = matricula;
        this.Cedula = cedula;
        this.Email = email;
        this.TelCel = telCel;
        this.TelRes = telRes;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public char getSexo() {
        return Sexo;
    }

    public void setSexo(char Sexo) {
        this.Sexo = Sexo;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String Matricula) {
        this.Matricula = Matricula;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTelCel() {
        return TelCel;
    }

    public void setTelCel(String TelCel) {
        this.TelCel = TelCel;
    }

    public String getTelRes() {
        return TelRes;
    }

    public void setTelRes(String TelRes) {
        this.TelRes = TelRes;
    }


}