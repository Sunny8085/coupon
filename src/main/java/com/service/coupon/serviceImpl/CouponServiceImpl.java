package com.service.coupon.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.coupon.dto.ApplicableCouponsRequest;
import com.service.coupon.dto.CartItemDTO;
import com.service.coupon.dto.CouponApplyDto;
import com.service.coupon.dto.CouponRequestDto;
import com.service.coupon.exceptionhandler.InvalidException;
import com.service.coupon.exceptionhandler.NotFoundException;
import com.service.coupon.model.Coupon;
import com.service.coupon.model.CouponDetails;
import com.service.coupon.model.CouponType;
import com.service.coupon.model.ProductType;
import com.service.coupon.repository.CouponRepository;
import com.service.coupon.service.CouponService;

import jakarta.transaction.Transactional;

@Service
public class CouponServiceImpl implements CouponService{

	@Autowired private CouponRepository couponRepository;

	@Override
	@Transactional
	public Coupon addNewCoupon(CouponRequestDto coupon) {
		Coupon newCoupon = couponMapper(coupon);
		return couponRepository.save(newCoupon);
	}
	
	@Override
	public List<Coupon> getAllCoupon() {
		return couponRepository.findAll();
	}

	@Override
	public Coupon findCoupon(Long id) {
		return couponRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteCoupon(Long id) {
		couponRepository.findById(id)
						.orElseThrow(() -> new NotFoundException("Coupon not found"));
		couponRepository.deleteById(id);
	}

	@Override
	public Coupon updateCoupon(Coupon coupon) {
		couponRepository.findById(coupon.getId())
						.orElseThrow(() -> new NotFoundException("Coupon not found"));
		return couponRepository.save(coupon);
	}
	
	@Override
	public List<ApplicableCouponsRequest> validCoupon(CartItemDTO items) {
		List<Coupon> coupons = couponRepository.findByExpirationDateAfter(LocalDate.now());
		List<ApplicableCouponsRequest> applicableCoupon = new ArrayList<>();
		if(!coupons.isEmpty()) {
			coupons.forEach(e ->{
				if(e.getExpirationDate().isBefore(LocalDate.now()))
					throw new InvalidException("Coupon expired");
				
				if(e.getType().equals(CouponType.CART_WISE)) {
					ApplicableCouponsRequest applicable = new ApplicableCouponsRequest();
					Double totalPrice = items.getCart().getItems().stream()
		                      .mapToDouble(a -> Double.parseDouble(a.getPrice())).sum();
					Double discount = Double.parseDouble(e.getDetails().get(0).getDiscount());
					Double discountAmount = (totalPrice*(discount / 100));
					applicable.setCoupon_id(e.getId());
					applicable.setDiscount(discountAmount);
					applicable.setType(CouponType.CART_WISE);
					applicableCoupon.add(applicable);
					
				}else if(e.getType().equals(CouponType.BXGY)) {
					ApplicableCouponsRequest applicable = new ApplicableCouponsRequest();
					boolean[] productFound = {false, false, false};
					Double[] discountAmount = {0.0};
					e.getDetails().forEach(p -> {
						for(int i = 0; i < items.getCart().getItems().size(); i++) {
							if(p.getProductId().equals(items.getCart().getItems().get(i).getProduct_id()) &&
								Integer.parseInt(p.getQuantity()) >= Integer.parseInt(items.getCart().getItems().get(i).getQuantity())
								&&	p.getProductType().equals(ProductType.BUY_PRODUCTS) && productFound[0] == false) {
								productFound[0] = true;
							}
							else if(p.getProductId().equals(items.getCart().getItems().get(i).getProduct_id()) &&
									Integer.parseInt(p.getQuantity()) >= Integer.parseInt(items.getCart().getItems().get(i).getQuantity())
									&&	p.getProductType().equals(ProductType.BUY_PRODUCTS) && productFound[1] == false) {
									productFound[1] = true;
							}else if(p.getProductId().equals(items.getCart().getItems().get(i).getProduct_id()) &&
									Integer.parseInt(p.getQuantity()) >= Integer.parseInt(items.getCart().getItems().get(i).getQuantity())
									&&	p.getProductType().equals(ProductType.GET_PRODUCTS) && productFound[2] == false) {
									productFound[2] = true;
									discountAmount[0] = Double.parseDouble(items.getCart().getItems().get(i).getQuantity()) * 
											Double.parseDouble(items.getCart().getItems().get(i).getPrice());
							}
						}
					});
					
					if(productFound[0] == true && productFound[1] == true && productFound[2] == true) {
						applicable.setCoupon_id(e.getId());
						applicable.setType(CouponType.BXGY);
						applicable.setDiscount(discountAmount[0]);
						applicableCoupon.add(applicable);
					}
				}
			});
		}else
			throw new NotFoundException("Coupon not found");
		
		return applicableCoupon;
	}
	
	@Override
	public CouponApplyDto applyCoupon(Long id, CartItemDTO iteams) {
		Coupon coupon = couponRepository.findById(id)
						.orElseThrow(() -> new NotFoundException("Coupon not found"));
		Double totalPrice,totalDiscount,finalPrice;
		CouponApplyDto updated_cart = new CouponApplyDto();
		
		if(coupon.getType().equals(CouponType.CART_WISE)) {
			totalPrice = iteams.getCart().getItems().stream()
						.mapToDouble(e-> Double.parseDouble(e.getPrice())).sum();
			totalDiscount = totalPrice * (Double.parseDouble(coupon.getDetails().get(0).getDiscount()))/100;
			finalPrice = totalPrice - totalDiscount;
			
			updated_cart.setItems(iteams.getCart().getItems());
			updated_cart.setTotal_price(totalPrice.toString());
			updated_cart.setTotal_discount(totalDiscount.toString());
			updated_cart.setFinal_price(finalPrice.toString());
		}else if(coupon.getType().equals(CouponType.BXGY)) {
			totalPrice = iteams.getCart().getItems().stream()
					.mapToDouble(e -> Double.parseDouble(e.getPrice())).sum();
			String productId = coupon.getDetails().stream()
					.filter(e -> e.getProductType().equals(ProductType.GET_PRODUCTS))
					.map(e -> e.getProductId()).collect(Collectors.joining());
			totalDiscount = iteams.getCart().getItems().stream()
					.filter(e -> e.getProduct_id().equals(productId))
					.mapToDouble(e -> Double.parseDouble(e.getPrice())).sum();
			finalPrice = totalPrice - totalDiscount;
			
			updated_cart.setItems(iteams.getCart().getItems());
			updated_cart.setTotal_price(totalPrice.toString());
			updated_cart.setTotal_discount(totalDiscount.toString());
			updated_cart.setFinal_price(finalPrice.toString());
		}else {
			throw new InvalidException("Coupon invalid");
		}
		return updated_cart;
	}
	
	//for dto to coupon class
	private static Coupon couponMapper(CouponRequestDto coupon) {
		Coupon newCoupon = new Coupon();
		List<CouponDetails> couponDetails = new ArrayList<>();
		if(coupon.getType().equals("cart-wise")) {
			newCoupon.setType(CouponType.CART_WISE);
			newCoupon.setExpirationDate(LocalDate.now().plusMonths(1));
			CouponDetails details = new CouponDetails();
			details.setThreshold(coupon.getDetails().getThreshold());
			details.setDiscount(coupon.getDetails().getDiscount());
			details.setProductType(ProductType.GEN_PRODUCTS);
			details.setCoupon(newCoupon);
			couponDetails.add(details);
			
			newCoupon.setDetails(couponDetails);
		}else if(coupon.getType().equals("product-wise")) {
			newCoupon.setType(CouponType.PRODUCT_WISE);
			newCoupon.setExpirationDate(LocalDate.now().plusMonths(1));
			CouponDetails details = new CouponDetails();
			details.setProductId(coupon.getDetails().getProduct_id());
			details.setDiscount(coupon.getDetails().getDiscount());
			details.setProductType(ProductType.GEN_PRODUCTS);
			details.setCoupon(newCoupon);
			couponDetails.add(details);
			
			newCoupon.setDetails(couponDetails);
		}
		else if(coupon.getType().equals("bxgy")) {
			newCoupon.setType(CouponType.BXGY);
			newCoupon.setExpirationDate(LocalDate.now().plusMonths(1));
			newCoupon.setRepetitionLimit(coupon.getDetails().getRepition_limit());
			
			coupon.getDetails().getBuy_products().forEach(e -> {
				CouponDetails details = new CouponDetails();
				details.setProductId(e.getProduct_id());
				details.setQuantity(e.getQuantity());
				details.setProductType(ProductType.BUY_PRODUCTS);
				details.setCoupon(newCoupon);
				couponDetails.add(details);
			});
			coupon.getDetails().getGet_products().forEach(e -> {
				CouponDetails details = new CouponDetails();
				details.setProductId(e.getProduct_id());
				details.setQuantity(e.getQuantity());
				details.setProductType(ProductType.GET_PRODUCTS);
				details.setCoupon(newCoupon);
				couponDetails.add(details);
			});
			
			newCoupon.setDetails(couponDetails);
		}else 
			throw new InvalidException("Coupon invalid");
		return newCoupon;
	}


	
	
}
