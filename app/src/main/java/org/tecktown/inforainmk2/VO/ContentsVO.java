package org.tecktown.inforainmk2.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class ContentsVO {


    public Integer contentsCode;

    private Integer groupCode;

    private String contentsName;

    private String regDate;

    private String upDate;

    private String fileName;

    private Integer fileSize;

    private String fileType;

    private Integer fileSeq;

    private Integer playTime;


    public ContentsVO(){
    }

    public ContentsVO(String name, int size, String regDate) {
        this.fileName = name;
        this.fileSize = size;
        this.regDate = regDate;
    }


    public Integer getContentsCode() {
        return contentsCode;
    }

    public void setContentsCode(Integer contentsCode) {
        this.contentsCode = contentsCode;
    }

    public Integer getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Integer groupCode) {
        this.groupCode = groupCode;
    }

    public String getContentsName() {
        return contentsName;
    }

    public void setContentsName(String contentsName) {
        this.contentsName = contentsName;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUpDate() {
        return upDate;
    }

    public void setUpDate(String upDate) {
        this.upDate = upDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getFileSeq() {
        return fileSeq;
    }

    public void setFileSeq(Integer fileSeq) {
        this.fileSeq = fileSeq;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    @Override
    public String toString() {
        return "ContentsList{" +
                "contentsCode=" + contentsCode +
                ", groupCode=" + groupCode +
                ", contentsName='" + contentsName + '\'' +
                ", regDate='" + regDate + '\'' +
                ", upDate='" + upDate + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                ", fileSeq=" + fileSeq +
                ", playTime=" + playTime +
                '}';
    }
}
