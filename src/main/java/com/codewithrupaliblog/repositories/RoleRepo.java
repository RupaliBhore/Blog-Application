package com.codewithrupaliblog.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.codewithrupaliblog.entities.Role;


public interface RoleRepo  extends JpaRepository<Role, Integer> {

}
