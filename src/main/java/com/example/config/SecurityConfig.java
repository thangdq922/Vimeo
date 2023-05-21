package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.security.CustomSuccessHandler;
import com.example.service.CusTomDetailService;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
	
	@Autowired
	CustomSuccessHandler successHandler;

	@Autowired
	CusTomDetailService cusTomDetailService;

    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http .csrf(crsf -> crsf.disable())        		
                .authorizeHttpRequests(
                        auth -> auth .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                                .anyRequest().permitAll())               
                .formLogin(formLogin ->
                        formLogin.loginPage("/login").loginProcessingUrl("/login").successHandler(successHandler).failureUrl("/login?error=true") .permitAll())
                .logout(logout ->
                        logout.logoutUrl("/logout").deleteCookies("JSESSIONID").logoutSuccessUrl("/"));
		return http.build();
	}
	
	@Bean
	 AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(cusTomDetailService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
}
