package com.example.frank.wuhanjikong.ui.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.ui.person.HskActivity;
import com.example.frank.wuhanjikong.ui.person.PersonCollectionActivity;
import com.example.frank.wuhanjikong.ui.person.PersonDetailActivity;
import com.example.frank.wuhanjikong.ui.person.PersonMoneyActivity;
import com.example.frank.wuhanjikong.ui.person.ServerActivity;
import com.example.frank.wuhanjikong.ui.person.SettingActivity;

public class PersonalActivity extends AppCompatActivity {

    private Button personInfo,personMoney,personHsk,personCollection,personSetting,personServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        personInfo=(Button)this.findViewById(R.id.button6);
        personMoney=(Button)this.findViewById(R.id.button7);
        personHsk=(Button)this.findViewById(R.id.button8);
        personCollection=(Button)this.findViewById(R.id.button9);
        personSetting=(Button)this.findViewById(R.id.button10);
        personServer=(Button)this.findViewById(R.id.button11);

        personInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(PersonalActivity.this, PersonDetailActivity.class);
                startActivity(intent);

            }
        });

        personMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalActivity.this, PersonMoneyActivity.class);
                startActivity(intent);
            }
        });

        personHsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalActivity.this, HskActivity.class);
                startActivity(intent);
            }
        });


        personCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalActivity.this, PersonCollectionActivity.class);
                startActivity(intent);
            }
        });

        personSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });


        personServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalActivity.this, ServerActivity.class);
                startActivity(intent);
            }
        });

    }
}
