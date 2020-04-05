package model;

import java.math.BigDecimal;

/**
 * 工资发放的参数
 * @author passerbyYSQ
 * @create 2020年4月3日 下午3:35:47
 */
public class SalaryArgs {
	
	private String title;
	private BigDecimal postWage;
	private BigDecimal performWage;
	private BigDecimal titleFactor; 
	private BigDecimal perLessonWage;

	public SalaryArgs(String title, BigDecimal postWage, BigDecimal performWage, BigDecimal titleFactor,
			BigDecimal perLessonWage) {
		super();
		this.title = title;
		this.postWage = postWage;
		this.performWage = performWage;
		this.titleFactor = titleFactor;
		this.perLessonWage = perLessonWage;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public BigDecimal getTitleFactor() {
		return titleFactor;
	}
	public void setTitleFactor(BigDecimal titleFactor) {
		this.titleFactor = titleFactor;
	}
	public BigDecimal getPerLessonWage() {
		return perLessonWage;
	}
	public void setPerLessonWage(BigDecimal perLessonWage) {
		this.perLessonWage = perLessonWage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaryArgs other = (SalaryArgs) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
}
