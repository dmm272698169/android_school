package com.dommy.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dommy.qrcode.util.Constant;

public class login_flag extends AppCompatActivity {
    private TextView Login_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_flag);
        Login_close=findViewById(R.id.login_close);
    }

    public void login_close(View view) {
        Intent intent=new Intent(login_flag.this,MainActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
        finish();
    }
}
