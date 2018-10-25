package com.example.secondapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.icu.util.Calendar;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class SubActivity extends AppCompatActivity {
    TextView textview;
    public TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        tts = new TextToSpeech(this,null);
        textview = (TextView)findViewById(R.id.timeLabel);
        textview.setText("現在時刻");
    }

    public void speech(View v){
        tts.speak(textview.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    public void srbtn(View v){
        //インテント作成
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);


        //表示させる文字列
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"音声を文字で出力します。");
        //インテント開始
        startActivityForResult(intent,1234);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //自分が投げたインテントであれば応答する
        if(requestCode == 1234 && resultCode == RESULT_OK){
            //すべての結果を配列に受け取る
            ArrayList<String> speechToChar = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenString = "";
            //ここでは、認識結果が複数あった場合に結合している。
            for(int i = 0; i < speechToChar.size() ; i++){
                spokenString += speechToChar.get(i) + "\n";
            }
            textview.setText(spokenString);
            //結果に”時刻”が含まれているか
            if(spokenString.indexOf("時刻") != -1){
                java.util.TimeZone tzn = java.util.TimeZone.getTimeZone("Asia/Tokyo");
                final Date date = new Date(System.currentTimeMillis());
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(date);
                calendar.setTimeZone(tzn);
                int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
                int min = calendar.get(java.util.Calendar.MINUTE);
                int sec = calendar.get(java.util.Calendar.SECOND);
                textview.setText("現在時刻："+ hour + "時" + min + "分" + sec + "秒");
            }
            else {
                textview.setText("「時刻」が含まれていません");
            }
            //トーストで表示
            Toast.makeText(this, spokenString, Toast.LENGTH_LONG).show();
            //ダイアログ表示
//            showDialog(this,"",spokenString);
            super.onActivityResult(requestCode,resultCode,data);// お決まり
        }
    }

    private void showDialog(final Activity activity, String title, String text){
        AlertDialog.Builder ad = new AlertDialog.Builder(activity);
        ad.setTitle(title);
        ad.setMessage(text);
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.setResult(Activity.RESULT_OK);
            }
        });
        ad.create();
        ad.show();
    }

    public void finishSub(View v) {
        finish();
    }

}
