/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /root/AndroidStudioProjects/SweetMusicPlayer/app/src/main/aidl/com/huwei/sweetmusicplayer/IMusicControlerService.aidl
 */
package com.huwei.sweetmusicplayer;
// Declare any non-default types here with import statements

public interface IMusicControlerService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.huwei.sweetmusicplayer.IMusicControlerService
{
private static final java.lang.String DESCRIPTOR = "com.huwei.sweetmusicplayer.IMusicControlerService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.huwei.sweetmusicplayer.IMusicControlerService interface,
 * generating a proxy if needed.
 */
public static com.huwei.sweetmusicplayer.IMusicControlerService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.huwei.sweetmusicplayer.IMusicControlerService))) {
return ((com.huwei.sweetmusicplayer.IMusicControlerService)iin);
}
return new com.huwei.sweetmusicplayer.IMusicControlerService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getPid:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPid();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_basicTypes:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
long _arg1;
_arg1 = data.readLong();
boolean _arg2;
_arg2 = (0!=data.readInt());
float _arg3;
_arg3 = data.readFloat();
double _arg4;
_arg4 = data.readDouble();
java.lang.String _arg5;
_arg5 = data.readString();
this.basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
return true;
}
case TRANSACTION_play:
{
data.enforceInterface(DESCRIPTOR);
this.play();
reply.writeNoException();
return true;
}
case TRANSACTION_pause:
{
data.enforceInterface(DESCRIPTOR);
this.pause();
reply.writeNoException();
return true;
}
case TRANSACTION_stop:
{
data.enforceInterface(DESCRIPTOR);
this.stop();
reply.writeNoException();
return true;
}
case TRANSACTION_seekTo:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.seekTo(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_preparePlayingList:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.util.List _arg1;
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_arg1 = data.readArrayList(cl);
this.preparePlayingList(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_isPlaying:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isPlaying();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPlayingSongIndex:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPlayingSongIndex();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getNowPlayingSong:
{
data.enforceInterface(DESCRIPTOR);
com.huwei.sweetmusicplayer.models.MusicInfo _result = this.getNowPlayingSong();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_isForeground:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isForeground();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_nextSong:
{
data.enforceInterface(DESCRIPTOR);
this.nextSong();
reply.writeNoException();
return true;
}
case TRANSACTION_preSong:
{
data.enforceInterface(DESCRIPTOR);
this.preSong();
reply.writeNoException();
return true;
}
case TRANSACTION_randomSong:
{
data.enforceInterface(DESCRIPTOR);
this.randomSong();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.huwei.sweetmusicplayer.IMusicControlerService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public int getPid() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPid, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(anInt);
_data.writeLong(aLong);
_data.writeInt(((aBoolean)?(1):(0)));
_data.writeFloat(aFloat);
_data.writeDouble(aDouble);
_data.writeString(aString);
mRemote.transact(Stub.TRANSACTION_basicTypes, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void play() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_play, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void pause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pause, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stop() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stop, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void seekTo(int mesc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mesc);
mRemote.transact(Stub.TRANSACTION_seekTo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void preparePlayingList(int musicIndex, java.util.List list) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(musicIndex);
_data.writeList(list);
mRemote.transact(Stub.TRANSACTION_preparePlayingList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isPlaying() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isPlaying, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getPlayingSongIndex() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPlayingSongIndex, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.huwei.sweetmusicplayer.models.MusicInfo getNowPlayingSong() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.huwei.sweetmusicplayer.models.MusicInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getNowPlayingSong, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.huwei.sweetmusicplayer.models.MusicInfo.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isForeground() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isForeground, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void nextSong() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_nextSong, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void preSong() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_preSong, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void randomSong() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_randomSong, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getPid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_basicTypes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_play = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_pause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_stop = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_seekTo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_preparePlayingList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_isPlaying = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getPlayingSongIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getNowPlayingSong = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_isForeground = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_nextSong = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_preSong = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_randomSong = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
}
public int getPid() throws android.os.RemoteException;
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws android.os.RemoteException;
public void play() throws android.os.RemoteException;
public void pause() throws android.os.RemoteException;
public void stop() throws android.os.RemoteException;
public void seekTo(int mesc) throws android.os.RemoteException;
public void preparePlayingList(int musicIndex, java.util.List list) throws android.os.RemoteException;
public boolean isPlaying() throws android.os.RemoteException;
public int getPlayingSongIndex() throws android.os.RemoteException;
public com.huwei.sweetmusicplayer.models.MusicInfo getNowPlayingSong() throws android.os.RemoteException;
public boolean isForeground() throws android.os.RemoteException;
public void nextSong() throws android.os.RemoteException;
public void preSong() throws android.os.RemoteException;
public void randomSong() throws android.os.RemoteException;
}
