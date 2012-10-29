package org.softwaregeeks.needletagger.utils;

import java.util.ArrayList;

import org.softwaregeeks.needletagger.common.Music;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class MediaContentProviderHelper {
	
	static String[] columns = new String[] {
		"audio._id AS _id",
		MediaStore.Audio.Media.ARTIST,
		MediaStore.Audio.Media.ALBUM,
		MediaStore.Audio.Media.TITLE,
		MediaStore.Audio.Media.DATA,
		MediaStore.Audio.Media.MIME_TYPE,
		MediaStore.Audio.Media.ALBUM_ID
	};
	
	public static ArrayList<Music> getAll(Context context) {
		return getAll(context,null);
	}
	
	public static ArrayList<Music> getAll(Context context,String where) {
		
		ArrayList<Music> list = new ArrayList<Music>();
		Cursor c = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				columns, where, null, null);
		try {
			if (c == null || c.getCount() == 0) {
				return list;
			}
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) {
				Music music = new Music();
				music.setId(c.getLong(0));
				music.setArtist(c.getString(1));
				music.setAlbum(c.getString(2));
				music.setTrack(c.getString(3));
				music.setPath(c.getString(4));
				music.setMimeType(c.getString(5));
				music.setAlbumId(c.getLong(6));

				list.add(music);
				c.moveToNext();
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return list;
	}
	
	public static String getArtworkPath(Context context, Long albumId) {
		Cursor c = query(context, MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Audio.Albums.ALBUM_ART },
				MediaStore.Audio.Albums._ID + "=" + albumId, null, null);
		try {
			if (c == null || c.getCount() == 0) {
				return null;
			}
			int size = c.getCount();
			if (size != 1)
				return null;

			c.moveToNext();
			return c.getString(0);
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}
	
	public static String getSongPath(Context context, String title) {
		if( title == null )
			return null;
		
		title = title.replaceAll("'","''");
		Cursor c = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Audio.Media.DATA },
				MediaStore.Audio.Media.TITLE + "='" + title + "'", null, null);
		try {
			if (c == null || c.getCount() == 0) {
				return null;
			}
			int size = c.getCount();
			if (size != 1)
				return null;

			c.moveToNext();
			return c.getString(0);
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	public static Cursor query(Context context, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder,
			int limit) {
		try {
			ContentResolver resolver = context.getContentResolver();
			if (resolver == null) {
				return null;
			}
			if (limit > 0) {
				uri = uri.buildUpon().appendQueryParameter("limit", "" + limit)
						.build();
			}
			return resolver.query(uri, projection, selection, selectionArgs,
					sortOrder);
		} catch (UnsupportedOperationException ex) {
			return null;
		}
	}

	public static Cursor query(Context context, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		return query(context, uri, projection, selection, selectionArgs,
				sortOrder, 0);
	}
}
