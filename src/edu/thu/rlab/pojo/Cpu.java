package edu.thu.rlab.pojo;

/**
 * Cpu entity. @author MyEclipse Persistence Tools
 */

public class Cpu implements java.io.Serializable {

	// Fields

	private String id;
	private User user;
	private String experimentName;
	private String variables;

	// Constructors

	/** default constructor */
	public Cpu() {
	}

	/** minimal constructor */
	public Cpu(User user) {
		this.user = user;
	}

	/** full constructor */
	public Cpu(User user, String experimentName, String variables) {
		this.user = user;
		this.experimentName = experimentName;
		this.variables = variables;
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

	public void setUser(User user) {
		this.user = user;
	}

	public String getExperimentName() {
		return this.experimentName;
	}

	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}

	public String getVariables() {
		return this.variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}

}