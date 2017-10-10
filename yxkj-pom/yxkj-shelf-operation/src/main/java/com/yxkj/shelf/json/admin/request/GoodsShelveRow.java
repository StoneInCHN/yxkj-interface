package com.yxkj.shelf.json.admin.request;

public class GoodsShelveRow {
	
	private Long id;
	private String height;
	private Integer count;
	private Boolean need;
	
	public GoodsShelveRow(){
		
	}	
	public GoodsShelveRow(Long id,String height,Integer count,Boolean need ){
		this.id = id;
		this.height = height;
		this.count = count;
		this.need = need;		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public Boolean getNeed() {
		return need;
	}
	public void setNeed(Boolean need) {
		this.need = need;
	}
	
	

}
