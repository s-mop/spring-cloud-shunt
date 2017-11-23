package org.shunt;

import javax.servlet.Filter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
        WeightMetadataRule.META_DATA_KEY_WEIGHT = shuntProperties.getWeight();
        return new TagMetadataRule();
    }

    @Bean
    public Filter TraceFilter() {
        return new TagFilter();
    }
}
