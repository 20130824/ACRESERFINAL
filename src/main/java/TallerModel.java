import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Dany on 06/11/2016.
 */
public class TallerModel<T extends  Taller> implements ICRUD<T> {
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
    private Taller taller;


    public TallerModel(int port, String passWord, String db_Name,String username ){
        this.port = port;
        this.passWord = passWord;
        this.db_Name = db_Name;
        this.username = username;
        id = new GenerateID();
    }

    @Override
    public Boolean insert(T entity) {

        String codigo =id.generateSessionKey(6);
        String sql = "insert into talleres (nombre, descripcion, codigo) Values (?, ?, ?);";
        String sql2 = "Select codigo from talleres where codigo= ?";
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {

            psmt= Conn.prepareStatement(sql);

            //verificando si el codigo existe
            psmt1 = Conn.prepareStatement(sql2);
            psmt1.setString(1, codigo);
            psmt1.executeQuery();
            resultSet= psmt1.getResultSet();

            psmt.setString    (1, entity.getNombre());
            psmt.setString    (2, entity.getDescripcion());
            if(resultSet.next()) {
                codigo = id.generateSessionKey(6);
                psmt.setString(3, codigo);
            }else {
                psmt.setString(3, codigo);
            }
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
        String query = "SELECT * FROM grupos;";

        ArrayList<Taller> talleres =  new ArrayList<>();

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            psmt= Conn.prepareStatement(query);
            psmt.executeQuery();

            resultSet = psmt.getResultSet();

            while(resultSet.next()) {
                taller = new Taller(
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("codigo")
                );

               talleres.add(taller);
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
        return (ArrayList<T>)talleres;
    }

    @Override
    public Boolean update(T entity) {

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {
            String updateQuery =
                    "update talleres set nombre=?, descripcion=?, codigo=? where codigo= ?;";

            psmt = Conn.prepareStatement(updateQuery);

            psmt.setString   (1,  entity.getNombre());
            psmt.setString   (2,  entity.getDescripcion());
            psmt.setString   (3,  entity.getCodigo());
            psmt.setString   (4,  entity.getCodigo());

            psmt.executeUpdate();
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
            return false;
        }
        return true;
    }

    @Override
    public T readOne(String codigo) {

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            String selectQuery = "SELECT * FROM talleres where codigo= ?";

            psmt = Conn.prepareStatement(selectQuery);
            psmt.setString(1, codigo);
            psmt.executeQuery();

            resultSet = psmt.getResultSet();

            if(resultSet.next()) {
                taller = new Taller (
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
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
        return (T) taller;
    }

    @Override
    public Boolean delete(String id) {
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {

            String deleteQuery = "DELETE FROM talleres WHERE codigo = ?";

            psmt = Conn.prepareStatement(deleteQuery);
            psmt.setString(1, id);
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
