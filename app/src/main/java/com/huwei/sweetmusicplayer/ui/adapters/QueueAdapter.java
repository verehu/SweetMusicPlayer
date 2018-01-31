package com.huwei.sweetmusicplayer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.data.models.AbstractMusic;
import com.huwei.sweetmusicplayer.business.core.MusicManager;

import java.util.List;

/**
 * 播放队列适配器
 *
 * @author Jayce
 * @date 2015/6/17
 */
public class QueueAdapter extends BaseAdapter {

    private List<AbstractMusic> list;
    Context mContext;

    int primary;
    int white;

    public QueueAdapter(Context mContext) {
        this.mContext = mContext;

        primary = mContext.getResources().getColor(R.color.primary);
        white = mContext.getResources().getColor(R.color.white);
    }

    public List<AbstractMusic> getList() {
        return list;
    }

    public void setList(List<AbstractMusic> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_music_queue, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tv_title.setText(list.get(position).getTitle());

        if (MusicManager.get().getNowPlayingIndex() == position) {
            viewHolder.tv_title.setTextColor(primary);
        } else {
            viewHolder.tv_title.setTextColor(white);
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
    }
}
