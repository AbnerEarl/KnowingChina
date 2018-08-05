package com.example.frank.wuhanjikong.config.local;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class FileSave {
    //保存QQ账号和登录 都Data.txt
    public static boolean saveUserInfo(Context context, String number, String password) throws FileNotFoundException {
        try {
            //通过上下文获取文件输出流
            FileOutputStream fos = context.openFileOutput ( "data.txt",context.MODE_PRIVATE );
            //把数据写到文件中
            fos.write ( (number+":"+password).getBytes () );
            fos.close ();
            return true;
        } catch (IOException e) {
            e.printStackTrace ();
            return false;
        }
    }

    //读取Map中 从data.txt文件中读取QQ账号和密码
    public static Map<String,String> getUserInfo(Context context){
        String content = "";

        try {

            FileInputStream fis = context.openFileInput ( "data.txt" );
            byte[] buffer = new byte[fis.available ()];
            fis.read (buffer);
            Map<String,String> userMap = new HashMap<String, String>(  );
            content = new String( buffer );
            String[] infos = content.split ( ":" );
            userMap.put ( "number",infos[0] );
            userMap.put ( "password",infos[1] );
            fis.close ();

            return userMap;

        } catch (IOException e) {
            return null;
        }

    }


}
