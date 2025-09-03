package com.voicegain.controller;


import com.voicegain.service.TTSGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/tts")
public class TTSController {

    private TTSGenerator ttsGenerator;

    @PostMapping("/v1/convert/wav")
    public void getTTSWAV(String text, String voiceName) throws Exception {

        System.out.println("Check");

        ttsGenerator.getAllTTSWAV(text, voiceName);

        return;
    }
}
