package org.softwaregeeks.needletagger.soapclient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlsongClient {
	private static final String patternStr = "\\[\\d{2}:\\d{2}\\.\\d{2}\\]";
	private AlsongSoapClient cli;
	
	private String[] splitStr;
	private String[] splitTime;
	
	private String title;
	private String artist;
	private String album;
	
	private String strLyric;
	private String hashKey;
	private boolean isProcessed;
	
	public int getTagSize(){
		if(cli != null){
			return cli.getTagSize();
		} else {
			return 0;
		}
	}
	
	public boolean initLyric(String fileName){
		if(fileName == null)
		{
			setProcessed(false);
			return isProcessed();
		}
		
		try{
			String[] result;
			cli = new AlsongSoapClient(fileName);
			setHashKey(cli.getHashKey());
			
			result = cli.getResultXmlParsing();
			
			
			if( "-1".equals(result[1]) )
			{
				setProcessed(false);
				return isProcessed();
			}
				
			
			
			setTitle(result[3]);
			setArtist(result[4]);
			setAlbum(result[5]);
			strLyric = result[8];
			
			
			int index = 0;
			
			splitStr = strLyric.split(patternStr);
			splitTime = new String[splitStr.length-1];
			
			//System.out.println(strLyric);
			//System.out.println(splitStr[1]);
			
			String patStr = "\\d{2}:\\d{2}\\.\\d{2}";
			
			Pattern pattern = Pattern.compile(patStr);
			Matcher matcher = pattern.matcher(strLyric);
			while(matcher.find()){
				String match = matcher.group();
				match = match.replace(":", "");
				match = match.replace(".", "");
				
				splitTime[index] = match;
				index++;
			}
			
			setProcessed(true);
			
		}catch(Exception ex){
			System.out.println(ex);
			setProcessed(false);
		}
		
		return isProcessed();
	}
	
	public String[] getSplitLyric(){
		return splitStr;
	}
	
	public String getLyric()
	{
		StringBuffer buffer = new StringBuffer();
		for( String temp : getSplitLyric() )
		{
			buffer.append(temp);
			//buffer.append("\r\n");
		}
		return buffer.toString();
	}
	
	public String[] getSplitTime(){
		return splitTime;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getArtist() {
		return artist;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAlbum() {
		return album;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

}
