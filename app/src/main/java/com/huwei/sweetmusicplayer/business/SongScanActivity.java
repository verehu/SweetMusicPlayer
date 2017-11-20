package com.huwei.sweetmusicplayer.business;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huwei.sweetmusicplayer.Permission;
import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.contains.IntentExtra;
import com.huwei.sweetmusicplayer.business.interfaces.OnScanListener;
import com.huwei.sweetmusicplayer.business.models.MusicInfo;
import com.huwei.sweetmusicplayer.util.MusicUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.huwei.sweetmusicplayer.Permission.CODE_READ_EXTERNAL_STORAGE;
import static com.huwei.sweetmusicplayer.Permission.PERMISSIONS;

@EActivity(R.layout.activity_songscan)
public class SongScanActivity extends BaseActivity {

    private int songCount = 0 ;
    private boolean mAutoEnterMain;
    private MusicUtils mMusicUtils = new MusicUtils(this);

    @ViewById
    TextView scannow_tv;
    @ViewById
    TextView scancount_tv;
    @ViewById
    Button scanfinish_btn;
    @ViewById(R.id.btn_enterhome)
    Button mBtnEnterHome;

    @ViewById
    Toolbar toolbar;

    @AfterViews
    void init(){
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("歌曲扫描");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initData();
        initListener();
    }

    void initData(){
        mAutoEnterMain = getIntent().getBooleanExtra(IntentExtra.EXTRA_AUTO_ENTERMAIN, false);
    }

    void initListener(){
        mBtnEnterHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkToMain();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMusicUtils.setOnScanListener(new OnScanListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext,"扫描完毕",Toast.LENGTH_SHORT).show();

                mBtnEnterHome.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail() {
                Toast.makeText(mContext,"扫描失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScan(MusicInfo musicInfo) {
                songCount ++ ;
                scancount_tv.setText(String.valueOf(songCount));
                scannow_tv.setText(musicInfo.getTitle() + "--" + musicInfo.getPath());
            }
        });

        if (Permission.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            mMusicUtils.startScan();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, CODE_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CODE_READ_EXTERNAL_STORAGE:
                if (Permission.isPermissionGranted(requestCode, permissions, grantResults)) {
                    mMusicUtils.startScan();
                } else {
                    Toast.makeText(mContext, "无存储权限，无法扫描歌曲", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void checkToMain(){
//        if (mAutoEnterMain) {
            startActivity(MainActivity.getStartActIntent(mContext));
//        }
    }

    public static Intent getStartActIntent(Context from){
        return getStartActIntent(from, false);
    }

    public static Intent getStartActIntent(Context from, boolean autoEnterMain){
        Intent intent = new Intent(from,SongScanActivity_.class);
        intent.putExtra(IntentExtra.EXTRA_AUTO_ENTERMAIN, autoEnterMain);
        return intent;
    }
}
 
 




