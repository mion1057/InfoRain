package org.tecktown.inforainmk2.VO;



public class Example {

//    @SerializedName("responseVo")
//    @Expose
    private ResponseVO responseVo;
//    @SerializedName("ftpInfoDto")
//    @Expose
    private FtpInfoDto ftpInfoDto;

    public ResponseVO getResponseVo() {
        return responseVo;
    }

    public void setResponseVo(ResponseVO responseVo) {
        this.responseVo = responseVo;
    }

    public FtpInfoDto getFtpInfoDto() {
        return ftpInfoDto;
    }

    public void setFtpInfoDto(FtpInfoDto ftpInfoDto) {
        this.ftpInfoDto = ftpInfoDto;
    }

}