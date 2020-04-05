package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import dao.WorkloadDao;

/**
 * 专任教师的工资
 * @author passerbyYSQ
 * @create 2020年4月3日 下午4:05:52
 */
public class FullTimeTeacherSalary extends TeacherSalary {

	private BigDecimal lessonWage; // 课酬
	
	private BigDecimal result; // 该月的最终工资
	
	/**
	 * 计算课酬、岗位工资和基础绩效
	 * @param user
	 * @param year
	 * @param month
	 */
	public FullTimeTeacherSalary(User user, Integer year, Integer month) {
		this(user, new BigDecimal(0), new BigDecimal(0), year, month);
	}
	
	/**
	 * 除了计算课酬、岗位工资和基础绩效，
	 * 该构造函数，还会计算出这个月应该发放的总工资
	 * @param proTitle	职称
	 * @param temp		加班报酬
	 * @param extra		补/扣
	 */
	public FullTimeTeacherSalary(User user, BigDecimal tempWage, 
			BigDecimal extra, Integer year, Integer month) {
		
		super(user.getProTitle(), tempWage, extra);
		
		// 上课节数和学生人数
		WorkloadDao workloadDao = new WorkloadDao();
		Workload workload = workloadDao.getWorkload(user.getUserId(), getTerm(year, month));
		
		// 计算课酬
		BigDecimal stuFactor = new BigDecimal(workload.getAttendCnt())
				.divide(new BigDecimal(60), 2, RoundingMode.HALF_UP);
		if (stuFactor.compareTo(new BigDecimal(1)) == -1) {
			// 学生系数小于1就当作1来计算
			stuFactor = new BigDecimal(1);
		}
		
		lessonWage = getTitleFactor(); 
		lessonWage = lessonWage.multiply(new BigDecimal(workload.getLessonHour()));
		lessonWage = lessonWage.multiply(stuFactor);
		lessonWage = lessonWage.multiply(getPerLessonWage());
	
		// 计算该月份的总工资
		result = getPostWage();
		result = result.add(getPerformWage());
		result = result.add(lessonWage);
		result = result.add(getTempWage());
		result = result.add(getExtra());
	}
	
	// 专任教师的工资 由 ： 岗位工资 + 基础绩效+课酬+临时性加班报酬+补/扣  （5部分组成）
	
	/*
	    课酬= 职称系数×节数×学生系数×单位课筹 
                         职称系数：教授职称系数为1.5，副教授为1.3，讲师为1.2，助教和其他人员为1.0；
                         学生系数 =学生人数/60，不足1的按1来计算，单位课酬为35元/节
	 */
	
	private String getTerm(Integer year, Integer month) {
		if (month >= 9) {
			// 第1学期
			return  year + "-" + (year+1) + "（1）";
		} else if (month >= 2) {
			// 第2学期
			return  (year-1) + "-" + year + "（2）";
		}
		return null;
	}

	public BigDecimal getLessonWage() {
		return lessonWage;
	}

	public void setLessonWage(BigDecimal lessonWage) {
		this.lessonWage = lessonWage;
	}

	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}
}
