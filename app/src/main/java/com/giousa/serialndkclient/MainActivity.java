package com.giousa.serialndkclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.giousa.serialndkclient.serialutil.SerialManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SerialManager.SerialSpeedChangeListener {

    @InjectView(R.id.btn_read)
    Button mBtnRead;
    @InjectView(R.id.btn_write)
    Button mBtnWrite;
    private SerialManager mSerialManager;
    private short[] mNoweData = new short[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

    }

    @OnClick({R.id.btn_read, R.id.btn_write})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_read:
                mSerialManager = SerialManager.getInstance();
                mSerialManager.openSerial();
                mSerialManager.setSerialSpeedChangeListener(this);
                mBtnRead.setClickable(false);
                break;
            case R.id.btn_write:
                sendNowelData();
                break;
        }
    }

    @Override
    public void onSerialSpeedChanged(short speed) {
        Log.d("串口数据：","speed="+speed);
    }

    /**
     * 发送数据，要根据具体协议，这里定义的是长度为9的数组
     */
    private void sendNowelData() {

        mNoweData[0] = 0x55;
        mNoweData[1] = 0x02;
        mNoweData[2] = 0x06;
        mNoweData[3] = 0x00;
        mNoweData[4] = 0x00;
        mNoweData[5] = 0x00;
        mNoweData[6] = 0x00;
        mNoweData[7] = 0x00;
        mNoweData[8] = 0xAA;

        mSerialManager.writeSerial(mNoweData);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSerialManager != null){
            mSerialManager.closeSerial();
        }
    }
}
