package com.example.myweb.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myweb.bean.Greeting;

@Controller
public class GreetingController {
    
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name,Model model) {
    	model.addAttribute("name", name);
    	return "greeting";
    }
    
    @RequestMapping("/greetingJson")
    public @ResponseBody Greeting greetingJson(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
