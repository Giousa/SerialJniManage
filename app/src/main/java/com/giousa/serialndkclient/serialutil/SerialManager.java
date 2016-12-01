package com.giousa.serialndkclient.serialutil;

import android.os.Handler;
import android.util.Log;

/**
 * Description:
 * Author：Giousa
 * Date：2016/8/15
 * Email：giousa@chinayoutu.com
 */
public class SerialManager {

    private final String TAG = SerialManager.class.getSimpleName();
    private static SerialManager mSerialManager;

    public static SerialManager getInstance() {
        if (mSerialManager == null) {
            mSerialManager = new SerialManager();
        }
        return mSerialManager;

    }

    public interface SerialSpeedChangeListener {
        void onSerialSpeedChanged(short speed);
    }

    private SerialSpeedChangeListener mSerialSpeedChangeListener;

    public void setSerialSpeedChangeListener(SerialSpeedChangeListener serialSpeedChangeListener) {
        mSerialSpeedChangeListener = serialSpeedChangeListener;
    }

    private Handler mHandler = new Handler();
    private Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            readSerial();
            mHandler.postDelayed(mTimerRunnable, 20);
        }
    };

    /**
     * 打开串口，设置波特率，这里可以随意
     */
    public void openSerial() {
        Serial.OpenSerial(4);
        setSerialBaud(555);

        mHandler.postDelayed(mTimerRunnable, 0);
    }

    /**
     * 关闭串口
     */
    public void closeSerial() {
        mHandler.removeCallbacks(mTimerRunnable);
        Serial.CloseSerial();
    }

    /**
     * 设置波特率
     * @param baud
     */
    public void setSerialBaud(long baud) {
        Serial.SetSerialBaud(baud);
    }

    /**
     * 读取串口，协议定义的是长度为9的short数组
     */
    public void readSerial() {

        short[] mShorts = new short[9];

        int serialBuf = Serial.ReadSerialBuf(mShorts, 9);
        Log.d(TAG, "serialBuf:" + serialBuf);

        if(serialBuf == 9){
            parseSerialData(mShorts);

        }
    }


    private void parseSerialData(short[] mShorts) {

        short speed = mShorts[2];
        if(mSerialSpeedChangeListener != null){
            mSerialSpeedChangeListener.onSerialSpeedChanged(speed);
        }
    }

    /**
     * 写入数据到串口
     * @param WriteBuf
     */
    public void writeSerial(short[] WriteBuf) {
        Log.d(TAG,"writeSerial:"+WriteBuf[2]+"     length:"+WriteBuf.length);
        Serial.WriteSerialBuf(WriteBuf, WriteBuf.length);
    }


}
