package com.example.lixuejian.tabhost918.Services;

import android.app.IntentService;
import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.lixuejian.tabhost918.Avtivity.BLEDeviceLinkActivity;
import com.example.lixuejian.tabhost918.StaticInfoClass.BleDefinedUUIDs;
import com.example.lixuejian.tabhost918.StaticInfoClass.Constant;

import java.util.UUID;

/**
 * Created by lixuejian on 2017/10/11.
 */

public class BLEService extends Service {

    Handler mHandler = new Handler();

    private static final String TAG = "BLEService提示：";
    final static private UUID mHeartRateServiceUuid = BleDefinedUUIDs.Service.HEART_RATE;
    final static private UUID mHeartRateCharacteristicUuid = BleDefinedUUIDs.Characteristic.HEART_RATE_MEASUREMENT;
    private BluetoothGatt gatt;

    @Override
    public void onCreate() {
        Log.i(TAG,"onCreate");
        gatt = Constant.Constantdevice.connectGatt(getApplicationContext(), false, mGattCallback);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 寻找服务
     */
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        private BluetoothGattCharacteristic mCharacteristic;

        /**
         * 当连接状态发生改变的时候回调的方法
         */
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            //判断蓝牙连接状态
            if (newState == BluetoothProfile.STATE_CONNECTED) {

                new Thread(){
                    public void run() {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "设备已连接", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }.start();
                //Toast.makeText(getApplicationContext(), "设备已连接", Toast.LENGTH_LONG).show();
                System.out.println("设备已连接");
                Log.i(TAG,"设备已连接");
                //寻找设备中的服务
                gatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                //Toast.makeText(BLEDeviceLinkActivity.this, "设备已断开连接", Toast.LENGTH_SHORT).show();
                System.out.println("设备已断开连接");
                Log.i(TAG,"设备已断开连接");
            }
        }
        /**
         * 当服务发现后所调用的方法
         */
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                System.out.println("Services discovered");
                Log.i(TAG,"onServicesDiscovered-Services discovered");
                //得到心率信息的service
                BluetoothGattService service = gatt.getService(mHeartRateServiceUuid);
                if (service == null) {
                    Toast.makeText(getApplicationContext(), "没有得到心率服务", Toast.LENGTH_SHORT).show();
                    System.out.println("没有得到心率服务");
                    Log.i(TAG,"没有得到心率服务！");
                } else {
                        /*toast=Toast.makeText(BLEDeviceLinkActivity.this, "得到心率服务", Toast.LENGTH_LONG );
                        toast.show();*/
                    System.out.println("得到心率服务");
                    Log.i(TAG,"得到心率服务！");
                    mCharacteristic = service.getCharacteristic(mHeartRateCharacteristicUuid);
                    if (mCharacteristic == null) {
                        Toast.makeText(getApplicationContext(), "不能找到心率", Toast.LENGTH_SHORT).show();
                        System.out.println("不能找到心率");
                        Log.i(TAG,"不能找到心率！");
                    } else {
                        boolean success = gatt.setCharacteristicNotification(mCharacteristic, true);//对应后边的onCharacteristicChanged
                        if (!success) {
                            System.out.println("Enabling notification failed!");
                            Log.i(TAG,"Enabling notification failed！");
                            return;
                        }

                        BluetoothGattDescriptor descriptor = mCharacteristic
                                .getDescriptor(BleDefinedUUIDs.Descriptor.CHAR_CLIENT_CONFIG);
                        if (descriptor != null) {
                            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                            gatt.writeDescriptor(descriptor);
                            System.out.println("Notification enabled");
                            Log.i(TAG,"Notification enabled");
                        } else {
                            System.out.println("Could not get descriptor for characteristic! Notification are not enabled.");
                            Log.i(TAG,"Could not get descriptor for characteristic! Notification are not enabled.");
                        }
                    }
                }
            } else {
                System.out.println("Unable to discover services");
                Log.i(TAG,"Unable to discover services");
            }
        }

        /**
         * 当service里边的characteristic发生改变调用
         */
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            //得到心率数据
            if(characteristic.equals(mCharacteristic)){

                byte[] raw = mCharacteristic.getValue();
                System.out.println("心率****="+raw);

                int index = ((raw[0] & 0x01) == 1) ? 2 : 1;
                int format = (index == 1) ? BluetoothGattCharacteristic.FORMAT_UINT8 : BluetoothGattCharacteristic.FORMAT_UINT16;
                int value = mCharacteristic.getIntValue(format, index);
                final String description = String.valueOf(value);
                // TODO: 2017/10/11 发送广播

                sendHeartRateBoradcast(description);

                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("心率："+description);
                        Log.i(TAG,"心率****="+description);
                        //textView.setText(description);
                    }
                });*/

            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic, int status) {
        };

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        };
    };

    private void sendHeartRateBoradcast(String heartratenumber) {
        Log.i(TAG,"sendHeartRateBoradcast");
        Log.i(TAG,"心率为："+heartratenumber);
        //记录信息并发送广播
        //long callBackTime = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        sb.append(heartratenumber);
        if (null == heartratenumber) {
            sb.append("获取心率失败：is null!!!!!!!");
        } else {
            //sb.append(Utils.getLocationStr(aMapLocation));
        }

        Intent mIntent = new Intent(BLEDeviceLinkActivity.RECEIVER_ACTION);
        mIntent.putExtra("result", sb.toString());

        //发送广播
        sendBroadcast(mIntent);
    }
    

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return null;
    }

//    private void showToastByRunnable(final IntentService context, final CharSequence text, final int duration)     {
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(context, text, duration).show();
//            }
//        });
//    }

}
