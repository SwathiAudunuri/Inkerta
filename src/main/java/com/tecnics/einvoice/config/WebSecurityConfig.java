package com.tecnics.einvoice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] pathArray = new String[] { "/test/get", "/test/post", "/test/get/{rollnumber}", "/api/login",
				"/api/partnerInvitation", "/api/partnerInvitation/{reg_id}", "/api/enquiry", "/api/enquiry/{id}",
				"/api/profileActivity", "/api/profileActivity/{regId}", "/api/partnerDetail", "/api/lookup",
				"/api/registrationDetails/{regId}", "/api/refresh_token", "/api/states", "/api/log_error",
				"/api/pull/ack/{status}", "/api/otp/generate", "/api/otp/validate", "/api/isDuplicategstn/{gstn}",
				"/api/isDuplicateemail/{email}","/api/isDuplicatephone/{primaryPhone}","/api/signup","/api/pwdreseturl/{user_id}",
				"/api/lookup/{fieldName}","/api/lookups/{fieldName}/{moduleName}","/api/reciepientMapping","/actuator","/actuator/health","/actuator/info"};
		http.csrf().disable().addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers(pathArray).permitAll().anyRequest().authenticated()

				.and().exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		http.cors();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs/**");
		web.ignoring().antMatchers("/swagger.json");
		web.ignoring().antMatchers("/swagger-ui.html");
		web.ignoring().antMatchers("/swagger-resources/**");
		web.ignoring().antMatchers("/webjars/**");
	}
}