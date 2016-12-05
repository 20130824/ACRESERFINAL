import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by Dany on 06/11/2016.
 */
public class PagoModel <T extends  Pago> implements  ICRUD<T> {
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
    private Pago pago;

    public PagoModel(int port, String passWord, String db_Name,String username ){
        this.port = port;
        this.passWord = passWord;
        this.db_Name = db_Name;
        this.username = username;
        id = new GenerateID();
    }

    @Override
    public Boolean insert(T entity) {
        String sql = "insert into historialpagos(fechapagado, montopagado, participante, codigo_pago, grupo) Values (?, ?, ?, ?, ?);";
        String matricula =id.generateSessionKey(6);
        String sql2 = "Select codigo_pago from entrenadores where codigo_pago= ?";
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {

            psmt= Conn.prepareStatement(sql);

            //verificando si el codigo existe
            psmt1 = Conn.prepareStatement(sql2);
            psmt1.setString(1, matricula);
            psmt1.executeQuery();
            resultSet= psmt1.getResultSet();

            psmt.setDate(1, new Date(entity.getFechaPagado().getTime()));
            psmt.setFloat(2, entity.getMontoPagado());
            psmt.setString(3, entity.getCodigoParticpante());
            if(resultSet.next()) {
                matricula = id.generateSessionKey(6);
                psmt.setString(4, matricula);
            }else {
                psmt.setString(4, matricula);
            }
            psmt.setString(5, entity.getGrupo());
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
        String query = "SELECT * FROM historialpagos;";

        ArrayList<Pago> pagos =  new ArrayList<>();

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            psmt= Conn.prepareStatement(query);
            psmt.executeQuery();

            resultSet = psmt.getResultSet();

            while(resultSet.next()) {
                pago = new Pago(
                        new java.util.Date(resultSet.getDate("fechapagado").getTime()),
                        resultSet.getFloat("montopagado"),
                        resultSet.getString("participante"),
                        resultSet.getString("codigo_pago"),
                        resultSet.getString("grupo"));
                pagos .add(pago);
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
        return (ArrayList<T>)pagos;
    }

    @Override
    public Boolean update(T entity) {

        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();
        try {
            String updateQuery =
                    "update historialpagos set fechapagado=?, montopagado= ?, participante=?, codigo_pago=?, grupo= ? where codigo_pago = ?;";

            psmt = Conn.prepareStatement(updateQuery);

            psmt.setDate(1, new Date(entity.getFechaPagado().getTime()));
            psmt.setFloat(2, entity.getMontoPagado());
            psmt.setString(3, entity.getCodigoParticpante());
            psmt.setString(4, entity.getCodigo());
            //psmt.setString(5, insert.getMatricula());
            psmt.setString(5, entity.getGrupo());
            psmt.setString(6, entity.getCodigo());
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
            String selectQuery = "SELECT * FROM historialpagos where codigo_pago= ?";

            psmt = Conn.prepareStatement(selectQuery);
            psmt.setString(1, matricula);
            psmt.executeQuery();


            resultSet = psmt.getResultSet();

            if(resultSet.next()) {
                pago = new Pago(
                        new java.util.Date(resultSet.getDate("fechapagado").getTime()),
                        resultSet.getFloat("montopagado"),
                        resultSet.getString("participante"),
                        resultSet.getString("codigo_pago"),
                        resultSet.getString("grupo")
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
        return (T) pago;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }
}
