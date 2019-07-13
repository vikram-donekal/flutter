package com.image.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

	public Object checkUser(String userName, String password) {
		if (userName =="sameena" && password == "App@546"){
			return "Login Sucess";
		}else{
			return null;
		}
	}
}
