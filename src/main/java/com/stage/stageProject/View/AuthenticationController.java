package com.stage.stageProject.View;

import com.stage.stageProject.Auth.JwtService;
import com.stage.stageProject.Notifications.NotificationServiceImpl;
import com.stage.stageProject.RolesMgmt.*;
import com.stage.stageProject.UserMgmt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

	private		final 		AuthenticationManager 		authenticationManager;
	private		final 		JwtService 					jwtService;
	private		final 		UserServiceImpl 			userService;
	private		final		NotificationServiceImpl 	notificationService;
	private		final 		UserRolesServiceImpl 		userRolesService;
	private		final		Logger						logger;
	private		final		String 						defaultErrorRedirect;

	public AuthenticationController(AuthenticationManager am, JwtService js, UserServiceImpl usi, NotificationServiceImpl nsi, UserRolesServiceImpl ursi) {
		this.authenticationManager = am;
		this.jwtService = js;
		this.userService = usi;
		this.notificationService = nsi;
		this.userRolesService = ursi;
		this.logger = LoggerFactory.getLogger(AuthenticationController.class);
		this.defaultErrorRedirect = "./fail?reason=";
	}

    @RequestMapping("/")
    public String redirectAuth(Model model) {
    	model.addAttribute("success", false);
        return "auth/login";
    }

    @RequestMapping(value="/registration")
    public String register(
    		@RequestParam("name") 	String 		name,
    		@RequestParam("mail") 	String 		mail,
    		@RequestParam("psw") 	String 		psw
	)
	{
    	User u = new User(name, mail,psw);
    	if (userService.createUser(u) == null) {

		} else {

		}
    	return "/auth/login"; 
    } 
    
    
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<?> login(
    		@RequestParam("name") 	String 		name,
    		@RequestParam("psw") 	String 		psw
	)
	{
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(name, psw)
		);
		if (auth.isAuthenticated()) {
			logger.info("User {} successfully logged in with password {}", name, psw);
			String token = jwtService.generateToken(auth.getName());
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setToken(token);
			loginResponse.setExpiration(String.valueOf(jwtService.getJwtExpiration()));
			return ResponseEntity.ok(loginResponse);
		} else return null;
    }

	private HttpHeaders buildHeader(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		return headers;
	}
    
    @GetMapping("/fail")
    public String fail(@RequestParam(value = "reason", defaultValue = "generic") String reason, Model model) {
    	model.addAttribute("reason", reason);
    	return "/auth/fail";
    }
    
    @RequestMapping(value="/logout")
    public String logout() {
    	return "/auth/login";
    }
}