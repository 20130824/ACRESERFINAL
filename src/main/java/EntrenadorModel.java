import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dany on 06/11/2016.
 */
public class EntrenadorModel<T extends Entrenador> implements ICRUD<T> {

    private  DbConnection connection;
    private Connection Conn ;
    private String username;
    private String passWord;
    private String db_Name;
    private int port;
    private ResultSet resultSet;
    private PreparedStatement psmt;
    private GenerateID id;
    private Entrenador entrenador;

    public EntrenadorModel(int port, String passWord, String db_Name,String username ){
        this.port = port;
        this.passWord = passWord;
        this.db_Name = db_Name;
        this.username = username;
        id = new GenerateID();
    }


    @Override
    public Boolean insert(T entity) {
        String sql = "insert into entrenadores(nombres, apellidos, fechanacimiento, sexo, matricula, cedula, email, telres, telcel) Values (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {

            psmt= Conn.prepareStatement(sql);

            psmt.setString(1, entity.getNombre());
            psmt.setString(2, entity.getApellidos());
            psmt.setDate(3, new java.sql.Date(entity.getFechaNacimiento().getTime()));
            psmt.setString(4, String.valueOf(entity.getSexo()));
            //psmt.setString(5, entity.getMatricula());
            psmt.setString(5, id.generateSessionKey(6));
            psmt.setString(6, entity.getCedula());
            psmt.setString(7, entity.getEmail());
            psmt.setString(8, entity.getTelRes());
            psmt.setString(9, entity.getTelCel());

            psmt.executeUpdate();
            psmt.close();

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
        return true;
    }

    @Override
    public ArrayList<T> getElements() {
        String query = "SELECT * FROM entrenadores;";

        ArrayList<Entrenador> entrenadors =  new ArrayList<>();

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            psmt= Conn.prepareStatement(query);
            psmt.executeQuery();

            resultSet = psmt.getResultSet();

            //
            while(resultSet.next()) {
                entrenador = new Entrenador(
                        resultSet.getString("nombres"),
                        resultSet.getString("apellidos"),
                        new java.util.Date(resultSet.getDate("fechanacimiento").getTime()),
                        resultSet.getString("sexo").charAt(0) ,
                        resultSet.getString("matricula"),
                        resultSet.getString("cedula"),
                        resultSet.getString("email"),
                        resultSet.getString("telcel"),
                        resultSet.getString("telres")
                );
                entrenadors.add(entrenador);
            }
            psmt.close();
            Conn.close();
        } catch(Exception e) {
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
        }

        // The interface ArticleDbService relies upon the generic type T so we cast it back
        return (ArrayList<T>) entrenadors;
    }

    @Override
    public   Boolean update(T entity) {
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {
            String updateQuery =
                    "update entrenadores set nombres=?, apellidos= ?, fechanacimiento=?, sexo=?, matricula= ?,  cedula=?, " +
                            "email=?, telres=?," +
                            " telcel=? where matricula = ?;";

            psmt = Conn.prepareStatement(updateQuery);

            psmt.setString(1, entity.getNombre());
            psmt.setString(2, entity.getApellidos());
            psmt.setDate(3, new java.sql.Date(entity.getFechaNacimiento().getTime()));
            psmt.setString(4, String.valueOf(entity.getSexo()));
            //psmt.setString(5, insert.getMatricula());
            psmt.setString(5, entity.getMatricula());
            psmt.setString(6, entity.getCedula());
            psmt.setString(7, entity.getEmail());
            psmt.setString(8, entity.getTelRes());
            psmt.setString(9, entity.getTelCel());
            psmt.setString(10, entity.getMatricula());
            psmt.executeUpdate();

            psmt.close();
            Conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

            try {
                if (null != psmt) {
                    psmt.close();
                }
                if (null != Conn) {
                    Conn.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return false;
        }
        return true;
    }


    @Override
    public T readOne(String matricula) {
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            String selectQuery = "SELECT * FROM entrenadores where matricula= ?";

            psmt = Conn.prepareStatement(selectQuery);
            psmt.setString(1, matricula);
            psmt.executeQuery();


            resultSet = psmt.getResultSet();

            if(resultSet.next()) {
                entrenador = new Entrenador(
                        resultSet.getString("nombres"),
                        resultSet.getString("apellidos"),
                        new java.util.Date(resultSet.getDate("fechanacimiento").getTime()),
                        resultSet.getString("sexo").charAt(0),
                        resultSet.getString("matricula"),
                        resultSet.getString("cedula"),
                        resultSet.getString("email"),
                        resultSet.getString("telcel"),
                        resultSet.getString("telres")
                );
                psmt.close();
                Conn.close();

            }
        } catch(Exception e) {
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
            return null;
        }
        return (T) entrenador;
    }

    @Override
    public Boolean delete(String matricula) {

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {

            String deleteQuery = "DELETE FROM entrenadores WHERE matricula = ?";

            psmt = Conn.prepareStatement(deleteQuery);
            psmt.setString(1,matricula);
            psmt.executeUpdate();
            psmt.close();
            Conn.close();


        }catch (Exception e) {
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
            return  false;
        }

        return true;
    }
}
