package com.huwei.sweetmusicplayer.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.SweetApplication;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.contains.IContain;
import com.huwei.sweetmusicplayer.dao.AlbumInfoDao;
import com.huwei.sweetmusicplayer.dao.ArtistInfoDao;
import com.huwei.sweetmusicplayer.dao.DaoSession;
import com.huwei.sweetmusicplayer.dao.MusicInfoDao;
import com.huwei.sweetmusicplayer.interfaces.OnScanListener;
import com.huwei.sweetmusicplayer.models.AlbumInfo;
import com.huwei.sweetmusicplayer.models.ArtistInfo;
import com.huwei.sweetmusicplayer.models.MusicInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 音乐查询工具类
 *
 * @author jayce
 * @date 2015/06/11
 */
public class MusicUtils implements IContain {
    public static final String TAG = "MusicUtils";

    public static final Uri sArtworkUri = Uri
            .parse("content://media/external/audio/albumart");
    private static final BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
    private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

    private static String[] proj_music = new String[]{
            MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.DURATION};

    private static String[] proj_album = new String[]{MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS, MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART, MediaStore.Audio.Albums.ARTIST};

    private static String[] proj_artist = new String[]{
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_ALBUMS};

    public static final int THUMBNAIL_LEN_DP = 56;

    public static final int MSG_SCAN_SUCCESS = 0;   //所有音乐扫描成功
    public static final int MSG_SCAM_FAIL = 1;  //扫描失败 或者取消
    public static final int MSG_SCAN_ING = 2;   //正在扫描某首歌  一般是扫描完成后显示

    public static final String KEY_BUNDLE_MUSICINFO = "KEY_BUNDLE_MUSICINFO";

    private Context mContext;

