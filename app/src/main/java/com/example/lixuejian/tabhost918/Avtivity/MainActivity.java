package com.example.lixuejian.tabhost918.Avtivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.example.lixuejian.tabhost918.R;

public class MainActivity extends TabActivity {
    TabHost mTabHost;
    private final String TAG="MainActivity提示~";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"onCreate");

        // 取得TabHost对象
        mTabHost = getTabHost();

        // 将类OfflineQuery赋给intent，以便连接到这个页面上
        Intent intent = new Intent().setClass(this, MapActivity.class);
        mTabHost.addTab(mTabHost.newTabSpec("tab1")                              // 新建一个newTabSpec(newTabSpec)
                //.setIndicator("", getResources().getDrawable(R.drawable.img1))   // 设置其标签和图标(setIndicator)
                .setIndicator("我的")   // 设置其标签和图标(setIndicator)
                .setContent(intent));                                            // 设置内容(setContent)


        // 将类OnlineQuery赋给intent，以便连接到这个页面上
        Intent intent1 = new Intent().setClass(this, HeartrateActivity.class);
        mTabHost.addTab(mTabHost.newTabSpec("tab2")
                //.setIndicator("", getResources().getDrawable(R.drawable.img2))
                .setIndicator("Ta的")   // 设置其标签和图标(setIndicator)
                .setContent(intent1));

        // 将类NoteBook赋给intent，以便连接到这个页面上
        Intent intent2 = new Intent().setClass(this, UserActivity.class);
        mTabHost.addTab(mTabHost.newTabSpec("tab3")
                //.setIndicator("", getResources().getDrawable(R.drawable.img3))
                .setIndicator("账户")   // 设置其标签和图标(setIndicator)
                .setContent(intent2));

        mTabHost.setCurrentTab(0);

    }
}
