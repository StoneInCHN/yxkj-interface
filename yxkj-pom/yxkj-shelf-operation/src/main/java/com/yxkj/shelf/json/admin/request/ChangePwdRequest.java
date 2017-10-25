package com.yxkj.shelf.json.admin.request;


public class ChangePwdRequest {
		
	/** 用户名 */
	private String userName;

	/** 旧密码（rsa密文） */
	private String oldPwd;
	
	/** 新密码 */
	private String newPwd;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

}
