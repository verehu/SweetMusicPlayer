package com.huwei.sweetmusicplayer.ui.itemviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.huwei.sweetmusicplayer.models.ArtistInfo;

import org.androidannotations.annotations.EViewGroup;

/**
 * @author jerry
 * @date 2016/01/15
 */
@EViewGroup
public class ArtistItemView extends LinearLayout implements  IRecycleViewItem<ArtistInfo>{
    public ArtistItemView(Context context) {
        super(context);
    }

    public ArtistItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public void bind(ArtistInfo albumInfo) {

    }
}
