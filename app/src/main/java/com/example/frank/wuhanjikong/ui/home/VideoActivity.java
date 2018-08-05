package com.example.frank.wuhanjikong.ui.home;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.frank.wuhanjikong.R;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;

    private String videoUrl2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView=(VideoView)this.findViewById(R.id.videoView2);

        //本地的视频  需要在手机SD卡根目录添加一个 fl1234.mp4 视频
       // String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+"/fl1234.mp4" ;

        videoUrl2=getIntent().getStringExtra("url");
        //网络视频
        // videoUrl2 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4" ;
        Uri uri = Uri.parse( videoUrl2 );

        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());

        //设置视频路径
        videoView.setVideoURI(uri);

        //开始播放视频
        videoView.start();


    }


    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( VideoActivity.this, "播放完结束了！", Toast.LENGTH_SHORT).show();
        }
    }


}
