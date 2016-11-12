/**
 * Created by Dany on 07/10/2016.
 */
import java.sql.DriverManager;
import java.sql.Connection;
import  java.sql.SQLException;

public class DbConnection {
    private String DB_NAME;
    private String USER_NAME;
    private String PASSWORD;
    private Connection Conn;
    private int PORT;

    public DbConnection(String username, String passWord, String db_Name, int port){
        this.DB_NAME= db_Name;
        this.USER_NAME = username;
        this.PASSWORD= passWord;
        this.PORT = port;
    }

    public Connection Connect(){
        try{
            Class.forName("org.postgresql.Driver");

        }
        catch(ClassNotFoundException e){
            System.out.println("PostGresql driver not found!");

        }
        System.out.println("Postgresql JDBC Driver Registered!");

        Conn = null;

        try{
            Conn = DriverManager.getConnection("jdbc:postgresql://localhost:"+PORT+"/"+DB_NAME , USER_NAME, PASSWORD);
        }catch(SQLException e){
            System.out.println("Connection Failed check output Console: ");
            e.printStackTrace();

        }
        if (Conn!=null)
        {
            System.out.println("Connected to the database!!!");
        }
        else{
            System.out.println("Failed to make Connection to the database!!!");
        }

        return Conn;
    }
}
