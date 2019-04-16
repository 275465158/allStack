package com.ecms.bean;

/**
 * @author alan wu
 * created 4/2/2019
 */
public class BpmDefinitionInfo extends BaseCompanyAndStatusInfo {
    private Integer modelId;
    private String deploySubject;
    private String defKey;
    private Integer deployId;
    private String taskNameRules;
    private String deployDesc;
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
