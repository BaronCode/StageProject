package com.stage.stageProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Mapper {
	@RequestMapping("/hello")
	public String hello() { return "redirect:/index.html"; }
	@RequestMapping("/login")
	public String login() { return "/auth/login"; }
	@RequestMapping("/registration")
	public String registration() { return "/auth/registration"; }
}
