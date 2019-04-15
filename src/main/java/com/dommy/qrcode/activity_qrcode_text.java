package com.dommy.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dommy.qrcode.util.Constant;

public class activity_qrcode_text extends AppCompatActivity {
    private TextView qrcode_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_text);

        qrcode_text=findViewById(R.id.qrcode_text);
        Intent intent=getIntent();
        String data = intent.getStringExtra("param");
        qrcode_text.setText(data);
    }

    public void login_back(View view) {
        Intent intent = new Intent(activity_qrcode_text.this, AliHomeActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
        finish();
    }
}
