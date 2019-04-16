package com.ecms.bean;


import com.ecms.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;

public class DepartmentInfo extends BaseInfo {

    private Integer departmentNumber;
    private String departmentNumberDisplay;
    private String departmentName;
    private Integer departmentAdminId;
    private String adminName;
    // 下级部门数
    private int children;
    // 直属部门人数
    private int employees;
    // 部门总人数（包含下级部门）
    private long totalEmployees;
    private Integer parentId;
    private Integer rootId;



    public Integer getDepartmentNumber() {
        if (departmentNumber == null && StringUtils.isNotBlank(departmentNumberDisplay)) {
            return NumberUtils.getNumberFromDisplay(departmentNumberDisplay);
        }
        return departmentNumber;
    }

    public void setDepartmentNumber(Integer departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

    public Integer getDepartmentAdminId() {
        return departmentAdminId;
    }

    public void setDepartmentAdminId(Integer departmentAdminId) {
        this.departmentAdminId = departmentAdminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public int getEmployees() {
		return employees;
	}

	public void setEmployees(int employees) {
		this.employees = employees;
	}

	public long getTotalEmployees() {
		return totalEmployees;
	}

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public void setDepartmentNumberDisplay(String departmentNumberDisplay) {
        this.departmentNumberDisplay = departmentNumberDisplay;
    }

    public String getDepartmentNumberDisplay() {
        if (StringUtils.isNotBlank(this.departmentNumberDisplay)) {
            return departmentNumberDisplay;
        } else if (this.departmentNumber != null) {
            return NumberUtils.getNumberDisplay4(this.departmentNumber);
        } else {
            return null;
        }
    }
}
