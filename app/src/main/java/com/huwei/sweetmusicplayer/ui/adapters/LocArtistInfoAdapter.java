package com.huwei.sweetmusicplayer.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.huwei.sweetmusicplayer.data.models.ArtistInfo;
import com.huwei.sweetmusicplayer.ui.itemviews.ArtistItemView;

/**
 * @author jerry
 * @date 2016/01/15
 */
public class LocArtistInfoAdapter extends RecyclerViewAdapterBase<ArtistInfo, ArtistItemView> {

    public LocArtistInfoAdapter(Context context) {
        super(context);
    }

    @Override
    protected ArtistItemView onCreateItemView(ViewGroup parent, int viewType) {
        return new ArtistItemView(mContext);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ArtistItemView> holder, int position) {
        ArtistItemView artistItemView = (ArtistItemView) holder.getView();
        ArtistInfo artistInfo = items.get(position);

        artistItemView.bind(artistInfo);
    }
}
