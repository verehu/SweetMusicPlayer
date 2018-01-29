package com.huwei.sweetmusicplayer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.business.comparator.MusicInfoComparator;
import com.huwei.sweetmusicplayer.business.core.MusicManager;
import com.huwei.sweetmusicplayer.data.models.MusicInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicAdapter extends BaseAdapter implements SectionIndexer {

    private Context mContext;
    private List<MusicInfo> list = new ArrayList<MusicInfo>();
    private OnItemClickListener listener;
    private boolean isABC;
    
    public MusicAdapter(Context mContext, boolean isABC) {
        this.mContext = mContext;
        this.isABC=isABC;
        //对歌曲进行排序
        if(isABC)    Collections.sort(list,new MusicInfoComparator());
    }

    public void setList(List<MusicInfo> list) {
        this.list = list;
    }

    public List getList() {
        return list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position,View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder mViewHolder = null;

        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_song, null);
            mViewHolder.song_text = (TextView) convertView.findViewById(R.id.song_title);
            mViewHolder.letter_text = (TextView) convertView.findViewById(R.id.catalog);
            mViewHolder.artist_tv = (TextView) convertView.findViewById(R.id.artist_tv);
            mViewHolder.duration_tv = (TextView) convertView.findViewById(R.id.duration_tv);
            mViewHolder.selected_view = convertView.findViewById(R.id.selected_view);
            mViewHolder.song_item = (LinearLayout) convertView.findViewById(R.id.song_item);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        //Song song = (Song) getItem(position);
        MusicInfo musicInfo= (MusicInfo) getItem(position);

        String title=isABC?"":(position+1)+".";

        mViewHolder.song_text.setText(title+musicInfo.getTitle());
        mViewHolder.artist_tv.setText(musicInfo.getArtist());
        mViewHolder.duration_tv.setText(musicInfo.getDurationStr());

        if(isABC) {
            int section = getSectionForPosition(position);

            if (position == getPositionForSection(section)) {
                mViewHolder.letter_text.setVisibility(View.VISIBLE);
                mViewHolder.letter_text.setText(musicInfo.getKeyofTitle());
            } else {
                mViewHolder.letter_text.setVisibility(View.GONE);
            }
        }else{
            mViewHolder.letter_text.setVisibility(View.GONE);
        }
        
        mViewHolder.song_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null){
                    listener.onItemClick(position);
                    notifyDataSetChanged();
                }
            }
        });

		if(MusicManager.isIndexNowPlaying(list, position)){
			mViewHolder.selected_view.setVisibility(View.VISIBLE);
		}else{
			mViewHolder.selected_view.setVisibility(View.GONE);
		}

        return convertView;
    }



    final static class ViewHolder {
        TextView song_text;
        TextView letter_text;
        TextView artist_tv;
        TextView duration_tv;
        View selected_view;
        LinearLayout song_item;
    }

    @Override
    public Object[] getSections() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPositionForSection(int section) {
        // TODO Auto-generated method stub
        for (int i = 0; i < getCount(); i++) {
            String sortStr = ((MusicInfo) getItem(i)).getKeyofTitle();
            char firstchar = sortStr.toUpperCase().charAt(0);

            if (firstchar == section) return i;
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        // TODO Auto-generated method stub
//        return ((Song) getItem(position)).getSortLetters().charAt(0);
        return ((MusicInfo) getItem(position)).getKeyofTitle().charAt(0);
    }

    public interface OnItemClickListener{
        void onItemClick(int postion);
    }
}
