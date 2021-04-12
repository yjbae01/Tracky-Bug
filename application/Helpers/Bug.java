package Helpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Bug {

    private static Connection con;
    private static Statement st;
    public static HashMap<Integer, ArrayList<String>> projectBugs = new HashMap<Integer, ArrayList<String>>();


    public static void addBug(int projectid, String name, String description, String datecreated, String datedue, String severity,
                              String status, String createdby) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        Statement st = con.createStatement();
        String sql = ("INSERT INTO bugs (project_id, name, description, datecreated, datedue, severity, status, createdby)" +
                " VALUES ('"+projectid+"','"+name+"','"+description+"',"+datecreated+","+datedue+",'"+severity+"','"+status+"','"+createdby+"');");

        st.executeQuery(sql);
    }

    public static void retrieveBugsFromProjectID(int projectid) throws SQLException {
        projectBugs.clear();
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        Statement st = con.createStatement();

        String sql = ("SELECT id,name,description,datecreated,createdby,severity,status,datedue FROM bugs WHERE project_id = "+projectid+";");
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()) {
            projectBugs.putIfAbsent(projectid, new ArrayList<String>());
            projectBugs.get(projectid).add(rs.getString("id"));
            projectBugs.get(projectid).add(rs.getString("name"));
            projectBugs.get(projectid).add(rs.getString("description"));
            projectBugs.get(projectid).add(rs.getString("datecreated"));
            projectBugs.get(projectid).add(rs.getString("createdby"));
            projectBugs.get(projectid).add(rs.getString("severity"));
            projectBugs.get(projectid).add(rs.getString("status"));
            projectBugs.get(projectid).add(rs.getString("datedue"));
        }
    }

    public static int getBugID(String bugname) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        Statement st = con.createStatement();

        int bugid = 0;
        String sql = ("SELECT id FROM bugs WHERE name = '"+bugname+"';");
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()){
            bugid = rs.getInt("id");
        }

        return bugid;
    }

    public static void removeBug(String bugid,int projectid) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        Statement st = con.createStatement();

        String sql = ("DELETE FROM bugs WHERE id = "+bugid+" AND project_id = "+projectid+";");
        st.executeQuery(sql);
    }

    public static void removeBugsFrom(int projectid) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        Statement st = con.createStatement();

        String sql = ("DELETE FROM bugs WHERE project_id = "+projectid+";");
        st.executeQuery(sql);
    }

    public static void updateBug(String bugid,String name, String description, String status) throws SQLException{
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        Statement st = con.createStatement();

        String sql = ("UPDATE bugs SET name = '"+name+"', description = '"+description+"',status = '"+status+"' WHERE id = "+bugid+";");
        st.executeQuery(sql);
    }


}
