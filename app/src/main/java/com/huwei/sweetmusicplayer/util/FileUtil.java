package com.huwei.sweetmusicplayer.util;

import java.io.File;

/**
 * 文件操作工具类
 * @author Jayce
 * @date 2015/6/14
 */
public class FileUtil {
    public static void createDir(String dir){
        File file=new File(dir);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
