package org.softwaregeeks.needletagger.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClient {
	public static String execute(String url, String encoding) {
		return execute(url, encoding, "GET", null);
	}
	
	public static String execute(String url, String encoding, String method, List<NameValuePair> datas ) {
		return execute(url, encoding, method, "String", datas, null);
	}
	
	public static String execute(String url, String encoding, String method, String returnType, List<NameValuePair> datas, MultipartEntity multipartEntity) {
		HttpRequestBase requestBase;
		StringBuffer buffer = new StringBuffer();
		buffer.setLength(0);
		
		boolean isPostMethod = false;
		if ("POST".equals(method.toUpperCase())) {
			isPostMethod = true;
		}
		
		if (isPostMethod) {
			HttpPost httpPost = null;
			httpPost = new HttpPost(url);
			requestBase = (HttpRequestBase) httpPost;
			
			if( multipartEntity == null ) {
				try {
					UrlEncodedFormEntity entityRequest;
					entityRequest = new UrlEncodedFormEntity(datas, "UTF-8");
					httpPost.setEntity(entityRequest);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else if ( isPostMethod && multipartEntity != null ) {
				httpPost.setEntity(multipartEntity);
			}
			
		} else {
			requestBase = new HttpGet(url);
		}

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;

		try {
			response = httpclient.execute(requestBase);
			HttpEntity entity = response.getEntity();

			BufferedReader br = null;
			String s = null;
			try {
				br = new BufferedReader(new InputStreamReader(entity.getContent(), encoding));
				while ((s = br.readLine()) != null) {
					buffer.append(s);
					buffer.append("\n");
				}
				br.close();
			} finally {
				if (br != null)
					try {
						br.close();
					} catch (Exception e) {
					}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}

	public static Object getContentsByObject(String url, String encoding, String method, List<NameValuePair> datas) {
		HttpRequestBase requestBase;
		StringBuffer buffer = new StringBuffer();
		Object obj = null;

		if ("POST".equals(method.toUpperCase())) {
			HttpPost httpPost = null;
			try {
				httpPost = new HttpPost(url);
				UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(datas, "UTF-8");
				httpPost.setEntity(entityRequest);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			requestBase = (HttpRequestBase) httpPost;
		} else
			requestBase = new HttpGet(url);

		buffer.setLength(0);
		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpResponse response;

		try {
			response = httpclient.execute(requestBase);
			HttpEntity entity = response.getEntity();
			InputStream in = null;

			try {
				in = entity.getContent();
				ObjectInputStream oin = new ObjectInputStream(in);
				obj = oin.readObject();
				oin.close();
				in.close();
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (Exception e) {
					}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
}