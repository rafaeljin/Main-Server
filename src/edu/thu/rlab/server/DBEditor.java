package edu.thu.rlab.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    // Database Editor
	public DBEditor(String name,String user,String password)
	{
		dbname = name;
		url = "jdbc:mysql://localhost:3306/" + dbname;
		this.user = user;
		this.password = password;
	}
	
	
	void connect()
	{
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
	
	
	String stringValue(String s)
	{
		return "'" + s + "'";
	}
	
	
	boolean	add(Course course){
        try {
        	List<String> keylist = new ArrayList<String>();
        	List<String> valuelist = new ArrayList<String>();
        	keylist.add("id"); valuelist.add(stringValue(course.getId()));
        	keylist.add("code"); valuelist.add(stringValue(course.getCode()));
        	keylist.add("name"); valuelist.add(stringValue(course.getName()));
        	keylist.add("year"); valuelist.add(Integer.toString(course.getYear()));
        	keylist.add("season"); valuelist.add(stringValue(course.getSeason()));
        	keylist.add("create_time"); valuelist.add(stringValue(dateFormat.format(course.getCreateTime())));
        	
        	sql = QueryCreator.getInsertQuery("maindb", "course", keylist, valuelist);
        	System.out.println(sql);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	
	boolean	add(Cpu cpu){
		try {
        	List<String> keylist = new ArrayList<String>();
        	List<String> valuelist = new ArrayList<String>();
        	keylist.add("id"); valuelist.add(stringValue(cpu.getId()));
        	keylist.add("user_id"); valuelist.add(stringValue(cpu.getUser().getId()));
        	keylist.add("experiment_name"); valuelist.add(stringValue(cpu.getExperimentName()));
        	keylist.add("variables"); valuelist.add(stringValue(cpu.getVariables()));
        	sql = QueryCreator.getInsertQuery("maindb", "cpu", keylist, valuelist);
        	System.out.println(sql);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	
	boolean	add(Device device){
		return true;
	}
	
	
	boolean	add(Experiment exp){
		return true;
	}
	
	
	boolean add(User user){
		try {
        	List<String> keylist = new ArrayList<String>();
        	List<String> valuelist = new ArrayList<String>();
        	if(user.getId()!= null){
	        	keylist.add("id"); valuelist.add(stringValue(user.getId()));
	        }
	        keylist.add("username"); valuelist.add(stringValue(user.getUsername()));
        	keylist.add("password"); valuelist.add(stringValue(user.getPassword()));
        	keylist.add("enabled"); valuelist.add(Integer.toString(1)); // enabled?
        	keylist.add("user_role"); valuelist.add(stringValue(user.getUserRole()));
        	if(user.getSchoolNo()!= null){
        		keylist.add("school_no"); valuelist.add(stringValue(user.getSchoolNo()));
        	}
        	keylist.add("name"); valuelist.add(stringValue(user.getName()));
        	if(user.getClazzName()!=null){
        		keylist.add("clazz_name"); valuelist.add(stringValue(user.getClazzName()));
        	}
        	keylist.add("email"); valuelist.add(stringValue(user.getEmail()));
        	keylist.add("phone"); valuelist.add(stringValue(user.getPhone()));
        	keylist.add("create_time"); valuelist.add(stringValue(dateFormat.format(user.getCreateTime())));
        	if(user.getLastLoginTime() != null){
        		keylist.add("last_login_time"); valuelist.add(stringValue(dateFormat.format(user.getLastLoginTime())));
        	}
        	if(user.getLastLoginIp()!=null){
        		keylist.add("last_login_ip"); valuelist.add(stringValue(user.getLastLoginIp()));
        	}
        	keylist.add("login_count"); valuelist.add(Integer.toString(user.getLoginCount()));
        	keylist.add("online_time"); valuelist.add(Long.toString(user.getOnlineTime()));
        	if(user.getCourse()!= null){
        		keylist.add("current_course_id"); valuelist.add(stringValue(user.getCourse().getId()));
        	}
        	
        	sql = QueryCreator.getInsertQuery("maindb", "user", keylist, valuelist);
        	System.out.println(sql);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		/*try {
        	sql = "INSERT INTO " + dbname + ".user " + 
		        "(id,username,password,enabled,user_role,"+
        		"school_no,name,clazz_name,email,phone,"+
		        "createTime,lastLoginTime,lastLoginIp,"+
        		"loginCount,onlineTime) VALUES("+
		        stringValue(user.getId()) + "," + user.getCode() + "','" +
		        user.getName() + "'," + user.getYear() + ",'" +
		        user.getSeason() + "')"; //course.getSeason() + "'," + course.getCreateTime() +")";
        	System.out.println(sql);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}
	
	
	List<Course> readAllCourses()
	{
		List<Course> courses = new ArrayList<Course>();
        try {
        	sql = QueryCreator.getSelectQueryAll(dbname,"course");
			ResultSet rs = statement.executeQuery(sql);
			String id = null, code = null, name = null, season = null; Integer year = null; Timestamp create_time = null;
            while(rs.next()){ // should include all matches, modify later
            	id = rs.getString("id");
            	code = rs.getString("code");
                name = rs.getString("name");
                year = Integer.parseInt(rs.getString("year"));
                season = rs.getString("season");
                Date date = dateFormat.parse(rs.getString("create_time"));
                create_time = new Timestamp (date.getTime());
                Course c = new Course(code, name, year, season, create_time); c.setId(id);
                courses.add(c);
                //System.out.println(id + "\t" + code + "\t" + name + "\t" + season + "\t" + year + "\t" + create_time);
            }
            rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return courses;
	}
	
	
	List<Cpu> readAllCpus()
	{
		List<Cpu> cpus = new ArrayList<Cpu>();
        /*try {
        	sql = QueryCreator.getSelectQueryAll(dbname,"cpu");
			ResultSet rs = statement.executeQuery(sql);
			String id = null, user_id = null, experiment_name = null, variables = null;
            while(rs.next()){ // should include all matches, modify later
            	id = rs.getString("id");
            	user_id = rs.getString("user_id");
            	experiment_name = rs.getString("experiment_name");
            	variables = rs.getString("variables");
                Course c = new Course(code, name, year, season, create_time);
                courses.add(c);
                //System.out.println(id + "\t" + code + "\t" + name + "\t" + season + "\t" + year + "\t" + create_time);
            }
            rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        return cpus;
	}
	
	
	List<User> readAllUsers()
	{
		List<User> users = new ArrayList<User>();
        try {
        	sql = QueryCreator.getSelectQueryAll(dbname,"user");
			ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){ // should include all matches, modify later
            	String id = null; Course course = null; String username;String password;Boolean enabled;String userRole;String schoolNo = null;String name;
    			String clazzName = null;String email;String phone;Timestamp createTime;Timestamp lastLoginTime = null;String lastLoginIp = null;
    			Integer loginCount;Long onlineTime;
            	
    			if(rs.getString("id")!= null)
            		id = rs.getString("id");
            	username = rs.getString("username");
            	password = rs.getString("password");
            	enabled = true; //enabled = 1?
            	userRole = rs.getString("user_role");
            	if(rs.getString("school_no")!= null)
            		schoolNo = rs.getString("school_no");
            	name = rs.getString("name");
            	if(rs.getString("clazz_name")!= null)
            		clazzName = rs.getString("clazz_name");
            	email = rs.getString("email");
            	phone = rs.getString("phone");
            	Date date = dateFormat.parse(rs.getString("create_time"));
            	createTime = new Timestamp (date.getTime());
            	if(rs.getString("last_login_time") != null){
	            	date = dateFormat.parse(rs.getString("last_login_time"));
	            	lastLoginTime = new Timestamp (date.getTime());
	            }
	            lastLoginIp = rs.getString("last_login_ip");
            	loginCount = Integer.parseInt(rs.getString("login_count"));
            	onlineTime = Long.parseLong(rs.getString("online_time"));
            	if(rs.getString("current_course_id")!=null){
	            	course = new Course(null,null,null,null,null);
	            	course.setId(rs.getString("current_course_id"));
            	}
            	
            	User u = new User(course,new Experiment(),username,password,enabled,userRole,schoolNo,
            			name,clazzName,email,phone,createTime,lastLoginTime,lastLoginIp,loginCount,onlineTime,null,
            			null, null); u.setId(id);
                users.add(u);
                System.out.println(id + "\t" + username + "\t" + name + "\t" + userRole + "\t" + schoolNo + "\t" + name);
            }
            rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return users;
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
