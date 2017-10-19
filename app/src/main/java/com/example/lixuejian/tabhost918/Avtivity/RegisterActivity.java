package com.example.lixuejian.tabhost918.Avtivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixuejian.tabhost918.Network.Communication;
import com.example.lixuejian.tabhost918.StaticInfoClass.Config;
import com.example.lixuejian.tabhost918.StaticInfoClass.Constant;
import com.example.lixuejian.tabhost918.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends BaseActivity {
    private EditText t_username;
    private EditText t_password;
    private EditText t_age;
    private RadioGroup rg_sex;
    private RadioButton rb_sex;
    private Spinner s_sportlevel;
    private Button btn_register;

    private String username;
    private String password;
    private int age;
    private int sex;
    private int sportlevel=0;

    private List<Integer> data_list;
    private ArrayAdapter<Integer> arr_adapter;

    private final String TAG="RegisterActivity提示~";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.e(TAG,"onCreate");

        init();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                username=t_username.getText().toString().trim();
                password=t_password.getText().toString();
                Log.i(TAG,"t_age.getText().toString()="+t_age.getText().toString()+" rb_sex.getText()="+rb_sex.getText());
                if(username.equals("")){
                    Toast toast = Toast.makeText(RegisterActivity.this,"用户名不能为空", Toast.LENGTH_LONG);
                    toast.show();
                    t_username.requestFocus();
                    return;
                }else if(password.equals("")){
                    Toast toast = Toast.makeText(RegisterActivity.this,"密码不能为空", Toast.LENGTH_LONG);
                    toast.show();
                    t_password.requestFocus();
                    return;
                }
                else if (t_age.getText().toString().equals("")){
                    Toast toast = Toast.makeText(RegisterActivity.this,"年龄不能为空", Toast.LENGTH_LONG);
                    toast.show();
                    t_age.requestFocus();
                    return;
                }
                age=Integer.parseInt(t_age.getText().toString());
                if (rb_sex.getText().equals("男")) {sex=1;}
                  else {sex=2;}

                Log.i(TAG,"username="+username+" password="+password+" age="+age+" 性别="+sex+" sportlevel="+sportlevel);
                con.register(username,password,age,sex,sportlevel);
            }


        });
        Log.i(TAG,"buttonlistenner");

        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 点击事件获取的选择对象
                rb_sex = (RadioButton) rg_sex.findViewById(checkedId);
                Toast.makeText(getApplicationContext(),"获取的ID是" + rb_sex.getText(),
                        Toast.LENGTH_LONG).show();
            }
        });
        Log.i(TAG,"radiolistenner");


        data_list = new ArrayList<Integer>();
        data_list.add(1);
        data_list.add(2);
        data_list.add(3);
        data_list.add(4);
        data_list.add(5);

        arr_adapter= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_sportlevel.setAdapter(arr_adapter);


        s_sportlevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                sportlevel=data_list.get(pos);
                //Toast.makeText(RegisterActivity.this, "你点击的是:"+data_list.get(pos), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        Log.i(TAG,"spinnerlistenner");


        con=Communication.newInstance();
    }

    private void init(){
        t_username=(EditText) findViewById(R.id.register_et_username);
        t_password=(EditText) findViewById(R.id.register_et_password);
        t_age=(EditText)findViewById(R.id.register_et_age);
        rg_sex=(RadioGroup)findViewById(R.id.register_rg_sex);
        rb_sex=(RadioButton)rg_sex.findViewById(rg_sex.getCheckedRadioButtonId());
        s_sportlevel=(Spinner)findViewById(R.id.register_spinner_sportlevel);
        btn_register=(Button) findViewById(R.id.register_btn_register);
        Log.i(TAG,"init");
    }


    @Override
    public void processMessage(Message msg) {
        switch(msg.what){
            case Config.REQUEST_REGISTER:
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(this,LoginActivity.class);
                startActivity(intent);

        }

    }
}