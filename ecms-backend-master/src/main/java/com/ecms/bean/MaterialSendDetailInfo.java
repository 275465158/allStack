package com.ecms.bean;

import java.util.List;
import java.util.Map;

/**
 * @author Richie
 * @version 2019/1/21 10:45
 */
public class MaterialSendDetailInfo extends BaseCompanyAndStatusInfo {
    //物料id
    private Integer materialId;
    //发货数量
    private Float sendQty;
    //合同id
    private String contractId;
    //物料编码
    private String materialCode;
    //物料名称
    private String materialName;
    //发货时间
    private String sendDate;
    //订单编号
    private String orderCode;
    //订单产品明细id
    private Integer sellerOrderProductId;
    //发货数组
    private List<Map<String,Object>> materialSendList;

    private List<Map<String,Object>> tableDataRow;

    //已发货数量
    private Float alreadySendQty;

    private Integer sendId;

    /**工厂物料编码**/
    private String factoryMaterialCode;

    /**物料类型**/
    private String number;

    /**原图材料**/
    private String originalMaterial;

    /**图号**/
    private String drawingCode;

    /**参数**/
    private String materialBomParamValueStr;

    /**物料来源**/
    private String source;

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Float getSendQty() {
        return sendQty;
    }

    public void setSendQty(Float sendQty) {
        this.sendQty = sendQty;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public List<Map<String, Object>> getTableDataRow() {
        return tableDataRow;
    }

    public void setTableDataRow(List<Map<String, Object>> tableDataRow) {
        this.tableDataRow = tableDataRow;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getSellerOrderProductId() {
        return sellerOrderProductId;
    }

    public void setSellerOrderProductId(Integer sellerOrderProductId) {
        this.sellerOrderProductId = sellerOrderProductId;
    }

    public List<Map<String, Object>> getMaterialSendList() {
        return materialSendList;
    }

    public void setMaterialSendList(List<Map<String, Object>> materialSendList) {
        this.materialSendList = materialSendList;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Float getAlreadySendQty() {
        return alreadySendQty;
    }

    public void setAlreadySendQty(Float alreadySendQty) {
        this.alreadySendQty = alreadySendQty;
    }

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public String getFactoryMaterialCode() {
        return factoryMaterialCode;
    }

    public void setFactoryMaterialCode(String factoryMaterialCode) {
        this.factoryMaterialCode = factoryMaterialCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getDrawingCode() {
        return drawingCode;
    }

    public void setDrawingCode(String drawingCode) {
        this.drawingCode = drawingCode;
    }

    public String getMaterialBomParamValueStr() {
        return materialBomParamValueStr;
    }

    public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
        this.materialBomParamValueStr = materialBomParamValueStr;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
