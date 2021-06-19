package com.mystudypeer.mystudypeer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY, reason = "User not found.")
public class UserNotFoundException extends RuntimeException {
}
