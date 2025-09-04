package com.voicegain;

import com.voicegain.errors.PlatformNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GEH {

    @ExceptionHandler(PlatformNotFoundException.class)
    public ResponseEntity handlePlatformNotfound(PlatformNotFoundException platformNotFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such Platform Found");
    }
}
