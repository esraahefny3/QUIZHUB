package com.toptal.quizhub.cache.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.quizhub.cache.api.CacheFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class RedisCache<T extends Object> implements CacheFactory<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCache.class);
    private final RedisTemplate<String, String> redisTemplate;

    private static ObjectMapper MAPPER = new ObjectMapper();

    public RedisCache(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void put(String key, T value) throws JsonProcessingException {

        final String valStr = MAPPER.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, valStr);
    }

    @Override
    public T get(String key, Class<?> cls) throws JsonProcessingException {
        String value = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(value)) {
            return (T) MAPPER.readValue(value, cls);
        }
        return null;
    }

    public void deleteAll(String pattern) {
        final Set<String> keys = redisTemplate.keys(pattern);
        if (Objects.nonNull(keys) && !keys.isEmpty()) {
            keys.stream().forEach(k -> redisTemplate.delete(k));
        }
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public List<String> getKeysByPattern(String pattern) {
        Set<String> matchedKeys = redisTemplate.keys(pattern);
        if(Objects.nonNull(matchedKeys))
        {
            return new ArrayList<>(matchedKeys);
        }
        return new ArrayList<>();

    }

    public List<T> getValuesByKeys(List<String> keys,Class<?> cls) {

        List<String> values =  redisTemplate.opsForValue().multiGet(keys);
        if (Objects.nonNull(values)) {
            return values.stream().map(v-> {
                try {
                    return (T) MAPPER.readValue(v, cls);
                } catch (JsonProcessingException e) {
                    LOGGER.error("Error while reading from cache.", e);
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        }
        return null;
    }

    public List<T> getPaginatedValuesByPattern(String pattern, int offset, int limit,Class<?> cls) {
        List<String> keys = getKeysByPattern(pattern);
        int startIndex = offset;
        int endIndex = startIndex + limit+1; //fetch extra element to determine hasNext in pagination flow
        if (startIndex >= keys.size()) {
            return new ArrayList<>();
        }
        if (endIndex >= keys.size()) {
            endIndex = keys.size() - 1;
        }
        if (keys.size() > 0) {
            List<String> paginatedKeys = keys.subList(startIndex, endIndex );
            return getValuesByKeys(paginatedKeys, cls);
        }
        return new ArrayList<>();
    }
}
