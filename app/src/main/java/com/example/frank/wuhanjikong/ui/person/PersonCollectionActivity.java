package com.example.frank.wuhanjikong.ui.person;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.config.PersonInfo;
import com.example.frank.wuhanjikong.entity.ContentInfo;
import com.example.frank.wuhanjikong.fragment.HomeFragment;
import com.example.frank.wuhanjikong.log.L;
import com.example.frank.wuhanjikong.service.ApiService;
import com.example.frank.wuhanjikong.ui.home.ApplicationLoad;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonCollectionActivity extends AppCompatActivity {

    private MyAdapter myAdapter;
    private ListView lv_info;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_collection);

        lv_info = (ListView) this.findViewById(R.id.lv_collect_content);
        myAdapter = new MyAdapter(this);//得到一个MyAdapter对象
        lv_info.setAdapter(myAdapter);//为ListView绑定Adapter
        getMessageInfo(PersonInfo.localSysUser.getUserId());
        dialog = new ProgressDialog(this);
        dialog.setTitle("hint");
        dialog.setMessage("loading...");

    }



    /*private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

        *//*构造函数*//*
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public int getCount() {

            return listItem.size();//返回数组的长度
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        *//*书中详细解释该方法*//*
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            //观察convertView随ListView滚动情况
            Log.v("MyListViewBase", "getView " + position + " " + convertView);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.layout_info, null);
                holder = new ViewHolder();
                *//*得到各个控件的对象*//*

                holder.nickName = (TextView) convertView.findViewById(R.id.textView4);
                holder.publishTime = (TextView) convertView.findViewById(R.id.textView20);
                holder.discussInfo = (TextView) convertView.findViewById(R.id.textView5);
                holder.contentTitle = (TextView) convertView.findViewById(R.id.textView6);
                holder.contentInfo = (TextView) convertView.findViewById(R.id.textView22);
                holder.dianZan = (TextView) convertView.findViewById(R.id.textView21);
                holder.imageView=(ImageView) convertView.findViewById(R.id.img_logo);
                holder.img_share=(ImageView) convertView.findViewById(R.id.imageView4);
                holder.img_zan=(ImageView) convertView.findViewById(R.id.imageView5);
                holder.img_discuss=(ImageView) convertView.findViewById(R.id.imageView6);
                holder.img_collect=(ImageView) convertView.findViewById(R.id.imageView16);


                convertView.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
            }
            *//*设置TextView显示的内容，即我们存放在动态数组中的数据*//*

            try{
                holder.nickName.setText(listItem.get(position).get("nickName").toString());
                holder.publishTime.setText(listItem.get(position).get("time").toString());
                holder.contentTitle.setText(listItem.get(position).get("contentTitle").toString());
                holder.contentInfo.setText(listItem.get(position).get("contentInfo").toString());
                if (listItem.get(position).get("discussInfo")!=null){
                    holder.discussInfo.setText(listItem.get(position).get("discussInfo").toString());
                }else {
                    holder.discussInfo.setText("");
                }
                if (listItem.get(position).get("zan")!=null){
                    holder.dianZan.setText(listItem.get(position).get("zan").toString());
                }else {
                    holder.dianZan.setText("");
                }

                //点赞个数暂时不写
                // holder.dianZan.setText(listItem.get(position).get("discussInfo").toString());
                *//*Bitmap bm = BitmapFactory.decodeFile(listItem.get(position).get("ItemImage").toString());
                holder.imageView.setImageBitmap(bm);*//*
                *//*if (listItem.get(position).get("tag").toString().equals("1")){
                    holder.imageView.setImageResource(R.drawable.videologo);
                }else if (listItem.get(position).get("tag").toString().equals("2")){
                    holder.imageView.setImageResource(R.drawable.vvlogo3);
                }else if (listItem.get(position).get("tag").toString().equals("3")){
                    holder.imageView.setImageResource(R.drawable.vvlogo2);
                }else if (listItem.get(position).get("tag").toString().equals("4")){
                    holder.imageView.setImageResource(R.drawable.vedio_class);
                }else {
                    holder.imageView.setImageResource(R.drawable.vediotem);
                }*//*
                Glide.with(PersonCollectionActivity.this).load(listItem.get(position).get("url").toString()).into(holder.imageView);
                L.g("imgurl======",listItem.get(position).get("url").toString());


            }catch (Exception e){
                e.printStackTrace();
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText (PersonCollectionActivity.this,"正在加载！", Toast.LENGTH_LONG ).show ();
                    Intent intent=new Intent(PersonCollectionActivity.this, ApplicationLoad.class);
                    intent.putExtra("url",myAdapter.listItem.get(position).get("url").toString());
                    startActivity(intent);
                }
            });

            holder.img_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout linearLayout=new LinearLayout(PersonCollectionActivity.this);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout distinct=new LinearLayout(PersonCollectionActivity.this);
                    distinct.setBackgroundColor(Color.RED);
                   *//* ViewGroup.LayoutParams layoutParams=distinct.getLayoutParams();
                    layoutParams.height=5;
                    distinct.setLayoutParams(layoutParams);*//*

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 25, 5, 25);
                    TextView share=new TextView(PersonCollectionActivity.this);
                    share.setText("分享");
                    share.setTextSize(18);
                    share.setLayoutParams(lp);
                    share.setGravity(Gravity.CENTER);
                    TextView report=new TextView(PersonCollectionActivity.this);
                    report.setTextSize(18);
                    report.setGravity(Gravity.CENTER);
                    report.setLayoutParams(lp);
                    report.setText("举报");

                    linearLayout.addView(share);
                    linearLayout.addView(distinct);
                    linearLayout.addView(report);

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MYTask().execute( listItem.get(position).get("url").toString());
                        }
                    });


                    new AlertDialog.Builder(PersonCollectionActivity.this).setView(linearLayout).show();


                }
            });

            holder.img_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.dianZan.getText().toString().contains(PersonInfo.userName)){
                        Toast.makeText(PersonCollectionActivity.this,"已经点过赞了！",Toast.LENGTH_SHORT).show();
                    }else {

                        if (holder.dianZan.getText().toString().trim().equals("")){
                            //holder.dianZan.setText(PersonInfo.userName+"等人觉得赞。");
                            listItem.get(position).put("zan",PersonInfo.userName+"等人觉得赞。");
                        }else {
                            //holder.dianZan.setText(PersonInfo.localSysUser.getNickName()+"、"+holder.dianZan.getText().toString());
                            listItem.get(position).put("zan",PersonInfo.localSysUser.getNickName()+"、"+holder.dianZan.getText().toString());
                        }
                        HashMap<String ,String> map=new HashMap<>();
                        map.put("contentId",listItem.get(position).get("contentId").toString());
                        map.put("thumbPeoples",listItem.get(position).get("zan").toString());
                        addThumbPeople(JSON.toJSONString(map));

                    }


                }
            });

            holder.img_discuss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LinearLayout linearLayout=new LinearLayout(PersonCollectionActivity.this);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    final EditText discuss=new EditText(PersonCollectionActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 25, 5, 5);
                    discuss.setHint("please enter your comment……");
                    discuss.setLayoutParams(lp);
                    linearLayout.addView(discuss);

                    new AlertDialog.Builder(PersonCollectionActivity.this).setView(linearLayout).setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (discuss.getText()!=null&&!discuss.getText().toString().trim().equals("")){
                                // holder.discussInfo.setText(holder.discussInfo.getText()+"\n"+PersonInfo.userName+"："+discuss.getText());
                                if (holder.discussInfo.getText().toString().trim().equals("")){
                                    listItem.get(position).put("discussInfo",PersonInfo.userName+"："+discuss.getText());
                                }else {
                                    listItem.get(position).put("discussInfo",holder.discussInfo.getText()+"\n"+PersonInfo.userName+"："+discuss.getText());
                                }

                                HashMap<String ,String> map=new HashMap<>();
                                map.put("contentId",listItem.get(position).get("contentId").toString());
                                map.put("discussInfo",listItem.get(position).get("discussInfo").toString());
                                addDiscussInfo(JSON.toJSONString(map));
                            }else {
                                Toast.makeText(PersonCollectionActivity.this,"no comment",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).setNegativeButton("cancel",null).show();
                }
            });


            holder.img_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HashMap<String ,String> map=new HashMap<>();
                    map.put("contentId",listItem.get(position).get("contentId").toString());
                    map.put("userId",PersonInfo.localSysUser.getUserId());
                    addCollectionInfo(JSON.toJSONString(map));
                }
            });

            return convertView;
        }

    }

    *//*存放控件*//*
    public final class ViewHolder {
        public TextView publishTime,nickName,contentTitle,contentInfo,discussInfo,dianZan;
        public ImageView imageView,img_share,img_zan,img_discuss,img_collect;


    }*/

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

        /*构造函数*/
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public int getCount() {

            return listItem.size();//返回数组的长度
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        /*书中详细解释该方法*/
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            //观察convertView随ListView滚动情况
            Log.v("MyListViewBase", "getView " + position + " " + convertView);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.layout_info, null);
                holder = new ViewHolder();
                /*得到各个控件的对象*/

                holder.nickName = (TextView) convertView.findViewById(R.id.textView4);
                holder.publishTime = (TextView) convertView.findViewById(R.id.textView20);
                holder.discussInfo = (TextView) convertView.findViewById(R.id.textView5);
                holder.contentTitle = (TextView) convertView.findViewById(R.id.textView6);
                holder.contentInfo = (TextView) convertView.findViewById(R.id.textView22);
                holder.dianZan = (TextView) convertView.findViewById(R.id.textView21);
                holder.imageView=(ImageView) convertView.findViewById(R.id.img_logo);
                holder.img_share=(ImageView) convertView.findViewById(R.id.imageView4);
                holder.img_zan=(ImageView) convertView.findViewById(R.id.imageView5);
                holder.img_discuss=(ImageView) convertView.findViewById(R.id.imageView6);
                holder.img_collect=(ImageView) convertView.findViewById(R.id.imageView16);
                holder.videoView=(VideoView)convertView.findViewById(R.id.videoView);


                convertView.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
            }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/

            try{
                holder.nickName.setText(listItem.get(position).get("nickName").toString());
                holder.publishTime.setText(listItem.get(position).get("time").toString());
                holder.contentTitle.setText(listItem.get(position).get("contentTitle").toString());
                holder.contentInfo.setText(listItem.get(position).get("contentInfo").toString());
                if (listItem.get(position).get("discussInfo")!=null){
                    holder.discussInfo.setText(listItem.get(position).get("discussInfo").toString());
                }else {
                    holder.discussInfo.setText("");
                }
                if (listItem.get(position).get("zan")!=null){
                    holder.dianZan.setText(listItem.get(position).get("zan").toString());
                }else {
                    holder.dianZan.setText("");
                }

                //点赞个数暂时不写
                // holder.dianZan.setText(listItem.get(position).get("discussInfo").toString());
                /*Bitmap bm = BitmapFactory.decodeFile(listItem.get(position).get("ItemImage").toString());
                holder.imageView.setImageBitmap(bm);*/
                /*if (listItem.get(position).get("tag").toString().equals("1")){
                    holder.imageView.setImageResource(R.drawable.videologo);
                }else if (listItem.get(position).get("tag").toString().equals("2")){
                    holder.imageView.setImageResource(R.drawable.vvlogo3);
                }else if (listItem.get(position).get("tag").toString().equals("3")){
                    holder.imageView.setImageResource(R.drawable.vvlogo2);
                }else if (listItem.get(position).get("tag").toString().equals("4")){
                    holder.imageView.setImageResource(R.drawable.vedio_class);
                }else {
                    holder.imageView.setImageResource(R.drawable.vediotem);
                }*/

                if (listItem.get(position).get("contentType").toString().equals("3")){
                    //final String url=listItem.get(position).get("url").toString();
                    holder.videoView.setVisibility(View.INVISIBLE);
                    holder.imageView.setVisibility(View.VISIBLE);
                    int width=holder.imageView.getMaxWidth();
                    holder.imageView.setAdjustViewBounds(true);
                    holder.imageView.setMaxHeight(600);
                    RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(width,600);
                    param.addRule(RelativeLayout.BELOW,R.id.textView22);
                    holder.imageView.setLayoutParams(param);
                    Glide.with(PersonCollectionActivity.this).load(listItem.get(position).get("url").toString()).into(holder.imageView);
                    //holder.videoView.setVisibility(View.INVISIBLE);
                    //holder.imageView.setVisibility(View.VISIBLE);
                    //holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(maxWidth,180));
                    //   holder.videoView.setVideoPath(url);
                    //    holder.videoView.requestFocus();
                    //   holder.videoView.start();
                    //holder.imageView.setImageBitmap(createVideoThumbnail(url,400,400));;


                    //下面几行可以实现效果，其他的代码是测试用的
                    //holder.videoView.setFocusable(true);
                    //holder.videoView.setVideoPath(url);
                    // holder.videoView.seekTo(1);
                    //  holder.videoView.requestFocus();




                    /*holder.videoView.setVisibility(View.INVISIBLE);
                    holder.imageView.setVisibility(View.VISIBLE);

                            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                            Bitmap bitmap = null;
                            try {
                                //这里要用FileProvider获取的Uri
                                if (url.contains("http")) {
                                    retriever.setDataSource(url, new HashMap<String, String>());
                                } else {
                                    retriever.setDataSource(url);
                                }
                                bitmap = retriever.getFrameAtTime();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            } finally {
                                try {
                                    retriever.release();
                                } catch (RuntimeException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            //showImageMessage(bitmap, positionTag, vv);
                            holder.imageView.setImageBitmap(bitmap);*/



                }else if (listItem.get(position).get("contentType").toString().equals("2")){
                    holder.videoView.setVisibility(View.INVISIBLE);
                    holder.imageView.setVisibility(View.VISIBLE);
                    // holder.videoView.setFocusable(false);
                    int width=holder.imageView.getMaxWidth();

                    holder.imageView.setAdjustViewBounds(true);
                    holder.imageView.setMaxHeight(600);
                    RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(width,600);
                    param.addRule(RelativeLayout.BELOW,R.id.textView22);
                    holder.imageView.setLayoutParams(param);
                    Glide.with(PersonCollectionActivity.this).load(listItem.get(position).get("url").toString()).into(holder.imageView);
                    //L.g("imgurl======",listItem.get(position).get("url").toString());
                }else {
                    // holder.videoView.setLayoutParams(new RelativeLayout.LayoutParams(2,2));
                    //  holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(2,2));
                    holder.imageView.setAdjustViewBounds(true);
                    holder.imageView.setMaxHeight(1);
                    int width=holder.imageView.getMaxWidth();
                    RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(width,1);
                    //添加规则，示例 靠父控件最右边
                    // param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    //如果相对某个控件
                    param.addRule(RelativeLayout.BELOW,R.id.textView22);
                    //添加控件
                    // addView(imageview,param);
                    //holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(width,1));
                    holder.imageView.setLayoutParams(param);
                    L.g("设置了最大高度为1.");
                    holder.videoView.setVisibility(View.INVISIBLE);
                    holder.imageView.setVisibility(View.INVISIBLE);
                }



            }catch (Exception e){
                e.printStackTrace();
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText (PersonCollectionActivity.this,"loading！", Toast.LENGTH_LONG ).show ();
                    Intent intent=new Intent(PersonCollectionActivity.this, ApplicationLoad.class);
                    intent.putExtra("url",myAdapter.listItem.get(position).get("url").toString());
                    if (myAdapter.listItem.get(position).get("vedioUrl")!=null){
                        intent.putExtra("vedioUrl",myAdapter.listItem.get(position).get("vedioUrl").toString());
                    }else {
                        intent.putExtra("vedioUrl",myAdapter.listItem.get(position).get("url").toString());
                    }

                    intent.putExtra("contentType",listItem.get(position).get("contentType").toString());
                    intent.putExtra("contentId",listItem.get(position).get("contentId").toString());
                    intent.putExtra("title",myAdapter.listItem.get(position).get("contentTitle").toString());
                    intent.putExtra("content",myAdapter.listItem.get(position).get("contentInfo").toString());
                    startActivity(intent);
                }
            });


            holder.contentInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText (PersonCollectionActivity.this,"loading！", Toast.LENGTH_LONG ).show ();
                    Intent intent=new Intent(PersonCollectionActivity.this, ApplicationLoad.class);
                    if (!listItem.get(position).get("contentType").toString().equals("1")) {
                        intent.putExtra("url", myAdapter.listItem.get(position).get("url").toString());
                        if (myAdapter.listItem.get(position).get("vedioUrl") != null) {
                            intent.putExtra("vedioUrl", myAdapter.listItem.get(position).get("vedioUrl").toString());
                        } else {
                            intent.putExtra("vedioUrl", myAdapter.listItem.get(position).get("url").toString());
                        }
                    }
                    intent.putExtra("contentType",listItem.get(position).get("contentType").toString());
                    intent.putExtra("contentId",listItem.get(position).get("contentId").toString());
                    intent.putExtra("title",myAdapter.listItem.get(position).get("contentTitle").toString());
                    intent.putExtra("content",myAdapter.listItem.get(position).get("contentInfo").toString());
                    startActivity(intent);
                }
            });


            /*holder.videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText (PersonCollectionActivity.this,"正在加载！", Toast.LENGTH_LONG ).show ();
                    Intent intent=new Intent(getActivity(), ApplicationLoad.class);
                    intent.putExtra("url",myAdapter.listItem.get(position).get("url").toString());
                    intent.putExtra("contentType",listItem.get(position).get("contentType").toString());
                    intent.putExtra("contentId",listItem.get(position).get("contentId").toString());
                    intent.putExtra("title",myAdapter.listItem.get(position).get("contentTitle").toString());
                    intent.putExtra("content",myAdapter.listItem.get(position).get("contentInfo").toString());
                    startActivity(intent);
                }
            });*/

            /*holder.videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    Toast.makeText (PersonCollectionActivity.this,"正在加载！", Toast.LENGTH_LONG ).show ();
                    Intent intent=new Intent(getActivity(), ApplicationLoad.class);
                    intent.putExtra("url",myAdapter.listItem.get(position).get("url").toString());
                    intent.putExtra("contentType",listItem.get(position).get("contentType").toString());
                    intent.putExtra("contentId",listItem.get(position).get("contentId").toString());
                    intent.putExtra("title",myAdapter.listItem.get(position).get("contentTitle").toString());
                    intent.putExtra("content",myAdapter.listItem.get(position).get("contentInfo").toString());
                    startActivity(intent);
                    return false;
                }
            });*/

            holder.img_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout linearLayout=new LinearLayout(PersonCollectionActivity.this);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout distinct=new LinearLayout(PersonCollectionActivity.this);
                    distinct.setBackgroundColor(Color.RED);
                   /* ViewGroup.LayoutParams layoutParams=distinct.getLayoutParams();
                    layoutParams.height=5;
                    distinct.setLayoutParams(layoutParams);*/

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 25, 5, 25);
                    TextView share=new TextView(PersonCollectionActivity.this);
                    share.setText("share");
                    share.setTextSize(18);
                    share.setLayoutParams(lp);
                    share.setGravity(Gravity.CENTER);
                    TextView report=new TextView(PersonCollectionActivity.this);
                    report.setTextSize(18);
                    report.setGravity(Gravity.CENTER);
                    report.setLayoutParams(lp);
                    report.setText("report");

                    linearLayout.addView(share);
                    linearLayout.addView(distinct);
                    linearLayout.addView(report);

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MYTask().execute( listItem.get(position).get("url").toString());
                        }
                    });


                    new AlertDialog.Builder(PersonCollectionActivity.this).setView(linearLayout).show();


                }
            });

            holder.img_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.dianZan.getText().toString().contains(PersonInfo.userName)){
                        Toast.makeText(PersonCollectionActivity.this," liked it！",Toast.LENGTH_SHORT).show();
                    }else {

                        if (holder.dianZan.getText().toString().trim().equals("")){
                            //holder.dianZan.setText(PersonInfo.userName+"等人觉得赞。");
                            listItem.get(position).put("zan",PersonInfo.userName+" like it。");
                        }else {
                            //holder.dianZan.setText(PersonInfo.localSysUser.getNickName()+"、"+holder.dianZan.getText().toString());
                            listItem.get(position).put("zan",PersonInfo.localSysUser.getNickName()+"、"+holder.dianZan.getText().toString());
                        }
                        HashMap<String ,String> map=new HashMap<>();
                        map.put("contentId",listItem.get(position).get("contentId").toString());
                        map.put("thumbPeoples",listItem.get(position).get("zan").toString());
                        addThumbPeople(JSON.toJSONString(map));

                    }


                }
            });

            holder.img_discuss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LinearLayout linearLayout=new LinearLayout(PersonCollectionActivity.this);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    final EditText discuss=new EditText(PersonCollectionActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 25, 5, 5);
                    discuss.setHint("Please enter your comment……");
                    discuss.setLayoutParams(lp);
                    linearLayout.addView(discuss);

                    new AlertDialog.Builder(PersonCollectionActivity.this).setView(linearLayout).setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (discuss.getText()!=null&&!discuss.getText().toString().trim().equals("")){
                                // holder.discussInfo.setText(holder.discussInfo.getText()+"\n"+PersonInfo.userName+"："+discuss.getText());
                                if (holder.discussInfo.getText().toString().trim().equals("")){
                                    listItem.get(position).put("discussInfo",PersonInfo.userName+"："+discuss.getText());
                                }else {
                                    listItem.get(position).put("discussInfo",holder.discussInfo.getText()+"\n"+PersonInfo.userName+"："+discuss.getText());
                                }

                                HashMap<String ,String> map=new HashMap<>();
                                map.put("contentId",listItem.get(position).get("contentId").toString());
                                map.put("discussInfo",listItem.get(position).get("discussInfo").toString());
                                addDiscussInfo(JSON.toJSONString(map));
                            }else {
                                Toast.makeText(PersonCollectionActivity.this,"No comments were entered",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).setNegativeButton("cancel",null).show();
                }
            });


            holder.img_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HashMap<String ,String> map=new HashMap<>();
                    map.put("contentId",listItem.get(position).get("contentId").toString());
                    map.put("userId",PersonInfo.localSysUser.getUserId());
                    addCollectionInfo(JSON.toJSONString(map));
                }
            });

            return convertView;
        }

    }

    /*存放控件*/
    public final class ViewHolder {
        public TextView publishTime,nickName,contentTitle,contentInfo,discussInfo,dianZan;
        public ImageView imageView,img_share,img_zan,img_discuss,img_collect;
        public VideoView videoView;


    }

    private void getMessageInfo(final String userId){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", userId);
                    ApiService.GetString(PersonCollectionActivity.this, "getContentInfoByUserId", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            try{
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                if (!response.contains("no data")&&!response.contains("failed to search")){
                                    myAdapter.listItem.clear();
                                    List<ContentInfo> contentInfoList = JSON.parseObject(response,new TypeReference< List<ContentInfo>>(){});
                                    for (ContentInfo contentInfo:contentInfoList){
                                        HashMap<String,Object> map=new HashMap<>();
                                        map.put("nickName",contentInfo.getOtherInfo());
                                        map.put("contentTitle",contentInfo.getContentTitle());
                                        map.put("contentInfo",contentInfo.getContentText());
                                        map.put("contentId",contentInfo.getContentId());
                                        map.put("contentType",contentInfo.getContentStyle());
                                        map.put("vedioUrl",contentInfo.getVedioUrl());
                                        map.put("tag","1");
                                        map.put("time",sdf.format(contentInfo.getPublishTime()));
                                        map.put("discussInfo",contentInfo.getDiscussInfo());
                                        map.put("url",contentInfo.getFileUrl());
                                        map.put("zan",contentInfo.getThumbPeoples());
                                        myAdapter.listItem.add(map);
                                    }
                                    myAdapter.notifyDataSetChanged();
                                }


                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Object tag, Throwable e) {

                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {

                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    private void addThumbPeople(final String dataInfo){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", dataInfo);
                    ApiService.GetString(PersonCollectionActivity.this, "addThumbPeople", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            if (response.contains("true")){
                                new AlertDialog.Builder(PersonCollectionActivity.this).setMessage("success！").setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                                myAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(Object tag, Throwable e) {

                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {

                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }




    private void addDiscussInfo(final String discussInfo){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", discussInfo);
                    ApiService.GetString(PersonCollectionActivity.this, "addDiscussInfo", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            if (response.equals("true")){
                                new AlertDialog.Builder(PersonCollectionActivity.this).setMessage("comment successful！").setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                                myAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(Object tag, Throwable e) {

                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {

                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    private void addCollectionInfo(final String collectionInfo) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", collectionInfo);
                    ApiService.GetString(PersonCollectionActivity.this, "addCollectionInfo", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {

                            new AlertDialog.Builder(PersonCollectionActivity.this).setMessage(response).setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                        }

                        @Override
                        public void onError(Object tag, Throwable e) {

                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void SharePhoto(String photoUri,final Activity activity) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(photoUri);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("*/*");
        startActivity(Intent.createChooser(shareIntent, activity.getTitle()));
    }


    public class MYTask extends AsyncTask<String, Void, Bitmap> {
        /**
         * 表示任务执行之前的操作
         */
        String url="";
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog.show();
        }

        /**
         * 主要是完成耗时的操作
         */
        @Override
        protected Bitmap doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            // 使用网络连接类HttpClient类王城对网络数据的提取
            url=arg0[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(arg0[0]);
            Bitmap bitmap = null;
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    HttpEntity httpEntity = httpResponse.getEntity();
                    byte[] data = EntityUtils.toByteArray(httpEntity);
                    bitmap = BitmapFactory
                            .decodeByteArray(data, 0, data.length);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //imageView.setImageBitmap(result);
            /*Random random=new Random();
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+random.nextInt(1000);*/
            saveBitmap(result,url);
//            dialog.dismiss();
        }
    }


    private void saveBitmap(Bitmap bitmap,String image_Path) {
        String share_img_path="";
        File appDir = new File(Environment.getExternalStorageDirectory(), "KnowingChina");
        if (!appDir.exists()) appDir.mkdir();
        String[] str = image_Path.split("/");
        String fileName = str[str.length - 1];
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            share_img_path=appDir+"/"+fileName;
            //file.getAbsolutePath();//获取保存的图片的文件名
            //    onSaveSuccess(file);
        } catch (IOException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(PersonCollectionActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
            });
            e.printStackTrace();
        }
        dialog.dismiss();
        SharePhoto(share_img_path,PersonCollectionActivity.this);

    }



}
