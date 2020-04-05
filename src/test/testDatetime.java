package test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author passerbyYSQ
 * @create 2020年3月23日 下午11:00:06
 */
public class testDatetime {
	public static void main(String[] args) {
		
		
		Calendar cur = Calendar.getInstance();
		// System.out.println(cur);
		System.out.println(cur.getTime());
		System.out.println(new Date());
		
		// 一个月之内
		cur.add(Calendar.MONTH, -1);
		System.out.println(cur.getTime());
		
		System.out.println(Integer.parseInt("ab10"));
		
	}
}
