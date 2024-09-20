package com.stage.stageProject.Auth;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stage.stageProject.UserMgmt.User;
import com.stage.stageProject.UserMgmt.UserDetailsMgmt;
import com.stage.stageProject.UserMgmt.UserRepo;

import lombok.extern.slf4j.Slf4j; 
@Service
@Slf4j
public class UserInfoService implements UserDetailsService {
	private final UserRepo repo;
	private final PasswordEncoder encoder;
	private final Logger logger;
	
	public UserInfoService(UserRepo repo, PasswordEncoder encoder) {
		this.repo = repo;
		this.encoder = encoder;
		logger = LoggerFactory.getLogger(UserInfoService.class);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> details = repo.findByName(username);
		UserDetailsMgmt data = details.map(UserDetailsMgmt::new).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
		logger.info("Logged USER=" + data.getUsername() + " PSW=" + encoder.encode(data.getPassword()));
		return data;
	}
	public String addUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		logger.info("Saved USER=" + user);
		return "User saved successfully!";
	}
	
}
