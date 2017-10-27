package com.yxkj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.beans.Setting;
import com.yxkj.common.log.LogUtil;

public class SmsUtil {
	
	private static Random random = new Random();
	
	private static Setting setting = SettingUtils.get();
	
	@Transactional
	public static String sendVerificationCode(String cellPhoneNum, String message) throws Exception{
		
		String verificationCode = getRandNum(6);
		
		//调用短信平台发送短信
		sendMessage(cellPhoneNum, message);
		
		LogUtil.debug(SmsUtil.class, "sendVerificationCode","向手机用户%s发送验证码:%s", cellPhoneNum, verificationCode);
		return verificationCode;
	}
	
    public static void sendMessage(String phoneNum, String message) throws Exception{
        String url = setting.getSmsUrl();
        String queryString = "Id="+setting.getSmsOrgId()
                +"&Name="+setting.getSmsUserName()
                +"&Psw="+setting.getSmsPwd()
                +"&Message="+message
                +"&Phone="+phoneNum
                +"&Timestamp=0";
        
        StringBuffer response = new StringBuffer(); 
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        method.setQueryString(URIUtil.encodeQuery(queryString)); 
        client.executeMethod(method);
        if (method.getStatusCode() == HttpStatus.SC_OK) { 
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream())); 
            String line; 
            while ((line = reader.readLine()) != null) { 
                   response.append(line); 
            } 
            reader.close(); 
        }
        System.out.println(response.toString());
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
