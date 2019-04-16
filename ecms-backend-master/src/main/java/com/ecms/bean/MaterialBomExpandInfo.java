package com.ecms.bean;

import java.util.List;

public class MaterialBomExpandInfo extends BaseInfo {
    //物料信息
    private MaterialBomInfo bomInfo;

    //物料编号 - 携带制程编码
    private String materialProcessNum;

    //项次号
    private String stepId;

    //物料数量
    private Float qty;

    //下级物料
    private List<MaterialBomExpandInfo> relationInfos;

    //制程
    private MaterialProcessInfo processInfo;

    public MaterialBomInfo getBomInfo() {
        return bomInfo;
    }

    public void setBomInfo(MaterialBomInfo bomInfo) {
        this.bomInfo = bomInfo;
    }

    public String getMaterialProcessNum() {
        return materialProcessNum;
    }

    public void setMaterialProcessNum(String materialProcessNum) {
        this.materialProcessNum = materialProcessNum;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public List<MaterialBomExpandInfo> getRelationInfos() {
        return relationInfos;
    }

    public void setRelationInfos(List<MaterialBomExpandInfo> relationInfos) {
        this.relationInfos = relationInfos;
    }

    public MaterialProcessInfo getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(MaterialProcessInfo processInfo) {
        this.processInfo = processInfo;
    }
}
