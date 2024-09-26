package com.service.coupon.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartItemDTO {
	
	private Cart cart;
	
	@Data
    public static class Cart {
		private List<Item> items;
	}
	
	@Data
	public static class Item{
		
		private String product_id;
		private String quantity;
		private String price;
		
	}
	
}
