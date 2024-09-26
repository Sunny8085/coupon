package com.service.coupon.dto;

import com.service.coupon.model.CouponType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ApplicableCouponsRequest {
	
	private Long coupon_id;
	@Enumerated(EnumType.STRING)
    private CouponType type;
	private Double discount;
	
}
