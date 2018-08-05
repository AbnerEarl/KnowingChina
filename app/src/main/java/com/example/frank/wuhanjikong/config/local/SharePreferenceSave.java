package com.example.frank.wuhanjikong.config.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;


public class SharePreferenceSave {
    //保存账号和登录密码到data.xml中
    public static boolean saveUserInfo(Context context, String number, String password){
        SharedPreferences sp = context.getSharedPreferences ( "data", context.MODE_PRIVATE );

        SharedPreferences.Editor edit = sp.edit ();

        edit.putString ( "username",number );

        edit.putString ( "pwd",password );

        edit.commit ();
        return true;
    }
    //从data.xml文件中获取存储的账号和密码
    public static Map<String,String> getUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences ( "data", context.MODE_PRIVATE );
        String number = sp.getString ( "username", "" );
        String password = sp.getString ( "pwd", "" );

        Map<String,String> userMap = new HashMap<>(  );

        userMap.put ( "number", number );
        userMap.put ( "password", password );

        return userMap;

    }

}
