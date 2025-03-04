package com.yxkj.service;

import java.awt.image.BufferedImage;

import com.yxkj.beans.Setting.CaptchaType;


/**
 * Service - 验证码
 * 
 */
public interface CaptchaService {

  /**
   * 生成验证码图片
   * 
   * @param captchaId 验证ID
   * @return 验证码图片
   */
  BufferedImage buildImage(String captchaId);

  /**
   * 验证码验证
   * 
   * @param captchaType 验证码类型
   * @param captchaId 验证ID
   * @param captcha 验证码(忽略大小写)
   * @return 验证码验证是否通过
   */
  boolean isValid(CaptchaType captchaType, String captchaId, String captcha);

}
