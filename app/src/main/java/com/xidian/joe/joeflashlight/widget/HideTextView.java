package com.xidian.joe.joeflashlight.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/19.
 */
public class HideTextView extends TextView {

    public HideTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                setVisibility(View.GONE);
            }else if(msg.what == 1){
                setVisibility(View.VISIBLE);
            }
        }
    };
    class TextViewThread extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.sendEmptyMessage(0);
        }
    }

    public void hide(){
        new TextViewThread().start();
    }
}
