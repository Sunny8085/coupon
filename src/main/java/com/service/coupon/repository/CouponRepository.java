package com.service.coupon.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.coupon.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long>{

	List<Coupon> findByExpirationDateAfter(LocalDate now);

	
}
