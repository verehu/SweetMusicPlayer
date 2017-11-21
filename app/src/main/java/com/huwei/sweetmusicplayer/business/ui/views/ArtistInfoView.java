package com.huwei.sweetmusicplayer.business.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
            GlideApp.with(mContext).load(artistInfo.avatar_s500).transform(new BlurBitmapTransformation(100)).into(iv_bg);
            gtoolbar.setGradientTitle(artistInfo.name);
        }
    }
}
