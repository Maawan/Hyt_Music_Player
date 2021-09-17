package com.hayatsoftwares.mybee.music.player.Models;

public class Song {
    private String id;
    private String title;
    private String displayName;
    private String artist;
    private String album;
    private String duration;
    private String path;
    private String albumID;

    public Song(String id, String title, String displayName, String artist, String album, String duration, String path, String albumID) {
        this.id = id;
        this.title = title;
        this.displayName = displayName;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.path = path;
        this.albumID = albumID;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getDuration() {
        return duration;
    }

    public String getPath() {
        return path;
    }

    public String getAlbumID() {
        return albumID;
    }
}
