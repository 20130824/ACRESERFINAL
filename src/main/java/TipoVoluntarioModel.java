import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dany on 03/12/2016.
 */
public class TipoVoluntarioModel <T extends TipoVoluntario>implements ICRUD<T> {

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
    private TipoVoluntario tiposvoluntario;

    public TipoVoluntarioModel(int port, String passWord, String db_Name, String username ){
        this.port = port;
        this.passWord = passWord;
        this.db_Name = db_Name;
        this.username = username;
        id = new GenerateID();
    }
    @Override
    public Boolean insert(T entity) {
        String codigo =id.generateSessionKey(6);
        String sql = "insert into tiposvoluntarios(nombre, descripcion, codigo) Values ( ?, ?, ?);";
        String sql2 = "Select codigo from tiposvoluntarios where codigo= ?";
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {

            psmt= Conn.prepareStatement(sql);

            //verificando si el codigo existe
            psmt1 = Conn.prepareStatement(sql2);
            psmt1.setString(1, codigo);
            psmt1.executeQuery();
            resultSet= psmt1.getResultSet();

            psmt.setString(1, entity.getNombre());
            psmt.setString(2, entity.getDescripcion());
            if(resultSet.next()) {
                codigo = id.generateSessionKey(6);
                psmt.setString(3, codigo);
            }else {
                psmt.setString(3, codigo);
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
        String query = "SELECT * FROM tiposvoluntarios;";

        ArrayList<TipoVoluntario> tiposvoluntarios =  new ArrayList<>();

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            psmt= Conn.prepareStatement(query);
            psmt.executeQuery();

            resultSet = psmt.getResultSet();


            while(resultSet.next()) {
                tiposvoluntario = new TipoVoluntario(
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("codigo")
                );

                tiposvoluntarios.add(tiposvoluntario);
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


        return (ArrayList<T>) tiposvoluntarios;
    }

    @Override
    public Boolean update(T entity) {
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {
            String updateQuery =
                    "update tiposvoluntarios set nombre=?, descripcion= ?, codigo=?  where codigo = ?;"
                    ;

            psmt = Conn.prepareStatement(updateQuery);

            psmt.setString(1, entity.getNombre());
            psmt.setString(2, entity.getDescripcion());
            psmt.setString(3, entity.getCodigo());
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
            String selectQuery = "SELECT * FROM tiposvoluntarios  where codigo= ?";

            psmt = Conn.prepareStatement(selectQuery);
            psmt.setString(1, codigo);
            psmt.executeQuery();


            resultSet = psmt.getResultSet();

            if(resultSet.next()) {
                tiposvoluntario = new TipoVoluntario(
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("codigo")
                );
                psmt.close();
                Conn.close();

            }
        } catch(Exception e) {
            System.out.println(e.getMessage()) ;

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
        return (T)tiposvoluntario;
    }


    @Override
    public Boolean delete(String id) {
        return null;

    }

    @Override
    public ArrayList<T> otherStuff() {
        return null;
    }
}
