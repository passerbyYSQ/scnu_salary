package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import dao.PayDao;

/**
 * @author passerbyYSQ
 * @create 2020年4月3日 下午5:00:13
 */
public class ManageTeacherSalary extends TeacherSalary {
	
	private BigDecimal manageWage; // 管理绩效
	
	private BigDecimal manageSubsidy; // 管理补助
	
	private BigDecimal result; // 该月的最终工资
	
	/**
	 * 计算该年该月份的岗位工资、基础绩效、管理绩效、岗位补助
	 * @param user
	 * @param year
	 * @param month
	 */
	public ManageTeacherSalary(User user, Integer year, Integer month) {
		this(user, new BigDecimal(0), new BigDecimal(0), year, month);
	}
	
	/**
	 * 除了计算该年该月份的岗位工资、基础绩效、管理绩效、岗位补助之外，
	 * 还会计算应该发放的总工资
	 * @param user
	 * @param tempWage
	 * @param extra
	 * @param year
	 * @param month
	 */
	public ManageTeacherSalary(User user, BigDecimal tempWage, 
			BigDecimal extra, Integer year, Integer month) {
		super(user.getPost(), tempWage, extra);
		
		// 计算管理绩效
		PayDao payDao = new PayDao();
		List<Pay> pays = payDao.getFullTimeTeacherRecord(year, month);
		
		// 该年该月份 全院所有专任教师课酬的平均值
		BigDecimal avg = new BigDecimal(0);
		if (pays.size() > 0) {
			for (Pay pay : pays) {
				avg = avg.add(pay.getLessonWage());
			}
			avg = avg.divide(new BigDecimal(pays.size()), 2, RoundingMode.HALF_UP);
		}
		
		// 管理绩效
		manageWage = getTitleFactor();
		manageWage = manageWage.multiply(avg);
		
		// 计算岗位补助
		BigDecimal lessonWage = new FullTimeTeacherSalary(user, tempWage, extra, year, month)
									.getLessonWage();
		manageSubsidy = lessonWage.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP);
		
		result = getPostWage();
		result = result.add(getPerformWage());
		result = result.add(manageWage);
		result = result.add(manageSubsidy);
		result = result.add(tempWage);
		result = result.add(extra);
	}
	/*
	 非专任教师的工资： 岗位工资 + 基础绩效+管理岗绩效+岗位补助+临时性加班报酬+补/扣
                岗位工资：院长/书记：2000元，副院长/副书记： 1800元，系\部\中心主任：1500元，系\部\中心副主任：1200元，其他：1000元
                基础绩效工资：院长/书记：1100元，副院长/副书记： 900元，系\部\中心主任：700元，系\部\中心副主任：600元，其他：500元
                管理岗绩效：院长/书记：AV*2，副院长/副书记：AV*1.8，系\部\中心主任：AV*1.5，系\部\中心副主任：AV*1.2，其他：AV 。(Av为全院所有专任教师课酬的平均值)
                岗位补助：从事管理工作又承担教学任务的非专任教师享受岗位补助。岗位补助标准为：课酬的一半。【课酬计算标准与专任教师相同】。
	 */

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

	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}
}
