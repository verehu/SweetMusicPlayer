package com.huwei.sweetmusicplayer.business.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Process;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.business.BottomPlayActivity;
import com.huwei.sweetmusicplayer.business.SongScanActivity;
import com.huwei.sweetmusicplayer.contants.Contants;

public class MainActivity extends BottomPlayActivity implements MainContract.View, Contants, View.OnClickListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private View mMenuSongScan, mMenuExit, mMenuCountDown;


    private MainContract.Presenter mPresenter;

    public static Intent getStartActIntent(Context from) {
        Intent intent = new Intent(from, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        new MainPresenter(this, this);
        mPresenter.bindMusicController();
        mPresenter.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected boolean isNeedStatusView() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unBindMusicController();
    }

    @Override
    public void onClick(final View v) {
        mDrawerLayout.closeDrawers();
        mDrawerLayout.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (v.getId()) {
                    case R.id.fl_song_scan:
                        songscan(v);
                        break;
                    case R.id.fl_count_down:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle(R.string.sleep_countdown_dialog_title);
                        builder.setSingleChoiceItems(R.array.sleep_times, mPresenter.getSleepItem(),
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.setSleepCountDownByItem(which);

                                dialog.dismiss();
                            }
                        });

                        builder.show();

                        break;
                    case R.id.fl_exit:
                        Process.killProcess(Process.myPid());
                        break;
                    default:
                        break;
                }
            }
        }, 260);
    }


    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar.setNavigationIcon(R.drawable.actionbar_menu);

        //menu
        mMenuSongScan = findViewById(R.id.fl_song_scan);
        mMenuCountDown = findViewById(R.id.fl_count_down);
        mMenuExit = findViewById(R.id.fl_exit);
    }

    private void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        //menu
        mMenuSongScan.setOnClickListener(this);
        mMenuCountDown.setOnClickListener(this);
        mMenuExit.setOnClickListener(this);
    }

    public void songscan(View v) {
        Intent intent = new Intent();
        intent.setClass(this, SongScanActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
