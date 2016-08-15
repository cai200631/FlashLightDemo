package com.xidian.joe.joeflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.xidian.joe.joeflashlight.widget.HideTextView;

/**
 * Created by Administrator on 2016/7/19.
 */
public class PoliceLight extends ColorLight {
    protected boolean mPoliceState;
    protected HideTextView mHideTextViewPoliceLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHideTextViewPoliceLight = (HideTextView) findViewById(R.id.textview_hide_police_light);

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int color = msg.what;
            mUIPoliceLight.setBackgroundColor(color);
        }
    };

    class PoliceThread extends  Thread{
        @Override
        public void run() {
            mPoliceState = true;
            while(mPoliceState){
                mHandler.sendEmptyMessage(Color.BLUE);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(Color.BLACK);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(Color.RED);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(Color.BLACK);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
