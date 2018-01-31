package com.huwei.sweetmusicplayer.business;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.business.main.MainActivity;
import com.huwei.sweetmusicplayer.ui.widgets.GradientTextProgress;
import com.huwei.sweetmusicplayer.util.Environment;
import com.huwei.sweetmusicplayer.util.FileUtil;
import com.huwei.sweetmusicplayer.util.LrcUtil;

import java.util.Random;


public class SplashActivity extends BaseActivity {
    private RelativeLayout rr_splash_container;
    private GradientTextProgress gtp_appname;
    private long mills = 0;
    private long delay = 1000;
    private static int maxValue = 100;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mills >= delay) {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            } else {
                mills += 100;
                gtp_appname.setProgress((int) (mills * maxValue / delay));

                handler.sendEmptyMessageDelayed(0, 100);
            }
        }
    };

    @Override
    protected boolean isNeedStatusView() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        rr_splash_container = (RelativeLayout) findViewById(R.id.rr_splash_container);
        gtp_appname = (GradientTextProgress) findViewById(R.id.gtp_appname);

        int resID = R.drawable.img_splash0;
        switch (new Random().nextInt(6)) {
            case 0:
                resID = R.drawable.img_splash0;
                break;
            case 1:
                resID = R.drawable.img_splash1;
                break;
            case 2:
                resID = R.drawable.img_splash2;
                break;
            case 3:
                resID = R.drawable.img_splash3;
                break;
            case 4:
                resID = R.drawable.img_splash4;
                break;
            case 5:
                resID = R.drawable.img_splash5;
                break;
            case 6:
                resID = R.drawable.img_splash6;
                break;
            case 7:
                resID = R.drawable.img_splash7;
                break;
            case 8:
                resID = R.drawable.img_splash8;
                break;
            case 9:
                resID = R.drawable.img_splash9;
                break;
        }
        rr_splash_container.setBackgroundDrawable(getResources().getDrawable(resID));
        gtp_appname.setMaxValue(maxValue);
        gtp_appname.setProColorInt(getResources().getColor(R.color.primary));

        FileUtil.createDir(LrcUtil.lrcRootPath);

        int hasRunCount = Environment.getHasRunCount(mContext);
        if (hasRunCount == 0) {
            startActivity(SongScanActivity.Companion.getStartActIntent(mContext, true));
            finish();
        } else {
            handler.sendEmptyMessage(0);
        }
        Environment.setHasRunCount(mContext, hasRunCount + 1);
    }
}
