package cn.staitech.system.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "forward")
public class ExampleServiceProperties {

    private String phyonUrl;

    public String getPhyonUrl() {
        return phyonUrl;
    }

    public void setPhyonUrl(String phyonUrl) {
        this.phyonUrl = phyonUrl;
    }
}