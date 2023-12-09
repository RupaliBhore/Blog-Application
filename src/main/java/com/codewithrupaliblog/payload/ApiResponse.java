package com.codewithrupaliblog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	
	//Response dete samay app kya kya dena chahate ho o likho yaha pe kio message dena chahhe ho kya status code
	//dena chahte ho ye sab likho
	
	private String message;
	private boolean success;

}
