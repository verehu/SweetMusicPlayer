package com.huwei.sweetmusicplayer.ui.adapters;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.res.ColorRes;

import java.util.List;

/**
 * 播放队列适配器
 * @author Jayce
 * @date 2015/6/17
 */
@EBean
public class QueueAdapter extends BaseAdapter{

    private List<AbstractMusic> list;
    @RootContext
    Context context;
    @SystemService
    LayoutInflater inflater;
    @ColorRes
    int primary;
    @ColorRes
    int white;

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
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.listitem_music_queue,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.tv_title.setText(list.get(position).getTitle());

        if(MusicManager.getInstance().getNowPlayingIndex()==position){
            viewHolder.tv_title.setTextColor(primary);
        }else{
            viewHolder.tv_title.setTextColor(white);
        }

        return convertView;
    }

    class ViewHolder{
        TextView tv_title;
    }
}
