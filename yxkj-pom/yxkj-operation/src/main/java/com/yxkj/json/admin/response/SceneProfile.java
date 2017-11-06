package com.yxkj.json.admin.response;

public class SceneProfile {
	
	private String key;
	private String label;
	private String sn;
	private boolean disabled;
	
	public SceneProfile(){
		
	}	
	public SceneProfile(String key,String label,String sn,boolean disabled ){
		this.key = key;
		this.label = label;
		this.sn = sn;
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
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	
	

}
