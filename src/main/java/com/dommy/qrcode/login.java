package com.dommy.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dommy.qrcode.util.Constant;
import com.dommy.qrcode.util.httpClient;
import com.google.zxing.activity.CaptureActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;

/**
 * login_edit_pwd 密码
 * login_edit_account 账号
 */
public class login extends AppCompatActivity {
    private EditText login_edit_pwd,login_edit_account;
    private  Map<String,String> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_edit_pwd=findViewById(R.id.login_edit_pwd);
        login_edit_account=findViewById(R.id.login_edit_account);
    }
    public void finish_login(View view){//登陆
        params=new HashMap<>();
        params.put("userid",login_edit_account.getText().toString());
        params.put("password",login_edit_pwd.getText().toString());
        new Thread(){
            @Override
            public void run() {
               String name= httpClient.submitPostData("http://www.prowhy.com:8080/Myproject/jsp/login.jsp",params,"utf-8");
               if (name.indexOf("用户名或密码错误")!=-1){
                   Toast.makeText(login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
               }else {
                   Intent intent = new Intent(login.this, MainActivity.class);
                   startActivityForResult(intent, Constant.REQ_QR_CODE);
               }
            }
        }.start();

    }
}
