package com.uyi.app.model.bean;

public class OrderBy {
	public String orderName;
	public Boolean isDesc;
	
	
	private OrderBy(String orderName, Boolean isDesc) {
		super();
		this.orderName = orderName;
		this.isDesc = isDesc;
	}

	
	
	
	public static OrderBy budlerOrderByAsc(String key){
		return new OrderBy(key, false);
	}
	
	public static OrderBy budlerOrderByDesc(String key){
		return new OrderBy(key, true);
	}
}
