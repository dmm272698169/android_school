package com.dommy.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dommy.qrcode.util.Constant;
import com.dommy.qrcode.util.Util;
import com.dommy.qrcode.util.httpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        final String account=login_edit_account.getText().toString();
        final String password=login_edit_pwd.getText().toString();
        new Thread(){
            @Override
            public void run() {
                if("".equals(account) || "".equals(password)){
                    Util.getToast(login.this,"用户名或密码不能为空");
                }else {
                    JSONObject object=new JSONObject();
//                    try {
////                        object.put("username",login_edit_account.getText().toString());
////                        object.put("password", login_edit_pwd.getText().toString());
////                        Log.i("账号",login_edit_account.getText().toString());
////                        Log.i("密码",login_edit_pwd.getText().toString());
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                    String requestData=Util.getBase64(object.toString());//aes加密
////                    String encrypted= null;
////                    try {
////                        encrypted = Util.resString(Util.getRandomString(16));//res加密
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//                    params.put("requestData",requestData);//数据加密
//                    params.put("encrypted",encrypted);//16位随机字符加密
                    params.put("userid",login_edit_account.getText().toString());//数据加密
                    params.put("encrypted",login_edit_pwd.getText().toString());//16位随机字符加密
//
//                    Log.i("requestData",requestData);
//                    Log.i("encrypted",encrypted);

//                    String name= httpClient.submitPostData("http://www.prowhy.com/api/login",params,"utf-8");
                    String name= httpClient.submitPostData("http://www.prowhy.com:8080/Myproject/jsp/login.jsp",params,"utf-8");
                    if (name.contains("用户名或密码不正确")) {
                        Util.getToast(login.this,"用户名或密码不正确");
                        Log.i("网页信息",name);
                    } else {
                        Log.i("网页信息",name);
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivityForResult(intent, Constant.REQ_QR_CODE);
                    }
                }
            }
        }.start();

    }
}
