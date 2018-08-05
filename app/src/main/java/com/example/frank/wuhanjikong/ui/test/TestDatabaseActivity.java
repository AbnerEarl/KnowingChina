package com.example.frank.wuhanjikong.ui.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.db.sqlite.mySQLite;

public class TestDatabaseActivity extends AppCompatActivity {

    private mySQLite myDatebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);

        myDatebaseHelper = new mySQLite(this, "people.db", null, 1);

    }




    //创建数据库
    public void Create_Datebase(View v){

        /*
         * 如果数据库没有被创建，就创建并获取一个可写（其实也可以读）的数据库 如果数据库被创建，就直接获取一个可写（其实也可以读）的数据库
         * 路径data/data/database/people.db
         */
        myDatebaseHelper.getWritableDatabase();

    }
    //使用api添加数据
    public void Add_Data(View v){
        //拿到数据库对象
        SQLiteDatabase db = myDatebaseHelper.getWritableDatabase();
        /*
         * 将要添加的数据封装在ContentValues对象中
         * */
        ContentValues values = new ContentValues();
        values.put("Name", "JoyceChu");
        values.put("Age", 18);
        values.put("Phone", "1380013");
        /*
         * 第一个参数，表名
         * 第二个参数，一般传入null
         * 第三个参数，ContentValues对象
         * 注意，db.insert("Gril", null, values);这条语句有返回值，如果返回-1表示插入数据失败
         * 所以，我们可以通过返回值判断是否插入成功
         * */
        db.insert("Girl", null, values);
        values.clear();
        values.put("Name", "四叶草");
        values.put("Age", 19);
        values.put("Phone", "1380013");
        db.insert("Girl", null, values);
        values.clear();
        values.put("Name", "朱主爱");
        values.put("Age", 19);
        values.put("Phone", "1380013");
        db.insert("Girl", null, values);
        values.clear();
        values.put("Name", "新垣结衣");
        values.put("Age", 19);
        values.put("Phone", "1380013");
        db.insert("Girl", null, values);
        values.clear();
        values.put("Name", "刘诗诗");
        values.put("Age", 19);
        values.put("Phone", "1380013");
        db.insert("Girl", null, values);
        values.clear();
        values.put("Name", "金莎");
        values.put("Age", 25);
        values.put("Phone", "1380013");
        db.insert("Girl", null, values);
        values.clear();
        values.put("Name", "林志玲");
        values.put("Age", 25);
        values.put("Phone", "1380013");
        db.insert("Girl", null, values);
        Toast.makeText(TestDatabaseActivity.this, "Add Successded", Toast.LENGTH_LONG).show();

    }

    //更新数据
    public void Update_Data(View v){

        SQLiteDatabase db = myDatebaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //将年龄改为25
        values.put("age", 25);
        /*
         * 第一个参数：表名
         * 第二个参数：ContentValues对象
         * 第三，第四个参数：约束条件，如果传入null，表示更新所有行。
         * 本例子表示将 Name = 刘诗诗 ，的age 修改为 25 ，如果没有约束，就所有age都变成25
         * update也是有返回值的，返回int型，表示影响的行数
         * */
        db.update("Girl", values, "Name = ?", new String[]{"刘诗诗"});
        Toast.makeText(TestDatabaseActivity.this, "Update Successded", Toast.LENGTH_LONG).show();
    }

    //删除数据
    public void Delete_Data(View v){
        SQLiteDatabase db = myDatebaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
         /*
         * 第一个参数：表名
         * 第二个参数：ContentValues对象
         * 第三，第四个参数：约束条件，如果传入null，表示更新所有行。
         * 本例子表示将 age > 19的行都删除
         * */
        db.delete("Girl", "Name = ?", new String[]{"刘诗诗"});
        Toast.makeText(TestDatabaseActivity.this, "Delete Successded", Toast.LENGTH_LONG).show();
    }
    //查询数据
    public void Query_Data(View v){
        SQLiteDatabase db = myDatebaseHelper.getWritableDatabase();
        /*
         * 查询的参数非常多。这里我们选择七个参数的重载
         * 第一个参数：表名
         * 第二个参数：指定查询那几列，没指定默认查所有列
         * 第三，四个参数：约束查询，没指定默认查所有列
         * 第五个参数：指定要去group by的列，没指定的话，就不对查询结果group by
         * 第六个参数：group by之后的过滤，没指定不过滤
         * 第七个参数：查询结果排序方式，没指定使用默认排序
         * 本例子传入null表示查询所有列
         * 查询方法返回一个Cursor对象
         * */

        Cursor cursor =  db.query("Girl", null, null, null, null, null, null);
        //如果没有下一行，就表示数据检索完毕了
        while(cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex("Name"));
            int age = cursor.getInt(cursor.getColumnIndex("Age"));
            String phone = cursor.getString(cursor.getColumnIndex("Phone"));
            Toast.makeText(TestDatabaseActivity.this, name+","+age+","+phone, Toast.LENGTH_LONG).show();

        }
    }




}
