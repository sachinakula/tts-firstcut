package com.voicegain;

import com.voicegain.errors.PlatformNotFoundException;
import com.voicegain.errors.VoiceNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@ControllerAdvice
public class GEH {

    @ExceptionHandler(PlatformNotFoundException.class)
    public ResponseEntity handlePlatformNotfound(PlatformNotFoundException platformNotFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such Platform supported");
    }

    @ExceptionHandler(VoiceNotFoundException.class)
    public void handlePlatformNotfound(VoiceNotFoundException voiceNotFoundException, HttpServletResponse response) throws IOException {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-type", "application/json");

        response.setContentType("application/json");
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        OutputStream out = response.getOutputStream();
        out.write(voiceNotFoundException.getMessage().getBytes(StandardCharsets.UTF_8));
        out.flush(); // Ensure all buffered data is sent to the client
    }

}
