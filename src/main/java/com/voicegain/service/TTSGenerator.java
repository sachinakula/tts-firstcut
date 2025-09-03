package com.voicegain.service;

import com.voicegain.errors.PlatformNotFoundException;
import com.voicegain.util.GCPTTSGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TTSGenerator {

    @Autowired
    private GCPTTSGenerator gcpttsGenerator;

//    private GCPTTSGenerator gcpttsGenerator = new GCPTTSGenerator();

    public byte[] getAllTTSWAV(String text, String voiceName, String platform) throws Exception {

        // Configure list of platform options here like GCP, AWS voice11 etc

        if(platform.equalsIgnoreCase("GCP")) {

            return gcpttsGenerator.getTTSWAV(text, voiceName, platform);

        } else {
            System.out.println("No such platform found !");
            throw new PlatformNotFoundException();
        }
    }

}
