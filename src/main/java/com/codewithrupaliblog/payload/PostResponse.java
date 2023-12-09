package com.codewithrupaliblog.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

	//jo response hum client ko de rahe he usame ye saari information hogi
	//page ke bare me saari information milegi
	
	//page ka content
	private List<PostDto> content;
	
	//page number kya he
	private int pageNumber;
	
	//page ka size kya he
	private int pageSize;
	
	//us page pe total records kitane he
	private long totalElements;
	
	//total kitane page he
	private int totalPages;	
	
	//kya ye last page he agar iasaki value true ho gayi to hum last page pe he
	private boolean lastPage;
}
