package com.huwei.sweetmusicplayer.business.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.huwei.sweetmusicplayer.business.models.AlbumInfo;
import com.huwei.sweetmusicplayer.business.ui.itemviews.AlbumItemView;
import com.huwei.sweetmusicplayer.business.ui.itemviews.AlbumItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * @author Jayce
 * @date 2015/6/14
 */
@EBean
public class LocAlbumAdapter extends RecyclerViewAdapterBase<AlbumInfo,AlbumItemView>{

    @RootContext
    Context context;

    @Override
    protected AlbumItemView onCreateItemView(ViewGroup parent, int viewType) {
        return AlbumItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<AlbumItemView> holder, int position) {
        AlbumItemView view= (AlbumItemView) holder.getView();
        AlbumInfo albumInfo=items.get(position);

        view.bind(albumInfo);
    }


}
