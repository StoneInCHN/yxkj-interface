package com.yxkj.json.base.admin.request;


public class AdminRequest {
	/**
	 * ID
	 */
	private Long id;
	/**
	 * IDs
	 */
	private Long[] ids;	
		
	/** 用户名 */
	private String userName;

	/** 密码 */
	private String password;
	
	/** 分页-页面大小*/
	private Integer pageSize = 10;

	/** 分页-当前页码*/
	private Integer pageNumber = 1;
	
	private String token;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
