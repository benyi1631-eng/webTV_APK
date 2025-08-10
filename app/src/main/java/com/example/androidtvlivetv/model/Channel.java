package com.example.androidtvlivetv.model;

import java.util.Objects;

public class Channel {
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String logo;
    private boolean isFavorite;
    private boolean isAvailable;

    public Channel(String name, String description, String url, String category) {
        this.id = generateId(name);
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.logo = "";
        this.isFavorite = false;
        this.isAvailable = true;
    }

    public Channel(String id, String name, String description, String url, String category, String logo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.logo = logo;
        this.isFavorite = false;
        this.isAvailable = true;
    }

    private String generateId(String name) {
        return name.replaceAll("\\s+", "_").toLowerCase();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return Objects.equals(id, channel.id) && Objects.equals(name, channel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
