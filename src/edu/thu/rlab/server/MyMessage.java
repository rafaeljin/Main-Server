package edu.thu.rlab.server;
import edu.thu.rlab.pojo.Course;
import edu.thu.rlab.pojo.CourseHasUser;
import edu.thu.rlab.pojo.Cpu;
import edu.thu.rlab.pojo.Device;
import edu.thu.rlab.pojo.Experiment;
import edu.thu.rlab.pojo.User;

public class MyMessage implements java.io.Serializable{
	
	/* serialize version ID */
	private static final long serialVersionUID = 3326100847047137828L;

	private int type;
	
	public static final int AUTHEN = 0;
	
	private String authen_code;
	
	public static final int ADD_COURSE = 1;
	
	private Course course = null;
	
	public static final int ADD_USER = 2;
	
	private User user = null;
	
	public static final int ADD_EXPERIMENT = 3;
	
	private Experiment expr = null;
	
	public static final int ADD_CPU = 4;
	
	private Cpu cpu = null;
	
	public static final int ADD_COURSEHASUSER = 5;
	
	private CourseHasUser coursehasuser = null;
		
	public static final int REQUEST_DEVICE_FROM_MAINSERVER = 6;
	
	public static final int RETURN_DEVICE_TO_MAINSERVER = 7;
	
	public static final int REQUEST_DEVICE_FROM_TARGET_RESOURCESERVER = 8;
	
	public static final int RETURN_DEVICE_TO_TARGET_RESOURCESERVER = 9;
	
	public static final int REMOVE_COURSE = 10;
	
	public static final int REMOVE_USER = 11;
	
	public static final int REMOVE_EXPERIMENT = 12;
	
	public static final int REMOVE_CPU = 13;
	
	private Device device = null;
	
	public MyMessage(int t,String code)
	{
		type = t;
		authen_code = code;
	}
	
	public MyMessage(int t,Experiment e)
	{
		type = t;
		expr = e;
	}
	
	public MyMessage(int t,User u)
	{
		type = t;
		user = u;
	}
	
	public MyMessage(int t,Cpu c)
	{
		type = t;
		cpu = c;
	}
	
	public MyMessage(int t,CourseHasUser ch)
	{
		type = t;
		coursehasuser = ch;
	}
	
	public MyMessage(int t,Course cs)
	{
		type = t;
		course = cs;
	}
	
	public MyMessage(int t,Device d)
	{
		type = t;
		device = d;
	}
	
	public MyMessage(int t,User u,Device d)
	{
		type = t;
		user = u;
		device = d;
	}
	
	public int getType()
	{
		return type;
	}
	
	public String getAuthenCode()
	{
		return authen_code;
	}
	
	public Experiment getExperiment()
	{
		return expr;
	}
	
	public Cpu getCpu()
	{
		return cpu;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public Course getCourse()
	{
		return course;
	}
	
	public CourseHasUser getCourseHasUser()
	{
		return coursehasuser;
	}
	
	public Device getDevice()
	{
		return device;
	}

}
