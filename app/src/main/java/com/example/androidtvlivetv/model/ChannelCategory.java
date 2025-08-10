package com.example.androidtvlivetv.model;

import java.util.ArrayList;
import java.util.List;

public class ChannelCategory {
    private String id;
    private String name;
    private String description;
    private List<Channel> channels;

    public ChannelCategory(String name, String description) {
        this.id = generateId(name);
        this.name = name;
        this.description = description;
        this.channels = new ArrayList<>();
    }

    public ChannelCategory(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.channels = new ArrayList<>();
    }

    private String generateId(String name) {
        return name.replaceAll("\\s+", "_").toLowerCase();
    }

    public void addChannel(Channel channel) {
        if (channel != null && !channels.contains(channel)) {
            channels.add(channel);
        }
    }

    public void removeChannel(Channel channel) {
        if (channel != null) {
            channels.remove(channel);
        }
    }

    public void removeChannelById(String channelId) {
        channels.removeIf(channel -> channel.getId().equals(channelId));
    }

    public Channel getChannelById(String channelId) {
        for (Channel channel : channels) {
            if (channel.getId().equals(channelId)) {
                return channel;
            }
        }
        return null;
    }

    public List<Channel> getChannels() {
        return new ArrayList<>(channels);
    }

    public void setChannels(List<Channel> channels) {
        this.channels = new ArrayList<>(channels);
    }

    public int getChannelCount() {
        return channels.size();
    }

    public boolean hasChannels() {
        return !channels.isEmpty();
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

    @Override
    public String toString() {
        return "ChannelCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", channelCount=" + getChannelCount() +
                '}';
    }
}
