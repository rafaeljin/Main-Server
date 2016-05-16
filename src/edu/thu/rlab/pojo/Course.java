package edu.thu.rlab.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * Course entity. @author MyEclipse Persistence Tools
 */

public class Course implements java.io.Serializable {

	// Fields

	private String id;
	private String code;
	private String name;
	private Integer year;
	private String season;
	private Timestamp createTime;
	private Set users = new HashSet(0);
	private Set users_1 = new HashSet(0);
	private Set experiments = new HashSet(0);

	// Constructors

	/** default constructor */
	public Course() {
	}

	/** minimal constructor */
	public Course(String code, String name, Integer year, String season, Timestamp createTime) {
		this.code = code;
		this.name = name;
		this.year = year;
		this.season = season;
		this.createTime = createTime;
	}

	/** full constructor */
	public Course(String code, String name, Integer year, String season,
			Timestamp createTime, Set users, Set users_1, Set experiments) {
		this.code = code;
		this.name = name;
		this.year = year;
		this.season = season;
		this.createTime = createTime;
		this.users = users;
		this.users_1 = users_1;
		this.experiments = experiments;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getSeason() {
		return this.season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	//@JSON(serialize=false)
	public Set getUsers() {
		return this.users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}
	//@JSON(serialize=false)
	public Set getUsers_1() {
		return this.users_1;
	}

	public void setUsers_1(Set users_1) {
		this.users_1 = users_1;
	}

	//@JSON(serialize=false)
	public Set getExperiments() {
		return this.experiments;
	}

	public void setExperiments(Set experiments) {
		this.experiments = experiments;
	}

}