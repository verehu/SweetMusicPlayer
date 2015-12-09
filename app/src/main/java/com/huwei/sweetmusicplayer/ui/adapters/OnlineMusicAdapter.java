package com.huwei.sweetmusicplayer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;

import java.util.List;

/**
 * 在线音乐的适配器
 *
 * @author jerry
 * @date 2015-11-20
 */
public class OnlineMusicAdapter extends BaseAdapter {

    private Context mContext;
    private List<Song> songList;

    public OnlineMusicAdapter(Context context, List<Song> songList) {
        this.mContext = context;
        this.songList = songList;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_online_music, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_song = (TextView) convertView.findViewById(R.id.tv_song);
            viewHolder.tv_artist = (TextView) convertView.findViewById(R.id.tv_artist);
            convertView.setTag(viewHolder);
        }

        final Song song = (Song) getItem(position);

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tv_song.setText(song.title);
        viewHolder.tv_artist.setText(song.author);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicManager.getInstance().preparePlayingList(position, Song.getAbstractMusicList(songList));
                MusicManager.getInstance().play();
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tv_song;
        TextView tv_artist;
    }
}
