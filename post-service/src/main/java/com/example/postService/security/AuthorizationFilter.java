package com.example.postService.security;

import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.shared.constants.SharedConstants.*;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    
	Environment environment;

    public AuthorizationFilter(AuthenticationManager authManager, Environment environment) {
        super(authManager);
        this.environment = environment;
    }
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        String authorizationHeader = req
                .getHeader(environment.getProperty(KEY_AUTHORIZATION_TOKEN_HEADER_NAME));

        if (authorizationHeader == null || !authorizationHeader
                .startsWith(environment.getProperty(KEY_AUTHORIZATION_TOKEN_HEADER_PREFIX))) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
       
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }  
    
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String authorizationHeader = req
                .getHeader(environment.getProperty(KEY_AUTHORIZATION_TOKEN_HEADER_NAME));
   
         if (authorizationHeader == null) {
             return null;
         }

         String token = authorizationHeader
                 .replace(environment.getProperty(KEY_AUTHORIZATION_TOKEN_HEADER_PREFIX),
                         "");

         String userId = Jwts.parser()
                 .setSigningKey(environment.getProperty(KEY_TOKEN_SECRET))
                 .parseClaimsJws(token)
                 .getBody()
                 .getSubject();

         if (userId == null) {
             return null;
         }
   
         return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());

     }
}
