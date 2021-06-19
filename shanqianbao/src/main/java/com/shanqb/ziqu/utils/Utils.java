package com.shanqb.ziqu.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.shanqb.ziqu.webview.AgentWebActivity;
import com.shanqb.ziqu.webview.AgentWebFragment;

/**
 * 全局工具类
 * 
 * @author liukaifu
 * 
 */
public final class Utils
{
	/**
	 * 获取本应用的MD5签名信息
	 */
	public static String getSignInfo(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			Signature sign = signs[0];

			if(sign == null)
			{
				return null;
			}
 			return Md5.MD5(sign.toByteArray()).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 直接对对象的某个属性赋值
	 * 
	 * @param obj
	 * @param property
	 * @param value
	 * @throws Exception
	 */
	public static void setValue(Object obj, String property, Object value) throws Exception {
		Class<?> c = obj.getClass();
		Field f = c.getDeclaredField(property);
		f.set(obj, value);
	}

	/**
	 * 获取一个属性对应的Get函数返回值的类型
	 * 
	 * @param c
	 * @param property
	 * @return
	 */
	public static Class<?> getClassByPropertyName(Class<?> c, String property) {

		try {
			char ch = (char) property.charAt(0);
			String methodName = new StringBuffer(property).delete(0, 1)
					.insert(0, Character.toUpperCase(ch)).insert(0, "get").toString();

			return c.getMethod(methodName, new Class<?>[0]).getReturnType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过Get方法获取某个指定字段的值（String类型）
	 * 
	 * @param obj
	 * @param property
	 * @return
	 */
	public static String getValueStrByGetMethod(Object obj, String property) {
		try {
			Class<?> c = obj.getClass();

			char ch = (char) property.charAt(0);
			String methodName = new StringBuffer(property).delete(0, 1)
					.insert(0, Character.toUpperCase(ch)).insert(0, "get").toString();

			Method method = c.getMethod(methodName);

			Object result = method.invoke(obj);
			if (result == null) {
				return null;
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 通过Object的set方法设置属性值
	 * 
	 * @param obj
	 * @param property
	 * @param value
	 */
	public static void setValueBySetMehtod(Object obj, String property, Object value) {
		try {
			Class<?> c = obj.getClass();

			char ch = (char) property.charAt(0);
			String methodName = new StringBuffer(property).delete(0, 1)
					.insert(0, Character.toUpperCase(ch)).insert(0, "set").toString();

			Method method = c.getMethod(methodName, getClassByPropertyName(c, property));

			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据Get方法获取请求参数列表
	 * 
	 * @param obj
	 * @return
	 */
	public static String getReqStrFromGetMethods(Object obj) {
		try {
			Class<?> c = obj.getClass();

			Method[] ms = c.getMethods();
			if (ms == null || ms.length == 0) {
				return null;
			}
			boolean isFirst = true;
			StringBuffer stringBuffer = new StringBuffer();

			for (int i = 0; i < ms.length; i++) {
				if (ms[i].getName().startsWith("get") && !ms[i].getName().equals("getClass")) {
					String res = getValFromObj(ms[i], obj);
					if (res != null) {
						if (isFirst) {
							isFirst = false;
						}
						else {
							stringBuffer.append("&");
						}
						stringBuffer.append(getPropertyNameFromGetMethod(ms[i].getName()));
						stringBuffer.append("=");
						stringBuffer.append(res);
					}
				}
			}
			return stringBuffer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取特定的所有Get函数返回值的描述串
	 * 
	 * @param method
	 * @param obj
	 * @return
	 */
	public static String getValFromObj(Method method, Object obj) {
		try {
			Object result = method.invoke(obj);

			if (result == null) {
				return null;
			}
			if (result instanceof List<?>) {
				List<?> c = (List<?>) result;
				if (c.size() == 0) {
					return "|";
				}
				StringBuffer stringBuffer = new StringBuffer();
				// boolean isFirst = true;
				for (int i = 0; i < c.size(); i++) {
					Object o = c.get(i);
					if (o != null) {
						// if (isFirst) {
						// isFirst = false;
						// }
						// else {
						// stringBuffer.append("|");
						// }
						stringBuffer.append(o.toString());
						stringBuffer.append("|");
					}
				}
				return stringBuffer.toString();
			}
			else {
				return result.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定属性对应的标准Get方法
	 * 
	 * @param id
	 * @return
	 */
	public static String getPropertyNameFromGetMethod(String id) {
		String str = id.substring(3, id.length());
		char ch = (char) str.charAt(0);
		return new StringBuffer(str).delete(0, 1).insert(0, Character.toLowerCase(ch)).toString();
	}

	// public static void writeByteArrayToFile(byte[] bytes, File file)
	// {
	// if (!file.exists()){
	// file.createNewFile();
	// }
	// FileOutputStream fileOutputStream = new FileOutputStream(file);
	// fileOutputStream.write(bytes);
	// }

	/**
	 * 从一个文件中读取内容，转化成byte[],注意必须是小文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static byte[] readByteArrayFromFile(File file) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = fileInputStream.read(buffer)) > 0) {
			byteArrayOutputStream.write(buffer, 0, length);
		}
		byteArrayOutputStream.flush();
		fileInputStream.close();

		byte[] result = byteArrayOutputStream.toByteArray();

		byteArrayOutputStream.close();

		return result;
	}

	/**
	 * 从一个输入流读出byte[],以输入流有EOF标志为准
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readByteArrayFromStream(InputStream inputStream) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = inputStream.read(buffer)) > 0) {
			byteArrayOutputStream.write(buffer, 0, length);
		}
		byteArrayOutputStream.flush();
		inputStream.close();

		byte[] result = byteArrayOutputStream.toByteArray();

		byteArrayOutputStream.close();

		return result;
	}

	/**
	 * 判断一个字符串是不是空串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}
	
	/**
	 * 隐藏手机号中间部分
	 * 
	 * @param str
	 * @return
	 */
	public static String ChangeMobileNum(String str) {
		String mobileNum = null;
		if (str != null && str.length() == 11) {
			String num = str.substring(0, 3);
			String num1 = str.substring(7, 11);
			mobileNum = num + "****" + num1;
		}
		return mobileNum;
	}
	
	/**
	 * 隐藏身份证号中间部分
	 * 
	 * @param str
	 * @return
	 */
	public static String ChangeUserNum(String str) {
		String userNum = null;
		if (str != null && (15 == str.length() || str.length() == 18)) {
			if (str.length() == 15) {
				String num = str.substring(0, 3);
				String num1 = str.substring(11, 15);
				userNum = num + "********" + num1;
			}
			else if (str.length() == 18) {
				String num = str.substring(0, 3);
				String num1 = str.substring(14, 18);
				userNum = num + "***********" + num1;
			}
			
		}
		return userNum;
	}
	
	//年月日 时间转换
	public static SimpleDateFormat dayFormat_yyyy_MM_dd;
	public static String changeDate(String date) {

		if (Utils.isEmpty(date)) {
			return null;
		}
		if (dateFormat_yyyyMMddHHmmss == null) {
			dateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
		}
		if (dayFormat_yyyy_MM_dd == null) {
			dayFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy年MM月dd日");
		}

		try {
			return dayFormat_yyyy_MM_dd.format(dateFormat_yyyyMMddHHmmss.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static SimpleDateFormat dateFormat_yyyyMMddHHmmss;
	public static SimpleDateFormat dayFormat_yyyy_MM_dd_HH_mm_ss;
	public static String changeDate1(String date) {
		
		if (Utils.isEmpty(date)) {
			return null;
		}
		if (dateFormat_yyyyMMddHHmmss == null) {
			dateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
		}
		if (dayFormat_yyyy_MM_dd_HH_mm_ss == null) {
			dayFormat_yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		}
		
		try {
			return dayFormat_yyyy_MM_dd_HH_mm_ss.format(dateFormat_yyyyMMddHHmmss.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 请求浏览器
	 *
	 * @param url
	 */
	public static void goWeb(Context context, final String url) {
		Intent intent = new Intent(context, AgentWebActivity.class);
		intent.putExtra(AgentWebFragment.KEY_URL, url);
		context.startActivity(intent);
	}

}
