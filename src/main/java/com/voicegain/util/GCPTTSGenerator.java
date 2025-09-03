package com.voicegain.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.util.Arrays;


@Service
public class GCPTTSGenerator {

    public void getTTSWAV(String text, String voiceName) throws Exception {

        // Test
        runStreamingTtsQuickstart();
    }

    // Copyright 2024 Google
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


//    public class StreamingTtsQuickstart {

        public static void runStreamingTtsQuickstart() throws Exception {

            // Path to your service account JSON key
            String jsonPath = "/Users/sachinakula/Downloads/tts-firstcut/src/main/resources/ascalon-dev-705e770fdfbc.json";

            // Load credentials
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));

            // Attach credentials to client settings
            TextToSpeechSettings settings = TextToSpeechSettings.newBuilder()
                    .setCredentialsProvider(() -> credentials)
                    .build();

            try (TextToSpeechClient client = TextToSpeechClient.create(settings)) {

                // Configure the voice
                VoiceSelectionParams voice =
                        VoiceSelectionParams.newBuilder()
                                .setName("en-US-Chirp3-HD-Charon")
                                .setLanguageCode("en-US")
                                .build();

                // Configure the streaming request
                StreamingSynthesizeConfig streamingConfig =
                        StreamingSynthesizeConfig.newBuilder()
                                .setVoice(voice)
                                .build();

                // First request: contains only the config
                StreamingSynthesizeRequest configRequest =
                        StreamingSynthesizeRequest.newBuilder()
                                .setStreamingConfig(streamingConfig)
                                .build();

                AudioConfig audioConfig = AudioConfig.newBuilder()
                        .setAudioEncoding(AudioEncoding.MP3)
                        .build();

//                String[] textChunks = {
//                        "Hello there. ",
//                        "How are you ",
//                        "today? It's ",
//                        "such nice weather outside."
//                };


                String[] textChunks = {
                        "Hello there. "
                };


                for (String text : textChunks) {
                    SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
                    SynthesizeSpeechResponse response =
                            client.synthesizeSpeech(input, voice, audioConfig);

                    byte[] audioContent = response.getAudioContent().toByteArray();

//                    System.out.write(audioContent);
//                    System.out.println();

                    String pathname = "audiobytes.wav";

                    writeToFile(audioContent, pathname);

                    System.out.println("Got " + audioContent.length + " bytes of audio.");

                    System.out.println("Printing audioContent bytes: "+ Arrays.toString(audioContent));
                }

                //  byte[] to WAV file

                // Output WAV file
                String outputFile = "output.wav";
                try (FileOutputStream fos = new FileOutputStream(outputFile)) {

                    // We'll buffer raw PCM first
                    ByteArrayOutputStream rawAudio = new ByteArrayOutputStream();

                    for (String text : textChunks) {
                        SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

                        SynthesizeSpeechResponse response =
                                client.synthesizeSpeech(input, voice, audioConfig);

                        ByteString audioContents = response.getAudioContent();
                        rawAudio.write(audioContents.toByteArray());

                        System.out.println("Appended " + audioContents.size() + " bytes");
                    }

                    // Now wrap raw PCM into a proper WAV header
                    byte[] pcmData = rawAudio.toByteArray();

                    // Define audio format (must match sampleRate + LINEAR16)
                    AudioFormat format = new AudioFormat(16000, 16, 1, true,
                            false);

                    ByteArrayInputStream bais = new ByteArrayInputStream(pcmData);

                    AudioInputStream ais = new AudioInputStream(bais, format,
                            pcmData.length / format.getFrameSize());

                    // Write WAV file
                    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, fos);
                    System.out.println("✅ WAV file written: " + outputFile);
                }



                //

//                // Text chunks to stream
//                String[] textIterator = {
//                        "Hello there. ",
//                        "How are you ",
//                        "today? It's ",
//                        "such nice weather outside."
//                };

                // Send requests
//                client.streamingSynthesizeCallable()
//                        .call(
//                                responseObserver -> {
//                                    // First send config request
//                                    responseObserver.onNext(configRequest);
//
//                                    // Then send text requests
//                                    for (String text : textIterator) {
//                                        StreamingSynthesizeRequest textRequest =
//                                                StreamingSynthesizeRequest.newBuilder()
//                                                        .setInput(
//                                                                StreamingSynthesisInput.newBuilder()
//                                                                        .setText(text)
//                                                                        .build())
//                                                        .build();
//                                        responseObserver.onNext(textRequest);
//                                    }
//
//                                    responseObserver.onCompleted();
//                                },
//                                (StreamingSynthesizeResponse response) -> {
//                                    System.out.println("Audio content size in bytes is: "
//                                            + response.getAudioContent().size());
//                                });
            }
        }

//        public static void main(String[] args) throws Exception {
//            runStreamingTtsQuickstart();
//        }
//    }

    public static void writeToFile(byte[] bytes, String pathname) {
        try (FileOutputStream fos = new FileOutputStream(pathname)) {
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


}
