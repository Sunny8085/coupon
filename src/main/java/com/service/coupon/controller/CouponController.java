package com.service.coupon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.coupon.dto.CartItemDTO;
import com.service.coupon.dto.CouponRequestDto;
import com.service.coupon.model.Coupon;
import com.service.coupon.response.ApiResponse;
import com.service.coupon.service.CouponService;

@RestController
public class CouponController {
	
	@Autowired private CouponService couponService;
	
	@PostMapping("/coupons")
	public ResponseEntity<ApiResponse> addCoupon(@RequestBody CouponRequestDto coupon) {
		Coupon newCoupon = couponService.addNewCoupon(coupon);
		if(newCoupon != null)
			return new ResponseEntity<>(new ApiResponse(true, newCoupon),HttpStatus.CREATED);
		else
			return new ResponseEntity<>(new ApiResponse(false, "Failed to add coupon"),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/coupons")
	public ResponseEntity<ApiResponse> applicableCoupon(){
		List<Coupon> coupons = couponService.getAllCoupon();
		if(coupons != null)
			return new ResponseEntity<>(new ApiResponse(true, coupons),HttpStatus.OK);
		else
			return new ResponseEntity<>(new ApiResponse(false,"Coupon not found"),HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/coupons/{id}")
	public ResponseEntity<ApiResponse> findCoupon(@PathVariable Long id){
		Coupon coupon = couponService.findCoupon(id);
		if(coupon != null)
			return new ResponseEntity<>(new ApiResponse(true, coupon),HttpStatus.OK);
		else
			return new ResponseEntity<>(new ApiResponse(false, "Coupon not found."),HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/coupons/{id}")
	public ResponseEntity<ApiResponse> deleteCoupon(@PathVariable Long id){
		couponService.deleteCoupon(id);
		return new ResponseEntity<>(new ApiResponse(true, "Coupon deleted successfully"),HttpStatus.OK);
	}
	
	@PutMapping("/coupons")
	public ResponseEntity<ApiResponse> updateCoupon(@RequestBody Coupon coupon){
		Coupon upatedCoupon = couponService.updateCoupon(coupon);
		return new ResponseEntity<>(new ApiResponse(true, upatedCoupon),HttpStatus.OK);
	}
	
	@PostMapping("/applicable-coupons")
	public ResponseEntity<ApiResponse> validCoupon(@RequestBody CartItemDTO items){
		return new ResponseEntity<>(new ApiResponse(true,couponService.validCoupon(items)),HttpStatus.OK);
	}
	
	@PostMapping("/apply-coupon/{id}")
	public ResponseEntity<ApiResponse> applyCoupon(@PathVariable Long id, @RequestBody CartItemDTO iteams){
		return new ResponseEntity<>(new ApiResponse(true,couponService.applyCoupon(id, iteams)),HttpStatus.OK);
	}
	
	
}


