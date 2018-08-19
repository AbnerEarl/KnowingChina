package com.example.frank.wuhanjikong.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.db.DatabaseHelper;
import com.example.frank.wuhanjikong.entity.User;

import java.sql.SQLException;
import java.util.List;

public class TestDbEntityActivity extends AppCompatActivity {

    private Button btn_test_dbentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db_entity);

        btn_test_dbentity=(Button)this.findViewById(R.id.btn_test_db_entity);
        //testAddUser();
        btn_test_dbentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testList();
            }
        });


    }



    //增加数据
    public void testAddUser(){
        //实体化一个实体类
        User u1 = new User("zhy1", "1B青年");
        //获得数据库操作实例
        DatabaseHelper helper = DatabaseHelper.getHelper(TestDbEntityActivity.this);
        try{
            //通过实例获得UserDao，根据框架自带的方法，进行增加,框架自带了三种常用的方法，createOrUpdate(),createIfNotExists(),create(),根据
            // 不同情况选择自己需要的方法

            helper.getUserDao().create(u1);
            u1 = new User("zhy2", "2B青年");
            helper.getUserDao().create(u1);
            u1 = new User("zhy3", "3B青年");
            helper.getUserDao().create(u1);
            u1 = new User("zhy4", "4B青年");
            helper.getUserDao().create(u1);
            u1 = new User("zhy5", "5B青年");
            helper.getUserDao().create(u1);
            u1 = new User("zhy6", "6B青年");
            helper.getUserDao().create(u1);

            testList();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //删除数据
    public void testDeleteUser(){
        DatabaseHelper helper = DatabaseHelper.getHelper(TestDbEntityActivity.this);
        try{
            //根据自带的方法，在不同的情况下，选择不同的方法进行操作
            helper.getUserDao().deleteById(2);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //更新数据
    public void testUpdateUser(){
        DatabaseHelper helper = DatabaseHelper.getHelper(TestDbEntityActivity.this);
        try{
            User u1 = new User("zhy-android", "2B青年");
            u1.setId(3);
            helper.getUserDao().update(u1);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //查询数据
    public void testList(){
        DatabaseHelper helper = DatabaseHelper.getHelper(TestDbEntityActivity.this);
        try {
            User u1 = new User("zhy-android", "2B青年");
            u1.setId(2);
            //List<User> users = helper.getUserDao().queryForAll();
            User user = helper.getUserDao().queryForId(2);

           if (user!=null){
               Log.e("TAG", "姓名："+user.getName().toString()+"    属性："+user.getDesc());
           }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }





}
