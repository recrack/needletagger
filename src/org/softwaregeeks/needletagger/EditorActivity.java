package org.softwaregeeks.needletagger;

import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.softwaregeeks.needletagger.common.ActivityHelper;
import org.softwaregeeks.needletagger.common.Music;
import org.softwaregeeks.needletagger.common.MusicManager;
import org.softwaregeeks.needletagger.soapclient.AlsongClient;
import org.softwaregeeks.needletagger.soapclient.GomAudioClient;
import org.softwaregeeks.needletagger.utils.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditorActivity extends Activity {

    private static final int MESSAGE_GET_IMAGE = 200;
    private static final int MESSAGE_GET_INFORMATION = 201;
    private static final int MESSAGE_GET_INFORMATION_GOMPLAYER = 202;
    private static final int MESSAGE_SAVE = 203;

    private static final int DIALOG_CHOICE = 100;
    private static final int DIALOG_CHOICE_INFORMATION = 101;

    private Thread processThread;
    private Handler processHandler;

    private Music music = new Music();
    private String imageUrl;

    private Button alsongButton;
    private Button okButton;
    private EditText trackEditText;
    private EditText artistEditText;
    private EditText albumEditText;
    private TextView pathTextView;
    private MusicArtworkView musicArtworkView;
    private OnClickListener onClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setInit();
        setHandler();
    }

    private void setInit() {
        MediaIntentReceiver.offNotify(this);
        ActivityHelper.setNavigationBar(this);
        ActivityHelper.setHeaderProgressBar(this, false);

        parseIntent(getIntent(), this.music);
        setOnClickListener();
        setComponent();
        setComponentInformation(this.music);
        setArtwork();
    }

    private void setHandler() {
        processHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg != null) {
                    switch (msg.what) {
                    case MESSAGE_GET_IMAGE: {
                        Bitmap bitmap = (Bitmap) msg.obj;
                        if (bitmap != null) {
                            music.setArtwork(bitmap);
                            bitmap = musicArtworkView.getResizedBitmap(bitmap);
                            musicArtworkView.setBitmap(bitmap);
                            musicArtworkView.onDraw();
                            musicArtworkView.invalidate();
                        } else {
                            Toast.makeText(EditorActivity.this, getString(R.string.dialogArtworkNoCaption),
                                Toast.LENGTH_LONG).show();
                        }
                    }
                        break;
                    case MESSAGE_GET_INFORMATION_GOMPLAYER: {
                        GomAudioClient gomAudioClient = (GomAudioClient) msg.obj;
                        if (gomAudioClient.isProcessed()) {
                            music.setTrack(gomAudioClient.getTitle());
                            music.setArtist(gomAudioClient.getArtist());
                            music.setAlbum(gomAudioClient.getAlbum());
                            music.setImageUrl(gomAudioClient.getImageUrl());
                            setComponentInformation(music);
                            ActivityHelper.setEndProcess(EditorActivity.this);
                            getArtworkFromGom();
                        } else {
                            Toast.makeText(EditorActivity.this, getString(R.string.dialogInformationNoCaption),
                                Toast.LENGTH_LONG).show();
                        }
                    }
                        break;
                    case MESSAGE_SAVE: {
                        String messageString = (String) msg.obj;
                        MusicManager.playMusic(EditorActivity.this);
                        Toast.makeText(EditorActivity.this, messageString, Toast.LENGTH_LONG).show();
                    }
                        break;
                    }

                    ActivityHelper.setEndProcess(EditorActivity.this);
                }
            }
        };
    }

    public void setOnClickListener() {
        onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                case R.id.title: {
                    trackEditText.setInputType(1);
                }
                    break;
                case R.id.album: {
                    albumEditText.setInputType(1);
                }
                    break;
                case R.id.artist: {
                    artistEditText.setInputType(1);
                }
                    break;
                case R.id.artwork: {
                    showDialog(DIALOG_CHOICE);
                }
                    break;
                case R.id.alsong: {
                    showDialog(DIALOG_CHOICE_INFORMATION);
                }
                    break;
                case R.id.ok: {
                    save();
                }
                    break;
                }
            }
        };
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DIALOG_CHOICE: {
            return new AlertDialog.Builder(EditorActivity.this).setMessage(R.string.dialogArtworkCaption)
                .setPositiveButton(R.string.dialogOkCaption, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getArtworkFromBugs();
                    }
                }).setNegativeButton(R.string.dialogCancelCaption, null).create();
        }
        case DIALOG_CHOICE_INFORMATION: {
            return new AlertDialog.Builder(EditorActivity.this).setMessage(R.string.dialogInformationCaption)
                .setPositiveButton(R.string.dialogOkCaption, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getID3TagFromAlsong();
                    }
                }).setNegativeButton(R.string.dialogCancelCaption, null).create();
        }
        }

        return null;
    }

    private void setArtwork() {
        musicArtworkView = (MusicArtworkView) findViewById(R.id.artwork);
        musicArtworkView.setSongId(music.getId());
        musicArtworkView.onDraw();
        musicArtworkView.invalidate();
        musicArtworkView.setOnClickListener(onClickListener);
    }

    public void parseIntent(Intent intent, Music music) {
        Long id = intent.getLongExtra("id", 0);
        String track = intent.getStringExtra("track");
        String artist = intent.getStringExtra("artist");
        String path = intent.getStringExtra("path");
        String album = intent.getStringExtra("album");
        Long albumId = intent.getLongExtra("albumId", 0);

        music.setId(id);
        music.setTrack(track);
        music.setArtist(artist);
        music.setAlbum(album);
        music.setPath(path);
        music.setAlbumId(albumId);
    }

    public void setComponent() {
        trackEditText = (EditText) findViewById(R.id.title);
        artistEditText = (EditText) findViewById(R.id.artist);
        albumEditText = (EditText) findViewById(R.id.album);
        pathTextView = (TextView) findViewById(R.id.path);

        trackEditText.setOnClickListener(onClickListener);
        artistEditText.setOnClickListener(onClickListener);
        albumEditText.setOnClickListener(onClickListener);
        trackEditText.setInputType(0);
        artistEditText.setInputType(0);
        albumEditText.setInputType(0);

        okButton = (Button) findViewById(R.id.ok);
        okButton.setOnClickListener(onClickListener);

        alsongButton = (Button) findViewById(R.id.alsong);
        alsongButton.setOnClickListener(onClickListener);
    }

    public void setComponentInformation(Music music) {
        trackEditText.setText(music.getTrack());
        artistEditText.setText(music.getArtist());
        albumEditText.setText(music.getAlbum());
        pathTextView.setText(music.getPath());
    }

    public void getArtworkFromGom() {
        ActivityHelper.setStartProcess(this);
        processThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String imageUrl = music.getImageUrl();
                if (StringUtils.isEmpty(music.getImageUrl())) {
                    return;
                }
                
                Bitmap bitmap = MusicManager.getImageBitmap(imageUrl);
                Message message = Message.obtain(processHandler, MESSAGE_GET_IMAGE, bitmap);
                processHandler.sendMessage(message);
            }
        });
        processThread.start();
    }

    public void getArtworkFromBugs() {
        
        if(music.getImageUrl() != null ) {
            getArtworkFromGom();
            return;
        }
        
        ActivityHelper.setStartProcess(this);
        processThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String keyword = trackEditText.getText().toString() + " " + artistEditText.getText().toString();
                Bitmap bitmap = MusicManager.getBugsImage(keyword);

                Message message = Message.obtain(processHandler, MESSAGE_GET_IMAGE, bitmap);
                processHandler.sendMessage(message);
            }
        });
        processThread.start();
    }

    public void getID3TagFromAlsong() {
        ActivityHelper.setStartProcess(this);
        processThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String path = pathTextView.getText().toString();

                GomAudioClient gomAudioClient = new GomAudioClient();
                gomAudioClient.setPath(path);
                gomAudioClient.excute();

                Message message = Message.obtain(processHandler, MESSAGE_GET_INFORMATION_GOMPLAYER, gomAudioClient);
                processHandler.sendMessage(message);
            }
        });
        processThread.start();
    }

    public void save() {

        MusicManager.stopMusic(this);
        ActivityHelper.setStartProcess(this);
        processThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Music updatedMusic = new Music(music);
                updatedMusic.setTrack(trackEditText.getText().toString());
                updatedMusic.setArtist(artistEditText.getText().toString());
                updatedMusic.setAlbum(albumEditText.getText().toString());
                
                String messageString;
                
                try {
                    MusicManager.updateMusicMetadata(EditorActivity.this, updatedMusic);
                    messageString = getString(R.string.saveMessage);
                } catch (CannotWriteException e) {
                    messageString = getString(R.string.saveMessage1);
                } catch (CannotReadException e) {
                    messageString = getString(R.string.saveMessage2);
                } catch (TagException e) {
                    messageString = getString(R.string.saveMessage3);
                } catch (ReadOnlyFileException e) {
                    messageString = getString(R.string.saveMessage4);
                } catch (InvalidAudioFrameException e) {
                    messageString = getString(R.string.saveMessage5);
                } catch (IOException e) {
                    messageString = getString(R.string.saveMessage6);
                } 

                // Run MediaScanner
                MusicManager.performMediaScanner(EditorActivity.this);
                Message message = Message.obtain(processHandler, MESSAGE_SAVE, messageString);
                processHandler.sendMessage(message);
            }
        });
        processThread.start();
    }
}