package com.voicegain.errors;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Platform")  // 404
public class PlatformNotFoundException extends RuntimeException {

}
