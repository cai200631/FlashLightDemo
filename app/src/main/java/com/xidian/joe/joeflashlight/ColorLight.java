package com.xidian.joe.joeflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.xidian.joe.joeflashlight.widget.HideTextView;

/**
 * Created by Administrator on 2016/7/19.
 */
public class ColorLight extends Bulb implements ColorPickerDialog.OnColorChangedListener {
    protected int mCurrentColorLight = Color.RED;
    protected HideTextView mHideTextViewColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHideTextViewColor = (HideTextView) findViewById(R.id.textview_color_light);
    }

    public void onClick_ShowColorPicker(View view){
        new ColorPickerDialog(this,this, Color.RED).show();
    }

    @Override
    public void colorChanged(int color) {
        mUIColorLight.setBackgroundColor(color);
        mCurrentColorLight = color;
    }
}
