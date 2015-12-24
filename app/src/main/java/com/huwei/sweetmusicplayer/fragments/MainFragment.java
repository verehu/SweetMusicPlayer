package com.huwei.sweetmusicplayer.fragments;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huwei.sweetmusicplayer.OnlineSearchActivity_;
import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.contains.IMusicViewTypeContain;
import com.huwei.sweetmusicplayer.fragments.base.BaseFragment;
import com.huwei.sweetmusicplayer.ui.adapters.PagerAdapter;
import com.huwei.sweetmusicplayer.util.TimeUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntArrayRes;

/**
 * @author Jayce
 * @date 2015/6/17
 */
@EFragment(R.layout.fragment_main)
public class MainFragment extends BaseFragment implements IMusicViewTypeContain {

    @ViewById(R.id.actionbar)
    Toolbar toolbar;
    @ViewById
    ViewPager viewPager;
    @ViewById
    TabLayout tabs;
    @ViewById
    TextView tv_sleepinfo, tv_sleep_cancel;
    @ViewById
    LinearLayout ll_sleepbar;

    @SystemService
    LayoutInflater inflater;
    @IntArrayRes
    int sleep_times[];


    private long sleeptime = 0;
    private final int SLEEP = 0;

    private Handler sleepHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (sleeptime > 0) {
                sleeptime = sleeptime - 1000;
                setSleepBarVisiable(true);
                tv_sleepinfo.setText(TimeUtil.mill2mmss(sleeptime));

                sleepHandler.sendEmptyMessageDelayed(SLEEP, 1000);
                //sleeptime == -1表示取消了计时
            } else if (sleeptime == -1) {
                sleeptime = 0;
            } else {
                sleeptime = 0;
                setSleepBarVisiable(false);

                //todo
//                exitApp();
                System.exit(0);
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (isSleepCountDown()) {
            setSleepBarVisiable(true);
        } else {
            setSleepBarVisiable(false);
        }
    }

    @AfterViews
    void init() {


        initToolBar();
        initPager();
    }

    @Click(R.id.tv_sleep_cancel)
    void tv_sleep_cancelWasClicked() {
        setSleepBarVisiable(false);
        sleeptime = -1;
    }

    void initToolBar() {
        toolbar.setTitle("SweetMusicPlayer");
        toolbar.inflateMenu(R.menu.activity_main_menu);
        initMenu(toolbar.getMenu());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_search:
//                        mAct.onSearchRequested();
                        break;
//                    case R.id.menu_scan:
//                        Intent intent = new Intent();
//                        intent.setClass(getActivity(), SongScanActivity_.class);
//                        startActivity(intent);
//                        return true;
//                    case R.id.menu_clock:
//                        final String[] mItems = new String[sleep_times.length];
//                        for (int i = 0; i < mItems.length; i++)
//                            mItems[i] = sleep_times[i] + "分钟";
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setTitle("请设置自动关闭的时间");
//                        builder.setItems(mItems, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                switch (which) {
//                                    case 0:
//                                        sleeptime = 10 * 60 * 1000;
//                                        sleepHandler.sendEmptyMessage(SLEEP);
//                                        break;
//                                    case 1:
//                                        sleeptime = 20 * 60 * 1000;
//                                        sleepHandler.sendEmptyMessage(SLEEP);
//                                        break;
//                                    case 2:
//                                        sleeptime = 30 * 60 * 1000;
//                                        sleepHandler.sendEmptyMessage(SLEEP);
//                                        break;
//                                    case 3:
//                                        sleeptime = 60 * 60 * 1000;
//                                        sleepHandler.sendEmptyMessage(SLEEP);
//                                        break;
//                                    case 4:
//                                        sleeptime = 90 * 60 * 1000;
//                                        sleepHandler.sendEmptyMessage(SLEEP);
//                                        break;
//                                }
//                                Toast.makeText(getActivity(), mItems[which], Toast.LENGTH_LONG).show();
//                            }
//                        });
//                        builder.create().show();
//                        return true;
                    default:
                        break;
                }
                return true;
            }
        });
    }


    private void initMenu(Menu menu) {
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) mAct.getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(mAct, OnlineSearchActivity_.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setIconifiedByDefault(true);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        searchView.setSubmitButtonEnabled(true);
    }

    private void initPager() {
        PagerAdapter mPagerAdapter = new PagerAdapter(
                getActivity().getSupportFragmentManager()){

            @Override
            public CharSequence getPageTitle(int position) {
                return getResources().getStringArray(R.array.tab_titles)[position];
            }
        };

        // add tabs_recent
//        mPagerAdapter.addFragment(new RecentlyAddedFragment());
        // add tab_songs
        Bundle bundle = new Bundle();
        bundle.putInt(MUSIC_SHOW_TYPE, SHOW_MUSIC);

        String tabs_str[] = getResources().getStringArray(R.array.tab_titles);

        LocalMusicFragment musicFragment = new LocalMusicFragment_();
        musicFragment.setArguments(bundle);
        mPagerAdapter.addFragment(musicFragment);
        // add tab_artists
        mPagerAdapter.addFragment(new LocalArtistFragment());
        // add tab_albums
        mPagerAdapter.addFragment(new AlbumsFragment_());
        //add tab_online
        mPagerAdapter.addFragment(new OnlineFragment_());
        // add tab_playlists
//        mPagerAdapter.addFragment(new PlaylistsFragment());
        // add tab_genres
//        mPagerAdapter.addFragment(new GenresFragment());

        viewPager.setAdapter(mPagerAdapter);

        tabs.setupWithViewPager(viewPager);

        tabs.setTabsFromPagerAdapter(mPagerAdapter);
    }


    public void setSleepBarVisiable(boolean flag) {
        int visiblity = flag ? View.VISIBLE : View.GONE;
        if (ll_sleepbar.getVisibility() != visiblity) {
            ll_sleepbar.setVisibility(visiblity);
        }
    }

    public boolean isSleepCountDown() {
        return sleeptime > 0;
    }
}
