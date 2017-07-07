package com.uyi.xinf.model.bean;

public class ViewPagerBean{
	   public String url;
	   public String value;
	   public boolean isBus;
	   public ViewPagerBean(String url,String value){
		   this.url = url;
		   this.value  = value;
		   this.isBus = true;
	   }
	public ViewPagerBean(String url, String value, boolean isBus) {
		super();
		this.url = url;
		this.value = value;
		this.isBus = isBus;
	}
	@Override
	public String toString() {
		return "ViewPagerBean [url=" + url + ", value=" + value + ", isBus="
				+ isBus + "]";
	}
	   
	   
}
