package com.xheghun.vidit.classes;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import com.xheghun.vidit.models.Audio;

import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;

public class AudioManager {

    private MediaPlayer mp;
    private static AudioManager audioManager;
    private List<Audio> albums;
    private Cursor cursor;
    private Context ctx;
    private Audio album;

    public AudioManager(Context ctx)
    {
        this.ctx = ctx;
    }

    public static AudioManager getInstance(Context ctx)
    {

        if(audioManager == null)
        {
            audioManager = new AudioManager(ctx);
        }

        return audioManager;
    }

    public List<Audio> listAudio()
    {

        albums = new ArrayList<>();

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.YEAR
        };
        cursor =  ctx.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, selection, null, null);


        while(cursor.moveToNext())
        {
            album = new Audio();

            album.setArtist(cursor.getString(1));
            album.setDisplayname(cursor.getString(4));
            album.setDuration(cursor.getString(5));
            album.setPath(cursor.getString(3));
            album.setTitle(cursor.getString(2));
            album.setYear(cursor.getString(6));

            albums.add(album);
        }

        return albums;
    }

    public byte[] convert(String path) throws IOException {

        FileInputStream fis = new FileInputStream(path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];

        for (int readNum; (readNum = fis.read(b)) != -1;) {
            bos.write(b, 0, readNum);
        }

        byte[] bytes = bos.toByteArray();

        return bytes;
    }

    public void play(String filename, int start, int end)
    {
        //set up MediaPlayer
        mp = new MediaPlayer();

        try {
            mp.setDataSource(filename);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            Log.d("PLAY", e.getLocalizedMessage());
        }
    }

    public void stop()
    {
        //set up MediaPlayer
        if(mp != null)
        {
            try {

                mp.stop();
                mp.release();
            } catch (Exception e) {
                Log.d("PLAY", e.getLocalizedMessage());
            }
        }


    }
}
