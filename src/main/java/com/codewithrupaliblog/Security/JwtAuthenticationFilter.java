package com.codewithrupaliblog.Security;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
//	1. get token 

//token aa raha hoga header se  request.getHeader ise hum token ko nikalenge key humari hogi Authorization
//jab token bhejenge to header me authorization key
	String requestToken = request.getHeader("Authorization");
	Enumeration<String> headerNames = request.getHeaderNames();
	



	while(headerNames.hasMoreElements())
	{
		System.out.println(headerNames.nextElement());
	}
	// Bearer 2352523sdgsg

//token kya mila he ye dekhana he t
	System.out.println(requestToken);

	
//token se do chize nikalenge username aur jwtHelper ke pass method he getusername karne ka usaki help se	
	String username = null;

//actual token he o nikalnege
	String token = null;

	
//kya humara requst token null nahi he null he matlab hume token nahi mila requst token start hona chiye
// Bearer se dono condition true he to matlab hume jo token mila he o sab thik he format sahi he par yesa
//nahi he to ek msg bhi dedenge Jwt token does not begin with Bearer
	
//sabkuch sahi he to sabase pahile token ko  featch karnge
	if (requestToken != null && requestToken.startsWith("Bearer")) 
	{
		
		

//Bearer 2352523sdgsg  2 jo he o 7 index par he space ko bhi pakado to 7 se leke sari substring nikalege 
		token = requestToken.substring(7);

		
//token mil jayega to userName bhi nikalenge
		try {
			username = this.jwtTokenHelper.getUsernameFromToken(token);
		} catch (IllegalArgumentException e) {
			System.out.println("Unable to get Jwt token");
		} catch (ExpiredJwtException e) {
			System.out.println("Jwt token has expired");
		} catch (MalformedJwtException e) {
			System.out.println("invalid jwt");

		}

	} else {
		System.out.println("Jwt token does not begin with Bearer");
	}

	
	// once we get the token , now validate
	
//token ko validate karo username me bhi kuch nahi he aur SecurityContext me jo authetication he o bhi null he to
//tab hume jwt token ko validate karana he aur authentication me set karana he

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				//userDetais nikalo pahile
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

				
				//validate karane ke liye userdeatils aur token ye 2 chize chaiye true he matlab token
				//validate he
				//.validateToken is method se token validate karnge
				if (this.jwtTokenHelper.validateToken(token, userDetails)) {
					// shi chal rha hai
					// authentication karna hai


//authentication ka object banane ke liye use karana hoga UsernamePasswordAuthenticationToken banana hoga
//aur isaka object nikalana hoga userDetails, null value, then authorities pass karane hogi
//details set karane ke liye isake pass method he usernamePasswordAuthenticationToken.setdeatils
//buildDetails() method ka use karana padega par ye method WebAuthenticationDetailsSource() isame he to
//isaka object banana hoga aur requst pass karnge to UsernamePasswordAuthenticationToken ready ho jayega
					//ye ek authentication he he jo hum set karenge authentication context me
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

					
					
					//token validate nahi he to Invalid jwt token ye error aayegi
				} else {
					System.out.println("Invalid jwt token");
				}

			} else {
				System.out.println("username is null or context is not null");
			}


//sabkuch sahi pass kiya he to context me set authentication hone ke baad  request aayegi agar token sahi nhi he to else condition chalegi			
//exception aayi to enrtypoint wala commence method chal jayega
			filterChain.doFilter(request, response);
		}




}
