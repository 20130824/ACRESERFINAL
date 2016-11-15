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
    private PreparedStatement psmt1;
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
        String codigo =id.generateSessionKey(6);
        String sql = "insert into grupos (fechainicio, fechafin, codigo, tipo, grupo, entrenador, precio,  cupo, nombre, fechapago1, fechapago2, fechapago3) Values (?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?);";
        String sql2 = "Select codigo from grupos where codigo= ?";
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {


            psmt= Conn.prepareStatement(sql);

            //verificando si el codigo existe
            psmt1 = Conn.prepareStatement(sql2);
            psmt1.setString(1, codigo);
            psmt1.executeQuery();
            resultSet= psmt1.getResultSet();

            psmt.setDate    (1,  new Date(entity.getFechaInicio().getTime()));
            psmt.setDate    (2,  new Date(entity.getFechaFin().getTime()));
            if(resultSet.next()) {
                codigo = id.generateSessionKey(6);
                psmt.setString(3, codigo);
            }else {
                psmt.setString(3, codigo);
            }
            psmt.setString  (4,  entity.getTipo().getCodigo());
            psmt.setString  (5,  entity.getGrupo());
            psmt.setString  (6,  entity.getCodigoEntrenador());
            psmt.setFloat   (7,  entity.getPrecio());
            psmt.setInt     (8,  entity.getCupo());
            psmt.setString  (9,  entity.getNombre());
            psmt.setDate    (10, new Date( entity.getFechaDePago1().getTime()));
            psmt.setDate    (11, new Date( entity.getFechaDePago2().getTime()));
            psmt.setDate    (12, new Date( entity.getFechaDePago3().getTime()));
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
    @SuppressWarnings("unchecked")
    public ArrayList<T> getElements() {

        String query = "SELECT * FROM grupos;";

        ArrayList<Grupo> grupos =  new ArrayList<>();

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            psmt= Conn.prepareStatement(query);
            psmt.executeQuery();

            resultSet = psmt.getResultSet();

            while(resultSet.next()) {
                grupo = new Grupo(
                        resultSet.getString("codigo"),
                        resultSet.getString("nombre"),
                        new java.util.Date(resultSet.getDate("fechainicio").getTime()),
                        new java.util.Date(resultSet.getDate("fechafin").getTime()),
                        new Taller(null,null,resultSet.getString("tipo")),
                        resultSet.getString("grupo"),
                        resultSet.getString("entrenador"),
                        resultSet.getInt("cupo"),
                        resultSet.getFloat("precio"),
                        resultSet.getDate("fechadepago1"),
                        resultSet.getDate("fechadepago2"),
                        resultSet.getDate("fechadepago3")
                );

                grupos.add(grupo);
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
        return (ArrayList<T>)grupos;
    }

    @Override
    public Boolean update(T entity) {

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {
            String updateQuery =
                    "update grupos set fechainicio=?, fechafin=?, codigo=?, tipo=?, entrenador=?, " +
                            "precio=?, cupo=?," +
                            " nombre=?, fechadepago1=?, fechadepago2=? , fechadepago3=?,  grupo= ? where codigo= ?;"
                    ;

            psmt = Conn.prepareStatement(updateQuery);

            psmt.setDate   (1,  new Date(entity.getFechaInicio().getTime()));
            psmt.setDate   (2,  new Date( entity.getFechaFin().getTime()));
            psmt.setString (3,  entity.getCodigo());
            psmt.setString (4,  entity.getTipo().getCodigo());
            //psmt.setString(5, insert.getMatricula());
            psmt.setString (5,  entity.getCodigoEntrenador());
            psmt.setFloat  (6,  entity.getPrecio());
            psmt.setInt    (7,  entity.getCupo());
            psmt.setString (8,  entity.getNombre());
            psmt.setDate   (9,  new Date( entity.getFechaDePago1().getTime()));
            psmt.setDate   (10, new Date( entity.getFechaDePago2().getTime()));
            psmt.setDate   (11, new Date( entity.getFechaDePago3().getTime()));
            psmt.setString (12, entity.getGrupo());
            psmt.setString (13, entity.getCodigo());

            psmt.executeUpdate();
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
    @SuppressWarnings("unchecked")
    public T readOne(String codigo ) {
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            String selectQuery = "SELECT * FROM grupos where codigo= ?";

            psmt = Conn.prepareStatement(selectQuery);
            psmt.setString(1, codigo);
            psmt.executeQuery();

            resultSet = psmt.getResultSet();

            if(resultSet.next()) {
                grupo = new Grupo (
                        resultSet.getString("codigo"),
                        resultSet.getString("nombre"),
                        new java.util.Date(resultSet.getDate("fechainicio").getTime()),
                        new java.util.Date(resultSet.getDate("fechafin").getTime()),
                        new Taller (null, null, resultSet.getString("tipo")),
                        resultSet.getString("grupo"),
                        resultSet.getString("entrenador"),
                        resultSet.getInt("cupo"),
                        resultSet.getFloat("precio"),
                        resultSet.getDate("fechadepago1"),
                        resultSet.getDate("fechadepago2"),
                        resultSet.getDate("fechadepago3")
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
        return (T) grupo;
    }

    @Override
    public Boolean delete(String id) {

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {

            String deleteQuery = "DELETE FROM grupos WHERE codigo = ?";

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
