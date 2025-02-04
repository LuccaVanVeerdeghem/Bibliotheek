package com.springBoot.bibliotheek;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
    DataSource dataSource;
	
	
		@Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder());
	    }
	    
	    @Bean
	    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	    	http.csrf().and().
	    	authorizeHttpRequests(requests -> 
	    		requests
	    				.requestMatchers("").permitAll()
	    				.requestMatchers("/").permitAll()
	    				.requestMatchers("/error").permitAll()
	    				.requestMatchers("/bib").permitAll()
	    				.requestMatchers("/bib?added").authenticated()
	    				.requestMatchers("/bib?removed").authenticated()
	    				.requestMatchers("/populair**").permitAll()
	    				.requestMatchers("/boek/*").permitAll()
	    				.requestMatchers(HttpMethod.POST, "/boek/**	").hasRole("USER")
	    				.requestMatchers("/login").permitAll()
	    				.requestMatchers("/css/**").permitAll()	
	    				.requestMatchers("/addBoek*").hasRole("ADMIN")
	    				.requestMatchers(HttpMethod.POST, "/addBoek").hasRole("ADMIN")
	    				.requestMatchers("/rest/boek/*").permitAll()
	    				.requestMatchers("/rest/auteur*").permitAll()
	    				.requestMatchers("/403**").permitAll()).
	    	
	    	
	    	formLogin(form -> 
	    		form.defaultSuccessUrl("/bib", true)
	                	.loginPage("/login")
	                	
	                	
	                	
	    	).exceptionHandling().accessDeniedPage("/403");;
	    	
	    	return http.build();
	    	        
	    }
}
