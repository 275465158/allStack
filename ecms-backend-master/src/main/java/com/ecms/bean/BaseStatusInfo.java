package com.ecms.bean;

/**
 * 
 * @author linlvping
 * @Description 只包含状态字段的基类
 *
 */
public class BaseStatusInfo extends BaseInfo {

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
