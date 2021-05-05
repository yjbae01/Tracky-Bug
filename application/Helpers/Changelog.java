package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Changelog {

    private static Connection con;
    private static Statement st;

    public static String generateLogDescription(String action, String name, String type){
        String description;

        description = "The "+type+" "+name+" has been "+action+"ed";

        return description;

    }

    public static void addProjectLog(String description, int projectid, String datemodified, String modifiedby) throws SQLException {

        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        Statement st = con.createStatement();
        String sql = ("INSERT INTO changelog (description, project_id, modified, modifiedby)" +
                " VALUES ('"+description+"','"+projectid+"','"+datemodified+"','"+modifiedby+"');");

        st.executeQuery(sql);
    }

    public static void addBugLog(String description, String bugid, String datemodified, String modifiedby) throws SQLException {

        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        Statement st = con.createStatement();
        String sql = ("INSERT INTO changelog (description, bug_id, modified, modifiedby)" +
                " VALUES ('"+description+"','"+bugid+"','"+datemodified+"','"+modifiedby+"');");

        st.executeQuery(sql);
    }



}
