package com.example.secondapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ProgressBar pb;
    Resources res;
    Button bt;
    int ct;
    int ctMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = getResources();
        ctMax = res.getInteger(R.integer.countMax);
        ct = 0;
        textView = (TextView)findViewById(R.id.textView1);
        textView.setText("カウント：" + ct);
        pb = (ProgressBar)findViewById(R.id.progressBar);
        bt = (Button)findViewById(R.id.button2);
        bt.setEnabled(false);
    }

    public void btnCountUP(View t) {
        if(ct < ctMax){
            ct++;
        }
        else{
            bt.setEnabled(true);
        }
        textView.setText("カウント："+ ct);
        pb.setProgress(ct);
        Log.d("btn", "押された");
    }
    public void btnCountDOWN(View t) {
        if(ct > 0){
            ct--;
        }
        textView.setText("カウント："+ ct);
        pb.setProgress(ct);
        Log.d("btn", "押された");
    }

    public void move(View t){
        Intent intent = new Intent(MainActivity.this, SubActivity.class);
        startActivity(intent);
    }
}
