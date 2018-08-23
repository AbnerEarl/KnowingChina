package com.example.frank.wuhanjikong.ui.person;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.config.PersonInfo;

public class PersonDetailActivity extends AppCompatActivity {

    private TextView nickName,loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        nickName=(TextView)this.findViewById(R.id.textView8);
        loginName=(TextView)this.findViewById(R.id.textView9);

        if (PersonInfo.localSysUser!=null) {
            nickName.setText(PersonInfo.localSysUser.getNickName());
            loginName.setText(PersonInfo.localSysUser.getLoginName());
        }else {

        }
    }
}
