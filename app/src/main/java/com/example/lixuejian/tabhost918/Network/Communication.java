package com.example.lixuejian.tabhost918.Network;

import android.content.Context;
import android.util.Log;

/**
 * Created by lixuejian on 2017/9/25.
 */

public class Communication {
    private final static String TAG="Communication提示~";

    Context context;
    private NetWorker netWorker;
    public static Communication instance;

    //生成Conmmunication通信类的实例
    public static Communication newInstance(){
        if(instance ==null){
            instance = new Communication();
            Log.i(TAG,"newInstance");
        }
        return instance;
    }

    //将构造函数私有化，使其不能生成多个实例，防止多次连接连接服务器
    private Communication(){
        netWorker = new NetWorker();
        netWorker.start();
    }
    /**
     * 登录
     */
    public void login(String userName,String password){
        netWorker.login(userName, password);
    }
    /**
     * 注册
     */
    public void register(String userName,String password,int age,int sex,int sportlevel){
        netWorker.register(userName,password,age,sex,sportlevel);
    }
    //发送退出游戏请求
    public void exitGame(){
        netWorker.exitGame();
    }
    //快速登陆
    public void registerq(){
        netWorker.registerq();
    }

    //商品
    public void getshop(String name){
        netWorker.getshop(name);

    }
    //道具
    public void changshop(String prpoName, int num) {
        netWorker.changshop(prpoName,num);

    }
    //玩家列表
    public void getPlayerList() {
        netWorker.getPlayerList();

    }

    //好友列表
    public void getFriendlist() {
        netWorker.getFriendList();

    }
    //添加玩家为好友
    public void addFriend(String friendname) {
        netWorker.addFriend(friendname);
    }

    public void deleteFriend_Communication(String friendname) {
        netWorker.deleteFriend_NetWork(friendname);
    }

    //邀请好友挑战
    public void yaoZhan(String playerName, String userName, int zfdmModel) {
        netWorker.yaoZhan(playerName,userName, zfdmModel);

    }
    //是否接受请求
    public void inviteResult(String playername, int result) {
        netWorker.inviteResult(playername,result);

    }
    //发送成语请求
    public void getChengYu(int num) {
        netWorker.getChengYu(num);

    }
    //pk中发送的分数
    public void addPlayerScore(String userName, int flag1) {

        netWorker.addPlayerScore(userName, flag1);
    }
    //发送获取玩家积分
    public void getScore(String name){
        netWorker.getScore(name);
    }
    //发送获取玩家积分
    public void getAddress(String name,String friendname){
        netWorker.getAddress(name,friendname);
    }
    //添加积分请求
    public void addScore(int num){
        netWorker.addScore(num);
    }
    //发送PK值
    public void sendPKResult(String playername){
        netWorker.sendPKResult(playername);
    }

    //发送游戏中退出游戏请求
    public void exitGameActivity(String playername,String username){
        netWorker.exitGameAcitvity(playername,username);
    }
    /**
     * 退出连接后，清空资源
     */
    public void clear(){
        netWorker.setOnWork(false);
        instance=null;
    }

    public void sendWeiXin_communication(String other_side_name,String content ){
        netWorker.sendWeiXin_network(other_side_name, content);
    }


//**************************************************************************************************
//------------------------------------------以下为新添功能--------------------------------------------
//**************************************************************************************************


//    public void login(){}
//
//    public void logout(){}
//
//    public void register(){
//        Log.e(TAG,"register");
//    }
//
//    public void addFriend(){}
//
//    public void deleteFriend(){}
//
//    public void getAllFriendList(){}
//
//    public void getOnlineFriendList(){}
//
//    public void changeUserInfo(){}
//
      public void uploadLocation(String username,double Latitude,double Longitude){
          netWorker.uploadAddress(username,Latitude,Longitude);
      }
//
      public void uploadHeartrate(String username,String start,String end,String heartraterecord,
                                  int avarage,int max,int min,String duration,String date){
        netWorker.uploadHeartrate(username, start,end,heartraterecord,avarage,max,min,duration,date);
    }
//
//    public void uploadStepNumber(){}
//
//    public void uploadDrinkWater(){}
//
//    public void uploadNowAllInfo(){}
//
//    public void uploadPathAnalyze(){}
//
//    public void uploadHeartrateAnalyze(){}
//
//    public void uploadStepNumberAnalyze(){}
//
//    public void uploadDrinkWaterAnalyze(){}
//
//    public void uploadAllInfoAnalyze(){}
//
//    public void getlocation(){}
//
//    public void getHeartrate(){}
    public void getNewestHeartrateRecord(String username){
        netWorker.getOneNewHeartraterecord(username);
    }

    public void getPeriodHeartrateRecord(String username,int datenum){
        netWorker.getPeriodHeartraterecord(username,datenum);
    }

    public void getALLHeartrateRecord(String username){
        netWorker.getAllHeartraterecord(username);
    }

    public void getFriendInfo(String myselfname,String friendname){
        netWorker.getFriendInfo(myselfname,friendname);

    }
//
//    public void getStepNumber(){}
//
//    public void getDrinkWater(){}
//
//    public void getNowAllInfo(){}
//
//    public void getpathAnalyze(){}
//
//    public void getHeartrateAnalyze(){}
//
//    public void getStepNumberAnalyze(){}
//
//    public void getDrinkWaterAnalyze(){}
//
//    public void getAllInfoAnalyze(){}
//
//    public void sendMessage2Friend(){}

}
