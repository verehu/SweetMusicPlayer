package com.ui.jerry.swipebackdemo;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.huwei.sweetmusicplayer.util.MusicUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        MusicUtils.queryArtistList();
    }
}