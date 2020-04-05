package utils;

import java.util.Random;

/**
 * Random的工具类
 * @author passerbyYSQ
 * 
 */
public class RandomUtils {
	
	/**
	 * 测试
	 * @param args
	 */
//	public static void main(String[] args) {
//		for (int i = 0; i < 10; i++) {
//			System.out.println(getInt(10, 100) + " " + getMixedStr(6));
//		}
//	}

	private static Random r = new Random();
	
	/**
	 * 生成一个在区间[low, high]的随机整数
	 * @param low
	 * @param high
	 * @return
	 */
	public static int getInt(int low, int high) {
		return (low + r.nextInt(high - low + 1)); 
	}
	
	/**
	 * 生成一个指定长度的混合有大小写字母和数字的字符串
	 * @param length
	 * @return
	 */
	public static String getMixedStr(int length) {
		char[] chArr = new char[length];
		for (int i = 0; i < length; i++) {
			int type = getInt(1, 3);
			switch (type) {
				case 1: {
					// 小写字母
					chArr[i] = (char)getInt(97, 122);
					break;
				}
				case 2: {
					// 大写字母
					chArr[i] = (char)getInt(65, 90);
					break;
				}
				case 3: {
					// 数字
					chArr[i] = (char)getInt(48, 57);
					break;
				}
			}
		}
		return new String(chArr);
	}
	
	/**
	 * 生成一个指定长度的小写的随机字符串
	 * @param length
	 * @return
	 */
	public static String getStr(int length) {
		char[] chArr = new char[length];
		
		for (int i = 0; i < length; i++) {
			chArr[i] = (char)getInt(97, 122);
		}
		
		return new String(chArr);
	}
	
	/**
	 * 随机生成一个性别
	 * @return
	 */
	public static char getGender() {
		final char[] gender = {'男', '女'};
		return gender[ getInt(0, 1) ];
	}
}
