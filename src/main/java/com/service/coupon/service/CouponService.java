package com.service.coupon.service;

import java.util.List;

import com.service.coupon.dto.ApplicableCouponsRequest;
import com.service.coupon.dto.CartItemDTO;
import com.service.coupon.dto.CouponApplyDto;
import com.service.coupon.dto.CouponRequestDto;
import com.service.coupon.model.Coupon;

public interface CouponService {

	Coupon addNewCoupon(CouponRequestDto coupon);

	List<Coupon> getAllCoupon();

	Coupon findCoupon(Long id);

	void deleteCoupon(Long id);

	Coupon updateCoupon(Coupon coupon);

	List<ApplicableCouponsRequest> validCoupon(CartItemDTO items);

	CouponApplyDto applyCoupon(Long id, CartItemDTO iteams);
	
	
	
}
