import java.util.Date;

/**
 * Created by Dany on 05/11/2016.
 */


public class Participante {


        private String  Nombre;
        private String  Apellidos;
        private Date    FechaNacimiento;
        private char    Sexo;
        private String  Matricula;
        private String  Cedula;
        private String  Email;
        private String  TelCel;
        private String  TelRes;
        private float   balance;

        public Participante(String nombre, String apellidos, Date fechaNacimiento, char sexo, String matricula, String cedula, String email, String telCel, String telRes , float balance) {
            this.Nombre = nombre;
            this.Apellidos = apellidos;
            this.FechaNacimiento = fechaNacimiento;
            this.Sexo = sexo;
            this.Matricula = matricula;
            this.Cedula = cedula;
            this.Email = email;
            this.TelCel = telCel;
            this.TelRes = telRes;
            this.balance = balance;
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

        public float getBalance() {
            return balance;
        }

        public void setBalance(float balance) {
            this.balance = balance;
        }

    public String getTelRes() {
        return TelCel;
    }

    public void setTelRes(String TelRes) {
        this.TelRes = TelRes;
    }
    }

