package dao;

import java.sql.ResultSet;

import dao.BaseDao.PackResult;
import model.User;
import model.Workload;

/**
 * @author passerbyYSQ
 * @create 2020年4月3日 上午10:19:43
 */
public class WorkloadDao {
	
	public int addWorkload(Workload workload) {
		String sql = "insert into workload value(null, ?, ?, ?, ?)";
		return BaseDao.insertOrUpdateOrDelete(sql, workload.getUserId(), workload.getTerm(), 
				workload.getLessonHour(), workload.getAttendCnt());
	}
	
	public int updateWorkload(Workload workload) {
		String sql = "update workload set lessonHour=?, attendCnt=? where userId=? and term=?";
		return BaseDao.insertOrUpdateOrDelete(sql, workload.getLessonHour(), 
				workload.getAttendCnt(), workload.getUserId(), workload.getTerm());
	}
	
	/**
	 * 查询指定用户指定学期的工作量，返回只有1条
	 * @param userId
	 * @param term
	 * @return
	 */
	public Workload getWorkload(String userId, String term) {
		String sql = "select * from workload where userId=? and term=?";
		return BaseDao.select(sql, new PackResult<Workload>() {
			@Override
			public Workload onResultReturn(ResultSet rs) throws Exception {
				return rs.next() ? packToWorkload(rs) : null;
			}
		}, userId, term);
	}
	
	private Workload packToWorkload(ResultSet rs) {
		try {
			Integer lessonHour = rs.getInt("lessonHour");
			Integer attendCnt = rs.getInt("attendCnt");
			
			return new Workload(null, null, null, lessonHour, attendCnt);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
}
