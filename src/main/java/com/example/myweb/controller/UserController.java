package com.example.myweb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myweb.bean.User;

@Controller
public class UserController {

	@RequestMapping("/user/login")
	public String userlogin(@RequestParam(value="username") String username,@RequestParam(value="password") String password,HttpSession session,Model model){
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		session.setAttribute("USER", user);
		model.addAttribute("username", username);
		return "userhome";
	}
	
	@RequestMapping("/user/logout")
	public String userloginout(HttpSession session){
		session.removeAttribute("USER");
		return "login";
	}
	
	@RequestMapping("/user/home")
	public String userHome(@RequestParam(value="username") String username,Model model){
		model.addAttribute("username", username);
		return "userhome";
	}
}
