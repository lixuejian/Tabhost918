package com.example.lixuejian.tabhost918.Avtivity;

import java.util.LinkedList;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;

import com.example.lixuejian.tabhost918.Network.Communication;
import com.example.lixuejian.tabhost918.StaticInfoClass.Config;
import com.example.lixuejian.tabhost918.StaticInfoClass.Constant;

public abstract class BaseActivity extends AppCompatActivity {

    //将生成的Activity都放到LinkList集合中
    protected static LinkedList<BaseActivity> queue= new LinkedList<BaseActivity>();
    public static Communication con;
    private final static String TAG="BaseActivity提示~";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"--------------------------------------------------------");
        if(!queue.contains(this)){
            queue.add(this);
            Log.i(TAG,"将"+queue.getLast()+"添加到list中去");
        }
    }

    public abstract void processMessage(Message message);

    private static Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case Config.REQUEST_SEND_INVITE:
                    final String playername=(String) msg.obj;

                    int model = msg.arg1;
                    Constant.gameModel=model;
                    System.out.println(queue.getLast().getClass().toString());
                    if(!(queue.getLast().getClass().toString()).equals("class com.llk.view.LLKPKGamesActivity")){

                        Builder build = new Builder(queue.getLast());
                        build.setTitle("提示");
                        build.setMessage(playername+"向你发出挑战的邀请！是否接受邀请？");
                        build.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                con.inviteResult(playername, Config.SUCCESS);
                                Constant.playerName=playername;
                                Toast.makeText(queue.getLast(),"同意对方的请求啦，进入游戏中。。。", Toast.LENGTH_LONG).show();
                                Intent in = new Intent (queue.getLast(),MainActivity.class);
                                queue.getLast().startActivity(in);
                                queue.getLast().finish();
                            }
                        });
                        build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                con.inviteResult(playername, Config.FAIl);

                            }
                        });
                        build.create().show();

                    }
                    else{

                        con.inviteResult(playername, Config.FAIl);
                    }
                    break;


                default:
                    Log.i(TAG,"执行到baseactivity的handler的defult");
                    if(!queue.isEmpty()){
                        Log.i(TAG,"msg.arg1的值为="+msg.arg1+"! "+"类型="+msg.what);
                        queue.getLast().processMessage(msg);
                    }
                    break;
            }


        };
    };

    //发送消息（...）
    public static void sendMessage(Message msg){
        handler.sendMessage(msg);
    }

}
