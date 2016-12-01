package com.giousa.serialndkclient.serialutil;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:16/9/1
 * Time:上午10:26
 */
public class SerialPort {

    private final String TAG = SerialPort.class.getSimpleName();
    private FileDescriptor mfd;

    public SerialPort(File device) throws SecurityException, IOException {
        if (!device.canRead() || !device.canWrite()) {
            try {
                Process su;
                su = Runtime.getRuntime().exec("/system/xbin/su");

                Log.i(TAG, "SerialPort: "+su.toString());
                String cmd = "chmod 777 " + device.getAbsolutePath() + "\n" + "exit\n";
                Log.i(TAG, "SerialPort: " + cmd.toString());


            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }
    }
}
