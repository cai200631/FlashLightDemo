package com.xidian.joe.joeflashlight;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/19.
 */
public class Morse extends WarningLight {
    private final int DOT_TIME = 200; // 点200ms
    private final int LINE_TIME = 600; // 线600ms
    private final int DOT_LINE_TIME = 200; // 点-线200ms
    private final int CHAR_CHAR_TIME = 600;  //字符-字符 600ms
    private final int WORD_WORD_TIME = 1400; //单词-单词 1400ms

    private String mMorseCode;
    private Map<Character, String> mMorseCodeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMorseCodeMap.put('a', ".-");
        mMorseCodeMap.put('b', "-...");
        mMorseCodeMap.put('c', "-.-.");
        mMorseCodeMap.put('d', "-..");
        mMorseCodeMap.put('e', ".");
        mMorseCodeMap.put('f', "..-.");
        mMorseCodeMap.put('g', "--.");
        mMorseCodeMap.put('h', "....");
        mMorseCodeMap.put('i', "..");
        mMorseCodeMap.put('j', ".---");
        mMorseCodeMap.put('k', "-.-");
        mMorseCodeMap.put('l', ".-..");
        mMorseCodeMap.put('m', "--");
        mMorseCodeMap.put('n', "-.");
        mMorseCodeMap.put('o', "---");
        mMorseCodeMap.put('p', ".--.");
        mMorseCodeMap.put('q', "--.-");
        mMorseCodeMap.put('r', ".-.");
        mMorseCodeMap.put('s', "...");
        mMorseCodeMap.put('t', "-");
        mMorseCodeMap.put('u', "..-");
        mMorseCodeMap.put('v', "...-");
        mMorseCodeMap.put('w', ".--");
        mMorseCodeMap.put('x', "-..-");
        mMorseCodeMap.put('y', "-.--");
        mMorseCodeMap.put('z', "--..");

        mMorseCodeMap.put('0', "-----");
        mMorseCodeMap.put('1', ".----");
        mMorseCodeMap.put('2', "..---");
        mMorseCodeMap.put('3', "...--");
        mMorseCodeMap.put('4', "....-");
        mMorseCodeMap.put('5', ".....");
        mMorseCodeMap.put('6', "-....");
        mMorseCodeMap.put('7', "--...");
        mMorseCodeMap.put('8', "---..");
        mMorseCodeMap.put('9', "----.");
    }

    public void onClick_SendMorse(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Toast.makeText(this,"当前设备没有闪光灯", Toast.LENGTH_LONG).show();
            return;
        }
        if(verifyMorseCode()){
            sendSentense(mMorseCode);
        }

    }

    protected void sleep(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendDot() {
        openFlashlight();
        sleep(DOT_TIME);
        closeFlashlight();
    }

    private void sendLine() {
        openFlashlight();
        sleep(LINE_TIME);
        closeFlashlight();
    }

    private void sendChar(char c) {
        String morseCode = mMorseCodeMap.get(c);
        if (morseCode != null) {
            char lastChar = ' ';
            for (int i = 0; i < morseCode.length(); i++) {
                char dotLine = morseCode.charAt(i);
                if (dotLine == '.') {
                    sendDot();
                } else if (dotLine == '-') {
                    sendLine();
                }
                if (i < morseCode.length() - 1) {
                    if (lastChar == '.' && dotLine == '-') {
                        sleep(DOT_LINE_TIME);
                    }
                }
                lastChar = dotLine;
            }
        }
        openFlashlight();
        sleep(LINE_TIME);
        closeFlashlight();
    }

    private void sendWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sendChar(c);
            if (i < s.length() - 1) {
                sleep(CHAR_CHAR_TIME);
            }
        }
    }

    private void sendSentense(String s) {
        String[] words = s.split(" +");
        for (int i = 0; i < words.length; i++) {
            sendWord(words[i]);
            if (i < words.length - 1) {
                sleep(WORD_WORD_TIME);
            }
        }
        Toast.makeText(this, "摩斯电码已经发送完成", Toast.LENGTH_LONG).show();
    }

    private boolean verifyMorseCode() {
        mMorseCode = mEditTextMorseCode.getText().toString().toLowerCase();
        if (mMorseCode.length() == 0) {
            Toast.makeText(this, "请输入摩斯电码字符串", Toast.LENGTH_LONG).show();
            return false;
        }
        for (int i = 0; i < mMorseCode.length(); i++) {
            char c = mMorseCode.charAt(i);
            if (!(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9') && c != ' ') {
                Toast.makeText(this, "摩斯电码只能是字母和数字！", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
}
