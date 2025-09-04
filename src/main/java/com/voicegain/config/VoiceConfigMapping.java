package com.voicegain.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class VoiceConfigMapping {

    private HashMap<String, VoiceConfig> voiceConfigMap;

    public HashMap<String, VoiceConfig> getVoiceConfigMap() {
        return voiceConfigMap;
    }

    public void setVoiceConfigMap(HashMap<String, VoiceConfig> voiceConfigMap) {
        this.voiceConfigMap = voiceConfigMap;
    }
}
