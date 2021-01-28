package Connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerThread extends Thread{
    private Socket clientSocket;
    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run(){
        try {

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
