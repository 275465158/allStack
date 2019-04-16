package com.ecms.bean;

/**
 * @author Richie
 * @version 2018/12/18 10:09
 */
public class UnqualifiedHandleInfo extends BaseCompanyAndStatusInfo {
    //类型:0:来料，1:生产
    private Integer handleType;

    //处理名称
    private String handleName;

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }
}
