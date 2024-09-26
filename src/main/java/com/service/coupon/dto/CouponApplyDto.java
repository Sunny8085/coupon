package com.service.coupon.dto;

import java.util.List;

import com.service.coupon.dto.CartItemDTO.Item;

import lombok.Data;

@Data
public class CouponApplyDto {
	
	private List<Item> items;
	
	private String total_price;
	private String total_discount;
	private String final_price;
	
}
