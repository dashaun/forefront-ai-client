package dev.dashaun.client.ai.forefront.cli;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
public class ClientConfig {
    
    private final ConfigProps configProps;

    public ClientConfig(ConfigProps configProps) {
        this.configProps = configProps;
    }

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(30));
        
        WebClient client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(String.format("https://solutions.forefront.ai/v1/organization/%s",configProps.teamId()))
                .defaultHeader("Authorization", String.format("Bearer %s",configProps.modelKey()))
                .defaultHeader("Content-Type", "application/json")
                .build();
        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
    }

    @Bean
    public ForeFrontClient foreFrontClient(HttpServiceProxyFactory factory) {
        return factory.createClient(ForeFrontClient.class);
    }

}