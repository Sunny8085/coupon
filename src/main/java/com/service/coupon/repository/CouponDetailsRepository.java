package com.service.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.coupon.model.CouponDetails;

public interface CouponDetailsRepository extends JpaRepository<CouponDetails, Long>{
	
	
}
