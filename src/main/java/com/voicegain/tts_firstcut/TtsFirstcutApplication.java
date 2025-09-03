package com.voicegain.tts_firstcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.voicegain"})
public class TtsFirstcutApplication {

	public static void main(String[] args) {

		SpringApplication.run(TtsFirstcutApplication.class, args);

	}

}
