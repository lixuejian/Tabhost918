package com.example.lixuejian.tabhost918.Avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.lixuejian.tabhost918.R;

public class WelcomeActivity extends AppCompatActivity {
    private final String TAG="WelcomeActivity提示~";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.i(TAG,"onCreate");

        new Thread(new Runnable(){
            @Override
            public void run() {
                // TODO 过渡图片
                try {
                    Thread.sleep(1000);
                    shiftActivityToMain();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /*
    * 从StartActivity跳转到MainActivity
    */
    private void shiftActivityToMain(){
        Log.i(TAG,"shiftActivityToMain");
        Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
//        Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        //关闭当先界面
        WelcomeActivity.this.finish();
    }

}
