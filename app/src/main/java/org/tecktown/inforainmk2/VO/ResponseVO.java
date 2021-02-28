package org.tecktown.inforainmk2.VO;


public class ResponseVO {

//    @SerializedName("resultCode")
//    @Expose
    private Boolean resultCode;
//    @SerializedName("message")
//    @Expose
    private String message;
//    @SerializedName("sessionId")
//    @Expose
    private String sessionId;
//    @SerializedName("responseBody")
//    @Expose
    private ResponseBody responseBody;

    public Boolean getResultCode() {
        return resultCode;
    }

    public void setResultCode(Boolean resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

}