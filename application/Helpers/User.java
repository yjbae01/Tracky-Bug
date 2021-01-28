package Helpers;

import java.sql.*;
import java.util.HashMap;

public class User {

    private static Connection con;
    private static Statement st;
    public static Boolean realuser = false;
    public static HashMap<String, String> allusers = new HashMap<String, String>();
    private static String currentUser;

    public static void setCurrentUser(String currentUser){
        User.currentUser = currentUser;
    }

    public static String getCurrentUser(){
        return currentUser;
    }


    public static void userSearcher(String user, String pass) throws SQLException {
        String usr;
        String pwd;
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        Statement st = con.createStatement();

        String sql = ("SELECT username, password FROM users WHERE username = '"+user+"' AND password = '"+pass+"';");
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()) {
            usr = rs.getString("username");
            pwd = rs.getString("password");
            if (usr != null && pwd != null){
                realuser = true;
            }
        }
    }

    public static String[] getUserInformation(String username) throws SQLException{
        String[] userData = new String[4];
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        Statement st = con.createStatement();

        String sql = ("SELECT * FROM users WHERE username = '"+username+"'");
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()){
            userData[0] = rs.getString("username");
            userData[1] = rs.getString("password");
            userData[2] = rs.getString("level");
        }
        return userData;
    }

    public static void createUser(String username, String password, String level) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        Statement st = con.createStatement();

        String sql = ("INSERT INTO users VALUES ('"+username+"','"+password+"','"+level+"')");
        st.executeQuery(sql);
        System.out.println(username+" successfully created");
    }

    public static void getAllUsers() throws SQLException {

        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        st = con.createStatement();
        allusers.clear();
        String sql = ("SELECT username, level FROM users");
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()) {
            allusers.put(rs.getString("username"), rs.getString("level"));
        }

    }

    public static void removeUser(String username) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        st = con.createStatement();

        String sql = ("DELETE FROM users WHERE username = '"+ username +"'");
        st.executeQuery(sql);
    }

    public static void updateUser(String originalName, String username, String password, String level) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        st = con.createStatement();

        String sql = ("UPDATE users SET username = '"+username+"', password = '"+password+"', level = '"+level+"' WHERE username = '"+originalName+"';");
        st.executeQuery(sql);

    }

    public static boolean isUserAdmin(String username) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "123");
        st = con.createStatement();

        String sql = ("SELECT username FROM users WHERE username = '"+username+"' AND level = 'Admin';");
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }


}
