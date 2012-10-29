package org.softwaregeeks.needletagger;

import java.util.ArrayList;

import org.softwaregeeks.needletagger.common.Music;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicListAdapter extends BaseAdapter
{
	Typeface typeface;
	ArrayList<Music> list;
	LayoutInflater inflater;
	Context context;
	int layout;
	
	public MusicListAdapter(Context context, int layout, ArrayList<Music> items)
	{
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = items;
		this.layout = layout;
	}
	
	@Override
    public int getCount() {
	    return list.size();
    }

	@Override
    public Object getItem(int position) {
	    return list.get(position).getId();
    }

	@Override
    public long getItemId(int position) {
	    return position;
    }
	
	static class ViewHolder {
		ImageView nowPlayImageView;
		TextView trackTextView;
		TextView artistTextView;
	}
	
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	
    	View v = convertView;
    	ViewHolder holder;
    	
    	if(convertView == null)
    	{
    		holder = new ViewHolder();
    		v = inflater.inflate(layout, parent, false);
    		holder.nowPlayImageView = (ImageView) v.findViewById(R.id.nowplay);
    		holder.trackTextView = (TextView) v.findViewById(R.id.track);
    		holder.artistTextView = (TextView) v.findViewById(R.id.artist);
    		v.setTag(holder);
    	}
    	else
    	{
            holder = (ViewHolder)v.getTag();
    	}
    	
    	Music music = list.get(position);
    	holder.trackTextView.setText(music.getTrack());
    	holder.artistTextView.setText(music.getArtist());
    	
    	if(music.isPlaying())
    		holder.nowPlayImageView.setVisibility(View.VISIBLE);
    	else
    		holder.nowPlayImageView.setVisibility(View.GONE);
    	
    	if (position % 2 != 0)
			v.setBackgroundResource(R.drawable.bg_none);
		else
			v.setBackgroundResource(R.drawable.bg_selected);
    	
		return v;
	}
	
}
