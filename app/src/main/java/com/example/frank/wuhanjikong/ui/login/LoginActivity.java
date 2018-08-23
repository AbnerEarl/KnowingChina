package com.example.frank.wuhanjikong.ui.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.config.PersonInfo;
import com.example.frank.wuhanjikong.config.local.SharePreferenceSave;
import com.example.frank.wuhanjikong.log.L;
import com.example.frank.wuhanjikong.service.ApiService;
import com.example.frank.wuhanjikong.ui.home.HomeActivity;
import com.pgyersdk.crash.PgyCrashManager;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etNumber;
    private EditText etPassword;
    private Button btnLogin,btnRegister;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );

        PgyCrashManager.register();
        //动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS);
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            }
        }

        //初始化view
        initView();
        //如果用户保存了信息，进行数据的回显
        /*Map<String,String> userInfo = FileSaveQQ.getUserInfo ( this );*/
        Map<String, String> userInfo= SharePreferenceSave.getUserInfo ( this );

        if (userInfo!=null){
            etNumber.setText ( userInfo.get ( "number" ) );
            etPassword.setText ( userInfo.get ( "password" ) );
        }
    }

    private void initView() {
        //完成控件的初始化
        etNumber = (EditText) findViewById ( R.id.et_number );
        etPassword = (EditText) findViewById ( R.id.et_password );
        btnLogin = (Button) findViewById ( R.id.btn_login );
        btnRegister = (Button) findViewById ( R.id.btn_register );

        //给按钮设置点击事件
        //btnLogin.setOnClickListener ( this );
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击按钮获取账号和密码
                String loginName = etNumber.getText ().toString ().trim ();
                String passWord = etPassword.getText ().toString ();
                //检查用户名密码是否为空
                if (TextUtils.isEmpty ( loginName )){
                    Toast.makeText ( LoginActivity.this,"请输入账号", Toast.LENGTH_LONG ).show ();
                    return;
                }
                if (TextUtils.isEmpty ( passWord )){
                    Toast.makeText ( LoginActivity.this,"请输入密码", Toast.LENGTH_LONG ).show ();
                    return;
                }



                if (!loginName.equals("")&&!passWord.equals("")){
                    userLogin(loginName,passWord);
                }else {
                    Toast.makeText (LoginActivity.this,"请输入合法的用户名或者密码！", Toast.LENGTH_LONG ).show ();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,Regesiter.class);
                startActivity(intent);
            }
        });

    }



    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "允许写存储！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "未允许写存储！", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            case REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "允许读存储！", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, "未允许读存储！", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
            }
        }
    }

    @Override
    public void onClick(View v){
        //点击按钮获取账号和密码
        String loginName = etNumber.getText ().toString ().trim ();
        String passWord = etPassword.getText ().toString ();
        //检查用户名密码是否为空
        if (TextUtils.isEmpty ( loginName )){
            Toast.makeText ( this,"请输入账号", Toast.LENGTH_LONG ).show ();
            return;
        }
        if (TextUtils.isEmpty ( passWord )){
            Toast.makeText ( this,"请输入密码", Toast.LENGTH_LONG ).show ();
            return;
        }

        //userLogin(loginName,passWord);

        if (!loginName.equals("")&&!passWord.equals("")){
            PersonInfo.userName=loginName;
            boolean isSaveSucess = SharePreferenceSave.saveUserInfo (LoginActivity.this, loginName, passWord );
            Toast.makeText (LoginActivity.this,"Login Successfully", Toast.LENGTH_LONG ).show ();
            Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText (LoginActivity.this,"请输入合法的用户名或者密码！", Toast.LENGTH_LONG ).show ();
        }


    }



    private void userLogin(final String loginName, final String passWord){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, String> values = new HashMap<String, String>();
                    values.put("loginName", loginName);
                    values.put("userPass",passWord);
                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", JSON.toJSONString(values));
                    ApiService.GetString(LoginActivity.this, "loginMobile", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {

                            if (response.trim().equals("登录成功")){
                                boolean isSaveSucess = SharePreferenceSave.saveUserInfo (LoginActivity.this, loginName, passWord );
                                PersonInfo.userName=loginName;
                                Toast.makeText (LoginActivity.this,"Login Successfully", Toast.LENGTH_LONG ).show ();
                                Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();

                            }else {
                                Toast.makeText (LoginActivity.this,""+response, Toast.LENGTH_LONG ).show ();
                            }


                        }

                        @Override
                        public void onError(Object tag, Throwable e) {
                            Toast.makeText(LoginActivity.this, "登录失败" + e, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {
                            Toast.makeText(LoginActivity.this, "登录失败" + e, Toast.LENGTH_SHORT).show();

                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
