package com.huwei.sweetmusicplayer.business.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jayce
 * @date 2015/6/14
 */
public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>>{

    protected List<T> items = new ArrayList<T>();
    private RecyclerViewAdapterBase.OnItemClickListener onItemClickListener;
    private RecyclerViewAdapterBase.OnItemLongClickListener onItemLongClickListener;

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent, viewType),onItemClickListener,onItemLongClickListener);
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    // additional methods to manipulate the items
    public void setData(List list){
        items = list;
    }

    public List<T> getData() {
        return items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
}