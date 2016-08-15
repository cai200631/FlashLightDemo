package com.xidian.joe.joeflashlight;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;

import com.xidian.joe.joeflashlight.widget.HideTextView;

/**
 * Created by Administrator on 2016/7/19.
 */
public class Bulb extends Morse {
    protected boolean mBulbCrossFadeFlag;
    protected TransitionDrawable mDrawable;
    protected HideTextView mHideTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawable = (TransitionDrawable) mImageViewBulb.getDrawable();
        mHideTextView = (HideTextView) findViewById(R.id.textview_bulb);
    }

    public void onClick_Bulb(View view){
        if(!mBulbCrossFadeFlag){
            screenBrightness(1f);
            mDrawable.startTransition(1000);
            mBulbCrossFadeFlag = true;
        }else{
            mDrawable.reverseTransition(1000);
            mBulbCrossFadeFlag = false;
        }
    }
}
