package com.example.lixuejian.tabhost918.Avtivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.lixuejian.tabhost918.R;
import com.example.lixuejian.tabhost918.StaticInfoClass.Constant;
import com.example.lixuejian.tabhost918.Model.AHeartrate;
import com.example.lixuejian.tabhost918.Model.AllInfoHeartRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HeartrateActivity extends BaseActivity {
    private final static String TAG="HeartrateActivity提示~";
    Button b_scandevice;
    Button b_getheartratetecord;
    ToggleButton b_startOrStopRecord;
    private AllInfoHeartRecord record;
    public static final String RECEIVER_ACTION = "ble_link";
    private long mStartTime;
    private long mStartTime1;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate);

        Log.i(TAG,"onCreate");

        //record = new AllInfoHeartRecord();

        //mt.setText("未检测心率");
        b_scandevice=(Button) findViewById(R.id.b_scandevice);
        b_getheartratetecord=(Button) findViewById(R.id.b_getheartraterecord);
        b_startOrStopRecord=(ToggleButton) findViewById(R.id.b_startOrStopRecord);

        b_scandevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "点击了扫描设备按钮", Toast.LENGTH_SHORT).show();
                Log.i(TAG,"点击了扫描设备按钮");
                Intent intent = new Intent(HeartrateActivity.this, BLEDeviceLinkActivity.class);
                startActivity(intent);
            }
        });


        b_startOrStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b_startOrStopRecord.isChecked()) {   //创建新的记录
                    Log.i(TAG,"按钮按下，开始记录");

                    record = new AllInfoHeartRecord();
                    IntentFilter intentFilter = new IntentFilter();
                    // TODO: 2017/10/11 添加action
                    intentFilter.addAction(RECEIVER_ACTION);
                    registerReceiver(heartRateBroadcastReceiver, intentFilter);
                    mStartTime = System.currentTimeMillis();
                    record.setmDate(getcueDate(mStartTime));


                } else {        //结束记录并存储
                    Log.i(TAG,"按钮按下，停止记录");
                    mEndTime = System.currentTimeMillis();
                    saveRecord(record.getmHeartratePath(), record.getmDate());

                    unregisterReceiver(heartRateBroadcastReceiver);
                }
            }
        });

        b_getheartratetecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"点击了获取心率记录按钮");
                Intent intent2ShowHeartrateInfo = new Intent(HeartrateActivity.this,ShowHeartrateInfoActivity.class);
                startActivity(intent2ShowHeartrateInfo);

            }
        });

    }


    //存储记录
    protected void saveRecord(List<AHeartrate> list, String time) {
        if (list != null && list.size() > 0) {
            //DbHepler = new DbAdapter(this);
            // DbHepler.open();
            int mAvarage = getmAvarage(list);
            int mMaxHeartrate = getmMaxHeartrate(list);
            int mMinHeartrate = getMinHeartrate(list);
            String mMDuration = getmMDuration();
            AHeartrate firstHeartrate = list.get(0);
            AHeartrate lastHeartrate = list.get(list.size() - 1);

            String heartraterecordString = getHeartrateRecordString(list);

            AllInfoHeartRecord allInfoHeartRecord=new AllInfoHeartRecord();

            mStartTime = System.currentTimeMillis();

            allInfoHeartRecord.setmHeartratePath(list);
            allInfoHeartRecord.setmAvarage(mAvarage);
            allInfoHeartRecord.setmMaxHeartrate(mMaxHeartrate);
            allInfoHeartRecord.setmMinHeartrate(mMinHeartrate);
            allInfoHeartRecord.setmStartPoint(list.get(0));
            allInfoHeartRecord.setmEndPoint(list.get(list.size() - 1));
            allInfoHeartRecord.setmDate(getcueDate(mStartTime));

            Log.i(TAG,"mAvarage为："+mAvarage);
            Log.i(TAG,"mMaxHeartrate："+mMaxHeartrate);
            Log.i(TAG,"mMinHeartrate："+mMinHeartrate);
            Log.i(TAG,"mMDuration为："+mMDuration);
            Log.i(TAG,"heartraterecordString为："+heartraterecordString);
            con.uploadHeartrate(
                    Constant.userName,
                    amapLocationToString(allInfoHeartRecord.getmStartPoint()),
                    amapLocationToString(allInfoHeartRecord.getmEndPoint()),
                    heartraterecordString,
                    allInfoHeartRecord.getmAvarage(),
                    allInfoHeartRecord.getmMaxHeartrate(),
                    allInfoHeartRecord.getmMinHeartrate(),
                    mMDuration,
                    allInfoHeartRecord.getmDate()
                    );
//            String stratpoint = amapLocationToString(firstLocaiton);
//            String endpoint = amapLocationToString(lastLocaiton);
//            DbHepler.createrecord(String.valueOf(distance), duration, average,
//                    pathlineSring, stratpoint, endpoint, time);
//            DbHepler.close();
        } else {
            Toast.makeText(HeartrateActivity.this, "没有记录到记录", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void processMessage(Message message) {


    }


    //把定位点转化为string
    private String amapLocationToString(AHeartrate location) {
        StringBuffer locString = new StringBuffer();
        locString.append(location.getHeartrateNumner()).append(",");
        locString.append(location.getHeaterateTime()).append(",");
        return locString.toString();
    }


    //得到持续时间
    private String getmMDuration() {
        return String.valueOf((mEndTime - mStartTime) / 1000f);
    }

    //获取平均心率
    private int getmAvarage(List<AHeartrate> list) {

        int allrate = 0;
        if (list == null || list.size() == 0) {
            return allrate;
        }
        for (int i = 0; i <=list.size() - 1; i++) {
            allrate=allrate+list.get(i).getHeartrateNumner();
        }
        Log.i(TAG,allrate+"----"+list.size());
        Log.i(TAG,allrate+"----"+list.size());
        Log.i(TAG,allrate+"----"+list.size());
        Log.i(TAG,allrate+"----"+list.size());
        return Integer.valueOf(allrate /( list.size() ));
    }

    //获取最大心率
    private int getmMaxHeartrate(List<AHeartrate> list){
        int maxheartratenum=0;

        if (list == null || list.size() == 0) {
            return maxheartratenum;
        }

        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getHeartrateNumner()>maxheartratenum)
                maxheartratenum=list.get(i).getHeartrateNumner();
        }
        return maxheartratenum;
    }

    //获取最小心率
    private int getMinHeartrate(List<AHeartrate> list){
        int minheartratenum=255;

        if (list == null || list.size() == 0) {
            return minheartratenum;
        }

        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getHeartrateNumner()<minheartratenum)
                minheartratenum=list.get(i).getHeartrateNumner();
        }
        return minheartratenum;
    }

    //把记录路径的list转化为string
    private String getHeartrateRecordString(List<AHeartrate> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuffer pathline = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            AHeartrate location = list.get(i);
            String locString = amapLocationToString(location);
            pathline.append(locString).append(";");
        }
        String pathLineString = pathline.toString();
        pathLineString = pathLineString.substring(0,
                pathLineString.length() - 1);
        return pathLineString;
    }


    @SuppressLint("SimpleDateFormat")
    private String getcueDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd  HH:mm:ss ");
        Date curDate = new Date(time);
        String date = formatter.format(curDate);
        return date;
    }


    /**
     * 返回一个BroadcastReceiver类
     */
    private BroadcastReceiver heartRateBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i(TAG,"heartRateBroadcastReceiver");

            String action = intent.getAction();
            if (action.equals(RECEIVER_ACTION)) {
                // TODO: 2017/10/11 广播接收待修改
                String heartrateResult = intent.getStringExtra("result");
                if (null != heartrateResult && !heartrateResult.trim().equals("")) {
                    Log.i(TAG,"心率为："+heartrateResult+"！！！！！！！！！！！！！！！！！");
                    AHeartrate aHeartrate=new AHeartrate();
                    aHeartrate.setHeartrateNumner(Integer.valueOf(heartrateResult).intValue());
                    mStartTime1 = System.currentTimeMillis();
                    aHeartrate.setHeaterateTime(getcueDate(mStartTime1));
                    //record.setDate(getcueDate(mStartTime));
                    record.addpoint(aHeartrate);





                }
            }
        }
    };


}
