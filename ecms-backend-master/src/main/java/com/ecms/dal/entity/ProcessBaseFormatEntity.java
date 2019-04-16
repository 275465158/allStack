package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "process_base_format")
public class ProcessBaseFormatEntity extends BaseCompanyAndStatusEntity {

    //格式编号
    @Column(name="id")
    private Integer id;

    //格式名称
    @Column(name = "process_format_name")
    private String processFormatName;

    //是否是基础格式
    @Column(name = "default_base_format")
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