package org.softwaregeeks.needletagger.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.softwaregeeks.needletagger.utils.ArtworkUtils;
import org.softwaregeeks.needletagger.utils.HttpClient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

public class MusicManager {
	
	public static void performMediaScanner(Context context)
	{
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	}
	
	public static void stopMusic(Context context)
	{
		if( ConfigurationManager.getDevice().equals("HTC Desire") )
		{
			context.sendBroadcast(new Intent("com.htc.music.musicservicecommand.pause"));
		}
	}
	
	public static void playMusic(Context context)
	{
		if( ConfigurationManager.getDevice().equals("HTC Desire") )
		{
			context.sendBroadcast(new Intent("com.htc.music.musicservicecommand.previous"));
//			context.sendBroadcast(new Intent("com.htc.music.musicservicecommand.togglepause"));
		}
	}
	
	public static void updateMusicMetadata(Context context, Music music) throws CannotWriteException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {

		if (music == null || music.getPath() == null
				|| "".equals(music.getPath()))
			return;

		AudioFile f = AudioFileIO.read(new File(music.getPath()));
        Tag tag = f.getTag();
        tag.setField(FieldKey.TITLE, music.getTrack());
        tag.setField(FieldKey.ARTIST, music.getArtist());
        tag.setField(FieldKey.ALBUM, music.getAlbum());
        
        if( music.getArtwork() != null && music.getArtwork().getWidth() > 0 )
        {
            Artwork artwork = new Artwork();
            artwork.setFromBitmap(music.getArtwork());
            
            if (tag.getArtworkList() != null)
                tag.deleteArtworkField();
            
            tag.setField(artwork);
        }
        AudioFileIO.write(f);

        if (music.getAlbumId() > -1) {
            ArtworkUtils.deleteArtwork(context, music.getAlbumId());
            ArtworkUtils.clearAlbumArtCache();
        }
	}

	public static Bitmap getBugsImage(String keyword) {
		String url = getBugsImageUrl(keyword);
		if (url == null)
			return null;

		return getImageBitmap(url);
	}

	public static String getBugsImageUrl(String keyword) {
		String image = null;

		try {
			String urlString = "http://search.bugs.co.kr/track?q="
					+ java.net.URLEncoder.encode(keyword, "UTF-8");
			String html = HttpClient.execute(urlString, "UTF-8");

			int pos = html.indexOf("bugs.music.listen('");
			if (pos == -1)
				return null;

			int pos2 = html.indexOf("')", pos);
			String idx = html.substring(pos + 19, pos2);

			urlString = "http://music.bugs.co.kr/player/track/" + idx;
			html = HttpClient.execute(urlString,"UTF-8");
			pos = html.indexOf("\"album_id\":");
			if (pos == -1)
				return null;

			pos2 = html.indexOf(",", pos);
			String albumId = html.substring(pos + 11, pos2);
			int album_id = Integer.parseInt(albumId);
			image = "http://image.bugsm.co.kr/album/images/224/"
					+ (album_id / 100) + "/" + albumId + ".jpg";

			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getImageBitmap(String url) {
		Bitmap bitmap = null;
		InputStream is = null;
		try {
			is = new URL(url).openStream();
			bitmap = BitmapFactory.decodeStream(is);
			if (is != null)
				is.close();
		} catch (Exception e) {
			e.toString();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}

		return bitmap;
	}
}