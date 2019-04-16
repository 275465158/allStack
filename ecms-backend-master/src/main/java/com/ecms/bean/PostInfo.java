package com.ecms.bean;

import com.ecms.utils.NumberUtils;

public class PostInfo extends BaseCompanyAndStatusInfo {

	private String name;
	private Long count;
	private Integer postNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Integer getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(Integer postNumber) {
		this.postNumber = postNumber;
	}

	public String getPostNumberDisplay() {
		if (this.postNumber != null) {
			return NumberUtils.getNumberDisplay4(this.postNumber);
		} else {
			return null;
		}
	}
}
