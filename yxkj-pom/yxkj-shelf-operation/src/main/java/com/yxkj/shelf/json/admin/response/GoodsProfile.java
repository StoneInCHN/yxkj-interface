package com.yxkj.shelf.json.admin.response;

public class GoodsProfile {
	
	private String key;
	private String label;
	private String sn;
	private String spec;
	private boolean disabled;
	
	public GoodsProfile(){
		
	}	
	public GoodsProfile(String key,String label,String sn,String spec,boolean disabled ){
		this.key = key;
		this.label = label;
		this.sn = sn;
		this.spec = spec;	
		this.disabled = disabled;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	
	

}
