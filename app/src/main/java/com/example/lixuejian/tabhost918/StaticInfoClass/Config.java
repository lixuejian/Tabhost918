package com.example.lixuejian.tabhost918.StaticInfoClass;

/**
 * Created by lixuejian on 2017/9/25.
 */

public interface Config {
    // TODO: 2017/9/26 修改config文件
    public static final int REQUEST_LOGIN = 1000;
    public static final int REQUEST_REGISTER = 1001;
    public static final int REQUEST_EXIT = 1002;
    public static final int SUCCESS = 2000;
    public static final int FAIl = 2001;
    public static final int USER_STATE_ONLINE = 3000;
    public static final int USER_STATE_NON_ONLINE = 3001;
    public static final String RESULT = "result";//结果
    public static final String REQUEST_TYPE = "requestType";//请求类型
    public static final int REQUEST_UPLOAD_ONE_RECORD = 1020; //	上传记录请求
    public static final int REQUEST_GET_ONE_RECORDS = 1022; //	获取一条记录请求
    public static final int REQUEST_GET_ONE_RECORDS2 = 10222; //	获取一条记录请求
    public static final int REQUEST_GET_ONE_RECORDS3 = 10223; //	获取一条记录请求
    public static final int REQUEST_GET_ONE_RECORDS4 = 10224; //	获取一条记录请求
    public static final int REQUEST_GET_ONE_RECORDS5 = 10225; //	获取一条记录请求
    public static final int REQUEST_GET_PERIOD_RECORDS = 1023; //	获取某时间段记录请求
    public static final int REQUEST_GET_ALL_RECORDS = 1021; //	获取某时间段记录请求
    public static final int REQUEST_MODIFY_PROPADDRESS = 2005; //	修改经纬度请求
    public static final int REQUEST_ADDRESS = 2006; //	查看朋友地址请求



    public static final int REQUEST_QUICK_LOGIN = 1003; //快速登录请求
    public static final int REQUEST_GET_PROP = 1004;//获取道具的请求
    public static final int REQUEST_GET_PROP2 = 1040;
    public static final int REQUEST_MODIFY_PROP = 1005;//修改道具的请求
    public static final int REQUEST_GET_USERS_ONLINE = 1008; //	获取在线用户请求
    public static final int REQUEST_ADD_FRIEND = 1006; //	添加好友请求
    public static final int REQUEST_GET_FRIEND = 1007; //	获取好友列表请求
    public static final int REQUEST_ADD_SCORES=1009;//修改积分的请求(发：username,requestType,propName(score),num 收：result)
    public static final int REQUEST_GET_SCORES=1010;//获取积分的请求(发：username,requestType.前台收到的：score)
    public static final int REQUEST_GET_SUBJECT=1011;//获取成语的请求(发：requestType，num.)
    public static final int REQUEST_SEND_INVITE=1012;// 发送邀请
    public static final int REQUEST_INVITE_RESULT = 1013;//邀请结果
    public static final int REQUEST_ADD_PLAYERSCORE = 1015;//PK时添加积分的请求
    public static final int REQUEST_PK_RESULT = 1016;//PK结果
    public static final int REQUEST_EXIT_GAME=1014;//退出游戏界面的请求
    public static final int REQUEST_DELETE_FRIEND = 1100; //	删除好友请求
    public static final int LLK_SEND_WEI_XIN = 1101; //	删除好友请求
    public static final int LLK_GET_WEI_XIN = 1102; //	删除好友请求

    public static final String USERNAME = "username";//用户名

    public static final int XLXF_MODEL = 1;
    public static final int ZFDM_MODEL = 2;

    public static final int level_01 = 500;
    public static final int level_02 = 800;
    public static final int level_03 = 1200;
    public static final int level_04 = 1500;
    public static final int level_05 = 2000;
    public static final int level_06 = 3500;
    public static final int level_07 = 8000;
    public static final int level_08 = 15000;
    public static final int level_09 = 20000;
    public static final int level_10 = 30000;

//    public static final int REQUEST_LOGIN = 1000; //登录请求
//    public static final int REQUEST_REGISTER = 1001; //注册请求
//    public static final int REQUEST_EXIT = 1002;  //退出请求
//    public static final int REQUEST_QUICK_LOGIN = 1003; //快速登录请求4
//    public static final int REQUEST_GET_PROP = 1004; //	获取道具请求
//    public static final int REQUEST_MODIFY_PROP = 1005; //	修改道具请求
//    public static final int REQUEST_MODIFY_PROPADDRESS = 2005; //	修改经纬度请求
//    public static final int REQUEST_ADDRESS = 2006; //	查看朋友地址请求
//    public static final int REQUEST_ADD_FRIEND = 1006; //	添加好友请求
//    public static final int REQUEST_GET_FRIEND = 1007; //	获取好友列表请求
//    public static final int REQUEST_GET_USERS_ONLINE = 1008; //	获取在线用户请求
//    public static final int REQUEST_ADD_SCORES=1009; //添加积分的请求
//    public static final int REQUEST_GET_SCORES=1010; //获取积分的请求
//    public static final int REQUEST_GET_CHENGYU=1011; //获取成语的请求
//    public static final int REQUEST_SEND_INVITE=1012;// 发送邀请
//    public static final int REQUEST_INVITE_RESULT = 1013;//邀请结果
//    public static final int REQUEST_EXIT_GAME = 1014;//退出游戏界面请求
//    public static final int REQUEST_ADD_PLAYERSCORE = 1015;//PK时添加积分的请求
//    public static final int REQUEST_PK_RESULT = 1016;//PK结果
//    public static final int REQUEST_DELETE_FRIEND = 1100; //	删除好友请求
//    public static final int LLK_SEND_WEI_XIN = 1101; //	删除好友请求
//    public static final int LLK_GET_WEI_XIN = 1102; //	删除好友请求
//
//    public static final int SUCCESS = 2000;  //成功结果
//    public static final int FAIl = 2001;     //失败结果
//
//    public static final int USER_STATE_ONLINE = 3000;  //用户在线的状态
//    public static final int USER_STATE_NON_ONLINE = 3001; //用户不在线的状态
//
//    public static final String RESULT = "result";//结果
//    public static final String REQUEST_TYPE = "requestType";//请求类型


}
