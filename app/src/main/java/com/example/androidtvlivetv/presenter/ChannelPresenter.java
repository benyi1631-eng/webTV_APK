package com.example.androidtvlivetv.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.example.androidtvlivetv.R;
import com.example.androidtvlivetv.model.Channel;

public class ChannelPresenter extends Presenter {

    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 150;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ImageCardView cardView = new ImageCardView(parent.getContext());
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if (item instanceof Channel) {
            Channel channel = (Channel) item;
            
            ImageCardView cardView = (ImageCardView) viewHolder.view;
            cardView.setTitleText(channel.getName());
            cardView.setContentText(channel.getDescription());
            
            // 设置频道图标
            if (channel.getLogo() != null && !channel.getLogo().isEmpty()) {
                // 这里应该使用图片加载库加载频道logo
                // 暂时使用默认图标
                cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
                cardView.setMainImage(cardView.getResources().getDrawable(R.drawable.channel_icon));
            } else {
                // 使用默认图标
                cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
                cardView.setMainImage(cardView.getResources().getDrawable(R.drawable.channel_icon));
            }
            
            // 设置可用状态
            if (!channel.isAvailable()) {
                cardView.setAlpha(0.5f);
            } else {
                cardView.setAlpha(1.0f);
            }
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }

    public static class ViewHolder extends Presenter.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
