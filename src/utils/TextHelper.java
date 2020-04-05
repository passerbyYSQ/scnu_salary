package utils;

/**
 * 我之前的博客参考：
 * https://blog.csdn.net/qq_43290318/article/details/101373319
 * @author passerbyYSQ
 * @create 2020年4月4日 下午4:27:46
 */
public class TextHelper {
	
	public static boolean isEmail(String str) {
		String regex = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-z]{2,}$";
		return str != null && str.matches(regex);
	}
	
	public static boolean isPhone(String str) {
		String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\\\d{8}$";
		return str != null && str.matches(regex);
	}
	
	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str);
	}
	
	/**
	 * 判断是不是正确的整数格式
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		String regex="(-?[1-9]\\d*)|0";
		return str != null && str.matches(regex);
	}
	
	/**
	 * 判断时是不是正确的【正】整数格式
	 * @param str
	 * @return
	 */
	public static boolean isPositiveInteger(String str) {
		String regex="([1-9]\\d*)|0";
		return str != null && str.matches(regex);
	}
	
	/**
	 * 判断是不是正确的浮点数格式
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		if(str.equals("-0"))
			return false;
		String regex="-?(0|[1-9]\\d*)(\\.\\d+)?";
		return str.matches(regex);
	}
	
	/**
	 * 判断是不是正确的【正】浮点数格式
	 * @param str
	 * @return
	 */
	public static boolean isPositiveDouble(String str) {
		String regex="(0|[1-9]\\d*)(\\.\\d+)?";
		return str != null && str.matches(regex);
	}
	
}
