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

public class MainActivity extends AppCompatActivity {
    private final int PERMISSON_REQUEST_CODE = 100;
    private final int SET_IMAGE = 102;
    private final int SET_VIDEO = 101;
    private final int SET_ADAPTER = 103;
    static final String URL = Environment.getExternalStorageDirectory().getAbsolutePath() + "/seongmin/";

    static VideoView vv;
    ImageView imageView2;
    Bitmap bitmap;

    Handler ImageHandler = new Handler(Looper.getMainLooper());
    Handler videoHandler = new Handler(Looper.getMainLooper());
    ArrayList<ContentsVO> contentsVO = LoginActivity.contentsVO;

    ImageButton start;
    ImageButton stop;
    ImageButton list;

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

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimThread thread = new AnimThread();
                thread.start();
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
                ListView listview = (ListView) findViewById(R.id.listView);
                ListAdapter adapter = new ListAdapter(getApplicationContext(), contentsVO);

                listview.setAdapter(adapter);
            }
        });

    }

    public class AnimThread extends Thread {
        @Override
        public void run() {
            while (true) {
                for (int index = 0; index < contentsVO.size(); index++) {
                    File file = new File(URL + contentsVO.get(index).getFileName());
                    System.out.println(String.valueOf(file));
                    int finalIndex = index;
                    if (contentsVO.get(finalIndex).getFileType().equals("image")) {
                        ImageHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView2.setVisibility(View.VISIBLE);
                                vv.setVisibility(View.INVISIBLE);
                                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/seongmin/" + contentsVO.get(finalIndex).getFileName());
                                imageView2.setImageURI(uri);
                                bitmap = BitmapFactory.decodeFile(String.valueOf(uri));
                                imageView2.setImageBitmap(bitmap);
                            }
                        });
                        //이미지일 경우 실행시간에 따라 이미지 표출
                        try {
                            if (contentsVO.get(index).getFileType().equals("image")) {
                                for (int i = 0; i < contentsVO.get(index).getPlayTime(); i++)
                                    Thread.sleep(1000);
                            }
                        } catch (InterruptedException e) {
                            e.fillInStackTrace();
                        }
                    } else if (contentsVO.get(finalIndex).getFileType().equals("video")) {
                        videoHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView2.setVisibility(View.INVISIBLE);
                                vv.setVisibility(View.VISIBLE);
                                Log.d("현재 콘텐츠:", contentsVO.get(finalIndex).getFileName());
                                // VideoView : 동영상을 재생하는 뷰
                                VideoView vv = (VideoView) findViewById(R.id.videoView);

                                // MediaController : 특정 View 위에서 작동하는 미디어 컨트롤러 객체
                                MediaController mc = new MediaController(getApplicationContext());
                                vv.setMediaController(mc); // Video View 에 사용할 컨트롤러 지정

                                Uri uri = Uri.parse(URL + contentsVO.get(finalIndex).getFileName());
                                vv.setVideoURI(uri);
                                // VideoView 로 재생할 영상
                                vv.requestFocus(); // 포커스 얻어오기
                                vv.start(); // 동영상 재생

                                vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        Toast.makeText(getApplicationContext(), "동영상 재생이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                        vv.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        });
                    }
                    if (index == contentsVO.size()) {
                        index = 0;
                        continue;
                    }
                }
            }
        }
    }
}

