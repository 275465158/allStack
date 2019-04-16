package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "procedure_equi")
public class ProcedureEquiEntity extends BaseCompanyAndStatusEntity {

    //程序编号
    @Column(name="id")
    private Integer id;

    //程序名称
    @Column(name = "procedure_name")
    private String procedureName;

    //设备型号
    @Column(name = "equipment_model")
    private String equipmentModel;

    //设备名称
    @Column(name = "equipment_name")
    private String equipmentName;

    //状态
    @Column(name="status")
    private Integer status;

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getStatus() { return status; }

    @Override
    public void setStatus(Integer status) { this.status = status; }

}