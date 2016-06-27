package edu.thu.rlab.pojo;

import java.util.List;

public class Database implements java.io.Serializable{ // getter, setter, private later
	
	public List<Course> courselist;
	
	public List<Experiment> experimentlist;
	
	public List<User> userlist;
	
	public List<Cpu> cpulist;
	
	public List<CourseHasUser> chasulist;
	
	// Constructor
	public Database(List<Course> courselist, List<Experiment> experimentlist,
			List<User> userlist, List<Cpu> cpulist, List<CourseHasUser> chasulist){
		this.courselist = courselist;
		this.experimentlist = experimentlist;
		this.userlist = userlist;
		this.cpulist = cpulist;
		this.chasulist = chasulist;
	}
	 
}
