package com.codewithrupaliblog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithrupaliblog.config.AppConstants;
import com.codewithrupaliblog.entities.Role;
import com.codewithrupaliblog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
		System.out.println("Strat the project....");
	}
	
	
	
	
	
	
	
	
	//bean annotaton lagao ge to spring isaka object automatic banayega aur provide karega jaha par bhi hum isko
	//autowired karenge
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	
	
	
	@Override
	public void run(String... args) throws Exception {

		System.out.println(this.passwordEncoder.encode("xyz"));

		try {

			//admin ka set karo role
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");

			
			//database me save karlo roles ki list milegi
			List<Role> roles = List.of(role, role1);

			List<Role> result = this.roleRepo.saveAll(roles);

			//result me dekho kon kon se role ko usane save kiya he
			result.forEach(r -> {
				System.out.println(r.getName());
			});
			
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
