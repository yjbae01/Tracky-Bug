package Helpers;

import java.lang.constant.Constable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {
    private static Connection con;
    private static Statement st;
    public static HashMap<String, List<String>> projects = new HashMap<String, List<String>>();
    private static int projectid;
    public static boolean hasprojects;
    private static String currentProject;

    public static void setCurrentProject(String projectname){
        currentProject = projectname;
    }

    public static String getCurrentProject(){
        return currentProject;
    }

    public static void addProject(String projectname) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        Statement st = con.createStatement();

        String sql = ("INSERT INTO projects (name, createdby) " +
                "VALUES ('"+projectname+"','"+User.getCurrentUser()+"')");
        System.out.println(User.getCurrentUser());

        st.executeQuery(sql);
        System.out.println(projectname+" has been succesfully created!");
    }

    public static void getProjects(String username) throws SQLException {
        projects.clear();
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        Statement st = con.createStatement();

        String sql = ("SELECT name FROM projects WHERE createdby = '"+username+"';");
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            projects.putIfAbsent(username, new ArrayList<String>());
            projects.get(username).add(rs.getString("name"));
        }
        if (projects.get(username) != null){
            hasprojects = true;
        }
    }

    public static int getProjectID(String projectname) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        Statement st = con.createStatement();

        String sql = ("SELECT id FROM projects WHERE name = '"+projectname+"';");
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()){
            projectid = rs.getInt("id");
        }

        return projectid;
    }

    public static void updateProject(String name, int id) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        st = con.createStatement();

        String sql = ("UPDATE projects SET name = '"+name+"' WHERE id = '"+id+"';");
        st.executeQuery(sql);
    }


    public static void removeProject(int id) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        Statement st = con.createStatement();

        String sql = ("DELETE FROM projects WHERE id = '"+id+"';");
        st.executeQuery(sql);
    }
}