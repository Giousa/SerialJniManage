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

    public void openSerial() {
        Serial.OpenSerial(4);
        setSerialBaud(555);

        mHandler.postDelayed(mTimerRunnable, 0);
    }

    public void closeSerial() {
        mHandler.removeCallbacks(mTimerRunnable);
        Serial.CloseSerial();
    }

    public void setSerialBaud(long baud) {
        Serial.SetSerialBaud(baud);
    }

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

    public void writeSerial(short[] WriteBuf) {
        Log.d(TAG,"writeSerial:"+WriteBuf[2]+"     length:"+WriteBuf.length);
        Serial.WriteSerialBuf(WriteBuf, WriteBuf.length);
    }


}
