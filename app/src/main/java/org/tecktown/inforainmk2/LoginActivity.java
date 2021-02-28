package org.tecktown.inforainmk2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.tecktown.inforainmk2.Network.FTPclient;
import org.tecktown.inforainmk2.Network.HttpRequest;
import org.tecktown.inforainmk2.VO.ContentsVO;
import org.tecktown.inforainmk2.VO.FtpInfoDto;
import org.tecktown.inforainmk2.VO.ResultCode;
import org.tecktown.inforainmk2.VO.values;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    TextView textResponse;
    FTPclient ftPclient = new FTPclient();
    ResultCode ftp = new ResultCode();
    static ArrayList<ContentsVO> contentsVO = new ArrayList<ContentsVO>();
    TextView correct;
//    ArrayList<ContentsVO> contentsVO = values.contentsVO;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            System.out.println("handle" + msg);
            switch (msg.what) {
                case HttpRequest.MSG_RESPONSE_FAIL:
                    resposeFailed();
                    break;
                case HttpRequest.MSG_RESPONSE_SUCCESS:
                    responseSuccessd((String) msg.obj);
                    break;
            }
            super.handleMessage(msg);

        }
    };

    Handler handler1 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            System.out.println("handle1" + msg);

            switch (msg.what) {
                case HttpRequest.MSG_RESPONSE_SUCCESS:
                    responseSuccessd2((String) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditText id = (EditText) findViewById(R.id.STBID);
        EditText pw = (EditText) findViewById(R.id.STBPassword);

        textResponse = (TextView) findViewById(R.id.textResponse);
        correct = (TextView)findViewById(R.id.alert);

        Button butSend = (Button) findViewById(R.id.signin);
        butSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpRequest httpRequest = new HttpRequest("POST", "http://dbs267.iptime.org:8080/client/connect", handler);
                String json = "{\"stbId\":\"" + id.getText().toString() + "\", \"stbPw\":\"" + pw.getText().toString() + "\"}";
                httpRequest.setBody(json);
                httpRequest.start();

            }
        });
    }

    void resposeFailed() {
        Toast.makeText(this, "Response Failed", Toast.LENGTH_SHORT).show();
    }

    void responseSuccessd(String response) {
        Log.d("login", "responseSuccessd@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        textResponse.setText(response);
        Log.d("login", "Success");

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        JsonObject jsonObject = (JsonObject) parser.parse(response);

        Log.d("ftp", jsonObject.toString());

        ftp = gson.fromJson(jsonObject, ResultCode.class);

        if (ftp != null) {
            HttpRequest httpRequest2 = new HttpRequest("GET", "http://dbs267.iptime.org:8080/client/contents/1", handler1);
            httpRequest2.setBody("1");
            httpRequest2.start();

        }


    }

    void responseSuccessd2(String response) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(response);
        JsonElement listElement = jsonObject.get("responseBody");
        JsonObject connObject = listElement.getAsJsonObject();
        JsonElement connElement = connObject.get("groupDataList");
        JsonArray connArray = connElement.getAsJsonArray();
        Log.d("Contents", connArray.toString());

        contentsVO = gson.fromJson(connArray, new TypeToken<ArrayList<ContentsVO>>(){}.getType());

        show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

    void show() {
        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout loginLayout = (LinearLayout) vi.inflate(R.layout.login_alert, null);
        //  ContentsVO contents = new ContentsVO();
//                Log.d("contents", );
        Log.d("ftp user", ftp.getFtpIP() + ftp.getFtpPORT() + ftp.getFtpUser() + ftp.getFtpPw());
        new Thread(new Runnable() {
            @Override
            public void run() {
                ftPclient.ftpConnect(ftp.getFtpIP(), ftp.getFtpPORT(), ftp.getFtpUser(), ftp.getFtpPw());

                for (int i = 0; i < contentsVO.size(); i++) {
                    ftPclient.downloadFile(contentsVO.get(i).getFileName());
                    }
                }
            }).start();

//        Show2();

    }

//    private void Show2() {
//        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout loginLayout = (LinearLayout) vi.inflate(R.layout.login_alert, null);
//
//    }
}

