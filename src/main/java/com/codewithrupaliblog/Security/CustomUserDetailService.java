package com.codewithrupaliblog.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithrupaliblog.entities.User;
import com.codewithrupaliblog.expections.ResourceNotFoundException;
import com.codewithrupaliblog.repositories.UserRepo;

//UserDetailsService ke pass user ki saari infomation hoti he spring boot kabi password chiye to o isise mangega crt+c karke
//aur is class ke baare me pado

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	//loading user from database by userName isake liye userRepositroy chaiye
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// loading user from database by username
		User user = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User ", " email : " + username, 0));

		return user;
		
		
		
	}
	
	
	//jaha jaha CustomUserDetailService spring security use karega to o load karane ke liye  loadUserByUsername is method
	//ko call karega usame humane findByEmail() method lagai he matlab user apne according find karega username agar user nahi
	//nahi mitala to ResourceNotFoundException mil jayegi
	
	
	
	//congig file me bhi btatana hoga ki hume basec authentication chiye saat hi me authentication database se karana he usaki
	//bhi configuration karani hogi authenticationmanagerbuilder() ye method isake andhar configuration karnge auth ka us 
	//karake userdetails pass karnge kis type ka password encoder use kar rahe ho o bhi batao hum use kar rahe he
	//BCrypritPasswordEncoder password jo store hoga bakend me o is encoder me pass hoga

	
	
	
	
	
	
	
	
	
	
	
	
}
