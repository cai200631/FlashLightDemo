package com.xidian.joe.joeflashlight;

import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/18.
 */

public class Flashlight extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageViewFlashlight.setTag(false);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        ViewGroup.LayoutParams layoutParams = mImageViewFlashlightController.getLayoutParams();
        layoutParams.height = point.y * 3 / 4;
        layoutParams.width = point.x / 3;
        mImageViewFlashlightController.setLayoutParams(layoutParams);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeFlashlight();
    }

    public void onClick_Flashlight(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Toast.makeText(this,"当前设备没有闪关灯",Toast.LENGTH_SHORT).show();
        }
        if(!((Boolean) mImageViewFlashlight.getTag())){
            openFlashlight();
        }else{
            closeFlashlight();
        }
    }

    protected void openFlashlight() {
        TransitionDrawable drawable = (TransitionDrawable) mImageViewFlashlight.getDrawable();
        drawable.startTransition(200);
        mImageViewFlashlight.setTag(true);
        try{
            mCamera = Camera.open();
            int textureId = 0;
            mCamera.setPreviewTexture(new SurfaceTexture(textureId));
            mCamera.startPreview();
            mParameters = mCamera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParameters);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void closeFlashlight(){
        TransitionDrawable drawalbe = (TransitionDrawable) mImageViewFlashlight.getDrawable();
        if(((Boolean) mImageViewFlashlight.getTag())){
            drawalbe.reverseTransition(200);
            mImageViewFlashlight.setTag(false);
            if(mCamera != null){
                mParameters = mCamera.getParameters();
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(mParameters);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }
    }

}
