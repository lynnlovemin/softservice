package com.lynn.user.result;

import java.util.List;

public class MultiResult<T> extends Result {

	private List<T> data;

	private long total;
	
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
