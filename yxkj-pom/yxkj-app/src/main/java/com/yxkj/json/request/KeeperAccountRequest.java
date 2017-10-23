package com.yxkj.json.request;

public class KeeperAccountRequest {
	
	/**
	 * 管家登录密码
	 */
	private String password;
	
	/**
	 * 管家登录手机号
	 */
	private String cellPhoneNum;
	
	/**
	 * 管家登录验证码
	 */
	private String verificationCode;
	
	/**
	 * 验证码类型
	 */
	private String type;

  /**
	 * 旧密码
	 */
	private String oldPwd;
	
	/**
	 * 新密码
	 */
	private String newPwd;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCellPhoneNum() {
		return cellPhoneNum;
	}

	public void setCellPhoneNum(String cellPhoneNum) {
		this.cellPhoneNum = cellPhoneNum;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
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
