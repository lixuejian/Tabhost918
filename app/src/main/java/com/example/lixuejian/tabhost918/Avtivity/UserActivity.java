package com.example.lixuejian.tabhost918.Avtivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.example.lixuejian.tabhost918.R;
import com.example.lixuejian.tabhost918.StaticInfoClass.Config;
import com.example.lixuejian.tabhost918.StaticInfoClass.Constant;
import com.example.lixuejian.tabhost918.Util.WeiboDialogUtils;

public class UserActivity extends BaseActivity {

    private static final String TAG="UserActivity提示：";

    MapView mMapView = null;
    private AMap aMap;
    private String friendname="bbb";
    private Dialog mWeiboDialog;

/*    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    break;
            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Log.i(TAG,"onCreate");

        mMapView = (MapView) findViewById(R.id.useractivity_map_map);

        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        con.getFriendInfo(Constant.userName,friendname);

        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(UserActivity.this, "加载中...");
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void processMessage(Message message) {
        switch(message.what){
            case Config.REQUEST_LOGIN:
                // TODO: 2017/10/20 待修改 
                int result = message.arg1;
                Log.i(TAG,"UserActivity的processmessage的登录请求回执为"+"result="+result);

                LatLng latLng = new LatLng(39.906901,116.397972);
                final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("关心人位置").snippet("DefaultMarker"));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

               // mHandler.sendEmptyMessageDelayed(1, 2000);
                WeiboDialogUtils.closeDialog(mWeiboDialog);

                break;
            default:
                break;
        }

    }
}