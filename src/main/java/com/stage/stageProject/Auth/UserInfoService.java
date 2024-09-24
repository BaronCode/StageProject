package com.stage.stageProject.Auth;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private UserRepo repo;
	@Autowired
	private PasswordEncoder encoder;
	private final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> details = repo.findByName(username);
		UserDetailsMgmt data = details.map(UserDetailsMgmt::new).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
		logger.info("Logged USER=" + data.getUsername() + " PSW=" + encoder.encode(data.getPassword()));
		return data;
	}
	public String addUser(User user) {
		user.setPsw(encoder.encode(user.getPsw()));
		repo.save(user);
		logger.info("Saved USER=" + user);
		return "User saved successfully!";
	}
	
}
