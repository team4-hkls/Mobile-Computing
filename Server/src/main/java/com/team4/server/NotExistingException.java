package com.team4.server;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not exist requested id")
public class NotExistingException extends RuntimeException  {
	
	public NotExistingException(String message) {
		super(message);
	}
}
