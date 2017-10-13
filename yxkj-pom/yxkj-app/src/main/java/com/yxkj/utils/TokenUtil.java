package com.yxkj.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.yxkj.beans.Setting;

public class TokenUtil {
	
	public static Setting setting = SettingUtils.get();
	
	public static String getJWTString(String id, String subject) {
	    // The JWT signature algorithm we will be using to sign the token
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	
	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	
	    // We will sign our JWT with our ApiKey secret
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("secret");
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	
	    // Let's set the JWT Claims
	    JwtBuilder builder =
	        Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject)
	            .signWith(signatureAlgorithm, signingKey);
	
	    // if it has been specified, let's add the expiration
	    long ttlMillis = setting.getTokenTimeOut();
	    if (ttlMillis >= 0) {
	      long expMillis = nowMillis + ttlMillis;
	      Date exp = new Date(expMillis);
	      builder.setExpiration(exp);
	    }
	
	    // Builds the JWT and serializes it to a compact, URL-safe string
	    return builder.compact();
	}

	/**
	   * 验证jwt token
	   * 
	   * @param jwt
	   * @return
	   */
    public static Claims parseJWT(String jwt) {
    	try {
    		Claims claims =
	          Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("secret"))
	              .parseClaimsJws(jwt).getBody();
	        // This line will throw an exception if it is not a signed JWS (as expected)
    		
	        // System.out.println("ID: " + claims.getId());
    		// System.out.println("Subject: " + claims.getSubject());
    		// System.out.println("Issuer: " + claims.getIssuer());
    		// System.out.println("Expiration: " + claims.getExpiration());
		    return claims;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	 }

    public static boolean isValid(String token, Key key) {
    	try {
    		Jwts.parser().setSigningKey(key).parseClaimsJws(token.trim());
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }
}
