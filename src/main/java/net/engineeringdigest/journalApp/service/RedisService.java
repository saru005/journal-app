package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object value = redisTemplate.opsForValue().get(key);

            if (value == null) return null;

            // Convert the stored object (possibly LinkedHashMap) into the target POJO
            return mapper.convertValue(value, entityClass);

        } catch (Exception e) {
            log.error("Redis get Exception :: {}", String.valueOf(e));
            return null;
        }
    }

    public void set(String key, Object value, Long ttlSeconds) {
        try {
            if (ttlSeconds != null && ttlSeconds > 0) {
                redisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
        } catch (Exception e) {
            System.out.println("Redis set exception: " + e.getMessage());
        }
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
