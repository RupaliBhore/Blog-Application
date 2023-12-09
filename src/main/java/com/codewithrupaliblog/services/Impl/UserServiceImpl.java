package com.codewithrupaliblog.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithrupaliblog.config.AppConstants;
import com.codewithrupaliblog.entities.Role;
import com.codewithrupaliblog.entities.User;
import com.codewithrupaliblog.expections.ResourceNotFoundException;
import com.codewithrupaliblog.payload.UserDto;
import com.codewithrupaliblog.repositories.RoleRepo;
import com.codewithrupaliblog.repositories.UserRepo;
import com.codewithrupaliblog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	//user ki information ke liye user repository chiye
	//autowired nahi karoge to null expetion aayegi user create hi nahi hoga usame o sari details jayegi nahi
	//user save bhe nahi hoga
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private  ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	//CREATE USER

	@Override
	public UserDto createUser(UserDto userDto) {
	
		User user = this.dtoToUser(userDto);
		
		//jo user save huva he o milega
		User savedUser = this.userRepo.save(user);
		
		//user ko convert karenge dto me conversion humane  method banake kiya he dtoToUser() 
		return this.userToDto(savedUser);
	}

	
	
	
	
	//UPDATE USER
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		//jo id ayi he use feacth karte he pahile get kiya id nahi milti to resorcenot found exception throw hogi
		//aisa user dete ho jo database me nahi he to ResourceNotFoundException aayegi
		User user = this.userRepo.findById(userId)
		.orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));

		       //user ko set kiya
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
          
        //user ko update kiya
       User updatedUser = this.userRepo.save(user);
       UserDto userDto1 = this.userToDto(updatedUser);
       return userDto1;
	  }
	
	
	
	
         //GET USER BY ID
	@Override
	public UserDto getUserById(Integer userId) {
		
		//aisa user dete ho jo database me nahi he to ResourceNotFoundException aayegi
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", " Id ", userId));

		return this.userToDto(user);
	
	}

	
	            //GET ALL USER
	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();
		
		//user ko convert karenge as a dto map us kro ek ek user mil jayaga
		//lambda Stream api humane use kiyi he list of user ko list of userDtos me convert karane ke liye
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		//pahile user ko get karo
		//aisa user dete ho jo database me nahi he to ResourceNotFoundException aayeg
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		//user mila to use delete karo
		this.userRepo.delete(user);
		
	}
	
	public User dtoToUser(UserDto userDto) {
		
		//map me 2 aru pass karo kis source ko convert karana he aur kis object me convaert karana he
		//hume userDto ko user class me convert karana he  to ye automatic conversion karke dega apako
		User user = this.modelMapper.map(userDto, User.class);

//		 User user=new User();
//		 user.setId(userDto.getId());
//		 user.setName(userDto.getName());
//		 user.setEmail(userDto.getEmail());
//		 user.setAbout(userDto.getAbout());
//		 user.setPassword(userDto.getPassword());
		
		return user;
	}

	public UserDto userToDto(User user) {
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
//		//pahile userDto ka object banao
//		UserDto userDto=new UserDto();
//		//set karu user ko
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//	
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
	}


//new user ko register karenge	
	@Override
	public UserDto registerNewUser(UserDto userDto) {

		//user ko change karane userDto class me
		User user = this.modelMapper.map(userDto, User.class);

//user ka passwod encorder karo
		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

//user ka roale jo he o use dedo api ke throgh kio user regisater ho raha he to o normal user assing karnege
		// roles role ko nikalne ke kliye use karte he
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		//user ko roale add karnge
		user.getRoles().add(role);

		//save karenge
		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}
	
	





}
