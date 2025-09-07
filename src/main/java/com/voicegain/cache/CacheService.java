package com.voicegain.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class CacheService {

    private static final Logger log = LoggerFactory.getLogger(CacheService.class);

    private Map<String, ByteArrayOutputStream> cachedContent = new HashMap<>();

    public CacheService() {
    }

    public void put(String key, ByteArrayOutputStream byteStream) {
        log.info("Caching content for: {}", key);
        cachedContent.put(key, byteStream);
    }

    public ByteArrayOutputStream get(String key) {
        if(cachedContent.containsKey(key)) {
            log.info("Found cached content for key: {}", key);
        }
        return cachedContent.get(key);
    }
}
