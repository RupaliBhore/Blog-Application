package com.codewithrupaliblog.expections;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	
	
	//Custom excepton banate he

	String resourceName;
	String fieldName;
	long fieldValue;

	       //create constructor
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) 
	{
		//super me hum msg dete he format method ka use karke
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
