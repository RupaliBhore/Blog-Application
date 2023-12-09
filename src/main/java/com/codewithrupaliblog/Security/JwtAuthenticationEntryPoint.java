package com.codewithrupaliblog.Security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

//is class ko as a spring component create karo

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	

//jab koi unauthorized client hamare authroized api ko access karane ki koshish karega to ye method
	//chamlegi hum sirf yaha se unauthroized status de denge koi bandha bina token ke access karane ki
	//koshishi karega to use ye msg Access Denined  jayega
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denined !!");

	}

}
