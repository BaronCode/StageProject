package com.stage.stageProject.Security;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.AuthenticationProvider; 
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; 
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; 
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.stage.stageProject.Auth.JwtAuthFilter;
import com.stage.stageProject.Auth.UserInfoService;
import com.stage.stageProject.UserMgmt.UserRepo;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Config { 
    private final JwtAuthFilter authFilter; 
    public Config(JwtAuthFilter authFilter) { 
        this.authFilter = authFilter; 
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo repo, PasswordEncoder encoder) { 
        return new UserInfoService(repo,encoder); 
    } 
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception { 
        return http
            .authorizeHttpRequests(auth -> {
            	auth.requestMatchers("/", "/**", "/auth/**", "/auth/").permitAll();
            	auth.requestMatchers("/hello", "/data/dashboard").authenticated();
                /*auth.requestMatchers("/auth/hello", "/auth/hi").authenticated()
                auth.requestMatchers("/auth/workingpage").hasRole("ABCS")
            */})
            /*.formLogin(login -> login.loginPage("/auth/login.html")
            		.loginProcessingUrl("/auth/login")
            		.defaultSuccessUrl("/hello", true)
            		.failureForwardUrl("/auth/registration.html")
            )*/
            .httpBasic(withDefaults()).csrf((csrf) -> csrf.disable())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
        return config.getAuthenticationManager(); 
    } 
}
