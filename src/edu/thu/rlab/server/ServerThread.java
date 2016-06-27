package edu.thu.rlab.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import edu.thu.rlab.pojo.*;

class ServerThread extends Thread {
    // 
    Socket socket = null;
    DBEditor dbeditor = null;
    ResourceManager rmanager = null;
    public ServerThread(Socket socket, ResourceManager rm){
        this.socket = socket;
        this.rmanager = rm;
    }
 
    public void run(){
    	try{
    		// prep
	    	ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()),inputStream2 = null;
	    	ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream()), outStream2 = null;
	    	// get address
	    	InetSocketAddress sockaddr = (InetSocketAddress)socket.getRemoteSocketAddress();
	    	InetAddress inaddr = sockaddr.getAddress();
	    	String rcv_addr = ((Inet4Address)inaddr).toString();
	    	String databaseName = "RS" + rcv_addr.replace('.', '_').replace("/", "");
	    	// get message
	        MyMessage ms = (MyMessage) inputStream.readObject();
	        // prep objects
	        User user = null;MyMessage ms2 = null; Device device = null;
	        switch(ms.getType()){
	        	case MyMessage.AUTHEN:
	        		if(rmanager.authen(rcv_addr, ms.getAuthenCode())){
	        			System.out.println("Authentication Succeed from " + rcv_addr);
	        			outStream.writeObject(true);
	        			Database database = (Database)inputStream.readObject();
	        			inputStream.close();
	        			DBEditor.rebuild(databaseName,"root","rlab");
	        			dbeditor = new DBEditor(databaseName,"root","rlab");
	        			dbeditor.connect();
	        			dbeditor.add(database);
	        			
	        		}else{
	        			System.out.println("Authentication Failed from " + rcv_addr);
	        			outStream.writeObject(false);
	        		}
	        		break;
	        	case MyMessage.ADD_COURSE:
        			dbeditor = new DBEditor(databaseName,"root","rlab");
        			dbeditor.connect();
        			dbeditor.add(ms.getCourse());
	        		break;
	        	case MyMessage.ADD_EXPERIMENT:
        			dbeditor = new DBEditor(databaseName,"root","rlab");
        			dbeditor.connect();
        			dbeditor.add(ms.getExperiment());
        			break;
	        	case MyMessage.ADD_CPU:
        			dbeditor = new DBEditor(databaseName,"root","rlab");
        			dbeditor.connect();
        			dbeditor.add(ms.getCpu());
        			break;
	        	case MyMessage.ADD_USER:
        			dbeditor = new DBEditor(databaseName,"root","rlab");
        			dbeditor.connect();
        			dbeditor.add(ms.getUser());
        			break;
	        	case MyMessage.ADD_COURSEHASUSER:
        			dbeditor = new DBEditor(databaseName,"root","rlab");
        			dbeditor.connect();
        			dbeditor.add(ms.getCourseHasUser());
        			break;
	        	case MyMessage.REQUEST_DEVICE_FROM_MAINSERVER:
	        		// get user from resource server who is requesting resource
	        		user = ms.getUser();
	        		// send resource server list
	        		outStream.writeObject(rmanager.getList().remove(rcv_addr));
	        		// get best resource server
	        		String target = (String)inputStream.readObject();
	        		Socket targetSocket = new Socket(target, 5502);
	        		//request from target
        			outStream2 = new ObjectOutputStream(targetSocket.getOutputStream());
        			inputStream2 = new ObjectInputStream(targetSocket.getInputStream());
        			ms2 = new MyMessage(MyMessage.REQUEST_DEVICE_FROM_TARGET_RESOURCESERVER,user);
        			outStream2.writeObject(ms2);
        			// acquire device
        			device = (Device)inputStream2.readObject();
        			device.setCloudParameters(true, target);
	        		// send back to demander
        			outStream.writeObject(device);
	        		break;
	        	case MyMessage.RETURN_DEVICE_TO_MAINSERVER:
	        		// get user from resource server who is returning resource
	        		device = ms.getDevice();
	        		user = ms.getUser();
	        		// return to target
	        		targetSocket = new Socket(device.getHostServer(), 5502);
        			outStream2 = new ObjectOutputStream(targetSocket.getOutputStream());
        			inputStream2 = new ObjectInputStream(targetSocket.getInputStream());
        			// send back
        			ms2 = new MyMessage(MyMessage.RETURN_DEVICE_TO_TARGET_RESOURCESERVER,user);
        			outStream2.writeObject(ms2);
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
