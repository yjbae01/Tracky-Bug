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
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "root", "");
    }
    public static void createDB() throws SQLException{
        st = conn.createStatement();
        final String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS trackybug";
        st.execute(CREATE_DATABASE);
        conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
    }

    public static Connection getConn(){
        return conn;
    }

    public static void createTables() throws SQLException {
        Statement st = conn.createStatement();

        final String CREATE_PROJECTS =
                "CREATE TABLE IF NOT EXISTS projects("
                        + "id INT PRIMARY KEY AUTO_INCREMENT,"
                        + "name VARCHAR(150) UNIQUE NOT NULL,"
                        + "createdby VARCHAR(16) NOT NULL,"
                        + "datecreated DATE NOT NULL"
                        +")";

        final String CREATE_PROJECTSUSERS =
                "CREATE TABLE IF NOT EXISTS projectusers("
                        + "user VARCHAR(16),"
                        + "project_id INT,"
                        + "level SET('Admin', 'User') NOT NULL DEFAULT 'User',"
                        + "FOREIGN KEY (user) REFERENCES users (username),"
                        + "FOREIGN KEY (project_id) REFERENCES projects (id)"
                        +")";

        final String CREATE_CHANGELOG =
                "CREATE TABLE IF NOT EXISTS changelog("
                        + "id INT PRIMARY KEY AUTO_INCREMENT,"
                        + "project_id INT,"
                        + "description VARCHAR(300) NOT NULL,"
                        + "modified DATE NOT NULL,"
                        + "modifiedby VARCHAR(16) NOT NULL,"
                        + "bug_id INT,"
                        + "FOREIGN KEY (project_id) REFERENCES projects (id),"
                        + "FOREIGN KEY (bug_id) REFERENCES bugs (id)"
                        +")";

        final String CREATE_USERS =
                         "CREATE TABLE IF NOT EXISTS users("
                        + "username VARCHAR(16) UNIQUE NOT NULL PRIMARY KEY,"
                        + "password VARCHAR(64) NOT NULL"
                + ")";

        final String CREATE_BUGS =
                "CREATE TABLE IF NOT EXISTS bugs("
                + "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(100) UNIQUE NOT NULL,"
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
        st.execute(CREATE_PROJECTSUSERS);
        st.execute(CREATE_BUGS);
        st.execute(CREATE_CHANGELOG);

    }

    public static void createAdmin() throws SQLException{
        Statement st = conn.createStatement();
        final String DELETE_ADMIN = "DELETE FROM users WHERE username = 'admin'";
        final String CREATE_ADMIN = "INSERT INTO users VALUES ('admin','123')";
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
