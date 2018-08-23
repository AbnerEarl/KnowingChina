package com.example.frank.wuhanjikong.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.ui.home.PersonalActivity;


public class TitleFragment extends Fragment {

    private static EditText search_keyword;

    private static ImageView person;


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

        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), PersonalActivity.class);
                startActivity(intent);
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
