import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dany on 07/12/2016.
 */
public class OtherVolunteerModel<T extends OtherVolunteer> implements ICRUD<T> {
    private DbConnection connection;
    private Connection Conn;
    private String username;
    private String passWord;
    private String db_Name;
    private int port;
    private ResultSet resultSet;
    private PreparedStatement psmt;
    private GenerateID id;
    private OtherVolunteer volunteer;

    public OtherVolunteerModel(int port, String passWord, String db_Name, String username) {
        this.port = port;
        this.passWord = passWord;
        this.db_Name = db_Name;
        this.username = username;
        id = new GenerateID();
    }

    @Override
    public Boolean insert(T entity) {
        return null;
    }

    @Override
    public ArrayList<T> getElements() {
        return null;
    }

    @Override
    public Boolean update(T entity) {
        return null;
    }

    @Override
    public T readOne(String id) {
        return null;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }

    @Override
    @SuppressWarnings("Unchecked")
    public ArrayList<T> otherStuff() {
        String query = "select p.nombres, p.apellidos, p.matricula, " +
                " t.nombre, v.fechainicio " +
                " FROM participantes p INNER JOIN voluntarios v" +
                " ON p.matricula = v.participanteid" +
                " INNER JOIN  tiposvoluntarios t ON v.tipovoluntarioid=t.codigo;";

        ArrayList<OtherVolunteer> volunteers = new ArrayList<>();
        connection = new DbConnection(username, passWord, db_Name, port);
        Conn = connection.Connect();

        try {
            psmt = Conn.prepareStatement(query);
            psmt.executeQuery();

            resultSet = psmt.getResultSet();
            // String cedula, String email, String telCel, String telRes , float balance

            while (resultSet.next()) {
                volunteer = new OtherVolunteer(
                        new TipoVoluntario(resultSet.getString("nombre"), null, null),
                        new Participante(resultSet.getString("nombres"), resultSet.getString("apellidos"), null, '0', resultSet.getString("matricula"), null, null, null, null, 0),
                        new Voluntario(null, null, new java.util.Date(resultSet.getDate("fechainicio").getTime()), null));

                volunteers.add(volunteer);
            }
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
        }
        return (ArrayList<T>) volunteers;
    }
}

