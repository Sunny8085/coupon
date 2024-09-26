package com.service.coupon.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "coupons")
public class Coupon {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Enumerated(EnumType.STRING)
    private CouponType type;
	
	@CreationTimestamp
	@Column(name="created_at",updatable = false)
	private LocalDateTime createdAt;
	
	private LocalDate expirationDate;
	
	private String repetitionLimit;
	
	@OneToMany(mappedBy = "coupon", cascade = {CascadeType.PERSIST, CascadeType.ALL}, fetch = FetchType.EAGER)
	private List<CouponDetails> details;
	
}




