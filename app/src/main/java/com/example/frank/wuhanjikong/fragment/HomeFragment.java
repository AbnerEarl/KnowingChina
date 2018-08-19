package com.example.frank.wuhanjikong.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.config.PublicInfo;
import com.example.frank.wuhanjikong.ui.home.ApplicationLoad;
import com.example.frank.wuhanjikong.ui.home.VideoActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeFragment extends Fragment {

    private MyAdapter myAdapter;
    private ListView lv_info;
    private TextView textViewCation,textViewRecommand,textViewNear,textViewClass;



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

        init(view);

        lv_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Toast.makeText (getContext(),"正在加载！", Toast.LENGTH_LONG ).show ();

                Intent intent=new Intent(getActivity(), ApplicationLoad.class);
                intent.putExtra("url",myAdapter.listItem.get(arg2).get("url").toString());
                startActivity(intent);


            }
        });

        textViewCation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // myAdapter.listItem.clear();
                myAdapter.listItem=PublicInfo.listItemCation;
                myAdapter.notifyDataSetChanged();
            }
        });

        textViewRecommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myAdapter.listItem.clear();
                myAdapter.listItem=PublicInfo.listItemRecommand;
                myAdapter.notifyDataSetChanged();
            }
        });

        textViewNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myAdapter.listItem.clear();
                myAdapter.listItem=PublicInfo.listItemNear;
                myAdapter.notifyDataSetChanged();
            }
        });

        textViewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myAdapter.listItem.clear();
                myAdapter.listItem=PublicInfo.listItemClass;
                myAdapter.notifyDataSetChanged();
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




        for (int i=0;i<10;i++){
            HashMap<String,Object> map=new HashMap<>();
            map.put("nickName","有意思的事");
            map.put("contentInfo","谁能看懂这个视频的内容？");
            map.put("tag","1");
            map.put("time",(i+1)+"分钟前");
            map.put("discussInfo","Frank:这个视频说的很有趣！\nSwollen：这个一定点赞！\nJerry：没看懂什么意思啊？？？");
            map.put("url","https://www.bilibili.com/video/av22261317");
            //blob:https://www.bilibili.com/b519c93f-396a-4883-a2b8-08adfb4e82bc
            PublicInfo.listItemCation.add(map);
        }


        for (int i=0;i<10;i++){
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
        }

        myAdapter.listItem= PublicInfo.listItemCation;
        myAdapter.notifyDataSetChanged();





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

                holder.publishTime = (TextView) convertView.findViewById(R.id.textView4);
                holder.nickName = (TextView) convertView.findViewById(R.id.textView5);
                holder.contentInfo = (TextView) convertView.findViewById(R.id.textView6);
                holder.imageView=(ImageView) convertView.findViewById(R.id.img_logo);


                convertView.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
            }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/

            try{
                holder.nickName.setText(listItem.get(position).get("nickName").toString());
                holder.publishTime.setText(listItem.get(position).get("time").toString());
                holder.contentInfo.setText(listItem.get(position).get("contentInfo").toString());
                holder.discussInfo.setText(listItem.get(position).get("discussInfo").toString());
                /*Bitmap bm = BitmapFactory.decodeFile(listItem.get(position).get("ItemImage").toString());
                holder.imageView.setImageBitmap(bm);*/
                if (listItem.get(position).get("tag").toString().equals("1")){
                    holder.imageView.setImageResource(R.drawable.videologo);
                }else if (listItem.get(position).get("tag").toString().equals("2")){
                    holder.imageView.setImageResource(R.drawable.vvlogo3);
                }else if (listItem.get(position).get("tag").toString().equals("3")){
                    holder.imageView.setImageResource(R.drawable.vvlogo2);
                }else if (listItem.get(position).get("tag").toString().equals("4")){
                    holder.imageView.setImageResource(R.drawable.vedio_class);
                }else {
                    holder.imageView.setImageResource(R.drawable.vediotem);
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), ApplicationLoad.class);
                    intent.putExtra("url",myAdapter.listItem.get(position).get("url").toString());
                    startActivity(intent);
                }
            });


            return convertView;
        }

    }

    /*存放控件*/
    public final class ViewHolder {
        public TextView publishTime,nickName,contentInfo,discussInfo;
        public ImageView imageView;


    }


}
