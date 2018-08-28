package com.example.frank.wuhanjikong.ui.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import com.amap.api.services.core.ServiceSettings;
import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.config.PersonInfo;
import com.example.frank.wuhanjikong.entity.ContentInfo;
import com.example.frank.wuhanjikong.entity.HskContent;
import com.example.frank.wuhanjikong.entity.MapShop;
import com.example.frank.wuhanjikong.log.L;
import com.example.frank.wuhanjikong.service.ApiService;
import com.example.frank.wuhanjikong.ui.home.ApplicationLoad;
import com.example.frank.wuhanjikong.ui.home.PublishContentActivity;
import com.example.frank.wuhanjikong.ui.map.lib.LocationTask;
import com.example.frank.wuhanjikong.ui.map.lib.OnLocationGetListener;
import com.example.frank.wuhanjikong.ui.map.lib.PositionEntity;
import com.example.frank.wuhanjikong.ui.map.lib.RegeocodeTask;
import com.example.frank.wuhanjikong.ui.map.lib.RouteTask;
import com.example.frank.wuhanjikong.ui.map.lib.Utils;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapMainActivity extends Activity  implements AMap.OnCameraChangeListener,
        AMap.OnMapLoadedListener, OnLocationGetListener, View.OnClickListener,
        RouteTask.OnRouteCalculateListener {

    private MapView mMapView;

    private AMap mAmap;

    private TextView mAddressTextView;

    private Button mDestinationButton;

    private Marker mPositionMark;

    private LatLng mStartPosition;

    private RegeocodeTask mRegeocodeTask;

    private LinearLayout mDestinationContainer;

    private TextView mRouteCostText;

    private TextView mDesitinationText;

    private LocationTask mLocationTask;

    private ImageView mLocationImage;

    private LinearLayout mFromToContainer;

    private Button mCancelButton;

    private boolean mIsFirst = true;

    private ImageView moreDetail,SortStyle;

    private PositionEntity positionEntitySelected=null;

    private int selectedStyle=0;
    private String selectedName="";

    private boolean mIsRouteSuccess = false;

    private ProgressDialog dialogLoading;

    public interface OnGetLocationListener {
        public void getLocation(String locationAddress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        ServiceSettings.getInstance().setLanguage(ServiceSettings.ENGLISH);//默认是中文ServiceSettings.CHINESE



        // 定义北京市经纬度坐标（此处以北京坐标为例）
        //LatLng centerBJPoint= new LatLng(30.438507859251835,114.26656527542318);
     //   LatLng centerBJPoint= new LatLng(114.26656527542318,30.438507859251835);
        // 定义了一个配置 AMap 对象的参数类
        AMapOptions mapOptions = new AMapOptions();
// 设置了一个可视范围的初始化位置
// CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
// CameraPosition 第二个参数： 目标可视区域的缩放级别
// CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
// CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
     //   mapOptions.camera(new CameraPosition(centerBJPoint, 10f, 0, 0));
// 定义一个 MapView 对象，构造方法中传入 mapOptions 参数类
     //   MapView mapView = new MapView(this, mapOptions);
// 调用 onCreate方法 对 MapView LayoutParams 设置
     //   mapView.onCreate(savedInstanceState);


        init(savedInstanceState);
        mLocationTask = LocationTask.getInstance(getApplicationContext());


        mLocationTask.setOnLocationGetListener(this);
        mRegeocodeTask = new RegeocodeTask(getApplicationContext());
        RouteTask.getInstance(getApplicationContext())
                .addRouteCalculateListener(this);
    }

    private void init(Bundle savedInstanceState) {

        moreDetail=(ImageView)this.findViewById(R.id.imageView13);
        mAddressTextView = (TextView) findViewById(R.id.address_text);
        mDestinationButton = (Button) findViewById(R.id.destination_button);
        mDestinationButton.setOnClickListener(this);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mAmap = mMapView.getMap();
        mAmap.getUiSettings().setZoomControlsEnabled(false);
        mAmap.setOnMapLoadedListener(this);
        mAmap.setOnCameraChangeListener(this);

        SortStyle=(ImageView)this.findViewById(R.id.style_sort) ;
        dialogLoading = new ProgressDialog(MapMainActivity.this);
        dialogLoading.setTitle("hint");
        dialogLoading.setMessage("loading...");


       /* moreDetail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (positionEntitySelected!=null){

                    Map<String,Object> map=new HashMap<>();
                    map.put("latitu",positionEntitySelected.latitue);
                    map.put("longtitu",positionEntitySelected.longitude);
                    getMapShopByData(JSON.toJSONString(map));
                }else {
                    Toast.makeText(MapMainActivity.this,"请先选中一个地点",Toast.LENGTH_SHORT).show();
                }


                return true;
            }
        });*/

       SortStyle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               LinearLayout linearLayout=new LinearLayout(MapMainActivity.this);
               linearLayout.setOrientation(LinearLayout.VERTICAL);

               LinearLayout distinct=new LinearLayout(MapMainActivity.this);
               distinct.setBackgroundColor(Color.RED);
                   /* ViewGroup.LayoutParams layoutParams=distinct.getLayoutParams();
                    layoutParams.height=5;
                    distinct.setLayoutParams(layoutParams);*/
               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               lp.setMargins(5, 8, 5, 8);

               TextView hsk1=new TextView(MapMainActivity.this);
               hsk1.setText("food");
               hsk1.setTextSize(18);
               hsk1.setLayoutParams(lp);
               hsk1.setGravity(Gravity.CENTER);

               TextView hsk2=new TextView(MapMainActivity.this);
               hsk2.setTextSize(18);
               hsk2.setGravity(Gravity.CENTER);
               hsk2.setLayoutParams(lp);
               hsk2.setText("store");

               TextView hsk3=new TextView(MapMainActivity.this);
               hsk3.setTextSize(18);
               hsk3.setGravity(Gravity.CENTER);
               hsk3.setLayoutParams(lp);
               hsk3.setText("bar");

               TextView hsk4=new TextView(MapMainActivity.this);
               hsk4.setTextSize(18);
               hsk4.setGravity(Gravity.CENTER);
               hsk4.setLayoutParams(lp);
               hsk4.setText("camera");

               TextView hsk5=new TextView(MapMainActivity.this);
               hsk5.setTextSize(18);
               hsk5.setGravity(Gravity.CENTER);
               hsk5.setLayoutParams(lp);
               hsk5.setText("beaty");

               TextView hsk6=new TextView(MapMainActivity.this);
               hsk6.setTextSize(18);
               hsk6.setGravity(Gravity.CENTER);
               hsk6.setLayoutParams(lp);
               hsk6.setText("more");


               linearLayout.addView(hsk1);
               //linearLayout.addView(distinct);
               linearLayout.addView(hsk2);
               linearLayout.addView(hsk3);
              // linearLayout.addView(hsk4);
              // linearLayout.addView(hsk5);
               linearLayout.addView(hsk6);


               final AlertDialog alertDialog=new AlertDialog.Builder(MapMainActivity.this).create();

               hsk1.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       getMarkInMapBySort(1);
                       alertDialog.dismiss();
                   }
               });

               hsk2.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       getMarkInMapBySort(2);
                       alertDialog.dismiss();
                   }
               });

               hsk3.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       getMarkInMapBySort(3);
                       alertDialog.dismiss();
                   }
               });

               hsk4.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       getMarkInMapBySort(4);
                       alertDialog.dismiss();
                   }
               });

               hsk5.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       getMarkInMapBySort(5);
                       alertDialog.dismiss();
                   }
               });

               hsk6.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       getMarkInMapBySort(6);
                       alertDialog.dismiss();
                   }
               });



               alertDialog.setView(linearLayout);
               // alertDialog.setTitle("请选择HSK等级：");
               alertDialog.show();
           }
       });

       moreDetail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (positionEntitySelected!=null){

                   Map<String,Object> map=new HashMap<>();
                   map.put("latitu",positionEntitySelected.latitue);
                   map.put("longtitu",positionEntitySelected.longitude);
                   dialogLoading.show();
                   getMapShopByData(JSON.toJSONString(map));
               }else {
                   Toast.makeText(MapMainActivity.this,"please choose a place first",Toast.LENGTH_SHORT).show();
               }
           }
       });


        mDestinationContainer = (LinearLayout) findViewById(R.id.destination_container);
        mRouteCostText = (TextView) findViewById(R.id.routecost_text);
        mDesitinationText = (TextView) findViewById(R.id.destination_text);
        mDesitinationText.setOnClickListener(this);
        mLocationImage = (ImageView) findViewById(R.id.location_image);
        mLocationImage.setOnClickListener(this);
        mFromToContainer = (LinearLayout) findViewById(R.id.fromto_container);
        mCancelButton = (Button) findViewById(R.id.cancel_button);

    }

    private void hideView() {

        mFromToContainer.setVisibility(View.GONE);
        mDestinationButton.setVisibility(View.GONE);
        mCancelButton.setVisibility(View.GONE);
    }

    private void showView() {
        mFromToContainer.setVisibility(View.VISIBLE);
        mDestinationButton.setVisibility(View.VISIBLE);
        if (mIsRouteSuccess) {
            mCancelButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCameraChange(CameraPosition arg0) {
        hideView();
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        showView();
        mStartPosition = cameraPosition.target;
        mRegeocodeTask.setOnLocationGetListener(this);
        mRegeocodeTask.search(mStartPosition.latitude, mStartPosition.longitude);
        /*if (mIsFirst) {
            Utils.addEmulateData(mAmap, mStartPosition);
            if (mPositionMark != null) {
                mPositionMark.setToTop();
            }
            mIsFirst = false;
        }*/

        if (mIsFirst) {

            getAllMarkInMap(1);
            if (mPositionMark != null) {
                mPositionMark.setToTop();
            }
            mIsFirst = false;
        }


    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.removeMarkers();
        mMapView.onDestroy();
        mLocationTask.onDestroy();
        RouteTask.getInstance(getApplicationContext()).removeRouteCalculateListener(this);

    }

    @Override
    public void onMapLoaded() {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(true);
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(30.445417, 14.274518));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.drawable.icon_loaction_start)));
        mPositionMark = mAmap.addMarker(markerOptions);
        mPositionMark.setPositionByPixels(mMapView.getWidth() / 2, mMapView.getHeight() / 2);
       // mLocationTask.startSingleLocate();

        LatLng marker1 = new LatLng(30.445417, 114.274518);
        //设置中心点和缩放比例
        mAmap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(14));
        mAmap.setMapLanguage("en");
        ServiceSettings.getInstance().setLanguage(ServiceSettings.ENGLISH);//默认是中文ServiceSettings.CHINESE

        //限制地图范围
        /*LatLng southwestLatLng = new LatLng(30.430317693318482, 114.26613209867469);
        LatLng northeastLatLng = new LatLng(30.44626608589725, 114.26700247548511);
        LatLngBounds latLngBounds = new LatLngBounds(southwestLatLng, northeastLatLng);
        mAmap.setMapStatusLimits(latLngBounds);*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.destination_button:
                Intent intent = new Intent(this, DestinationActivity.class);
                startActivity(intent);
                break;
            case R.id.location_image:
                mLocationTask.startSingleLocate();
                LatLng marker1 = new LatLng(30.445417, 14.274518);
                //设置中心点和缩放比例
                mAmap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
                mAmap.moveCamera(CameraUpdateFactory.zoomTo(200));
                break;
            case R.id.destination_text:
                Intent destinationIntent = new Intent(this, DestinationActivity.class);
                startActivity(destinationIntent);
                break;
        }
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        // todo 这里在网络定位时可以减少一个逆地理编码

        L.g("获得了城市1：", entity.address);
        L.g("获得了城市2：", entity.city);

        mAddressTextView.setText(entity.address);
        RouteTask.getInstance(getApplicationContext()).setStartPoint(entity);
        mStartPosition = new LatLng(entity.latitue, entity.longitude);
        CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
                mStartPosition, mAmap.getCameraPosition().zoom);
        mAmap.animateCamera(cameraUpate);

    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {
        mAddressTextView.setText(entity.address);
        L.g("选中了城市1：", entity.address);
        L.g("选中了城市2：", entity.city);

        entity.latitue = mStartPosition.latitude;
        entity.longitude = mStartPosition.longitude;
        L.g("选中了latitue：", entity.latitue+"");
        L.g("选中了longitude：", entity.longitude+"");
        /*HashMap<String,String> map=new HashMap<>();
        map.put("longitude",contentId);
        map.put("latitue","HSK2");
        getContentInfoByHsk(JSON.toJSONString(map));*/
        RouteTask.getInstance(getApplicationContext()).setStartPoint(entity);
        positionEntitySelected=entity;
        RouteTask.getInstance(getApplicationContext()).search();
    }

    @Override
    public void onRouteCalculate(float cost, float distance, int duration) {
        mDestinationContainer.setVisibility(View.VISIBLE);
        mIsRouteSuccess = true;
        mRouteCostText.setVisibility(View.VISIBLE);
        mDesitinationText.setText(RouteTask
                .getInstance(getApplicationContext()).getEndPoint().address);
        mRouteCostText.setText(String.format("预估费用%.2f元，距离%.1fkm,用时%d分", cost,
                distance, duration));
        mDestinationButton.setText("route");
        mCancelButton.setVisibility(View.VISIBLE);
       // mDestinationButton.setOnClickListener(null);
        mDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapMainActivity.this, SearchActivity.class);
                intent.putExtra("url","http://m.amap.com/navigation/index");
                startActivity(intent);
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RouteTask.getInstance(getApplicationContext()).setEndPoint(null);
                Intent intent=new Intent(MapMainActivity.this,MapMainActivity.class);
                onDestroy();
                startActivity(intent);
                finish();
                /*mIsRouteSuccess=false;
                hideView();*/
            }
        });
    }


    private void getMapShopByData(final String data){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", data);
                    ApiService.GetString(MapMainActivity.this, "getMapShopByData", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            dialogLoading.dismiss();
                            try{
                                if (!response.contains("no data")&&!response.contains("failed to search")){

                                    final MapShop mapShop = JSON.parseObject(response,MapShop.class);
                                    if (mapShop!=null){
                                        final AlertDialog alertDialog=new AlertDialog.Builder(MapMainActivity.this).create();
                                        View view= LayoutInflater.from(MapMainActivity.this).inflate(R.layout.shop_detail,null);
                                        TextView mapName=(TextView)view.findViewById(R.id.textView24);
                                        TextView shopName=(TextView)view.findViewById(R.id.textView26);
                                        TextView shopAddress=(TextView)view.findViewById(R.id.textView34);
                                        TextView shopStar=(TextView)view.findViewById(R.id.textView27);
                                        final TextView discussInfo=(TextView)view.findViewById(R.id.textView28);
                                        Button joinDiscuss=(Button)view.findViewById(R.id.button38);
                                        mapName.setText(mapShop.getMapName());
                                        shopName.setText("shop name："+mapShop.getShopName());
                                        if (mapShop.getShopAddress()!=null){
                                            shopAddress.setText("shop address："+mapShop.getShopAddress());
                                        }else {
                                            shopAddress.setText("shop address："+"no valid address");
                                        }

                                        shopStar.setText("remark："+mapShop.getShopStar());
                                        if (mapShop.getDiscussInfo()!=null){
                                            discussInfo.setText(mapShop.getDiscussInfo());
                                        }else {
                                            discussInfo.setText("no cotent");
                                        }
                                        final EditText editText=new EditText(MapMainActivity.this);
                                        editText.setHint("please enter your comment……");
                                        joinDiscuss.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                new AlertDialog.Builder(MapMainActivity.this).setView(editText).setTitle("comment it").setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if (editText.getText()!=null&&!editText.getText().toString().trim().equals("")) {
                                                            if (discussInfo.getText().toString().contains("no comment")) {
                                                                discussInfo.setText(PersonInfo.localSysUser.getNickName() + ":" + editText.getText());
                                                            } else {
                                                                String temDiscuss=discussInfo.getText().toString() + "\n";
                                                                discussInfo.setText(temDiscuss+ PersonInfo.localSysUser.getNickName() + ":" + editText.getText());
                                                            }
                                                            Map<String, Object> map = new HashMap<>();
                                                            map.put("recordId", mapShop.getRecordId());
                                                            map.put("discussInfo", discussInfo.getText().toString());
                                                            dialogLoading.show();
                                                            addDiscussShop(JSON.toJSONString(map));
                                                        }else {
                                                            Toast.makeText(MapMainActivity.this,"please enter your comment",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }).setNegativeButton("cancel",null).show();
                                            }
                                        });

                                        alertDialog.setView(view);
                                        alertDialog.setButton("cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                alertDialog.dismiss();
                                            }
                                        });

                                        alertDialog.setButton("confirm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                alertDialog.dismiss();
                                            }
                                        });
                                        alertDialog.show();

                                    }
                                }else {
                                    final AlertDialog alertDialog=new AlertDialog.Builder(MapMainActivity.this).create();
                                    View view= LayoutInflater.from(MapMainActivity.this).inflate(R.layout.recommend_shop,null);
                                    TextView city=(TextView)view.findViewById(R.id.textView30);
                                    TextView shopName=(TextView)view.findViewById(R.id.textView29);
                                    TextView latitu=(TextView)view.findViewById(R.id.textView31);
                                    TextView longtitu=(TextView)view.findViewById(R.id.textView32);
                                    Button recommend=(Button)view.findViewById(R.id.button39);
                                    city.setText("City："+positionEntitySelected.city);
                                    shopName.setText(""+positionEntitySelected.address);
                                    latitu.setText("latitude："+positionEntitySelected.latitue);
                                    longtitu.setText("longitude"+positionEntitySelected.longitude);

                                    Spinner spinner = (Spinner)view.findViewById(R.id.spinner);//获取spinner组件的id 用于以后对其操作
                                    final ArrayList<String> arrayList = new ArrayList<String>();//创建数组列表 用来存放以后要显示的内容
                                    arrayList.add("food");//添加要显示的内容
                                    arrayList.add("store");
                                    arrayList.add("bar");
                                    //arrayList.add("camera");
                                    //arrayList.add("beaty");
                                    arrayList.add("other");


                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MapMainActivity.this,android.R.layout.simple_spinner_item,arrayList);//创建适配器  this--上下文  android.R.layout.simple_spinner_item--显示的模板   arrayList--显示的内容
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉之后的布局的样式 这里采用的是系统的一个布局
                                    spinner.setAdapter(arrayAdapter);//将适配器给下拉框
                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//当改变下拉框的时候会触发
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//改变内容的时候
                                            //打印所选中的东西arrayList.get(position)--position--数组中第几个是选中的
                                            selectedStyle=position+1;
                                            selectedName=arrayList.get(position);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {//没有改变的时候


                                        }
                                    });

                                    final LinearLayout linearLayout_recommand=new LinearLayout(MapMainActivity.this);
                                    linearLayout_recommand.setOrientation(LinearLayout.VERTICAL);
                                     final EditText editText_reason=new EditText(MapMainActivity.this);
                                    editText_reason.setHint("reasons for recommendation……");
                                     final EditText editText_shop_name=new EditText(MapMainActivity.this);
                                    editText_shop_name.setHint("enter shop name……");
                                    linearLayout_recommand.addView(editText_shop_name);
                                    linearLayout_recommand.addView(editText_reason);
                                    recommend.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(MapMainActivity.this).setView(linearLayout_recommand).setTitle("recommend it").setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    if (editText_reason.getText()==null||editText_reason.getText().toString().trim().equals("")){
                                                        Toast.makeText(MapMainActivity.this,"reasons for recommendation",Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }else if (editText_shop_name.getText()==null||editText_shop_name.getText().toString().trim().equals("")){
                                                        Toast.makeText(MapMainActivity.this,"enter shop name",Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }else {
                                                        Map<String, Object> map = new HashMap<>();
                                                        map.put("mapName", selectedName);
                                                        map.put("mapStyle", selectedStyle);
                                                        map.put("longtitu", positionEntitySelected.longitude);
                                                        map.put("latitu", positionEntitySelected.latitue);
                                                        map.put("reason", editText_reason.getText().toString());
                                                        map.put("recommandId", PersonInfo.localSysUser.getUserId());
                                                        map.put("shopName", editText_shop_name.getText().toString());
                                                        map.put("shopAddress", positionEntitySelected.address);
                                                        dialogLoading.show();
                                                        addRecommandShop(JSON.toJSONString(map));
                                                    }

                                                }
                                            }).setNegativeButton("cancel",null).show();
                                        }
                                    });


                                    alertDialog.setView(view);
                                    alertDialog.setButton("cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                        }
                                    });

                                    alertDialog.setButton("confirm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                        }
                                    });
                                    alertDialog.show();
                                }


                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            dialogLoading.dismiss();
                        }

                        @Override
                        public void onError(Object tag, Throwable e) {
                            dialogLoading.dismiss();
                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {
                            dialogLoading.dismiss();
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }



    private void getAllMarkInMap(final int shopStatus){
        dialogLoading.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", shopStatus);
                    ApiService.GetString(MapMainActivity.this, "getAllMarkInMap", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            dialogLoading.dismiss();
                            try{
                                if (!response.contains("no data")&&!response.contains("failed to search")){

                                    List<Double> latituList=new ArrayList<>();
                                    List<Double> longtituList=new ArrayList<>();
                                    List<Integer> shopStyle=new ArrayList<>();
                                    List<MapShop> mapShopList = JSON.parseObject(response,new TypeReference< List<MapShop>>(){});
                                    for (MapShop mapShop:mapShopList){
                                        latituList.add(mapShop.getShopLatitude());
                                        longtituList.add(mapShop.getShopLongitude());
                                        shopStyle.add(mapShop.getMapStyle());
                                    }

                                    if (latituList.size()>0){
                                        Utils.addEmulateData(mAmap, mStartPosition,latituList,longtituList,shopStyle);
                                    }else {
                                        Toast.makeText(MapMainActivity.this,"no recommend place",Toast.LENGTH_SHORT).show();
                                    }
                                }


                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Object tag, Throwable e) {
                            dialogLoading.dismiss();
                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {
                            dialogLoading.dismiss();
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    private void getMarkInMapBySort(final int sortId){

        dialogLoading.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Utils.removeMarkers();
                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", sortId);
                    ApiService.GetString(MapMainActivity.this, "getMarkInMapBySort", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            dialogLoading.dismiss();
                            try{
                                if (!response.contains("no data")&&!response.contains("failed to search")){

                                    List<Double> latituList=new ArrayList<>();
                                    List<Double> longtituList=new ArrayList<>();
                                    List<Integer> shopStyle=new ArrayList<>();
                                    List<MapShop> mapShopList = JSON.parseObject(response,new TypeReference< List<MapShop>>(){});
                                    for (MapShop mapShop:mapShopList){
                                        latituList.add(mapShop.getShopLatitude());
                                        longtituList.add(mapShop.getShopLongitude());
                                        shopStyle.add(mapShop.getMapStyle());
                                    }

                                    if (latituList.size()>0){
                                        Utils.addEmulateData(mAmap, mStartPosition,latituList,longtituList,shopStyle);
                                    }else {
                                        Toast.makeText(MapMainActivity.this,"no recommend place",Toast.LENGTH_SHORT).show();
                                    }
                                }


                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Object tag, Throwable e) {
                            dialogLoading.dismiss();
                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {
                            dialogLoading.dismiss();
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }




    private void addRecommandShop(final String data){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", data);
                    ApiService.GetString(MapMainActivity.this, "addRecommandShop", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            dialogLoading.dismiss();
                            try{
                                if (response.contains("推荐成功")){

                                }
                                Toast.makeText(MapMainActivity.this,response,Toast.LENGTH_SHORT).show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Object tag, Throwable e) {
                            dialogLoading.dismiss();
                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {
                            dialogLoading.dismiss();
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    private void addDiscussShop(final String data){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", data);
                    ApiService.GetString(MapMainActivity.this, "addDiscussShop", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            dialogLoading.dismiss();
                            try{
                                if (response.contains("comment successful")){

                                }
                                Toast.makeText(MapMainActivity.this,response,Toast.LENGTH_SHORT).show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Object tag, Throwable e) {
                            dialogLoading.dismiss();
                        }

                        @Override
                        public void onCancel(Object tag, Throwable e) {
                            dialogLoading.dismiss();
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }



}
