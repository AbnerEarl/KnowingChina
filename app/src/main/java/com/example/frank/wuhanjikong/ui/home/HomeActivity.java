package com.example.frank.wuhanjikong.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.fragment.AddInfoFragment;
import com.example.frank.wuhanjikong.fragment.HomeFragment;
import com.example.frank.wuhanjikong.fragment.PersonFragment;
import com.example.frank.wuhanjikong.fragment.SubmissionFragment;
import com.example.frank.wuhanjikong.fragment.TitleFragment;
import com.example.frank.wuhanjikong.fragment.WorkFragment;

public class HomeActivity extends FragmentActivity implements OnClickListener {

    private RadioButton rb_assignment;
    private RadioButton rb_complete;
    private RadioButton rb_submission;
    private RadioButton rb_addsubmission;
    private RadioButton rb_person;

    private RadioGroup rGroup;

    private Fragment title,f1,f2,f3,f4,f5;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        initView();

    }

    public void initView(){
        /**
         * 拿到事务管理器并开启事务
         */
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        /**
         * 初始化按钮
         */
        rb_assignment = (RadioButton) findViewById(R.id.rb_assignment);
        rb_complete = (RadioButton) findViewById(R.id.rb_complete);
        rb_submission = (RadioButton) findViewById(R.id.rb_submission);
        rb_addsubmission = (RadioButton) findViewById(R.id.rb_addsubmission);
        rb_person = (RadioButton) findViewById(R.id.rb_person);

        rGroup = (RadioGroup) findViewById(R.id.rg_menu);

        /**
         * 为三个按钮添加监听
         */
        rb_assignment.setOnClickListener(this);
        rb_complete.setOnClickListener(this);
        rb_submission.setOnClickListener(this);
        rb_addsubmission.setOnClickListener(this);
        rb_person.setOnClickListener(this);

        /**
         * 启动默认选中第一个
         */
        rGroup.check(R.id.rb_assignment);
        f1 = new HomeFragment();
        TitleFragment.setVisiblePerson();
        transaction.replace(R.id.fl_content, f1);
        transaction.commit();

    }


    @Override
    public void onClick(View v) {

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        switch (v.getId()) {
            case R.id.rb_assignment :
                /**
                 * 为了防止重叠，需要点击之前先移除其他Fragment
                 */
                hideFragment(transaction);
                f1 = new HomeFragment();
                TitleFragment.setVisiblePerson();
                transaction.replace(R.id.fl_content, f1);
                transaction.commit();
                TitleFragment.setTitle("search");
                break;
            case R.id.rb_complete :
                hideFragment(transaction);
                f2 = new WorkFragment();
                TitleFragment.setVisiblePerson();
                transaction.replace(R.id.fl_content, f2);
                transaction.commit();
                TitleFragment.setTitle("search");
                break;
            case R.id.rb_submission :
                hideFragment(transaction);
                f3 = new SubmissionFragment();
                TitleFragment.setVisiblePerson();
                transaction.replace(R.id.fl_content, f3);
                transaction.commit();
                TitleFragment.setTitle("search");
                break;
            case R.id.rb_person :
                hideFragment(transaction);
                f4 = new PersonFragment();
                transaction.replace(R.id.fl_content, f4);
                transaction.commit();
                TitleFragment.setVisiblePerson();
                TitleFragment.setTitle("search");
                break;
            case R.id.rb_addsubmission :
                hideFragment(transaction);
                f5 = new AddInfoFragment();
                transaction.replace(R.id.fl_content, f5);
                transaction.commit();
                TitleFragment.setInvisiblePerson();
                break;

            default :
                break;
        }
    }

    /*
     * 去除（隐藏）所有的Fragment
     * */
    private void hideFragment(FragmentTransaction transaction) {
        if (f1 != null) {
            //transaction.hide(f1);隐藏方法也可以实现同样的效果，不过我一般使用去除
            transaction.remove(f1);
        }
        if (f2 != null) {
            //transaction.hide(f2);
            transaction.remove(f2);
        }
        if (f3 != null) {
            //transaction.hide(f3);
            transaction.remove(f3);
        }


    }

}