package com.huwei.sweetmusicplayer.util.concurrent.locks;

/**
 * 不可重入锁
 * @author Ezio
 * @date 2017/06/21
 */

public class TLock   {
    private boolean isLocked = false;
    public synchronized void lock() throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
    }
    public synchronized void unlock(){
        isLocked = false;
        notifyAll();
    }
}
