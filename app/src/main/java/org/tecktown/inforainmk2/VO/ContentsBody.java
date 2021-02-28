package org.tecktown.inforainmk2.VO;

import java.util.List;

public class ContentsBody {

    private Integer groupCode;
    private String groupName;
    private String regDate;
    private List<ContentsVO> contentsVO = null;
    public Integer getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Integer groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public List<ContentsVO> getContentsVO() {
        return contentsVO;
    }

    public void setContentsVO(List<ContentsVO> contentsVO) {
        this.contentsVO = contentsVO;
    }

}
