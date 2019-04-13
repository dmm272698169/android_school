package com.dommy.qrcode;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private CustomVideoView videoview;
    private Map<String,String> params;
    private Button logiin_flag_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_flag);
        init();
    }
    public void init(){
        Login_close=findViewById(R.id.login_close);
        videoview = (CustomVideoView) findViewById(R.id.videoview);
        logiin_flag_login=(Button) findViewById(R.id.logiin_flag_login);
        text=(TextView)findViewById(R.id.text);
    }
    public void login_close(View view) {
        Intent intent=new Intent(login_flag.this,MainActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
        finish();
    }
    public void initView(){//背景
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.beijing));

        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });
    }

    public void Login_login(View view) throws JSONException {
        params=new HashMap<>();
        String param= (String) getIntent().getSerializableExtra("param");
        JSONObject object=new JSONObject(param);

        params.put("uid", object.get("uid").toString());
        Map<String,String> map=Util.getUser(login_flag.this);
        String content= httpClient.submitPostData("http://www.prowhy.com/api/mobile/login",map,"utf-8");
        params.put("token",content);
        String str= httpClient.submitPostData("http://www.prowhy.com/api/mobile/login",params,"utf-8");
        Util.getToast(login_flag.this,"登陆成功，即将返回。");

        Intent intent=new Intent(login_flag.this,MainActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
        finish();
    }
}
