package edu.thu.rlab.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import edu.thu.rlab.pojo.*;

public class DBEditor {
	
	// Driver for MYSQL
    String driver = "com.mysql.jdbc.Driver";
	// Database name
	String dbname;
	// Url to database named 
    String url;
    // MYSQL user
    String user;
    // MYSQL password
    String password;
    // Connection to database
 	Connection conn = null;
	// MYSQL statement
    Statement statement = null;
    String sql = null;
    
    
    // Database Editor
	public DBEditor(String name,String user,String password){
		dbname = name;
		url = "jdbc:mysql://localhost:3306/" + dbname;
		this.user = user;
		this.password = password;
	}
	
	
	void connect(){
		// Load Driver
        try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("Database Driver Init Failed.");
			e.printStackTrace();
		}
        // Connect to Database
        try {
			conn = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			System.out.println("Connection to Database Failed.");
			e.printStackTrace();
		}
        // Check connection
        try {
			if(!conn.isClosed()) {
				System.out.println("Connected to mainDB!");
			}
		} catch (SQLException e) {
			System.out.println("Connection Error.");
			e.printStackTrace();
		}
        // Init statement
 		try {
 			statement = conn.createStatement();
 		} catch (SQLException e) {
 			System.out.println("MYSQL statement error.");
 			e.printStackTrace();
 		}
		return;
	}
	
	String stringValue(String s){
		return "'" + s + "'";
	}
	
	boolean	create(Course course){
        try {
        	sql = "INSERT INTO " + dbname + ".course " + 
		        "(id,code,name,year,season) VALUES('" +
		        course.getId() + "','" + course.getCode() + "','" +
		        course.getName() + "'," + course.getYear() + ",'" +
		        course.getSeason() + "')"; //course.getSeason() + "'," + course.getCreateTime() +")";
        	System.out.println(sql);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
	boolean	create(Cpu cpu){
		return true;
	}
	boolean	create(Device device){
		return true;
	}
	boolean	create(Experiment exp){
		return true;
	}
	
	
	boolean create(User user){
		try {
        	sql = "INSERT INTO " + dbname + ".user " + 
		        "(id,username,password,enabled,user_role,"+
        		"school_no,name,clazz_name,email,phone,"+
		        "createTime,lastLoginTime,lastLoginIp,"+
        		"loginCount,onlineTime) VALUES("+
		        stringValue(user.getId()) + "," + course.getCode() + "','" +
		        course.getName() + "'," + course.getYear() + ",'" +
		        course.getSeason() + "')"; //course.getSeason() + "'," + course.getCreateTime() +")";
        	System.out.println(sql);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		return true;
	}
	
	
	Course readby(String selector, String value){
		Course course = null;
		
        try {
        	sql = "select * from " + dbname + ".course" + "where " + selector + "='" + value + "'";
			ResultSet rs = statement.executeQuery(sql);
			String id = null, code = null, name = null, season = null;
			Integer year = null;
			Timestamp createTime = null;
            while(rs.next()){ // should include all matches, modify later
                id = rs.getString("id");
                name = rs.getString("name");
                
                //name = new String(name.getBytes("ISO-8859-1"),"gb2312");
                System.out.println(id + "\t" + name);
            }
            rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return course;
	}
}
