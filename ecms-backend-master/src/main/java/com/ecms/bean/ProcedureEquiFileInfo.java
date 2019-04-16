package com.ecms.bean;

public class ProcedureEquiFileInfo extends BaseCompanyAndStatusInfo {
    //文件编号
    private String fileid;

    //设备程序编号
    private Integer procedureEquiId;

    //文件名称
    private String fileName;
    
    //文件地址
    private String fileUrl;

    //版本号
    private Integer fileVersion;

    //编制人
    private String compileMan;

    //编制日期
    private String compileDate;

    //审批人
    private String approvalMan;

    //审批结果
    private String approvalResult;

    //审批时间
    private String approvalDate;

    //使用时间
    private String useDate;

    //备注
    private String remark;
    
    

   
    public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Integer getProcedureEquiId() {
        return procedureEquiId;
    }

    public void setProcedureEquiId(Integer procedureEquiId) {
        this.procedureEquiId = procedureEquiId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(Integer fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getCompileMan() {
        return compileMan;
    }

    public void setCompileMan(String compileMan) {
        this.compileMan = compileMan;
    }

    public String getCompileDate() {
        return compileDate;
    }

    public void setCompileDate(String compileDate) {
        this.compileDate = compileDate;
    }

    public String getApprovalMan() {
        return approvalMan;
    }

    public void setApprovalMan(String approvalMan) {
        this.approvalMan = approvalMan;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}