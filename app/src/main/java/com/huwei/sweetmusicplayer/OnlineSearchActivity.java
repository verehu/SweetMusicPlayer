package com.huwei.sweetmusicplayer;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.baidumusic.po.Album2;
import com.huwei.sweetmusicplayer.baidumusic.po.QueryResult;
import com.huwei.sweetmusicplayer.baidumusic.po.Song2;
import com.huwei.sweetmusicplayer.baidumusic.resp.QueryMergeResp;
import com.huwei.sweetmusicplayer.contains.IntentExtra;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.interfaces.IQueryReuslt;
import com.huwei.sweetmusicplayer.ui.adapters.SearchResultAdapter;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线音乐搜索的结果页面
 *
 * @author Jayce
 * @date 2015/8/17
 */
@EActivity(R.layout.activity_online_search)
public class OnlineSearchActivity extends BaseActivity {
    public static final String TAG = "OnlineSearchActivity";

    private int pageNo = 1;
    private int pageSize = 50;

    @ViewById
    ListView lv_online_search;
    @ViewById(R.id.actionbar)
    Toolbar toolbar;

    SearchResultAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            adapter.notifyDataSetInvalidated();
        }
    };

    @AfterViews
    void init() {
        initView();
        initListener();

        handleIntent(getIntent());
    }

    void initView() {
        adapter = new SearchResultAdapter(mContext);
        lv_online_search.setAdapter(adapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked(v);
            }
        });
        lv_online_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IQueryReuslt reuslt = (IQueryReuslt) parent.getItemAtPosition(position);
                switch (reuslt.getSearchResultType()) {
                    case Song:
                        List<AbstractMusic> list = new ArrayList<>();
                        Log.i(TAG, "song:" + ((Song2) reuslt).songInfo);
                        list.add((Song2) reuslt);
                        //点击当前歌曲，把当前歌曲加入播放队列
                        MusicManager.getInstance().preparePlayingList(0, list);


                        finish();
                        break;
                    case Album:
                        Intent intent = new Intent(OnlineSearchActivity.this,AlbumInfoActivity_.class);
                        intent.putExtra(IntentExtra.EXTRA_ALBUM_ID,((Album2)reuslt).getAlbumid());
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            setTitle(query);
            doQuery(query);

            Toast.makeText(mContext, query, Toast.LENGTH_SHORT).show();
        }
    }

    void doQuery(String query) {
        //todo暂时只搜索 1-50个  后续加入下拉刷新列表
        BaiduMusicUtil.queryMerge(query, pageNo, pageSize, new HttpHandler(mContext) {
            @Override
            public void onSuccess(String response) {
                adapter.getData().clear();

                final QueryMergeResp sug = new Gson().fromJson(response, QueryMergeResp.class);
                QueryResult result = sug.result;

                if(result!=null){

                    if(result.song_info!=null){
                        adapter.addALl(result.song_info.song_list);
                    }

                    Log.d(TAG,"album_list:"+result.album_info.album_list);

                    if(result.album_info!=null){
                        adapter.addALl(result.album_info.album_list);
                    }

                    //todo 后续加入其他类型
                }

                adapter.notifyDataSetInvalidated();

            }
        });
    }

}
