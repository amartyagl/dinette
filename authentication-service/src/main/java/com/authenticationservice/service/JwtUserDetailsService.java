package com.authenticationservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.authenticationservice.model.UserDao;
import com.authenticationservice.model.UserDto;
import com.authenticationservice.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUserDetailsService {

	private static final int JWT_TOKEN_VALIDITY = 1000*60*60*2;

	private String secret = "dadsnrgsthghdjgmdhgxfxmgfdyjmhgtxgdcjbngkjghchhfjdlshvfishdvuhzsuhvujksdbvkub";

	@Autowired
	private UserRepository userDao;

	public UserDao save(UserDto user) {
		UserDao newUser = new UserDao();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());

		return userDao.save(newUser);

	}

	public String authenticateUser(String email, String password) {
		UserDao user1 = userDao.findById(email).get();
		String jwttoken = "";
		if (email.equals(user1.getEmail()) && password.equals(user1.getPassword())) {
			Claims claims = Jwts.claims();
			claims.put("emailId", user1.getEmail());
			jwttoken = Jwts.builder().setClaims(claims).setSubject(user1.getEmail()).setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000*60*60*2))
					.signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
		}
		return jwttoken;
	}

}
