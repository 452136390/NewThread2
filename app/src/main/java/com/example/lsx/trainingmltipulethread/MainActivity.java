package com.example.lsx.trainingmltipulethread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageview;
    private Button mLoadImageButton;
    private Button mToastButton;
    private ProgressBar mProgressBar;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case  0:
                mProgressBar.setVisibility(View.VISIBLE);
                break;
                case  1:
                mProgressBar.setProgress((int)msg.obj);
                break;
                case  2:
                mImageview.setImageBitmap((Bitmap) msg.obj);
                break;
                case  3:
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageview = (ImageView) findViewById(R.id.activity_main_image_view);
        mLoadImageButton = (Button) findViewById(R.id.activity_main_load_image_button);
        mToastButton = (Button) findViewById(R.id.activity_main_toast_button);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);


        mLoadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = 0;
                                mHandler.sendMessage(msg);
                                for(int i=1;i<11;i++) {
                                    sleep();
                                Message msg2 = new Message();
                                msg2.what = 1;
                                msg2.obj = i*10;
                                    mHandler.sendMessage(msg2);

                                }
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                                        R.drawable.ic_launcher);
                                Message msgBitMap = mHandler.obtainMessage();
                                msgBitMap.what = 2;
                                msgBitMap.obj = bitmap;
                                mHandler.sendMessage(msgBitMap);


                                Message msg3 = mHandler.obtainMessage();
                                msg3.what = 3;
                                mHandler.sendMessage(msg3);

                            }

                            private void sleep() {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();

            }
        });
        mToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(MainActivity.this, "xxxxxxxxxxxxxx", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
