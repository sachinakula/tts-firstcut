package com.voicegain.controller;


import com.voicegain.service.TTSGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TTSController {

    @Autowired
    private TTSGenerator ttsGenerator;

    @PostMapping("/v1/convert/wav/stream")
    public void getTTSWAVStream(@RequestParam String text,
                                @RequestParam String voice, HttpServletResponse response) throws Exception {

        System.out.println("text: " + text);
        System.out.println("voice: " + voice);

        response.setContentType("audio/wav");

        ttsGenerator.getAllTTSWAVStream(text, voice, response.getOutputStream());
    }

}
