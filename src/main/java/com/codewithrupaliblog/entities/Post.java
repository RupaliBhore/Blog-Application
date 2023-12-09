package com.codewithrupaliblog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.stream.events.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;

	@Column(name = "post_title", length = 100, nullable = false)
	private String title;

	@Column(length = 1000000000)
	private String content;

	private String imageName;

	private Date addedDate;

	//ek category ke andhar multipale post he to to ManyToOne relation tyara hoga post entity me 
	//kis category me ye post add huva he
	//jab post add hoga to ye post kis user ka he aur kis category ka he ralation banalo
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	
	//kis user ne is post ko add kiya
	@ManyToOne
	private User user;
	
	
	//ek post ke pass multiplae comment ho sakti he to yaha OneToMany retion banega
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private Set<Comments> comments=new HashSet<>();

}
