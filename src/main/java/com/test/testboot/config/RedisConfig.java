package com.test.testboot.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> Template = new RedisTemplate<Object, Object>();
        Template.setKeySerializer(new StringRedisSerializer());
        Template.setDefaultSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        Template.setConnectionFactory(redisConnectionFactory);
        return Template;
    }

}
