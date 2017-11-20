package com.huwei.sweetmusicplayer.business.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.huwei.sweetmusicplayer.business.models.ArtistInfo;
import com.huwei.sweetmusicplayer.business.ui.itemviews.ArtistItemView;
import com.huwei.sweetmusicplayer.business.ui.itemviews.ArtistItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * @author jerry
 * @date 2016/01/15
 */
@EBean
public class LocArtistInfoAdapter extends RecyclerViewAdapterBase<ArtistInfo, ArtistItemView> {
    @RootContext
    Context mContext;

    @Override
    protected ArtistItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ArtistItemView_.build(mContext);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ArtistItemView> holder, int position) {
        ArtistItemView artistItemView = (ArtistItemView) holder.getView();
        ArtistInfo artistInfo = items.get(position);

        artistItemView.bind(artistInfo);
    }
}
