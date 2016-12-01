package com.giousa.serialndkclient.serialutil;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/1
 * Email:65489469@qq.com
 */
public class Serial {
    public static native int OpenSerial(int num);
    public static native void SetSerialBaud(long baud);
    public static native int ReadSerialBuf(short[] ReadBuf,int ReadLen);
    public static native int WriteSerialBuf(short[] WriteBuf,int WriteLen);
    public static native int CloseSerial();

    static {
        System.loadLibrary("Serial");//之前在build.gradle里面设置的so名字，必须一致
    }
}
