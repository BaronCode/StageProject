package com.stage.stageProject.Auth;

import java.io.IOException;

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

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private		final		JwtService			jwtService;
	private 	final 		UserDetailsService	userDetailsService;

	private JwtAuthFilter(JwtService js, UserDetailsService uds) {
		jwtService = js;
		userDetailsService = uds;
	}
    
    @Override
    protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		String email = null, token = null;
		System.out.println("authHeader: " + authHeader);

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			System.out.println("Bearer " + authHeader);
			token = authHeader.substring(7);
			email = jwtService.extractUserMail(token);
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			System.out.println("Sono passato di qua");
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			if (jwtService.validateToken(userDetails.getUsername(), token)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

        filterChain.doFilter(request, response); 
    }
}