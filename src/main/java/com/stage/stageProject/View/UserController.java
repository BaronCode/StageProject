package com.stage.stageProject.View;

import java.time.LocalDateTime;

import com.stage.stageProject.Notifications.NotificationServiceImpl;
import com.stage.stageProject.RolesMgmt.UserRolesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.stage.stageProject.Auth.AuthData;
import com.stage.stageProject.Auth.JwtService;
import com.stage.stageProject.Auth.UserInfoService;
import com.stage.stageProject.Notifications.Notification;
import com.stage.stageProject.Notifications.NotificationService;
import com.stage.stageProject.RolesMgmt.ROLES;
import com.stage.stageProject.RolesMgmt.UserRoles;
import com.stage.stageProject.RolesMgmt.UserRolesRepo;
import com.stage.stageProject.UserMgmt.User;
import com.stage.stageProject.UserMgmt.UserRepo;
import com.stage.stageProject.UserMgmt.UserToken;

import jakarta.ws.rs.FormParam;
/**
 * Controller class is where all the user requests are handled and required/appropriate
 * responses are sent
 */import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@Controller
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	private UserInfoService service;
	@Autowired
	private NotificationServiceImpl notiService;
	@Autowired
    private JwtService jwtService; 
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private AuthenticationManager authenticationManager; 
    @Autowired
    private UserRolesServiceImpl userRolesService;
    private String default_error = "/auth/fail?reason=";

    @RequestMapping(path="/")
    public String redirectAuth(Model model) {
    	model.addAttribute("success", false);
        return "/auth/login";
    }
    
    @RequestMapping("/registration")
    public String addNewUser(
    		@RequestParam("name") String name,
    		@RequestParam("mail") String mail,
    		@RequestParam("psw") String psw,
    		Model model
    		) {
    	User u = new User(name, mail,psw);
    	userRolesService.saveRoles(new UserRoles(name, ROLES.USER));
    	notiService.saveNotification(new Notification(notiService.findMaxId()+1, "User creation", "Created " + u.notificationToString(), LocalDateTime.now(), false));
    	
    	String response = service.addUser(u); 
    	ResponseEntity.status(HttpStatus.CREATED).body(response);
    	
    	model.addAttribute("register", u);
    	model.addAttribute("success", true);
    	return "/auth/login"; 
    } 
    
    
    @CrossOrigin(origins = "http://localhost:8084/auth/")
    @RequestMapping("/login")
    public ResponseEntity<Void> login(
    		@RequestParam("name") String name,
    		@RequestParam("psw") String psw) {
        logger.info("Tried to log in with USER={} PSW={}", name, psw);
    	Authentication authentication = null;
    	try {
    		authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, psw));
    	} catch (AuthenticationException ae) {
    		logger.error("An error occurred while authenticating with USER=" + name + " PSW=" + psw);
    		String reason = ae.getMessage();
    		reason = reason.replace(" ", "_").toLowerCase().concat("_error");
    		logger.error(reason);
    		//return new RedirectView(default_error + reason);
    	}
    	if (authentication.isAuthenticated()) {
        	String token = jwtService.generateToken(name);
        	UserToken.addToken(new AuthData(name, psw), token);
            logger.info("Logged USER={} PSW={}", name, psw);
        	return ResponseEntity.ok().headers(buildHeader(token)).body(null);
			//return new RedirectView("../data/");
        } else return null; //return new RedirectView(default_error + "auth_error");
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
        /*logger.info("Logged out user {}", jwtService.extractUsername(token));
    	UserToken.invalidateToken(token);*/
    	return "/auth/login";
    }
}