package com.yxkj.json.base;


public class BaseRequest {
	
	/** ID */
	private Long id;
	
	/** IDs*/
	private Long[] ids;	

	/** 用户名 */
	private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	

}
