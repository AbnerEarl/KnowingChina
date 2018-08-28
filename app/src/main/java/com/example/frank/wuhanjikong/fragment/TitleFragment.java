package com.example.frank.wuhanjikong.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.log.L;
import com.example.frank.wuhanjikong.ui.home.PersonalActivity;


public class TitleFragment extends Fragment {

    private static EditText search_keyword;

    private static ImageView person,startSearch;


    public TitleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_title, container, false);
        search_keyword=view.findViewById(R.id.editText5);

        person=view.findViewById(R.id.menu_more);
        startSearch=view.findViewById(R.id.imageButton_search);

        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if (search_keyword.getText().toString().trim().length()>0){
                    startSearch.setVisibility(View.VISIBLE);
                }else {
                    startSearch.setVisibility(View.INVISIBLE);
                }

            }
        };
       // handler.postDelayed(runnable,1000);

        startSearch.setVisibility(View.INVISIBLE);

        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), PersonalActivity.class);
                startActivity(intent);
            }
        });

        search_keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.g("点击：","onclick");
                //startSearch.setVisibility(View.VISIBLE);
            }
        });

        search_keyword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (search_keyword.getText().toString().trim().length()>0){
                    startSearch.setVisibility(View.VISIBLE);
                }else {
                    startSearch.setVisibility(View.INVISIBLE);
                }

                L.g("点击：","touch");

                return false;
            }
        });



        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.KEYWORDS=search_keyword.getText().toString();
                HomeFragment.handler.postDelayed(HomeFragment.runnable,200);
                L.g("执行搜索:"+search_keyword.getText().toString());
            }
        });

        return view;
    }

   /* public static void setTitle(String title){
        title_text.setText(title);
    }*/
    public static void setInvisiblePerson(){
        person.setVisibility(View.INVISIBLE);
    }
    public static void setVisiblePerson(){
        person.setVisibility(View.VISIBLE);
    }
}
