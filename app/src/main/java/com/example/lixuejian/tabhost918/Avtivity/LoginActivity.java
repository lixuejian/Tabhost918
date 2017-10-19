package com.example.lixuejian.tabhost918.Avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixuejian.tabhost918.Network.Communication;
import com.example.lixuejian.tabhost918.StaticInfoClass.Config;
import com.example.lixuejian.tabhost918.StaticInfoClass.Constant;
import com.example.lixuejian.tabhost918.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView t_username;
    private TextView t_password;
    private Button btn_login;
    private Button btn_register;
    private String username;
    private String password;
    Toast toast;
    private final String TAG="LoginActivity提示~";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i(TAG,"onCreate");

        t_username=(TextView) findViewById(R.id.username);
        t_password=(TextView) findViewById(R.id.password);
        btn_login=(Button) findViewById(R.id.btn_login);
        btn_register=(Button) findViewById(R.id.btn_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        con= Communication.newInstance();
    }


    @Override
    public void onClick(View view) {
        Log.i(TAG,"onclick");

        username=t_username.getText().toString().trim();
        password=t_password.getText().toString();

        switch (view.getId()){
            case R.id.btn_login:

                if(username.equals("")){
                    Toast.makeText(LoginActivity.this,"用户名不能为空", Toast.LENGTH_LONG).show();
                    t_username.requestFocus();
                    return;
                }else if(password.equals("")){
                    Toast.makeText(LoginActivity.this,"密码不能为空", Toast.LENGTH_LONG).show();
                    t_password.requestFocus();
                    return;
                }

                Log.i(TAG,"btn_button is onclick");
                Log.i(TAG,"username"+username);
                Log.i(TAG,"password"+password);
                con.login(username,password);
                break;
            case R.id.btn_register:
                Log.i(TAG,"btn_register is onclick");
                Intent intent = new Intent();
                intent.setClass(this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    @Override
    public void processMessage(Message msg) {
        switch(msg.what){
            case Config.REQUEST_LOGIN:
                int result = msg.arg1;
                Log.i(TAG,"loginactivity的processmessage的登录请求回执为"+"result="+result);
                if(result == Config.SUCCESS){
                    Toast toast =Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_LONG);
                    toast.show();
                    Constant.userName = username;
                    Constant.userPassword = password;
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    msg = null;
                    Log.i(TAG,"登陆成功");
                }else{
                    Log.i(TAG,"loginactivity的processmessage的登录失败，"+"result="+result);
                    Toast toast = Toast.makeText(this,"登录失败", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;

            default:
                break;
        }

    }
}