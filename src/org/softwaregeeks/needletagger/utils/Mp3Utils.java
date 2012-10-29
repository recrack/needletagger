package org.softwaregeeks.needletagger.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import org.farng.mp3.MP3File;

public class Mp3Utils {

	public static String getHashKey(String fileName) throws Exception
	{
		return getHashKey(fileName, 163840);
	}
	
	public static String getHashKey(String fileName, int size) throws Exception
    {
        String s = "";
    
        MP3File file = new MP3File();
        int tagSize = (int)file.getMp3StartByte(new File(fileName));
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fin = new FileInputStream(fileName);
        DigestInputStream in = new DigestInputStream(fin,md);
        
        fin.getChannel().position(tagSize);
        byte[] bytes = new byte[size];
        in.read(bytes,0,size);

        md = in.getMessageDigest();
        s = bytesToHex(md.digest());
        
        fin.close();
        in.close();
        
        file = null;
        bytes = null;
        
        return s;
    }
	
	public static String bytesToHex(byte[] a)
	{
		StringBuffer s = new StringBuffer();
		for(int i=0;i<a.length;++i)
		{
			s.append(Character.forDigit((a[i]>>4) & 0x0f, 16));
			s.append(Character.forDigit(a[i] & 0x0f, 16));
		}
		return s.toString();            
	}
}
