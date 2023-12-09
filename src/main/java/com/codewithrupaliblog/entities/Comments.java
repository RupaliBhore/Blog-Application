package com.codewithrupaliblog.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//matlab comment kya hoga
	private String content;

	
	//comment kis post ka he ek post ke saat bahot sare comments ho skate he to ManyToMany relation banalo
	@ManyToOne
	private Post post;

}
