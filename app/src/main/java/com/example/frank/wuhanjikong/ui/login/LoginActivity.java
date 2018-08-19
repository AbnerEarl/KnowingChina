package com.example.frank.wuhanjikong.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.config.PersonInfo;
import com.example.frank.wuhanjikong.config.local.SharePreferenceSave;
import com.example.frank.wuhanjikong.log.L;
import com.example.frank.wuhanjikong.service.ApiService;
import com.example.frank.wuhanjikong.ui.home.HomeActivity;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etNumber;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
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

        //给按钮设置点击事件
        btnLogin.setOnClickListener ( this );
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
                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data",loginName+"##"+passWord);
                    ApiService.GetString(LoginActivity.this, "loginMobile", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {

                            if (response.trim().equals("登录成功")){


                                //保存用户的信息。
                               /* try {
                                    boolean  isSaveSucess= FileSave.saveUserInfo ( this,number,password );
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace ();
                                }*/

                                boolean isSaveSucess = SharePreferenceSave.saveUserInfo (LoginActivity.this, loginName, passWord );
                                if (isSaveSucess){
                                    //Toast.makeText ( this,"保存成功", Toast.LENGTH_LONG ).show ();
                                    L.g("Login Successfully");
                                }else {
                                    //Toast.makeText ( this,"保存失败", Toast.LENGTH_LONG ).show ();
                                    L.g("保存失败");
                                }

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
