package com.yxkj.json.admin.response;

public class SelectProps {
	
	private String value;
	private String label;
	private boolean disabled;
	
	public SelectProps(){
		
	}	
	public SelectProps(String value,String label,boolean disabled ){
		this.value = value;
		this.label = label;
		this.disabled = disabled;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}


}
