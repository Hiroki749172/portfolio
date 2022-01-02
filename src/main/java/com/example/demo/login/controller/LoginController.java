package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	//ログイン画面のGET用コントローラー
	@GetMapping("/login")
	public String getLogin(Model model) {
		//login.htmlに画面遷移
		return "login/login";
	}
	
	@GetMapping("/adminlogin")
	public String getAdmin(Model model) {
		return "login/adminlogin";
	}
	
	//ログイン画面のPOST用コントローラー
	@PostMapping("/login")
	public String postLogin(Model model) {
		//login.htmlに画面遷移
//		return "login/login";
		return "redirect:/home";
	}
	
	@PostMapping("/adminlogin")
	public String postAdmin(Model model) {
		return "redirect:/home";
	}
}



//localhost:8080/loginにGETメソッド、POSTメソッドでHTTPリクエストが来たら、login配下のlogin.htmlに遷移する
