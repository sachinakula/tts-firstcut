package com.voicegain;

import com.voicegain.service.TTSGenerator;

public class TestMain {

    public static void main(String[] args) {

        TTSGenerator ttsGenerator = new TTSGenerator();

        try {
            ttsGenerator.getAllTTSWAV("Hello", "Test");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
