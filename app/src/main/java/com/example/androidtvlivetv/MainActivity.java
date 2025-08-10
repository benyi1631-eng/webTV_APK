package com.example.androidtvlivetv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.BrowseSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.example.androidtvlivetv.model.Channel;
import com.example.androidtvlivetv.model.ChannelCategory;
import com.example.androidtvlivetv.service.VideoSourceSearchService;
import com.example.androidtvlivetv.presenter.ChannelPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    private BrowseSupportFragment mBrowseSupportFragment;
    private ArrayObjectAdapter mRowsAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    
    // 模拟频道数据
    private List<ChannelCategory> mChannelCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setupUIElements();
        loadChannelData();
        setupEventListeners();
        
        // 启动视频源搜索服务
        startVideoSourceSearch();
    }

    private void setupUIElements() {
        mBrowseSupportFragment = (BrowseSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.browse_fragment);
        
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        mBrowseSupportFragment.setAdapter(mRowsAdapter);
        
        mBrowseSupportFragment.setHeadersState(BrowseSupportFragment.HEADERS_ENABLED);
        mBrowseSupportFragment.setHeadersTransitionOnBackEnabled(true);
        mBrowseSupportFragment.setTitle(getString(R.string.app_name));
        mBrowseSupportFragment.setBadgeDrawable(getDrawable(R.drawable.app_icon));
    }

    private void loadChannelData() {
        mChannelCategories = new ArrayList<>();
        
        // 创建频道分类
        createChannelCategories();
        
        // 加载频道数据到UI
        loadChannelsToUI();
    }

    private void createChannelCategories() {
        // 新闻频道
        ChannelCategory newsCategory = new ChannelCategory("新闻频道", "news");
        newsCategory.addChannel(new Channel("CCTV-1", "CCTV-1综合", "http://example.com/cctv1.m3u8", "news"));
        newsCategory.addChannel(new Channel("CCTV-13", "CCTV-13新闻", "http://example.com/cctv13.m3u8", "news"));
        newsCategory.addChannel(new Channel("凤凰卫视", "凤凰卫视中文台", "http://example.com/phoenix.m3u8", "news"));
        mChannelCategories.add(newsCategory);

        // 娱乐频道
        ChannelCategory entertainmentCategory = new ChannelCategory("娱乐频道", "entertainment");
        entertainmentCategory.addChannel(new Channel("CCTV-3", "CCTV-3综艺", "http://example.com/cctv3.m3u8", "entertainment"));
        entertainmentCategory.addChannel(new Channel("CCTV-6", "CCTV-6电影", "http://example.com/cctv6.m3u8", "entertainment"));
        entertainmentCategory.addChannel(new Channel("湖南卫视", "湖南卫视", "http://example.com/hunan.m3u8", "entertainment"));
        mChannelCategories.add(entertainmentCategory);

        // 体育频道
        ChannelCategory sportsCategory = new ChannelCategory("体育频道", "sports");
        sportsCategory.addChannel(new Channel("CCTV-5", "CCTV-5体育", "http://example.com/cctv5.m3u8", "sports"));
        sportsCategory.addChannel(new Channel("CCTV-5+", "CCTV-5+体育赛事", "http://example.com/cctv5plus.m3u8", "sports"));
        mChannelCategories.add(sportsCategory);
    }

    private void loadChannelsToUI() {
        mRowsAdapter.clear();
        
        for (ChannelCategory category : mChannelCategories) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new ChannelPresenter());
            listRowAdapter.addAll(0, category.getChannels());
            
            HeaderItem header = new HeaderItem(category.getName());
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }
    }

    private void setupEventListeners() {
        mBrowseSupportFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                    RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof Channel) {
                    Channel channel = (Channel) item;
                    Log.d(TAG, "Channel clicked: " + channel.getName());
                    
                    // 启动播放器Activity
                    Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                    intent.putExtra("channel_name", channel.getName());
                    intent.putExtra("channel_url", channel.getUrl());
                    startActivity(intent);
                }
            }
        });

        mBrowseSupportFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                    RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof Channel) {
                    Channel channel = (Channel) item;
                    Log.d(TAG, "Channel selected: " + channel.getName());
                }
            }
        });
    }

    private void startVideoSourceSearch() {
        // 启动后台服务搜索可用视频源
        Intent serviceIntent = new Intent(this, VideoSourceSearchService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止视频源搜索服务
        Intent serviceIntent = new Intent(this, VideoSourceSearchService.class);
        stopService(serviceIntent);
    }
}
