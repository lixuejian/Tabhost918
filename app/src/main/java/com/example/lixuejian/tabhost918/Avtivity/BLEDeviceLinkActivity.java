package com.example.lixuejian.tabhost918.Avtivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixuejian.tabhost918.Model.AHeartrate;
import com.example.lixuejian.tabhost918.Model.AllInfoHeartRecord;
import com.example.lixuejian.tabhost918.R;
import com.example.lixuejian.tabhost918.Services.BLEService;
import com.example.lixuejian.tabhost918.StaticInfoClass.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BLEDeviceLinkActivity extends BaseActivity {

    private final static String TAG="BLEDeviceLinkActivty提示~";
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private Handler mHandler = new Handler();
    private TextView textView;
    public Toast toast;
    private ListView lv;
    private LeDeviceListAdapter adapter;
    private BluetoothGatt gatt;
    private boolean mScanning;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    public static final String RECEIVER_ACTION = "ble_link";
    private AllInfoHeartRecord record;
    private long mStartTime;
    private long mStartTime1;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_bledevicelink);

        Log.i(TAG,"onCreate");

        textView=(TextView)findViewById(R.id.heartbeat);
        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);
        lv = (ListView) findViewById(R.id.lv);

        record = new AllInfoHeartRecord();


        /**
         * 判断当前设备是否支持ble
         */
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持BLE设备", Toast.LENGTH_SHORT).show();
            System.out.println("不支持BLE设备");
            Log.i(TAG,"不支持BLE设备");
        }

        //得到蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        }

        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(BLEDeviceLinkActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (ContextCompat.checkSelfPermission(BLEDeviceLinkActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BLEDeviceLinkActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_COARSE_LOCATION);
        }


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION);
        registerReceiver(heartRateBroadcastReceiver, intentFilter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new LeDeviceListAdapter();
        start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                scanLeDevice(true);
                lv.setAdapter(adapter);
                adapter.clear();
            }
        });

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                mStartTime = System.currentTimeMillis();
                record.setmDate(getcueDate(mStartTime));
                //把搜索到的设备信息放到listview中，然后连接设备

                BluetoothDevice device = adapter.getDevice(position);
                Constant.Constantdevice=device;
                getApplicationContext().startService(new Intent(BLEDeviceLinkActivity.this, BLEService.class));
                //gatt = device.connectGatt(getApplicationContext(), false, mGattCallback);
            }
        });

        stop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (gatt == null) {
                    return;
                }
                gatt.disconnect();
                gatt.close();
                gatt = null;
            }

        });



    }


    /**
     * 搜索蓝牙设备
     *
     * @param enable
     */
    private void scanLeDevice(boolean enable) {
        // TODO Auto-generated method stub
        if (enable) {
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(callback);
                }
            }, 10000);
            mScanning = true;
            mBluetoothAdapter.startLeScan(callback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(callback);
        }
    }

    /**
     * 蓝牙搜索的回调方法
     */
    private BluetoothAdapter.LeScanCallback callback = new LeScanCallback() {

        public void onLeScan(final BluetoothDevice device, int rssi,
                             byte[] scanRecord) {
            System.out.println(device + "****" + device.getName() + "***" + device.getAddress());
            Log.i(TAG,device + "****" + device.getName() + "***" + device.getAddress());
            runOnUiThread(new Runnable() {
                public void run() {
                    adapter.addDevice(device);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };

    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = BLEDeviceLinkActivity.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view
                        .findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view
                        .findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText("未知设备");
            viewHolder.deviceAddress.setText(device.getAddress());

            return view;
        }
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
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


    @SuppressLint("SimpleDateFormat")
    private String getcueDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd  HH:mm:ss ");
        Date curDate = new Date(time);
        String date = formatter.format(curDate);
        return date;
    }

    public void close() {
        if (gatt == null) {
            return;
        }
        gatt.close();
        gatt = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO request success
                }
                break;
        }
    }


    //监听返回按钮操作事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this, "您点击了返回按钮", Toast.LENGTH_LONG).show();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public void processMessage(Message message) {

    }
}
