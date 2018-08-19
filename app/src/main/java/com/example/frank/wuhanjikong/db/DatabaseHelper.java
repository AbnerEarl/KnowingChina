package com.example.frank.wuhanjikong.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.frank.wuhanjikong.entity.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * PROJECT_NAME:WuHanJiKong
 * PACKAGE_NAME:com.example.frank.wuhanjikong.db
 * USER:Frank
 * DATE:2018/6/26
 * TIME:21:57
 * DAY_NAME_FULL:星期二
 * DESCRIPTION:On the description and function of the document
 **/


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    //数据库文件的名称
    private static final String DATA_BASE_NAME = "sqlit-test.db";

    /*userDao , 每张表对应一个*/
    private Dao<User, Integer> userDao;

    private DatabaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try{
            //创建表
            TableUtils.createTable(connectionSource, User.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try{
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static DatabaseHelper instance;

    /*
    * 单例获取该Helper*/
    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null){
            synchronized (DatabaseHelper.class){
                if (instance == null){
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    /*
    * 获得userDao*/
    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null){
            userDao = getDao(User.class);
        }
        return userDao;
    }

    /*
    * 释放资源*/
    public void close(){
        super.close();
        userDao=null;
    }
}
