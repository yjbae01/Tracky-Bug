package Connections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ServerConnection {
    private static ServerSocket serversocket;

    public void StartConnection() throws SQLException, IOException, NullPointerException {
        try {
            System.out.println("Establishing connection to database...");
            InitiateDB.connectDatabase();
            InitiateDB.createDB();
            System.out.println("Database successfully connected!");
        }catch (SQLException sqe){
            System.out.println("Failed to connect to database...");
        }
        InitiateDB.createTables();
        InitiateDB.createAdmin();

        serversocket = new ServerSocket(8000);
        System.out.println("Server is now running...");
    }

    public static void loginSuccess(String username){
        System.out.println(username+" has successfully logged in!");
    }

    public void runServer() {

        while(true){
            try{
                Socket clientSocket = serversocket.accept();
                System.out.println("Server is now running...");
                new ServerThread(clientSocket).start();

            }catch(IOException ioex){
                ioex.printStackTrace();
                break;
            }
        }
    }

    public static void clientSocketclose() throws IOException {
        serversocket.close();
    }


    public void endConnection() {
        try{
            InitiateDB.closeConnection();
        } catch(SQLException sqlioex){
            sqlioex.printStackTrace();
        }
    }
}
