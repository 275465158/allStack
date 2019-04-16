package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author alan wu
 * Created 4/2/2019
 * 流程属性表
 *          字段随时扩展
 */
@Table(name = "bpm_definition")
public class BpmDefinitionEntity extends BaseCompanyAndStatusEntity{

    //流程id
    @Column(name = "bpm_definition_model_id")
    private Integer modelId;

    //部署id
    @Column(name = "bpm_definition_deploy_id")
    private Integer deployId;

    //流程名称
    @Column(name = "bpm_deploy_subject")
    private String deploySubject;

    //流程模块
    @Column(name = "bpm_deploy_def_key")
    private String defKey;

    //任务名称规则
    @Column(name = "bpm_deploy_task_name_rules")
    private String taskNameRules;

    //流程备注
    @Column(name = "bpm_deploy_descp")
    private String deployDesc;

    //流程设计图数据
    @Column(name = "bpm_deploy_xml")
    private String deployXml;

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getDeploySubject() {
        return deploySubject;
    }

    public void setDeploySubject(String deploySubject) {
        this.deploySubject = deploySubject;
    }

    public Integer getDeployId() {
        return deployId;
    }

    public void setDeployId(Integer deployId) {
        this.deployId = deployId;
    }

    public String getDefKey() {
        return defKey;
    }

    public void setDefKey(String defKey) {
        this.defKey = defKey;
    }

    public String getTaskNameRules() {
        return taskNameRules;
    }

    public void setTaskNameRules(String taskNameRules) {
        this.taskNameRules = taskNameRules;
    }

    public String getDeployDesc() {
        return deployDesc;
    }

    public void setDeployDesc(String deployDesc) {
        this.deployDesc = deployDesc;
    }

    public String getDeployXml() {
        return deployXml;
    }

    public void setDeployXml(String deployXml) {
        this.deployXml = deployXml;
    }
}
