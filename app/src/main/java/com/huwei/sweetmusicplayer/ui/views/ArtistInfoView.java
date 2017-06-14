package com.huwei.sweetmusicplayer.ui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.SweetApplication;
import com.huwei.sweetmusicplayer.baidumusic.po.ArtistInfo;
import com.huwei.sweetmusicplayer.helper.BlurHelper;
import com.huwei.sweetmusicplayer.ui.widgets.GradientToolbar;
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
//            mImageLoader.loadImage(artistInfo.avatar_s500, new ImageLoadingListener() {
//                @Override
//                public void onLoadingStarted(String imageUri, View view) {
////                            genBlurBitmap(BitmapUtil.drawable2bitamp(iv_album.getDrawable()));
//                }
//
//                @Override
//                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//                }
//
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                    iv_bg.setImageBitmap(loadedImage);
//                    mBlurHelper.blurBitmap(loadedImage, 100, new BlurHelper.OnGenerateBitmapCallback() {
//                        @Override
//                        public void onGenerateBitmap(Bitmap bitmap) {
//                            gtoolbar.setToolbarBg(bitmap);
//                        }
//                    });
//                }
//
//                @Override
//                public void onLoadingCancelled(String imageUri, View view) {
//
//                }
//            });
            gtoolbar.setGradientTitle(artistInfo.name);
        }
    }
}
