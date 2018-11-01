package com.example.secondapplication;

import android.accessibilityservice.AccessibilityService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class AccessibillityVoiceService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo accessibilityNodeInfo = event.getSource();
        if (null == accessibilityNodeInfo)
            return;

        String className = accessibilityNodeInfo.getClassName().toString(); //!< 音声認識の判断クラス
        final CharSequence text = accessibilityNodeInfo.getText();  //!< 音声認識に登録されたテキスト
        if(-1 == className.indexOf("com.google.android.apps.gsa.searchplate") || null == text)
            return;

        Toast.makeText(getApplicationContext(), text.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInterrupt() {
    }
}