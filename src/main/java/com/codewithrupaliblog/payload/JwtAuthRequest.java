package com.codewithrupaliblog.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	//email hi username he
	private String userName;
	
	private String password;

}
