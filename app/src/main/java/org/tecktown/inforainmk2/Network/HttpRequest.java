package org.tecktown.inforainmk2.Network;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpRequest extends Thread {
    public static final int MSG_RESPONSE_SUCCESS = 101;
    public static final int GET_RESPONSE_SUCCESS = 102;
    public static final int MSG_RESPONSE_FAIL = 100;

    StringBuffer paramBuffer = null;
    String method;
    String uri;
    Handler handler;

     public HttpRequest(String method, String uri, Handler handler) {
        this.method = method;
        this.uri = uri;
        this.handler = handler;
    }


    @Override
    public void run() {
        String response = "";

        if (method.equals("GET")) response = get();
        else response = post();

        if (response == null || response.length() < 1) {
            handler.sendEmptyMessage(MSG_RESPONSE_FAIL);
            return ;
        }
            handler.obtainMessage(MSG_RESPONSE_SUCCESS, response).sendToTarget();


     }

    String get() {
        String response = "";

        if (paramBuffer.length() > 0) {
            uri += "?";
            uri += paramBuffer.toString();
        }

        try {
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String string = bufferedReader.readLine();
                if (string == null) break;
                else stringBuilder.append(string);
            }
            response = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    String post() {
        String response = "";
        try {
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            if (paramBuffer.length() > 0) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                outputStreamWriter.write(paramBuffer.toString());
                outputStreamWriter.flush();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String string = bufferedReader.readLine();
                if (string == null) break;
                else stringBuilder.append(string);
            }
            response = stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    void setParam(String key, String value) {
        if (paramBuffer == null) paramBuffer = new StringBuffer();
        if (paramBuffer.length() > 1) paramBuffer.append("&");
        paramBuffer.append(key);
        paramBuffer.append("=");
        paramBuffer.append(value);
    }

    public void setBody(String body) {
        if (paramBuffer == null) paramBuffer = new StringBuffer();
        paramBuffer.append(body);
    }
}