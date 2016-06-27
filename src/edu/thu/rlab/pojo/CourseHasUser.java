package edu.thu.rlab.pojo;

public class CourseHasUser implements java.io.Serializable{
	
	public String courseid;
	public String userid;
	
	public CourseHasUser(String courseid, String userid) {
		this.userid = userid;
		this.courseid = courseid;
	}
}
