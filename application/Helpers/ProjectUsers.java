package Helpers;

import java.sql.*;

public class ProjectUsers {
    private static Connection con;
    private static Statement st;

    public static void updateUserPermissions(String username, int projectid, String level) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        st = con.createStatement();

        String sql = ("INSERT INTO projectusers VALUES ('"+username+"','"+projectid+"','"+level+"')");
        st.executeQuery(sql);
    }

    public static void createUser(String username, String password, String project, String level) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        st = con.createStatement();
        String sql = ("INSERT INTO projectusers VALUES ('"+username+"','"+project+"','"+level+"')");
        User.createUser(username,password);
        st.executeQuery(sql);
    }

    public static boolean isUserAdmin(String username) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        st = con.createStatement();

        String sql = ("SELECT user FROM projectusers WHERE user = '"+username+"' AND level = 'Admin';");
        ResultSet rs = st.executeQuery(sql);

        return rs.next();
    }

    public static boolean isUserProject(int projectid) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        st = con.createStatement();

        String sql = ("SELECT user FROM projectusers WHERE project_id ='"+projectid+"'");
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }


}
