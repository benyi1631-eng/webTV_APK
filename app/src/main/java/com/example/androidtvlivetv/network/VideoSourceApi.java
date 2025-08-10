package com.example.androidtvlivetv.network;

import com.example.androidtvlivetv.model.VideoSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface VideoSourceApi {
    
    /**
     * 搜索视频源
     */
    @GET
    Call<List<VideoSource>> searchSources(@Url String url);
    
    /**
     * 获取频道列表
     */
    @GET("channels")
    Call<List<VideoSource>> getChannels();
    
    /**
     * 获取指定分类的频道
     */
    @GET("channels")
    Call<List<VideoSource>> getChannelsByCategory(@Query("category") String category);
    
    /**
     * 搜索频道
     */
    @GET("search")
    Call<List<VideoSource>> searchChannels(@Query("q") String query);
    
    /**
     * 获取热门频道
     */
    @GET("popular")
    Call<List<VideoSource>> getPopularChannels();
    
    /**
     * 获取推荐频道
     */
    @GET("recommended")
    Call<List<VideoSource>> getRecommendedChannels();
    
    /**
     * 验证视频源可用性
     */
    @GET("validate")
    Call<Boolean> validateSource(@Query("url") String url);
}
