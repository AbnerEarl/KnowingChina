package com.example.frank.wuhanjikong.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.frank.wuhanjikong.config.PublicInfo;
import com.example.frank.wuhanjikong.entity.ContentInfo;
import com.example.frank.wuhanjikong.log.L;
import com.example.frank.wuhanjikong.service.ApiService;
import com.example.frank.wuhanjikong.ui.home.ApplicationLoad;
import com.example.frank.wuhanjikong.ui.home.HomeActivity;
import com.example.frank.wuhanjikong.ui.home.VideoActivity;
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


public class HomeFragment extends Fragment {

    private MyAdapter myAdapter;
    private ListView lv_info;
    private TextView textViewCation,textViewRecommand,textViewNear,textViewClass;
    private ProgressDialog dialog;
    private  int startIndex=0,contengPlate=1;
    private  int numberShow=5;
    private  int firstVisibleItemTag=0;
    private static boolean requestFlag=false;
    private int totalItemFlag=0;
    private int maxWidth=200;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        maxWidth=dm2.widthPixels-8;
        init(view);

        lv_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                /*Toast.makeText (getContext(),"正在加载！", Toast.LENGTH_LONG ).show ();
                Intent intent=new Intent(getActivity(), ApplicationLoad.class);
                intent.putExtra("url",myAdapter.listItem.get(arg2).get("url").toString());
                startActivity(intent);*/


            }
        });

        textViewCation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // myAdapter.listItem.clear();
                /*myAdapter.listItem=PublicInfo.listItemCation;
                myAdapter.notifyDataSetChanged();*/
                contengPlate=1;
                getMessageInfo(1,startIndex,numberShow,true);
            }
        });

        textViewRecommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myAdapter.listItem.clear();
                /*myAdapter.listItem=PublicInfo.listItemRecommand;
                myAdapter.notifyDataSetChanged();*/
                contengPlate=2;
                getMessageInfo(2,startIndex,numberShow,true);
            }
        });

        textViewNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myAdapter.listItem.clear();
                /*myAdapter.listItem=PublicInfo.listItemNear;
                myAdapter.notifyDataSetChanged();*/
                contengPlate=3;
                getMessageInfo(3,startIndex,numberShow,true);
            }
        });

        textViewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myAdapter.listItem.clear();
                /*myAdapter.listItem=PublicInfo.listItemClass;
                myAdapter.notifyDataSetChanged();*/
                contengPlate=4;
                getMessageInfo(4,startIndex,numberShow,true);
            }
        });

        return view;
    }


    private void init(View view) {

        lv_info = (ListView) view.findViewById(R.id.lv_info);
        myAdapter = new MyAdapter(getContext());//得到一个MyAdapter对象
        lv_info.setAdapter(myAdapter);//为ListView绑定Adapter
        textViewCation=(TextView)view.findViewById(R.id.textView);
        textViewRecommand=(TextView)view.findViewById(R.id.textView2);
        textViewNear=(TextView)view.findViewById(R.id.textView3);
        textViewClass=(TextView)view.findViewById(R.id.textView15);

        dialog = new ProgressDialog(getContext());
        dialog.setTitle("提示信息");
        dialog.setMessage("正在处理，请稍候...");


        /*for (int i=0;i<10;i++){
            HashMap<String,Object> map=new HashMap<>();
            map.put("nickName","有意思的事");
            map.put("contentInfo","谁能看懂这个视频的内容？");
            map.put("tag","1");
            map.put("time",(i+1)+"分钟前");
            map.put("discussInfo","Frank:这个视频说的很有趣！\nSwollen：这个一定点赞！\nJerry：没看懂什么意思啊？？？");
            map.put("url","https://www.bilibili.com/video/av22261317");
            //blob:https://www.bilibili.com/b519c93f-396a-4883-a2b8-08adfb4e82bc
            PublicInfo.listItemCation.add(map);
        }*/

        getMessageInfo(contengPlate,startIndex,numberShow,true);

        /*for (int i=0;i<10;i++){
            HashMap<String,Object> map=new HashMap<>();
            map.put("nickName","小故事，大情调");
            map.put("contentInfo","生活在中国，每天都有一个有趣的故事。");
            map.put("tag","2");
            map.put("time",(i+1)+"分钟前");
            map.put("discussInfo","Frank:这个视频说的很有趣！\nSwollen：这个一定点赞！\nJerry：没看懂什么意思啊？？？");
            map.put("url","http://www.365yg.com/a6397953445903876353/#mid=50218837103");
            PublicInfo.listItemRecommand.add(map);
        }


        for (int i=0;i<10;i++){
            HashMap<String,Object> map=new HashMap<>();
            map.put("nickName","周边新鲜事");
            map.put("contentInfo","每一个地方，都有属于他的一个故事。");
            map.put("tag","3");
            map.put("time",(i+1)+"分钟前");
            map.put("discussInfo","Frank:这个视频说的很有趣！\nSwollen：这个一定点赞！\nJerry：没看懂什么意思啊？？？");
            map.put("url","https://www.bilibili.com/video/av23667293");
            PublicInfo.listItemNear.add(map);
        }

        for (int i=0;i<10;i++){
            HashMap<String,Object> map=new HashMap<>();
            map.put("nickName","必修课堂");
            map.put("contentInfo","每一个举动，都会创作一个故事。");
            map.put("tag","4");
            map.put("time",(i+1)+"分钟前");
            map.put("discussInfo","Frank:这个课堂说的很有趣！\nSwollen：这个一定点赞！\nJerry：这个课堂讲的是什么内容？");
            map.put("url","https://www.bilibili.com/video/av23667293");
            PublicInfo.listItemClass.add(map);
        }*/

        /*myAdapter.listItem= PublicInfo.listItemCation;
        myAdapter.notifyDataSetChanged();*/


        lv_info.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE://停止滑动
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL://正在滑动
                        break;
                    case SCROLL_STATE_FLING://滑动ListView离开后，由于惯性继续滑动

                        break;
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //用于底部加载更多数据的判断逻辑,在这个地方调用自己的方法请求网络数据，一次性请求10条或者15条等
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0&&totalItemCount>totalItemFlag) {
                    totalItemFlag=totalItemCount;
                    if (requestFlag){
                        requestFlag=false;
                        getMessageInfo(contengPlate,startIndex,numberShow,false);
                    }

                }

                //判断ListView的滑动方向
                if (firstVisibleItemTag == firstVisibleItem) {
                    Log.e("滑动分页：", "未发生滑动");
                } else if (firstVisibleItemTag > firstVisibleItem) {
                    Log.e("滑动分页：", "发生下滑");
                } else {
                    Log.e("滑动分页：", "发生上滑");
                }
                firstVisibleItemTag = firstVisibleItem;


            }
        });




    }



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
                    Glide.with(getContext()).load(listItem.get(position).get("url").toString()).into(holder.imageView);
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
                  //  holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(maxWidth,180));
                    Glide.with(getContext()).load(listItem.get(position).get("url").toString()).into(holder.imageView);
                    L.g("imgurl======",listItem.get(position).get("url").toString());
                }else {
                    //holder.videoView.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
                   // holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
                    holder.videoView.setVisibility(View.INVISIBLE);
                    holder.imageView.setVisibility(View.INVISIBLE);
                }



            }catch (Exception e){
                e.printStackTrace();
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText (getContext(),"正在加载！", Toast.LENGTH_LONG ).show ();
                    Intent intent=new Intent(getActivity(), ApplicationLoad.class);
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

            /*holder.videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText (getContext(),"正在加载！", Toast.LENGTH_LONG ).show ();
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

                    Toast.makeText (getContext(),"正在加载！", Toast.LENGTH_LONG ).show ();
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
                    LinearLayout linearLayout=new LinearLayout(getContext());
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout distinct=new LinearLayout(getContext());
                    distinct.setBackgroundColor(Color.RED);
                   /* ViewGroup.LayoutParams layoutParams=distinct.getLayoutParams();
                    layoutParams.height=5;
                    distinct.setLayoutParams(layoutParams);*/

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 25, 5, 25);
                    TextView share=new TextView(getContext());
                    share.setText("分享");
                    share.setTextSize(18);
                    share.setLayoutParams(lp);
                    share.setGravity(Gravity.CENTER);
                    TextView report=new TextView(getContext());
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


                    new AlertDialog.Builder(getContext()).setView(linearLayout).show();


                }
            });

            holder.img_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.dianZan.getText().toString().contains(PersonInfo.userName)){
                        Toast.makeText(getContext(),"已经点过赞了！",Toast.LENGTH_SHORT).show();
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

                    LinearLayout linearLayout=new LinearLayout(getContext());
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    final EditText discuss=new EditText(getContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 25, 5, 5);
                    discuss.setHint("请输入您的评论内容……");
                    discuss.setLayoutParams(lp);
                    linearLayout.addView(discuss);

                    new AlertDialog.Builder(getContext()).setView(linearLayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                                Toast.makeText(getContext(),"没有输入评论内容",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).setNegativeButton("取消",null).show();
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



    private void getMessageInfo(final int plate, final int startIndexPos, final int numberShowSum, final boolean isNotSearch){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", plate);
                    paremetes.put("startIndex",startIndexPos);
                    paremetes.put("number",numberShowSum);
                    ApiService.GetString(getActivity(), "getContentInfoByPlate", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            try{
                                if (isNotSearch){
                                    myAdapter.listItem.clear();
                                }
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                if (!response.contains("没有数据")&&!response.contains("查询失败")){
                                    startIndex=startIndex+numberShow;
                                    requestFlag=true;
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
                    ApiService.GetString(getActivity(), "addThumbPeople", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                                if (response.contains("true")){
                                    new AlertDialog.Builder(getContext()).setMessage("点赞成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                    ApiService.GetString(getActivity(), "addDiscussInfo", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                           if (response.equals("true")){
                               new AlertDialog.Builder(getContext()).setMessage("评论成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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


    private void addCollectionInfo(final String collectionInfo){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", collectionInfo);
                    ApiService.GetString(getActivity(), "addCollectionInfo", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {

                                new AlertDialog.Builder(getContext()).setMessage(response).setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
        }
        dialog.dismiss();
        SharePhoto(share_img_path,getActivity());

    }



    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

}
