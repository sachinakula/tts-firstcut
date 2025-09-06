package com.voicegain.errors;

/**
 * If the given voice id is not found in the config, this error is thrown.
 */
public class VoiceNotFoundException extends RuntimeException {

    public VoiceNotFoundException() {
        super("No such voice found.");
    }
}
