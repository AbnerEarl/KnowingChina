package com.example.frank.wuhanjikong.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.config.PersonInfo;
import com.example.frank.wuhanjikong.ui.person.HskActivity;
import com.example.frank.wuhanjikong.ui.person.PersonCollectionActivity;
import com.example.frank.wuhanjikong.ui.person.PersonDetailActivity;
import com.example.frank.wuhanjikong.ui.person.PersonMoneyActivity;
import com.example.frank.wuhanjikong.ui.person.ServerActivity;
import com.example.frank.wuhanjikong.ui.person.SettingActivity;

public class PersonalActivity extends AppCompatActivity {

    private Button personMoney,personHsk,personCollection,personSetting,myOrder,myCart,lookHelp;
    private TextView personInfo,personName;
    private ImageView person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        personInfo=(TextView)this.findViewById(R.id.textView10);
        personName=(TextView)this.findViewById(R.id.textView14);
        myOrder=(Button)this.findViewById(R.id.button7);
        myCart=(Button)this.findViewById(R.id.button8);
        personMoney=(Button)this.findViewById(R.id.button6);
        personHsk=(Button)this.findViewById(R.id.button10);
        personCollection=(Button)this.findViewById(R.id.button9);
        personSetting=(Button)this.findViewById(R.id.button12);
        lookHelp=(Button)this.findViewById(R.id.button11);
        person=(ImageView)this.findViewById(R.id.imageView14);

        if (PersonInfo.localSysUser!=null) {
            personName.setText(PersonInfo.localSysUser.getNickName());
            personInfo.setText(PersonInfo.localSysUser.getLoginName());
        }else {

        }

        person.setOnClickListener(new View.OnClickListener() {
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


        lookHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalActivity.this, ServerActivity.class);
                startActivity(intent);
            }
        });

        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PersonalActivity.this).setMessage("敬请期待！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PersonalActivity.this).setMessage("敬请期待！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

    }
}
