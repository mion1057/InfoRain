package org.tecktown.inforainmk2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.tecktown.inforainmk2.Network.FTPclient;
import org.tecktown.inforainmk2.VO.ContentsVO;
import org.tecktown.inforainmk2.VO.values;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSON_REQUEST_CODE = 100;
    private final int SET_IMAGE = 101;
    private final int SET_VIDEO = 102;
    static final String URL = Environment.getExternalStorageDirectory().getAbsolutePath() + "/seongmin/";

    static VideoView vv;
    ImageView imageView2;
    Bitmap bitmap;

    ArrayList<ContentsVO> contentsVO = LoginActivity.contentsVO;

    ImageButton start;
    ImageButton stop;
    ImageButton list;
    TextView fileinfo;

    static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //이미지뷰 선언
        imageView2 = (ImageView) findViewById(R.id.Image);
        vv = (VideoView) findViewById(R.id.videoView);
        start = (ImageButton) findViewById(R.id.start);
        stop = (ImageButton) findViewById(R.id.stop);
        list = (ImageButton) findViewById(R.id.list);
        fileinfo = (TextView) findViewById(R.id.fileinfo);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentsVO.get(index).getFileType().equals("image")) {
                    Log.d("현재 콘텐츠", contentsVO.get(index).getFileName());
                    Message msg = handler.obtainMessage(SET_IMAGE);
                    handler.sendMessage(msg);
                } else if (contentsVO.get(index).getFileType().equals("video")) {
                    Message msg1 = handler.obtainMessage(SET_VIDEO);
                    handler.sendMessage(msg1);
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d("들어오는 이벤트", contentsVO.get(index).getFileType());
            switch (msg.what) {
                case SET_IMAGE:
                    vv.setVisibility(View.GONE);
                    imageView2.setVisibility(View.VISIBLE);
                    Log.d("현재 이미지 콘텐츠", contentsVO.get(index).getFileName());
                    Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/seongmin/" + contentsVO.get(index).getFileName());
                    imageView2.setImageURI(uri);
                    bitmap = BitmapFactory.decodeFile(String.valueOf(uri));
                    imageView2.setImageBitmap(bitmap);
                    fileinfo.setText(contentsVO.get(index).getFileName());
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            index++;
                            if(index == contentsVO.size()){
                                index = 0;
                            }
                            if(contentsVO.get(index).getFileType().equals("image")){
                                Message msg2 = handler.obtainMessage(SET_IMAGE);
                                handler.sendMessage(msg2);
                            }else if(contentsVO.get(index).getFileType().equals("video")){
                                Message msg3 = handler.obtainMessage(SET_VIDEO);
                                handler.sendMessage(msg3);
                            }
                            timer.cancel();
                        }
                    };
                    timer.schedule(task, contentsVO.get(index).getPlayTime()*1000,contentsVO.get(index).getPlayTime()*1000);
                    break;
                case SET_VIDEO:
                    imageView2.setVisibility(View.GONE);
                    vv.setVisibility(View.VISIBLE);
                    Log.d("현재 콘텐츠 미디어:", contentsVO.get(index).getFileName());
                    // VideoView : 동영상을 재생하는 뷰
                    VideoView vv = (VideoView) findViewById(R.id.videoView);
                    fileinfo.setText(contentsVO.get(index).getFileName());
                    // MediaController : 특정 View 위에서 작동하는 미디어 컨트롤러 객체
                    MediaController mc = new MediaController(MainActivity.this);
                    vv.setMediaController(mc); // Video View 에 사용할 컨트롤러 지정

                    uri = Uri.parse(URL + contentsVO.get(index).getFileName());
                    System.out.println(uri);
                    vv.setVideoURI(uri);
                    // VideoView 로 재생할 영상
                    vv.requestFocus(); // 포커스 얻어오기
                    vv.start(); // 동영상 재생

                    vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            //mp.stop();
                            //mp.release();
                            Log.d("영상", "완료");
                            index++;
                            if(index == contentsVO.size()){
                                index = 0;
                            }
                            if(contentsVO.get(index).getFileType().equals("image")){
                                Message msg4 = handler.obtainMessage(SET_IMAGE);
                                handler.sendMessage(msg4);
                            }else if(contentsVO.get(index).getFileType().equals("video")){
                                Message msg5 = handler.obtainMessage(SET_VIDEO);
                                handler.sendMessage(msg5);
                            }
                        }
                    });
                    break;
            }
        }
    };
}