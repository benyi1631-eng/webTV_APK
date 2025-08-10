package com.example.androidtvlivetv.model;

import java.util.Objects;

public class VideoSource {
    private String id;
    private String name;
    private String url;
    private String type;
    private String format;
    private String quality;
    private String provider;
    private boolean isAvailable;
    private long lastChecked;
    private int priority;

    public VideoSource(String name, String url, String type, String format) {
        this.id = generateId(name, url);
        this.name = name;
        this.url = url;
        this.type = type;
        this.format = format;
        this.quality = "auto";
        this.provider = "unknown";
        this.isAvailable = true;
        this.lastChecked = System.currentTimeMillis();
        this.priority = 0;
    }

    public VideoSource(String name, String url, String type, String format, String quality, String provider) {
        this.id = generateId(name, url);
        this.name = name;
        this.url = url;
        this.type = type;
        this.format = format;
        this.quality = quality;
        this.provider = provider;
        this.isAvailable = true;
        this.lastChecked = System.currentTimeMillis();
        this.priority = 0;
    }

    private String generateId(String name, String url) {
        String combined = name + "_" + url;
        return combined.replaceAll("[^a-zA-Z0-9_]", "_").toLowerCase();
    }

    public void updateAvailability(boolean available) {
        this.isAvailable = available;
        this.lastChecked = System.currentTimeMillis();
    }

    public boolean isExpired(long expirationTime) {
        return (System.currentTimeMillis() - lastChecked) > expirationTime;
    }

    public boolean isHLS() {
        return "m3u8".equalsIgnoreCase(format) || url.toLowerCase().contains(".m3u8");
    }

    public boolean isMP4() {
        return "mp4".equalsIgnoreCase(format) || url.toLowerCase().contains(".mp4");
    }

    public boolean isFLV() {
        return "flv".equalsIgnoreCase(format) || url.toLowerCase().contains(".flv");
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public long getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(long lastChecked) {
        this.lastChecked = lastChecked;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoSource that = (VideoSource) o;
        return Objects.equals(id, that.id) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }

    @Override
    public String toString() {
        return "VideoSource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", format='" + format + '\'' +
                ", quality='" + quality + '\'' +
                ", provider='" + provider + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
