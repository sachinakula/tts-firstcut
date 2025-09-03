package com.voicegain.controller;


import com.voicegain.service.TTSGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TTSController {

    @Autowired
    private TTSGenerator ttsGenerator;

    @PostMapping("/v1/convert/wav")
    public byte[] getTTSWAV(@RequestParam String text,
                            @RequestParam String voiceName,
                            @RequestParam String platform) throws Exception {

        System.out.println("text: "+text);
        System.out.println("voiceName: "+voiceName);
        System.out.println("platform: "+platform);

        return ttsGenerator.getAllTTSWAV(text, voiceName, platform);
    }

}
