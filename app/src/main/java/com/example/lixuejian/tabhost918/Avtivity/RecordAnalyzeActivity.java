package com.example.lixuejian.tabhost918.Avtivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.lixuejian.tabhost918.R;
import com.example.lixuejian.tabhost918.Model.AllInfoHeartRecord;
import com.example.lixuejian.tabhost918.Model.AllinfoheartStatic;


import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class RecordAnalyzeActivity extends AppCompatActivity {

    private final static String TAG="RecordAnalyzeActvity提示~";

    private PieChartView pie_chart;
    //数据
    private PieChartData pieChardata;
    List<SliceValue> values = new ArrayList<SliceValue>();
    private int[] data;
    private int[] colorData = {
            Color.parseColor("#ec063d"),
            Color.parseColor("#f1c704"),
            Color.parseColor("#c9c9c9"),
            Color.parseColor("#2bc208"),
            Color.parseColor("#333333"),
            Color.parseColor("#632223")};
    private String[] stateChar = {"散步", "慢跑", "中速跑", "快跑", "冲刺", "极限跑"};

    private List<AllInfoHeartRecord> mAllRecord = new ArrayList<AllInfoHeartRecord>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordanalyze);

       // Log.i(TAG, AllinfoheartStatic.mDate.toString());
        //Log.i(TAG, AllinfoheartStatic.mHeartratePath.toString());

        int level1=60;
        int level2=65;
        int level3=70;
        int level4=75;
        int level5=80;

        int s1=0;
        int s2=0;
        int s3=0;
        int s4=0;
        int s5=0;
        int s6=0;

        for (int i=0;i<AllinfoheartStatic.mHeartratePath.size();i++){
            int a=AllinfoheartStatic.mHeartratePath.get(i).getHeartrateNumner();
            if (a<=level1){
                s1++;
            }else if (level1<a && a<=level2){
                s2++;
            }else if (level2<a && a<=level3){
                s3++;
            }else if (level3<a && a<=level4){
                s4++;
            }else if (level4<a && a<=level5){
                s5++;
            }else if (level5<a){
                s6++;
            }
        }

        Log.i(TAG,"s1:"+s1+",s2:"+s2+",s3:"+s3+",s4:"+s4+",s5:"+s5+",s6:"+s6);

        int[] data2 = {s1, s2, s3, s4, s5, s6};
        for (int i=0;i<6;i++){data[i]=data2[i];}


//        pie_chart = (PieChartView) findViewById(R.id.pie_chart);
//        pie_chart.setOnValueTouchListener(selectListener);//设置点击事件监听
//        setPieChartData();
//        initPieChart();

        Log.i(TAG,"onCreate");

    }

    /**
     * 获取数据
     */
    private void setPieChartData() {

        for (int i = 0; i < data.length; ++i) {
            SliceValue sliceValue = new SliceValue((float) data[i], colorData[i]);
            values.add(sliceValue);
        }
    }


    /**
     * 初始化
     */
    private void initPieChart() {
        pieChardata = new PieChartData();
        pieChardata.setHasLabels(true);//显示表情
        pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChardata.setHasCenterCircle(true);//是否是环形显示
        pieChardata.setValues(values);//填充数据
        pieChardata.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
        pieChardata.setCenterCircleScale(0.5f);//设置环形的大小级别
        pie_chart.setPieChartData(pieChardata);
        pie_chart.setValueSelectionEnabled(true);//选择饼图某一块变大
        pie_chart.setAlpha(0.9f);//设置透明度
        pie_chart.setCircleFillRatio(1f);//设置饼图大小

    }


    /**
     * 监听事件
     */
    private PieChartOnValueSelectListener selectListener = new PieChartOnValueSelectListener() {

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onValueSelected(int arg0, SliceValue value) {
            //选择对应图形后，在中间部分显示相应信息
            pieChardata.setCenterText1(stateChar[arg0]);
            pieChardata.setCenterText1Color(colorData[arg0]);
            pieChardata.setCenterText1FontSize(10);
            pieChardata.setCenterText2(value.getValue() + "（" + calPercent(arg0) + ")");
            pieChardata.setCenterText2Color(colorData[arg0]);
            pieChardata.setCenterText2FontSize(12);
            Toast.makeText(RecordAnalyzeActivity.this, stateChar[arg0] + ":" + value.getValue(), Toast.LENGTH_SHORT).show();
        }
    };

    private String calPercent(int i) {
        String result = "";
        int sum = 0;
        for (int i1 = 0; i1 < data.length; i1++) {
            sum += data[i1];
        }
        result = String.format("%.2f", (float) data[i] * 100 / sum) + "%";
        return result;
    }


}
