package com.example.userService.security;

import com.example.shared.model.User;
import com.example.userService.model.LoginRequest;
import com.example.userService.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.shared.constants.SharedConstants.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UsersService usersService;
	private Environment environment;
	
	public AuthenticationFilter(UsersService usersService,
                                Environment environment,
                                AuthenticationManager authenticationManager) {
		this.usersService = usersService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}
	
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
  
            LoginRequest creds = new ObjectMapper()
                    .readValue(req.getInputStream(), LoginRequest.class);
            
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
    	String userName = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        User userDetails = usersService.getUserDetailsByEmail(userName);
    	
        String token = Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(environment.getProperty(KEY_TOKEN_EXPIRATION_TIME))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty(KEY_TOKEN_SECRET) )
                .compact();
        
        res.addHeader(TOKEN, token);
        res.addHeader(USER_ID, userDetails.getUserId());

    } 
    
	
}
