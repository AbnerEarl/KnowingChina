package com.example.frank.wuhanjikong.ui.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.entity.ContentInfo;
import com.example.frank.wuhanjikong.entity.HskContent;
import com.example.frank.wuhanjikong.fragment.HomeFragment;
import com.example.frank.wuhanjikong.service.ApiService;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationLoad extends AppCompatActivity {

    public WebView myWebView;
    private ProgressDialog dialog=null;
    private String urrl;

    private ImageButton back;
    private TextView title;
    private String titleInfo,contentInfo,contentId;
    private TextView textViewTitle,textViewContent;
    private Button hsk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_load);

        Intent intent=getIntent();
        urrl= intent.getStringExtra("url");
        titleInfo=intent.getStringExtra("title");
        contentInfo=intent.getStringExtra("content");
        contentId=intent.getStringExtra("contentId");



        textViewTitle=(TextView)findViewById(R.id.textView25);
        textViewContent=(TextView)findViewById(R.id.textView23);
        hsk=(Button)this.findViewById(R.id.button28);
        back=(ImageButton)this.findViewById(R.id.titleback);
        title=(TextView)this.findViewById(R.id.titleplain);
        //标题栏设置
        title.setText("Knowing China");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textViewTitle.setText(titleInfo);
        textViewContent.setText(contentInfo);


        String str = urrl;//http://www.google.cn/maps/@37.263579,74.9775024,4z?hl=en
        Context context=ApplicationLoad.this;

        hsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout=new LinearLayout(ApplicationLoad.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout distinct=new LinearLayout(ApplicationLoad.this);
                distinct.setBackgroundColor(Color.RED);
                   /* ViewGroup.LayoutParams layoutParams=distinct.getLayoutParams();
                    layoutParams.height=5;
                    distinct.setLayoutParams(layoutParams);*/
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 8, 5, 8);

                TextView hsk1=new TextView(ApplicationLoad.this);
                hsk1.setText("HSK1");
                hsk1.setTextSize(18);
                hsk1.setLayoutParams(lp);
                hsk1.setGravity(Gravity.CENTER);

                TextView hsk2=new TextView(ApplicationLoad.this);
                hsk2.setTextSize(18);
                hsk2.setGravity(Gravity.CENTER);
                hsk2.setLayoutParams(lp);
                hsk2.setText("HSK2");

                TextView hsk3=new TextView(ApplicationLoad.this);
                hsk3.setTextSize(18);
                hsk3.setGravity(Gravity.CENTER);
                hsk3.setLayoutParams(lp);
                hsk3.setText("HSK3");

                TextView hsk4=new TextView(ApplicationLoad.this);
                hsk4.setTextSize(18);
                hsk4.setGravity(Gravity.CENTER);
                hsk4.setLayoutParams(lp);
                hsk4.setText("HSK4");

                TextView hsk5=new TextView(ApplicationLoad.this);
                hsk5.setTextSize(18);
                hsk5.setGravity(Gravity.CENTER);
                hsk5.setLayoutParams(lp);
                hsk5.setText("HSK5");

                TextView hsk6=new TextView(ApplicationLoad.this);
                hsk6.setTextSize(18);
                hsk6.setGravity(Gravity.CENTER);
                hsk6.setLayoutParams(lp);
                hsk6.setText("HSK6");

                TextView hsk7=new TextView(ApplicationLoad.this);
                hsk7.setTextSize(18);
                hsk7.setGravity(Gravity.CENTER);
                hsk7.setLayoutParams(lp);
                hsk7.setText("HSK7");

                TextView hsk8=new TextView(ApplicationLoad.this);
                hsk8.setTextSize(18);
                hsk8.setGravity(Gravity.CENTER);
                hsk8.setLayoutParams(lp);
                hsk8.setText("HSK8");

                linearLayout.addView(hsk1);
                //linearLayout.addView(distinct);
                linearLayout.addView(hsk2);
                linearLayout.addView(hsk3);
                linearLayout.addView(hsk4);
                linearLayout.addView(hsk5);
                linearLayout.addView(hsk6);
                linearLayout.addView(hsk7);
                linearLayout.addView(hsk8);

                final AlertDialog alertDialog=new AlertDialog.Builder(ApplicationLoad.this).create();

                hsk1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,String> map=new HashMap<>();
                        map.put("contentId",contentId);
                        map.put("hsk","HSK1");
                        getContentInfoByHsk(JSON.toJSONString(map));
                        alertDialog.dismiss();
                    }
                });

                hsk2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String,String> map=new HashMap<>();
                        map.put("contentId",contentId);
                        map.put("hsk","HSK2");
                        getContentInfoByHsk(JSON.toJSONString(map));
                        alertDialog.dismiss();
                    }
                });

                hsk3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String,String> map=new HashMap<>();
                        map.put("contentId",contentId);
                        map.put("hsk","HSK3");
                        getContentInfoByHsk(JSON.toJSONString(map));
                        alertDialog.dismiss();
                    }
                });

                hsk4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String,String> map=new HashMap<>();
                        map.put("contentId",contentId);
                        map.put("hsk","HSK4");
                        getContentInfoByHsk(JSON.toJSONString(map));
                        alertDialog.dismiss();
                    }
                });

                hsk5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String,String> map=new HashMap<>();
                        map.put("contentId",contentId);
                        map.put("hsk","HSK5");
                        getContentInfoByHsk(JSON.toJSONString(map));
                        alertDialog.dismiss();
                    }
                });

                hsk6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String,String> map=new HashMap<>();
                        map.put("contentId",contentId);
                        map.put("hsk","HSK6");
                        getContentInfoByHsk(JSON.toJSONString(map));
                        alertDialog.dismiss();
                    }
                });

                hsk7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String,String> map=new HashMap<>();
                        map.put("contentId",contentId);
                        map.put("hsk","HSK7");
                        getContentInfoByHsk(JSON.toJSONString(map));
                        alertDialog.dismiss();
                    }
                });

                hsk8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String,String> map=new HashMap<>();
                        map.put("contentId",contentId);
                        map.put("hsk","HSK8");
                        getContentInfoByHsk(JSON.toJSONString(map));
                        alertDialog.dismiss();
                    }
                });

               alertDialog.setView(linearLayout);
              // alertDialog.setTitle("请选择HSK等级：");
               alertDialog.show();


            }
        });


        myWebView = (WebView) findViewById(R.id.webview1);

        // myWebView.setWebViewClient(new WebViewClient());

        //同步cookie
        //new HttpCookie(mHandler,str).start();
        syncCookie(context,str);
        //WebSettings webSettings = myWebView.getSettings();
        // 设置可以访问文件  
        myWebView.getSettings().setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript  
        myWebView.getSettings().setJavaScriptEnabled(true);
        //myWebView.getSettings().setUserAgentString(MainActivity.getUserAgent());
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setDatabaseEnabled(true);
        // webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放  
        //  webSettings.setLoadWithOverviewMode(true);// 充满全屏幕  
        //  webSettings.setBuiltInZoomControls(false);
        //  webSettings.setJavaScriptEnabled(true);
        //  webSettings.setAppCacheEnabled(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //设置允许访问文件数据  
        myWebView.getSettings().setAllowFileAccess(true);
        // 设置缓存模式  
        // webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // 开启 DOM storage API 功能  
        //webSettings.setDomStorageEnabled(true);
        myWebView.setHorizontalScrollBarEnabled(false);// 水平不显示滚动条  
        myWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);// 禁止即在网页顶出现一个空白，又自动回去。  
        myWebView.setWebChromeClient(new webChromClient());
        myWebView.setWebViewClient(new webClient());//防止外部浏览器


        // dialog = ProgressDialog.show(ApplicationLoad.this, null, "正在加载中，请稍后···");
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings settings = myWebView.getSettings();
        // 开启javascript设置
        settings.setJavaScriptEnabled(true);
        // 设置可以使用localStorage
        settings.setDomStorageEnabled(true);
        // 应用可以有数据库
        settings.setDatabaseEnabled(true);
        String dbPath =this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(dbPath);
        // 应用可以有缓存
        settings.setAppCacheEnabled(true);
        String appCaceDir =this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCaceDir);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        myWebView.loadUrl(str);


        //监听下载
        myWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                // System.out.println(url: + url);
                if (url.endsWith(".zip")){
                    // 如果传进来url包含.zip文件，那么就开启下载线程
                    // System.out.println(download start...);
                    new DownloadThread(url).start();
                }
            }
        });
        myWebView.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if(event.getAction()== KeyEvent.ACTION_DOWN){
                    if(keyCode== KeyEvent.KEYCODE_BACK&&myWebView.canGoBack()){
                        myWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });





    }




    private class webChromClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress){
            // TODO Auto-generated method stub  
            super.onProgressChanged(view, newProgress);
        }


        @Override
        public void getVisitedHistory(ValueCallback<String[]> callback){
            super.getVisitedHistory(callback);
            //Log.i(TAG,"getVisitedHistory");
        }

        @Override
        public void onCloseWindow(WebView window){
            super.onCloseWindow(window);
            // Log.i(TAG,"onCloseWindow");
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg){
            // Log.i(TAG, "onCreateWindow");
            return super.onCreateWindow(view,isDialog,isUserGesture,resultMsg);
        }

        @Override
        @Deprecated
        public void onExceededDatabaseQuota(String url,
                                            String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota,
                                            WebStorage.QuotaUpdater quotaUpdater){
            super.onExceededDatabaseQuota(url,databaseIdentifier,quota, estimatedDatabaseSize,totalQuota,quotaUpdater);
            //Log.i(TAG,"onExceededDatabaseQuota");
        }
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon){
            super.onReceivedIcon(view,icon);
            //Log.i(TAG,"gonReceivedIcon");
        }

        @Override
        public void onReceivedTitle(WebView view, String title){
            super.onReceivedTitle(view,title);
            //Log.i(TAG,"onReceivedTitle");
        }

        @Override
        public void onRequestFocus(WebView view){
            super.onRequestFocus(view);
            // Log.i(TAG,"onRequestFocus");
        }



        public void openFileChooser(ValueCallback<Uri> uploadMsg){
            ValueCallback<Uri> mUploadMessag;
            mUploadMessag = uploadMsg;
            Intent i=new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            ApplicationLoad.this.startActivityForResult(
                    Intent.createChooser(i,"File Chooser"),ApplicationLoad.RESULT_FIRST_USER);
        }
        public void openFileChooser(ValueCallback uploadMsg, String acceptType){

            Intent i=new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            ApplicationLoad.this.startActivityForResult(
                    Intent.createChooser(i,"File Browser"),ApplicationLoad.RESULT_FIRST_USER);
        }
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
            ValueCallback<Uri> mUploadMessage;
            mUploadMessage= uploadMsg;
            Intent i=new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            ApplicationLoad.this.startActivityForResult(
                    Intent.createChooser(i,"File Chooser"),ApplicationLoad.RESULT_FIRST_USER);
        }


    }



    private void getContentInfoByHsk(final String hsk){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, Object> paremetes = new HashMap<>();
                    paremetes.put("data", hsk);
                    ApiService.GetString(ApplicationLoad.this, "getContentInfoByHsk", paremetes, new RxStringCallback() {
                        @Override
                        public void onNext(Object tag, String response) {
                            try{
                                if (!response.contains("没有数据")&&!response.contains("查询失败")){

                                    HskContent hskContent = JSON.parseObject(response,HskContent.class);
                                    textViewTitle.setText(hskContent.getContentTitle());
                                    textViewContent.setText(hskContent.getContentText());
                                    Toast.makeText(ApplicationLoad.this,"当前HSK等级为："+hskContent.getHskLevel(),Toast.LENGTH_SHORT).show();
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





    private class webClient extends WebViewClient {

        @Override
        public void onPageStarted(final WebView view, String url, Bitmap favicon){
            // TODO Auto-generated method stub  
            super.onPageStarted(view, url,favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url){
            // TODO Auto-generated method stub  
            // super.onPageFinished(view, url);  
            dialog.dismiss();

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            // TODO Auto-generated method stub  
            super.onReceivedError(view,errorCode,description,failingUrl);

            // guo add  
            //  Toast.makeText(ApplicationLoad.this,"网页加载出错！", Toast.LENGTH_LONG).show();

            //view.loadUrl("file:///android_asset/defaultpage/index1.html");// 加载一个默认的本地网页  
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            // TODO Auto-generated method stub  
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onLoadResource(WebView view, String url){
            super.onLoadResource(view,url);
            // Log.i(TAG,"onLoadResource: ");
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view,
                                              HttpAuthHandler handler, String host, String realm){
            super.onReceivedHttpAuthRequest(view,handler,host,realm);
            // Log.i(TAG,"onReceivedHttpAuthRequest: ");
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
            super.onReceivedSslError(view,handler,error);
            //Log.i(TAG,"onReceivedSslError: ");
        }

        @Override
        @Deprecated
        public void onTooManyRedirects(WebView view, Message cancelMsg,
                                       Message continueMsg){
            super.onTooManyRedirects(view,cancelMsg,continueMsg);
            // Log.i(TAG,"onTooManyRedirects");
        }




    }

    @Override
    protected void onResume(){
// TODO Auto-generated method stub  
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        // TODO Auto-generated method stub  
        super.onDestroy();
    }

    // 网络状态判断  
    public boolean isNetworkConnected(Context context){
        if(context!=null){
            ConnectivityManager mConnectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo=mConnectivityManager.getActiveNetworkInfo();
            if(mNetworkInfo!=null){
                return mNetworkInfo.isAvailable();
            }
        }
        return false;

    }



    /**
          * 执行下载的线程
          */
    class DownloadThread extends Thread {
        private String mUrl;

        public DownloadThread(String url) {
            this.mUrl = url;
        }

        @Override
        public void run() {
            try {
                URL httpUrl = new URL(mUrl);
                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                InputStream in = conn.getInputStream();
                FileOutputStream out = null;
                // 获取下载路径
                File downloadFile;
                File sdFile;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    downloadFile = Environment.getExternalStorageDirectory();
                    sdFile = new File(downloadFile,".zip");
                    out = new FileOutputStream(sdFile);
                }
                byte[] b = new byte[8 * 1024];
                int len;
                while ((len = in.read(b)) != -1) {
                    if (out != null) {
                        out.write(b, 0, len);
                    }
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                // System.out.println(download success...);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



//cook同步
    /*public static void synCookies(Context context,String url){
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager=CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除  
        cookieManager.setCookie(url,cookies);//cookies是在HttpClient中获得的cookie  
        CookieSyncManager.getInstance().sync();
    }*/



    /**
     * Sync Cookie
     */
    private void syncCookie(Context context, String url){
        try{
            // Log.d("Nat: webView.syncCookie.url", url);
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            String oldCookie = cookieManager.getCookie(url);
            if(oldCookie != null){
                // Log.d("Nat: webView.syncCookieOutter.oldCookie", oldCookie);
            }
            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format("JSESSIONID=%s","INPUT YOUR JSESSIONID STRING"));
            sbCookie.append(String.format(";domain=%s", "INPUT YOUR DOMAIN STRING"));
            sbCookie.append(String.format(";path=%s","INPUT YOUR PATH STRING"));
            String cookieValue = sbCookie.toString();
            cookieManager.setCookie(url, cookieValue);
            CookieSyncManager.getInstance().sync();
            String newCookie = cookieManager.getCookie(url);
            if(newCookie != null){
                // Log.d("Nat: webView.syncCookie.newCookie", newCookie);
            }
        }catch(Exception e){
            //Log.e("Nat: webView.syncCookie failed", e.toString());
        }
    }






}
