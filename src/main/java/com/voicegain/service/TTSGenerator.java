package com.voicegain.service;

import com.voicegain.util.GCPTTSGenerator;
import org.springframework.stereotype.Service;

@Service
public class TTSGenerator {

    private GCPTTSGenerator gcpttsGenerator = new GCPTTSGenerator();

    public void getAllTTSWAV(String text, String voiceName) throws Exception {

        // Configure list of platform options here like GCP, AWS voice11 etc


        gcpttsGenerator.getTTSWAV(text, voiceName);


    }

}
