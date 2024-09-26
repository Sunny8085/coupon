package com.service.coupon.exceptionhandler;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetail {
	
	private String errorDetails;
	private String details;
	private LocalDate timeStamp;
	
}
