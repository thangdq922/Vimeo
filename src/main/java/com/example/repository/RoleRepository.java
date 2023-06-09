package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	public RoleEntity findByCode(String Code) ;
	

}
