package com.huwei.sweetmusicplayer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


/**
 * 项目中Activity基类，用于对activity的整体控制
 * @author Jayce
 * @date 2015/6/19
 */
public class BaseActivity extends ActionBarActivity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
    }

    public void onBackClicked(View view){
        onBackPressed();
    }
}
