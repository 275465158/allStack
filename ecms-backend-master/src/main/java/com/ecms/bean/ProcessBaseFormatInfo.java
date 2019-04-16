package com.ecms.bean;

/**
 * @author zhengli
 * 2018年9月3日
 * @Description 格式实体
 */
public class ProcessBaseFormatInfo extends BaseCompanyAndStatusInfo {

    //工艺格式编号
    private Integer id;

    //工艺格式名称
    private String processFormatName;

    //是否是基础格式 1：是 0：不是
    private Integer defaultBaseFormat;

    @Override
    public Integer getId() { return id; }

    @Override
    public void setId(Integer id) { this.id = id; }

    public String getProcessFormatName() {
        return processFormatName;
    }

    public void setProcessFormatName(String processFormatName) {
        this.processFormatName = processFormatName;
    }

    public Integer getDefaultBaseFormat() {
        return defaultBaseFormat;
    }

    public void setDefaultBaseFormat(Integer defaultBaseFormat) {
        this.defaultBaseFormat = defaultBaseFormat;
    }
}