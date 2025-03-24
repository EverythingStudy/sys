package cn.staitech.gateway.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 切片服务列表
 *
 * @author staitech
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "slideserver")
public class SlideServerProperties {

    private List<String> hosts = new ArrayList<>();

    public Map<Integer, String> getHosts() {

        Map<Integer, String> map = new HashMap<>();
        for (String host : hosts) {
            String[] hostArr = host.split(",");
            map.put(Integer.parseInt(hostArr[0].trim()), hostArr[1].trim());
        }
        return map;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

}
