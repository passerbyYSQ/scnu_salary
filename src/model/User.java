package model;

/**
 * User的实体类，对应数据库的user表
 * 实体类的属性名与数据库user表中的字段名一样（对应） 
 * @author passerbyYSQ
 * @create 2020年3月21日 下午12:13:54
 */
public class User {
	private String userId;
	
	private String password;
	
	private String userName;	
	
	private String sex;
	
	private Integer depId; // 所属部门Id
	private String depName;
	
	private String loginAuth; // 登录权限
	
	private String proTitle; // 职称
	
	private String post; // 主要职务
	
	private String salaryType; // 工资发放类型，是以专任教师还是非专任教师
	
	private String email; 
	
	private String phone;

	public User() {
	}
	
	public User(String userId, String password, String userName, String sex, int depId, String depName, 
			String loginAuth, String proTitle, String post, String salaryType, String email, String phone) {
		super();
		this.userId = userId;
		this.password = password;
		this.userName = userName;
		this.sex = sex;
		this.depId = depId;
		this.setDepName(depName);
		this.loginAuth = loginAuth;
		this.proTitle = proTitle;
		this.post = post;
		this.salaryType = salaryType;
		this.email = email;
		this.phone = phone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getDepId() {
		return depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	public String getLoginAuth() {
		return loginAuth;
	}

	public void setLoginAuth(String loginAuth) {
		this.loginAuth = loginAuth;
	}

	public String getProTitle() {
		return proTitle;
	}

	public void setProTitle(String proTitle) {
		this.proTitle = proTitle;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	// 必须复写，用于显示结点的名称
	@Override
	public String toString() {
		return userName; 
	}

	
	
}
