package com.huwei.sweetmusicplayer.business.ui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.business.baidumusic.po.ArtistInfo;
import com.huwei.sweetmusicplayer.frameworks.image.BlurBitmapTransformation;
import com.huwei.sweetmusicplayer.frameworks.image.GlideApp;
import com.huwei.sweetmusicplayer.helper.BlurHelper;
import com.huwei.sweetmusicplayer.business.ui.widgets.GradientToolbar;
import com.huwei.sweetmusicplayer.util.DisplayUtil;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author jerry
 * @date 2015-12-22
 */
@EViewGroup(R.layout.listheader_artistinfo)
public class ArtistInfoView extends FrameLayout {

    @ViewById
    TextView tv_artist, tv_country;
    @ViewById
    ImageView iv_bg;
    @Bean
    BlurHelper mBlurHelper;

    Context mContext;

    public static final int HEIGHT_DP = 196;

    public ArtistInfoView(Context context) {
        this(context, null);
    }

    public ArtistInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArtistInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mContext, HEIGHT_DP));
        setLayoutParams(layoutParams);
    }

    public void bind(@NonNull ArtistInfo artistInfo, final GradientToolbar gtoolbar) {
        if (artistInfo != null) {
            tv_artist.setText(artistInfo.name);
            tv_country.setText(artistInfo.country + "歌手");
            GlideApp.with(mContext).asBitmap().load(artistInfo.avatar_s500).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    iv_bg.setImageBitmap(resource);
                    return false;
                }
            }).transform(new BlurBitmapTransformation(100)).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    gtoolbar.setToolbarBg(resource);
                }
            });

            gtoolbar.setGradientTitle(artistInfo.name);
        }
    }
}
