package com.huwei.sweetmusicplayer.ui.widgets.auto;

/**
 * Ë¢ÐÂ½Ó¿Ú
 * @author jerry
 * @date 2015-11-23
 */
public interface IPullRefershBase {

//    void setRefreshEnable(boolean refreshEnable);

    void setLoadEnable(boolean loadEnable);

    void setOnRefreshListener(OnRefreshListener onRefreshListener);

    void setOnLoadListener(OnLoadListener onLoadListener);

    void onRefreshComplete();

    void onLoadComplete(boolean hasMore);

    void onRefresh();

    void onLoad();

    public interface OnRefreshListener{
        void onRefresh();
    }

    public interface OnLoadListener{
        void onLoad();
    }
}
