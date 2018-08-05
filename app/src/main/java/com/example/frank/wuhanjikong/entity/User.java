package com.example.frank.wuhanjikong.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * PROJECT_NAME:WuHanJiKong
 * PACKAGE_NAME:com.example.frank.wuhanjikong.entity
 * USER:Frank
 * DATE:2018/6/26
 * TIME:21:56
 * DAY_NAME_FULL:星期二
 * DESCRIPTION:On the description and function of the document
 **/

/*
* 当定义表时，第一个建议便是使用final变量定义数据库表名和列名，
* 该方法可以简化代码的维护工作，不过本例并没用使用*/


@DatabaseTable(tableName = "tb_user") //@DatabaseTable：标明这是数据库的一张表
public class User {
    /*@DatabaseField：标明这是表中的字段
      columnName: 为该字段在数据中的列名
      generatedId：表示id为自增长*/
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "desc")
    private String desc;

    /*ORMLite需要用到无参构造方法
    * 当ORMLite需要创建User类时会使用到无参数的构造方法，
    * 并通过反射机制设置成员变量，也可以使用setter方法设置成员变量*/
    public User() {
    }

    public User(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
