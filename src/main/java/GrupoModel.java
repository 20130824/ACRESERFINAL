import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Dany on 06/11/2016.
 */
public class GrupoModel<T extends Grupo> implements ICRUD<T> {
    private  DbConnection connection;
    private Connection Conn ;
    private String username;
    private String passWord;
    private String db_Name;
    private int port;
    private ResultSet resultSet;
    private PreparedStatement psmt;
    private GenerateID id;
    private Grupo grupo;

    public GrupoModel(int port, String passWord, String db_Name,String username ){
        this.port = port;
        this.passWord = passWord;
        this.db_Name = db_Name;
        this.username = username;
        id = new GenerateID();
    }


    @Override
    public Boolean insert(T entity) {

        String sql = "insert into grupos (fechainicio, fechafin, codigo, tipo, grupo, entrenador,precio,  cupo, nombre) Values (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {


            psmt= Conn.prepareStatement(sql);

            psmt.setDate(1, new Date(entity.getFechaInicio().getTime()));
            psmt.setDate(2, new Date(entity.getFechaFin().getTime()));
            psmt.setDate(3, new java.sql.Date(insert.getFechaNacimiento().getTime()));
            psmt.setString(4, String.valueOf(insert.getSexo()));
            //psmt.setString(5, insert.getMatricula());
            psmt.setString(5, id.generateSessionKey(6));
            psmt.setString(6, insert.getCedula());
            psmt.setString(7, insert.getEmail());
            psmt.setString(8, insert.getTelRes());
            psmt.setString(9, insert.getTelCel());

            psmt.executeUpdate();
            psmt.close();
            return true;
        }
        catch (SQLException e){

            System.out.println(e.getMessage());

            try {
                if(null != psmt) {
                    psmt.close();
                }
                if(null != Conn) {
                    Conn.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

            return false;
        }
    }

    @Override
    public ArrayList<T> getElements() {
        return null;
    }

    @Override
    public Boolean update(T entity) {
        return  null;
    }

    @Override
    public T readOne(String matricula) {
        return null;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }
}
