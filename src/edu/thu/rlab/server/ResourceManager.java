package edu.thu.rlab.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResourceManager {
	private final String authen_code = "temp_code";
	
	private Set <String> resourceServerIP;
	
	public boolean authen(String ip, String code){
		if(code.equals(authen_code)){
			resourceServerIP.add(ip);
			return true;
		}else
			return false;
	}
	
	public List<String> getList(){
		List<String> l = new ArrayList<>(resourceServerIP);
		return l;
	} 
}
