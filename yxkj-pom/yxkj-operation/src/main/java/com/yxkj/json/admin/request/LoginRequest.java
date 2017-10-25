package com.yxkj.json.admin.request;

import com.yxkj.json.base.BaseRequest;

public class LoginRequest extends BaseRequest{
	
	/** 验证码*/
	private String captcha;
	
	/** 验证码ID*/
	private String captchaId;
	
	/** 自动登录*/
	private boolean autoLogin;	
	
	/** 密码（rsa密文） */
	private String password;
	

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}

	public boolean isAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
