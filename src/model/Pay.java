package model;

import java.math.BigDecimal;

/**
 * 对应数据库的pay表
 * @author passerbyYSQ
 * @create 2020年3月23日 下午11:13:18
 */
public class Pay {
	private Integer payId; // 标识每一条发放记录
	
	private BigDecimal result; // 总的发放工资
	
	private String userId; // 用户编号，工号
	
	// 数据库表对应的字段的数据类型为datetime
	// 由于我们拿到时间后一般都是用于显示，因此此处我们用String来接受
	// 如何将datetime从结果集中取出并转化为String？在其他地方再说
	private String payTime; // 工资发放时间
	
	// 金额用BigDecimal来接受，不要用浮点数，不精确
	private BigDecimal postWage; // 岗位工资
	
	private BigDecimal performWage; // 绩效工资
	
	private BigDecimal tempWage; // 临时性加班报酬
	
	private BigDecimal extra; // 补发或补扣
	
	private BigDecimal lessonWage = new BigDecimal(0); // 课酬
	
	private BigDecimal manageWage = new BigDecimal(0); // 管理绩效
	
	private BigDecimal manageSubsidy = new BigDecimal(0); // 管理补助
	
	private String postscript; // 备注
	

	public Pay(BigDecimal result, Integer payId, String userId, String payTime, BigDecimal postWage, 
			BigDecimal performWage, BigDecimal tempWage, BigDecimal extra, BigDecimal lessonWage, 
			BigDecimal manageWage, BigDecimal manageSubsidy, String postscript) {
		super();
		this.result = result;
		this.payId = payId;
		this.userId = userId;
		this.payTime = payTime;
		this.postWage = postWage;
		this.performWage = performWage;
		this.tempWage = tempWage;
		this.extra = extra;
		this.lessonWage = lessonWage;
		this.manageWage = manageWage;
		this.manageSubsidy = manageSubsidy;
		this.postscript = postscript;
	}
	
	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public BigDecimal getPostWage() {
		return postWage;
	}

	public void setPostWage(BigDecimal postWage) {
		this.postWage = postWage;
	}

	public BigDecimal getPerformWage() {
		return performWage;
	}

	public void setPerformWage(BigDecimal performWage) {
		this.performWage = performWage;
	}

	public BigDecimal getTempWage() {
		return tempWage;
	}

	public void setTempWage(BigDecimal tempWage) {
		this.tempWage = tempWage;
	}

	public BigDecimal getExtra() {
		return extra;
	}

	public void setExtra(BigDecimal extra) {
		this.extra = extra;
	}

	public BigDecimal getLessonWage() {
		return lessonWage;
	}

	public void setLessonWage(BigDecimal lessonWage) {
		this.lessonWage = lessonWage;
	}

	public BigDecimal getManageWage() {
		return manageWage;
	}

	public void setManageWage(BigDecimal manageWage) {
		this.manageWage = manageWage;
	}

	public BigDecimal getManageSubsidy() {
		return manageSubsidy;
	}

	public void setManageSubsidy(BigDecimal manageSubsidy) {
		this.manageSubsidy = manageSubsidy;
	}

	public String getPostscript() {
		return postscript;
	}

	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}

	@Override
	public String toString() {
		return "Pay [payId=" + payId + ", userId=" + userId + ", payTime=" + payTime + ", postWage=" + postWage
				+ ", performWage=" + performWage + ", tempWage=" + tempWage + ", extra=" + extra + ", lessonWage="
				+ lessonWage + ", manageWage=" + manageWage + ", manageSubsidy=" + manageSubsidy + ", postscript="
				+ postscript + "]";
	}
	
	

}

