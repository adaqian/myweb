package com.example.myweb.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myweb.bean.User;
import com.example.myweb.mapper.UserMapper;

@RequestMapping("/user")
@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/login")
	public String userlogin(@RequestParam(value="username") String username,@RequestParam(value="password") String password,HttpSession session,Model model){
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		session.setAttribute("USER", user);
		model.addAttribute("username", username);
		Map paraMap=new HashMap();
		paraMap.put("user_job_no", username);
		Map map=userMapper.getUserInfo(paraMap);
		if(map==null){
			model.addAttribute("hasErrors", true);
			return "login";
		}
			
		log.info(map.toString());

		/*paraMap.put("username", "test");
		PageHelper.startPage(1, 10);
		List<Map> list=userMapper.getStudentList(paraMap);*/
		return "redirect:/user/home";
	}
	
	@RequestMapping("/logout")
	public String userloginout(HttpSession session){
		session.removeAttribute("USER");
		return "login";
	}
	
	@RequestMapping("/home")
	public String myHome(HttpSession session,Model model){
		User user = (User)session.getAttribute("USER");
        if(user == null)
        {
            return "login";
        }
		model.addAttribute("username", user.getUsername());
		return "userhome";
	}
	
	@RequestMapping("/{username}")
	public String userHome(@PathVariable("username") String username,HttpSession session,Model model){
		User user = (User)session.getAttribute("USER");
        if(user == null)
        {
            return "login";
        }
		model.addAttribute("username", username);
		return "userhome";
	}
}
