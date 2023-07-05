package com.karlos.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String username) { // gera um TOKEN usuando a palavra chave Secret
		return Jwts.builder().setSubject(username) // soma hora atual com tempo de expiração
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact(); // só aceita BYTES
	}
	
}
