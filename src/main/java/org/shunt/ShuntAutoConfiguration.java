package org.shunt;

import javax.servlet.Filter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by charles on 2017/5/25.
 */
@Configuration
@EnableConfigurationProperties(ShuntProperties.class)
public class ShuntAutoConfiguration {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new TagInterceptor());
        return restTemplate;
    }

    @Bean
    public TagMetadataRule getTagMetadataRule(ShuntProperties shuntProperties) {
        TagHolder.X_TAG_NAME = shuntProperties.getTag();
        return new TagMetadataRule();
    }

    @Bean
    public Filter TraceFilter() {
        return new TagFilter();
    }
}
