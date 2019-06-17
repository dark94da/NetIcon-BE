package com.neticon.neticon.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @Primary
    public AsyncRestTemplate asyncRestTemplate() {
        // NIO high level concurrent
        HttpComponentsAsyncClientHttpRequestFactory httpRequestFactory = new
                HttpComponentsAsyncClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(300);
        httpRequestFactory.setReadTimeout(600);
        httpRequestFactory.setConnectionRequestTimeout(300);

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(httpRequestFactory);
        asyncRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        asyncRestTemplate.getMessageConverters().add(0, tryMapJson2HttpConverter());

        return asyncRestTemplate;
    }

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(300);
        httpRequestFactory.setReadTimeout(600);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(0, tryMapJson2HttpConverter());

        return restTemplate;
    }

    private TryMapJson2HttpConverter tryMapJson2HttpConverter() {
        TryMapJson2HttpConverter tryMapJson2HttpConverter = new TryMapJson2HttpConverter();
        tryMapJson2HttpConverter.getObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        tryMapJson2HttpConverter.getObjectMapper()
                .getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        return tryMapJson2HttpConverter;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return objectMapper;
    }
}
