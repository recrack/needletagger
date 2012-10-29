package org.softwaregeeks.needletagger.utils;

public class StringUtils {

	public static boolean isEquals(String value1, String value2) {

		if (isEmpty(value1) || isEmpty(value2)) return false;

		return value1.equals(value2);
	}

	public static boolean isEmpty(String value) {
		if (value == null || "null".equals(value) || "".equals(value)) return true;
		else return false;
	}

	public static String isChange(String value) {

		if (isEmpty(value)) value = "";
		value = value.replace("'", "''");

		return value;
	}
	
	public static String decodingKorean(String data) {
        String transData = "";

        try {
            if (data == null) transData = null;
            else transData = new String(data.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transData;
    }
}
