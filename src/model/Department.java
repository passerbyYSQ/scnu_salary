package model;

/**
 * @author passerbyYSQ
 * @create 2020年3月31日 上午9:43:49
 */
public class Department {
	
	private Integer depId;
	
	private String depName;
	
	private Integer memberCount;
	
	public Department() {
	}
	
	public Department(Integer depId, String depName) {
		super();
		this.depId = depId;
		this.depName = depName;
		//this.memberCount = memberCount;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	// 必须复写，用于显示结点名称
	@Override
	public String toString() {
		return depName;
	}
}
