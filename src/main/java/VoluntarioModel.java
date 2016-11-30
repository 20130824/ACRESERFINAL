import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dany on 06/11/2016.
 */
public class VoluntarioModel<T extends Voluntario> implements ICRUD<T> {
    private  DbConnection connection;
    private Connection Conn ;
    private String username;
    private String passWord;
    private String db_Name;
    private int port;
    private ResultSet resultSet;
    private PreparedStatement psmt;
    private GenerateID id;
    private PreparedStatement psmt1;
    private Voluntario voluntario;

    public VoluntarioModel(int port, String passWord, String db_Name, String username ){
        this.port = port;
        this.passWord = passWord;
        this.db_Name = db_Name;
        this.username = username;
        id = new GenerateID();
    }

    @Override
    public Boolean insert(T entity) {

        String codigo =id.generateSessionKey(6);
        String sql = "insert into voluntarios(participanteid, tipovoluntarioid, fechainicio, codigo) Values (?, ?, ?, ?);";
        String sql2 = "Select codigo from voluntarios where codigo= ?";
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {

            psmt= Conn.prepareStatement(sql);

            //verificando si el codigo existe
            psmt1 = Conn.prepareStatement(sql2);
            psmt1.setString(1, codigo);
            psmt1.executeQuery();
            resultSet= psmt1.getResultSet();

            psmt.setString(1, entity.getParticipanteID()); // need the field of the participante  -- participanteId
            psmt.setString(2, entity.getTipoVoluntario());
            psmt.setDate(3, new java.sql.Date(entity.getFechaInicio().getTime()));


            if(resultSet.next()) {
                codigo = id.generateSessionKey(6);
                psmt.setString(4, codigo);
            }else {
                psmt.setString(4, codigo);
            }

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
    @SuppressWarnings("unchecked")
    public ArrayList<T> getElements() {

        String query = "SELECT * FROM voluntarios;";

        ArrayList<Voluntario> voluntarios =  new ArrayList<>();

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            psmt= Conn.prepareStatement(query);
            psmt.executeQuery();

            resultSet = psmt.getResultSet();


            while(resultSet.next()) {
                  voluntario = new Voluntario(
                        resultSet.getString("participanteid"),
                          resultSet.getString("tipovoluntarioid"),
                          new java.util.Date(resultSet.getDate("fechaincio").getTime()),
                          resultSet.getString("codigo")
                  );

                voluntarios.add(voluntario);
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


        return (ArrayList<T>) voluntarios;
    }

    @Override
    public Boolean update(T entity) {

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {
            String updateQuery =
                    "update voluntarios set participanteid=?, tipovoluntarioid= ?, fechainicio=?, codigo=?  where codigo = ?;"
                    ;

            psmt = Conn.prepareStatement(updateQuery);

            psmt.setString(1, entity.getParticipanteID());
            psmt.setString(2, entity.getTipoVoluntario());
            psmt.setDate(3, new java.sql.Date(entity.getFechaInicio().getTime()));
            psmt.setString(4, entity.getCodigo());

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
    @SuppressWarnings("unchecked")
    public T readOne(String codigo) {

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            String selectQuery = "SELECT * FROM voluntarios  where codigo= ?";

            psmt = Conn.prepareStatement(selectQuery);
            psmt.setString(1, codigo);
            psmt.executeQuery();


            resultSet = psmt.getResultSet();

            if(resultSet.next()) {
                voluntario = new Voluntario(
                        resultSet.getString("participanteid"),
                        resultSet.getString("tipovoluntarioid"),
                        new java.util.Date(resultSet.getDate("fechainicio").getTime()),
                        resultSet.getString("codigo")
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
        return (T)voluntario;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }
}
