package com.ecms.common;

import java.io.Serializable;
import java.util.List;

public class Pager<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private int pageNum;
	private int pageSize;
	private long total;
	private long pages;
	private List<T> list;

	public Pager() {

	}

	public Pager(int pageNum, int pageSize, long total, List list) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = total;
		this.list = list;
		this.pages = total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPages() {
		return total % pageSize == 0 ? total / pageSize : (total / pageSize + 1);
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
