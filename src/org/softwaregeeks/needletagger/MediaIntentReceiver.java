package org.softwaregeeks.needletagger;

import org.softwaregeeks.needletagger.common.ConfigurationManager;
import org.softwaregeeks.needletagger.common.Music;
import org.softwaregeeks.needletagger.utils.MediaContentProviderHelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MediaIntentReceiver extends BroadcastReceiver {
	
	public static final int NOTIFICATION_ID = 300000;
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		try
		{
			ConfigurationManager.checkLoad(context);
			
			Music music = getMusic(context, intent);
			if( music == null )
				return;
			
			if( music.getId() == 0L )
				return;
			
			if(music.isPlaying())
				ConfigurationManager.getNowPlayingMusic().set(music);
			else
				ConfigurationManager.getNowPlayingMusic().reset();
			
			context.sendBroadcast(new Intent(MusicListActivity.UPDATED_INTENT));
			
			// Notification Bar
			//
			if( !ConfigurationManager.isPlayerLink() )
				return;
			
			if(music.isPlaying())
			{
				Intent sendIntent = new Intent(context,EditorActivity.class);
				sendIntent.putExtra("id",music.getId());
				sendIntent.putExtra("track",music.getTrack());
				sendIntent.putExtra("artist",music.getArtist());
				sendIntent.putExtra("album",music.getAlbum());
				sendIntent.putExtra("path",music.getPath());
				sendIntent.putExtra("albumId",music.getAlbumId());
				onNotify(context, sendIntent, music);
			}
			else
			{
				offNotify(context);
			}
		}
		catch (Exception e) {
			//TODO
		}
	}
	
	private Music getMusic(Context context, Intent intent) throws Exception
	{
		String intentAction = intent.getAction();
		boolean isPlaying = false;
		
		Long id = null;
		String track = null;
		String artist = null;
		String album = null;
		Long albumId = null;
		
		if( intentAction.startsWith("com.android.music") )
		{
			id = (long) intent.getLongExtra("id",0L);
			track = intent.getStringExtra("track");
			artist = intent.getStringExtra("artist");
			album = intent.getStringExtra("album");
			albumId = (long) intent.getLongExtra("albumid",0L);
			
			if( id != 0L )
				isPlaying = true;
		}
		else if ( intentAction.startsWith("com.htc.music") )
		{
			id = (long) intent.getIntExtra("id",0);
			track = intent.getStringExtra("track");
			artist = intent.getStringExtra("artist");
			album = intent.getStringExtra("album");
			albumId = (long) intent.getIntExtra("albumid",0);
			isPlaying = intent.getBooleanExtra("isplaying",false);
		}
		
		String path = MediaContentProviderHelper.getSongPath(context,track);
		
		if( id == null )
			id = 0L;
		
		if( albumId == null )
			albumId = 0L;
		
		if( track == null || "".equals(track) || "null".equals(track) )
		{
			track = "";
		}
		
		if( artist == null || "".equals(artist) || "null".equals(artist) )
		{
			artist = "";
		}
		
		if( album == null || "".equals(album) || "null".equals(album) )
		{
			artist = "";
		}
		
		if( path == null || "".equals(path) || "null".equals(path) )
		{
			path = "";
		}
		
		Music music = new Music();
		music.setId(id);
		music.setAlbum(album);
		music.setTrack(track);
		music.setArtist(artist);
		music.setPlaying(isPlaying);
		music.setPath(path);
		music.setAlbumId(albumId);
		
		return music;
	}

	public static void onNotify(Context context, Intent sendIntent, Music music)
	{
		final PendingIntent pendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID, sendIntent,Intent.FLAG_ACTIVITY_NEW_TASK);
		final Notification notification = new Notification(R.drawable.icon_tag,"NeedleTagger", System.currentTimeMillis());
		notification.setLatestEventInfo(context,"NeedleTagger, ID3Tag Editor",music.getPath(),pendingIntent);
		final NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
	
	public static void offNotify(Context context)
	{
		final NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(NOTIFICATION_ID);
	}
}