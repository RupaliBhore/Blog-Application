package com.codewithrupaliblog.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//autoincrement ke liye
	private Integer categoryId;
	
	
	
	@Column(name="title",length = 100,nullable = false)
	private String categoryTitle;
	
	
	
	@Column(name="description")
	private String categoryDescription;
	
	
	
	
	
	
	//ek category ke andhar bahot sare post ho sahakate he to oneto Many retion create hoga
	//mappedby matalab kis coulm ke saat map karana he
	//casecadeType.ALL matlab parents ko hata denge to usake sare chid bhi hat jaye agar parent ko add kar rahe
	//he to cgild ko alag se save na karana pade o khud save ho jaye
	//parent nikale cgild na nikale to featchtype LAZY karo
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Post> posts=new ArrayList<>();

}
