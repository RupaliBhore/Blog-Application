package com.codewithrupaliblog.services;

import java.util.List;


import com.codewithrupaliblog.payload.UserDto;

public interface UserService {
	
	//data ko transfer karane ke liye Dto ka use karenge
	  
	
	//create a user
	UserDto createUser(UserDto userDto);
	

	//user ko update karo usaki id se
	UserDto updateUser(UserDto user, Integer userId);
	
	
	//get single user by id
	UserDto getUserById(Integer userId);
	
	
	
	//sare user ko get karana he to
	List<UserDto> getAllUsers();
	
	
	
	void deleteUser(Integer userId);
	
	
	UserDto registerNewUser(UserDto user);
	
	
	//koi bhi api banani ho to userserivce me aake method banalo then usaki implemention  impl class me karo

}
