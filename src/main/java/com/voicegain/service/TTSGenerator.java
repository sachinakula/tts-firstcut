package com.voicegain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicegain.config.VoiceConfig;
import com.voicegain.config.VoiceConfigMapping;
import com.voicegain.errors.PlatformNotFoundException;
import com.voicegain.util.GCPTTSGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

@Service
public class TTSGenerator {

    @Autowired
    private VoiceConfigMapping voiceConfigMapping;

    @Autowired
    private GCPTTSGenerator gcpttsGenerator;

    public void getAllTTSWAVStream(String text, String voiceName, OutputStream outputStream) throws Exception {

        String platform = getPlatformByVoice(voiceName);

        if (platform.equalsIgnoreCase("GCP")) {

            gcpttsGenerator.getTTSWAVStream(text, voiceName, outputStream);

        } else {
            System.out.println("No such platform found !");
            throw new PlatformNotFoundException();
        }
    }


    public void loadConfigFromFile() {
// create Object Mapper
        ObjectMapper mapper = new ObjectMapper();

        // read JSON file and map/convert to java POJO
        try {
            List<VoiceConfig> voiceConfigList = mapper.readValue(new File("/Users/sachinakula/Downloads/tts-firstcut/src/main/resources/voice-config.json"),
                    new TypeReference<List<VoiceConfig>>() {
                    });

            voiceConfigMapping.setVoiceConfigMap(new HashMap<>());

            voiceConfigList.forEach(x -> voiceConfigMapping.getVoiceConfigMap().put(x.getVoice(), x));

            System.out.println(voiceConfigMapping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getPlatformByVoice(String voiceName) {

        if (voiceConfigMapping.getVoiceConfigMap() == null) {
            loadConfigFromFile();
        }

        return voiceConfigMapping.getVoiceConfigMap().get(voiceName).getPlatform();
    }

}
