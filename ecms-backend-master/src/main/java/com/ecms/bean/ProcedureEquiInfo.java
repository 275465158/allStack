package com.ecms.bean;

import java.util.List;
import java.util.Map;

public class ProcedureEquiInfo extends BaseCompanyAndStatusInfo {

//    //程序编号
//    private String procedureNum;

    //程序名称
    private String procedureName;

    //设备型号
    private String equipmentModel;

    //设备名称
    private String equipmentName;

    //设备文件
    private  ProcedureEquiFileInfo procedureEquiFileInfo;//后期会变为多个文件
    
    private List<Map<String, Object>> procedureEquiFileInfoList;
    
    

//    public String getProcedureNum() {
//		return procedureNum;
//	}
//
//	public void setProcedureNum(String procedureNum) {
//		this.procedureNum = procedureNum;
//	}

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

	public Object getProcedureEquiFileInfo() {
		return procedureEquiFileInfo;
	}

	public void setProcedureEquiFileInfo(ProcedureEquiFileInfo procedureEquiFileInfo) {
		this.procedureEquiFileInfo = procedureEquiFileInfo;
	}

	public List<Map<String, Object>> getProcedureEquiFileInfoList() {
		return procedureEquiFileInfoList;
	}

	public void setProcedureEquiFileInfoList(List<Map<String, Object>> procedureEquiFileInfoList) {
		this.procedureEquiFileInfoList = procedureEquiFileInfoList;
	}




    

}