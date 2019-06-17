package com.neticon.neticon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * . suppose we only support json as encoding and decoding type so, try to convert string, html or else media type to
 * json and to POJO object
 */
public class TryMapJson2HttpConverter extends MappingJackson2HttpMessageConverter {

    public TryMapJson2HttpConverter() {
        super();
        extendSupportedMediaTypes();
    }

    public TryMapJson2HttpConverter(ObjectMapper objectMapper) {
        super(objectMapper);
        extendSupportedMediaTypes();
    }

    protected void extendSupportedMediaTypes() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.addAll(getSupportedMediaTypes());
        mediaTypes.add(new MediaType("text", "*", DEFAULT_CHARSET));
        setSupportedMediaTypes(mediaTypes);
    }
}

