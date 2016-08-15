package com.xidian.joe.joeflashlight;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/18.
 */
public class BaseActivity extends Activity {
    protected enum UIType {
        UI_TYPE_MAIN, UI_TYPE_FLASHLIGHT, UI_TYPE_WARNINGLIGHT,
        UI_TYPE_MORSE, UI_TYPE_BULB, UI_TYPE_COLOR, UI_TYPE_POLICE,
        UI_TYPE_SETTING
    }
    protected ImageView mImageViewFlashlight;
    protected ImageView mImageViewFlashlightController;
    protected ImageView mImageViewWarninglight1;
    protected ImageView mImageViewWarninglight2;
    protected Camera mCamera;
    protected Camera.Parameters mParameters;
    protected EditText mEditTextMorseCode;
    protected ImageView mImageViewBulb;
    protected int mCurrentWarningLightInterval = 500;
    protected int mCurrentPoliceLightInterval = 100;
    protected SeekBar mSeekBarWarningLight;
    protected SeekBar mSeekBarPoliceLight;
    protected Button  mButtonAddShortcut;
    protected Button  mButtonRemoveShortcut;


    protected FrameLayout mUIFlashlight;
    protected LinearLayout mUIMain;
    protected LinearLayout mUIWarninglight;
    protected LinearLayout mUIMorse;
    protected FrameLayout mUIBulb;
    protected FrameLayout mUIColorLight;
    protected FrameLayout mUIPoliceLight;
    protected LinearLayout mUISettings;

    protected  int mFinishCount = 0;
    protected SharedPreferences mSharedPreferences;

    protected UIType mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
    protected UIType mLastUIType = UIType.UI_TYPE_FLASHLIGHT;

    protected int mDefaultScreenBrightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageViewFlashlight = (ImageView) findViewById(R.id.imageview_flashlight);
        mImageViewFlashlightController = (ImageView) findViewById(R.id.imageview_flashlight_controller);
        mImageViewWarninglight1 = (ImageView) findViewById(R.id.imageview_warning_light1);
        mImageViewWarninglight2 = (ImageView) findViewById(R.id.imageview_warning_light2);
        mEditTextMorseCode =(EditText) findViewById(R.id.ed_morse_code);
        mImageViewBulb = (ImageView) findViewById(R.id.imageview_bulb);
        mButtonAddShortcut = (Button) findViewById(R.id.button_add_shortcut);
        mButtonRemoveShortcut = (Button) findViewById(R.id.button_delete_shortcut);
        mSeekBarPoliceLight =(SeekBar) findViewById(R.id.seekbar_police_light);
        mSeekBarWarningLight =(SeekBar) findViewById(R.id.seekbar_warning_light);

        mUIFlashlight = (FrameLayout) findViewById(R.id.framelayout_flashlight);
        mUIMain = (LinearLayout) findViewById(R.id.linearlayout_main);
        mUIWarninglight = (LinearLayout) findViewById(R.id.linearlayout_warninglight);
        mUIMorse =(LinearLayout) findViewById(R.id.linearlayout_morse);
        mUIBulb =(FrameLayout) findViewById(R.id.framelayout_bulb);
        mUIColorLight =(FrameLayout) findViewById(R.id.framelayout_color_light);
        mUIPoliceLight =(FrameLayout) findViewById(R.id.framelayout_police_light);
        mUISettings = (LinearLayout) findViewById(R.id.linearlayout_settings);
        mSharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);


        mDefaultScreenBrightness = getDefaultScreenBrightness();
        mCurrentPoliceLightInterval = mSharedPreferences.getInt("police_light_interval",200);
        mCurrentWarningLightInterval = mSharedPreferences.getInt("warning_light_interval",200);
        mSeekBarPoliceLight.setProgress(mCurrentPoliceLightInterval -50);
        mSeekBarWarningLight.setProgress(mCurrentWarningLightInterval - 100);
    }

    @Override
    public void finish() {
        mFinishCount++;
        if(mFinishCount == 1){
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_LONG).show();
        }if(mFinishCount == 2){
            super.finish();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {  //拦截总的触摸事件
        mFinishCount =0;
        return super.dispatchTouchEvent(ev);
    }

    protected void hideAllUI() {
        mUIMain.setVisibility(View.GONE);
        mUIFlashlight.setVisibility(View.GONE);
        mUIWarninglight.setVisibility(View.GONE);
        mUIMorse.setVisibility(View.GONE);
        mUIBulb.setVisibility(View.GONE);
        mUIColorLight.setVisibility(View.GONE);
        mUIPoliceLight.setVisibility(View.GONE);
        mUISettings.setVisibility(View.GONE);
    }

    protected void screenBrightness(float value){
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = value;
        getWindow().setAttributes(layoutParams);
    }

    protected int getDefaultScreenBrightness(){
        int value =0;
        try {
            value = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }


}
