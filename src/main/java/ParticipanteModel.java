import java.util.ArrayList;
import java.sql.*;

/**
 * Created by Dany on 05/11/2016.
 */
public class ParticipanteModel<T  extends Participante> implements ICRUD<T> {
    private  DbConnection connection;
    private  Connection Conn ;
    private String username;
    private String passWord;
    private String db_Name;
    private int port;
    private ResultSet resultSet;
    private PreparedStatement psmt;
    private GenerateID id;
    private Participante participante;

    public ParticipanteModel(int port, String passWord, String db_Name, String username ){
        this.port = port;
        this.passWord = passWord;
        this.db_Name = db_Name;
        this.username = username;
        id = new GenerateID();
    }

    @Override
    @SuppressWarnings("Unchecked")
    public Boolean insert( T insert) {
        String sql = "insert into participantes(nombres, apellidos, fechanacimiento, sexo, matricula, cedula, email, telres, telcel) Values (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {


            psmt= Conn.prepareStatement(sql);

            psmt.setString(1, insert.getNombre());
            psmt.setString(2, insert.getApellidos());
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
    @SuppressWarnings("Unchecked")
    public ArrayList <T> getElements() {
        String query = "SELECT * FROM participantes;";

        ArrayList<Participante> participantes =  new ArrayList<>();

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            psmt= Conn.prepareStatement(query);
            psmt.executeQuery();

             resultSet = psmt.getResultSet();

            while(resultSet.next()) {
                participante = new Participante(
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

                participantes.add(participante);
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


        return (ArrayList<T>) participantes;
    }

    @Override
    public Boolean update(T entity) {
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {
            String updateQuery =
                    "update participantes set nombres=?, apellidos= ?, fechanacimiento=?, sexo=?, matricula= ?,  cedula=?, " +
                            "email=?, telres=?," +
                            " telcel=? where matricula = ?;"
                    ;

            psmt = Conn.prepareStatement(updateQuery);

            psmt.setString(1, entity.getNombre());
            psmt.setString(2, entity.getApellidos());
            psmt.setDate(3, new java.sql.Date(entity.getFechaNacimiento().getDate()));
            psmt.setString(4, String.valueOf(entity.getSexo()));
            //psmt.setString(5, insert.getMatricula());
            psmt.setString(5, entity.getMatricula());
            psmt.setString(6, entity.getCedula());
            psmt.setString(7, entity.getEmail());
            psmt.setString(8, entity.getTelRes());
            psmt.setString(9, entity.getTelCel());
            psmt.setString(10, entity.getMatricula());
            psmt.executeUpdate();
            return true;
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
            return false;
        }
    }

    @Override
    @SuppressWarnings("Uncheked")
    public T readOne(String matricula) {
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            String selectQuery = "SELECT * FROM participantes where matricula= ?";

            psmt = Conn.prepareStatement(selectQuery);
            psmt.setString(1, matricula);
            psmt.executeQuery();


            resultSet = psmt.getResultSet();

            if(resultSet.next()) {
                 participante = new Participante(
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
        return (T) participante;
    }

    @Override
    public Boolean delete(String matricula) {
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {

            String deleteQuery = "DELETE FROM participantes WHERE matricula = ?";

            psmt = Conn.prepareStatement(deleteQuery);
            psmt.setString(1, matricula);
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

