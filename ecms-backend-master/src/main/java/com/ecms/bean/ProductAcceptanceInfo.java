package com.ecms.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductAcceptanceInfo extends BaseCompanyAndStatusInfo {

	private String name;
    private String owner;
    private Date created;
    private String iseditname;//验收名称是否被修改的flag，1为修改了，0为未修改

	private String contentJson;

	private List<ProductAcceptanceDetailInfo> productAcceptanceDetailInfoList;

	private ProductAcceptanceDetailInfo productAcceptanceDetailInfo;
	private List<Map<String, Object>> detailInfoList;
	private String inspectionDiagram;

	public String getInspectionDiagram() {
		return inspectionDiagram;
	}

	public void setInspectionDiagram(String inspectionDiagram) {
		this.inspectionDiagram = inspectionDiagram;
	}

	public String getIseditname() {
		return iseditname;
	}

	public void setIseditname(String iseditname) {
		this.iseditname = iseditname;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

	public List<Map<String, Object>> getDetailInfoList() {
		return detailInfoList;
	}

	public void setDetailInfoList(List<Map<String, Object>> detailInfoList) {
		this.detailInfoList = detailInfoList;
	}

	public List<ProductAcceptanceDetailInfo> getProductAcceptanceDetailInfoList() {
		return productAcceptanceDetailInfoList;
	}

	public void setProductAcceptanceDetailInfoList(List<ProductAcceptanceDetailInfo> productAcceptanceDetailInfoList) {
		this.productAcceptanceDetailInfoList = productAcceptanceDetailInfoList;
	}

	public ProductAcceptanceDetailInfo getProductAcceptanceDetailInfo() {
		return productAcceptanceDetailInfo;
	}

	public void setProductAcceptanceDetailInfo(ProductAcceptanceDetailInfo productAcceptanceDetailInfo) {
		this.productAcceptanceDetailInfo = productAcceptanceDetailInfo;
	}

	public String getContentJson() {

		return contentJson;
	}

	public void setContentJson(String contentJson) {
		this.contentJson = contentJson;
	}
}