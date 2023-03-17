package com.ducheng.easy.ms.autoconfig;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/**
 *  meilisearch的自动注入配置类
 */
@Configuration
public class MeiliSearchAutoConfiguration {

    @Resource
    MeiliSearchProperties properties;

    @Bean
    @ConditionalOnMissingBean(Client.class)
    Client client() {
        return new Client(config());
    }

    @Bean
    @ConditionalOnMissingBean(Config.class)
    Config config() {
        return new Config(properties.getHost(), properties.getApiKey());
    }

}
