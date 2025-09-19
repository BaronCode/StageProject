package com.stage.stageProject.Auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stage.stageProject.UserMgmt.User;
import com.stage.stageProject.UserMgmt.UserRepo;

import lombok.extern.slf4j.Slf4j; 
@Service
@Slf4j
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	private 	final		UserRepo		repo;

	public UserDetailsService(UserRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User u = repo.findByEmail(email);
		return org.springframework.security.core.userdetails.User.
				withUsername(u.getEmail())
				.password(u.getPassword())
				.build();
	}
}
