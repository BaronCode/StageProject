package com.stage.stageProject.Auth;

import java.io.IOException;

import com.stage.stageProject.UserMgmt.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter { 
    @Autowired
	private JwtService jwtService;
    @Autowired
    private UserInfoService userDetailsService; 
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {try {
			Collections.list(request.getHeaderNames()).forEach(System.out::println);
			String authHeader = request.getHeader("Authorization");
		    String token = null; 
		    String username = null;
		    if (authHeader != null && authHeader.startsWith("Bearer ")) {
		        token = authHeader.substring(7);
		        username = jwtService.extractUsername(token); 
		    } 
		    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		    	UserDetails userDetails = userDetailsService.loadUserByUsername(username); 
		        if (jwtService.validateToken(token)) { 
		            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
		            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
		            SecurityContextHolder.getContext().setAuthentication(authToken); 
		        } 
		    } 
        } catch (ExpiredJwtException e) {
        	String isRefreshToken = request.getHeader("isRefreshToken");
			String requestURL = request.getRequestURL().toString();
			// allow for Refresh Token creation if following conditions are true.
			if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
				allowForRefreshToken(e, request);
			} else request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response); 
    } 
    
    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
    	System.out.println("Allowed");
		// create a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		// Set the claims so that in controller we will be using it to create
		// new JWT
		request.setAttribute("claims", ex.getClaims());

	}
}