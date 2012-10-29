package org.softwaregeeks.needletagger.soapclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.softwaregeeks.needletagger.utils.HttpClient;
import org.softwaregeeks.needletagger.utils.Mp3Utils;
import org.softwaregeeks.needletagger.utils.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GomAudioClient {
    
    private static final String SERVER = "http://newlyrics.gomtv.com/xml/xml_searchtag.php?file_key=";
    private String path;
    private String title;
    private String artist;
    private String album;
    private String lyric;
    private String hashKey;
    private String imageUrl;
    private boolean isProcessed = false;
    
    public boolean excute() {
        
        try {
            setHashKey(Mp3Utils.getHashKey(getPath(),102400));
            String url = SERVER + hashKey;
            String html = HttpClient.execute(url, "EUC-KR");
            parse(html);
            
            if( !StringUtils.isEmpty(getTitle()) && !StringUtils.isEmpty(getArtist()) )
                setProcessed(true);
                
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        return isProcessed();
    }
    
    public void parse(String html) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = factory.newDocumentBuilder();
        
        InputStream is = new ByteArrayInputStream( html.getBytes("EUC-KR") );
        Document doc = b.parse(is);
        Element root = doc.getDocumentElement();
        
        NodeList items = root.getElementsByTagName("item");
        for ( int i = 0 ; i < items.getLength() ; i++ ) {
            Node item = items.item(i);
            NamedNodeMap attributes = item.getAttributes();
            for ( int j = 0 ; j < attributes.getLength(); j++ ) {
                Node attribute = attributes.item(j);
                String name = attribute.getNodeName();
                String value = attribute.getNodeValue();
                
                if( StringUtils.isEquals("title",name)) {
                    setTitle(value);
                } else if( StringUtils.isEquals("album",name)) {
                    setAlbum(value);
                } else if( StringUtils.isEquals("artist",name)) {
                    setArtist(value);
                } else if( StringUtils.isEquals("image",name)) {
                    setImageUrl(value);
                }
            }
        }
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
    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
    public String getLyric() {
        return lyric;
    }
    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }
    public String getHashKey() {
        return hashKey;
    }
    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }
    public boolean isProcessed() {
        return isProcessed;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
