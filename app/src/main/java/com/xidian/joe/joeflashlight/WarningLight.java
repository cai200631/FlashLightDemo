package com.xidian.joe.joeflashlight;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/7/19.
 */
public class WarningLight extends Flashlight {
    protected boolean mWarninglightFlicker;  //闪烁
    protected boolean mWarninglightState;  //灯亮暗状态的切换


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWarninglightFlicker = true;
        mWarninglightState = true;
    }

    class WarninglightThread extends Thread{
        MyHandler mMyHandler = new MyHandler(WarningLight.this);
        @Override
        public void run() {
            mWarninglightFlicker = true;
            while(mWarninglightFlicker){
                try {
                    Thread.sleep(300);
                    mMyHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class MyHandler extends Handler{
        WeakReference<WarningLight> mWeakReference ;

        public MyHandler(WarningLight light) {
            mWeakReference = new WeakReference<>(light);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WarningLight light = mWeakReference.get();
            if(light != null){
                if (light.mWarninglightState) {
                    light.mImageViewWarninglight1.setImageResource(R.drawable.warning_light_on);
                    light.mImageViewWarninglight2.setImageResource(R.drawable.warning_light_off);
                    light.mWarninglightState = false;
                }else {
                    light.mImageViewWarninglight1.setImageResource(R.drawable.warning_light_off);
                    light.mImageViewWarninglight2.setImageResource(R.drawable.warning_light_on);
                    light.mWarninglightState = true;
                }
            }
        }
    }

}
