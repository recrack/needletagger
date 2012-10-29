package org.softwaregeeks.needletagger;

import org.softwaregeeks.needletagger.utils.ArtworkUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class MusicArtworkView extends View {
	
	private Context context;
	private long songId = -1L;
	private long albumId = -1L;
	private int width = 345;
	private int height = 345;
	private Bitmap bitmap;
	private Canvas canvas;
	
	public MusicArtworkView(Context context) {
		super(context);
		this.context = context;
	}
	
	public MusicArtworkView(Context context, AttributeSet attrs) { 
		super(context, attrs);
		this.context = context;
	}
	
	public MusicArtworkView(Context context,long id,long albumId) {
		super(context);
		this.context = context;
		this.setSongId(id);
		this.setAlbumId(albumId);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		if( canvas == null )
			return;
		
		if( this.canvas == null )
			this.canvas = canvas;
		
		if( getSongId() == -1L )
			return;
		
		super.onDraw(canvas);
		if( getBitmap() == null )
		{
			setBitmap(ArtworkUtils.getArtwork(context,getSongId(),getAlbumId()));
			setBitmap(getResizedBitmap(bitmap));
		}
		
		canvas.drawBitmap(getBitmap(),0,0,null);
	}
	
	public Bitmap getResizedBitmap(Bitmap bitmap)
	{
		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}
	
	public void reset()
	{
		this.setBitmap(null);
	}
	
	public void onDraw() {
		onDraw(this.canvas);
	}
	
	public void setData(long songId,long albumId) {
		this.setSongId(songId);
		this.setAlbumId(albumId);
	}
	
	public void setSongId(long songId) {
		this.songId = songId;
	}

	public long getSongId() {
		return songId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public long getAlbumId() {
		return albumId;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
}
