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
    Bitmap bitmap;
    int index = 0;

    Handler mainHandler = new Handler();
    Handler videoHandler = new Handler();
    ArrayList<ContentsVO> contentsVO = LoginActivity.contentsVO;

    ImageButton start;
    ImageButton stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //이미지뷰 선언
        imageView2 = (ImageView) findViewById(R.id.Image);

        start = (ImageButton) findViewById(R.id.start);
        stop = (ImageButton) findViewById(R.id.stop);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimThread thread = new AnimThread();
                thread.start();
                if(contentsVO.get(index).getFileType().equals("image")){
                    videoView.setVisibility(View.INVISIBLE);
                }else{
                    videoView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public class AnimThread extends Thread {
        @Override
        public void run() {
            while (true){
                File file = new File(URL + contentsVO.get(index).getFileName());
                System.out.println(String.valueOf(file));
                System.out.println();
                if (contentsVO.get(index).getFileType().equals("image")){
                    controlHandler.sendEmptyMessage(SET_IMAGE);
                    for (int i = 0; i<contentsVO.get(index).getPlayTime();i++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(contentsVO.get(index).getFileType().equals("video")){
                    controlHandler.sendEmptyMessage(SET_VIDEO);
                }
                index++;

                if (index == contentsVO.size()){
                    index = 0;
                    continue;
                }
            }
        }
    }

    Handler controlHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SET_IMAGE:
                    Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/seongmin/" + contentsVO.get(index).getFileName());
                    imageView2.setImageURI(uri);
                    bitmap = BitmapFactory.decodeFile(String.valueOf(uri));
                    imageView2.setImageBitmap(bitmap);
                case SET_VIDEO:
                    // VideoView : 동영상을 재생하는 뷰
                    VideoView vv = (VideoView) findViewById(R.id.videoView);

                    // MediaController : 특정 View 위에서 작동하는 미디어 컨트롤러 객체
                    MediaController mc = new MediaController(getApplicationContext());
                    vv.setMediaController(mc); // Video View 에 사용할 컨트롤러 지정

                    String path = Environment.getExternalStorageDirectory()
                            .getAbsolutePath(); // 기본적인 절대경로 얻어오기

                    // 절대 경로 = SDCard 폴더 = "storage/emulated/0"
                    Log.d("test", "절대 경로 : " + path);

                    vv.setVideoPath(path + "/seongmin/" + contentsVO.get(index).getFileName());
                    // VideoView 로 재생할 영상
                    vv.requestFocus(); // 포커스 얻어오기
                    vv.start(); // 동영상 재생
            }
        }
    };
}

