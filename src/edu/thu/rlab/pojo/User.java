package edu.thu.rlab.pojo;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements UserDetails, java.io.Serializable {

	public static enum DEVICESTATE {
		NULL, APPLYING, USEING, TIMEOUT
	};

	private DEVICESTATE deviceState;
	private Experiment experiment;

	public DEVICESTATE getDeviceState() {
		return deviceState;
	}
	public void setDeviceState(DEVICESTATE deviceState) {
		this.deviceState = deviceState;
	}
	public Experiment getExperiment() {
		return this.experiment;
	}

	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}
	//for security
	private Collection<? extends GrantedAuthority> authorities;
	
	@JSON(serialize=false)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	@JSON(serialize=false)
	public boolean isAccountNonExpired() {
		return this.isEnabled();
	}
	@JSON(serialize=false)
	public boolean isAccountNonLocked() {
		return this.isEnabled();
	}
	@JSON(serialize=false)
	public boolean isCredentialsNonExpired() {
		return this.isEnabled();
	}
	@JSON(serialize=false)
	public boolean isEnabled() {
		return this.enabled;
	}
	
	//following field in database
	
	// Fields

	private String id;
	private Course course;
	private String username;
	private String password;
	private Boolean enabled;
	private String userRole;
	private String schoolNo;
	private String name;
	private String clazzName;
	private String email;
	private String phone;
	private Timestamp createTime;
	private Timestamp lastLoginTime;
	private String lastLoginIp;
	private Integer loginCount;
	private Long onlineTime;
	private Set experimentsForUserId = new HashSet(0);
	private Set experimentsForRemarkUserId = new HashSet(0);
	private Set courses = new HashSet(0);
	
	
	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String username, Timestamp createTime) {
		this.username = username;
		this.createTime = createTime;
	}

	/** full constructor */
	public User(Course course, Experiment experiment, String username,
			String password, Boolean enabled, String userRole, String schoolNo,
			String name, String clazzName, String email, String phone,
			Timestamp createTime, Timestamp lastLoginTime, String lastLoginIp,
			Integer loginCount, Long onlineTime, Set experimentsForUserId,
			Set experimentsForRemarkUserId, Set courses) {
		this.course = course;
		this.experiment = experiment;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
		this.schoolNo = schoolNo;
		this.name = name;
		this.clazzName = clazzName;
		this.email = email;
		this.phone = phone;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.lastLoginIp = lastLoginIp;
		this.loginCount = loginCount;
		this.onlineTime = onlineTime;
		this.experimentsForUserId = experimentsForUserId;
		this.experimentsForRemarkUserId = experimentsForRemarkUserId;
		this.courses = courses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}



	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getSchoolNo() {
		return this.schoolNo;
	}

	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClazzName() {
		return this.clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Long getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Set getExperimentsForUserId() {
		return this.experimentsForUserId;
	}

	public void setExperimentsForUserId(Set experimentsForUserId) {
		this.experimentsForUserId = experimentsForUserId;
	}

	public Set getExperimentsForRemarkUserId() {
		return this.experimentsForRemarkUserId;
	}

	public void setExperimentsForRemarkUserId(Set experimentsForRemarkUserId) {
		this.experimentsForRemarkUserId = experimentsForRemarkUserId;
	}

	public Set getCourses() {
		return this.courses;
	}

	public void setCourses(Set courses) {
		this.courses = courses;
	}

}