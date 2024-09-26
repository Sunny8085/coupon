package com.service.coupon.dto;

import java.util.List;

import lombok.Data;

@Data
public class CouponRequestDto {
	
	private String type;
	
	private Details details;
	
	@Data
	public static class Details{
		
		private String product_id;
		private String threshold;
        private String discount;
        private List<BuyProduct> buy_products;
        private List<GetProduct> get_products;
        private String repition_limit;
		
	}
	
	@Data
	public static class BuyProduct {
        private String product_id;
        private String quantity;
    }
	
	@Data
    public static class GetProduct {
        private String product_id;
        private String quantity;
    }
	
}
