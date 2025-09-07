package com.voicegain.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import com.voicegain.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;


@Service
public class GCPTTSGenerator {

    private static final Logger log = LoggerFactory.getLogger(GCPTTSGenerator.class);

    @Autowired
    private CacheService cacheService;

    public void getTTSWAVStream(String text, String voiceName, OutputStream outputStream) throws Exception {

        // Test
        runStreamingTtsQuickstartStream(text, voiceName, outputStream);
    }

    public void runStreamingTtsQuickstartStream(String inputText, String voiceName, OutputStream outputStream) throws Exception {

        String cacheKey = inputText.toLowerCase() + voiceName.toLowerCase();

        if (cacheService.get(cacheKey) != null) {
            outputStream.write(cacheService.get(cacheKey).toByteArray());
            outputStream.flush();
            return;
        }

        byte[] audioContent = null;

        // Path to your service account JSON key
        String jsonPath = "/Users/sachinakula/Downloads/tts-firstcut/src/main/resources/ascalon-dev-705e770fdfbc.json";

        // Load credentials
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));

        // Attach credentials to client settings
        TextToSpeechSettings settings = TextToSpeechSettings.newBuilder().setCredentialsProvider(() -> credentials).build();

        log.info("Calling GCP TTS API for: {}", inputText);

        try (TextToSpeechClient client = TextToSpeechClient.create(settings)) {

            // Configure the voice
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder().setName(voiceName).setLanguageCode("en-US").build();

            // Configure the streaming request
            StreamingSynthesizeConfig streamingConfig = StreamingSynthesizeConfig.newBuilder().setVoice(voice).build();

            // First request: contains only the config
            StreamingSynthesizeRequest configRequest = StreamingSynthesizeRequest.newBuilder().setStreamingConfig(streamingConfig).build();

            AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

            String[] textChunks = new String[1];
            textChunks[0] = inputText;

            for (String text : textChunks) {
                SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

                OutputStream myOutputStream;

                try (ByteArrayOutputStream copyStream = new ByteArrayOutputStream()) {

                    // Create a custom OutputStream that also writes to the copyStream
                    myOutputStream = new CustomTeeOutputStream(outputStream, copyStream);

                    client.synthesizeSpeech(input, voice, audioConfig).writeTo(myOutputStream);

                    myOutputStream.flush();

                    cacheService.put(cacheKey, copyStream);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void writeToFile(byte[] bytes, String pathname, FileOutputStream fos) {

        try {
            fos.write(bytes);
            //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
        } catch (FileNotFoundException e) {

            System.out.println("FileNotFoundException");
            throw new RuntimeException(e);
        } catch (IOException e) {

            System.out.println("IOException");
            throw new RuntimeException(e);
        }
    }

    /**
     * Todo: Not used remove
     *
     * @param textChunks
     * @param client
     * @param voice
     * @param audioConfig
     * @throws IOException
     */
    public void otherWayToWriteWAVFile(String[] textChunks, TextToSpeechClient client,
                                       VoiceSelectionParams voice, AudioConfig audioConfig) throws IOException {
        //  byte[] to WAV file

        // Output WAV file
        String outputFile = "output.wav";

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {

            // We'll buffer raw PCM first
            ByteArrayOutputStream rawAudio = new ByteArrayOutputStream();

            for (String text : textChunks) {
                SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

                SynthesizeSpeechResponse response = client.synthesizeSpeech(input, voice, audioConfig);

                ByteString audioContents = response.getAudioContent();
                rawAudio.write(audioContents.toByteArray());

                System.out.println("Appended " + audioContents.size() + " bytes");
            }

            // Now wrap raw PCM into a proper WAV header
            byte[] pcmData = rawAudio.toByteArray();

            // Define audio format (must match sampleRate + LINEAR16)
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);

            ByteArrayInputStream bais = new ByteArrayInputStream(pcmData);

            AudioInputStream ais = new AudioInputStream(bais, format, pcmData.length / format.getFrameSize());

            // Write WAV file
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, fos);
            System.out.println("✅ WAV file written: " + outputFile);

        }

    }

}
