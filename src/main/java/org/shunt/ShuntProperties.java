package org.shunt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "org.shunt")
public class ShuntProperties {

    /**
     * name of header in http request, used for A/B test or canary test
     */
    private String tag = "x-tag";

    /**
     * weight of instance
     */
    private String weight = "weight";

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}