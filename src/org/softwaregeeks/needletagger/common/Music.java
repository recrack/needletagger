package org.softwaregeeks.needletagger.common;

import android.graphics.Bitmap;

public class Music {
	
	private Long id;
	private Long albumId;
	private String album;
	private String path;
	private String track;
	private String artist;
	private String mimeType;
	private Bitmap artwork;
	private String imageUrl;
	private boolean isPlaying;
	
	public Music()
	{
		reset();
	}
	
	public Music(Music music)
	{
		if( music == null )
			return;
		
		reset();
		set(music);
	}
	
	public void set(Music music)
	{
		this.id = music.getId();
		this.album = music.getAlbum().trim();
		this.path = music.getPath().trim();
		this.track = music.getTrack().trim();
		this.artist = music.getArtist().trim();
		this.artwork = music.getArtwork();
		this.albumId = music.getAlbumId();
	}
	
	public void reset()
	{
		this.id = 0L;
		this.album = null;
		this.path = null;
		this.track = null;
		this.artist = null;
		this.artwork = null;
		this.albumId = 0L;
	}
	
	public void setPath(String path) {
		path = path.replaceAll("/mnt/","/");
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getArtist() {
		return artist;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getTrack() {
		return track;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getAlbum() {
		return album;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setArtwork(Bitmap artwork) {
		this.artwork = artwork;
	}

	public Bitmap getArtwork() {
		return artwork;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}