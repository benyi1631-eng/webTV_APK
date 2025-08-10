package com.example.androidtvlivetv;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;

import com.example.androidtvlivetv.model.Channel;
import com.example.androidtvlivetv.model.ChannelCategory;
import com.example.androidtvlivetv.presenter.ChannelPresenter;

import java.util.ArrayList;
import java.util.List;

public class ChannelListActivity extends AppCompatActivity {
    
    private static final String TAG = "ChannelListActivity";
    
    private androidx.leanback.app.BrowseSupportFragment mBrowseFragment;
    private ArrayObjectAdapter mRowsAdapter;
    private List<ChannelCategory> mChannelCategories;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setupUIElements();
        loadChannelData();
        setupEventListeners();
    }
    
    private void setupUIElements() {
        mBrowseFragment = (androidx.leanback.app.BrowseSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.browse_fragment);
        
        if (mBrowseFragment != null) {
            mBrowseFragment.setHeadersState(androidx.leanback.app.BrowseSupportFragment.HEADERS_ENABLED);
            mBrowseFragment.setHeadersTransitionOnBackEnabled(true);
            
            // 设置页面标题
            mBrowseFragment.setTitle(getString(R.string.search_channels));
            
            // 设置快速操作
            mBrowseFragment.setBadgeDrawable(getDrawable(R.drawable.app_icon));
            
            mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
            mBrowseFragment.setAdapter(mRowsAdapter);
        }
    }
    
    private void loadChannelData() {
        mChannelCategories = createChannelCategories();
        
        for (ChannelCategory category : mChannelCategories) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new ChannelPresenter());
            for (Channel channel : category.getChannels()) {
                listRowAdapter.add(channel);
            }
            
            HeaderItem header = new HeaderItem(category.getName());
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }
    }
    
    private List<ChannelCategory> createChannelCategories() {
        List<ChannelCategory> categories = new ArrayList<>();
        
        // 新闻频道
        ChannelCategory newsCategory = new ChannelCategory("新闻频道", "实时新闻资讯");
        newsCategory.addChannel(new Channel("CCTV-1", "中央电视台综合频道", "http://example.com/cctv1.m3u8", "新闻"));
        newsCategory.addChannel(new Channel("CCTV-新闻", "中央电视台新闻频道", "http://example.com/cctv-news.m3u8", "新闻"));
        newsCategory.addChannel(new Channel("凤凰卫视", "凤凰卫视中文台", "http://example.com/phoenix.m3u8", "新闻"));
        categories.add(newsCategory);
        
        // 娱乐频道
        ChannelCategory entertainmentCategory = new ChannelCategory("娱乐频道", "综艺娱乐节目");
        entertainmentCategory.addChannel(new Channel("CCTV-3", "中央电视台综艺频道", "http://example.com/cctv3.m3u8", "娱乐"));
        entertainmentCategory.addChannel(new Channel("湖南卫视", "湖南卫视", "http://example.com/hunan.m3u8", "娱乐"));
        entertainmentCategory.addChannel(new Channel("浙江卫视", "浙江卫视", "http://example.com/zhejiang.m3u8", "娱乐"));
        categories.add(entertainmentCategory);
        
        // 体育频道
        ChannelCategory sportsCategory = new ChannelCategory("体育频道", "体育赛事直播");
        sportsCategory.addChannel(new Channel("CCTV-5", "中央电视台体育频道", "http://example.com/cctv5.m3u8", "体育"));
        sportsCategory.addChannel(new Channel("CCTV-5+", "中央电视台体育赛事频道", "http://example.com/cctv5plus.m3u8", "体育"));
        categories.add(sportsCategory);
        
        return categories;
    }
    
    private void setupEventListeners() {
        if (mBrowseFragment != null) {
            mBrowseFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
                @Override
                public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                        RowPresenter.ViewHolder rowViewHolder, Row row) {
                    if (item instanceof Channel) {
                        Channel channel = (Channel) item;
                        if (channel.isAvailable()) {
                            Intent intent = new Intent(ChannelListActivity.this, PlayerActivity.class);
                            intent.putExtra("channel_name", channel.getName());
                            intent.putExtra("channel_url", channel.getUrl());
                            startActivity(intent);
                        } else {
                            Toast.makeText(ChannelListActivity.this, 
                                getString(R.string.channel_unavailable), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            
            mBrowseFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
                @Override
                public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                         RowPresenter.ViewHolder rowViewHolder, Row row) {
                    // 可以在这里添加选中项的处理逻辑
                }
            });
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                // 处理确认键
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清理资源
    }
}
