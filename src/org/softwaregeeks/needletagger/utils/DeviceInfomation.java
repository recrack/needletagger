package org.softwaregeeks.needletagger.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.softwaregeeks.needletagger.common.ConfigurationManager;

import android.os.Build;

public class DeviceInfomation {
	
	public static List<NameValuePair> getInformation(){
		
		Locale systemLocale = ConfigurationManager.getSystemLocale();
		List<NameValuePair> datas = new ArrayList<NameValuePair>();
		datas.add(new BasicNameValuePair("sdk",Build.VERSION.RELEASE));
		datas.add(new BasicNameValuePair("carrier",Build.BRAND));
		datas.add(new BasicNameValuePair("model",Build.MODEL));
		datas.add(new BasicNameValuePair("display",Build.DISPLAY));
		if( systemLocale != null )
		{
			datas.add(new BasicNameValuePair("country",systemLocale.getDisplayCountry()));
			datas.add(new BasicNameValuePair("language",systemLocale.getLanguage()));
		}
		
		return datas;
	}
}
