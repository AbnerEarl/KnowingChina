package com.example.frank.wuhanjikong.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.example.frank.wuhanjikong.fragment.TestFragment;
import com.example.frank.wuhanjikong.fragment.TitleFragment;
import com.example.frank.wuhanjikong.fragment.WorkFragment;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_bar);

        BottomBar bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                .addItem(HomeFragment.class,
                        "ChinaTown",
                        R.drawable.home,
                        R.drawable.home_red)
                .addItem(PersonFragment.class,
                        "Discover",
                        R.drawable.discover,
                        R.drawable.discover_red)
                .addItem(AddInfoFragment.class,
                        "",
                        R.drawable.addin,
                        R.drawable.addin)
                .addItem(SubmissionFragment.class,
                        "Map",
                        R.drawable.map,
                        R.drawable.map_red)
                .addItem(WorkFragment.class,
                        "Mail",
                        R.drawable.mail,
                        R.drawable.mail_red)
                .build();
    }
}
