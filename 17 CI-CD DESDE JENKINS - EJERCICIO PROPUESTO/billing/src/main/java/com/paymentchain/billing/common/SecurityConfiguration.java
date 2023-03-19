/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.common;

import java.time.Duration;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 *
 * @author sotobotero
 * 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

       private static final String[] AUTH_LIST = { //
                        "/v2/api-docs", //
                        "/configuration/ui", //
                        "/swagger-resources", //
                        "/configuration/security", //
                        "/swagger-ui.html", //
                        "/webjars/**" //
        };
       
     //Authentication method
     @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
                        .withUser("api-user")
                        .password(passwordEncoder().encode("password"))
                        .roles("USER")
                        .and()
			  .withUser("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles("USER", "ADMIN");
	}
        
        //Authorization
        @Override
        protected void configure(HttpSecurity http) throws Exception {
              http
                 .cors(withDefaults())                  
                 .csrf().disable().authorizeRequests()                   
                        .and().authorizeRequests()
                         .antMatchers(AUTH_LIST).authenticated() 
                        //.antMatchers(HttpMethod.POST).authenticated()
                        .antMatchers("/billing/**").authenticated()
                        .and()                        
                        .httpBasic();    
                  }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
        
         @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowedHeaders(Arrays.asList("Origin,Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers","Authorization"));
                cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));                
		cc.setAllowedOrigins(Arrays.asList("/*"));
		cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT","PATCH"));
                cc.addAllowedOrigin("*");
                cc.setMaxAge(Duration.ZERO);
                cc.setAllowCredentials(Boolean.TRUE);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", cc);
		return source;
	}
  
}
