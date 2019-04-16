package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "procedure_equi_file")
public class ProcedureEquiFileEntity extends BaseCompanyAndStatusEntity {
    //文件编号
    @Column(name="id")
    private Integer id;

    //设备程序编号
    @Column(name = "procedure_equi_id")
    private Integer procedureEquiId;

    //文件名称
    @Column(name = "file_name")
    private String fileName;
    
  //文件地址
    @Column(name="file_url")
    private String fileUrl;

    //版本号
    @Column(name="file_version")
    private Integer fileVersion;

    //编制人
    @Column(name = "compile_man")
    private String compileMan;

    //编制日期
    @Column(name = "compile_date")
    private String compileDate;

    //审批人
    @Column(name = "approval_man")
    private String approvalMan;

    //备注
    @Column(name="remark")
    private String remark;

    //审批结果
    @Column(name="approval_result")
    private String approvalResult;

    //审批时间
    @Column(name="approval_date")
    private String approvalDate;

    //使用时间
    @Column(name="use_date")
    private String useDate;

    //状态
    @Column(name="status")
    private Integer status;

    @Override
    public Integer getId() { return id; }

    @Override
    public void setId(Integer id) { this.id = id; }

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

    public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
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

    @Override
    public Integer getStatus() { return status; }

    @Override
    public void setStatus(Integer status) { this.status = status; }
}