package com.service.coupon.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidException(String message) {
		super(message);
	}
}
