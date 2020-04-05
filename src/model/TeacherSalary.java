package model;

import java.math.BigDecimal;
import java.util.Map;

import dao.SalaryArgsDao;

/**
 * 教师工资
 * 作为基类
 * @author passerbyYSQ
 * @create 2020年4月3日 下午3:18:23
 */
public class TeacherSalary {
	
	protected Map<String, SalaryArgs> args; 
	
	//private String salaryType; // 工资发放类型
	//private String title; // 职称或者管理岗名称
	
	private BigDecimal postWage; // 岗位工资
	
	private BigDecimal performWage; // 基础绩效
	
	private BigDecimal tempWage; // 加班报酬
	
	private BigDecimal extra; // 补/扣
	
	private BigDecimal titleFactor;  // 系数
	
	private BigDecimal perLessonWage; // 每节课的报酬

	public TeacherSalary(String title, BigDecimal tempWage, BigDecimal extra) {
		//title = title1;
		
		SalaryArgsDao salaryArgsDao = new SalaryArgsDao();
		Map<String, SalaryArgs> args = salaryArgsDao.getAllSalaryArgs();
		
		postWage = args.get(title).getPostWage();
		performWage = args.get(title).getPerformWage();
		perLessonWage = args.get(title).getPerLessonWage();
		titleFactor = args.get(title).getTitleFactor();
		
		this.tempWage = tempWage;
		this.extra = extra;
	}
	
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}

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
	
	public BigDecimal getPerLessonWage() {
		return perLessonWage;
	}

	public void setPerLessonWage(BigDecimal perLessonWage) {
		this.perLessonWage = perLessonWage;
	}

	public BigDecimal getTitleFactor() {
		return titleFactor;
	}

	public void setTitleFactor(BigDecimal titleFactor) {
		this.titleFactor = titleFactor;
	}

	
}
