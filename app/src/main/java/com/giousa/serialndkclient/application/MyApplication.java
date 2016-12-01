package com.giousa.serialndkclient.application;

import android.app.Application;
import android.util.Log;

import com.giousa.serialndkclient.serialutil.SerialPort;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/1
 * Email:65489469@qq.com
 */
public class MyApplication extends Application {

    private final String mSerialPath = "/dev/ttyS3";

    @Override
    public void onCreate() {
        super.onCreate();
        exeShell();
    }

    private void exeShell() {
        changeSerialPortMode(mSerialPath, "777");
        openSerialPort(mSerialPath);
    }

    public void changeSerialPortMode(String serialPortName, String mode) {
        try {
            Process sh = Runtime.getRuntime().exec("/system/bin/su");

            String changeModeCommand = "chmod " + mode + " " + serialPortName;
            OutputStream os = sh.getOutputStream();
            writeCommand(os, changeModeCommand);
            String exitCommand = "exit";
            writeCommand(os, exitCommand);
            os.close();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void openSerialPort(String serialPortName) {
        try {
            SerialPort serialPort = new SerialPort(new File(serialPortName));
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeCommand(OutputStream os, String command) throws Exception {
        os.write((command + "\n").getBytes("ASCII"));
        os.flush();
    }
}
