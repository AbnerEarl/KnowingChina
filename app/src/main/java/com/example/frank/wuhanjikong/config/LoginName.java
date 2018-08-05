package com.example.frank.wuhanjikong.config;

import android.app.Activity;

import com.example.frank.wuhanjikong.config.local.SharePreferenceSave;

import java.util.Map;

/**
 * PROJECT_NAME:WuHanJiKong
 * PACKAGE_NAME:com.example.frank.wuhanjikong.config
 * USER:Frank
 * DATE:2018/7/7
 * TIME:13:08
 * DAY_NAME_FULL:星期六
 * DESCRIPTION:On the description and function of the document
 **/
public class LoginName {
    public static String getLoginName(Activity activity){
        Map<String, String> userInfo= SharePreferenceSave.getUserInfo (activity);
        String loginName="";
        if (userInfo!=null){
            loginName= userInfo.get("number").toString();
        }

        return loginName;
    }
}
