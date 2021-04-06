package main;

import java.io.IOException;
import java.sql.SQLException;

public class Server {

    public static void main (String[] args){
        Connections.ServerConnection serverConnection = new Connections.ServerConnection();
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Driver Connected!");
            serverConnection.StartConnection();
            serverConnection.runServer();
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        } catch(IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (NullPointerException npe){
            npe.printStackTrace();
        }finally {
            serverConnection.endConnection();
            System.out.println("Server Connection has ended.");
        }
    }
}
