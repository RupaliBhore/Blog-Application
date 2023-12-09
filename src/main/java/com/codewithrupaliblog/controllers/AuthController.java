package com.codewithrupaliblog.controllers;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithrupaliblog.Security.JwtTokenHelper;
import com.codewithrupaliblog.entities.User;
import com.codewithrupaliblog.expections.ApiException;
import com.codewithrupaliblog.payload.JwtAuthRequest;
import com.codewithrupaliblog.payload.JwtAuthResponse;
import com.codewithrupaliblog.payload.UserDto;
import com.codewithrupaliblog.repositories.UserRepo;
import com.codewithrupaliblog.services.UserService;





@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	
	
//CREATE TOKEN	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUserName(), request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		
		//response.setUser(this.mapper.map((User) userDetails, UserDto.class));
		
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}


//autheticate karenge authetication manager se	
	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new ApiException("Invalid username or password !!");
		}

	}

	// register new user api

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) 
	{
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}

	// get loggedin user data
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/current-user/")
	public ResponseEntity<UserDto> getUser(Principal principal) {
		User user = this.userRepo.findByEmail(principal.getName()).get();
		return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
	}

}
