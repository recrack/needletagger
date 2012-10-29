package org.softwaregeeks.needletagger.soapclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.softwaregeeks.needletagger.utils.Mp3Utils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


public class AlsongSoapClient {
	private final static String alsongUrl = "http://lyrics.alsong.co.kr/alsongwebservice/service1.asmx";
	private HttpURLConnection httpConn;

	private String resultStr = "";
	
	private int tagSize = 0;
	private String hashKey;

	public AlsongSoapClient(String fileName) throws Exception {
		String checkSum;
		connServer(alsongUrl);

//		checkSum = getFileMD5(fileName);
		checkSum = getFileMD5_v2(fileName);
		setHashKey(checkSum);
		sendXml(checkSum);
	}

	private void connServer(String SOAPUrl) throws Exception {
		// Create the connection where we're going to send the file.
		URL url = new URL(SOAPUrl);
		URLConnection connection = url.openConnection();

		httpConn = (HttpURLConnection) connection;
	}

	private void sendXml(String checkSum) throws IOException{
		String inStr = "<?xml version='1.0' encoding='UTF-8'?> " + 
		"<SOAP-ENV:Envelope xmlns:SOAP-ENV='http://www.w3.org/2003/05/soap-envelope' xmlns:SOAP-ENC='http://www.w3.org/2003/05/soap-encoding' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:ns2='ALSongWebServer/Service1Soap' xmlns:ns1='ALSongWebServer' xmlns:ns3='ALSongWebServer/Service1Soap12'> " + 
		"<SOAP-ENV:Body> " +
		"<ns1:GetLyric5> " +
		"<ns1:stQuery> " +
		"<ns1:strChecksum>" +
		checkSum +
		"</ns1:strChecksum> " +
		"<ns1:strVersion>1.93</ns1:strVersion> " +
		"<ns1:strMACAddress></ns1:strMACAddress> " +
		"<ns1:strIPAddress></ns1:strIPAddress> " +
		"</ns1:stQuery> " +
		"</ns1:GetLyric5> " +
		"</SOAP-ENV:Body> " +
		"</SOAP-ENV:Envelope>";

		InputStream is = new ByteArrayInputStream( inStr.getBytes() );
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		// Copy the SOAP file to the open connection.
		copy(is,bout);
		is.close();

		byte[] b = bout.toByteArray();

		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty( "Content-Length", String.valueOf( b.length ) );
		httpConn.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
		httpConn.setRequestMethod( "POST" );
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);

		// Everything's set up; send the XML that was read in to b.
		OutputStream out = httpConn.getOutputStream();
		out.write( b );    
		out.close();

		// Read the response and write it to standard out.
		InputStreamReader isr =
			new InputStreamReader(httpConn.getInputStream(), "UTF-8");
		BufferedReader in = new BufferedReader(isr);

		String inputLine;

		while ((inputLine = in.readLine()) != null){
			resultStr += inputLine;
			//			System.out.println(inputLine);
		}

		in.close();
		bout.close();
		isr.close();
	}

	// copy method from From E.R. Harold's book "Java I/O"
	private static void copy(InputStream in, OutputStream out) throws IOException {

		// do not allow other threads to read from the
		// input or write to the output while copying is
		// taking place

		synchronized (in) {
			synchronized (out) {

				byte[] buffer = new byte[256];
				while (true) {
					int bytesRead = in.read(buffer);
					if (bytesRead == -1) break;
					out.write(buffer, 0, bytesRead);
				}
			}
		}
	} 


	//XML�� �Ľ��ؼ� �ʿ��� �κ��� ��ȯ�Ѵ�.
	public String[] getResultXmlParsing(){
		String xml = resultStr;

		String[] str = new String[18];

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			// parse() �޼ҵ忡 �� �� �ִ� ���� ��, �Ʒ�ó�� URI String �� �ֽ��ϴ�.

			InputStream is = new ByteArrayInputStream( xml.getBytes("UTF-8") );

			Document doc = builder.parse(is);

			NodeList channel = doc.getElementsByTagName("GetLyric5Result");
			// _n ������ <GetLyric5Result> ~~~ </GetLyric5Result> �� ���� ������ ���ϴ�.
			NodeList _n = channel.item(0).getChildNodes();
			
			//StringBuffer buffer = new StringBuffer();
			for (int i=0; i<18; i++) {
				str[i] = getTextContent(_n.item(i));
				
				//				System.out.println("test : [" + i + "] " + _n.item(i).getTextContent());
			} 


			is.close();
		} catch(Exception e) {
			System.out.println(e.toString());
		}

		return str;
	}

	
	private String getFileMD5_v2(String fileName)  throws Exception {
		return Mp3Utils.getHashKey(fileName);
	}
	
	public int getTagSize(){
		return tagSize;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String getHashKey() {
		return hashKey;
	}

	public static String getTextContent(Node node) throws DOMException
	{
		String textContent = "";
		
		if (node.getNodeType() == Node.ATTRIBUTE_NODE)
		{
			textContent = node.getNodeValue();	
		}
		else
		{
			Node child = node.getFirstChild();
			if (child != null)
			{
				Node sibling = child.getNextSibling();
				if (sibling != null)
				{
					StringBuffer sb = new StringBuffer();
					getTextContent(node, sb);
					textContent = sb.toString();
				}
				else
				{					
					if (child.getNodeType() == Node.TEXT_NODE)			
					{
						textContent = child.getNodeValue();
					}
					else
					{
						textContent = getTextContent(child);
					}
				}
			}
		}
		
		return textContent;
	}


	private static void getTextContent(Node node, StringBuffer sb) throws DOMException
	{
	    Node child = node.getFirstChild();
	    while (child != null)
	    {
			if (child.getNodeType() == Node.TEXT_NODE)			
			{
				sb.append(child.getNodeValue());
			}
			else
			{
				getTextContent(child, sb);
			}
	        child = child.getNextSibling();
	    }
	}	


	public static void setTextContent(Node node, String textContent) throws DOMException
	{
		if (node.getNodeType() == Node.ATTRIBUTE_NODE)
		{
			if (textContent == null) textContent = "";
			node.setNodeValue(textContent);	
		}
		else
		{
			Node child;
	        while ((child = node.getFirstChild()) != null)
	        {
	            node.removeChild(child);
	        }
	        
	        if ( textContent != null && "".equals(textContent) && "null".equals(textContent) )
	        {
				Text textNode = node.getOwnerDocument().createTextNode(textContent);
				node.appendChild(textNode);
	        }
		}
	}
}

