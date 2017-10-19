package com.example.lixuejian.tabhost918.StaticInfoClass;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lixuejian on 2017/9/26.
 */

public class Constant {

    public static String userName = "";//用户名
    public static String userPassword = "";//用户密码
    public static int gameModel = -1;//0为征战四方模式，1为旧的争分夺秒模式（发三套题）2为新的争分夺秒模式
    public static String playerName = null;
    public static List<String> list = null;
    public static MediaPlayer player; // MediaPlayer对象
    public static boolean yinXiao = true;
    public static SoundPool soundpool;	//声明一个SoundPool对象
    public static HashMap<Integer, Integer> soundmap = new HashMap<Integer, Integer>();	//创建一个HashMap对象
    public static int jifen = 0;
    public static int guanqia = 0;

//    public static BluetoothGatt=;
    public static BluetoothDevice Constantdevice = null;

}
