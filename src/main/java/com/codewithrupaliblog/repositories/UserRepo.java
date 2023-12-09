package com.codewithrupaliblog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithrupaliblog.entities.User;

//spring boot automatic isaka object banayegi jaha par bhi hum usako autowired karte he us verible me o object
//aa jayega
public interface UserRepo extends JpaRepository<User, Integer>{
	
	//database ke sare operation karane ki facility jpa hume deta he
	
	//email id jo he o user name he
	Optional<User> findByEmail(String email);

}
