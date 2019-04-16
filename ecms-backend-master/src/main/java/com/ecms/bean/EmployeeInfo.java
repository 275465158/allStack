package com.ecms.bean;

import com.ecms.utils.CalculateUtils;

public class EmployeeInfo extends BaseCompanyAndStatusInfo {

    private String name;
    private String birthday;
    private String sex;
    private String phone;
    private String identificationType;
    private String identificationNumber;
    private String identificationAddress;
    private String address;
    private Integer rootDepartmentId;
    private Integer departmentId;
    private Integer postId;
    private String maritalStatus;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String joinDate;
    private String dimissionDate;
    private Integer secondDepartmentId;
    private Integer thirdDepartmentId;
    private String firstDepartmentName;
    private String secondDepartmentName;
    private String thirdDepartmentName;
    private String postName;
    private Integer status;
    private String departmentName;
    private Integer equiflag;
    private Integer employeeNumber;
    private String employeePic;


	@Override
	public int hashCode() {
		return this.identificationNumber.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
       if ((obj == null) || (obj.getClass() != this.getClass())) {
		   return false;
	   }
        return obj.hashCode() == this.hashCode();

	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getIdentificationAddress() {
		return identificationAddress;
	}

	public void setIdentificationAddress(String identificationAddress) {
		this.identificationAddress = identificationAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRootDepartmentId() {
		return rootDepartmentId;
	}

	public void setRootDepartmentId(Integer rootDepartmentId) {
		this.rootDepartmentId = rootDepartmentId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getEmergencyContactName() {
		return emergencyContactName;
	}

	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}

	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}

	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getDimissionDate() {
		return dimissionDate;
	}

	public void setDimissionDate(String dimissionDate) {
		this.dimissionDate = dimissionDate;
	}

    public Integer getSecondDepartmentId() {
        return secondDepartmentId;
    }

    public void setSecondDepartmentId(Integer secondDepartmentId) {
        this.secondDepartmentId = secondDepartmentId;
    }

    public Integer getThirdDepartmentId() {
        return thirdDepartmentId;
    }

    public void setThirdDepartmentId(Integer thirdDepartmentId) {
        this.thirdDepartmentId = thirdDepartmentId;
    }

    public String getFirstDepartmentName() {
        return firstDepartmentName;
    }

    public void setFirstDepartmentName(String firstDepartmentName) {
        this.firstDepartmentName = firstDepartmentName;
    }

    public String getSecondDepartmentName() {
        return secondDepartmentName;
    }

    public void setSecondDepartmentName(String secondDepartmentName) {
        this.secondDepartmentName = secondDepartmentName;
    }

    public String getThirdDepartmentName() {
        return thirdDepartmentName;
    }

    public void setThirdDepartmentName(String thirdDepartmentName) {
        this.thirdDepartmentName = thirdDepartmentName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getJobAge() {
        return CalculateUtils.getJobAge(this.joinDate, this.dimissionDate);
    }

    public String getAge() {
        return CalculateUtils.getAgeByBirth(this.birthday);
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getEquiflag() {
		return 0;
	}

	public void setEquiflag(Integer equiflag) {
		this.equiflag = 0;
	}

	public Integer getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeePic() {
		return employeePic;
	}

	public void setEmployeePic(String employeePic) {
		this.employeePic = employeePic;
	}

}
