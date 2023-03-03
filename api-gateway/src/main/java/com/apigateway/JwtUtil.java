package com.apigateway;

import java.util.Date;   

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {


	private String secret = "dadsnrgsthghdjgmdhgxfxmgfdyjmhgtxgdcjbngkjghchhfjdlshvfishdvuhzsuhvujksdbvkub";

	public Claims getAllClaimsFromToken(String token) {
//        return Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody();
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();

    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

	

}
