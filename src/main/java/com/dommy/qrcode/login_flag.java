package com.dommy.qrcode;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dommy.qrcode.others.CustomVideoView;
import com.dommy.qrcode.util.Constant;
import com.dommy.qrcode.util.Util;
import com.dommy.qrcode.util.httpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login_flag extends AppCompatActivity {
    private TextView Login_close,text;
    //private CustomVideoView videoview;
    private Map<String,String> params;
    private  Map<String,String> map;
    private Button logiin_flag_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_flag);
        init();
        logiin_flag_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        params=new HashMap<>();
                        Intent intent = getIntent();
                        String data = intent.getStringExtra("param");
                        Log.i("参数",data);
                        try {
                            JSONObject object = new JSONObject(data);
                            params.put("uid",object.getString("uid"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       // params.put("uid","1ba9f78e-4bff-4d62-8a62-b8cf5e7c8f69");
                        map=new HashMap<>();
                        map=Util.getUser(login_flag.this);

                        String content= httpClient.submitPostData("http://www.prowhy.com/api/mobile/login",map,"utf-8");
                        Log.i("登陆信息",content);
                        try {
                            JSONObject object1=new JSONObject(content);
                            params.put("token",object1.getString("token"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String str= httpClient.submitPostData("http://www.prowhy.com/api/qr/login",params,"utf-8");
                        if(!str.contains("登陆成功")){
                            Intent intent1=new Intent(login_flag.this,AliHomeActivity.class);
                            startActivityForResult(intent1, Constant.REQ_QR_CODE);
                            Util.getToast(login_flag.this,"登陆成功!");
                            finish();
                        }else {
                            Intent intent1=new Intent(login_flag.this,AliHomeActivity.class);
                            startActivityForResult(intent1, Constant.REQ_QR_CODE);
                            Util.getToast(login_flag.this,"登陆失败!");
                            finish();
                        }




                    }
                }.start();
            }
        });
    }
    public void init(){
        Login_close=findViewById(R.id.login_close);
        //videoview = (CustomVideoView) findViewById(R.id.videoview);
        logiin_flag_login=(Button) findViewById(R.id.logiin_flag_login);
        text=(TextView)findViewById(R.id.text);
    }
    public void login_close(View view) {
        Intent intent=new Intent(login_flag.this,AliHomeActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
        finish();
    }
//    public void initView(){//背景
//        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.beijing));
//
//        //播放
//        videoview.start();
//        //循环播放
//        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                videoview.start();
//            }
//        });
//    }
}
