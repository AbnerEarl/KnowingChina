package com.example.frank.wuhanjikong.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.crash.PgyerCrashObservable;
import com.pgyersdk.crash.PgyerObserver;
import com.pgyersdk.feedback.PgyerFeedbackManager;

/**
 * Created by frank on 15/11/19.
 */
public class PgyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //PgyCrashManager.register(getApplicationContext());  // 弃用
        PgyCrashManager.register();
        PgyerCrashObservable.get().attach(new PgyerObserver() {
            @Override
            public void receivedCrash(Thread thread, Throwable throwable) {

            }
        });
        new PgyerFeedbackManager.PgyerFeedbackBuilder()
                .setShakeInvoke(true)
//                        .setColorDialogTitle("")    //设置Dialog 标题栏的背景色，默认为颜色为#ffffff
//                        .setColorTitleBg("")        //设置Dialog 标题的字体颜色，默认为颜色为#2E2D2D
                .setDisplayType(PgyerFeedbackManager.TYPE.DIALOG_TYPE)   //设置以Dialog 的方式打开
                .setMoreParam("KEY1","VALUE1")
                .setMoreParam("KEY2","VALUE2")
                .builder()
                .register();

    }


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
