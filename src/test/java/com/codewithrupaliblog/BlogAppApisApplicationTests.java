package com.codewithrupaliblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codewithrupaliblog.repositories.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	
	
	@Test
	void contextLoads() {
	}
	
	//kisi bhi interface ka class aur pacakge aap yese nikal sakate ho
	@Test
	public void repoTest()
	{
		String className=this.userRepo.getClass().getName();
		String packageName= this.userRepo.getClass().getPackageName();
		System.out.println("className: " +className);
		System.out.println("pakageName :" +packageName);
		
		//right click karo aur run karo run as jUnitTest
	}

}
