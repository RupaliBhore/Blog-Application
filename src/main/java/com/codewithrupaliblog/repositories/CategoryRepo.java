package com.codewithrupaliblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithrupaliblog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
