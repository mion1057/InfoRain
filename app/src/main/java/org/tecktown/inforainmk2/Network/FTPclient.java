package org.tecktown.inforainmk2.Network;

import android.os.Environment;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.tecktown.inforainmk2.VO.ContentsVO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FTPclient {
    private final String TAG = "FTPUtil";
    public static String desDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/seongmin/" ;
    public static String workDir = "/HDD1/publicFolder/inforain/Group1/";
    FTPClient FTPClient= new FTPClient();
    ContentsVO[] contentsVO;


    public boolean ftpConnect(String server, int port, String id, String password){
        boolean result = false;


        try {
            FTPClient = new FTPClient();
            FTPClient.setControlEncoding("UTF-8");
            FTPClient.connect(server, port);
            result = FTPClient.login(id, password);
            FTPClient.setBufferSize(1024*1024);
            FTPClient.setConnectTimeout(720000);
            FTPClient.setDefaultTimeout(720000);
            FTPClient.setKeepAlive(true);
            FTPClient.setControlKeepAliveReplyTimeout(3000);
            FTPClient.setControlKeepAliveTimeout(10);
            FTPClient.setDataTimeout(1000 * 60);
            FTPClient.enterLocalPassiveMode();
            FTPClient.changeWorkingDirectory(workDir);

            Log.d(TAG, String.valueOf(FileList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    ArrayList<ContentsVO> FileList() {
        ContentsVO fileVO;
        ArrayList<ContentsVO> filelists = new ArrayList<ContentsVO>();
        ContentsVO[] ftpFiles = new ContentsVO[0];

        if (ftpFiles != null){
            for(int i=0;i<ftpFiles.length;i++){
                ContentsVO file = contentsVO[i];
                fileVO = new ContentsVO(file.getFileName(), file.getFileSize(), file.getRegDate());
                filelists.add(fileVO);
                Log.d(TAG, filelists.toString());
            }
        }

        return filelists;
    }

    public boolean ftpDisconnect(){
        boolean result = false;
        try {
            FTPClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean downloadFile(String fileName){
        boolean result = false;
        try {
                FTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                String remofile = workDir + fileName;
                Log.d("파일 경로", remofile);
                File down = new File(desDir , fileName);
                boolean isExist = down.exists();
                if(isExist){
                    Log.d("FileCheck : " , "저장된 파일이 존재합니다.");
                    Log.d("파일 크기", String.valueOf(down.length()));
                }
                else {
                    Log.d("파일 경로", down.getName());
                    FileOutputStream fos = new FileOutputStream(down);
                    result = FTPClient.retrieveFile(remofile, fos);
                    fos.close();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

//    public static boolean downloadFolder(String server, int port, String id, String pw) {
//        FTPClient ftpClient = null;
//        boolean result = false;
//
//        try {
//            ftpClient = new FTPClient();
//            ftpClient.setControlEncoding("UTF-8");
//            ftpClient.connect(server, port);
//
//            ftpClient.login(id, pw);
//            ftpClient.changeWorkingDirectory(workDir);
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//            ftpClient.enterLocalPassiveMode();
//
//            FTPFile[] fileList = ftpClient.listFiles();
//            for (int i=0; i<fileList.length; i++) {
//                File destFile = new File(desDir, fileList[i].getName());
//                OutputStream outputStream = null;
//                try {
//                    outputStream = new FileOutputStream(destFile);
//                    result = ftpClient.retrieveFile(fileList[i].getName(), outputStream);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    outputStream.close();
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//            result = false;
//        } catch (IOException e) {
//            e.printStackTrace();
//            result = false;
//        }
//
//        return result;
//    }

