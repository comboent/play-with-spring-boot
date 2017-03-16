package org.combo.cfg;

import org.combo.util.JsonUtils;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringMVCCfg{

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        MappingJackson2HttpMessageConverter jacksonMsgConverter = new MappingJackson2HttpMessageConverter();
        jacksonMsgConverter.setDefaultCharset(Charset.forName("UTF-8"));
        jacksonMsgConverter.setObjectMapper(JsonUtils.getObjectMapper());
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.valueOf("text/html;charset=UTF-8"));
        mediaTypes.add(MediaType.valueOf("application/json;charset=UTF-8"));
        jacksonMsgConverter.setSupportedMediaTypes(mediaTypes);
        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(jacksonMsgConverter);
        return httpMessageConverters;
    }
}
