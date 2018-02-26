[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![PRs Welcome](https://img.shields.io/badge/prs-welcome-brightgreen.svg)](http://makeapullrequest.com)

**SweetMusicPlayer**是一款基于百度音乐API的在线音乐播放器。拥有播放本地、在线音乐以及mv等功能，采用 MVP + RxJava + Retrofit 架构的项目。
更多请看[博客](http://blog.csdn.net/column/details/sweetmusicplayer.html)。

## Features  
- 本地音乐按照歌曲，专辑，艺术家显示对应信息
- 在线搜索音乐并播放
- 智能匹配本地歌词,在线歌词
- 歌词调整播放进度
- 定时停止播放音乐
- 在线音乐播放MV
- 检索本地音乐功能

## TODO
- 换肤
- 音效调节
- 登录、收藏音乐

## Screenshots
![][1]
![][2]
![][3]

## Changelog
```
2018-02-05
支持在线MV播放

2018-01-31
引入Retrofit2，移除Volley

2018-01-27
修复在线搜索、歌词列表等显示bug

2018-01-19
播放界面加入圆形转盘

2018-01-17
重新定义版本号v2.0,完全移除注解框架androidannotations

2018-01-08
侧边菜单实现定时停止功能

2017-11-19
重新改版，使用kotlin混合开发

2017-04-15
改版MD后添加侧边菜单并重新设计图标

2016-10-23
添加闪屏页多种背景图片

2016-03-11
实现在线音乐API搜索 音乐、歌手、专辑

2015-10-22
音乐播放器改版为MD风格

2015-06-01
适配百度音乐API，支持在线播放音乐

2014-10-04
开始第一版的开发
```

## Libraries
- [Glide](https://github.com/bumptech/glide)
- [Retrofit](https://github.com/square/retrofit)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [OkHttp](https://github.com/square/okhttp)
- [ijkplayer](https://github.com/Bilibili/ijkplayer)
- [Rxbus](https://github.com/AndroidKnife/RxBus)
- [GreenDAO](https://github.com/greenrobot/greenDAO)


  [1]: screenshots/1_1.png
  [2]: screenshots/1_2.png
  [3]: screenshots/1_3.png