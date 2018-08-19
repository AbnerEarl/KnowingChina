package com.example.frank.wuhanjikong.ui.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.frank.wuhanjikong.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        try {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).start();


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
