package model;

/**
 * @author passerbyYSQ
 * @create 2020年4月3日 上午10:17:02
 */
public class Workload {
	private Integer workloadId;
	
	private String userId;
	
	private String term;
	
	private Integer lessonHour; // 全日制本科课时
	
	private Integer attendCnt; // 对应的上课人数

	public Workload(Integer workloadId, String userId, String term, Integer lessonHour, Integer attendCnt) {
		super();
		this.workloadId = workloadId;
		this.userId = userId;
		this.term = term;
		this.lessonHour = lessonHour;
		this.attendCnt = attendCnt;
	}

	public Integer getWorkloadId() {
		return workloadId;
	}

	public void setWorkloadId(Integer workloadId) {
		this.workloadId = workloadId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Integer getLessonHour() {
		return lessonHour;
	}

	public void setLessonHour(Integer lessonHour) {
		this.lessonHour = lessonHour;
	}

	public Integer getAttendCnt() {
		return attendCnt;
	}

	public void setAttendCnt(Integer attendCnt) {
		this.attendCnt = attendCnt;
	}
}
