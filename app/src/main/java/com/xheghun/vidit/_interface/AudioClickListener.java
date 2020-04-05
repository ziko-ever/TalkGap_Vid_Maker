package com.xheghun.vidit._interface;


import com.xheghun.vidit.models.Audio;

public interface AudioClickListener {
    void onClicked();
    void onPlay(String filename, int start, int end);
    void onStop();
    void onAudioSelected(Audio audio);
}
