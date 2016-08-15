package com.xidian.joe.joeflashlight;

import android.graphics.Color;
import android.view.View;

import java.util.Set;

public class MainActivity extends Settings {

    public void onClick_ToFlashlight(View view) {
        hideAllUI();
        mUIFlashlight.setVisibility(View.VISIBLE);
        mLastUIType = UIType.UI_TYPE_FLASHLIGHT;
        mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
    }

    public void onClick_Controller(View view) {
        hideAllUI();
        if (mCurrentUIType != UIType.UI_TYPE_MAIN) {
            mUIMain.setVisibility(View.VISIBLE);
            mCurrentUIType = UIType.UI_TYPE_MAIN;
            mWarninglightFlicker = false;
            mBulbCrossFadeFlag = false;
            mPoliceState = false;
            mSharedPreferences.edit().putInt("warning_light_interval",mCurrentWarningLightInterval).
                    putInt("police_light_interval",mCurrentPoliceLightInterval).apply();
            screenBrightness(mDefaultScreenBrightness / 255f); //将屏幕亮度恢复到原来的样式
        } else {
            switch (mLastUIType) {
                case UI_TYPE_FLASHLIGHT:
                    mUIFlashlight.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
                    break;
                case UI_TYPE_WARNINGLIGHT:
                    mUIWarninglight.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
                    new WarninglightThread().start();
                    break;
                case UI_TYPE_MORSE:
                    mUIMorse.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_MORSE;
                    break;
                case UI_TYPE_BULB:
                    mUIBulb.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_BULB;
                    break;
                case UI_TYPE_COLOR:
                    mUIColorLight.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_COLOR;
                    break;
                case UI_TYPE_POLICE:
                    mUIPoliceLight.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_POLICE;
                    new PoliceThread().start();
                    break;
                case UI_TYPE_SETTING:
                    mUISettings.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_SETTING;
                    mLastUIType = mCurrentUIType;
                    break;
                default:
                    break;
            }
        }
    }

    public void onClick_ToWarninglight(View view) {   //存在的问题：
        hideAllUI();
        mUIWarninglight.setVisibility(View.VISIBLE);
        mLastUIType = UIType.UI_TYPE_WARNINGLIGHT;
        mCurrentUIType = mLastUIType;
        screenBrightness(1f);
        new WarninglightThread().start();
    }

    public void onClick_ToMorse(View view) {   //存在的问题：
        hideAllUI();
        mUIMorse.setVisibility(View.VISIBLE);
        mLastUIType = UIType.UI_TYPE_MORSE;
        mCurrentUIType = mLastUIType;
    }

    public void onClick_ToBulb(View view) {
        hideAllUI();
        mUIBulb.setVisibility(View.VISIBLE);
        mHideTextView.hide();
        mHideTextView.setTextColor(Color.BLACK);
        mLastUIType = UIType.UI_TYPE_BULB;
        mCurrentUIType = mLastUIType;
        screenBrightness(1f);
    }

    public void onClick_ToColor(View view){
        hideAllUI();
        mUIColorLight.setVisibility(View.VISIBLE);
        screenBrightness(1f);
        mLastUIType = UIType.UI_TYPE_COLOR;
        mCurrentUIType = mLastUIType;
        mHideTextViewColor.setTextColor(Color.rgb(255-Color.red(mCurrentColorLight),
                255-Color.green(mCurrentColorLight), 255-Color.blue(mCurrentColorLight)));  //互补色
    }

    public void onClick_ToPolice(View view){
        hideAllUI();
        mUIPoliceLight.setVisibility(View.VISIBLE);
        screenBrightness(1f);
        mLastUIType = UIType.UI_TYPE_POLICE;
        mCurrentUIType = mLastUIType;
        mHideTextViewPoliceLight.hide();
        new PoliceThread().start();
    }
    public void onClick_ToSettings(View view){
        hideAllUI();
        mUISettings.setVisibility(View.VISIBLE);
        mLastUIType = UIType.UI_TYPE_SETTING;
        mCurrentUIType = mLastUIType;

    }

}
