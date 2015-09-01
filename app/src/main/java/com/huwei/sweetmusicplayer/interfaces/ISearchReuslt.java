package com.huwei.sweetmusicplayer.interfaces;

/**
 * 在线搜索出来的公共结果
 * @author jayce
 * @date 2015/08/18
 */
public interface ISearchReuslt {
    public enum SearchResultType{
        None,Song,Album,Artist
    }

    //抽象一个公共的方法显示名称
    public abstract String getName();

    //抽象一个公共的方法显示type
    public abstract SearchResultType getSearchResultType();
}
