package com.example.lixuejian.tabhost918.Avtivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.lixuejian.tabhost918.R;

public class UserActivity extends BaseActivity {
//    TextView mt;
//    private final String TAG="UserActivity提示~";
//    private MapView mapView;
//    private MapView mMapView;
//    private AMap mAMap;
//    private AMap aMap;
//    private Marker mOriginStartMarker;
//    private LocationSource.OnLocationChangedListener mListener;
//    private AMapLocationClient mlocationClient;
//    private AMapLocationClientOption mLocationOption;
//    private RadioGroup mGPSModeGroup;
//    private Marker mGraspStartMarker, mGraspEndMarker, mGraspRoleMarker;
//
//    private TextView text_heartrate;
//    private TextView text_bloodpressure;
//    private TextView text_bloodsugar;
//
//    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
//    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
//    public static final String RECEIVER_ACTION = "ble_link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

//        mt= (TextView) findViewById(R.id.start_textView);
//        mt.setText("activity_user");
//
//
//        mapView = (MapView) findViewById(R.id.map2);
//        mapView.onCreate(savedInstanceState);
//
//        init();
//
//con.getScore("bbb");
//
//
//        AMapLocation location = null;
//        location = new AMapLocation("gps");
//        location.setLatitude(111.111);
//        location.setLongitude(31.1111);
//
//        AMapLocation startLoc;
//
//        LatLng startPoint=new LatLng(location.getLatitude(),
//                location.getLongitude());
//
//        mOriginStartMarker = mAMap.addMarker(new MarkerOptions().position(
//                startPoint).icon(
//                BitmapDescriptorFactory.fromResource(R.drawable.poi_marker_pressed)));

    }


//    /**
//     * 初始化AMap对象
//     */
//    private void init() {
//        if (mAMap == null) {
//            mAMap = mMapView.getMap();
//           // mAMap.setOnMapLoadedListener(UserActivity.this);
//        }
//
//    }



    @Override
    public void processMessage(Message message) {

    }
}