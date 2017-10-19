package com.example.lixuejian.tabhost918.Avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixuejian.tabhost918.Adapter.RecordAdapter;
import com.example.lixuejian.tabhost918.R;
import com.example.lixuejian.tabhost918.StaticInfoClass.Config;
import com.example.lixuejian.tabhost918.StaticInfoClass.Constant;
import com.example.lixuejian.tabhost918.Util.HeartrateUtil;
import com.example.lixuejian.tabhost918.uploadHeartrate.AHeartrate;
import com.example.lixuejian.tabhost918.uploadHeartrate.AllInfoHeartRecord;
import com.example.lixuejian.tabhost918.uploadHeartrate.AllinfoheartStatic;

import java.util.ArrayList;
import java.util.List;

public class ShowHeartrateInfoActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private final static String TAG="ShowHertreInfctivy提示~";

    private RecordAdapter mAdapter;
    private ListView mAllRecordListView;
    private List<AllInfoHeartRecord> mAllRecord = new ArrayList<AllInfoHeartRecord>();
    private List<AllInfoHeartRecord> recordList;
    public static final String RECORD_ID = "record_id";
    private Button button1;
    private AllInfoHeartRecord allInfoHeartRecord=new AllInfoHeartRecord();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showheartrateinfo);

        Log.i(TAG,"onCreate");




        button1=(Button)findViewById(R.id.button1) ;
//        mAllRecordListView = (ListView) findViewById(R.id.recordlist);


//        con.getPeriodHeartrateRecord(Constant.userName,17); //查询一段时间的心率记录
        con.getNewestHeartrateRecord(Constant.userName);//查询一条心率记录

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent mIntent = new Intent(ShowHeartrateInfoActivity.this,TestActivity.class);
                startActivity(mIntent);
//                mAllRecord.add(allInfoHeartRecord) ;
//                mAdapter = new RecordAdapter(ShowHeartrateInfoActivity.this, mAllRecord);
//                mAllRecordListView.setAdapter(mAdapter);
//                mAllRecordListView.setOnItemClickListener(ShowHeartrateInfoActivity.this);
            }
        });






    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    //public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        PathRecord xml_recordadapter_recorditem = (PathRecord) parent.getAdapter().getItem(position);
//


        Intent mIntent = new Intent(ShowHeartrateInfoActivity.this,TestActivity.class);
        startActivity(mIntent);
        //finish();

    }



    @Override
    public void processMessage(Message message) {
        switch(message.what){
            case Config.REQUEST_GET_ONE_RECORDS:
                try {
                    String result= message.obj.toString();
//                    int max=message.arg1;
                    Log.i(TAG,"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    Log.i(TAG,"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    Log.i(TAG,result);
                    Log.i(TAG+"1",HeartrateUtil.parseAllInfoHeartRecords2List(result).toString());

                    allInfoHeartRecord.setmHeartratePath(HeartrateUtil.parseAllInfoHeartRecords2List(result));
//                    allInfoHeartRecord.setmMaxHeartrate(max);
                    AllinfoheartStatic.mHeartratePath=allInfoHeartRecord.getmHeartratePath();
                    AllinfoheartStatic.mMaxHeartrate=allInfoHeartRecord.getmMaxHeartrate();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                break;
//            case Config.REQUEST_GET_ONE_RECORDS2:
//                try {
//                    String start= message.obj.toString();
//                    int min=message.arg1;
//                    Log.i(TAG+"2",start);
//                    Log.i(TAG+"2",HeartrateUtil.parseOneHeartrateSTR2OneaHeartrate(start).toString());
//                    allInfoHeartRecord.setmStartPoint(HeartrateUtil.parseOneHeartrateSTR2OneaHeartrate(start));
//                    allInfoHeartRecord.setmMinHeartrate(min);
//                    AllinfoheartStatic.mStartPoint=allInfoHeartRecord.getmStartPoint();
//                    AllinfoheartStatic.mMinHeartrate=allInfoHeartRecord.getmMinHeartrate();
//
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case Config.REQUEST_GET_ONE_RECORDS3:
//                try {
//                    String end= message.obj.toString();
//                    int avarage=message.arg1;
//                    Log.i(TAG+"3",end);
//                    Log.i(TAG+"2",HeartrateUtil.parseOneHeartrateSTR2OneaHeartrate(end).toString());
//                    allInfoHeartRecord.setmEndPoint(HeartrateUtil.parseOneHeartrateSTR2OneaHeartrate(end));
//                    allInfoHeartRecord.setmAvarage(avarage);
//                    AllinfoheartStatic.mAvarage=allInfoHeartRecord.getmAvarage();
//                    AllinfoheartStatic.mEndPoint=allInfoHeartRecord.getmEndPoint();
//
//
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case Config.REQUEST_GET_ONE_RECORDS4:
//                try {
//                    String duration= message.obj.toString();
//                    allInfoHeartRecord.setmMDuration(duration);
//                    AllinfoheartStatic.mMDuration=allInfoHeartRecord.getmMDuration();
//
//
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
            case Config.REQUEST_GET_ONE_RECORDS5:
                try {
                    String date= message.obj.toString();
                    allInfoHeartRecord.setmDate(date);
                    AllinfoheartStatic.mDate=allInfoHeartRecord.getmDate();

                }catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }// switch
    }
}
