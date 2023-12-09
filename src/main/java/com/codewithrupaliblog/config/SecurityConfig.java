package com.codewithrupaliblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithrupaliblog.Security.CustomUserDetailService;
import com.codewithrupaliblog.Security.JwtAuthenticationEntryPoint;
import com.codewithrupaliblog.Security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {
	
	//v1 asiye hi liya he ye sare urls public he 
    public static final String[] PUBLIC_URLS = {"/api/v1/auth/**", "/v3/api-docs", "/v2/api-docs",
            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**"

    };
	
	  @Autowired
	    private CustomUserDetailService customUserDetailService;
	  
	  @Autowired
	    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	    @Autowired
	    private JwtAuthenticationFilter jwtAuthenticationFilter;
	

//csrf ko disable kia aur request ko authorized kiya anyRequest ko matlab all request ko authorized kiya
//kist type ka authentication chiye to httpBasic chaiye matalb jo huame sing in form mil raha tha ab o nahi milega basic 
//authentication he to javascript ka o form ab nahi dikhai dega
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http.
	                csrf().disable()
	                .authorizeHttpRequests()
	                .antMatchers(PUBLIC_URLS)
	                .permitAll()
	                .antMatchers(HttpMethod.GET)
	                .permitAll()
	                .anyRequest()
	                .authenticated()
	                .and().exceptionHandling()
	                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
	                .and()
	                .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	         http.authenticationProvider(daoAuthenticationProvider());
	        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

	        return defaultSecurityFilterChain;


	    }
	 
	 
	 
	  @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	  
	  
	  @Bean
	    public DaoAuthenticationProvider daoAuthenticationProvider() {

	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setUserDetailsService(this.customUserDetailService);
	        provider.setPasswordEncoder(passwordEncoder());
	        return provider;

	    }
	  
	  
	  
	  @Bean
	    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }

	  
	  
	  
//CORS error aati he jab aap requst kahi aur se bhej rahe ho aur server kisi aur domain pe ho to
	  //handale karane ke lite CORS ki configuration kari he
	  @Bean
	    public FilterRegistrationBean coresFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

	        CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.setAllowCredentials(true);
	        corsConfiguration.addAllowedOriginPattern("*");
	        corsConfiguration.addAllowedHeader("Authorization");
	        corsConfiguration.addAllowedHeader("Content-Type");
	        corsConfiguration.addAllowedHeader("Accept");
	        corsConfiguration.addAllowedMethod("POST");
	        corsConfiguration.addAllowedMethod("GET");
	        corsConfiguration.addAllowedMethod("DELETE");
	        corsConfiguration.addAllowedMethod("PUT");
	        corsConfiguration.addAllowedMethod("OPTIONS");
	        corsConfiguration.setMaxAge(3600L);

	        source.registerCorsConfiguration("/**", corsConfiguration);

	        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

	        bean.setOrder(-110);

	        return bean;
	    }
	  
	  
	  
	  
	  
	  

}
