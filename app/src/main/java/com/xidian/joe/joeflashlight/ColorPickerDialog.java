package com.xidian.joe.joeflashlight;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/7/19.
 */
public class ColorPickerDialog extends Dialog {
    private final int COLOR_DIALOG_WIDTH = 500;
    private final int COLOR_DIALOG_HEIGHT = 500;
    private final int CENTER_X = COLOR_DIALOG_WIDTH / 2;
    private final int CENTER_Y = COLOR_DIALOG_HEIGHT / 2;
    private final int CENTER_RADIUS = 60;

    public interface OnColorChangedListener {
        void colorChanged(int color);
    }

    private OnColorChangedListener mListener;
    private int mInitialColor;

    public ColorPickerDialog(Context context, OnColorChangedListener listener,int initialColor) {
        super(context);
        mListener = listener;
        mInitialColor = initialColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnColorChangedListener listener = new OnColorChangedListener() {
            @Override
            public void colorChanged(int color) {
                mListener.colorChanged(color);
                dismiss();
            }
        };
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new ColorPickerView(getContext(),listener,mInitialColor));
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(Color.BLACK);
        getWindow().setBackgroundDrawable(colorDrawable);

        getWindow().setAttributes(new WindowManager.LayoutParams(COLOR_DIALOG_WIDTH,COLOR_DIALOG_HEIGHT,0,0,0));
    }

    private class ColorPickerView extends View {
        private Paint mPaint;
        private Paint mCenterPaint;
        private final int[] mColors;  // 颜色带的起始颜色
        private OnColorChangedListener mListener;
        private boolean mTrackingCenter;
        private boolean mHeightlightCenter;
        private static final float PI = 3.1415926f;

        public ColorPickerView(Context context, OnColorChangedListener listener, int color) {
            super(context);
            mListener = listener;
            mColors = new int[]{0xffff0000, 0xffff00ff, 0xff0000ff, 0xff00ff00,
                    0xff00ff00, 0xffffff00, 0xffff0000};
            Shader shader = new SweepGradient(0, 0, mColors, null);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setShader(shader);  //使用shader进行渲染
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(80);

            mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCenterPaint.setColor(color);
            mCenterPaint.setStrokeWidth(5);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float r = CENTER_X - mPaint.getStrokeWidth() * 0.5f - 20;
            canvas.translate(CENTER_X, CENTER_Y);
            canvas.drawCircle(0, 0, r, mPaint);
            canvas.drawCircle(0, 0, CENTER_RADIUS, mCenterPaint);

            if (mTrackingCenter) {
                int c = mCenterPaint.getColor();
                mCenterPaint.setStyle(Paint.Style.STROKE);
                if (mHeightlightCenter) {
                    mCenterPaint.setAlpha(0xff);
                } else {
                    mCenterPaint.setAlpha(0x00);
                }
                canvas.drawCircle(0, 0, CENTER_RADIUS + mCenterPaint.getStrokeWidth(), mCenterPaint);
                mCenterPaint.setStyle(Paint.Style.FILL);
                mCenterPaint.setColor(c);

            }

        }

        private int ave(int s, int d, float p) {  //起始颜色+颜色值的偏移量值
            return s + Math.round(p * (d - s));
        }

        private int interpColor(int colors[], float unit) {
            if (unit <= 0) {
                return colors[0];
            }
            if (unit >= 1) {
                return colors[colors.length - 1];
            }
            float p = unit * (colors.length - 1);
            int i = (int) p;
            p -= i;
            int c0 = colors[i];
            int c1 = colors[i + 1];
            int a = ave(Color.alpha(c0), Color.alpha(c1), p);
            int r = ave(Color.red(c0), Color.red(c1), p);
            int g = ave(Color.green(c0), Color.green(c1), p);
            int b = ave(Color.blue(c0), Color.blue(c1), p);
            return Color.argb(a, r, g, b);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX() - CENTER_X;
            float y = event.getY() - CENTER_Y;
            boolean inCenter = Math.sqrt(x*x + y*y)<= CENTER_RADIUS;
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mTrackingCenter = inCenter;
                    if(inCenter){
                        mHeightlightCenter = true;
                        invalidate();
                        break;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    float angle = (float) Math.atan2(y,x);
                    float unit = angle/(2*PI);
                    if(unit < 0){
                        unit += 1;
                    }
                    mCenterPaint.setColor(interpColor(mColors,unit));
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    if(mTrackingCenter){
                        if(inCenter){
                            mListener.colorChanged(mCenterPaint.getColor());
                        }
                        mTrackingCenter = false;
                        invalidate();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}
