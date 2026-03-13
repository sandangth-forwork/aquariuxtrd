package sanlab.itv.aquariuxtrd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    @Lazy
    @Bean
    public RestClient binanceClient(@Value("${app.client.binance.url}") String binanceUrl) {
        return RestClient.create(binanceUrl);
    }

    @Lazy
    @Bean
    public RestClient houbiClient(@Value("${app.client.houbi.url}") String houbiUrl) {
        return RestClient.create(houbiUrl);
    }

}
