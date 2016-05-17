package edu.thu.rlab.pojo;

import java.sql.Timestamp;

/**
 * Experiment entity. @author MyEclipse Persistence Tools
 */

public class Experiment implements java.io.Serializable {

	// Fields

	private String id;
	private User user;
	private Course course;
	private User remarkUser;
	private String name;
	private Timestamp createTime;
	private Long opTime;
	private Integer opTimes;
	private Integer submitTimes;
	private String lastSubmitPath;
	private Boolean done;
	private Timestamp doneTime;
	private String srcPath;
	private Integer grade;
	private String remark;

	// Constructors

	/** default constructor */
	public Experiment() {
	}

	/** minimal constructor */
	public Experiment(User userByUserId, Course course, String name,
			Timestamp createTime) {
		this.user = userByUserId;
		this.course = course;
		this.name = name;
		this.createTime = createTime;
	}

	/** full constructor */
	public Experiment(User userByUserId, Course course,
			User userByRemarkUserId, String name, Timestamp createTime,
			Long opTime, Integer opTimes, Integer submitTimes,
			String lastSubmitUrl, Boolean done, Timestamp doneTime,
			String srcUrl, Integer grade, String remark) {
		this.user = userByUserId;
		this.course = course;
		this.remarkUser = userByRemarkUserId;
		this.name = name;
		this.createTime = createTime;
		this.opTime = opTime;
		this.opTimes = opTimes;
		this.submitTimes = submitTimes;
		this.lastSubmitPath = lastSubmitUrl;
		this.done = done;
		this.doneTime = doneTime;
		this.srcPath = srcUrl;
		this.grade = grade;
		this.remark = remark;

	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User userByUserId) {
		this.user = userByUserId;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public User getRemarkUser() {
		return this.remarkUser;
	}

	public void setRemarkUser(User userByRemarkUserId) {
		this.remarkUser = userByRemarkUserId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Long opTime) {
		this.opTime = opTime;
	}

	public Integer getOpTimes() {
		return this.opTimes;
	}

	public void setOpTimes(Integer opTimes) {
		this.opTimes = opTimes;
	}

	public Integer getSubmitTimes() {
		return submitTimes;
	}

	public void setSubmitTimes(Integer submitTimes) {
		this.submitTimes = submitTimes;
	}

	public String getLastSubmitPath() {
		return this.lastSubmitPath;
	}

	public void setLastSubmitPath(String lastSubmitUrl) {
		this.lastSubmitPath = lastSubmitUrl;
	}

	public Boolean getDone() {
		return this.done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public Timestamp getDoneTime() {
		return this.doneTime;
	}

	public void setDoneTime(Timestamp doneTime) {
		this.doneTime = doneTime;
	}

	public String getSrcPath() {
		return this.srcPath;
	}

	public void setSrcPath(String srcUrl) {
		this.srcPath = srcUrl;
	}

	public Integer getGrade() {
		return this.grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}