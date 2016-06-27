package edu.thu.rlab.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

	public static final String authenCode = "secret";
	
	/**
     * Runs the server.
     */
    public static void main(String[] args) throws IOException {
    	
        ResourceManager rmanager = new ResourceManager();
        
        // Listening for connection from 
        ServerSocket listener = new ServerSocket(9091,50); // modify: max connections = 50
        int socket_count = 0;
        try {
            while (true) {
                Socket socket = listener.accept();
                ServerThread serverThread = new ServerThread(socket,rmanager);
                serverThread.start();
                socket_count++;
                System.out.println("Resource Servers connected:" + socket_count);// or client
                /*InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的IP ： " + address.getHostAddress());*/
                
                
                /*try {
                    System.out.print(new Date().toString());
                    System.out.print(cs.getName());
                } finally {
                    socket.close();
                }*/
            }
        } catch (Exception e) {
			e.printStackTrace();
		}
        finally {
            listener.close();
        }
    }
}