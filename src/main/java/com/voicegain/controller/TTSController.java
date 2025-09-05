package com.voicegain.controller;


import com.voicegain.service.TTSGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;

@RestController
public class TTSController {

    @Autowired
    private TTSGenerator ttsGenerator;

    @PostMapping("/v1/convert/wav")
    public ResponseEntity<byte[]> getTTSWAV(@RequestParam String text,
                                            @RequestParam String voice) throws Exception {

        System.out.println("text: " + text);
        System.out.println("voice: " + voice);

        byte[] audioContent = ttsGenerator.getAllTTSWAV(text, voice);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("audio/wav")); // Or audio/wav, etc.

        return new ResponseEntity<>(audioContent, headers, HttpStatus.OK);
    }

    @PostMapping("/v1/convert/wav/stream")
    public void getTTSWAVStream(@RequestParam String text,
                                @RequestParam String voice, HttpServletResponse response) throws Exception {

        System.out.println("text: " + text);
        System.out.println("voice: " + voice);

        response.setContentType("audio/wav");

        ttsGenerator.getAllTTSWAVStream(text, voice, response.getOutputStream());
    }

}
