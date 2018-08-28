package com.example.frank.wuhanjikong.ui.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.config.PersonInfo;
import com.example.frank.wuhanjikong.service.ApiService;
import com.example.frank.wuhanjikong.ui.login.LoginActivity;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;

import java.util.HashMap;
import java.util.Map;

public class PublishContentActivity extends AppCompatActivity {

    private ImageButton back;
    private TextView title;
    private EditText content,fileUrl;
    private Button publishContent;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.fragment_add_info);

        back=(ImageButton)this.findViewById(R.id.titleback);
        title=(TextView)this.findViewById(R.id.titleplain);
        content=(EditText)findViewById(R.id.editText);
        fileUrl=(EditText)findViewById(R.id.editText2);
        publishContent=(Button)findViewById(R.id.button20) ;
        //标题栏设置
        title.setText("Knowing China");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog = new ProgressDialog(PublishContentActivity.this);
        dialog.setTitle("hint");
        dialog.setMessage("loading...");

        publishContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentInfo=content.getText().toString();
                if (contentInfo==null||contentInfo.trim().equals("")||contentInfo.length()<2){
                    Toast.makeText(PublishContentActivity.this,"please enter your content",Toast.LENGTH_SHORT).show();
                }else {
                    HashMap<String ,String> map=new HashMap<>();
                    if (contentInfo.length()>15){
                        map.put("contentTitle",contentInfo.substring(0,15));
                    }else {
                        map.put("contentTitle",contentInfo);
                    }
                    map.put("contentText",contentInfo);
                    map.put("publishId", PersonInfo.localSysUser.getUserId());
                    map.put("fileUrl",fileUrl.getText().toString());
                    addContentInfo(JSON.toJSONString(map));
                }
            }
        });



    }



    private void addContentInfo(final String contentInfo){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", contentInfo);
                    ApiService.GetString(PublishContentActivity.this, "addContentByUserId", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            Toast.makeText(PublishContentActivity.this,response,Toast.LENGTH_SHORT).show();
                            if (response.equals("发布成功")){
                                finish();
                            }

                        }

                        @Override
                        public void onError(Object tag, Throwable e) {

                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {

                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }




}
