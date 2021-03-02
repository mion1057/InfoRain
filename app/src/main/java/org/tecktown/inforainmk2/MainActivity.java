package org.tecktown.inforainmk2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity {
    private final int PERMISSON_REQUEST_CODE = 100;
    private final int SET_IMAGE = 102;
    private final int SET_VIDEO = 101;
    static final String URL = Environment.getExternalStorageDirectory().getAbsolutePath() + "/seongmin/";
    //    private static final ArrayList<String> EXTENSION = new ArrayList<>(Arrays.asList(".jpg" , ".png", ".jpeg"));
//    private static final ArrayList<String> EXTENSION2 = new ArrayList<>(Arrays.asList(".mp4" , ".avi", ".wmv"));
    ImageView imageView2;
    VideoView videoView;

    Handler mainHandler = new Handler();
    ArrayList<ContentsVO> contentsVO = LoginActivity.contentsVO;

    ImageButton start;
    ImageButton stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //비디오, 이미지뷰 선언
        imageView2 = (ImageView) findViewById(R.id.Image);
        videoView = (VideoView) findViewById(R.id.videoView);

        //비디오 뷰를 커스텀 해주는 미디어 컨트롤러 객체 생성
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer player) {
                Toast.makeText(getApplicationContext(), "동영상이 준비되었습니다.\n'재생' 버튼을 누르세요.", Toast.LENGTH_LONG).show();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer player) {
                Toast.makeText(getApplicationContext(), "동영상 재생이 완료되었습니다.", Toast.LENGTH_LONG).show();
            }
        });


        imageView2.setVisibility(View.INVISIBLE);
        videoView.setVisibility(View.INVISIBLE);

        start = (ImageButton) findViewById(R.id.start);
        stop = (ImageButton) findViewById(R.id.stop);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimThread thread = new AnimThread();
                thread.start();
            }
        });
    }
    public class AnimThread extends Thread{
        @Override
        public void run(){

            int index = 0;
            while (true){
                File file = new File(URL + contentsVO.get(index).getFileName());
                System.out.println(String.valueOf(file));
                int finalIndex = index;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (contentsVO.get(finalIndex).getFileType().equals("image")){
                            imageView2.setVisibility(View.VISIBLE);
                            Uri uri = Uri.parse(URL + contentsVO.get(finalIndex).getFileName());
                            imageView2.setImageURI(uri);
                        }else if (contentsVO.get(finalIndex).getFileType().equals("video")){
                            videoView.setVisibility(View.VISIBLE);
                            Uri uri = Uri.parse(URL + contentsVO.get(finalIndex).getFileName());
                            videoView.setVideoURI(uri);
                            videoView.requestFocus();
                            videoView.start();

                        }
                    }
                });
                index++;
                try {
                    if(contentsVO.get(index).getFileType().equals("image")){
                        Thread.sleep(contentsVO.get(index).getPlayTime() * 1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

