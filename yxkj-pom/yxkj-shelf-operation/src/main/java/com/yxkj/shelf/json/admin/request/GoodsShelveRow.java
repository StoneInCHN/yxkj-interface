package com.yxkj.shelf.json.admin.request;

public class GoodsShelveRow {
	
	private Long id;
	private String spec;
	private Integer count;
	private Boolean need;
	
	public GoodsShelveRow(){
		
	}	
	public GoodsShelveRow(Long id,String spec,Integer count,Boolean need ){
		this.id = id;
		this.spec = spec;
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

	public Boolean getNeed() {
		return need;
	}
	public void setNeed(Boolean need) {
		this.need = need;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
}
