package com.hasan.thesisProject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasan.thesisProject.dto.ProductResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ProductResponseDTO> redisTemplate(
            RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, ProductResponseDTO> template =
                new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);

        // Clean JSON serializer
        Jackson2JsonRedisSerializer<ProductResponseDTO> serializer =
                new Jackson2JsonRedisSerializer<>(ProductResponseDTO.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();

        return template;
    }
}
