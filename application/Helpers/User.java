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
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
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
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        Statement st = con.createStatement();

        String sql = ("SELECT * FROM users WHERE username = '"+username+"'");
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()){
            userData[0] = rs.getString("username");
            userData[1] = rs.getString("password");
        }
        return userData;
    }

    public static void createUser(String username, String password) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        Statement st = con.createStatement();

        String sql = ("INSERT INTO users VALUES ('"+username+"','"+password+"')");
        st.executeQuery(sql);
    }

    public static void getAllUsers() throws SQLException {

        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        st = con.createStatement();
        allusers.clear();
        String sql = ("SELECT user, level FROM projectusers");
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()) {
            allusers.put(rs.getString("user"), rs.getString("level"));
        }
    }

    public static void removeUser(String username) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        st = con.createStatement();

        String sql = ("DELETE a.*, b.* \n" +
                "FROM projectusers a \n" +
                "LEFT JOIN users b \n" +
                "ON b.username = a.user \n" +
                "WHERE a.user = '"+username+"'");

        st.executeQuery(sql);
    }

    public static void updateUser(String originalName, String username, String password) throws SQLException {
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/trackybug", "root", "");
        st = con.createStatement();

        String sql = ("UPDATE users SET username = '"+username+"', password = '"+password+"' WHERE username = '"+originalName+"';");
        st.executeQuery(sql);

    }



}
