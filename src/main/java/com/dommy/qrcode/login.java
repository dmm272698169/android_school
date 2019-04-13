package com.dommy.qrcode;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dommy.qrcode.others.CustomVideoView;
import com.dommy.qrcode.sql.SQLiteHelper;
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
    private CustomVideoView videoview;
    private JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    public void  initView(){
        login_edit_pwd=findViewById(R.id.login_edit_pwd);
        login_edit_account=findViewById(R.id.login_edit_account);
        videoview = (CustomVideoView) findViewById(R.id.videoview);
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

    /**
     * Finish login.
     *
     *  account 用户名
     *  password 密码s
     */
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
                    params.put("username",account);
                    params.put("password",password);
                    String content= httpClient.submitPostData("http://www.prowhy.com/api/mobile/login",params,"utf-8");
                    if (content.contains("用户名或密码不正确")) {
                        Util.getToast(login.this,"用户名或密码不正确");
                    } else {
                        try {
                            jsonObject=new JSONObject(content);
//                            Util.deleteDatabse(login.this);
//                            if(Util.findById(login.this,account)){
//                                Util.updateSQL(login.this,(String) jsonObject.get("token"),account);
//                            }else {
//                                Util.insertSqlLite(login.this,(String) jsonObject.get("token"),account);
//                            }
                            Util.saveUser(login.this,account,password);
                            Log.i("网页信息",jsonObject.get("token").toString());
                            Intent intent = new Intent(login.this,AliHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("username",account);
                            startActivityForResult(intent, Constant.REQ_QR_CODE);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }.start();
    }

}
