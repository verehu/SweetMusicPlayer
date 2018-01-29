package com.huwei.sweetmusicplayer.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Jayce
 * @date 2015/6/14
 */
public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    private RecyclerViewAdapterBase.OnItemClickListener onItemClickListener;
    private RecyclerViewAdapterBase.OnItemLongClickListener onItemLongClickListener;

    public ViewWrapper(V itemView, RecyclerViewAdapterBase.OnItemClickListener onItemClickListener, RecyclerViewAdapterBase.OnItemLongClickListener onItemLongClickListener) {
        super(itemView);
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public View getView() {
        return itemView;
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null){
            onItemClickListener.onItemClick(itemView,getPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(onItemLongClickListener!=null){
            onItemLongClickListener.onItemLongClick(itemView,getPosition());
        }
        return true;
    }

}
