package com.example.lixuejian.tabhost918.Network;

import android.os.Message;
import android.util.Log;

import com.example.lixuejian.tabhost918.Avtivity.BaseActivity;
import com.example.lixuejian.tabhost918.StaticInfoClass.Config;
import com.example.lixuejian.tabhost918.StaticInfoClass.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuejian on 2017/9/25.
 */

public class NetWorker extends Thread {
    // Context context;
    String TAG = "NetWorker提示~";
    private static final String IP = "10.52.15.107";
    private static final int PORT = 6788;

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    int dataType;
    int flag = 0;

    private Boolean onWork = true;
    protected final byte connect = 1;
    protected final byte running = 2;
    protected byte state = connect;

    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        Log.i(TAG,"------------------------------------------------------------------");
        Log.i(TAG,"run启动第一步");

        while (onWork) {

            switch (state) {
                case connect:
                    Log.i(TAG,"run启动state为connect");
                    connect();
                    break;
                case running:
                    Log.i(TAG,"run启动state为running");
                    receiveMsg();
                    break;
            }

        }
    }

    private void connect() {
        try {
            Log.i(TAG,"connect方法启动");
            Log.i(TAG,"向IP为："+IP+"端口号为："+PORT+"发出连接请求");
            socket = new Socket(IP, PORT);
            Log.i(TAG, "连接到服务器啦");
            state = running;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "UTF-8"));

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void receiveMsg() {
        Log.i(TAG, "一直在等待接受服务器返回的信息！");
        try {
            String msg = in.readLine();
            Log.i(TAG, "从服务器返回的消息是：" + msg);
            jsonObject = new JSONObject(msg);
            dataType = jsonObject.getInt("requestType");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 登录
        if (dataType == Config.REQUEST_LOGIN) {
            handLogin();

        }
        // 快速登录
        else if (dataType == Config.REQUEST_QUICK_LOGIN) {
            handregisterq();
        }
        // 注册
        else if (dataType == Config.REQUEST_REGISTER) {
            handRegister();
        }
        // 退出
        else if (dataType == Config.REQUEST_EXIT) {
            Message msg = new Message();
            int num = 7;
            msg.what = num;
            BaseActivity.sendMessage(msg);
        }
        // 获取道具商城
        else if (dataType == Config.REQUEST_GET_PROP) {
            handGetshop();
        }
        else if (dataType == Config.REQUEST_GET_ONE_RECORDS) {
            handgetOneNewHeartraterecord();
        }
        // 获取道具修改
        else if (dataType == Config.REQUEST_MODIFY_PROP) {
            handChangshop();
        }

        // 判断获取在线玩家
        else if (dataType == Config.REQUEST_GET_USERS_ONLINE) {
            handPlayerList();
        }
        // 判断类型为获取好友，在进行处理
        else if (dataType == Config.REQUEST_ADD_FRIEND) {
            handAddFriend();

        }

        else if (dataType == Config.REQUEST_GET_PERIOD_RECORDS) {
            handgetPeriodHeartraterecord();
        }
        //判断类型是否为邀战请求
        else if(dataType == Config.REQUEST_SEND_INVITE){

            handYaoZhan();
        }
        //判断对方否接收请求
        else if(dataType == Config.REQUEST_INVITE_RESULT){

            handInviteResult();
        }
        // 判断获取好友列表
        else if (dataType == Config.REQUEST_GET_FRIEND) {
            handFriendList();
        }
        //获取成语
        else if(dataType ==  Config.REQUEST_GET_SUBJECT){
            handGetChengYu();
        }
        //获取好友的积分
        else if(dataType ==Config.REQUEST_ADD_PLAYERSCORE){
            handAddPlayerScore();
        }
        //获取积分
        else if(dataType == Config.REQUEST_GET_SCORES){
            handGetSocre();
        }
        //获取PK结果
        else if(dataType == Config.REQUEST_PK_RESULT){
            handPKResult();
        }
        //返回游戏中退出游戏的请求
        else if(dataType == Config.REQUEST_EXIT_GAME){
            handExitGameActivity();
        }else if(dataType == Config.LLK_GET_WEI_XIN){
            getWeiXin_network();
        }
    }

    // 登录
    public void login(String userName, String password) {

        Log.i(TAG,"想服务器发送login请求"+"username:"+userName+" password:"+password);
        // JSOn
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_LOGIN);
            jo.put("username", userName);
            jo.put("password", password);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i(TAG, "发送登录的请求为：" + jo.toString());

        out.println(jo.toString());
    }


    // 登录
    public void getAddress(String userName,String friendname) {

        Log.i(TAG,"想服务器发送地址请求"+"username:"+userName);
        // JSOn
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_ADDRESS);
            jo.put("username", userName);
            jo.put("friendname", friendname);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i(TAG, "发送登录的请求为：" + jo.toString());

        out.println(jo.toString());
    }
    // 登录
    public void handgetAddress( ) {

        //Log.i(TAG,"想服务器发送地址请求"+"username:"+userName);
        // JSOn
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_ADDRESS);
            //jo.put("username", userName);
           // jo.put("friendname", friendname);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i(TAG, "发送登录的请求为：" + jo.toString());

        out.println(jo.toString());
    }


    // 传递登录
    public void handLogin() {
        Log.i(TAG, "传递从服务器端返回的'登录'的请求");
        int result = 0;
        try {
            result = jsonObject.getInt("result");
            Message msg = new Message();
            msg.arg1 = result;
            msg.what = Config.REQUEST_LOGIN;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 快速登录
    public void registerq() {

        System.out.println("发送--快速登录--的请求");
        JSONObject jo = new JSONObject();
        try {
            System.out.println("发送--快速登录--的请求");
            jo.put("requestType", Config.REQUEST_QUICK_LOGIN);
            System.out.println("发送--快速登录--的请求");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("发送--快速登录--的请求");
        out.println(jo.toString());
        Log.i(TAG, "发送快速登录请求为：" + jo.toString());

    }

    // 传递快速登录
    private void handregisterq() {
        Log.i(TAG, "传递从服务器端返回的快速登录的请求");
        System.out.println("传递从服务器端返回的快速登录的请求");

        try {
            // int result = jsonObject.getInt("result");
            // if (result == Config.SUCCESS) {
            Constant.userName = jsonObject.getString("username");
            Constant.userPassword = jsonObject.getString("password");
            System.out.println(Constant.userName + "!!!!!!!!!!!!"
                    + Constant.userPassword);
            Message msg = new Message();
            msg.what = Config.REQUEST_QUICK_LOGIN;
            BaseActivity.sendMessage(msg);
            // } else if(result == Config.FAIl){
            // registerq();
            // }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 注册
    public void register(String userName,String password,int age,int sex,int sportlevel) {
        Log.i(TAG, "发送注册的请求dd");
        System.out.println("发送注册的请求dd");
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_REGISTER);
            jo.put("username", userName);
            jo.put("password", password);
            jo.put("age", age);
            jo.put("sex", sex);
            jo.put("sportlevel", sportlevel);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送注册的请求为：" + jo.toString());
        System.out.println("发送注册的请求为：" + jo.toString());
    }

    // 传递注册
    private void handRegister() {
        Log.i(TAG, "传递从服务器端返回的~注册~的请求");
        System.out.println("传递从服务器端返回的~注册~的请求");
        int result = 0;
        try {
            result = jsonObject.getInt("result");
            Message msg = new Message();
            msg.arg1 = result;
            msg.what = Config.REQUEST_REGISTER;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 上传一条记录
    public void uploadHeartrate(String userName,String start,String end,String heartraterecord,
                                int avarage,int max,int min,String duration,String date) {

        Log.i(TAG,"想服务器发送上传心脏记录请求"+"username:"+userName+" 记录:"+heartraterecord);
        // JSOn
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_UPLOAD_ONE_RECORD);
            jo.put("username", userName);
            jo.put("start", start);
            jo.put("end", end);
            jo.put("heartraterecord", heartraterecord);
            jo.put("avarage", avarage);
            jo.put("max", max);
            jo.put("min", min);
            jo.put("duration", duration);
            jo.put("date", date);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i(TAG, "发送登录的请求为：" + jo.toString());

        out.println(jo.toString());
    }

    // 获取一条记录
    public void getOneNewHeartraterecord(String userName) {

        Log.i(TAG,"向服务器发送获取一条心脏记录请求"+"username:"+userName);
        // JSOn
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_GET_ONE_RECORDS);
            jo.put("username", userName);
            } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i(TAG, "发送获取一条心脏记录的请求为：" + jo.toString());

        out.println(jo.toString());
    }

    // 处理获取一条记录
    public void handgetOneNewHeartraterecord() {

        Log.i(TAG,"传递从服务器获取的一条心脏记录请求");
        // JSOn
        JSONObject jo = jsonObject;
        try {
            String result=jo.getString("heartrecord");
            String start=jo.getString("start");
            String end=jo.getString("end");
            String duration=jo.getString("duration");
            String date=jo.getString("date");
            int max=jo.getInt("max");
            int min=jo.getInt("min");
            int avarage=jo.getInt("avarage");

            Log.i(TAG,"请求一条心率记录返回的result的值为："+result);
            Log.i(TAG,"请求一条心率记录返回的start的值为："+start);
            Log.i(TAG,"请求一条心率记录返回的end的值为："+end);
            Log.i(TAG,"请求一条心率记录返回的duration的值为："+duration);
            Log.i(TAG,"请求一条心率记录返回的date的值为："+date);
            Log.i(TAG,"请求一条心率记录返回的max的值为："+max);
            Log.i(TAG,"请求一条心率记录返回的min的值为："+min);
            Log.i(TAG,"请求一条心率记录返回的avarage的值为："+avarage);
            Message msg=new Message();
            Message msg2=new Message();
            Message msg3=new Message();
            Message msg4=new Message();
            Message msg5=new Message();

            msg.arg1=max;
            msg.obj=result;
            msg.what = Config.REQUEST_GET_ONE_RECORDS;
            BaseActivity.sendMessage(msg);

            msg2.arg1=min;
            msg2.obj=start;
            msg2.what = Config.REQUEST_GET_ONE_RECORDS2;
            BaseActivity.sendMessage(msg2);

            msg3.arg1=avarage;
            msg3.obj=end;
            msg3.what = Config.REQUEST_GET_ONE_RECORDS3;
            BaseActivity.sendMessage(msg3);

            msg4.obj=duration;
            msg4.what = Config.REQUEST_GET_ONE_RECORDS4;
            BaseActivity.sendMessage(msg4);

            msg5.obj=date;
            msg5.what = Config.REQUEST_GET_ONE_RECORDS5;
            BaseActivity.sendMessage(msg5);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i(TAG, "发送获取一条心脏记录的结果为：" + jo.toString());

       // out.println(jo.toString());
    }


    // 获取某个时间段内的记录
    public void getPeriodHeartraterecord(String userName,int datenum) {

        Log.i(TAG,"向服务器发送获取一条心脏记录请求"+"username:"+userName);
        // JSOn
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_GET_PERIOD_RECORDS);
            jo.put("username", userName);
            jo.put("datenum", datenum);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i(TAG, "发送获取一条心脏记录的请求为：" + jo.toString());

        out.println(jo.toString());
    }


    // 获取一条记录
    public void handgetPeriodHeartraterecord() {

        Log.i(TAG,"传递从服务器获取的一段时间内心脏记录请求");
        // JSOn
        JSONObject jo = jsonObject;
        try {
            String result=jo.getString("list");
            Log.i(TAG,"查看一段时间内的心率记录的结果为："+result);
            Message msg=new Message();
            msg.obj=result;
            msg.what = Config.REQUEST_GET_PERIOD_RECORDS;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "发送获取一段时间内心脏记录的结果为：" + jo.toString());

    }



    // 获取某个时间段内的记录
    public void getAllHeartraterecord(String userName) {

        Log.i(TAG,"向服务器发送获取一条心脏记录请求"+"username:"+userName);
        // JSOn
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_GET_ALL_RECORDS);
            jo.put("username", userName);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i(TAG, "发送获取一条心脏记录的请求为：" + jo.toString());

        out.println(jo.toString());
    }





    // 上传一条记录
    public void uploadAddress(String username,double Latitude, double Longitude) {

        Log.i(TAG,"想服务器发送上传地址请求"+"Latitude:"+Latitude+" Longitude:"+Longitude);
        // JSOn
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_MODIFY_PROPADDRESS);
            jo.put("username", username);
            jo.put("latitudenum", Latitude);
            jo.put("longitudenum", Longitude);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i(TAG, "发送上传地址请求为：" + jo.toString());

        out.println(jo.toString());
    }

    // 上传一条记录
    public void getFriendInfo(String username,String friendname) {

        Log.i(TAG,"向服务器发送获取朋友信息请求"+"username:"+username+" friendname:"+friendname);
        // JSOn
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_FRIEND_INFO);
            jo.put("username", username);
            jo.put("friendname", friendname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "发送获取朋友信息请求为：" + jo.toString());

        out.println(jo.toString());
    }

    public void handgetFriendInfo(){
        Log.i(TAG,"handgetFriendInfo：接收服务器发送的朋友信息");
        JSONObject jo = jsonObject;
        try {
            String result=jo.getString("heartrecord");
            String start=jo.getString("start");
            String end=jo.getString("end");
            String duration=jo.getString("duration");
            String date=jo.getString("date");
            int max=jo.getInt("max");
            int min=jo.getInt("min");
            int avarage=jo.getInt("avarage");
            aa
        }catch (JSONException e) {
            e.printStackTrace();
        }



    }






    // 退出游戏
    public void exitGame() {
        Log.i(TAG, "发送退出游戏的请求");
        System.out.println("发送退出游戏的请求");
        JSONObject jo = new JSONObject();
        try {
            jo.put("username", Constant.userName);
            jo.put("requestType", Config.REQUEST_EXIT);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送退出游戏的请求为：" + jo.toString());
        System.out.println("发送退出游戏的请求为：" + jo.toString());
    }

    // 商品的购买
    public void getshop(String username) {
        Log.i(TAG, "发送获取道具的请求");
        System.out.println("发送获取道具的请求");

        JSONObject jo = new JSONObject();
        try {
            jo.put("username", username);
            jo.put("requestType", Config.REQUEST_GET_PROP);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送获取道具的请求为：" + jo.toString());
        System.out.println("发送获取道具的请求为：" + jo.toString());
    }

    // 商城线程处理
    public void handGetshop() {
        Log.i(TAG, "传递从服务器端返回的~获取道具~的请求");
        System.out.println("传递从服务器端返回的~获取道具~的请求");
        try {
            int xmd = jsonObject.getInt("deng");
            int tsk = jsonObject.getInt("ka");
            int xcq = jsonObject.getInt("xiao");
            int jsq = jsonObject.getInt("jia");
            // Constant.playerTskNum = tsk;
            // Constant.playerXmdNum = xmd;
            Message msg = new Message();
            Message msg2 = new Message();
            msg.arg1 = tsk;
            msg.arg2 = xmd;
            msg2.arg1 = xcq;
            msg2.arg2 = jsq;

            msg.what = Config.REQUEST_GET_PROP;
            msg2.what = Config.REQUEST_GET_PROP2;
            BaseActivity.sendMessage(msg);
            BaseActivity.sendMessage(msg2);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 道具的请求
    public void changshop(String prpoName, int num) {
        Log.i(TAG, "发送更改道具的请求");
        System.out.println("发送更改道具的请求");

        JSONObject jo = new JSONObject();
        try {
            jo.put("username", Constant.userName);
            jo.put("propName", prpoName);
            jo.put("num", num);
            jo.put("requestType", Config.REQUEST_MODIFY_PROP);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送更改道具的请求为：" + jo.toString());
        System.out.println("发送更改道具的请求为：" + jo.toString());
    }

    // 商城道具线程处理
    public void handChangshop() {
        Log.i(TAG, "传递从服务器端返回的~更改道具~的请求");
        System.out.println("传递从服务器端返回的~更改道具~的请求");
        try {
            int result = jsonObject.getInt("result");
            Message msg = new Message();
            msg.arg1 = result;
            msg.what = Config.REQUEST_MODIFY_PROP;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // 发送获取玩家请求
    public void getPlayerList() {
        System.out.println("发送获取在线玩家的请求");
        JSONObject jo = new JSONObject();
        try {
            jo.put("username", Constant.userName);
            jo.put("requestType", Config.REQUEST_GET_USERS_ONLINE);
            System.out.println(Constant.userName);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送获取在线玩家的请求为：" + jo.toString());
    }
    // 线程处理在线玩家
    public void handPlayerList() {
        Log.i(TAG, "传递从服务器端返回的~获取在线玩家~");
        System.out.println("传递从服务器端返回的~获取在线玩家");
        try {
            JSONArray ja = jsonObject.getJSONArray("list");
            System.out.println(ja.toString());
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for(int i = 0; i<ja.length();i++ ){
                if (!ja.getJSONObject(i).optString("username")
                        .equals(Constant.userName)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("score", ja.getJSONObject(i).optInt("score"));
                    map.put("username",ja.getJSONObject(i).optString("username"));
                    list.add(map);
                    System.out.println(list);
                }
            }
            Message msg = new Message();
            msg.obj = list;
            msg.what = Config.REQUEST_GET_USERS_ONLINE;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {

            e.printStackTrace();
        }

    }

    // 向服务器发送添加好友请求
    public void addFriend(String friendname) {

        System.out.println("发送添加好友的请求");
        JSONObject jo = new JSONObject();
        try {
			/*jo.put("selfName", Constant.userName);
			jo.put("friendName", friendname);
			jo.put("requestType", Config.REQUEST_ADD_FRIEND);*/
            jo.put(Config.USERNAME, Constant.userName);
            jo.put("playername", friendname);
            jo.put(Config.REQUEST_TYPE, Config.REQUEST_ADD_FRIEND);
            out.println(jo.toString());
            System.out.println("添加好友的请求为："+jo.toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //out.println(jo.toString());
        Log.i(TAG, "发送添加好友的请求为："+jo.toString());
        System.out.println("发送添加好友的请求为："+jo.toString());
    }

    // 处理服务器返回好友
    private void handAddFriend() {
        Log.i(TAG, "传递从服务器端返回的~添加好友列表~的请求");

        Message msg = new Message();
        try {
            msg.arg1=jsonObject.getInt("result");
            msg.what=Config.REQUEST_ADD_FRIEND;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4
    // 向服务器发送删除好友请求
    public void deleteFriend_NetWork(String friendname) {

        System.out.println("发送删除好友的请求  friendname"+friendname);
        JSONObject jo = new JSONObject();
        try {
				/*jo.put("selfName", Constant.userName);
				jo.put("friendName", friendname);
				jo.put("requestType", Config.REQUEST_ADD_FRIEND);*/
            jo.put(Config.USERNAME, Constant.userName);
            jo.put("friendname", friendname);
            jo.put(Config.REQUEST_TYPE, Config.REQUEST_DELETE_FRIEND);
            out.println(jo.toString());
            System.out.println("ecf 删除好友的请求为："+jo.toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //out.println(jo.toString());
        Log.i(TAG, "发送删除好友的请求为："+jo.toString());
        System.out.println("发送删除好友的请求为："+jo.toString());
    }
    private void handDeleteFriend_NetWork(){
        Message msg = new Message();
        try {
            msg.arg1=jsonObject.getInt("result");
            msg.what=Config.REQUEST_DELETE_FRIEND;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4

    // 向服务器发送获取好友请求
    public void getFriendList() {

        System.out.println("发送好友列表的请求");
        JSONObject jo = new JSONObject();
        try {
            jo.put("username", Constant.userName);
            jo.put("requestType", Config.REQUEST_GET_FRIEND);
            System.out.println("发送获取好友列表的请求为："+jo.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送好友列表的请求为："+jo.toString());

    }

    // 处理服务器好友列表
    private void handFriendList() {

        Log.i(TAG, "传递从服务器端返回的~获取好友列表~的请求");
        JSONArray ja;
        List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
        try {
            ja = jsonObject.optJSONArray("list");
            System.out.println(ja.toString());
            for(int i = 0; i<ja.length();i++){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("score", ja.getJSONObject(i).getInt("score"));
                map.put("friendName", ja.getJSONObject(i).getString("friendName"));
                list.add(map);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Message msg = new Message();
        msg.obj = list;
        msg.what=Config.REQUEST_GET_FRIEND;
        BaseActivity.sendMessage(msg);

    }
    //向服务器发送邀请玩家挑战请求
    public void yaoZhan(String playerName, String userName, int model) {
        Log.i(TAG, "发送邀战的请求");
        JSONObject jo = new JSONObject();
        try {
            jo.put("playername", playerName);
            jo.put("username", userName);
            jo.put("model", model);
            jo.put("requestType", Config.REQUEST_SEND_INVITE);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送邀战的请求为："+jo.toString());

    }

    //服务器返回邀战的请求
    public void handYaoZhan(){
        Log.i(TAG, "传递从服务器端返回的~邀战~的请求");
        System.out.println("传递从服务器端返回的~邀战~的请求");
        Message msg = new Message();
        try {
            msg.arg1=jsonObject.getInt("model");
            msg.obj=jsonObject.getString("username");
            msg.what=Config.REQUEST_SEND_INVITE;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //向服务器发送是否接受请求信息
    public void inviteResult(String playername, int result) {

        Log.i(TAG, "发送是否接受邀请的请求");
        System.out.println("发送是否接受邀请的请求");
        JSONObject jo = new JSONObject();
        try {
            jo.put("playername", playername);
            jo.put("requestType", Config.REQUEST_INVITE_RESULT);
            jo.put("result", result);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送是否接受邀请的请求为："+jo.toString());
        System.out.println("发送是否接受邀请的请求为："+jo.toString());
    }

    //服务器返回是否接收请求
    public void handInviteResult(){
        Log.i(TAG, "传递从服务器端返回的~~是否接受邀请");
        System.out.println("传递从服务器端返回的~是否接受邀请~的请求");

        Message msg = new Message();
        try {
            msg.arg1=jsonObject.getInt("result");
            msg.what=Config.REQUEST_INVITE_RESULT;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //向服务器发送成语请求
    public void getChengYu(int num) {
        System.out.println("发送获取成语的请求");
        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_GET_SUBJECT);
            jo.put("num", num);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送获取成语的请求为："+jo.toString());

    }
    //服务器传递成语的请求
    public void handGetChengYu(){

        System.out.println("传递获取成语的请求");
        Message msg = new Message();
        try {
            JSONArray ja = jsonObject.getJSONArray("chengyus");
            msg.obj=ja;
            msg.what = Config.REQUEST_GET_SUBJECT;
            BaseActivity.sendMessage(msg);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }



    //发送玩家积分请求
    public void getScore(String name){
        Log.i(TAG, "发送获取玩家积分的请求");

        JSONObject jo = new JSONObject();
        try {
            jo.put("username", name);
            System.out.println(Constant.userName);
            jo.put("requestType", Config.REQUEST_GET_SCORES);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送获取玩家积分的请求为："+jo.toString());
        System.out.println("发送获取玩家积分的请求为："+jo.toString());
    }

    public void handGetSocre(){
        Log.i(TAG, "传递从服务器端返回的~玩家积分~的请求");
        System.out.println("传递从服务器端返回的~玩家积分~的请求");
        try {

            int score=jsonObject.getInt("score");
            Message msg = new Message();
            msg.arg1=score;
            msg.what=Config.REQUEST_GET_SCORES;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //发送添加积分的请求
    public void addScore(int num){
        Log.i(TAG, "发送添加积分的请求");
        System.out.println("发送添加积分的请求");
        JSONObject jo = new JSONObject();
        try {
            jo.put("username", Constant.userName);
            jo.put("requestType", Config.REQUEST_ADD_SCORES);
            jo.put("propName", "score");
            jo.put("num", num);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送添加积分的请求为："+jo.toString());
        System.out.println("发送添加积分的请求为："+jo.toString());
    }
    //向服务器发送挑战积分请求
    public void addPlayerScore(String playername,int num){

        System.out.println("发送挑战时添加积分的请求");
        JSONObject jo = new JSONObject();
        try {
            jo.put("playername", playername);
            jo.put("requestType", Config.REQUEST_ADD_PLAYERSCORE);
            jo.put("num", num);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送挑战时添加积分的请求为："+jo.toString());

    }
    //从服务器返回对战玩家的积分
    public void handAddPlayerScore(){

        System.out.println("传递从服务器端返回的~对战玩家积分增加~的请求");
        try {

            int num=jsonObject.getInt("num");
            Message msg = new Message();
            msg.arg1=num;
            msg.what=Config.REQUEST_ADD_PLAYERSCORE;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //发送pK结果给服务器，让服务器判断谁胜利
    public void sendPKResult(String playername){
        Log.i(TAG, "发送pk结果的请求");

        JSONObject jo = new JSONObject();
        try {
            jo.put("requestType", Config.REQUEST_PK_RESULT);
            jo.put("playername", playername);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        System.out.println("发送pk结果的请求为："+jo.toString());
    }

    //服务器的返回结果
    public void handPKResult(){

        System.out.println("传递从服务器端返回的~pk结果~的请求");
        Message msg = new Message();
        msg.what=Config.REQUEST_PK_RESULT;
        BaseActivity.sendMessage(msg);
    }

    public void exitGameAcitvity(String playername,String username){
        Log.i(TAG, "发送退出游戏界面的请求");
        JSONObject jo = new JSONObject();
        try {
            jo.put("username", username);
            jo.put("playername", playername);
            jo.put("requestType", Config.REQUEST_EXIT_GAME);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        System.out.println("发送退出游戏界面的请求为："+jo.toString());
    }

    public void handExitGameActivity(){

        Log.i(TAG, "传递从服务器端返回的~~退出游戏界面");
        Message msg = new Message();
        try {
            msg.obj = jsonObject.getString("username");
            msg.what=Config.REQUEST_EXIT_GAME;
            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void setOnWork(Boolean onWork) {
        this.onWork = onWork;
    }

    public void sendWeiXin_network(String receiver,String content ){
        Log.i(TAG, "发送微信的请求");

        JSONObject jo = new JSONObject();
        try {
            jo.put("receiver", receiver);
            jo.put("sender", Constant.userName);
            jo.put("content", content);
            System.out.println("other_side_name"+receiver);
            jo.put("requestType", Config.LLK_SEND_WEI_XIN);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.println(jo.toString());
        Log.i(TAG, "发送微信的请求为："+jo.toString());
        System.out.println("发送微信的请求为："+jo.toString());
    }

    public void getWeiXin_network(){

        try {
            String sender = jsonObject.getString("sender");
            String content = jsonObject.getString("content");

            Message msg = new Message();

            msg.what=Config.LLK_GET_WEI_XIN;
            msg.obj=(Object)(sender+":"+content);

            BaseActivity.sendMessage(msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
