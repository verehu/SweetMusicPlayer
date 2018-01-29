package com.huwei.sweetmusicplayer.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.huwei.sweetmusicplayer.data.models.AlbumInfo;
import com.huwei.sweetmusicplayer.ui.itemviews.AlbumItemView;

/**
 * @author Jayce
 * @date 2015/6/14
 */
public class LocAlbumAdapter extends RecyclerViewAdapterBase<AlbumInfo,AlbumItemView>{

    public LocAlbumAdapter(Context context) {
        super(context);
    }

    @Override
    protected AlbumItemView onCreateItemView(ViewGroup parent, int viewType) {
        return new AlbumItemView(mContext);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<AlbumItemView> holder, int position) {
        AlbumItemView view= (AlbumItemView) holder.getView();
        AlbumInfo albumInfo=items.get(position);

        view.bind(albumInfo);
    }


}
