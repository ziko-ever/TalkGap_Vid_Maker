package com.xheghun.vidit.classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;

public class FFmpegSingleton {
    private static final String TAG = "FFMPEG" ;
    private static boolean isCommandSucessful = false;
    private FFmpegSingleton() {}



    private static   FFmpeg ffmpeg;
    public static boolean executeCmD(Context context, String[] cmd, Intent intent) {
            Log.d(TAG, "ffmpeg: null");
            if (ffmpeg == null) {
                ffmpeg = FFmpeg.getInstance(context);
            }
            try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    Toast.makeText(context, "failed to load binaries", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG, "ffmpeg : correct Loaded");
                    Toast.makeText(context, "success loading binaries", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (FFmpegNotSupportedException e) {
            Toast.makeText(context, "Sorry Your devices doesn't fully support this app", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d(TAG, "EXception not supported : " + e);
        }

        try {

            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setTitle("Executing");
            dialog.setMessage("Please wait...");

            File file = new File(context.getFilesDir(),"video.mp4");
            file.delete();

            ffmpeg.execute(cmd,new ExecuteBinaryResponseHandler(){
                @Override
                public void onSuccess(String message) {
                    dialog.dismiss();
                    super.onSuccess(message);
                    Log.v("FFMPEG",message);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }

                @Override
                public void onProgress(String message) {
                    super.onProgress(message);
                }

                @Override
                public void onFailure(String message) {
                    dialog.dismiss();
                    super.onFailure(message);
                    //Toast.makeText(context, "status ->"+message, Toast.LENGTH_LONG).show();
                    file.delete();
                    Log.d("FFMPEG MESSAGE",message);
                }

                @Override
                public void onStart() {
                    dialog.show();
                    super.onStart();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
        }

        return isCommandSucessful;
    }

}
