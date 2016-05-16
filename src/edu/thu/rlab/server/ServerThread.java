package edu.thu.rlab.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

class ServerThread extends Thread {
    // 
    Socket socket = null;
    DBEditor dbeditor = null;
    ResourceManager rmanager = null;
    public ServerThread(Socket socket, DBEditor db, ResourceManager rm){
        this.socket = socket;
        this.dbeditor = db;
        this.rmanager = rm;
    }
 
    public void run(){
    	try{
	    	ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
	        ServerMessage ms = (ServerMessage) inputStream.readObject();
	        switch(ms.getType()){
	        	case ServerMessage.RESOURCE_SERVER_AUTHEN:
	        		if(ms.getAuthenCode().equals(MainServer.authenCode)){
	        			System.out.println("Authentication Succeed.");
	        		}else{
	        			System.out.println("Authentication Failed.");
	        		}
	        		break;
	        	case ServerMessage.RESOURCE_SERVER_UPDATE_COURSE:
	        		System.out.println(ms.getCourse().getName());
	        		synchronized(dbeditor){
	        			dbeditor.create(ms.getCourse());
	        		}
	        		break;
	        	default:
	        		break;
	        }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	System.out.print("class not found");
			e.printStackTrace();
		}finally {
        } 
    }
}
