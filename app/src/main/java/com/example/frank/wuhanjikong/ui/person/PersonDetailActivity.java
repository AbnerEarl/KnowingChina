package com.example.frank.wuhanjikong.ui.person;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.config.PersonInfo;

public class PersonDetailActivity extends AppCompatActivity {

    private TextView personLoginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        personLoginName=(TextView)this.findViewById(R.id.textView9);

        personLoginName.setText("当前登录用户为："+PersonInfo.userName);
    }
}
