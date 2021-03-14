package com.example.userService.security;

import com.example.userService.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.shared.constants.SharedConstants.KEY_API_LOGIN_URL_PATH;
import static com.example.shared.constants.SharedConstants.KEY_API_REGISTRATION_URL_PATH;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private final Environment environment;
	private final UsersService usersService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public WebSecurity(final Environment environment, final UsersService usersService,
					   final BCryptPasswordEncoder bCryptPasswordEncoder)
	{
		this.environment = environment;
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, environment.getProperty(KEY_API_REGISTRATION_URL_PATH)).permitAll()
				.antMatchers(HttpMethod.POST, environment.getProperty(KEY_API_LOGIN_URL_PATH)).permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(getAuthenticationFilter())
				.addFilter(new AuthorizationFilter(authenticationManager(), environment));

		http.headers().frameOptions().disable();

	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception
	{
		AuthenticationFilter authenticationFilter =
				new AuthenticationFilter(usersService, environment, authenticationManager());
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("api.login.url.path"));
		return authenticationFilter;
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
    }

}
