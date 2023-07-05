package com.karlos.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.karlos.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try { // retorna o usuario logado SE HOUVER!
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
