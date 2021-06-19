package com.mystudypeer.mystudypeer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY, reason = "Invalid email or password.")
public class InvalidEmailPasswordException extends RuntimeException {
}
