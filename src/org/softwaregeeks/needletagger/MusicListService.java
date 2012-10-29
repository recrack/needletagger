package org.softwaregeeks.needletagger;

import java.util.ArrayList;

import org.softwaregeeks.needletagger.common.ConfigurationManager;
import org.softwaregeeks.needletagger.common.Music;
import org.softwaregeeks.needletagger.utils.MediaContentProviderHelper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

public class MusicListService implements Runnable
{
	private String keyword;
	private Handler dataLoadHandler;
	private Context context;
	
	@Override
	public void run()
	{
		ArrayList<Music> list = null;
		if( keyword != null )
		{
			String where = MediaStore.Audio.Media.TITLE + " like '%" + keyword + "%' OR " + MediaStore.Audio.Media.ARTIST + " like '%" + keyword + "%'";
			
			// Playing List
			list = MediaContentProviderHelper.getAll(getContext(),where);
		}
		else
		{
			list = MediaContentProviderHelper.getAll(getContext());
		}
		
		Message message = Message.obtain(dataLoadHandler,0,list);
		dataLoadHandler.sendMessage(message);
	}
	
	public void setNetworkHandler(Handler networkHandler) {
		this.dataLoadHandler = networkHandler;
	}

	public Handler getNetworkHandler() {
		return dataLoadHandler;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
}