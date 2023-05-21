package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.UserEntity;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	@Transactional(value = TxType.REQUIRES_NEW)
	 UserEntity findByUserName(String username);
	 
	 List<UserEntity> findByFullNameContaining(String fullName);
}
