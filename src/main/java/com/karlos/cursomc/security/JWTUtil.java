package com.karlos.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token); // claim são as reividicações do usuario
		if (claims != null) {
			String userName = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			
			if (userName != null && expirationDate != null && now.before(expirationDate)) {
				return true; // now.before verifica se o momento atual é antes da expiração
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
			// Recupera os claims apartir de um TOKEN
		}
		catch (Exception e) {
			return null;
		}
	}
	
}
