package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "post")
public class PostEntity extends BaseCompanyAndStatusEntity {

	private String name;
	@Column(name = "post_number")
	private Integer postNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(Integer postNumber) {
		this.postNumber = postNumber;
	}
}
