package com.example.frank.wuhanjikong.db.sqlite;

/**
 * PROJECT_NAME:WuHanJiKong
 * PACKAGE_NAME:com.example.frank.wuhanjikong.db.sqlite
 * USER:Frank
 * DATE:2018/6/26
 * TIME:20:42
 * DAY_NAME_FULL:星期二
 * DESCRIPTION:On the description and function of the document
 **/
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

//创建SQLLite需要继承SQLiteOpenHelper类抽象类
public class mySQLite extends SQLiteOpenHelper{

    public static final String CREATE_GIRL = "create table Girl(_id integer primary key autoincrement,Name char(20),Age integer,Phone char(20))" ;
    private Context mContext;

    /*
     * 构造方法参数说明
     * 第一个：传入上下文对象
     * 第二个：要创建的数据库名字
     * 第三个：油标工厂,传入油标对象，其实就是一个指针的功能。和ResultSet功能差不多，这个参数一般传入null
     * 第四个:数据库版本号，用于升级的时候调用。版本号必须大于1
     * 构造方法四个参数传入其实是给父类调用的。
     * */
    public mySQLite(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    //数据库被创建的时候会调用这个方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        /*db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext,"Create Successded",0).show();*/
        db.execSQL(CREATE_GIRL);
        Toast.makeText(mContext,"Create Successded",Toast.LENGTH_LONG).show();
    }
    //数据库升级的时候调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub


    }

}