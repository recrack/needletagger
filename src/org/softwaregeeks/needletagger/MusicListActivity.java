package org.softwaregeeks.needletagger;

import java.util.ArrayList;

import org.softwaregeeks.needletagger.common.ActivityHelper;
import org.softwaregeeks.needletagger.common.ConfigurationManager;
import org.softwaregeeks.needletagger.common.Music;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class MusicListActivity extends Activity
{
	public static final String UPDATED_INTENT = "org.softwaregeeks.needletagger.updated";
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	ActivityHelper.setHeaderProgressBar(MusicListActivity.this,true);
	    	setNowPlayMusic(musicList);
	    	updateListView();
	    	ActivityHelper.setHeaderProgressBar(MusicListActivity.this,false);
	    }
	};
	
	private String keyword;
	private OnClickListener onClickListener;
	
	private ImageButton buttonSearch;
	private EditText editKeyword;
	
	private ArrayList<Music> musicList = new ArrayList<Music>();
	private MusicListAdapter listAdapter;
	private ListView listView;
	
	private MusicListService service = new MusicListService();
	private Thread dataLoadThread;
	private Handler handler;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		setInit();
		setListView();
		setHandler();
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(UPDATED_INTENT);
		registerReceiver(broadcastReceiver,intentFilter);
    }
	
	@Override
	protected void onStart() {
		super.onStart();
		loadData();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

	private void setInit()
	{
		ConfigurationManager.load(this);
		
		setOnClickListener();
		ActivityHelper.setNavigationBar(this);
		
		listView = (ListView) findViewById(R.id.listView);
		editKeyword = (EditText)findViewById(R.id.keyword);
		buttonSearch = (ImageButton)findViewById(R.id.search);
		buttonSearch.setOnClickListener(onClickListener);
		editKeyword.setOnClickListener(onClickListener);
		editKeyword.setInputType(0);
	}
	
	public void setOnClickListener()
	{
		onClickListener = new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				switch (v.getId())
				{
				case R.id.keyword:
					{
						editKeyword.setInputType(1);
					}
				break;
				case R.id.search:
					{
						keyword = editKeyword.getText().toString();
						loadData();
					}
				}
			}
		};
	}
	
	private void setListView()
	{
		listAdapter = new MusicListAdapter(this,R.layout.list_row, musicList);
		listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new OnItemClickListener()
        {
        	@SuppressWarnings("rawtypes")
			@Override
			public void onItemClick(AdapterView parent, View view, int position, long id)
			{
				Music music = musicList.get(position);
				if( music == null )
					return;
				
				Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
				intent.putExtra("id",music.getId());
				intent.putExtra("track",music.getTrack());
				intent.putExtra("artist",music.getArtist());
				intent.putExtra("album",music.getAlbum());
				intent.putExtra("path",music.getPath());
				intent.putExtra("albumId",music.getAlbumId());
				startActivity(intent);
				overridePendingTransition(0,0);
			}
		});
	}
	
	private void updateListView()
	{
		listAdapter.notifyDataSetChanged();
	}
	
	private void setHandler()
	{
		handler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				
				if( msg != null )
				{
					switch (msg.what)
					{
					case 0:
						@SuppressWarnings("unchecked")
						ArrayList<Music> list = (ArrayList<Music>)msg.obj;
						if( list != null )
						{
							musicList.clear();
							setNowPlayMusic(list);
							musicList.addAll(list);
							updateListView();
						}
						ActivityHelper.setHeaderProgressBar(MusicListActivity.this,false);
					break;
					}
				}
			}
		};
	}
	
	private void setNowPlayMusic(ArrayList<Music> list)
	{
		if( list == null )
			return;
		
		Music music = ConfigurationManager.getNowPlayingMusic();
		if( music.getId() != 0 && (keyword == null || "".equals(keyword)) )
		{
			if( list.size() > 0 )
			{
				Music prevMusic = list.get(0);
				if( prevMusic != null && prevMusic.isPlaying() )
					list.remove(0);
			}
			music.setPlaying(true);
			list.add(0,music);
		}
	}
	
	private void loadData()
	{
		service.setNetworkHandler(handler);
		service.setContext(this);
		service.setKeyword(keyword);
		dataLoadThread = new Thread(service);
		dataLoadThread.start();
		
		ActivityHelper.setHeaderProgressBar(this,true);
	}
}