    private OnScanListener mOnScanListener;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mOnScanListener != null) {
                switch (msg.what) {
                    case MSG_SCAN_SUCCESS:
                        mOnScanListener.onSuccess();
                        break;
                    case MSG_SCAM_FAIL:
                        mOnScanListener.onFail();
                        break;
                    case MSG_SCAN_ING:
                        mOnScanListener.onScan((MusicInfo) msg.getData().getParcelable(KEY_BUNDLE_MUSICINFO));
                        break;
                }
            }
        }
    };

    private Thread mScanMusicThread = new Thread() {
        @Override
        public void run() {
            scanMusicToSQLite();
        }
    };

    public MusicUtils(Context context) {
        this.mContext = context;
    }

    public void startScan() {
        mScanMusicThread.start();
    }

    public void setOnScanListener(OnScanListener mOnScanListener) {
        this.mOnScanListener = mOnScanListener;
    }

    /**
     * 扫描本地音乐  并且添加到本地数据库
     */
    private boolean scanMusicToSQLite() {
        ContentResolver cr = mContext.getContentResolver();
        StringBuffer select = new StringBuffer(" 1=1 ");
        // 查询语句：检索出.mp3为后缀名，时长大于1分钟，文件大小大于1MB的媒体文件
        if (Environment.isFilterSize(mContext)) {
            select.append(" and " + MediaStore.Audio.Media.SIZE + " > " + FILTER_SIZE);
        }
        if (Environment.isFilterDuration(mContext)) {
            select.append(" and " + MediaStore.Audio.Media.DURATION + " > " + FILTER_DURATION);
        }
        final Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj_music,
                select.toString(), null,
                MediaStore.Audio.Media.TITLE_KEY);

        while (cursor.moveToNext()) {

            final DaoSession session = SweetApplication.getDaoSession();

            final MusicInfoDao musicInfoDao = session.getMusicInfoDao();
            final AlbumInfoDao albumInfoDao = session.getAlbumInfoDao();
            final ArtistInfoDao artistInfoDao = session.getArtistInfoDao();

            final MusicInfo musicInfo = new MusicInfo();
            musicInfo.setSongId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicInfo.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicInfo.setAlbumId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicInfo.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            musicInfo.setArtistId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)));
            musicInfo.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            musicInfo.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            musicInfo.setFavorite(false);

            final AlbumInfo albumInfo = getAlbumById(mContext, musicInfo.getAlbumId());

            final ArtistInfo artistInfo = getArtistInfoById(mContext, musicInfo.getArtistId());

            try {
                session.runInTx(new Runnable() {
                    @Override
                    public void run() {
                        if (musicInfo == null) {
                            return;
                        }

                        if (musicInfoDao.load(musicInfo.getSongId()) == null) {
                            musicInfoDao.insert(musicInfo);
                        }

                        if (albumInfoDao.load(musicInfo.getAlbumId()) == null && albumInfo != null) {
                            albumInfoDao.insertOrReplace(albumInfo);
                        }

                        if (artistInfoDao.load(musicInfo.getArtistId()) == null && artistInfo != null) {
                            artistInfoDao.insertOrReplace(artistInfo);
                        }
                    }
                });

                Message msg = Message.obtain();
                msg.what = MSG_SCAN_ING;
                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY_BUNDLE_MUSICINFO, musicInfo);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                Log.i(TAG, "Exception:" + e.toString());
                e.printStackTrace();
                mHandler.sendEmptyMessage(MSG_SCAM_FAIL);
                return false;
            }

        }

        mHandler.sendEmptyMessage(MSG_SCAN_SUCCESS);
        return true;
    }

    /**
     * 通过Id查询专辑信息   contentProvider
     *
     * @param albumId
     * @return
     */
    public static AlbumInfo getAlbumById(Context context, Long albumId) {
        ContentResolver cr = context.getContentResolver();
        AlbumInfo albumInfo = new AlbumInfo();
        String selection = MediaStore.Audio.Albums._ID + " =? ";
        String[] selectionArgs = new String[]{albumId + ""};
        Cursor cursor = cr.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, proj_album,
                selection, selectionArgs,
                MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);
        if (cursor != null && cursor.moveToNext()) {
            albumInfo.setAlbumId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID)));
            albumInfo.setAlbumArt(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
            albumInfo.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));
            albumInfo.setNumSongs(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));
            albumInfo.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
            return albumInfo;
        }
        return null;
    }

    public static ArtistInfo getArtistInfoById(Context context, Long artistId) {
        ContentResolver cr = context.getContentResolver();
        ArtistInfo artistInfo = new ArtistInfo();
        String selection = MediaStore.Audio.Artists._ID + " =? ";
        String[] selectionArgs = new String[]{artistId + ""};
        Cursor cursor = cr.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, proj_artist,
                selection, selectionArgs,
                MediaStore.Audio.Artists.DEFAULT_SORT_ORDER);
        if (cursor != null && cursor.moveToNext()) {
            artistInfo.setArtistId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID)));
            artistInfo.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
            return artistInfo;
        }
        return null;
    }

    /**
     * 查询音乐信息
     */
    public static List<MusicInfo> queryMusic() {
        DaoSession session = SweetApplication.getDaoSession();
        MusicInfoDao musicInfoDao = session.getMusicInfoDao();

        return musicInfoDao.loadAll();
    }

    public static List<MusicInfo> queryMusicByAlbumId(Long album_id) {
        DaoSession session = SweetApplication.getDaoSession();
        MusicInfoDao musicInfoDao = session.getMusicInfoDao();

        return musicInfoDao.queryBuilder().where(MusicInfoDao.Properties.AlbumId.eq(album_id)).list();
    }

    /**
     * 通过artistId查询本地音乐
     *
     * @return
     */
    public static List<MusicInfo> queryMusicByArtistId(Long artistId) {
        DaoSession session = SweetApplication.getDaoSession();
        MusicInfoDao musicInfoDao = session.getMusicInfoDao();

        return musicInfoDao.queryBuilder().where(MusicInfoDao.Properties.ArtistId.eq(artistId)).list();
    }

    /**
     * 查询专辑信息
     *
     * @return
     */
    public static List<AlbumInfo> queryAlbumList() {
        DaoSession session = SweetApplication.getDaoSession();
        AlbumInfoDao albumInfoDao = session.getAlbumInfoDao();

        return albumInfoDao.loadAll();
    }

    /**
     * 查询歌手列表
     *
     * @return
     */
    public static List<ArtistInfo> queryArtistList() {
        DaoSession session = SweetApplication.getDaoSession();
        ArtistInfoDao artistInfoDao = session.getArtistInfoDao();

        return artistInfoDao.loadAll();
    }

    /**
     * 默认是缩略图
     *
     * @param context
     * @param album_id
     * @return
     */
    public static Bitmap getArtworkQuick(Context context, long album_id) {
        BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.img_album_background);
        int thumbnailLen_px = DisplayUtil.dip2px(context, THUMBNAIL_LEN_DP);
        return getArtworkQuick(context, album_id, thumbnailLen_px, thumbnailLen_px);  //默认尺寸
    }

    //todo 添加其他两种类型的size
    public static Bitmap getArtworkQuick(Context context, long album_id, AbstractMusic.PicSizeType picSizeType) {
        switch (picSizeType) {
            case SMALL:
                return getArtworkQuick(context, album_id);
            case HUGE:
                return getArtworkQuick(context, album_id, SweetApplication.mScreenWidth, SweetApplication.mScreenHeight);
        }
        return null;
    }

    public static Bitmap getArtworkQuick(Context context, long album_id, int w, int h) {
        w -= 1;
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
        if (uri != null) {
            ParcelFileDescriptor fd = null;
            try {
                fd = res.openFileDescriptor(uri, "r");
                int sampleSize = 1;

                sBitmapOptionsCache.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, sBitmapOptionsCache);
                int nextWidth = sBitmapOptionsCache.outWidth >> 1;
                int nextHeight = sBitmapOptionsCache.outHeight >> 1;
                while (nextWidth > w && nextHeight > h) {
                    sampleSize <<= 1;
                    nextWidth >>= 1;
                    nextHeight >>= 1;
                }

                sBitmapOptionsCache.inSampleSize = sampleSize;
                sBitmapOptionsCache.inJustDecodeBounds = false;
                Bitmap b = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, sBitmapOptionsCache);

                if (b != null) {
                    //按照所给的宽高缩放
                    if (sBitmapOptionsCache.outWidth != w ||
                            sBitmapOptionsCache.outHeight != h) {
                        Bitmap tmp = Bitmap.createScaledBitmap(b, w, h, true);

                        if (tmp != b) {
                            b.recycle();
                        }
                        b = tmp;
                    }
                }

                return b;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fd != null) {
                    try {
                        fd.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        //默认图片
        return BitmapUtil.drawable2bitamp(context.getResources().getDrawable(R.drawable.img_album_background));
    }

    public static String getAlbumArtPath(Context context, Long albumid) {
        String strAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{android.provider.MediaStore.Audio.AlbumColumns.ALBUM_ART};
        Cursor cur = context.getContentResolver().query(
                Uri.parse(strAlbums + "/" + Long.toString(albumid)),
                projection, null, null, null);
        String strPath = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            strPath = cur.getString(0);
        }
        cur.close();
        cur = null;
        return strPath;
    }

    public static ArrayList<MusicInfo> getMusicList(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        ArrayList<MusicInfo> musicInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setSongId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicInfo.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicInfo.setAlbumId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicInfo.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            musicInfo.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            musicInfo.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            musicInfo.setFavorite(false);
            musicInfoList.add(musicInfo);
        }
        return musicInfoList;
    }

    public static List<AlbumInfo> getAlbumList(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        List<AlbumInfo> albumInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            AlbumInfo albumInfo = new AlbumInfo();
            albumInfo.setAlbumId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID)));
            albumInfo.setAlbumArt(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
            albumInfo.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));
            albumInfo.setNumSongs(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));
            albumInfo.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
            albumInfoList.add(albumInfo);
        }
        return albumInfoList;
    }


    public static List getArtistList(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        List<ArtistInfo> artistInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            ArtistInfo artistInfo = new ArtistInfo();
            artistInfo.setArtistId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID)));
            artistInfo.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
//            artistInfo.setNumSongs(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)));
            artistInfoList.add(artistInfo);
        }

        return artistInfoList;
    }
}
