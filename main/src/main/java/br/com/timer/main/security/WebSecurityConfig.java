package br.com.timer.main.security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import br.com.timer.security.KeySecurity;
import br.com.timer.security.KeyUtil;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/users/authenticate").antMatchers(HttpMethod.POST, "/users");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	    .authorizeRequests()
	    	.antMatchers(HttpMethod.POST, "/users").permitAll()
	    	.anyRequest().authenticated()
	    .and().csrf().disable()
        	.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
	    	.httpBasic().authenticationEntryPoint(authEntryPoint);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select usr_nm as username, usr_pw as password, 1 as enabled from user where usr_nm=?")
			.passwordEncoder(new PasswordEncoder() {
				@Override
				public String encode(CharSequence rawPassword) {
					return doEncode(rawPassword);
				}

				@Override
				public boolean matches(CharSequence rawPassword, String encodedPassword) {
					return doEncode(rawPassword).equals(encodedPassword);
				}
			})
        	.authoritiesByUsernameQuery("select usr_nm as username, 'USER' from user where usr_nm=?");
		
		// auth.inMemoryAuthentication().withUser("manoel").password("12345").roles("USER");
	}
	
	private String doEncode(CharSequence rawPassword) {
		try {
			return KeyUtil.doHash((String) rawPassword, KeySecurity.KEY);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return rawPassword.toString();
		}
	} 

}
