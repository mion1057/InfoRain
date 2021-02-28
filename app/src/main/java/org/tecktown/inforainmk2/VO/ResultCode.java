package org.tecktown.inforainmk2.VO;

public class ResultCode {

    private Boolean resultCode;
    private Integer statusCode;
    private String message;
    private String timestamp;
    private Object sessionId;
    private ResponseBody responseBody;
    private String ftpIP;
    private Integer ftpPORT;
    private String ftpUser;
    private String ftpPw;
    private String ftpWkdir;

    public Boolean getResultCode() {
        return resultCode;
    }

    public void setResultCode(Boolean resultCode) {
        this.resultCode = resultCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getSessionId() {
        return sessionId;
    }

    public void setSessionId(Object sessionId) {
        this.sessionId = sessionId;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    public String getFtpIP() {
        return ftpIP;
    }

    public void setFtpIP(String ftpIP) {
        this.ftpIP = ftpIP;
    }

    public Integer getFtpPORT() {
        return ftpPORT;
    }

    public void setFtpPORT(Integer ftpPORT) {
        this.ftpPORT = ftpPORT;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPw() {
        return ftpPw;
    }

    public void setFtpPw(String ftpPw) {
        this.ftpPw = ftpPw;
    }

    public String getFtpWkdir() {
        return ftpWkdir;
    }

    public void setFtpWkdir(String ftpWkdir) {
        this.ftpWkdir = ftpWkdir;
    }
}
