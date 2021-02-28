package org.tecktown.inforainmk2.VO;

public class ResponseBody {

//    @SerializedName("stbId")
//    @Expose
    private String stbId;
//    @SerializedName("stbPw")
//    @Expose
    private String stbPw;
//    @SerializedName("groupCode")
//    @Expose
    private Integer groupCode;

    public String getStbId() {
        return stbId;
    }

    public void setStbId(String stbId) {
        this.stbId = stbId;
    }

    public String getStbPw() {
        return stbPw;
    }

    public void setStbPw(String stbPw) {
        this.stbPw = stbPw;
    }

    public Integer getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Integer groupCode) {
        this.groupCode = groupCode;
    }

}