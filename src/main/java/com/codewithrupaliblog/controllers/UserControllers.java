package com.codewithrupaliblog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithrupaliblog.payload.ApiResponse;
import com.codewithrupaliblog.payload.UserDto;
import com.codewithrupaliblog.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserControllers {
	
	//alag alag url ko handle karane ke liye alag alag methods banan ne honge post put delete get
	
	@Autowired
	private UserService userService;
	
	//Dto humane isaliye banaye taki hum direct user yaha se expose na kar paye entity se use expose na kar paye koi
	//kunki entity me bahot sare  chize ho sakati he to
	//validation ko work karne ke liye use enable karana padega to @Valid annotaion lagao
	// POST-create user
		@PostMapping("/")
		public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
			
			//user create karo
			UserDto createUserDto = this.userService.createUser(userDto);
			
			//response me hamara jo user create huva he o jayega or status created jayega
			return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
		}
		
		
		// PUT- update user

		@PutMapping("/{userId}")
		public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) 
		{
			//userService ka update method call karo use userid deke
			UserDto updatedUser = this.userService.updateUser(userDto, uid);
			return ResponseEntity.ok(updatedUser);
		}
		
		
//is api ko sirf admin hi acess kar sakata he matlab sirf admin delete karega		
		//apiResponse ki class banai
		//ADMIN
		// DELETE -delete user
		@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("/{userId}")
		public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) 
		{
			this.userService.deleteUser(uid);
			return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
		}

		// GET - user get all users
		@GetMapping("/")
		public ResponseEntity<List<UserDto>> getAllUsers() {
			return ResponseEntity.ok(this.userService.getAllUsers());
		}
		
		
		

		// GET - user get single user
		@GetMapping("/{userId}")
		public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
			return ResponseEntity.ok(this.userService.getUserById(userId));
		}
	

}
