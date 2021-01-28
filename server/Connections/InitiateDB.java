package Connections;



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitiateDB {
    private static Connection conn = null;
    private static Statement st = null;


    public static void connectDatabase() throws SQLException {
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "root", "123");
    }
    public static void createDB() throws SQLException{
        st = conn.createStatement();
        final String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS trackybug";
        st.execute(CREATE_DATABASE);
        conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
    }

    public static Connection getConn(){
        Connection connection = conn;
        return connection;
    }

    public static void createTables() throws SQLException {
        Statement st = conn.createStatement();

        final String CREATE_PROJECTS =
                "CREATE TABLE IF NOT EXISTS projects("
                        + "id INT PRIMARY KEY AUTO_INCREMENT,"
                        + "name VARCHAR(150) NOT NULL,"
                        + "createdby VARCHAR(16) NOT NULL"
                        +")";

        final String CREATE_USERS =
                         "CREATE TABLE IF NOT EXISTS users("
                        + "username varchar(16) UNIQUE NOT NULL PRIMARY KEY,"
                        + "password varchar(64) NOT NULL,"
                        + "level set('User','Admin') NOT NULL"
                        + ")";


        final String CREATE_BUGS =
                "CREATE TABLE IF NOT EXISTS bugs("
                + "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(100) NOT NULL,"
                + "description VARCHAR(300) NOT NULL,"
                + "datecreated DATE NOT NULL,"
                + "datedue DATE NOT NULL,"
                + "severity SET ('Minor', 'Major', 'Critical'),"
                + "status SET ('Open','In Progress..', 'Solved'),"
                + "createdby VARCHAR(16) NOT NULL,"
                        +"project_id INT,"
                        +"FOREIGN KEY (project_id) REFERENCES projects (id)"
                +")";
        st.execute(CREATE_USERS);
        st.execute(CREATE_PROJECTS);
        st.execute(CREATE_BUGS);
    }

    public static void createAdmin() throws SQLException{
        Statement st = conn.createStatement();
        final String DELETE_ADMIN = "DELETE FROM users WHERE username = 'admin'";
        final String CREATE_ADMIN = "INSERT INTO users VALUES ('admin','123','Admin')";
        st.execute(DELETE_ADMIN);
        st.execute(CREATE_ADMIN);
        System.out.println("Admin account successfully created.");
    }

    public static void closeConnection() throws SQLException {
        try{
            Connection connection = conn;
            connection.close();
            ServerConnection.clientSocketclose();
        }catch(SQLException sqle){
            System.out.println(sqle);
        }catch(IOException ioex){
            ioex.printStackTrace();
        }
    }
}
