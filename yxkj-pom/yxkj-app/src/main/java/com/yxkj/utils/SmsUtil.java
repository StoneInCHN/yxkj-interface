package com.yxkj.utils;

import java.util.Random;

import org.springframework.transaction.annotation.Transactional;

import com.yxkj.common.log.LogUtil;

public class SmsUtil {
	
	private static Random random = new Random();
	
	@Transactional
	public static String sendVerificationCode(String cellPhoneNum){
		
		String verificationCode = getRandNum(6);
		
		//调用短信平台发送短信
		
		LogUtil.debug(SmsUtil.class, "sendVerificationCode","向手机用户%s发送验证码:%s", cellPhoneNum, verificationCode);
		
		return verificationCode;
	}
	
	public static String getRandNum(int charCount) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < charCount; i++) {
            int c = random.nextInt(10);
            buffer.append(c+"");
        }
        return buffer.toString();
    }
	
}
