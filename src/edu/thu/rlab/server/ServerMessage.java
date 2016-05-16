package edu.thu.rlab.server;

import edu.thu.rlab.pojo.*;

public class ServerMessage {
	
	private int type;
	
	public static final int RESOURCE_SERVER_AUTHEN = 0;
	
	private String authen_code;
	
	public static final int RESOURCE_SERVER_UPDATE_COURSE = 1;
	
	private Course course;
	
	public ServerMessage(int t,String code){
		type = t;
		authen_code = code;
	}
	
	public ServerMessage(int t,Course cs){
		type = t;
		course = cs;
	}
	
	public int getType(){
		return type;
	}
	
	public String getAuthenCode(){
		return authen_code;
	}
	
	public Course getCourse(){
		return course;
	}

}
