package com.example.androidtvlivetv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class PlayerActivity extends AppCompatActivity {
    private static final String TAG = "PlayerActivity";
    
    private PlayerView mPlayerView;
    private ExoPlayer mExoPlayer;
    private TextView mChannelNameText;
    private View mControlsContainer;
    
    private String mChannelName;
    private String mChannelUrl;
    private boolean mControlsVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        
        // 获取传递的频道信息
        Intent intent = getIntent();
        mChannelName = intent.getStringExtra("channel_name");
        mChannelUrl = intent.getStringExtra("channel_url");
        
        if (mChannelName == null || mChannelUrl == null) {
            Log.e(TAG, "Missing channel information");
            finish();
            return;
        }
        
        initViews();
        setupPlayer();
        loadChannel();
    }

    private void initViews() {
        mPlayerView = findViewById(R.id.player_view);
        mChannelNameText = findViewById(R.id.channel_name_text);
        mControlsContainer = findViewById(R.id.controls_container);
        
        mChannelNameText.setText(mChannelName);
        
        // 设置全屏模式
        mPlayerView.setUseController(false);
        mPlayerView.setKeepContentOnPlayerReset(true);
    }

    private void setupPlayer() {
        mExoPlayer = new ExoPlayer.Builder(this).build();
        mPlayerView.setPlayer(mExoPlayer);
        
        // 设置播放器监听器
        mExoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                switch (playbackState) {
                    case Player.STATE_READY:
                        Log.d(TAG, "Player ready");
                        hideControls();
                        break;
                    case Player.STATE_BUFFERING:
                        Log.d(TAG, "Player buffering");
                        break;
                    case Player.STATE_ENDED:
                        Log.d(TAG, "Player ended");
                        break;
                    case Player.STATE_IDLE:
                        Log.d(TAG, "Player idle");
                        break;
                }
            }
            
            @Override
            public void onPlayerError(com.google.android.exoplayer2.PlaybackException error) {
                Log.e(TAG, "Player error: " + error.getMessage());
                // 尝试重新加载或切换到备用源
                retryPlayback();
            }
        });
    }

    private void loadChannel() {
        try {
            Log.d(TAG, "Loading channel: " + mChannelName + " URL: " + mChannelUrl);
            
            // 创建媒体源
            MediaSource mediaSource = createMediaSource(mChannelUrl);
            
            // 设置媒体源并准备播放
            mExoPlayer.setMediaSource(mediaSource);
            mExoPlayer.prepare();
            mExoPlayer.setPlayWhenReady(true);
            
        } catch (Exception e) {
            Log.e(TAG, "Error loading channel: " + e.getMessage());
            // 显示错误信息
            showError("无法加载频道: " + e.getMessage());
        }
    }

    private MediaSource createMediaSource(String url) {
        Uri uri = Uri.parse(url);
        
        if (url.contains(".m3u8")) {
            // HLS流
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory();
            return new HlsMediaSource.Factory(httpDataSourceFactory).createMediaSource(MediaItem.fromUri(uri));
        } else {
            // 其他格式
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this);
            return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri));
        }
    }

    private void retryPlayback() {
        Log.d(TAG, "Retrying playback...");
        // 延迟重试
        mPlayerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadChannel();
            }
        }, 3000);
    }

    private void showError(String message) {
        // 显示错误信息
        mChannelNameText.setText("错误: " + message);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                togglePlayPause();
                return true;
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
            case KeyEvent.KEYCODE_MENU:
                toggleControls();
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                toggleControls();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void togglePlayPause() {
        if (mExoPlayer != null) {
            if (mExoPlayer.isPlaying()) {
                mExoPlayer.pause();
            } else {
                mExoPlayer.play();
            }
        }
    }

    private void toggleControls() {
        if (mControlsVisible) {
            hideControls();
        } else {
            showControls();
        }
    }

    private void showControls() {
        mControlsVisible = true;
        mControlsContainer.setVisibility(View.VISIBLE);
        mChannelNameText.setVisibility(View.VISIBLE);
        
        // 3秒后自动隐藏
        mControlsContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mControlsVisible) {
                    hideControls();
                }
            }
        }, 3000);
    }

    private void hideControls() {
        mControlsVisible = false;
        mControlsContainer.setVisibility(View.GONE);
        mChannelNameText.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mExoPlayer != null) {
            mExoPlayer.play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
