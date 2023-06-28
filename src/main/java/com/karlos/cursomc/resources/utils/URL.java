package com.karlos.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static String decoParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8"); // converte caso tenha espaço no nome da url
		}
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Integer> decodeIntList(String s){
		List<Integer> list = new ArrayList<>();
		String[] vet = s.split(",");
		
		for (int i = 0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
	}
}
