import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dany on 21/11/2016.
 */
public class CicloModel<T extends  Ciclo> implements ICRUD<T> {
    private  DbConnection connection;
    private Connection Conn ;
    private String username;
    private String passWord;
    private String db_Name;
    private int port;
    private ResultSet resultSet;
    private PreparedStatement psmt;
    private PreparedStatement psmt1;
    private GenerateID id;
    private Ciclo ciclo;

    public CicloModel(int port, String passWord, String db_Name,String username ){
        this.port = port;
        this.passWord = passWord;
        this.db_Name = db_Name;
        this.username = username;
        id = new GenerateID();
    }


    @Override
    public Boolean insert(T entity) {

        String codigo =id.generateSessionKey(6);
        String sql = "insert into ciclo(nombre, codigo, fechainicio, fechafin , tipo, talleres) Values (?, ?, ?, ?,?, ?);";
        String sql2 = "Select codigo from ciclo where codigo= ?";
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {

            psmt= Conn.prepareStatement(sql);

            //verificando si el codigo existe
            psmt1 = Conn.prepareStatement(sql2);
            psmt1.setString(1, codigo);
            psmt1.executeQuery();
            resultSet= psmt1.getResultSet();

            psmt.setString   (1, entity.getNombre()); // need the field of the participante  -- participanteId
            if(resultSet.next()) {
                codigo = id.generateSessionKey(6);
                psmt.setString(2, codigo);
            }else {
                psmt.setString(2, codigo);
            }
            psmt.setDate      (3, new java.sql.Date(entity.getFechaInicio().getTime()));
            psmt.setDate      (4, new java.sql.Date(entity.getFechaFin().getTime()));
            psmt.setInt       (5, entity.getTipo());
            psmt.setString    (6, entity.getTalleres().getNombre() );

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
        String query = "SELECT * FROM ciclo;";

        ArrayList<Ciclo> ciclos =  new ArrayList<>();

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            psmt= Conn.prepareStatement(query);
            psmt.executeQuery();
            resultSet = psmt.getResultSet();

            while(resultSet.next()) {
                ciclo = new Ciclo(
                        resultSet.getString("nombre"),
                        resultSet.getString("Codigo"),
                        new java.util.Date(resultSet.getDate("fechainicio").getTime()),
                        new java.util.Date(resultSet.getDate("fechafin").getTime()),
                        resultSet.getInt("tipo"),
                        new Taller(resultSet.getString("talleres"), null, null, null)
                );

                ciclos.add(ciclo);
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


        return (ArrayList<T>) ciclos;
    }

    @Override
    public Boolean update(T entity) {
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {
            String updateQuery =
                    "update ciclo set nombre=?, codigo= ?, fechaincio=?, fechafin=?, tipo=? , talleres=?  where codigo = ?;"
                    ;

            psmt = Conn.prepareStatement(updateQuery);

            psmt.setString(1, entity.getNombre());
            psmt.setString(2, entity.getCodigo());
            psmt.setDate  (3, new java.sql.Date(entity.getFechaInicio().getTime()));
            psmt.setDate  (4, new java.sql.Date(entity.getFechaFin().getTime()));
            psmt.setInt   (5, entity.getTipo());
            psmt.setString(6, entity.getTalleres().getNombre());
            psmt.setString(7, entity.getCodigo());

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
            String selectQuery = "SELECT * FROM ciclo  where codigo= ?";

            psmt = Conn.prepareStatement(selectQuery);
            psmt.setString(1, codigo);
            psmt.executeQuery();


            resultSet = psmt.getResultSet();

            if(resultSet.next()) {
                ciclo = new Ciclo(
                        resultSet.getString("nombre"),
                        resultSet.getString("codigo"),
                        new java.util.Date(resultSet.getDate("fechainicio").getTime()),
                        new java.util.Date(resultSet.getDate("fechafin").getTime()),
                        resultSet.getInt("tipo"),
                        new Taller(resultSet.getString("talleres"), null, null, null)
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
        return (T)ciclo;
    }

    @Override
    public Boolean delete(String codigo) {
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {

            String deleteQuery = "DELETE FROM ciclo WHERE codigo = ?";

            psmt = Conn.prepareStatement(deleteQuery);
            psmt.setString(1, codigo);
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
