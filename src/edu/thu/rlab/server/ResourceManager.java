package edu.thu.rlab.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResourceManager {
	
	private Set <String> resourceServerIP;
	
	// for sync 
	private Object lock = new Object();
	
	public boolean authen(String ip, String code){
		if(code.equals(MainServer.authenCode)){
			synchronized(lock){
				resourceServerIP.add(ip);
			}
			return true;
		}else
			return false;
	}
	
	public void remove(String ip)
	{
		synchronized(lock){
			resourceServerIP.remove(ip);
		}
		return;
	}
	
	public List<String> getList(){
		List<String> l = null;
		synchronized(lock){
			l = new ArrayList<>(resourceServerIP);
		}
		return l;
	} 
}
