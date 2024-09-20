package com.stage.stageProject.UserMgmt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; 

@SuppressWarnings("serial")
public class UserDetailsMgmt implements UserDetails { 
    private String name; 
    private String password;
    //private List<GrantedAuthority> authorities; 
    
    public UserDetailsMgmt(User user) { 
        name = user.getName(); 
        password = user.getPassword();
    } 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { 
        return null;
    	//return authorities; 
    } 
    @Override
    public String getPassword() { 
        return password; 
    } 
    @Override
    public String getUsername() { 
        return name; 
    } 
    @Override
    public boolean isAccountNonExpired() { 
        return true; 
    } 
    @Override
    public boolean isAccountNonLocked() { 
        return true; 
    } 
    @Override
    public boolean isCredentialsNonExpired() { 
        return true; 
    } 
    @Override
    public boolean isEnabled() { 
        return true; 
    } 
    @Override
    public String toString() {
    	return "\n" + name +
    	"\n" + password;
    }
}
