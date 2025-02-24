package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.DeliveryAddress;


public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer> {
	
	List<DeliveryAddress> findByUsers_UserId(Integer userId);
}
