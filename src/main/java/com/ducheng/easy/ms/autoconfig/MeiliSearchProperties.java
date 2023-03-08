package com.ducheng.easy.ms.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *meilisearch的属性配置类
 */
@Configuration
@ConfigurationProperties(prefix = "easy-ms")
public class MeiliSearchProperties {

    private static   String  default_host = "http://81.70.16.9:7700";

    //meilisearch 的url地址
    private String host =  default_host;

    //默认的认证的签名key
    private String apiKey = "";

    public String getHost() {
        return host ;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
