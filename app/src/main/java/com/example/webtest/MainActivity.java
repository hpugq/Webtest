package com.example.webtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String path = "https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF";
    private ImageView logo;
//    private Handler handler = new Handler() {             // 使用Handle解决
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            if (msg.what == 11){
//                Bitmap bitmap = (Bitmap) msg.obj;
//                logo.setImageBitmap(bitmap);
//            }
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.imageView);
        new Thread(){
            @Override
            public void run(){
                downpic(path);
            }
        }.start();
    }
    private void downpic(String strurl){
        try {
            URL url = new URL(strurl);
            HttpURLConnection cn = (HttpURLConnection) url.openConnection();
            if (cn.getResponseCode() == 200){
                InputStream inputStream = cn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(new Runnable() {      // 切换回主线程
                    @Override
                    public void run() {
                        logo.setImageBitmap(bitmap);
                    }
                });
//                Message msg = new Message();          // 使用Handle解决
//                msg.what = 11;
//                msg.obj = bitmap;
//                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}