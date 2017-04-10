package com.huwei.sweetmusicplayer.ui.itemviews;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.models.AlbumInfo;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author Jayce
 * @date 2015/6/14
 */
@EViewGroup(R.layout.card_ablum)
public class AlbumItemView extends RelativeLayout implements IRecycleViewItem<AlbumInfo>{
    @ViewById
    ImageView img_album;
    @ViewById
    TextView tv_title,tv_numsongs;

    public AlbumItemView(Context context) {
        super(context);
    }

    public AlbumItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlbumItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void bind(AlbumInfo albumInfo) {
        img_album.setImageURI(Uri.parse("file://"+albumInfo.getAlbumArt()));
        tv_title.setText(albumInfo.getTitle());
        tv_numsongs.setText(albumInfo.getNumSongs()+"首");
    }
}
