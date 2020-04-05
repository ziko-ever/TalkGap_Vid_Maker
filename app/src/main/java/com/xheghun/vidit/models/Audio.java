package com.xheghun.vidit.models;

import java.io.Serializable;
import java.util.Random;

public class Audio implements Serializable {
    private String artist;
    private String title;
    private String displayname;
    private String path;
    private String duration;
    private String year;
    private String thumbnail;
    private byte[] raw;
    private boolean expanded;
    private int length = 1024 * 200;
    private int start;
    private int end;
    private long audioDuration;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public byte[] getRaw() {
        final Random rnd = new Random();
        raw = new byte[length];
        rnd.nextBytes(raw);
        return raw;
    }

    public void setRaw(byte[] raw) {
        this.raw = raw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public long getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(long audioDuration) {
        this.audioDuration = audioDuration;
    }
}
