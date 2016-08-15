package com.xidian.joe.joeflashlight;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/20.
 */
public class Settings extends PoliceLight implements SeekBar.OnSeekBarChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSeekBarWarningLight.setOnSeekBarChangeListener(this);
        mSeekBarWarningLight.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekbar_warning_light:
                mCurrentWarningLightInterval = progress + 100;
                break;
            case R.id.seekbar_police_light:
                mCurrentPoliceLightInterval = progress + 50;
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onClick_AddShortcut(View view) {
        if (!shortcutInScreen()) {
            Intent installShortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            installShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "JoeFlashLight");
            Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);

            Intent flashlightIntent = new Intent();
            flashlightIntent.setClassName("com.xidian.joe.joeflashlight", "com.xidian.joe.joeflashlight.MainActivity");
            flashlightIntent.setAction(Intent.ACTION_MAIN);
            flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            installShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, flashlightIntent);
            sendBroadcast(installShortcut);
            Toast.makeText(this, "已成功将快捷方式添加到桌面！", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"快捷方式已经存在",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick_RemoveShortcut(View view) {
        if(shortcutInScreen()) {

            Intent uninstallShortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
            uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "JoeFlashLight");

            Intent flashlightIntent = new Intent();
            flashlightIntent.setClassName("com.xidian.joe.joeflashlight", "com.xidian.joe.joeflashlight.MainActivity");
            flashlightIntent.setAction(Intent.ACTION_MAIN);
            flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, flashlightIntent);
            sendBroadcast(uninstallShortcut);   // 也可以通过直接在数据库中删除的方式来进行；
        }
        else{
            Toast.makeText(this,"快捷方式不存在",Toast.LENGTH_SHORT).show();
        }

    }

    private boolean shortcutInScreen() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.cyanogenmod.trebuchet.settings/favorites"),
                null, "intent like ?", new String[]{"%component=com.xidian.joe.joeflashlight.MainActivity%"}, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }
}
