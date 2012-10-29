package org.softwaregeeks.needletagger.common;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.softwaregeeks.needletagger.utils.DeviceInfomation;
import org.softwaregeeks.needletagger.utils.HttpClient;

import android.util.Log;

public class LogManager {
	
	private final static boolean isDebug = true;
	private final static boolean isInfo = true;
	private final static boolean isError = true;
	
	public static void debug(String tag,String message)
	{
		if( isDebug )
			Log.d(tag, message);
	}
	
	public static void info(String tag,String message)
	{
		if( isInfo )
			Log.i(tag, message);
	}
	
	public static void error(String tag,String message)
	{
		if( isError )
			Log.e(tag, message);
	}
	
	public static final String URL = "http://needletagger.appspot.com/Log";
	public static boolean sendLogMessage(String stackTrace,String message)
	{
		List<NameValuePair> nameValuePairs = DeviceInfomation.getInformation();
		nameValuePairs.add(new BasicNameValuePair("stackTrace", stackTrace));
		nameValuePairs.add(new BasicNameValuePair("message", message));
		String html = HttpClient.execute(URL,"UTF-8","POST",nameValuePairs);
		
		if( html.startsWith("true"))
			return true;
		else
			return false;
	}
}