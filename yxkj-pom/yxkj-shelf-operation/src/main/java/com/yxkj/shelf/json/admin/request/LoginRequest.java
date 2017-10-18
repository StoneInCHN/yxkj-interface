package com.yxkj.shelf.json.admin.request;

public class LoginRequest extends AdminRequest{
	
	private String captcha;
	
	private String captchaId;

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
	
	
	
}
