package com.service.coupon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name ="coupon_details")
public class CouponDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "details_id")
	private Long detailsId;
	
	private String productId;
	
	private String threshold;
	
	@Enumerated(EnumType.STRING)
    private ProductType productType;
	
	private String quantity;
	
	private String discount;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name ="coupon_id", updatable = false)
	private Coupon coupon;
	
}



